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

import ilarkesto.core.logging.Log;
import static ilarkesto.io.IO.UTF_8;
import static ilarkesto.io.nio.httpserver.HttpMethod.values;
import static ilarkesto.io.nio.httpserver.HttpStatusCode.BAD_REQUEST;
import static ilarkesto.io.nio.httpserver.HttpStatusCode.INTERNAL_SERVER_ERROR;
import static ilarkesto.io.nio.httpserver.HttpStatusCode.NOT_IMPLEMENTED;
import ilarkesto.io.nio.tcpserver.DataHandler;
import ilarkesto.io.nio.tcpserver.ServerDataEvent;
import ilarkesto.io.nio.tcpserver.TcpConnection;
import java.io.UnsupportedEncodingException;
import static java.lang.System.arraycopy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpDataHandler implements DataHandler {

	private static final Log log = Log.get(HttpDataHandler.class);

	private static final String CRLF = "\r\n";

	private final HttpServer server;

	private ServerDataEvent event;

	private HttpRequest request;
	private boolean startLineDone;
	private boolean headerDone;

	public HttpDataHandler(HttpServer server) {
		super();
		this.server = server;
	}

	@Override
	public void onDataReceived(ServerDataEvent event) {
		this.event = event;

		if (request == null) {
			request = new HttpRequest(event.getConnection());
			request.setResponseHeaderServer(server.getName());
		}

		try {
			processEvent(event);
		} catch (Throwable ex) {
			log.error("Processing request failed:", request.getUri() == null ? event.getConnection() : request, ex);
			request.sendEmptyResponse(INTERNAL_SERVER_ERROR, null);
		}
	}

	private void processEvent(ServerDataEvent event) {
                try {
                        byte[] data = event.getData();

                        if (headerDone) {
                                onBodyData(data);
                                return;
                        }

                        String s = new String(data, UTF_8);

                        int from = 0;
                        int idx = s.indexOf(CRLF);
                        while (idx >= 0) {
                                String line = s.substring(from, idx);
                                onLineReceived(line);
                                if (headerDone) {
                                        int len = data.length - idx;
                                        byte[] remainingData = new byte[len];
                                        arraycopy(data, idx, remainingData, 0, len);
                                        onBodyData(remainingData);
                                        return;
                                }
                                from = idx + 2;
                                idx = s.indexOf(CRLF, from);
                        }
                } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(HttpDataHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
	}

	private void onBodyData(byte[] data) {}

	private void onLineReceived(String line) {
		if (!startLineDone) {
			parseStartLine(line);
			return;
		}

		if (line.length() == 0) {
			headerDone = true;
			onHeaderDone();
			return;
		}

		parseHeaderLine(line);
	}

	private void onHeaderDone() {
		if (!checkHeaders()) {
                        return;
                }
		server.onHttpRequest(request);
	}

	private boolean checkHeaders() {
		if (request.getHeaderContentLenght() != null) {
			request.sendEmptyResponse(NOT_IMPLEMENTED, "Content-Length");
			return false;
		}
		if (request.getHeaderTransferEncoding() != null) {
			request.sendEmptyResponse(NOT_IMPLEMENTED, "Transfer-Encoding");
			return false;
		}
		return true;
	}

	private void parseHeaderLine(String line) {
		int idx = line.indexOf(':');
		if (idx <= 0) {
			request.sendEmptyResponse(BAD_REQUEST, "Invalid header line: " + line);
			return;
		}
		String name = line.substring(0, idx);
		String value = line.substring(idx + 1).trim();
		request.setHeader(name, value);
	}

	private void parseStartLine(String line) {
		if (startLineDone) {
                        throw new IllegalStateException("Start line already parsed");
                }
		startLineDone = true;
		for (HttpMethod m : values()) {
			if (line.startsWith(m.name())) {
				request.setMethod(m);
				break;
			}
		}
		if (request.getMethod() == null) {
			int idx = line.indexOf(' ');
			String methodName = idx > 0 ? line.substring(0, idx) : line;
			request.sendEmptyResponse(BAD_REQUEST, "Unknown method: " + methodName);
			return;
		}

		int pathIdx = request.getMethod().name().length() + 1;
		if (pathIdx >= line.length()) {
			request.sendEmptyResponse(BAD_REQUEST, "Invalid start line: " + line);
			return;
		}

		int versionIdx = line.indexOf(' ', pathIdx);
		if (versionIdx > 0 && versionIdx + 1 >= line.length()) {
			request.sendEmptyResponse(BAD_REQUEST, "Invalid start line: " + line);
			return;
		}

		String uri = versionIdx > 0 ? line.substring(pathIdx, versionIdx) : line.substring(pathIdx);
		String version = versionIdx > 0 ? line.substring(versionIdx + 1) : null;

		request.setUri(uri);
		request.setVersion(version);

		log.debug(request);
	}

	@Override
	public void onConnectionClosed(TcpConnection connection) {}

}
