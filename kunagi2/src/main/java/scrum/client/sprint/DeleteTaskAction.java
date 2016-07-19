
package scrum.client.sprint;

import scrum.client.common.TooltipBuilder;

/**
 *
 * @author erik
 */
public class DeleteTaskAction extends GDeleteTaskAction {

    /**
     *
     * @param task
     */
    public DeleteTaskAction(Task task) {
		super(task);
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
		tb.setText("Delete this Task permanently.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_TEAM);
		} else {
			if (task.isOwnerSet() && !task.isOwner(getCurrentUser())) {
                            tb.addRemark("Another user owns this Task.");
            }
		}
	}

	@Override
	public boolean isExecutable() {
		if (task.isOwnerSet() && !task.isOwner(getCurrentUser())) {
                    return false;
        }
		return task.getBurnedWork() <= 0;
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		task.getRequirement().deleteTask(task);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Delete " + task.getReference() + " " + task.getLabel();
		}

		@Override
		protected void onUndo() {
			getDao().createTask(task);
		}

	}

}
