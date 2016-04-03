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
package scrum.client.sprint;

import ilarkesto.gwt.client.AnchorPanel;
import scrum.client.common.ABlockWidget;
import scrum.client.common.AScrumAction;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.dnd.TrashSupport;
import scrum.client.img.Img;
import scrum.client.journal.ActivateChangeHistoryAction;
import scrum.client.tasks.TaskWidget;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class TaskInHistoryBlock extends ABlockWidget<Task> implements TrashSupport {

	private Sprint sprint;
	private AnchorPanel statusIcon;

	public TaskInHistoryBlock(Sprint sprint) {
		super();
		this.sprint = sprint;
	}

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		Task task = getObject();
		SprintReport report = sprint.getSprintReport();

		boolean closed = report.containsClosedTask(task);
		statusIcon = header.addIconWrapper();
		Image statusImage = null;
		if (closed) {
			statusImage = Img.bundle.tskClosed().createImage();
			statusImage.setTitle("Closed.");
		}
		statusIcon.setWidget(statusImage);

		header.addText(task.getLabelModel());
		header.setDragHandle(task.getReference());
		header.addMenuAction(new ActivateChangeHistoryAction(task));
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {}

	@Override
	protected Widget onExtendedInitialization() {
		return new TaskWidget(getObject());
	}

	@Override
	public AScrumAction getTrashAction() {
		return null;
	}

	public static final BlockWidgetFactory<Task> createFactory(final Sprint sprint) {
		return new BlockWidgetFactory<Task>() {

			@Override
			public TaskInHistoryBlock createBlock() {
				return new TaskInHistoryBlock(sprint);
			}
		};
	}

}
