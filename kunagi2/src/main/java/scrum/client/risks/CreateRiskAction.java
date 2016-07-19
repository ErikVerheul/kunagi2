
package scrum.client.risks;

import ilarkesto.core.scope.Scope;
import scrum.client.common.TooltipBuilder;
import scrum.client.workspace.ProjectWorkspaceWidgets;

/**
 *
 * @author erik
 */
public class CreateRiskAction extends GCreateRiskAction {

	@Override
	public String getLabel() {
		return "Create Risk";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create new Risk.");
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
		Risk risk = getCurrentProject().createNewRisk();
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(risk);
	}

}
