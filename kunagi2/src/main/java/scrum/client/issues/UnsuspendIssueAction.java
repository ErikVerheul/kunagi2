
package scrum.client.issues;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class UnsuspendIssueAction extends GUnsuspendIssueAction {

    /**
     *
     * @param issue
     */
    public UnsuspendIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Unsuspend";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Unhide this issue.");
	}

	@Override
	public boolean isExecutable() {
		if (!issue.isSuspended()) {
                    return false;
        }
		return issue.isOpen();
	}

	@Override
	protected void onExecute() {
		issue.unsuspend();
	}

}