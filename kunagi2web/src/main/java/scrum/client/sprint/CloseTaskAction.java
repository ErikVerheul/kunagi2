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

import scrum.client.sprint.GCloseTaskAction;
import scrum.client.common.TooltipBuilder;

public class CloseTaskAction extends GCloseTaskAction {

	public CloseTaskAction(Task task) {
		super(task);
	}

	@Override
	public String getLabel() {
		return "Done";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Mark this Task as done.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_TEAM);
		} else {
			if (task.isClosed()) tb.addRemark("Task is already closed.");
			if (task.isOwnerSet() && !task.isOwner(getCurrentUser())) tb.addRemark("Another user owns this Task.");
		}
	}

	@Override
	public boolean isExecutable() {

		if (task.isClosed()) return false;
		if (task.isOwnerSet() && !task.isOwner(getCurrentUser())) return false;
		return true;

	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		task.setDone(getCurrentUser());
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Set done for " + task.getReference() + " " + task.getLabel();
		}

		@Override
		protected void onUndo() {
			task.setUnDone(getCurrentUser());
		}

	}

}
