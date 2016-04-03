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

import generated.client.sprint.GCreateTaskAction;
import ilarkesto.core.scope.Scope;
import scrum.client.common.TooltipBuilder;
import scrum.client.project.Requirement;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class CreateTaskAction extends GCreateTaskAction {

	private Requirement requirement;

	public CreateTaskAction(Requirement requirement) {
		this.requirement = requirement;
	}

	@Override
	public String getLabel() {
		return "Create Task";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create a new Task for this Story.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_TEAM);
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		Task task = requirement.createNewTask();
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(task);
	}

}
