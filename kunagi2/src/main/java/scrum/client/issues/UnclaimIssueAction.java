
package scrum.client.issues;

/**
 *
 * @author erik
 */
public class UnclaimIssueAction extends GUnclaimIssueAction {

    /**
     *
     * @param issue
     */
    public UnclaimIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Unclaim";
	}

	@Override
	public boolean isExecutable() {
		return issue.isOwnerSet();
	}

	@Override
	public boolean isPermitted() {
		return issue.isOwner(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		issue.setOwner(null);
	}

}