
package scrum.client.release;

import ilarkesto.core.scope.Scope;
import scrum.client.common.TooltipBuilder;
import scrum.client.workspace.ProjectWorkspaceWidgets;

/**
 *
 * @author erik
 */
public class CreateReleaseAction extends GCreateReleaseAction {

	@Override
	public String getLabel() {
		return "Create Release";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create new major Release.");
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
		Release release = getCurrentProject().createNewRelease(null);
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(release);
	}

}