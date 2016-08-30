
package scrum.client.sprint;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class ReopenTaskAction extends GReopenTaskAction {

    /**
     *
     * @param task
     */
    public ReopenTaskAction(Task task) {
		super(task);
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
		tb.setText("Reopen this Task, stating that is is not yet done or needs to be performed again.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_TEAM);
		} else {
			if (!task.isClosed()) {
                            tb.addRemark("Task is not closed.");
            }
		}
	}

	@Override
	public boolean isExecutable() {
		return task.isClosed();
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		task.setUnDone(getCurrentUser());
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Set Undone for " + task.getReference() + " " + task.getLabel();
		}

		@Override
		protected void onUndo() {
			task.setDone(getCurrentUser());
		}

	}

}
