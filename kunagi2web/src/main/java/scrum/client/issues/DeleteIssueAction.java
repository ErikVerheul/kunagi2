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

import generated.scrum.client.issues.GDeleteIssueAction;
import scrum.client.common.TooltipBuilder;

public class DeleteIssueAction extends GDeleteIssueAction {

	public DeleteIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Delete";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Delete this Issue permanently.");
		if (!issue.getProject().isScrumTeamMember(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
		if (!issue.isOpen()) tb.addRemark("Only issues from the inbox can be deleted.");
	}

	@Override
	public boolean isExecutable() {
		if (!issue.isOpen()) return false;
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!issue.getProject().isScrumTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		getCurrentProject().deleteIssue(issue);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Delete " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			getDao().createIssue(issue);
		}

	}

}