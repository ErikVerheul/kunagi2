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

import ilarkesto.core.scope.Scope;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class CreateIssueAction extends GCreateIssueAction {

	@Override
	public String getLabel() {
		return "Create Issue";
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	protected void onExecute() {
		Issue issue = getCurrentProject().createNewIssue();
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(issue);
		addUndo(new Undo(issue));
	}

	class Undo extends ALocalUndo {

		private Issue issue;

		public Undo(Issue issue) {
			this.issue = issue;
		}

		@Override
		public String getLabel() {
			return "Undo Create " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			issue.getProject().deleteIssue(issue);
		}

	}

}
