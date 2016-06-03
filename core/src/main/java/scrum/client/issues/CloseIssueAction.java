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

import generated.client.issues.GCloseIssueAction;
import scrum.client.common.TooltipBuilder;

public class CloseIssueAction extends GCloseIssueAction {

	public CloseIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Close";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Close this Issue, marking it as resolved or rejected. You can give a reason in the Statement and Change Log.");
		if (issue.isIdea() || issue.isBug()) {
			if (!getCurrentProject().isProductOwner(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
		} else {
			if (!getCurrentProject().isScrumTeamMember(getCurrentUser()))
				tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
		}
	}

	@Override
	public boolean isExecutable() {
		if (issue.isClosed()) return false;
		return true;
	}

	@Override
	public boolean isPermitted() {
		if ((issue.isIdea() || issue.isBug()) && !getCurrentProject().isProductOwner(getCurrentUser())) return false;
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		issue.close();
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Close " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			issue.reopen();
		}

	}

}