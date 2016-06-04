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
package scrum.client.issues;

import generated.scrum.client.issues.GClaimIssueAction;
import scrum.client.admin.User;
import scrum.client.common.TooltipBuilder;

public class ClaimIssueAction extends GClaimIssueAction {

	public ClaimIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Claim";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Claim ownership for this Issue, stating that you are working on it.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_TEAM);
		} else {
			if (issue.isClosed()) tb.addRemark("Issue is already closed.");
			if (issue.isFixed()) tb.addRemark("Issue is already fixed.");
			if (issue.isOwner(getCurrentUser())) tb.addRemark("Issue is already owned by you.");
		}
	}

	@Override
	public boolean isExecutable() {
		if (!issue.isUrgent()) return false;
		if (issue.isClosed()) return false;
		if (issue.isFixed()) return false;
		if (issue.isOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		User owner = issue.getOwner();
		issue.claim(getCurrentUser());
		addUndo(new Undo(owner));
	}

	class Undo extends ALocalUndo {

		User owner;

		public Undo(User owner) {
			this.owner = owner;
		}

		@Override
		public String getLabel() {
			return "Undo Claim " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			issue.setOwner(owner);
		}

	}

}