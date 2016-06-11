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

public class ChangeProjectAction extends GChangeProjectAction {

	private Project project;

	public ChangeProjectAction(Project project) {
		this.project = project;
	}

	@Override
	public String getLabel() {
		return project.getLabel();
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Switch to another Project.");
	}

	@Override
	protected void onExecute() {
		getNavigator().gotoProject(project.getId());
	}

}