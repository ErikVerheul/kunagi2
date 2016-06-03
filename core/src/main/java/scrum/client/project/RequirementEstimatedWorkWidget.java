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

import ilarkesto.gwt.client.ADropdownViewEditWidget;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.ToolbarWidget;
import scrum.client.common.AScrumAction;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RequirementEstimatedWorkWidget extends AScrumWidget {

	private Requirement requirement;
	private EstimatedWorkWidget estimatedWork;
	private ToolbarWidget toolbar;

	public RequirementEstimatedWorkWidget(Requirement requirement) {
		this.requirement = requirement;
	}

	@Override
	protected Widget onInitialization() {
		estimatedWork = new EstimatedWorkWidget();
		toolbar = new ToolbarWidget();

		HorizontalPanel panel = new HorizontalPanel();
		panel.setStyleName("RequirementEstimatedWorkWidget");
		panel.add(estimatedWork);
		panel.add(toolbar);
		return panel;
	}

	@Override
	protected void onUpdate() {
		estimatedWork.update();
		toolbar.clear();

		AScrumAction dirtAction = requirement.isDirty() ? new SetRequirementCleanAction(requirement)
				: new SetRequirementDirtyAction(requirement);
		if (dirtAction.isExecutable()) toolbar.add(new ButtonWidget(dirtAction));

		StartRequirementEstimationVotingAction pokerAction = new StartRequirementEstimationVotingAction(requirement);
		if (pokerAction.isExecutable()) toolbar.add(new ButtonWidget(pokerAction));

		toolbar.update();
	}

	class EstimatedWorkWidget extends ADropdownViewEditWidget {

		@Override
		protected void onViewerUpdate() {
			setViewerText(requirement.getEstimatedWorkWithUnit());
		}

		@Override
		public boolean isEditable() {
			if (requirement.isInCurrentSprint()) return false;
			if (requirement.isClosed()) return false;
			if (!getCurrentProject().isTeamMember(getCurrentUser())) return false;
			return true;
		}

		@Override
		protected void onEditorUpdate() {
			setOptions(Requirement.getWorkEstimationValues());
			String work = requirement.getEstimatedWorkAsString();
			setSelectedOption(work == null ? "" : work.toString());
			super.onEditorUpdate();
		}

		@Override
		protected void onEditorSubmit() {
			String value = getSelectedOption();
			requirement.setEstimatedWork(value.length() == 0 ? null : Float.parseFloat(value));
		}
	}

}
