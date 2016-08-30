
package scrum.client.release;

import ilarkesto.core.scope.Scope;
import scrum.client.common.TooltipBuilder;
import scrum.client.workspace.ProjectWorkspaceWidgets;

/**
 *
 *
 */
public class CreateBugfixReleaseAction extends GCreateBugfixReleaseAction {

    /**
     *
     * @param release
     */
    public CreateBugfixReleaseAction(scrum.client.release.Release release) {
		super(release);
	}

	@Override
	public String getLabel() {
		return "Create Bugfix Release";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create a new Bugfix Release for this Release.");
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) {
                    tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
        }
	}

	@Override
	public boolean isExecutable() {
		return !release.isBugfix();
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isScrumTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		Release bugfix = getCurrentProject().createNewRelease(release);
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(bugfix);
	}

}