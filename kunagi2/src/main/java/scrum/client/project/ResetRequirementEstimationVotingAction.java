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
import scrum.client.estimation.ActivateRequirementEstimationVotingServiceCall;

public class ResetRequirementEstimationVotingAction extends GResetRequirementEstimationVotingAction {

	public ResetRequirementEstimationVotingAction(scrum.client.project.Requirement requirement) {
		super(requirement);
	}

	@Override
	public String getLabel() {
		return "Reset";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Remove all cards from the table to start a new round.");
	}

	@Override
	public boolean isExecutable() {
		if (!requirement.isWorkEstimationVotingActive()) return false;
		// if (!requirement.containsWorkEstimationVotes()) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		new ActivateRequirementEstimationVotingServiceCall(requirement.getId()).execute();
		requirement.updateLocalModificationTime();
	}

}