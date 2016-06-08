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

import static ilarkesto.core.base.Str.isBlank;
import ilarkesto.core.logging.Log;
import static ilarkesto.io.nio.httpserver.HttpStatusCode.INTERNAL_SERVER_ERROR;
import ilarkesto.io.nio.tcpserver.TcpConnection;
import static java.lang.Integer.parseInt;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest<S> {

	static String SERVER = "Ilarkesto/1.1";
	static final String PROTOCOL_VERSION = "HTTP/1.1";

	private static Log log = Log.get(HttpRequest.class);

	private HttpMethod method;
	private String uri;
	private String version;
	private TcpConnection connection;
	private Map<String, String> headers = new HashMap<>();

	private String responseStatusLine;
	private Map<String, String> responseHeaders = new HashMap<>();
	private boolean responseHeadersSent;

	private HttpSession<S> session;

	public HttpRequest(TcpConnection connection) {
		super();
		this.connection = connection;
	}

	void setSession(HttpSession<S> session) {
		this.session = session;
	}

	public HttpSession<S> getSession() {
		return session;
	}

	public String getHeader(String name) {
		return headers.get(formatHeaderName(name));
	}

	public Integer getHeaderAsInteger(String name) {
		String value = getHeader(name);
		if (value == null) {
                        return null;
                }
		return parseInt(value);
	}

	void setMethod(HttpMethod method) {
		this.method = method;
	}

	void setUri(String uri) {
		this.uri = uri;
	}

	void setVersion(String version) {
		this.version = version;
	}

	void setHeader(String name, String value) {
		headers.put(formatHeaderName(name), value);
	}

	private String formatHeaderName(String name) {
		return name.toLowerCase();
	}

	public void setResponseHeader(String name, String value) {
		responseHeaders.put(formatHeaderName(name), value);
	}

	private void closeConnection() {
		connection.close();
	}

	public void setResponseStatus(HttpStatusCode code, String message) {
		String text = code.getText();
		if (!isBlank(message)) {
                        text += ": " + message;
                }
		responseStatusLine = PROTOCOL_VERSION + " " + code.getCode() + " " + text;
	}

	public void sendEmptyResponse(HttpStatusCode code) {
		sendEmptyResponse(code, null);
	}

	public void sendEmptyResponse(HttpStatusCode code, String statusMessage) {
		setResponseStatus(code, statusMessage);
		sendResponseHeaders();
		closeConnection();
	}

	public void sendResponseHeaders() {
		if (responseHeadersSent) {
                        throw new IllegalStateException("Response headers already sent: " + responseStatusLine + " -> "
                                + toString());
                }
		if (responseStatusLine == null) {
			setResponseStatus(INTERNAL_SERVER_ERROR, null);
			log.error("sendHeaders() responseStatusLine==null");
		}
		sendLine(responseStatusLine);
		completeResponseHeaders();
		for (Map.Entry<String, String> header : responseHeaders.entrySet()) {
			sendLine(header.getKey() + ": " + header.getValue());
		}
		sendLine("");
		responseHeadersSent = true;
		log.debug(responseStatusLine);
	}

	private void completeResponseHeaders() {
		completeResponseHeader("Server", SERVER);
	}

	private void completeResponseHeader(String name, String defaultValue) {
		name = formatHeaderName(name);
		if (!responseHeaders.containsKey(name)) {
                        responseHeaders.put(name, defaultValue);
                }
	}

	private void sendLine(String line) {
		connection.sendLineCrLf(line);
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(method.name());
		sb.append(" ");
		sb.append(uri);
		sb.append(" ");
		sb.append(version);
		return sb.toString();
	}

	// --- header fields ---

	public String getHeaderHost() {
		return getHeader("Host");
	}

	public String getHeaderTransferEncoding() {
		return getHeader("Transfer-Encoding");
	}

	public Integer getHeaderContentLenght() {
		return getHeaderAsInteger("Content-Lenght");
	}

	// --- response header fields ---

	public void setResponseHeaderServer(String server) {
		setResponseHeader("Server", server);
	}

}
