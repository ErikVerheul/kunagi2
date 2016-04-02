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

import java.util.HashMap;
import java.util.Map;

public class PerConnectionDataHandler implements DataHandler {

	private HandlerFacotry factory;

	private Map<TcpConnection, DataHandler> handlers = new HashMap<>();

	public PerConnectionDataHandler(Class<? extends DataHandler> handlerType) {
		this(new ReflectionHandlerFactory(handlerType));
	}

	public PerConnectionDataHandler(HandlerFacotry factory) {
		super();
		this.factory = factory;
	}

	@Override
	public void onDataReceived(ServerDataEvent event) {
		getHandler(event.getConnection()).onDataReceived(event);
	}

	@Override
	public void onConnectionClosed(TcpConnection connection) {
		synchronized (handlers) {
			handlers.remove(connection);
		}
		getHandler(connection).onConnectionClosed(connection);
	}

	private DataHandler getHandler(TcpConnection connection) {
		synchronized (handlers) {
			DataHandler handler = handlers.get(connection);
			if (handler == null) {
				handler = factory.createHandler(connection);
				handlers.put(connection, handler);
			}
			return handler;
		}
	}

	public static interface HandlerFacotry {

		DataHandler createHandler(TcpConnection connection);

	}

	private static class ReflectionHandlerFactory implements HandlerFacotry {

		private Class<? extends DataHandler> type;

		public ReflectionHandlerFactory(Class type) {
			super();
			this.type = type;
		}

		@Override
		public DataHandler createHandler(TcpConnection connection) {
			try {
				return type.newInstance();
			} catch (InstantiationException | IllegalAccessException ex) {
				throw new RuntimeException(ex);
			}
		}

	}

}
