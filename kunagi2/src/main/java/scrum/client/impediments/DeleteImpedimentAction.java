
package scrum.client.impediments;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class DeleteImpedimentAction extends GDeleteImpedimentAction {

    /**
     *
     * @param impediment
     */
    public DeleteImpedimentAction(Impediment impediment) {
		super(impediment);
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
		tb.setText("Delete this Impediment permanently.");
		if (!impediment.getProject().isScrumTeamMember(getCurrentUser())) {
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
		getCurrentProject().deleteImpediment(impediment);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Delete " + impediment.getReference() + " " + impediment.getLabel();
		}

		@Override
		protected void onUndo() {
			getDao().createImpediment(impediment);
		}

	}

}
