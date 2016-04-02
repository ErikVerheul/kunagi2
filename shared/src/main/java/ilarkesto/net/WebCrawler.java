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
package ilarkesto.net;

import static ilarkesto.base.StrExtend.getCharsetFromHtml;
import static ilarkesto.core.base.Str.indexOf;
import static ilarkesto.core.base.Str.isBlank;
import static ilarkesto.core.base.Utl.toList;
import ilarkesto.core.logging.Log;
import static ilarkesto.io.IO.UTF_8;
import static ilarkesto.io.IO.downloadToFile;
import static ilarkesto.io.IO.openUrlConnection;
import static ilarkesto.io.IO.readToByteArray;
import static ilarkesto.io.IO.writeFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Collections.emptySet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCrawler {

	private static final Log log = Log.get(WebCrawler.class);

	private Filter filter;
	private Consumer consumer;
	private String defaultEncoding = UTF_8;

	private final Set<String> crawledUrls = new HashSet<>();

	public static void download(String url, String destinationDir) {
		WebCrawler wc = new WebCrawler();
		wc.activateDownloading(destinationDir);
		wc.crawl(url);
	}

	public void crawl(String url) {
		if (filter == null) {
                        filter = new HostFilter(url);
                }
		crawl(toList(url));
	}

	private void crawl(Collection<String> urls) {
		Set<String> newUrls = new HashSet<>();
		for (String url : urls) {
			Set<String> parsedUrls = doCrawl(url);
			if (!parsedUrls.isEmpty()) {
                                log.debug("  parsed", parsedUrls.size(), "URLs");
                        }
			newUrls.addAll(parsedUrls);
		}
		List<String> nextUrls = new ArrayList<>();
		for (String url : newUrls) {
			if (crawledUrls.contains(url)) {
                                continue;
                        }
			if (filter != null && !filter.accept(url)) {
				log.debug("  filtered out:", url);
				continue;
			}
			nextUrls.add(url);
		}
		if (nextUrls.isEmpty()) {
                        return;
                }
		crawl(nextUrls);
	}

	private Set<String> doCrawl(String url) {
		log.debug("Crawling:", url);
		crawledUrls.add(url);
		if (!isProbablyHtml(url)) {
			if (consumer == null || consumer.skipNonHtml(url)) {
                                return emptySet();
                        }
		}
		URLConnection connection = openUrlConnection(url, null, null);
		String type;
		try {
			connection.connect();
			if (consumer != null) {
                                consumer.onConnected(url, connection);
                        }
			type = connection.getContentType();
		} catch (Exception ex) {
			if (consumer != null) {
				try {
					consumer.onError(ex, url, connection);
				} catch (Exception ex1) {
					throw new RuntimeException(ex1);
				}
				return emptySet();
			} else {
				throw new RuntimeException(ex);
			}
		}
		if (isBlank(type)) {
                        type = "application/unknown";
                }
		if (type.startsWith("text/html")) {
			String encoding = connection.getContentEncoding();
			if (isBlank(encoding)) {
                                encoding = defaultEncoding;
                        }
			byte[] data;
			try {
				data = readToByteArray(connection.getInputStream());
			} catch (FileNotFoundException ex) {
				log.debug("  not found:", url);
				if (consumer != null) {
                                        consumer.onNotFound(url);
                                }
				return emptySet();
			} catch (IOException ex) {
				throw new RuntimeException("Loading URL failed: " + url, ex);
			}
			String html;
			try {
				html = new String(data, encoding);
			} catch (UnsupportedEncodingException ex) {
				throw new RuntimeException("Loading URL failed: " + url, ex);
			}
			String charset = getCharsetFromHtml(html, encoding);
			if (!encoding.equals(charset)) {
				try {
					html = new String(data, charset);
				} catch (UnsupportedEncodingException ex) {}
			}
			if (consumer != null) {
                                consumer.onHtml(url, html);
                        }
			return parseUrls(html, url);
		}
		if (consumer != null) {
                        consumer.onUnknown(url, connection);
                }
		return emptySet();
	}

	static boolean isProbablyHtml(String url) {
		String s;
		try {
			s = new URL(url).getPath();
		} catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
		s = s.toLowerCase();
		int idx = s.lastIndexOf('/');
		if (idx > 0) {
                        s = s.substring(idx);
                }
		if (!s.contains(".")) {
                        return true;
                }
		if (s.endsWith(".html")) {
                        return true;
                }
		if (s.endsWith(".htm")) {
                        return true;
                }
		if (s.endsWith(".jsp")) {
                        return true;
                }
		return s.endsWith(".php");
	}

	static String normalizeUrl(String url) {
		int idx = url.indexOf('#');
		if (idx >= 0) {
                        url = url.substring(0, idx);
                }

		url = url.replace("/./", "/");

		while (url.contains("/../")) {
			int index = url.indexOf("/../");
			int from = index - 1;
			while (url.charAt(from) != '/') {
				from--;
			}
			url = url.substring(0, from) + url.substring(index + 3);
		}

		return url;
	}

	private Set<String> parseUrls(String html, String sourceUrl) {
		Set<String> urls = new HashSet<>();
		HtmlParser parser = new HtmlParser(html);
		String url;
		while ((url = parser.nextUrl()) != null) {
			url = normalizeUrl(url);
			if (isBlank(url)) {
                                continue;
                        }
			url = concatUrlWithRelative(sourceUrl, url);
			url = normalizeUrl(url);
			urls.add(url);
		}
		return urls;
	}

	static String concatUrlWithRelative(String sourceUrl, String relativeUrl) {
		if (relativeUrl.startsWith("http://") || relativeUrl.startsWith("https://")) {
                        return relativeUrl;
                }
		String baseUrl = getBaseUrl(sourceUrl);
		return baseUrl + relativeUrl;
	}

	static String getBaseUrl(String url) {
		int fromIdx = 7;
		if (url.startsWith("https://")) {
                        fromIdx++;
                }
		int idx = url.lastIndexOf('/');
		if (idx > fromIdx) {
                        url = url.substring(0, idx);
                }
		if (!url.endsWith("/")) {
                        url = url + '/';
                }
		return url;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public void activateDownloading(String destinationDir) {
		setConsumer(new DownloadConsumer(destinationDir, UTF_8));
	}

	public Set<String> getCrawledUrls() {
		return crawledUrls;
	}

	private static class HtmlParser {

		private String html;

		public HtmlParser(String html) {
			super();
			this.html = html;
		}

		public String nextUrl() {
			int idx = indexOf(html, new String[] { "href=", "src=" }, 0);
			if (idx < 0) {
                                return null;
                        }
			html = html.substring(idx);
			if (html.startsWith("href=")) {
				html = html.substring(5);
				return nextAttributeValue();
			}
			if (html.startsWith("src=")) {
				html = html.substring(4);
				return nextAttributeValue();
			}
			throw new IllegalStateException(html);
		}

		private String nextAttributeValue() {
			int idx = indexOf(html, new String[] { "\"", "'" }, 0);
			if (idx < 0) {
                                return nextUrl();
                        }
			char quote = html.charAt(idx);
			html = html.substring(idx + 1);
			idx = html.indexOf(quote);
			if (idx < 0) {
                                return nextUrl();
                        }
			String url = html.substring(0, idx);
			html = html.substring(idx + 1);
			if (url.contains("\" + gaJsHost + \"")) {
                                return nextUrl();
                        }
			return url;
		}
	}

	public static interface Filter {

		boolean accept(String url);

	}

	public static class HostFilter implements Filter {

		private String host;

		public HostFilter(String url) {
			super();
			URL u;
			try {
				u = new URL(url);
			} catch (MalformedURLException ex) {
				throw new RuntimeException(ex);
			}
			this.host = u.getProtocol() + "://" + u.getHost();
			// System.out.println("------------------> host: " + host);
		}

		@Override
		public boolean accept(String url) {
			return url.startsWith(host);
		}

	}

	public static interface Consumer {

		void onConnected(String url, URLConnection connection);

		void onNotFound(String url);

		boolean skipNonHtml(String url);

		void onError(Exception ex, String url, URLConnection connection) throws Exception;

		void onUnknown(String url, URLConnection connection);

		void onHtml(String url, String html);

	}

	public static class DownloadConsumer implements Consumer {

		private final String destinationDir;
		private boolean skipNonHtml;
		private String encoding;

		public DownloadConsumer(String destinationDir, String encoding) {
			super();
			this.encoding = encoding;
			this.destinationDir = destinationDir;
		}

		@Override
		public void onHtml(String url, String html) {
			File file = getFile(url);
			log.info("Storing:", file);
			if (encoding == null) {
                                encoding = getCharsetFromHtml(html, UTF_8);
                        }
			writeFile(file, html, encoding);
		}

		@Override
		public void onUnknown(String url, URLConnection connection) {
			File file = getFile(url);
			log.info("Storing:", file);
			downloadToFile(connection, file);
		}

		private File getFile(String url) {
			URL u;
			try {
				u = new URL(url);
			} catch (MalformedURLException ex) {
				throw new RuntimeException(ex);
			}
			String path = u.getPath();
			if (isBlank(path)) {
                                path = "/";
                        }
			if (path.endsWith("/")) {
                                path = "_.html";
                        }
			return new File(destinationDir + "/" + u.getHost() + "/" + path);
		}

		@Override
		public boolean skipNonHtml(String url) {
			return skipNonHtml;
		}

		@Override
		public void onNotFound(String url) {}

		@Override
		public void onError(Exception ex, String url, URLConnection connection) throws Exception {
			throw ex;
		}

		@Override
		public void onConnected(String url, URLConnection connection) {}

	}

}
