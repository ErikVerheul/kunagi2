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

import ilarkesto.core.scope.Scope;

import java.util.List;

import scrum.client.common.TooltipBuilder;
import scrum.client.workspace.Ui;
import scrum.client.workspace.UsersWorkspaceWidgets;

public class CreateExampleProjectAction extends GCreateExampleProjectAction {

	@Override
	public String getLabel() {
		return "Create Example Project";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create a new example Project with some content.");
		if (!getCurrentUser().isAdmin() && getDao().getSystemConfig().isProjectCreationDisabled())
			tb.addRemark("Creating new projects is disabled.");
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentUser().isAdmin() && getDao().getSystemConfig().isProjectCreationDisabled()) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		Scope.get().getComponent(Ui.class).lock("Creating Example Project...");
		final List<Project> previousProjects = getDao().getProjects();
		new CreateExampleProjectServiceCall().execute(new Runnable() {

			@Override
			public void run() {
				Scope.get().getComponent(Ui.class).unlock();
				List<Project> newProjects = getDao().getProjects();
				newProjects.removeAll(previousProjects);
				if (!newProjects.isEmpty()) {
					Scope.get().getComponent(UsersWorkspaceWidgets.class).getProjectSelector()
							.select(newProjects.get(0));
				}
			}
		});
	}

}