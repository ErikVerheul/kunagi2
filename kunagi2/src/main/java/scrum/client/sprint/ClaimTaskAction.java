
package scrum.client.sprint;

import scrum.client.admin.User;
import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class ClaimTaskAction extends GClaimTaskAction {

    /**
     *
     * @param task
     */
    public ClaimTaskAction(Task task) {
		super(task);
	}

	@Override
	public String getLabel() {
		return "Claim";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Claim ownership for this Task, stating that you are working on this Task.");
		if (!getCurrentProject().isTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_TEAM);
		} else {
			if (task.isClosed()) {
                            tb.addRemark("Task is already closed.");
            }
			if (task.isOwner(getCurrentUser())) {
                            tb.addRemark("Task is already owned by you.");
            }
		}
	}

	@Override
	public boolean isExecutable() {
            if (task.isClosed()) {
                return false;
        }
		return !task.isOwner(getCurrentUser());
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		User owner = task.getOwner();
		task.claim();
		addUndo(new Undo(owner));
	}

	class Undo extends ALocalUndo {

		User owner;

		public Undo(User owner) {
			this.owner = owner;
		}

		@Override
		public String getLabel() {
			return "Undo Claim " + task.getReference() + " " + task.getLabel();
		}

		@Override
		protected void onUndo() {
			task.setOwner(owner);
		}

	}

}
