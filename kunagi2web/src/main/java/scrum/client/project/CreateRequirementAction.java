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
import scrum.client.common.TooltipBuilder;
import scrum.client.project.ProductBacklogWidget.FilterToggleAction;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class CreateRequirementAction extends GCreateRequirementAction {

	private Requirement relative;
	private boolean before;
	private FilterToggleAction filterToggleAction;

	public CreateRequirementAction(FilterToggleAction filterToggleAction) {
		this.filterToggleAction = filterToggleAction;
	}

	@Override
	public String getLabel() {
		return "Create Story";
	}

	@Override
	public boolean isExecutable() {
		if (filterToggleAction.filterActive) return false;
		return true;
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create a new Story.");
		if (!getCurrentProject().isProductOwner(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isProductOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		Requirement requirement = getCurrentProject().createNewRequirement(relative, before, false);
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(requirement);
	}

	public void setRelative(Requirement nextRequirement) {
		this.relative = nextRequirement;
	}

	public void setBefore(boolean before) {
		this.before = before;
	}

}
