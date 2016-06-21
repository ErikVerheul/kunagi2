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

import ilarkesto.gwt.client.Gwt;
import scrum.client.admin.SystemMessageWidget;
import scrum.client.collaboration.ChatWidget;
import scrum.client.collaboration.UsersStatusWidget;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProjectSidebarWidget extends AScrumWidget {

	private ScrumNavigatorWidget navigator;
	private ChatWidget chat;
	private UsersStatusWidget usersStatus;

	@Override
	protected Widget onInitialization() {
		FlowPanel sidebar = new FlowPanel();
		sidebar.setStyleName("ProjectSidebarWidget");
		sidebar.add(new SystemMessageWidget());
		sidebar.add(getNavigator());
		sidebar.add(Gwt.createSpacer(1, 10));
		sidebar.add(getChat());
		sidebar.add(Gwt.createSpacer(1, 10));
		sidebar.add(getUsersStatus());
		sidebar.add(Gwt.createSpacer(1, 10));
		return sidebar;
	}

	public ChatWidget getChat() {
		if (chat == null) chat = new ChatWidget();
		return chat;
	}

	public UsersStatusWidget getUsersStatus() {
		if (usersStatus == null) usersStatus = new UsersStatusWidget();
		return usersStatus;
	}

	public ScrumNavigatorWidget getNavigator() {
		if (navigator == null) navigator = new ScrumNavigatorWidget();
		return navigator;
	}

}
