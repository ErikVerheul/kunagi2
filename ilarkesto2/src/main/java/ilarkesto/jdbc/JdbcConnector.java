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
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package ilarkesto.jdbc;

import ilarkesto.core.logging.Log;
import ilarkesto.jdbc.Jdbc.RecordHandler;
import static ilarkesto.jdbc.Jdbc.closeQuiet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcConnector {

	private static final Log log = Log.get(JdbcConnector.class);

	private final String driver;
	private final String protocol;
	private final String host;
	private final String port;
	private final String database;
	private final String login;
	private final String password;

	private Connection connection;

	public JdbcConnector(String driver, String protocol, String host, String port, String database, String login,
			String password) {
		this.driver = driver;
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.database = database;
		this.login = login;
		this.password = password;
	}

	public PreparedStatement prepareStatement(String sql, Object... params) {
		return Jdbc.prepareStatement(getConnection(), sql, params);
	}

	public void executeQuery(RecordHandler handler, String sql, Object... params) throws SQLException {
		Jdbc.executeQuery(getConnection(), handler, sql, params);
	}

	public void execute(String sql, Object... params) throws SQLException {
		Jdbc.execute(connection, sql, params);
	}

	public synchronized Connection getConnection() {
		if (connection == null) {
			createConnection();
		} else {
			try {
				if (connection.isClosed()) {
                                        createConnection();
                                }
			} catch (SQLException ex) {
				createConnection();
			}
		}
		return connection;
	}

	private synchronized void createConnection() {
		connection = Jdbc.createConnection(driver, protocol, host, port, database, login, password);
	}

	public synchronized void closeConnection() {
		closeQuiet(connection);
		connection = null;
	}

}
