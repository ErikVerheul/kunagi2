
package scrum.client.sprint;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class UnclaimTaskAction extends GReopenTaskAction {

    /**
     *
     * @param task
     */
    public UnclaimTaskAction(Task task) {
		super(task);
	}

	@Override
	public String getLabel() {
		return "Unclaim";
	}

    /**
     *
     * @param tb
     */
    @Override
	public void updateTooltip(TooltipBuilder tb) {
		tb.setText("Unclaim ownership for this Task.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_TEAM);
		} else {
			if (task.isClosed()) {
                            tb.addRemark("Task is already closed.");
            }
		}
	}

	@Override
	public boolean isExecutable() {
		if (!task.isClaimed()) {
                    return false;
        }
                if (task.isClosed()) {
                    return false;
        }
		return getCurrentProject().isTeamMember(getCurrentUser());
	}

	@Override
	public boolean isPermitted() {
		return true;
	}

	@Override
	protected void onExecute() {
		task.setUnOwned();
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Unclaim " + task.getReference() + " " + task.getLabel();
		}

		@Override
		protected void onUndo() {
			task.claim();
		}

	}

}
