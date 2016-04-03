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
package scrum.client.collaboration;

import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.Gwt;

import java.util.Collections;
import java.util.List;

import scrum.client.ScrumGwt;
import scrum.client.admin.User;
import scrum.client.common.AScrumGwtEntity;
import scrum.client.common.AScrumWidget;
import scrum.client.workspace.ProjectWorkspaceWidgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class UserStatusWidget extends AScrumWidget {

	private User user;
	private UsersStatus usersStatus;
	private Label nameLabel;
	private SimplePanel selectedEntitiesWrapper;
	private FlowPanel wrapper;
	private UserStatusDetailsWidget details;

	private List<AScrumGwtEntity> selectedEntities = Collections.emptyList();
	private FocusPanel focusPanel;

	public UserStatusWidget(User user) {
		this.user = user;
	}

	@Override
	protected Widget onInitialization() {
		usersStatus = Scope.get().getComponent(UsersStatus.class);

		nameLabel = new Label(user.getName() + getCurrentProject().getUsersRolesAsString(user, " (", ")"));

		selectedEntitiesWrapper = new SimplePanel();
		selectedEntitiesWrapper.setStyleName("UserStatusWidget-selectedEntities");

		focusPanel = new FocusPanel(nameLabel);
		focusPanel.setStyleName("UserStatusWidget-name");
		focusPanel.addClickHandler(new ExpansionClickHandler());

		FlowPanel headerPanel = new FlowPanel();
		headerPanel.setStyleName("UserStatusWidget-header");
		headerPanel.add(focusPanel);
		headerPanel.add(selectedEntitiesWrapper);
		headerPanel.add(Gwt.createFloatClear());

		wrapper = new FlowPanel();
		wrapper.setStyleName("UserStatusWidget");
		wrapper.add(headerPanel);
		return wrapper;
	}

	@Override
	protected void onUpdate() {
		String color;
		if (user != getCurrentUser() && usersStatus.isIdle(user)) {
			color = "darkgray";
		} else {
			color = getCurrentProject().getUserConfig(user).getColor();
		}
		nameLabel.getElement().getStyle().setProperty("color", color);
		Highlighter highlighter = new Highlighter();
		nameLabel.addMouseMoveHandler(highlighter);
		nameLabel.addMouseOutHandler(highlighter);
		if (usersStatus.isOnline(user) || user == getCurrentUser()) {
			focusPanel.addStyleDependentName("online");
		} else {
			focusPanel.removeStyleDependentName("online");
		}
		List<AScrumGwtEntity> selectedEntities = usersStatus.getSelectedEntities(user);
		if (!this.selectedEntities.equals(selectedEntities)) {
			this.selectedEntities = selectedEntities;
			selectedEntitiesWrapper.setWidget(ScrumGwt.createReferencesWidget(selectedEntities));
		}
		Gwt.update(details);
	}

	public void expand() {
		details = new UserStatusDetailsWidget(user);
		wrapper.add(details.update());
	}

	public void collapse() {
		wrapper.remove(details);
		details = null;
	}

	public void toggle() {
		if (details == null) {
			expand();
		} else {
			collapse();
		}
	}

	class ExpansionClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			toggle();
			focusPanel.setFocus(false);
		}
	}

	class Highlighter implements MouseMoveHandler, MouseOutHandler {

		@Override
		public void onMouseMove(MouseMoveEvent event) {
			Scope.get().getComponent(ProjectWorkspaceWidgets.class).highlightUser(user);
		}

		@Override
		public void onMouseOut(MouseOutEvent event) {
			Scope.get().getComponent(ProjectWorkspaceWidgets.class).highlightUser(null);
		}
	}
}
