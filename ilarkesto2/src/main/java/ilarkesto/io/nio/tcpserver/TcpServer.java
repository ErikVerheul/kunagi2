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

import ilarkesto.concurrent.TaskManager;

// http://rox-xmlrpc.sourceforge.net/niotut/
public class TcpServer {

	private SelectorTask selectorTask;
	private WorkerTask workerTask;

	public TcpServer(int port, DataHandler dataHandler) {
		workerTask = new WorkerTask(dataHandler);
		selectorTask = new SelectorTask(port, workerTask);
	}

	public void start(TaskManager taskManager) {
		taskManager.start(workerTask);
		taskManager.start(selectorTask);
	}

	public void stop() {
		selectorTask.abort();
		workerTask.abort();
	}

	public int getPort() {
		return selectorTask.getPort();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ":" + selectorTask.getPort();
	}

}
