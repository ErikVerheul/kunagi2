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
package ilarkesto.testng.net;

import ilarkesto.net.WebCrawler;
import ilarkesto.testng.ATest;
import java.io.File;
import org.testng.annotations.Test;

/**
 *
 * @author erik
 */
public class WebCrawlerTest extends ATest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		WebCrawler wc = new WebCrawler();
		// wc.setDefaultEncoding("iso-8859-2");
		wc.setFilter(new WebCrawler.Filter() {

			@Override
			public boolean accept(String url) {
                                // if (url.contains("animalprogress.strefa.pl")) return true;
                                
				return url.contains("animalprogress.strefa.pl");
			}
		});
		wc.activateDownloading("/home/witek/inbox/crawler");
		wc.crawl("http://animalprogress.strefa.pl/index.html");

	}

    /**
     *
     */
    @Test
	public void normalizeUrl() {
		assertEquals(WebCrawler.normalizeUrl("http://koczewski.de/#a"), "http://koczewski.de/");
		assertEquals(WebCrawler.normalizeUrl("http://koczewski.de/./index.html"), "http://koczewski.de/index.html");
		assertEquals(WebCrawler.normalizeUrl("http://koczewski.de/test/../index.html"),
			"http://koczewski.de/index.html");
	}

    /**
     *
     */
    @Test
	public void getBaseUrl() {
		assertEquals("http://koczewski.de/", WebCrawler.getBaseUrl("http://koczewski.de"));
		assertEquals("http://koczewski.de/", WebCrawler.getBaseUrl("http://koczewski.de/"));
		assertEquals("http://koczewski.de/", WebCrawler.getBaseUrl("http://koczewski.de/index.html"));
		assertEquals("http://koczewski.de/", WebCrawler.getBaseUrl("http://koczewski.de/start"));
	}

    /**
     *
     */
    @Test
	public void isProbablyHtml() {
		assertTrue(WebCrawler.isProbablyHtml("http://koczewski.de"));
		assertTrue(WebCrawler.isProbablyHtml("http://koczewski.de/"));
		assertTrue(WebCrawler.isProbablyHtml("http://koczewski.de/index.html"));
		assertTrue(WebCrawler.isProbablyHtml("http://koczewski.de/index.php"));
		assertTrue(WebCrawler.isProbablyHtml("http://koczewski.de/index.jsp"));
		assertFalse(WebCrawler.isProbablyHtml("http://koczewski.de/image.png"));
	}

    /**
     *
     */
    @Test
	public void crawl() {
		WebCrawler wc = new WebCrawler();
		wc.crawl(new File("etc/WebCrawler.html").toURI().toString());
	}

    /**
     *
     */
    @Test
	public void download() {
		WebCrawler.download("http://servisto.de", OUTPUT_DIR + "/webcrawler");
	}

}
