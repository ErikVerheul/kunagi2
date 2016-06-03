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
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.animation.AnimatingFlowPanel.InsertCallback;

import java.util.List;

import scrum.client.ScrumGwt;
import scrum.client.collaboration.EmoticonsWidget;
import scrum.client.common.ABlockWidget;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.img.Img;
import scrum.client.journal.ActivateChangeHistoryAction;
import scrum.client.journal.ChangeHistoryWidget;
import scrum.client.project.CloseRequirementAction;
import scrum.client.project.FixRequirementAction;
import scrum.client.project.RejectRequirementAction;
import scrum.client.project.RemoveRequirementFromSprintAction;
import scrum.client.project.ReopenRequirementAction;
import scrum.client.project.Requirement;
import scrum.client.project.RequirementWidget;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class RequirementInSprintBlock extends ABlockWidget<Requirement> {

	private BlockListWidget<Task> taskList;
	private RequirementWidget requirementWidget;
	private FlowPanel right;
	private FlexTable bodyWidget;
	private ChangeHistoryWidget changeHistoryWidget;

	private AnchorPanel statusIcon;

	private boolean decidableOnInitialization;

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		Requirement requirement = getObject();

		decidableOnInitialization = requirement.isDecidable();

		statusIcon = header.addIconWrapper();
		header.addText(requirement.getLabelModel());
		header.addText(requirement.getThemesAsStringModel(), true, false);
		header.addText(requirement.getTaskStatusLabelModel(), true);
		header.appendOuterCell(new EmoticonsWidget(requirement), null, true);
		header.addMenuAction(new RejectRequirementAction(requirement));
		header.addMenuAction(new FixRequirementAction(requirement));
		header.addMenuAction(new CloseRequirementAction(requirement));
		header.addMenuAction(new ReopenRequirementAction(requirement));
		header.addMenuAction(new RemoveRequirementFromSprintAction(requirement));
		header.addMenuAction(new ActivateChangeHistoryAction(requirement));
		header.addMenuAction(new CreateTaskAction(requirement));
	}

	@Override
	protected boolean isResetRequired() {
		return decidableOnInitialization != getObject().isDecidable();
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		Requirement requirement = getObject();
		header.setDragHandle(requirement.getReference());
		Image statusImage = null;
		if (requirement.isRejected()) {
			statusImage = Img.bundle.reqRejected().createImage();
			statusImage.setTitle("Rejected.");
		} else if (requirement.isClosed()) {
			statusImage = Img.bundle.reqClosed().createImage();
			statusImage.setTitle("Accepted.");
		} else if (requirement.isTasksClosed()) {
			statusImage = Img.bundle.reqTasksClosed().createImage();
			statusImage.setTitle("All tasks done.");
		} else if (requirement.isBlocked()) {
			statusImage = Img.bundle.tskBlocked().createImage();
			statusImage.setTitle("Blocked by " + requirement.getImpediment().getReferenceAndLabel() + ".");
		}
		statusIcon.setWidget(statusImage);
	}

	@Override
	protected Widget onExtendedInitialization() {
		Requirement requirement = getObject();

		requirementWidget = new RequirementWidget(requirement, false, false, true, false, false, false, false);
		taskList = new BlockListWidget<Task>(TaskInRequirementBlock.FACTORY);
		taskList.setAutoSorter(requirement.getTasksOrderComparator());
		if (requirement.getProject().isTeamMember(getCurrentUser())) {
			taskList.setDndSorting(true);
			taskList.setMoveObserver(new MoveObserver());
		}
		createTaskButton = new ButtonWidget(new CreateTaskAction(requirement));
		changeHistoryWidget = new ChangeHistoryWidget(requirement);

		FlowPanel left = new FlowPanel();
		left.add(requirementWidget);
		left.add(taskList);
		left.add(Gwt.createDiv("CreateTaskButtonWrapper", createTaskButton));
		left.add(changeHistoryWidget);

		right = new FlowPanel();
		if (requirement.isDecidable() && requirement.getProject().isProductOwner(getCurrentUser())) {
			right.add(RequirementWidget.createActionsPanelForCompletedRequirement(requirement));
			right.add(Gwt.createSpacer(1, 10));
		}
		right.add(ScrumGwt.createEmoticonsAndComments(requirement));

		bodyWidget = TableBuilder.row(20, left, right);
		return bodyWidget;
	}

	@Override
	protected void onUpdateBody() {
		requirementWidget.update();
		taskList.setObjects(getObject().getTasksInSprint());
		taskList.update();
		Gwt.update(right, createTaskButton);
		changeHistoryWidget.update();
	}

	public boolean selectTask(Task task) {
		return taskList.showObject(task);
	}

	class MoveObserver implements InsertCallback {

		@Override
		public void onInserted(int index) {
			List<Task> tasks = taskList.getObjects();
			getObject().updateTasksOrder(tasks);
			update();
		}

	}

	public static final BlockWidgetFactory<Requirement> FACTORY = new BlockWidgetFactory<Requirement>() {

		@Override
		public RequirementInSprintBlock createBlock() {
			return new RequirementInSprintBlock();
		}
	};
	private ButtonWidget createTaskButton;
}
