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
package scrum.client.release;

import generated.scrum.client.release.GCreateBugfixReleaseAction;
import ilarkesto.core.scope.Scope;
import scrum.client.common.TooltipBuilder;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class CreateBugfixReleaseAction extends GCreateBugfixReleaseAction {

	public CreateBugfixReleaseAction(scrum.client.release.Release release) {
		super(release);
	}

	@Override
	public String getLabel() {
		return "Create Bugfix Release";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create a new Bugfix Release for this Release.");
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
	}

	@Override
	public boolean isExecutable() {
		if (release.isBugfix()) return false;
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		Release bugfix = getCurrentProject().createNewRelease(release);
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(bugfix);
	}

}