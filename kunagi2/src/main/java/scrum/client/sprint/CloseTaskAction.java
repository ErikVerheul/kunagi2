
package scrum.client.sprint;

import scrum.client.common.TooltipBuilder;

/**
 *
 * @author erik
 */
public class CloseTaskAction extends GCloseTaskAction {

    /**
     *
     * @param task
     */
    public CloseTaskAction(Task task) {
		super(task);
	}

	@Override
	public String getLabel() {
		return "Done";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Mark this Task as done.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_TEAM);
		} else {
			if (task.isClosed()) {
                            tb.addRemark("Task is already closed.");
            }
			if (task.isOwnerSet() && !task.isOwner(getCurrentUser())) {
                            tb.addRemark("Another user owns this Task.");
            }
		}
	}

	@Override
	public boolean isExecutable() {

            if (task.isClosed()) {
                return false;
        }
		return !(task.isOwnerSet() && !task.isOwner(getCurrentUser()));

	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		task.setDone(getCurrentUser());
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Set done for " + task.getReference() + " " + task.getLabel();
		}

		@Override
		protected void onUndo() {
			task.setUnDone(getCurrentUser());
		}

	}

}
