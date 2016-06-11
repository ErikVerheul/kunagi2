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

public class CloseRequirementAction extends GCloseRequirementAction {

	public CloseRequirementAction(scrum.client.project.Requirement requirement) {
		super(requirement);
	}

	@Override
	public String getLabel() {
		return "Accept";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Accept this Story as done.");
		if (!getCurrentProject().isProductOwner(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
		} else {
			if (requirement.isClosed()) tb.addRemark("Story is already closed.");
			if (!requirement.getTasksInSprint().isEmpty() && !requirement.isTasksClosed())
				tb.addRemark("Requirement contains unclosed tasks.");
		}
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isProductOwner(getCurrentUser())) return false;
		if (!requirement.getTasksInSprint().isEmpty() && !requirement.isTasksClosed()) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (requirement.isClosed()) return false;
		if (!requirement.isInCurrentSprint()) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		requirement.setClosed(true);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Close " + requirement.getReferenceAndLabel();
		}

		@Override
		protected void onUndo() {
			requirement.setClosed(false);
		}

	}

}