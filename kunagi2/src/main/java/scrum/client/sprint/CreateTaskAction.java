
package scrum.client.sprint;

import ilarkesto.core.scope.Scope;
import scrum.client.common.TooltipBuilder;
import scrum.client.project.Requirement;
import scrum.client.workspace.ProjectWorkspaceWidgets;

/**
 *
 *
 */
public class CreateTaskAction extends GCreateTaskAction {

	private Requirement requirement;

    /**
     *
     * @param requirement
     */
    public CreateTaskAction(Requirement requirement) {
		this.requirement = requirement;
	}

	@Override
	public String getLabel() {
		return "Create Task";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create a new Task for this Story.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
                    tb.addRemark(TooltipBuilder.NOT_TEAM);
        }
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		Task task = requirement.createNewTask();
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(task);
	}

}
