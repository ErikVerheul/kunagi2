/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package scrum.client.sprint;

import ilarkesto.core.base.ChangeIndicator;
import ilarkesto.gwt.client.Gwt;

import java.util.Set;

import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.project.Requirement;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class SprintHistorySprintWidget extends AScrumWidget {

	private final Sprint sprint;
	private BlockListWidget<Requirement> requirementList;
	private ChangeIndicator changeIndicator = new ChangeIndicator();

	public SprintHistorySprintWidget(Sprint sprint) {
		super();
		this.sprint = sprint;
	}

	@Override
	protected Widget onInitialization() {
		SprintReport report = sprint.getSprintReport();
		requirementList = new BlockListWidget<Requirement>(RequirementInHistoryBlock.createFactory(sprint));
		requirementList.setAutoSorter(report != null ? report.getRequirementsOrderComparator() : sprint
				.getRequirementsOrderComparator());

		HTML pdfLink = ScrumGwt.createPdfLink("Download Report as PDF", "sprintReport", sprint);
		return Gwt.createFlowPanel(new SprintWidget(sprint), requirementList, pdfLink);
	}

	@Override
	protected void onUpdate() {
		SprintReport report = sprint.getSprintReport();
		if (sprint.getProject().historyLoaded && report != null) {
			Set<Requirement> allRequirements = report.getAllRequirements();
			if (changeIndicator.update(allRequirements)) {
				requirementList.setObjects(allRequirements);
			}
		}
		super.onUpdate();
	}

	public boolean selectRequirement(Requirement r) {
		return requirementList.showObject(r);
	}

	public boolean selectTask(Task task) {
		update();
		RequirementInHistoryBlock rBlock = (RequirementInHistoryBlock) requirementList.getBlock(task.getRequirement());
		requirementList.extendBlock(rBlock, true);
		return rBlock.selectTask(task);
	}

}
