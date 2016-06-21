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
package scrum.client.tasks;

import ilarkesto.gwt.client.AIntegerViewEditWidget;
import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.ToolbarWidget;
import scrum.client.common.AScrumAction;
import scrum.client.sprint.CloseTaskAction;
import scrum.client.sprint.ReopenTaskAction;
import scrum.client.sprint.Task;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TaskRemainingWorkWidget extends AWidget {

	private Task task;
	private HorizontalPanel panel;
	private RemainingWorkWidget remainingWork;
	private ToolbarWidget toolbar;
	private boolean lastClosed = false;

	public TaskRemainingWorkWidget(Task task) {
		this.task = task;
	}

	@Override
	protected Widget onInitialization() {
		lastClosed = task.isClosed();

		remainingWork = new RemainingWorkWidget();
		toolbar = new ToolbarWidget();

		panel = new HorizontalPanel();
		panel.setStyleName("TaskRemainingWorkWidget");
		// panel.setWidth("100%");
		if (task.isClosed()) {
			panel.add(new Label("-"));
		} else {
			panel.add(remainingWork);
		}
		panel.add(toolbar);
		return panel;
	}

	@Override
	protected void onUpdate() {
		toolbar.clear();
		if (task.isClosed()) {
			AScrumAction action = new ReopenTaskAction(task);
			if (action.isExecutable()) {
				toolbar.addButton(action);
			}
		} else {
			AScrumAction action = new CloseTaskAction(task);
			if (action.isExecutable()) {
				toolbar.addButton(action);
			}
		}
		super.onUpdate();
	}

	@Override
	protected boolean isResetRequired() {
		return lastClosed != task.isClosed();
	}

	class RemainingWorkWidget extends AIntegerViewEditWidget {

		@Override
		protected void onIntegerViewerUpdate() {
			setViewerValue(task.getRemainingWork());
		}

		@Override
		protected void onEditorUpdate() {
			setEditorValue(task.getRemainingWork());
		}

		@Override
		protected void onEditorSubmit() {
			task.setRemainingWork(getEditorValue(1));
		}

		@Override
		protected void onMinusClicked() {
			task.decrementRemainingWork();
		}

		@Override
		protected void onPlusClicked() {
			task.incrementRemainingWork();
		}

		@Override
		public boolean isEditable() {
			return task.isEditable();
		}

	}

}
