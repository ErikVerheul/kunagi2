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

import scrum.client.common.TooltipBuilder;

public class SetRequirementCleanAction extends GSetRequirementCleanAction {

	protected SetRequirementCleanAction(Requirement requirement) {
		super(requirement);
	}

	@Override
	public String getLabel() {
		return "Confirm Estimation";
	}

	@Override
	public void updateTooltip(TooltipBuilder tb) {
		tb.setText("Confirm that the estimation of the Story is still accurate.");
		if (!requirement.getProject().isTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_TEAM);
		} else {
			if (requirement.isClosed()) tb.addRemark("Story is already closed.");
			if (!requirement.isDirty()) tb.addRemark("The estimation has already been confirmed.");
			if (requirement.getEstimatedWork() == null) tb.addRemark("Story is not yet estimated.");
			if (requirement.isInCurrentSprint()) tb.addRemark("Story is in current Sprint and cannot be changed.");
		}
	}

	@Override
	public boolean isPermitted() {
		if (!requirement.getProject().isTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (requirement.isClosed()) return false;
		if (!requirement.isDirty()) return false;
		if (requirement.getEstimatedWork() == null) return false;
		if (requirement.isInCurrentSprint()) return false;
		if (requirement.isWorkEstimationVotingActive()) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		requirement.setDirty(false);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Mark as clean for " + requirement.getReferenceAndLabel();
		}

		@Override
		protected void onUndo() {
			requirement.setDirty(true);
		}

	}

}
