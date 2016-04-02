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

import static ilarkesto.io.IO.UTF_8;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import static java.nio.ByteBuffer.wrap;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TcpConnection {

	static final ByteBuffer CLOSE_CONNECTION = wrap(new byte[0]);

	SelectorTask server;
	SocketChannel socketChannel;
	String remoteHost;
	int remotePort;
	int localPort;

	ConcurrentLinkedQueue<ByteBuffer> pendingData = new ConcurrentLinkedQueue<>();
	boolean closed;

	TcpConnection(SelectorTask server, SocketChannel socketChannel) {
		this.server = server;
		this.socketChannel = socketChannel;
		Socket socket = getSocket();
		remoteHost = socket.getInetAddress().getHostAddress();
		remotePort = socket.getPort();
		localPort = socket.getLocalPort();
	}

	public void sendData(byte[] data) {
		if (closed) {
                        throw new IllegalStateException("Connection already closed: " + toString());
                }
		server.sendChangeRequestForWrite(socketChannel);
		pendingData.add(data == null ? CLOSE_CONNECTION : wrap(data));
		server.wakeupSelector();
	}

	public void sendString(String s) throws UnsupportedEncodingException {
		sendData(s.getBytes(UTF_8));
	}

	public void sendLineCrLf(String line) {
                try {
                        sendString(line);
                } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
		sendData(new byte[] { 13, 10 });
	}

	public void close() {
		sendData(null);
		closed = true;
	}

	Socket getSocket() {
		return socketChannel.socket();
	}

	@Override
	public String toString() {
		return localPort + "<=" + remoteHost + ":" + remotePort;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof TcpConnection)) {
                        return false;
                }
		return socketChannel.equals(((TcpConnection) other).socketChannel);
	}

	@Override
	public int hashCode() {
		return socketChannel.hashCode();
	}

}
