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

import generated.client.issues.GAcceptIssueAsBugAction;
import scrum.client.common.TooltipBuilder;

public class AcceptIssueAsBugAction extends GAcceptIssueAsBugAction {

	public AcceptIssueAsBugAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Accept as bug";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Accept this issue as a bug. Bugs can be classified by severeness and need to be reviewed and (if necessary) fixed by the Team.");
		if (!issue.getProject().isProductOwnerOrScrumMaster(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER_NOR_SCRUMMASTER);
		}
	}

	@Override
	public boolean isPermitted() {
		if (!issue.getProject().isProductOwnerOrScrumMaster(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (issue.isClosed()) return false;
		if (issue.isBug()) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		issue.acceptAsBug();
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Accept as bug: " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			issue.reopen();
		}

	}

}