/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
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
package ilarkesto.webapp;

import static ilarkesto.base.Net.getHostnameOrIp;
import static ilarkesto.base.TmExtend.toUtc;
import static ilarkesto.core.base.Str.encodeUrlParameter;
import static ilarkesto.core.base.Str.format;
import ilarkesto.core.logging.Log;
import ilarkesto.core.time.DateAndTime;
import static ilarkesto.io.IO.UTF_8;
import static ilarkesto.io.IO.copyFile;
import static ilarkesto.io.IO.getHostName;
import static ilarkesto.io.IO.readToString;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import static java.lang.Long.toHexString;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import static javax.servlet.http.HttpServletRequest.CLIENT_CERT_AUTH;
import static javax.servlet.http.HttpServletRequest.DIGEST_AUTH;
import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_NOT_MODIFIED;

public abstract class Servlet {

	private static final Log log = Log.get(Servlet.class);

	public static final String ENCODING = UTF_8;

	public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy, HH:mm:";

	private Servlet() {}

	public static String readContentToString(HttpServletRequest request) {
		BufferedReader in;
		try {
			in = request.getReader();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		if (in == null) {
                        return null;
                }
		return readToString(in);
	}

	public static String getBaseUrl(HttpServletRequest request) {
		String context = request.getContextPath();
		String url = request.getRequestURL().toString();
		int offset = url.indexOf("//") + 2;
		int idx = context.length() == 0 ? url.indexOf('/') : url.indexOf(context, offset);
		String baseUrl = url.substring(0, idx + context.length());
		if (!baseUrl.endsWith("/")) {
                        baseUrl += "/";
                }
		return baseUrl;
	}

	public static String getEtag(HttpServletRequest request) {
		return request.getHeader("If-None-Match");
	}

	public static String getWebappUrl(ServletConfig servletConfig, int port, boolean ssl) {
		String protocol = ssl ? "https" : "http";
		String host = getHostName();
		if (port != 80) {
                        host += ":" + port;
                }
		String context = servletConfig.getServletContext().getServletContextName();
		return protocol + "://" + host + "/" + context;
	}

	public static void writeCachingHeaders(HttpServletResponse httpResponse, DateAndTime lastModified) {
		writeCachingHeaders(httpResponse, createEtag(lastModified), lastModified);
	}

	public static String createEtag(File file) {
		return toHexString(file.lastModified());
	}

	public static String createEtag(DateAndTime lastModified) {
		return toHexString(lastModified.toMillis());
	}

	public static void writeCachingHeaders(HttpServletResponse httpResponse, String eTag, DateAndTime lastModified) {
		setLastModified(httpResponse, lastModified);
		setEtag(httpResponse, eTag);
	}

	public static void setLastModified(HttpServletResponse httpResponse, DateAndTime lastModified) {
		httpResponse.setHeader("Last-Modified",
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(toUtc(lastModified.toJavaDate())) + " GMT");
	}

	public static void setEtag(HttpServletResponse httpResponse, String eTag) {
		httpResponse.setHeader("ETag", eTag);
	}

	public static void writeCachingHeaders(HttpServletResponse httpResponse, File file) {
		writeCachingHeaders(httpResponse, new DateAndTime(file.lastModified()));
	}

	public static void preventCaching(HttpServletResponse httpResponse) {
		// prevent caching HTTP 1.1
		httpResponse.setHeader("Cache-Control", "no-cache");

		// prevent caching HTTP 1.0
		httpResponse.setHeader("Pragma", "no-cache");

		// prevent caching at the proxy server
		httpResponse.setDateHeader("Expires", 0);
	}

	public static void serveFile(File file, HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			boolean setFilename, boolean enableCaching) {
		if (!file.exists()) {
			try {
				httpResponse.sendError(SC_NOT_FOUND);
			} catch (IOException ex) {
				throw new RuntimeException("Serving file failed: " + file, ex);
			}
			return;
		}

		if (enableCaching) {
			String eTag = createEtag(file);
			String requestEtag = getEtag(httpRequest);
			if (eTag.equals(requestEtag)) {
				log.debug("ETag valid. Returning: 304 Not Modified");
				try {
					httpResponse.sendError(SC_NOT_MODIFIED);
				} catch (IOException ex) {
					throw new RuntimeException("Serving file failed: " + file, ex);
				}
				return;
			}
			setEtag(httpResponse, eTag);
		}

		httpResponse.setContentType("application/octet-stream");
		httpResponse.setContentLength((int) file.length());
		if (setFilename) {
                        setFilename(file.getName(), httpResponse);
                }
		try {
			copyFile(file, httpResponse.getOutputStream());
		} catch (IOException ex) {
			throw new RuntimeException("Serving file failed: " + file, ex);
		}
	}

	public static void setFilename(String fileName, HttpServletResponse httpResponse) {
		httpResponse.setHeader("Content-Disposition", "inline; filename=" + encodeUrlParameter(fileName) + ";");
	}

	public static String getContextPath(ServletConfig servletConfig) {
		return getContextPath(servletConfig.getServletContext());
	}

	public static String getContextPath(ServletContext servletContext) {
		String path = servletContext.getContextPath();
		if (path == null) return null;
		path = path.trim();
		if (path.startsWith("/")) path = path.substring(1);
		if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
		path = path.trim();
		if (path.length() == 0) return null;
		if (path.equals("ROOT")) return null;
		if (ilarkesto.base.Sys.isDevelopmentMode() && path.equals("war")) return null;
		return path;
	}

	public static String getUriWithoutContextWithParameters(HttpServletRequest httpRequest) {
		StringBuilder sb = new StringBuilder();
		sb.append(getUriWithoutContext(httpRequest));
		sb.append("?");
		Enumeration e = httpRequest.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			sb.append(parameter);
			sb.append('=');
			sb.append(httpRequest.getParameter(parameter));
			sb.append("&");
		}
		return sb.toString();
	}

	public static String getUriWithoutContext(HttpServletRequest httpRequest) {
		String uri = httpRequest.getRequestURI();
		String context = httpRequest.getContextPath();
		if (uri.length() <= context.length() + 1) {
                        return "";
                }
		return uri.substring(context.length() + 1);
	}

	public static String getRemoteHost(HttpServletRequest r) {
		return getHostnameOrIp(r.getRemoteAddr());
	}

	public static String getUserAgent(HttpServletRequest r) {
		return r.getHeader("User-Agent");
	}

	public static String toString(HttpServletRequest r, String indent) {
		StringBuilder sb = new StringBuilder();
		sb.append(indent).append("requestedURL:       ").append(r.getRequestURL()).append("\n");
		sb.append(indent).append("requestedURI:       ").append(r.getRequestURI()).append("\n");
		sb.append(indent).append("queryString:        ").append(r.getQueryString()).append("\n");
		sb.append(indent).append("contextPath:        ").append(r.getContextPath()).append("\n");
		sb.append(indent).append("pathInfo:           ").append(r.getPathInfo()).append("\n");
		sb.append(indent).append("pathTranslated:     ").append(r.getPathTranslated()).append("\n");
		sb.append(indent).append("parameters:         ").append(format(r.getParameterMap())).append("\n");
		sb.append(indent).append("headers:            ").append(format(getHeaders(r))).append("\n");
		sb.append(indent).append("attributes:         ").append(format(getAttributes(r))).append("\n");
		sb.append(indent).append("cookies:            ").append(format(r.getCookies())).append("\n");
		sb.append(indent).append("protocol:           ").append(r.getProtocol()).append("\n");
		sb.append(indent).append("method:             ").append(r.getMethod()).append("\n");
		sb.append(indent).append("scheme:             ").append(r.getScheme()).append("\n");
		sb.append(indent).append("contentType:        ").append(r.getContentType()).append("\n");
		sb.append(indent).append("contentLenght:      ").append(r.getContentLength()).append("\n");
		sb.append(indent).append("characterEncoding:  ").append(r.getCharacterEncoding()).append("\n");
		sb.append(indent).append("authType:           ").append(r.getAuthType()).append("\n");
		sb.append(indent).append("CLIENT_CERT_AUTH:   ").append(r.getHeader(CLIENT_CERT_AUTH))
				.append("\n");
		sb.append(indent).append("DIGEST_AUTH:        ").append(r.getHeader(DIGEST_AUTH))
				.append("\n");
		sb.append(indent).append("remoteUser:         ").append(r.getRemoteUser()).append("\n");
		sb.append(indent).append("remoteAddr:         ").append(r.getRemoteAddr()).append("\n");
		sb.append(indent).append("remoteHost:         ").append(r.getRemoteHost()).append("\n");
		sb.append(indent).append("remotePort:         ").append(r.getRemotePort()).append("\n");
		sb.append(indent).append("requestedSessionId: ").append(r.getRequestedSessionId()).append("\n");
		sb.append(indent).append("secure:             ").append(r.isSecure()).append("\n");
		sb.append(indent).append("locale:             ").append(r.getLocale()).append("\n");
		sb.append(indent).append("locales:            ").append(format(r.getLocales())).append("\n");
		sb.append(indent).append("localName:          ").append(r.getLocalName()).append("\n");
		sb.append(indent).append("localPort:          ").append(r.getLocalPort()).append("\n");
		sb.append(indent).append("localAddr:          ").append(r.getLocalAddr()).append("\n");
		sb.append(indent).append("serverName:         ").append(r.getServerName()).append("\n");
		sb.append(indent).append("serverPort:         ").append(r.getServerPort()).append("\n");
		sb.append(indent).append("servletPath:        ").append(r.getServletPath()).append("\n");
		return sb.toString();
	}

	public static Map<String, String> getHeaders(HttpServletRequest r) {
		Map<String, String> result = new HashMap<>();
		Enumeration names = r.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = r.getHeader(name);
			result.put(name, value);
		}
		return result;
	}

	public static Map<String, Object> getAttributes(HttpServletRequest r) {
		Map<String, Object> result = new HashMap<>();
		Enumeration names = r.getAttributeNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			Object value = r.getAttribute(name);
			result.put(name, value);
		}
		return result;
	}

	public static void removeCookie(HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(1);
		response.addCookie(cookie);
	}

	public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue,
			int maxAgeInSeconds) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxAgeInSeconds);
		response.addCookie(cookie);
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie cookie = getCookie(request, cookieName);
		if (cookie == null) {
                        return null;
                }
		return cookie.getValue();
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
                        return null;
                }
                for (Cookie cookie : cookies) {
                        if (cookie.getName().equals(name)) {
                                return cookie;
                        }
                }
		return null;
	}

}