
package scrum.client.issues;

import scrum.client.common.TooltipBuilder;

/**
 *
 * @author erik
 */
public class DeleteIssueAction extends GDeleteIssueAction {

    /**
     *
     * @param issue
     */
    public DeleteIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Delete";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Delete this Issue permanently.");
		if (!issue.getProject().isScrumTeamMember(getCurrentUser())) {
                    tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
        }
		if (!issue.isOpen()) {
                    tb.addRemark("Only issues from the inbox can be deleted.");
        }
	}

	@Override
	public boolean isExecutable() {
		return issue.isOpen();
	}

	@Override
	public boolean isPermitted() {
		return issue.getProject().isScrumTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		getCurrentProject().deleteIssue(issue);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Delete " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			getDao().createIssue(issue);
		}

	}

}