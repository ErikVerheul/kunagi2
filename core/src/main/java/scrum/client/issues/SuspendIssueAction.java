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

import generated.scrum.client.issues.GSuspendIssueAction;
import scrum.client.common.TooltipBuilder;

public class SuspendIssueAction extends GSuspendIssueAction {

	private int days;

	public SuspendIssueAction(scrum.client.issues.Issue issue, int days) {
		super(issue);
		this.days = days;
	}

	@Override
	public String getLabel() {
		return "Suspend for " + days + " days";
	}

	@Override
	public void updateTooltip(TooltipBuilder tb) {
		tb.setText("Hide this issue for " + days + " days (if you want to postpone a decision).");
		if (!issue.getProject().isProductOwner(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
	}

	@Override
	public boolean isPermitted() {
		if (!issue.getProject().isProductOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (issue.isSuspended()) return false;
		if (!issue.isOpen()) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		issue.suspend(days);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Suspend " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			issue.setSuspendedUntilDate(null);
		}

	}

}