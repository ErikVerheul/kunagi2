
package scrum.client.issues;

import scrum.client.common.TooltipBuilder;
import scrum.client.project.Requirement;

/**
 *
 *
 */
public class ConvertIssueToRequirementAction extends GConvertIssueToRequirementAction {

    /**
     *
     * @param issue
     */
    public ConvertIssueToRequirementAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Convert to Story";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Convert this Issue to a Story in the Product Backlog.");
		if (!issue.getProject().isProductOwner(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
		}
	}

	@Override
	public boolean isExecutable() {
		return !issue.isClosed();
	}

	@Override
	public boolean isPermitted() {
		return issue.getProject().isProductOwner(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		new ConvertIssueToStoryServiceCall(issue.getId()).execute();
	}

	class Undo extends ALocalUndo {

		private Requirement requirement;

		public Undo(Requirement requirement) {
			this.requirement = requirement;
		}

		@Override
		public String getLabel() {
			return "Undo Convert " + issue.getReference() + " " + issue.getLabel() + " to Story";
		}

		@Override
		protected void onUndo() {
			getDao().deleteRequirement(requirement);
			getDao().createIssue(issue);
		}

	}

}