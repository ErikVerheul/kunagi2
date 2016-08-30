
package scrum.client.impediments;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class CloseImpedimentAction extends GCloseImpedimentAction {

    /**
     *
     * @param impediment
     */
    public CloseImpedimentAction(scrum.client.impediments.Impediment impediment) {
		super(impediment);
	}

	@Override
	public String getLabel() {
		return "Close";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Close this Impediment, marking it as solved or obsolete.");
		if (!impediment.getProject().isScrumTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
		}
	}

	@Override
	public boolean isExecutable() {
		return !impediment.isClosed();
	}

	@Override
	public boolean isPermitted() {
		return impediment.getProject().isScrumTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		impediment.setClosed(true);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Close " + impediment.getReference() + " " + impediment.getLabel();
		}

		@Override
		protected void onUndo() {
			impediment.setClosed(false);
		}

	}

}