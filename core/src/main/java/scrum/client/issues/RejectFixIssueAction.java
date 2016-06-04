/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.issues;

import generated.scrum.client.issues.GRejectFixIssueAction;

public class RejectFixIssueAction extends GRejectFixIssueAction {

	public RejectFixIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Reject fix";
	}

	@Override
	public boolean isExecutable() {
		if (!issue.isUrgent()) return false;
		if (issue.isClosed()) return false;
		if (!issue.isFixed()) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		issue.rejectFix();
	}

}