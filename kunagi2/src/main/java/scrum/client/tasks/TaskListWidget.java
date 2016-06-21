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

import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.animation.AnimatingFlowPanel.InsertCallback;

import java.util.List;

import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.common.ElementPredicate;
import scrum.client.dnd.BlockListDropAction;
import scrum.client.project.Requirement;
import scrum.client.sprint.CreateTaskAction;
import scrum.client.sprint.Task;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class TaskListWidget extends AScrumWidget {

	private BlockListWidget<Task> list;
	private final BlockListDropAction<Task> dropAction;

	private final TaskBlockContainer container;
	private final Requirement requirement;
	private final boolean createTaskButton;

	public TaskListWidget(Requirement requirement, TaskBlockContainer container, BlockListDropAction<Task> dropAction,
			boolean createTaskButton) {
		this.requirement = requirement;
		this.container = container;
		this.dropAction = dropAction;
		this.createTaskButton = createTaskButton;
	}

	public Task getSelectedTask() {
		return list == null ? null : list.getExtendedObject();
	}

	@Override
	protected Widget onInitialization() {
		list = new BlockListWidget<Task>(new TaskBlock.TaskBlockFactory(container), this.dropAction);
		list.setSelectionManager(container.getSelectionManager());
		list.setMinHeight(100);
		list.setAutoSorter(requirement.getTasksOrderComparator());
		if (requirement.getProject().isTeamMember(getCurrentUser())) {
			list.setDndSorting(createTaskButton);
			list.setMoveObserver(new MoveObserver());
		}

		FlowPanel panel = new FlowPanel();
		panel.add(list);
		if (createTaskButton)
			panel.add(Gwt.createDiv("CreateTaskButtonWrapper",
				new ButtonWidget(new CreateTaskAction(requirement)).update()));

		return panel;
	}

	public boolean selectTask(Task task) {
		if (!list.contains(task)) update();
		return list.showObject(task);
	}

	public void setTasks(List<Task> tasks) {
		initialize();
		list.setObjects(tasks);
	}

	public void setTaskHighlighting(ElementPredicate<Task> predicate) {
		list.setTaskHighlighting(predicate);
	}

	public void clearTaskHighlighting() {
		list.clearTaskHighlighting();
	}

	class MoveObserver implements InsertCallback {

		@Override
		public void onInserted(int index) {
			List<Task> tasks = list.getObjects();
			requirement.updateTasksOrder(tasks);
			update();
		}

	}
}
