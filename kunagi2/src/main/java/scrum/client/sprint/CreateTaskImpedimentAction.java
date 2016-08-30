
package scrum.client.sprint;

import ilarkesto.core.scope.Scope;
import scrum.client.common.TooltipBuilder;
import scrum.client.impediments.Impediment;
import scrum.client.workspace.ProjectWorkspaceWidgets;

/**
 *
 *
 */
public class CreateTaskImpedimentAction extends GCreateTaskImpedimentAction {

    /**
     *
     * @param task
     */
    public CreateTaskImpedimentAction(scrum.client.sprint.Task task) {
		super(task);
	}

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
		tb.setText("Create new Impediment, which is blocking this Task.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
                    tb.addRemark(TooltipBuilder.NOT_TEAM);
        }
	}

	@Override
	public boolean isExecutable() {
		return !task.isImpedimentSet();
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		Impediment impediment = getCurrentProject().createNewImpediment();
		task.setImpediment(impediment);
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(impediment);
	}
}