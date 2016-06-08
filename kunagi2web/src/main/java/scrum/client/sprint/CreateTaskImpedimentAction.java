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

import generated.scrum.client.sprint.GCreateTaskImpedimentAction;
import ilarkesto.core.scope.Scope;
import scrum.client.common.TooltipBuilder;
import scrum.client.impediments.Impediment;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class CreateTaskImpedimentAction extends GCreateTaskImpedimentAction {

	public CreateTaskImpedimentAction(scrum.client.sprint.Task task) {
		super(task);
	}

	@Override
	public String getLabel() {
		return "Create Impediment";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create new Impediment, which is blocking this Task.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_TEAM);
	}

	@Override
	public boolean isExecutable() {
		if (task.isImpedimentSet()) return false;
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		Impediment impediment = getCurrentProject().createNewImpediment();
		task.setImpediment(impediment);
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(impediment);
	}
}