
package scrum.client.issues;

import scrum.client.common.TooltipBuilder;

/**
 *
 * @author erik
 */
public class AcceptIssueAsBugAction extends GAcceptIssueAsBugAction {

    /**
     *
     * @param issue
     */
    public AcceptIssueAsBugAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Accept as bug";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Accept this issue as a bug. Bugs can be classified by severeness and need to be reviewed and (if necessary) fixed by the Team.");
		if (!issue.getProject().isProductOwnerOrScrumMaster(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER_NOR_SCRUMMASTER);
		}
	}

	@Override
	public boolean isPermitted() {
		return issue.getProject().isProductOwnerOrScrumMaster(getCurrentUser());
	}

	@Override
	public boolean isExecutable() {
		if (issue.isClosed()) {
                    return false;
        }
		return !issue.isBug();
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