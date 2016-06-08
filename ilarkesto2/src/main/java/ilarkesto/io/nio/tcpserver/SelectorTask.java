/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.io.nio.tcpserver;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import ilarkesto.concurrent.ALoopTask;
import ilarkesto.core.logging.Log;
import static ilarkesto.core.logging.Log.get;
import static ilarkesto.io.nio.tcpserver.ChangeRequest.CHANGEOPS;
import static ilarkesto.io.nio.tcpserver.TcpConnection.CLOSE_CONNECTION;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import static java.nio.ByteBuffer.allocate;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import static java.nio.channels.SelectionKey.OP_ACCEPT;
import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import static java.nio.channels.ServerSocketChannel.open;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import static java.nio.channels.spi.SelectorProvider.provider;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SelectorTask extends ALoopTask {

	private Log log = get(getClass());

	private WorkerTask worker;
	private int port;

        @SuppressWarnings("UWF_UNWRITTEN_FIELD")
	private InetAddress hostAddress;
	private ServerSocketChannel serverChannel;
	private Selector selector;

	private ByteBuffer readBuffer = allocate(8192);

	private final List changeRequests = new LinkedList();
	private final List<TcpConnection> connections = new LinkedList<>();

	public SelectorTask(int port, WorkerTask worker) {
		this.port = port;
		this.worker = worker;
	}

	@Override
	protected void beforeLoop() throws InterruptedException {
		try {
			selector = initSelector();
		} catch (IOException ex) {
			throw new RuntimeException("Initializing selector failed.", ex);
		}
		log.info("TCP server started on port", port);
	}

	@Override
	protected void iteration() throws InterruptedException {
		synchronized (this.changeRequests) {
			Iterator changes = this.changeRequests.iterator();
			while (changes.hasNext()) {
                                ChangeRequest change = (ChangeRequest) changes.next();
                                if (change.type == CHANGEOPS) {
                                        SelectionKey key = change.socket.keyFor(this.selector);
                                        if (key.isValid()) {
                                                key.interestOps(change.ops);
                                        }
                                }
                        }
			this.changeRequests.clear();
		}

		try {
			selector.select();
		} catch (IOException ex) {
			throw new RuntimeException("Selector.select() failed.");
		}

		Iterator selectedKeys = this.selector.selectedKeys().iterator();
		while (selectedKeys.hasNext()) {
			SelectionKey key = (SelectionKey) selectedKeys.next();
			selectedKeys.remove();

			if (!key.isValid()) {
				continue;
			}

			if (key.isAcceptable()) {
				try {
					accept(key);
				} catch (IOException ex) {
					throw new RuntimeException("Accepting connection failed.", ex);
				}
			} else if (key.isReadable()) {
				try {
					read(key);
				} catch (IOException ex) {
					throw new RuntimeException("Reading failed.", ex);
				}
			} else if (key.isWritable()) {
				try {
					this.write(key);
				} catch (IOException ex) {
					throw new RuntimeException("Writing failed.", ex);
				}
			}
		}
	}

	@Override
	protected void onError(Throwable ex) throws Throwable {
		throw ex;
	}

	void sendChangeRequestForWrite(SocketChannel socket) {
		synchronized (changeRequests) {
			changeRequests.add(new ChangeRequest(socket, CHANGEOPS, OP_WRITE));
		}
	}

	void wakeupSelector() {
		selector.wakeup();
	}

	private void write(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		TcpConnection connection = getConnectionByChannel(socketChannel);
		// Write until there's not more data ...
		while (!connection.pendingData.isEmpty()) {
			ByteBuffer data = connection.pendingData.peek();
			if (data == CLOSE_CONNECTION) {
				log.debug("Closing client connection:", connection);
				closeConnectionInternal(connection);
				return;
			}
			socketChannel.write(data);
			if (data.remaining() > 0) {
				// ... or the socket's buffer fills up
				break;
			}
			connection.pendingData.remove(data);
		}
		if (connection.pendingData.isEmpty()) {
			// We wrote away all data, so we're no longer interested
			// in writing on this socket. Switch back to waiting for
			// data.
			key.interestOps(OP_READ);
		}
	}

	private void read(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		TcpConnection connection = getConnectionByChannel(socketChannel);

		this.readBuffer.clear();

		int numRead;
		try {
			numRead = socketChannel.read(this.readBuffer);
		} catch (IOException e) {
			log.debug("Client forcibly closed connection:", connection);
			closeConnection(key);
			return;
		}

		if (numRead == -1) {
			log.debug("Client closed connection:", connection);
			closeConnection(key);
			return;
		}

		worker.processData(connection, readBuffer.array(), numRead);
	}

	private TcpConnection getConnectionByKey(SelectionKey key) {
		return getConnectionByChannel(key.channel());
	}

	private TcpConnection getConnectionByChannel(SelectableChannel channel) {
		synchronized (connections) {
			for (TcpConnection connection : connections) {
				if (connection.socketChannel == channel) {
                                        return connection;
                                }
			}
		}
		throw new IllegalStateException("No TcpConnection found for channel " + channel);
	}

	private void closeConnection(SelectionKey key) {
		key.cancel();
		closeConnectionInternal(getConnectionByKey(key));
	}

	private void closeConnectionInternal(TcpConnection connection) {
		synchronized (connections) {
			connections.remove(connection);
		}
		try {
			connection.socketChannel.close();
		} catch (IOException ex) {
			// nop
		}
		worker.processData(connection, null, -1);
	}

	private void accept(SelectionKey key) throws IOException {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		TcpConnection tcpConnection = new TcpConnection(this, socketChannel);
		synchronized (connections) {
			connections.add(tcpConnection);
		}

		log.debug("Client connected:", tcpConnection);

		socketChannel.configureBlocking(false);
		socketChannel.register(this.selector, OP_READ);
	}

	private Selector initSelector() throws IOException {
		Selector socketSelector = provider().openSelector();
		serverChannel = open();
		serverChannel.configureBlocking(false);
		InetSocketAddress isa = new InetSocketAddress(hostAddress, port);
		serverChannel.socket().bind(isa);
		serverChannel.register(socketSelector, OP_ACCEPT);
		return socketSelector;
	}

	public int getPort() {
		return port;
	}

	@Override
	public String toString() {
		return "TCP-Server:" + port;
	}

}
