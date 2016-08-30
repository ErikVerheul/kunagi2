
package scrum.client.issues;

import ilarkesto.core.base.Str;
import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class ReplyToIssueAuthorAction extends GReplyToIssueAuthorAction {

    /**
     *
     * @param issue
     */
    public ReplyToIssueAuthorAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Reply by email";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Reply to the issue author by email.");
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isScrumTeamMember(getCurrentUser());
	}

	@Override
	public boolean isExecutable() {
		return !Str.isBlank(issue.getIssuerEmail());
	}

	@Override
	protected void onExecute() {
		new ReplyToIssueDialog(issue).showDialog();
	}

}