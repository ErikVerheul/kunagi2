
package scrum.client.issues;

/**
 *
 * @author erik
 */
public class IssueManager extends GIssueManager {

	private boolean suspendedIssuesVisible;
	private boolean closedIssuesVisible;

    /**
     *
     * @param suspendedIssuesVisible
     */
    public void setSuspendedIssuesVisible(boolean suspendedIssuesVisible) {
		this.suspendedIssuesVisible = suspendedIssuesVisible;
	}

    /**
     *
     * @return
     */
    public boolean isSuspendedIssuesVisible() {
		return suspendedIssuesVisible;
	}

    /**
     *
     * @return
     */
    public boolean isClosedIssuesVisible() {
		return closedIssuesVisible;
	}

    /**
     *
     * @param closedIssuesVisible
     */
    public void setClosedIssuesVisible(boolean closedIssuesVisible) {
		if (this.closedIssuesVisible == closedIssuesVisible) {
                    return;
        }
		this.closedIssuesVisible = closedIssuesVisible;
		if (closedIssuesVisible) {
			new RequestClosedIssuesServiceCall().execute();
		}
	}

}
