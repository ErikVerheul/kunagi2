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

import generated.client.issues.GReopenIssueAction;
import ilarkesto.core.scope.Scope;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class ReopenIssueAction extends GReopenIssueAction {

	public ReopenIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public boolean isPermitted() {
		if (!issue.getProject().isProductOwnerOrScrumMaster(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (issue.isOpen()) return false;
		return true;
	}

	@Override
	public String getLabel() {
		return issue.isClosed() ? "Re-Open" : "Move to inbox";
	}

	@Override
	protected void onExecute() {
		issue.reopen();
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(issue);
	}

}