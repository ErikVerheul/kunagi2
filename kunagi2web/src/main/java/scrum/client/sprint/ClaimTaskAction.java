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

import scrum.client.sprint.GClaimTaskAction;
import scrum.client.admin.User;
import scrum.client.common.TooltipBuilder;

public class ClaimTaskAction extends GClaimTaskAction {

	public ClaimTaskAction(Task task) {
		super(task);
	}

	@Override
	public String getLabel() {
		return "Claim";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Claim ownership for this Task, stating that you are working on this Task.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_TEAM);
		} else {
			if (task.isClosed()) tb.addRemark("Task is already closed.");
			if (task.isOwner(getCurrentUser())) tb.addRemark("Task is already owned by you.");
		}
	}

	@Override
	public boolean isExecutable() {
		if (task.isClosed()) return false;
		if (task.isOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		User owner = task.getOwner();
		task.claim();
		addUndo(new Undo(owner));
	}

	class Undo extends ALocalUndo {

		User owner;

		public Undo(User owner) {
			this.owner = owner;
		}

		@Override
		public String getLabel() {
			return "Undo Claim " + task.getReference() + " " + task.getLabel();
		}

		@Override
		protected void onUndo() {
			task.setOwner(owner);
		}

	}

}
