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

public class RequirementEstimationVotingShowoffAction extends GRequirementEstimationVotingShowoffAction {

	public RequirementEstimationVotingShowoffAction(scrum.client.project.Requirement requirement) {
		super(requirement);
	}

	@Override
	public String getLabel() {
		return "Show votes";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Expose all cards on the table.");
	}

	@Override
	public boolean isExecutable() {
		if (!requirement.isWorkEstimationVotingActive()) return false;
		if (requirement.isWorkEstimationVotingShowoff()) return false;
		if (!requirement.containsWorkEstimationVotes()) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		requirement.activateWorkEstimationVotingShowoff();
	}

}