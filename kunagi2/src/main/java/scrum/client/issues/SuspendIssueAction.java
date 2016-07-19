
package scrum.client.issues;

import scrum.client.common.TooltipBuilder;

/**
 *
 * @author erik
 */
public class SuspendIssueAction extends GSuspendIssueAction {

	private int days;

    /**
     *
     * @param issue
     * @param days
     */
    public SuspendIssueAction(scrum.client.issues.Issue issue, int days) {
		super(issue);
		this.days = days;
	}

	@Override
	public String getLabel() {
		return "Suspend for " + days + " days";
	}

    /**
     *
     * @param tb
     */
    @Override
	public void updateTooltip(TooltipBuilder tb) {
		tb.setText("Hide this issue for " + days + " days (if you want to postpone a decision).");
		if (!issue.getProject().isProductOwner(getCurrentUser())) {
                    tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
        }
	}

	@Override
	public boolean isPermitted() {
		return issue.getProject().isProductOwner(getCurrentUser());
	}

	@Override
	public boolean isExecutable() {
		if (issue.isSuspended()) {
                    return false;
        }
		return issue.isOpen();
	}

	@Override
	protected void onExecute() {
		issue.suspend(days);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Suspend " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			issue.setSuspendedUntilDate(null);
		}

	}

}