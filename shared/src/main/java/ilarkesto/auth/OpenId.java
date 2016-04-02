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
package ilarkesto.auth;

import static ilarkesto.core.base.Str.cutFrom;
import static ilarkesto.core.base.Str.cutFromTo;
import static ilarkesto.core.base.Str.cutTo;
import static ilarkesto.core.base.Str.isBlank;
import ilarkesto.core.logging.Log;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.Parameter;
import org.openid4java.message.ParameterList;
import static org.openid4java.message.ax.AxMessage.OPENID_NS_AX;
import org.openid4java.message.ax.FetchRequest;
import static org.openid4java.message.ax.FetchRequest.createFetchRequest;
import org.openid4java.message.ax.FetchResponse;
import static org.openid4java.util.HttpClientFactory.setProxyProperties;
import org.openid4java.util.ProxyProperties;

/**
 * http://code.google.com/p/openid4java/
 */
public class OpenId {

	public static final String MYOPENID = "http://myopenid.com/";
	public static final String GOOGLE = "https://www.google.com/accounts/o8/id";
	public static final String YAHOO = "https://me.yahoo.com/";
	public static final String LAUNCHPAD = "http://login.launchpad.net";
	public static final String VERISIGN = "https://pip.verisignlabs.com/";
	public static final String BLOGSPOT = "https://www.blogspot.com/";
	public static final String AOL = "http://openid.aol.com/";
	public static final String FLICKR = "http://www.flickr.com/";
	public static final String MYVIDOOP = "https://myvidoop.com/";
	public static final String WORDPRESS = "https://wordpress.com/";

	public static final String LIVEJOURNAL_TEMPLATE = "http://${username}.livejournal.com/";
	public static final String CLAIMID_TEMPLATE = "https://claimid.com/$(username)";
	public static final String TECHNORATI_TEMPLATE = "https://technorati.com/people/technorati/$(username)/";

	private static Log log = Log.get(OpenId.class);

	public static String cutUsername(String openId) {
		if (openId == null) {
                        return null;
                }
		String name = openId;
		if (name.startsWith(GOOGLE + "?id=")) {
                        return cutFrom(name, "=");
                }
		if (name.startsWith(YAHOO)) {
                        return cutFrom(name, ".com/");
                }
		if (name.startsWith("https://login.launchpad.net/+id/")) {
                        return cutFrom(name, "+id/");
                }
		if (name.startsWith("https://") && name.endsWith(".pip.verisignlabs.com/")) {
                        return cutFromTo(name, "//", ".pip");
                }
		if (name.startsWith("http://openid.aol.com/")) {
                        return cutFrom(name, ".com/");
                }
		if (name.startsWith("https://") && name.endsWith(".myvidoop.com/")) {
                        return cutFromTo(name, "//", ".myvidoop");
                }
		if (name.contains("/")) {
                        name = cutFrom(name, "/");
                }
		if (name.endsWith(".myopenid.com/")) {
                        name = cutTo(name, ".");
                }
		return name;
	}

	public static boolean isOpenIdCallback(HttpServletRequest request) {
		if (request.getParameter("openid.ns") != null) {
                        return true;
                }
		return request.getParameter("openid.identity") != null;
	}

	public static String createAuthenticationRequestUrl(String openId, String returnUrl, HttpSession session,
			boolean fetchNickname, boolean nicknameRequired, boolean fetchFullname, boolean fullnameRequired,
			boolean fetchEmail, boolean emailRequired) {
		AuthRequest authReq = createAuthenticationRequest(openId, returnUrl, session);

		FetchRequest fetch = createFetchRequest();
		if (fetchNickname) {
                        addAttribute(fetch, "nickname", getNicknameFetchRequestAttribute(openId), nicknameRequired);
                }
		if (fetchFullname) {
                        addAttribute(fetch, "fullname", getFullnameFetchRequestAttribute(openId), fullnameRequired);
                }
		if (fetchEmail) {
                        addAttribute(fetch, "email", getEmailFetchRequestAttribute(openId), emailRequired);
                }
		if (!fetch.getAttributes().isEmpty()) {
                        addExtension(authReq, fetch);
                }

		return authReq.getDestinationUrl(true);
	}

	private static String getEmailFetchRequestAttribute(String openId) {
		if (isAxProvider(openId)) {
                        return "http://axschema.org/contact/email";
                }
		return "http://schema.openid.net/contact/email";
	}

	private static String getNicknameFetchRequestAttribute(String openId) {
		if (isAxProvider(openId)) {
                        return "http://axschema.org/namePerson/friendly";
                }
		return "http://schema.openid.net/namePerson/friendly";
	}

	private static String getFullnameFetchRequestAttribute(String openId) {
		if (isAxProvider(openId)) {
                        return "http://axschema.org/namePerson";
                }
		return "http://schema.openid.net/namePerson";
	}

	public static boolean isAxProvider(String openId) {
		return !openId.contains("myopenid.com");
	}

	public static String createAuthenticationRequestUrl(String openId, String returnUrl, HttpSession session)
			throws RuntimeException {
		AuthRequest authReq = createAuthenticationRequest(openId, returnUrl, session);
		return authReq.getDestinationUrl(true);
	}

	public static void appendFetchRequest(AuthRequest authReq, boolean required, String... attributes) {
		FetchRequest fetch = createFetchRequest();
		for (String attribute : attributes) {
			addAttribute(fetch, attribute, attribute, required);
		}
		addExtension(authReq, fetch);
	}

	public static void addExtension(AuthRequest authReq, FetchRequest fetch) {
		try {
			authReq.addExtension(fetch);
		} catch (MessageException ex) {
			throw new RuntimeException("Adding fetch request to OpenID authentication request failed.", ex);
		}
	}

	public static void addAttribute(FetchRequest fetch, String alias, String attribute, boolean required) {
		try {
			fetch.addAttribute(alias, attribute, required);
		} catch (MessageException ex) {
			throw new RuntimeException("Adding fetch request attribute to OpenID authentication request failed: "
					+ attribute, ex);
		}
	}

	public static AuthRequest createAuthenticationRequest(String openId, String returnUrl, HttpSession session) {
		ConsumerManager manager = getConsumerManager(session);
		List discoveries;
		try {
			discoveries = manager.discover(openId);
		} catch (DiscoveryException ex) {
			throw new RuntimeException("Discovering OpenID failed: " + openId, ex);
		}
		DiscoveryInformation discovered = manager.associate(discoveries);
		if (discovered == null) {
                        throw new RuntimeException("No DiscoveryInformation endpoint associated: " + openId);
                }
		session.setAttribute("openIdDiscovered", discovered);
		AuthRequest authReq;
		try {
			authReq = manager.authenticate(discovered, returnUrl);
		} catch (Exception ex) {
			throw new RuntimeException("OpenID Authentication with the provider failed: "
					+ discovered.getDelegateIdentifier(), ex);
		}
		return authReq;
	}

	public static String getIdentifierIdFromCallback(HttpServletRequest request) {
		Identifier verifiedId = getIdentifierFromCallback(request);
		return verifiedId == null ? null : verifiedId.getIdentifier();
	}

	public static Identifier getIdentifierFromCallback(HttpServletRequest request) {
		VerificationResult verification = getVerificationFromCallback(request);
		Identifier verifiedId = verification.getVerifiedId();
		return verifiedId;
	}

	public static VerificationResult getVerificationFromCallback(HttpServletRequest request) {
		log.info("Reading OpenID response");
		ParameterList openidResp = new ParameterList(request.getParameterMap());
		for (Iterator iterator = openidResp.getParameters().iterator(); iterator.hasNext();) {
			Parameter param = (Parameter) iterator.next();
			log.info("   ", param.getKey(), "->", param.getValue());
		}
		HttpSession session = request.getSession();
		DiscoveryInformation discovered = (DiscoveryInformation) session.getAttribute("openIdDiscovered");

		// extract the receiving URL from the HTTP request
		StringBuffer receivingURL = request.getRequestURL();
		String queryString = request.getQueryString();
		if (queryString != null && queryString.length() > 0) {
                        receivingURL.append("?").append(request.getQueryString());
                }

		// verify the response
		VerificationResult verification;
		try {
			verification = getConsumerManager(session).verify(receivingURL.toString(), openidResp, discovered);
		} catch (Exception ex) {
			throw new RuntimeException("Reading OpenID response data failed.", ex);
		}
		return verification;
	}

	public static String getEmail(VerificationResult verification) {
		String value = getFetchResponseAttribute(verification, "email");
		return isBlank(value) ? null : value;
	}

	public static String getFullname(VerificationResult verification) {
		String value = getFetchResponseAttribute(verification, "fullname");
		if (isBlank(value)) {
                        return null;
                }
		if (value.equals(getOpenId(verification))) {
                        return null;
                }
		return value;
	}

	public static String getNickname(VerificationResult verification) {
		String value = getFetchResponseAttribute(verification, "nickname");
		if (isBlank(value)) {
                        return null;
                }
		if (value.equals(getOpenId(verification))) {
                        return null;
                }
		return value;
	}

	public static String getOpenId(VerificationResult verification) {
		if (verification == null) {
                        return null;
                }
		Identifier identifier = verification.getVerifiedId();
		if (identifier == null) {
                        return null;
                }
		String id = identifier.getIdentifier();
		if (id == null || !id.contains("#")) {
                        return id;
                }
		return cutTo(id, "#");
	}

	public static String getFetchResponseAttribute(VerificationResult verification, String attributeAlias) {
		if (verification == null) {
                        return null;
                }
		AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();
		if (!authSuccess.hasExtension(OPENID_NS_AX)) {
                        return null;
                }
		FetchResponse fetchResp;
		try {
			fetchResp = (FetchResponse) authSuccess.getExtension(OPENID_NS_AX);
		} catch (MessageException ex) {
			throw new RuntimeException("Reading fetch response from OpenID callback failed.", ex);
		}
		List values = fetchResp.getAttributeValues(attributeAlias);
		if (values.isEmpty()) {
                        return null;
                }
		return (String) values.get(0);
	}

	public static String getOpenIdFromCallback(HttpServletRequest request) {
		VerificationResult verification = getVerificationFromCallback(request);
		return getOpenId(verification);
	}

	public static ConsumerManager getConsumerManager(HttpSession session) {
		String sessionAttribute = "openIdConsumerManager";
		ConsumerManager manager = (ConsumerManager) session.getAttribute(sessionAttribute);
		if (manager == null) {
                    manager = new ConsumerManager();
			session.setAttribute(sessionAttribute, manager);
		}
		return manager;
	}

	public static void setHttpProxy(String hostname, int port) {
		ProxyProperties proxyProps = new ProxyProperties();
		proxyProps.setProxyHostName(hostname);
		proxyProps.setProxyPort(port);
		setProxyProperties(proxyProps);
	}

}
