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
package ilarkesto.io.nio.httpserver;

import ilarkesto.concurrent.TaskManager;
import static ilarkesto.io.nio.httpserver.HttpStatusCode.NOT_FOUND;
import ilarkesto.io.nio.tcpserver.DataHandler;
import ilarkesto.io.nio.tcpserver.PerConnectionDataHandler;
import ilarkesto.io.nio.tcpserver.PerConnectionDataHandler.HandlerFacotry;
import ilarkesto.io.nio.tcpserver.TcpConnection;
import ilarkesto.io.nio.tcpserver.TcpServer;
import java.util.HashSet;
import java.util.Set;

public class HttpServer<S> {

	private TcpServer server;
	private String name;

	private Set<HttpSession<S>> sessions = new HashSet<>();

	public HttpServer(int port, String serverName) {
		this.name = serverName;
		server = new TcpServer(port, new PerConnectionDataHandler(new LocalHandlerFactory()));
	}

	void onHttpRequest(HttpRequest request) {
		updateSession(request);
		request.sendEmptyResponse(NOT_FOUND);
	}

	private void updateSession(HttpRequest request) {
		HttpSession<S> session = getSession("todo");
		if (session == null) {
			session = new HttpSession<>();
			// TODO create session bean
		}
		request.setSession(session);
		session.touch();
	}

	private HttpSession<S> getSession(String id) {
		for (HttpSession<S> session : sessions) {
			if (session.getId().equals(id)) {
                                return session;
                        }
		}
		return null;
	}

	public void start(TaskManager taskManager) {
		server.start(taskManager);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "HTTP-Server:" + server.getPort();
	}

	class LocalHandlerFactory implements HandlerFacotry {

		@Override
		public DataHandler createHandler(TcpConnection connection) {
			return new HttpDataHandler(HttpServer.this);
		}
	}

}
