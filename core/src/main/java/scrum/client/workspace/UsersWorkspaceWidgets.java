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
package scrum.client.workspace;

import generated.client.workspace.GUsersWorkspaceWidgets;
import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.Gwt;
import scrum.client.admin.ProjectSelectorWidget;
import scrum.client.admin.SystemConfigWidget;
import scrum.client.admin.SystemMessageManagerWidget;
import scrum.client.admin.SystemMessageWidget;
import scrum.client.admin.UserConfigWidget;
import scrum.client.admin.UserListWidget;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;

public class UsersWorkspaceWidgets extends GUsersWorkspaceWidgets {

	private FlowPanel sidebar;
	private ScrumNavigatorWidget navigator;
	private ProjectSelectorWidget projectSelector;
	private UserConfigWidget userConfig;
	private UserListWidget userList;
	private SystemMessageManagerWidget messageManager;
	private SystemConfigWidget systemConfig;

	@Override
	public void initialize() {
		projectSelector = new ProjectSelectorWidget();
		userConfig = new UserConfigWidget();
		messageManager = new SystemMessageManagerWidget();
		systemConfig = new SystemConfigWidget();

		navigator = new ScrumNavigatorWidget();
		navigator.addItem("Projects", projectSelector);
		navigator.addItem("Personal Preferences", userConfig);
		if (auth.getUser().isAdmin()) {
			userList = new UserListWidget();
			navigator.addItem("System Configuration", systemConfig);
			navigator.addItem("User Management", userList);
			navigator.addItem("System Message", messageManager);
		}

		sidebar = new FlowPanel();
		sidebar.getElement().getStyle().setMarginTop(10, Unit.PX);
		sidebar.getElement().getStyle().setMarginLeft(10, Unit.PX);
		sidebar.add(new SystemMessageWidget());
		sidebar.add(Gwt.createSpacer(1, 10));
		sidebar.add(navigator);
	}

	public void activate(String page) {
		AWidget widget = projectSelector;
		if (page != null) {
			if (page.equals(Page.getPageName(userConfig))) widget = userConfig;
			if (page.equals(Page.getPageName(messageManager))) widget = messageManager;
			if (page.equals(Page.getPageName(userList))) widget = userList;
			if (page.equals(Page.getPageName(systemConfig))) widget = systemConfig;
		}
		ui.show(sidebar, widget);
	}

	public UserListWidget getUserList() {
		return userList;
	}

	public ProjectSelectorWidget getProjectSelector() {
		return projectSelector;
	}

}
