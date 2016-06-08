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

import static ilarkesto.base.Sys.setHeadless;
import static ilarkesto.core.base.Str.removeSuffix;
import ilarkesto.core.logging.Log;
import ilarkesto.di.app.AApplication;
import ilarkesto.gwt.server.AGwtConversation;
import static ilarkesto.logging.DefaultLogRecordHandler.setLogFile;
import ilarkesto.webapp.jsonapi.JsonApiFactory;
import ilarkesto.webapp.jsonapi.ReflectionJsonApiFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AWebApplication extends AApplication {

	private static final Log LOG = Log.get(AWebApplication.class);

	protected abstract void onStartWebApplication();

	protected abstract void onShutdownWebApplication();

	protected abstract AWebSession createWebSession(HttpServletRequest httpRequest);

	private final Set<AWebSession> webSessions = new HashSet<>();

	private String applicationName;

	private JsonApiFactory restApiFactory;

	@Override
	protected void onStart() {
		if (!isDevelopmentMode()) {
                        setHeadless(true);
                }
		setLogFile(new File(getApplicationDataDir() + "/error.log"));
		LOG.info("Initializing web application");
		onStartWebApplication();
	}

	@Override
	protected void onShutdown() {
		onShutdownWebApplication();
	}

	public final AWebApplication getWebApplication() {
		return this;
	}

	public final void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@Override
	public String getApplicationName() {
		if (applicationName != null) {
                        return applicationName;
                }
		String name = super.getApplicationName();
		name = removeSuffix(name, "Web");
		applicationName = name;
		return applicationName;
	}

	private static final String WEB_SESSION_SESSION_ATTRIBUTE = "_webSession";

	public AWebSession getWebSession(HttpServletRequest httpRequest) {
		if (isShuttingDown()) {
                        return null;
                }
		HttpSession httpSession = httpRequest.getSession();
		AWebSession webSession = (AWebSession) httpSession.getAttribute(WEB_SESSION_SESSION_ATTRIBUTE);
		if (webSession != null && webSession.isSessionInvalidated()) {
                        webSession = null;
                }
		if (webSession == null) {
			webSession = createWebSession(httpRequest);
			httpSession.setAttribute(WEB_SESSION_SESSION_ATTRIBUTE, webSession);
			synchronized (webSessions) {
				webSessions.add(webSession);
			}
		} else {
			webSession.touch();
		}
		return webSession;
	}

	public final void destroyTimeoutedSessions() {
		for (AWebSession session : getWebSessions()) {
			if (session.isTimeouted() || session.isSessionInvalidated()) {
				LOG.info("Destroying invalid/timeouted session:", session);
				destroyWebSession(session, null);
			}
		}
	}

	public final void destroyTimeoutedGwtConversations() {
		for (AGwtConversation conversation : getGwtConversations()) {
			if (conversation.isTimeouted()) {
				AWebSession session = conversation.getSession();
				LOG.info("Destroying invalid/timeouted GwtConversation:", conversation);
				session.destroyGwtConversation(conversation);
			}
		}
	}

	public final void destroyWebSession(AWebSession webSession, HttpSession httpSession) {
		synchronized (webSessions) {
			webSessions.remove(webSession);
			webSession.destroy();
			if (httpSession != null) {
				try {
					httpSession.removeAttribute(WEB_SESSION_SESSION_ATTRIBUTE);
				} catch (Throwable t) {}
				try {
					httpSession.invalidate();
				} catch (Throwable t) {}
			}
		}
	}

	public final Set<AWebSession> getWebSessions() {
		synchronized (webSessions) {
			return new HashSet<>(webSessions);
		}
	}

	public Set<AGwtConversation> getGwtConversations() {
		Set<AGwtConversation> ret = new HashSet<>();
		for (AWebSession session : getWebSessions()) {
			ret.addAll(session.getGwtConversations());
		}
		return ret;
	}

	public JsonApiFactory getRestApiFactory() {
		if (restApiFactory == null) {
                        restApiFactory = createRestApiFactory();
                }
		return restApiFactory;
	}

	protected ReflectionJsonApiFactory createRestApiFactory() {
		return new ReflectionJsonApiFactory(this);
	}

	public static AWebApplication get() {
		return (AWebApplication) AApplication.get();
	}

}
