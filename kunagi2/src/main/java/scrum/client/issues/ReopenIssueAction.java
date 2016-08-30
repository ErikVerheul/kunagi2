
package scrum.client.issues;

import ilarkesto.core.scope.Scope;
import scrum.client.workspace.ProjectWorkspaceWidgets;

/**
 *
 *
 */
public class ReopenIssueAction extends GReopenIssueAction {

    /**
     *
     * @param issue
     */
    public ReopenIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public boolean isPermitted() {
		return issue.getProject().isProductOwnerOrScrumMaster(getCurrentUser());
	}

	@Override
	public boolean isExecutable() {
		return !issue.isOpen();
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