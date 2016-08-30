
package scrum.client.issues;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class CloseIssueAction extends GCloseIssueAction {

    /**
     *
     * @param issue
     */
    public CloseIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Close";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Close this Issue, marking it as resolved or rejected. You can give a reason in the Statement and Change Log.");
		if (issue.isIdea() || issue.isBug()) {
			if (!getCurrentProject().isProductOwner(getCurrentUser())) {
                            tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
            }
		} else {
			if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) {
                            tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
            }
		}
	}

	@Override
	public boolean isExecutable() {
		return !issue.isClosed();
	}

	@Override
	public boolean isPermitted() {
            if ((issue.isIdea() || issue.isBug()) && !getCurrentProject().isProductOwner(getCurrentUser())) {
                return false;
        }
		return getCurrentProject().isScrumTeamMember(getCurrentUser());
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