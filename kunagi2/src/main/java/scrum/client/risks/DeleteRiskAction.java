
package scrum.client.risks;

import scrum.client.common.TooltipBuilder;

/**
 *
 * @author erik
 */
public class DeleteRiskAction extends GDeleteRiskAction {

    /**
     *
     * @param risk
     */
    protected DeleteRiskAction(Risk risk) {
		super(risk);
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
		tb.setText("Delete this Risk permanently.");
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) {
                    tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
        }
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isScrumTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		getCurrentProject().deleteRisk(risk);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Delete " + risk.getReference() + " " + risk.getLabel();
		}

		@Override
		protected void onUndo() {
			getDao().createRisk(risk);
		}

	}

}
