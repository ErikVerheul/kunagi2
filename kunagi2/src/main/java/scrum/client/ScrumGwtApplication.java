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
package scrum.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import ilarkesto.core.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.AGwtApplication;
import ilarkesto.gwt.client.AGwtDao;
import ilarkesto.gwt.client.ErrorWrapper;
import java.util.List;
import scrum.client.admin.Auth;
import scrum.client.admin.LogoutServiceCall;
import scrum.client.calendar.SimpleEvent;
import scrum.client.collaboration.Subject;
import scrum.client.communication.Pinger;
import scrum.client.communication.StartConversationServiceCall;
import scrum.client.core.ApplicationStartedEvent;
import scrum.client.files.File;
import scrum.client.impediments.Impediment;
import scrum.client.issues.Issue;
import scrum.client.pr.BlogEntry;
import scrum.client.project.Quality;
import scrum.client.project.Requirement;
import scrum.client.release.Release;
import scrum.client.risks.Risk;
import scrum.client.sprint.Sprint;
import scrum.client.sprint.Task;
import scrum.client.workspace.Ui;
import scrum.client.workspace.WorkspaceWidget;

public class ScrumGwtApplication extends GScrumGwtApplication {

	public static final String LOGIN_TOKEN_COOKIE = "kunagiLoginToken";

	static final String[] REFERENCE_PREFIXES = new String[] { Requirement.REFERENCE_PREFIX,
			Task.REFERENCE_PREFIX, Quality.REFERENCE_PREFIX, Issue.REFERENCE_PREFIX, Impediment.REFERENCE_PREFIX,
			Risk.REFERENCE_PREFIX, File.REFERENCE_PREFIX, Subject.REFERENCE_PREFIX, SimpleEvent.REFERENCE_PREFIX,
			Release.REFERENCE_PREFIX, BlogEntry.REFERENCE_PREFIX, Sprint.REFERENCE_PREFIX };

	private final Log log = Log.get(getClass());

	public ApplicationInfo applicationInfo;

	@Override
	public void onModuleLoad() {
		System.out.println("ScrumGwtApplication.onModuleLoad()");

		ScrumScopeManager.initialize();

		// if (true) {
		// RootPanel.get().add(new WidgetsTesterWidget().update());
		// return;
		// }

		final WorkspaceWidget workspace = Scope.get().getComponent(Ui.class).getWorkspace();
		workspace.lock("Loading...");

		RootPanel rootPanel = RootPanel.get();
		rootPanel.getElement().getStyle().setProperty("position", "relative");
		rootPanel.add(workspace);
		ScrumJs.initialize();

		new StartConversationServiceCall().execute(new Runnable() {

			@Override
			public void run() {
				new ApplicationStartedEvent().fireInCurrentScope();
			}
		});

	}

	public ApplicationInfo getApplicationInfo() {
		return applicationInfo;
	}

	public void logout() {
		log.info("Logging out");

		Cookies.removeCookie(LOGIN_TOKEN_COOKIE);

		Scope.get().getComponent(Ui.class).lock("Logging out...");
		Scope.get().getComponent(Auth.class).logout();
		Scope.get().getComponent(Pinger.class).shutdown();
		Scope.get().getComponent(Dao.class).clearAllEntities();

		new LogoutServiceCall().execute(new Runnable() {

			@Override
			public void run() {
				Window.Location.replace(ScrumGwt.getLoginUrl());
			}
		});

	}

	@Override
	public void handleServiceCallError(String serviceCall, List<ErrorWrapper> errors) {
		for (ErrorWrapper error : errors) {
			if ("ilarkesto.webapp.GwtConversationDoesNotExist".equals(error.getName())) {
				Scope.get().getComponent(Ui.class).getWorkspace().abort("Session timed out.");
				return;
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append("<strong>Server service call error</strong><br>");
		sb.append("Calling service <em>").append(serviceCall).append("</em> failed.<br>");
		for (ErrorWrapper error : errors) {
			sb.append(Str.toHtml(error.toString())).append("<br>");
		}
		Scope.get().getComponent(Ui.class).getWorkspace().abort(sb.toString());
	}

	@Override
	protected void handleUnexpectedError(Throwable ex) {
		log.error("Unexpected error", ex);
		StringBuilder sb = new StringBuilder();
		sb.append("<strong>Unexpected Error</strong><br>");
		sb.append(Str.toHtml(Str.formatException(ex)));
		Scope.get().getComponent(Ui.class).getWorkspace().abort(sb.toString());
	}

	@Override
	protected AGwtDao getDao() {
		return Dao.get();
	}

	public static ScrumGwtApplication get() {
		return (ScrumGwtApplication) AGwtApplication.get();
	}

}
