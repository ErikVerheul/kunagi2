
package scrum.client.issues;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class PublishIssueAction extends GPublishIssueAction {

    /**
     *
     * @param issue
     */
    public PublishIssueAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Publish";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Make this issue available on the homepage.");
	}

	@Override
	public boolean isExecutable() {
		if (issue.isPublished()) {
                    return false;
        }
		return getCurrentProject().getHomepageDir() != null;
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isScrumTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		issue.setPublished(true);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Publish: " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			issue.setPublished(false);
		}
	}

}