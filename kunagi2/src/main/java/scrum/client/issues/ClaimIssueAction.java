
package scrum.client.issues;

import scrum.client.admin.User;
import scrum.client.common.TooltipBuilder;

/**
 *
 * @author erik
 */
public class ClaimIssueAction extends GClaimIssueAction {

    /**
     *
     * @param issue
     */
    public ClaimIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Claim";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Claim ownership for this Issue, stating that you are working on it.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_TEAM);
		} else {
			if (issue.isClosed()) {
                            tb.addRemark("Issue is already closed.");
            }
			if (issue.isFixed()) {
                            tb.addRemark("Issue is already fixed.");
            }
                        if (issue.isOwner(getCurrentUser())) {
                            tb.addRemark("Issue is already owned by you.");
            }
		}
	}

	@Override
        public boolean isExecutable() {
		if (!issue.isUrgent()) {
                    return false;
        }
		if (issue.isClosed()) {
                    return false;
        }
		if (issue.isFixed()) {
            return false;
        }
		return !issue.isOwner(getCurrentUser());
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		User owner = issue.getOwner();
		issue.claim(getCurrentUser());
		addUndo(new Undo(owner));
	}

	class Undo extends ALocalUndo {

		User owner;

		public Undo(User owner) {
			this.owner = owner;
		}

		@Override
		public String getLabel() {
			return "Undo Claim " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			issue.setOwner(owner);
		}

	}

}