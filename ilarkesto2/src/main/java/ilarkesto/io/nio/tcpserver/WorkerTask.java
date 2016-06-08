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

import ilarkesto.concurrent.ALoopTask;
import static java.lang.System.arraycopy;
import java.util.LinkedList;
import java.util.List;

public class WorkerTask extends ALoopTask {

	private DataHandler handler;

	private final List<ServerDataEvent> queue = new LinkedList<>();

	public WorkerTask(DataHandler handler) {
		super();
		this.handler = handler;
	}

	public void processData(TcpConnection connection, byte[] data, int count) {
		byte[] dataCopy;
		if (data == null) {
			dataCopy = null;
		} else {
			dataCopy = new byte[count];
			arraycopy(data, 0, dataCopy, 0, count);
		}
		synchronized (queue) {
			queue.add(new ServerDataEvent(connection, dataCopy));
			queue.notify();
		}
	}

	@Override
	protected void iteration() throws InterruptedException {
		ServerDataEvent dataEvent;

		synchronized (queue) {
			while (queue.isEmpty()) {
				queue.wait();
			}
			dataEvent = queue.remove(0);
		}

		if (dataEvent.getData() == null) {
			handler.onConnectionClosed(dataEvent.getConnection());
		} else {
			handler.onDataReceived(dataEvent);
		}
	}

}
