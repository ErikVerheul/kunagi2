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

public class ServerDataEvent {

	private final TcpConnection connection;
	private final byte[] data;

        @SuppressWarnings("EI_EXPOSE_REP2")
	public ServerDataEvent(TcpConnection connection, byte[] data) {
		this.connection = connection;
		this.data = data;
	}

	public TcpConnection getConnection() {
		return connection;
	}

        @SuppressWarnings("EI_EXPOSE_REP")
	public byte[] getData() {
		return data;
	}

}
