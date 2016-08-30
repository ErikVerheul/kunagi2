
package scrum.client.impediments;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class CreateImpedimentAction extends GCreateImpedimentAction {

	@Override
	public String getLabel() {
		return "Create Impediment";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create new Impediment.");
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
		Impediment impediment = getCurrentProject().createNewImpediment();
		getNavigator().gotoPageWithEntity(null, impediment);
	}

}
