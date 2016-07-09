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

import static ilarkesto.core.logging.ClientLog.ERROR;
import static ilarkesto.core.logging.ClientLog.INFO;
import ilarkesto.core.scope.CascadingScope;
import ilarkesto.core.scope.NonConcurrentScopeManager;
import ilarkesto.core.scope.Scope;
import ilarkesto.core.scope.ScopeManager;
import ilarkesto.gwt.client.AGwtApplication;
import ilarkesto.gwt.client.ObjectMappedFlowPanel;
import scrum.client.admin.Auth;
import scrum.client.admin.SystemMessageManager;
import scrum.client.admin.User;
import scrum.client.calendar.Calendar;
import scrum.client.collaboration.Chat;
import scrum.client.collaboration.UsersStatus;
import scrum.client.collaboration.Wiki;
import scrum.client.communication.Pinger;
import scrum.client.communication.ServerErrorManager;
import scrum.client.core.RichtextAutosaver;
import scrum.client.core.ServiceCaller;
import scrum.client.files.Uploader;
import scrum.client.i18n.Localizer;
import scrum.client.issues.IssueManager;
import scrum.client.journal.ChangeHistoryManager;
import scrum.client.project.CloseProjectServiceCall;
import scrum.client.project.Project;
import scrum.client.search.Search;
import scrum.client.undo.Undo;
import scrum.client.workspace.DndManager;
import scrum.client.workspace.Navigator;
import scrum.client.workspace.ProjectWorkspaceWidgets;
import scrum.client.workspace.Ui;
import scrum.client.workspace.UsersWorkspaceWidgets;

public class ScrumScopeManager {

	private static NonConcurrentScopeManager scopeManager;
	private static CascadingScope appScope;
	private static CascadingScope userScope;
	private static CascadingScope projectScope;

	static synchronized void initialize() {
		Dao dao = Dao.get();
		ScrumGwtApplication app = (ScrumGwtApplication) AGwtApplication.get();

		scopeManager = NonConcurrentScopeManager.createCascadingScopeInstance("app", new ScrumComponentsReflector());
		appScope = (CascadingScope) scopeManager.getScope();
		Scope scope = appScope;

		scope.putComponent("app", app);
		scope.putComponent(dao);
		scope.putComponent(new ServiceCaller());
		scope.putComponent(new Pinger());
		scope.putComponent(new Ui());
		scope.putComponent(new SystemMessageManager());
		scope.putComponent(new Auth());
		scope.putComponent(new Navigator());
		scope.putComponent(new ServerErrorManager());
		scope.putComponent(new RichtextAutosaver());

		appScope.wireComponents();
	}

	public static void createUserScope(User user) {
		assert user != null;

		userScope = appScope.createScope("user");
		Scope scope = scopeManager.setScope(userScope);

		scope.putComponent(user);
		scope.putComponent(appScope.getComponent(Dao.class).getSystemConfig());
		scope.putComponent(new UsersWorkspaceWidgets());
		scope.putComponent(new Localizer());

		userScope.wireComponents();
	}

	public static void createProjectScope(Project project) {
                if (project == null) ERROR("project == null!");
		projectScope = userScope.createScope("project");
		Scope scope = scopeManager.setScope(projectScope);

                INFO("project");
		scope.putComponent(project);
                 INFO("project.getUserConfig(userScope.getComponent(User.class))");
		scope.putComponent(project.getUserConfig(userScope.getComponent(User.class)));
                 INFO("new ProjectWorkspaceWidgets()");
		scope.putComponent(new ProjectWorkspaceWidgets());
                 INFO("new Chat()");
		scope.putComponent(new Chat());
                 INFO("new ChangeHistoryManager()");
		scope.putComponent(new ChangeHistoryManager());
                 INFO("new Wiki()");
		scope.putComponent(new Wiki());
                 INFO("new Calendar()");
		scope.putComponent(new Calendar());
                 INFO("new Undo()");
		scope.putComponent(new Undo());
                 INFO("new DndManager()");
		scope.putComponent(new DndManager());
                 INFO("new Uploader()");
		scope.putComponent(new Uploader());
                 INFO("new Search()");
		scope.putComponent(new Search());
                 INFO("new UsersStatus()");
		scope.putComponent(new UsersStatus());
                 INFO("new IssueManager()");
		scope.putComponent(new IssueManager());

		projectScope.wireComponents();
	}

	public static void destroyProjectScope() {
		Scope.get().getComponent(Ui.class).lock("Closing project...");
		new CloseProjectServiceCall().execute();
		appScope.getComponent(Dao.class).clearProjectEntities();
		ObjectMappedFlowPanel.objectHeights.clear();
		projectScope = null;
		scopeManager.setScope(userScope);
	}

	public static boolean isProjectScope() {
		return projectScope != null;
	}

	public static Project getProject() {
		return (Project) projectScope.getComponent("project");
	}
}
