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

import generated.client.sprint.GPullNextRequirementAction;
import scrum.client.project.AddRequirementToCurrentSprintAction;
import scrum.client.project.Requirement;

public class PullNextRequirementAction extends GPullNextRequirementAction {

	public PullNextRequirementAction(scrum.client.sprint.Sprint sprint) {
		super(sprint);
	}

	@Override
	protected void updateTooltip(scrum.client.common.TooltipBuilder tb) {
		Requirement req = getCurrentProject().getNextProductBacklogRequirement();
		if (req != null) tb.setText("Pull " + req.getReferenceAndLabel() + " to current Sprint.");
	}

	@Override
	public String getLabel() {
		return "Pull next Story";
	}

	@Override
	public boolean isExecutable() {
		AddRequirementToCurrentSprintAction addAction = getAddAction();
		if (addAction == null) return false;
		return addAction.isExecutable();
	}

	@Override
	public boolean isPermitted() {
		AddRequirementToCurrentSprintAction addAction = getAddAction();
		if (addAction == null) return false;
		return addAction.isPermitted();
	}

	@Override
	protected void onExecute() {
		AddRequirementToCurrentSprintAction addAction = getAddAction();
		if (addAction != null) addAction.onExecute();
	}

	private AddRequirementToCurrentSprintAction getAddAction() {
		Requirement req = getCurrentProject().getNextProductBacklogRequirement();
		if (req == null) return null;
		return new AddRequirementToCurrentSprintAction(req);
	}

}