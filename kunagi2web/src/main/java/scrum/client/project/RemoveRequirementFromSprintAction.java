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
import scrum.client.sprint.KickStoryFromSprintServiceCall;
import scrum.client.sprint.Sprint;

public class RemoveRequirementFromSprintAction extends GRemoveRequirementFromSprintAction {

	public RemoveRequirementFromSprintAction(Requirement requirement) {
		super(requirement);
	}

	@Override
	public String getLabel() {
		return "Kick from Sprint";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Remove this Story from the current Sprint.");
		if (!requirement.getProject().isProductOwner(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
		} else {
			if (!requirement.isSprintSet()) tb.addRemark("Story is not in in the current Sprint.");
		}
	}

	@Override
	public boolean isExecutable() {
		if (!requirement.isInCurrentSprint()) return false;
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!requirement.getProject().isProductOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		new KickStoryFromSprintServiceCall(requirement.getId()).execute();
		// Sprint sprint = requirement.getSprint();
		// requirement.removeFromSprint();
		//
		// Project project = getCurrentProject();
		// List<Requirement> requirements = project.getProductBacklogRequirements();
		// requirements.remove(requirement);
		// Collections.sort(requirements, project.getRequirementsOrderComparator());
		// requirements.add(0, requirement);
		// project.updateRequirementsOrder(requirements);

		// addUndo(new Undo(requirement.getSprint()));
	}

	class Undo extends ALocalUndo {

		private Sprint s;

		public Undo(Sprint s) {
			this.s = s;
		}

		@Override
		public String getLabel() {
			return "Undo Remove " + requirement.getReferenceAndLabel() + " from Sprint";
		}

		@Override
		protected void onUndo() {
			requirement.setSprint(s);
			s.updateRequirementsOrder();
		}

	}

}
