
package scrum.client.issues;

/**
 *
 * @author erik
 */
public class RejectFixIssueAction extends GRejectFixIssueAction {

    /**
     *
     * @param issue
     */
    public RejectFixIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Reject fix";
	}

	@Override
	public boolean isExecutable() {
		if (!issue.isUrgent()) {
                    return false;
        }
		if (issue.isClosed()) {
                    return false;
        }
		return issue.isFixed();
	}

	@Override
	protected void onExecute() {
		issue.rejectFix();
	}

}