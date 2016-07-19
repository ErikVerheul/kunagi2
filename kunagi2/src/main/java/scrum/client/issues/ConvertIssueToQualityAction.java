
package scrum.client.issues;

import scrum.client.common.TooltipBuilder;
import scrum.client.project.Quality;

/**
 *
 * @author erik
 */
public class ConvertIssueToQualityAction extends GConvertIssueToQualityAction {

    /**
     *
     * @param issue
     */
    public ConvertIssueToQualityAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Convert to Quality";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Convert this issue to a Quality in the Quality Backlog.");
		if (!issue.getProject().isProductOwner(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
		}
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public boolean isPermitted() {
		return issue.getProject().isProductOwner(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		Quality quality = new Quality(issue);
		getDao().createQuality(quality);
		getDao().deleteIssue(issue);
		addUndo(new Undo(quality));
	}

	class Undo extends ALocalUndo {

		private Quality quality;

		public Undo(Quality quality) {
			this.quality = quality;
		}

		@Override
		public String getLabel() {
			return "Undo Convert " + issue.getReference() + " " + issue.getLabel() + " to Quality";
		}

		@Override
		protected void onUndo() {
			getDao().deleteQuality(quality);
			getDao().createIssue(issue);
		}

	}

}
