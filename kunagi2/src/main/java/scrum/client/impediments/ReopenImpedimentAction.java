
package scrum.client.impediments;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class ReopenImpedimentAction extends GCloseImpedimentAction {

    /**
     *
     * @param impediment
     */
    public ReopenImpedimentAction(scrum.client.impediments.Impediment impediment) {
		super(impediment);
	}

	@Override
	public String getLabel() {
		return "Reopen";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Reopen this Impediment, marking it as impeding.");
		if (!impediment.getProject().isScrumTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
		}
	}

	@Override
	public boolean isExecutable() {
		return impediment.isClosed();
	}

	@Override
	public boolean isPermitted() {
		return impediment.getProject().isScrumTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		impediment.setClosed(false);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Reopen " + impediment.getReferenceAndLabel();
		}

		@Override
		protected void onUndo() {
			impediment.setClosed(true);
		}

	}

}