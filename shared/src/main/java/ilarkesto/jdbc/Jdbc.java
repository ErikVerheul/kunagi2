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
import static java.lang.Class.forName;
import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc {

	private static final Log log = Log.get(Jdbc.class);

	public static Connection createConnection(String driver, String protocol, String host, String port,
			String database, String login, String password) {
		loadDriver(driver);
		String url = createConnectionUrl(protocol, host, port, database);
		log.info("Connecting database:", url);
		try {
			return getConnection(url, login, password);
		} catch (SQLException ex) {
			throw new RuntimeException("Connecting database failed: " + url, ex);
		}
	}

	public static String createConnectionUrl(String protocol, String host, String port, String database) {
		StringBuilder sb = new StringBuilder();
		sb.append(protocol);
		sb.append("://");
		sb.append(host == null ? "localhost" : host);
		if (port != null) {
                        sb.append(":").append(port);
                }
		if (database != null) {
                        sb.append("/").append(database);
                }
		return sb.toString();
	}

	public static void loadDriver(String driver) {
		try {
			forName(driver).newInstance();
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("Loading JDBC driver failed: " + driver, ex);
		} catch (Exception ex) {
			throw new RuntimeException("Instantiating JDBC driver failed: " + driver, ex);
		}
	}

	public static PreparedStatement prepareStatement(Connection connection, String sql, Object... params) {
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
		} catch (SQLException ex) {
			throw new RuntimeException("Preparing SQL statement failed", ex);
		}
		int len = params.length;
		for (int i = 0; i < len; i++) {
			Object value = params[i];
			try {
				stmt.setObject(i + 1, value);
			} catch (SQLException ex) {
				throw new RuntimeException("Setting param " + i + " in prepared SQL statement failed:" + value, ex);
			}
		}
		return stmt;
	}

	public static void executeQuery(Connection connection, RecordHandler handler, String sql, Object... params)
			throws SQLException {
		executeQuery(handler, prepareStatement(connection, sql, params));
	}

	public static void executeQuery(RecordHandler handler, PreparedStatement stmt) throws SQLException {
		execute(handler, stmt);
	}

	public static void execute(Connection connection, String sql, Object... params) throws SQLException {
		execute(prepareStatement(connection, sql, params));
	}

	public static void execute(PreparedStatement stmt) throws SQLException {
		execute(null, stmt);
	}

	private static void execute(RecordHandler handler, PreparedStatement stmt) throws SQLException {
		log.debug("SQL:", stmt);

		ResultSet rs = null;
		try {
			stmt.execute();
			if (handler != null) {
				rs = stmt.getResultSet();
				handler.onExecuted(rs);
				while (rs.next()) {
					handler.onRecord(rs);
				}
			}
		} finally {
			closeQuiet(rs);
			closeQuiet(stmt);
		}
	}

	public static void closeQuiet(Connection connection) {
		if (connection == null) {
                        return;
                }
		try {
			if (connection.isClosed()) {
                                return;
                        }
		} catch (SQLException ex) {}
		try {
			connection.close();
		} catch (SQLException ex) {
			log.error("Closing database connection failed", ex);
		}
	}

	public static void closeQuiet(Statement stmt) {
		if (stmt == null) {
                        return;
                }
		try {
			if (stmt.isClosed()) {
                                return;
                        }
		} catch (SQLException ex) {}
		try {
			stmt.close();
		} catch (SQLException ex) {
			log.error("Closing database statement failed", ex);
		}
	}

	public static void closeQuiet(ResultSet rs) {
		if (rs == null) {
                        return;
                }
		try {
			if (rs.isClosed()) {
                                return;
                        }
		} catch (SQLException ex) {}
		try {
			rs.close();
		} catch (SQLException ex) {
			log.error("Closing database result set failed", ex);
		}
	}

	public static abstract class RecordHandler {

		void onExecuted(ResultSet rs) throws SQLException {}

		abstract void onRecord(ResultSet rs) throws SQLException;
	}

}
