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
package ilarkesto.integration.httpunit;

//import com.meterware.httpunit.ClientProperties;
//import com.meterware.httpunit.HTMLElement;
//import com.meterware.httpunit.HttpUnitOptions;
//import static com.meterware.httpunit.HttpUnitOptions.setScriptingEnabled;
//import com.meterware.httpunit.WebConversation;
//import com.meterware.httpunit.WebResponse;
//import com.meterware.httpunit.WebTable;
import com.meterware.httpunit.ClientProperties;
import com.meterware.httpunit.HTMLElement;
import static com.meterware.httpunit.HttpUnitOptions.setScriptingEnabled;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import static ilarkesto.base.Sys.getHttpProxyHost;
import static ilarkesto.base.Sys.getHttpProxyPort;
import java.io.IOException;
import org.xml.sax.SAXException;

/**
 *
 * @author erik
 */
public class HttpUnit {

    /**
     *
     * @param id
     * @param response
     * @return
     */
    public static HTMLElement getElementWithId(String id, WebResponse response) {
		try {
			return response.getElementWithID(id);
		} catch (SAXException ex) {
			throw new RuntimeException(ex);
		}
	}

    /**
     *
     * @param response
     * @return
     */
    public static String getTitle(WebResponse response) {
		try {
			return response.getTitle();
		} catch (SAXException ex) {
			throw new RuntimeException(ex);
		}
	}

    /**
     *
     * @param tableId
     * @param response
     * @return
     */
    public static WebTable getTable(String tableId, WebResponse response) {
		try {
			return response.getTableWithID(tableId);
		} catch (SAXException ex) {
			throw new RuntimeException(ex);
		}
	}

    /**
     *
     * @param response
     * @param name
     * @param value
     * @return
     */
    public static HTMLElement getFirstElementWithAttribute(WebResponse response, String name, String value) {
		HTMLElement[] elements;
		try {
			elements = response.getElementsWithAttribute("class", "photo");
		} catch (SAXException ex) {
			throw new RuntimeException(ex);
		}
		return elements == null || elements.length < 1 ? null : elements[0];
	}

    /**
     *
     * @param url
     * @param proxyHost
     * @param proxyPort
     * @return
     */
    public static WebResponse loadPage(String url, String proxyHost, Integer proxyPort) {
		try {
			return createWebConversation(false, proxyHost, proxyPort).getResponse(url);
		} catch (IOException | SAXException ex) {
			throw new RuntimeException("Loading URL failed: " + url, ex);
		}
	}

    /**
     *
     * @param url
     * @return
     */
    public static WebResponse loadPage(String url) {
		return loadPage(url, getHttpProxyHost(), getHttpProxyPort());
	}

    /**
     *
     * @param acceptCookies
     * @return
     */
    public static WebConversation createWebConversation(boolean acceptCookies) {
		return createWebConversation(acceptCookies, getHttpProxyHost(), getHttpProxyPort());
	}

    /**
     *
     * @param acceptCookies
     * @param proxyHost
     * @param proxyPort
     * @return
     */
    public static WebConversation createWebConversation(boolean acceptCookies, String proxyHost, Integer proxyPort) {
		setScriptingEnabled(false);
		WebConversation webConversation = new WebConversation();
		if (proxyHost != null) {
                        webConversation.setProxyServer(proxyHost, proxyPort == null ? 3128 : proxyPort);
                }
		ClientProperties props = webConversation.getClientProperties();
		props.setAcceptGzip(false);
		props.setAcceptCookies(acceptCookies);
		props.setAutoRedirect(false);
		return webConversation;
	}

}
