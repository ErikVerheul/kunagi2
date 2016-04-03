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
package scrum.client.project;

import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;
import scrum.client.common.AScrumWidget;
import scrum.client.project.ProductBacklogWidget.FilterToggleAction;
import scrum.client.workspace.DndManager;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CreateStoryButtonWidget extends AScrumWidget {

	private FocusPanel dragHandle;
	private CreateRequirementAction action;
	private FilterToggleAction filterToggleAction;

	public CreateStoryButtonWidget(FilterToggleAction filterToggleAction) {
		this.filterToggleAction = filterToggleAction;
	}

	@Override
	protected Widget onInitialization() {
		setStyleName("CreateStoryButtonWidget");

		action = new CreateRequirementAction(filterToggleAction);

		dragHandle = new FocusPanel(new Label("new sto"));
		Scope.get().getComponent(DndManager.class).makeDraggable(this, dragHandle);
		dragHandle.setStyleName("CreateStoryButtonWidget-dragHandle");

		return Gwt.createHorizontalPanel(0, new ButtonWidget(action), dragHandle);
	}

	@Override
	protected void onUpdate() {
		dragHandle.getElement().getStyle().setDisplay(filterToggleAction.filterActive ? Display.NONE : Display.BLOCK);
		super.onUpdate();
	}

	public CreateRequirementAction getAction() {
		return action;
	}

}
