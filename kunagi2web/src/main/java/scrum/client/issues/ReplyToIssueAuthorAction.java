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

import scrum.client.issues.GReplyToIssueAuthorAction;
import ilarkesto.core.base.Str;
import scrum.client.common.TooltipBuilder;

public class ReplyToIssueAuthorAction extends GReplyToIssueAuthorAction {

	public ReplyToIssueAuthorAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Reply by email";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Reply to the issue author by email.");
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (Str.isBlank(issue.getIssuerEmail())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		new ReplyToIssueDialog(issue).showDialog();
	}

}