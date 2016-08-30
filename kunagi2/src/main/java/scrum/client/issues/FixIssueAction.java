
package scrum.client.issues;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class FixIssueAction extends GFixIssueAction {

    /**
     *
     * @param issue
     */
    public FixIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Mark as fixed";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Mark this Issue as fixed.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_TEAM);
		} else {
			if (issue.isFixed()) {
                            tb.addRemark("Issue is already fixed.");
            }
			if (issue.isClosed()) {
                            tb.addRemark("Issue is already closed.");
            }
		}
	}

	@Override
	public boolean isExecutable() {
            if (!issue.isUrgent()) {
                return false;
            }
            if (issue.isFixed()) {
            return false;
        }
		return !issue.isClosed();

	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		issue.setFixed(getCurrentUser());
	}

}