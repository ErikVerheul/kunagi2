
package scrum.client.issues;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class AcceptIssueAsIdeaAction extends GAcceptIssueAsIdeaAction {

    /**
     *
     * @param issue
     */
    public AcceptIssueAsIdeaAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Accept as idea";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Accept this issue as an idea for future stories.");
	}

	@Override
	public boolean isPermitted() {
		return issue.getProject().isProductOwner(getCurrentUser());
	}

	@Override
	public boolean isExecutable() {
		return issue.isOpen() || issue.isUnclosedBug();
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