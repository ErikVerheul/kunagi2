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

import scrum.client.issues.GAcceptIssueAsIdeaAction;
import scrum.client.common.TooltipBuilder;

public class AcceptIssueAsIdeaAction extends GAcceptIssueAsIdeaAction {

	public AcceptIssueAsIdeaAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Accept as idea";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Accept this issue as an idea for future stories.");
	}

	@Override
	public boolean isPermitted() {
		if (!issue.getProject().isProductOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (!(issue.isOpen() || issue.isUnclosedBug())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		issue.acceptAsIdea();
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Accept idea " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			issue.reopen();
		}

	}

}