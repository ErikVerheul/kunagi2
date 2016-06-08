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
package scrum.server.common;

import ilarkesto.base.PermissionDeniedException;
import ilarkesto.base.StrExtend;
import ilarkesto.core.logging.Log;
import ilarkesto.core.logging.LogRecord;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.io.IO;
import ilarkesto.persistence.AEntity;
import ilarkesto.ui.web.HtmlRenderer;
import ilarkesto.webapp.AServlet;
import ilarkesto.webapp.RequestWrapper;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;

import scrum.client.ApplicationInfo;
import scrum.client.ScrumGwtApplication;
import scrum.server.KunagiRootConfig;
import scrum.server.ScrumWebApplication;
import scrum.server.WebSession;
import scrum.server.admin.SystemConfig;
import scrum.server.admin.User;
import scrum.server.admin.UserDao;
import scrum.server.project.Project;

public abstract class AKunagiServlet extends AServlet<ScrumWebApplication, WebSession> {

	protected static final int LOGIN_TOKEN_COOKIE_MAXAGE = 1209600; // 14 days

	private static final Log log = Log.get(AKunagiServlet.class);

	protected KunagiRootConfig config;
	protected ApplicationInfo applicationInfo;
	protected SystemConfig systemConfig;
	protected UserDao userDao;

	protected abstract void onRequest(RequestWrapper<WebSession> req) throws IOException;

	@Override
	protected void onGet(RequestWrapper<WebSession> req) throws IOException {
		req.preventCaching();
		try {
			onRequest(req);
		} catch (Throwable ex) {
			log.fatal("GET failed:", getClass().getName(), "->", ex);
		}
	}

	@Override
	protected void onPost(RequestWrapper<WebSession> req) throws IOException {
		req.preventCaching();
		try {
			onRequest(req);
		} catch (Throwable ex) {
			log.fatal("POST failed:", getClass().getName(), "->", ex);
		}
	}

	@Override
	protected void onInit(ServletConfig servletConfig) {
		super.onInit(servletConfig);
		config = webApplication.getConfig();
		applicationInfo = webApplication.getApplicationInfo();
		systemConfig = webApplication.getSystemConfig();
		userDao = webApplication.getUserDao();
	}

	// --- helper ---

	protected HtmlRenderer createDefaultHtmlWithHeader(RequestWrapper<WebSession> req, String subtitle)
			throws IOException {
		return createDefaultHtmlWithHeader(req, subtitle, 0, null);
	}

	protected HtmlRenderer createDefaultHtmlWithHeader(RequestWrapper<WebSession> req, String subtitle,
			int refreshSeconds, String refreshUrl) throws IOException {
		String charset = IO.UTF_8;
		req.setContentTypeHtml();
		HtmlRenderer html = new HtmlRenderer(req.getWriter(), charset);
		html.startHTMLstandard();
		String title = "Kunagi";
		if (config.isShowRelease()) title += " " + applicationInfo.getRelease();
		title += " " + subtitle;
		if (systemConfig.isInstanceNameSet()) title += " @ " + systemConfig.getInstanceName();
		html.startHEAD(title, "EN");
		html.META("X-UA-Compatible", "IE=edge");
		if (!StrExtend.isBlank(refreshUrl)) html.METArefresh(refreshSeconds, refreshUrl);
		html.LINKfavicon();
		html.endHEAD();
		return html;
	}

	protected void logsTable(HtmlRenderer html, List<LogRecord> logs) {
		startTABLE(html);
		headersRow(html, "Level", "Logger", "Message", "Context");
		for (LogRecord logLocal : logs) {
			String color = "#666";
			if (logLocal.level.isErrorOrWorse()) color = "#c00";
			if (logLocal.level.isWarn()) color = "#990";
			if (logLocal.level.isInfo()) color = "#000";
			valuesRowColored(html, color, logLocal.level, logLocal.name, logLocal.getParametersAsString(), logLocal.context);
		}
		endTABLE(html);
	}

	protected void startTABLE(HtmlRenderer html) {
		html.startTABLE();
	}

	protected void headersRow(HtmlRenderer html, String... headers) {
		html.startTR();

		for (String header : headers) {
			html.startTH().setStyle(getLabelStyle());
			html.text(header);
			html.endTH();
		}

		html.endTR();
		html.flush();
	}

	protected void valuesRowColored(HtmlRenderer html, String color, Object... values) {
		html.startTR();

		for (Object value : values) {
			html.startTD().setStyle(getValueStyle() + " color: " + color + ";");
			html.text(value);
			html.endTD();
		}

		html.endTR();
		html.flush();
	}

	protected void valuesRow(HtmlRenderer html, Object... values) {
		html.startTR();

		for (Object value : values) {
			html.startTD().setStyle(getValueStyle());
			html.text(value);
			html.endTD();
		}

		html.endTR();
		html.flush();
	}

	protected void keyValueRow(HtmlRenderer html, String key, Object value) {
		html.startTR();

		html.startTD().setStyle(getLabelStyle());
		html.text(key);
		html.endTD();

		html.startTD().setStyle(getValueStyle());
		html.text(value);
		html.endTD();

		html.endTR();
		html.flush();
	}

	protected void endTABLE(HtmlRenderer html) {
		html.endTABLE();
		html.flush();
	}

	protected void sectionHeader(HtmlRenderer html, String title) {
		html.H2(title);
	}

	private String getLabelStyle() {
		return "color: #999; font-weight: normal; padding: 2px 20px 2px 5px; text-align: left;";
	}

	private String getValueStyle() {
		return "font-family: mono; padding: 2px 20px 2px 5px;";
	}

	protected String getDefaultStartPage() {
		return webApplication.isDevelopmentMode() ? "index.html?gwt.codesvr=127.0.0.1:9997" : "";
	}

	protected void adminLinks(HtmlRenderer html, RequestWrapper<WebSession> req) {
		html.startP();
		html.text("[ ");
		html.A("admin.html", "Admin page");
		html.text(" ] [ ");
		html.A("logs.html", "Latest logs");
		html.text(" ] [ ");
		html.A(req.getBaseUrl(), "Kunagi");
		html.text(" ]");
		html.endP();
	}

	protected boolean tokenLogin(RequestWrapper<WebSession> req) throws IOException {
		String loginToken = req.getCookie(ScrumGwtApplication.LOGIN_TOKEN_COOKIE);
		if (!StrExtend.isBlank(loginToken)) {
			User user = userDao.getUserByLoginToken(loginToken);
			if (user != null) {
				user.setLastLoginDateAndTime(DateAndTime.now());
				req.getSession().setUser(user);
				req.setCookie(ScrumGwtApplication.LOGIN_TOKEN_COOKIE, user.getLoginToken(), LOGIN_TOKEN_COOKIE_MAXAGE);
				return true;
			}
		}
		return false;
	}

	protected void redirectToLogin(RequestWrapper<WebSession> req) throws IOException {
		String url = "login.html";
		String token = StrExtend.cutFrom(req.getUri(), "#");
		if (!StrExtend.isBlank(token)) url += "?historyToken=" + StrExtend.encodeUrlParameter(token);
		url = webApplication.createUrl(url);
		log.debug("Redirecting to", url);
		req.sendRedirect(url);
	}

	public static <E extends AEntity> E getEntityByParameter(RequestWrapper<WebSession> req, Class<E> type) {
		return getEntityByParameter(req, "entityId", type);
	}

	public static <E extends AEntity> E getEntityByParameter(RequestWrapper<WebSession> req, String parameterName,
			Class<E> type) {
		String id = req.getMandatory(parameterName);
		return (E) ScrumWebApplication.get().getDaoService().getById(id);
	}

	public static Project getProject(RequestWrapper<WebSession> req) {
		Project project = getEntityByParameter(req, "projectId", Project.class);
		if (!project.isVisibleFor(req.getSession().getUser())) throw new PermissionDeniedException();
		return project;
	}

}
