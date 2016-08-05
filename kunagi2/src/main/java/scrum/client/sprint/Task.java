package scrum.client.sprint;

import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.HyperlinkWidget;
import ilarkesto.gwt.client.editor.AFieldModel;
import java.util.HashMap;
import scrum.client.ScrumGwt;
import scrum.client.admin.Auth;
import scrum.client.admin.User;
import scrum.client.collaboration.ForumSupport;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.common.ShowEntityAction;
import scrum.client.project.Project;
import scrum.client.project.Requirement;
import scrum.client.tasks.WhiteboardWidget;

/**
 *
 * @author erik
 */
public class Task extends GTask implements ReferenceSupport, LabelSupport, ForumSupport {

    /**
     *
     */
    public static final int INIT_EFFORT = 1;

    /**
     *
     */
    public static final String REFERENCE_PREFIX = "tsk";

    /**
     *
     * @return
     */
    public Sprint getSprint() {
		return getRequirement().getSprint();
	}

    /**
     *
     * @param requirement
     */
    public Task(Requirement requirement) {
		setRequirement(requirement);
		// setLabel("New Task");
		setRemainingWork(INIT_EFFORT);
	}

    /**
     *
     * @param data
     */
    public Task(HashMap<String, Object> data) {
		super(data);
	}

    /**
     *
     * @return
     */
    public boolean isInCurrentSprint() {
		return getRequirement().isInCurrentSprint();
	}

//	@Override
//	public void updateLocalModificationTime() {
//		super.updateLocalModificationTime();
//		try {
//			Requirement requirement = getRequirement();
//			if (requirement != null) requirement.updateLocalModificationTime();
//		} catch (EntityDoesNotExistException ex) {
//			return;
//		}
//	}

    /**
     *
     * @return
     */
    
	public boolean isBlocked() {
		if (!isImpedimentSet()) {
                    return false;
        }
		return getImpediment().isOpen();
	}

    /**
     *
     */
    public void claim() {
		User user = Scope.get().getComponent(Auth.class).getUser();
		boolean ownerchange = !isOwner(user);
		if (isClosed()) {
			setUnDone(user);
		} else {
			setOwner(user);
		}
	}

    /**
     *
     * @param showOwner
     * @param showRequirement
     * @return
     */
    public String getLongLabel(boolean showOwner, boolean showRequirement) {
		StringBuilder sb = new StringBuilder();
		sb.append(getLabel());
		if (showOwner && isOwnerSet()) {
			sb.append(" (").append(getOwner().getName()).append(')');
		}
		if (showRequirement) {
			Requirement requirement = getRequirement();
			sb.append(" (").append(requirement.getReference()).append(" ").append(requirement.getLabel()).append(')');
		}
		return sb.toString();
	}

    /**
     *
     * @return
     */
    @Override
	public String getReference() {
		return REFERENCE_PREFIX + getNumber();
	}

    /**
     *
     * @param user
     */
    public void setDone(User user) {
		if (user == null) {
                    throw new IllegalArgumentException("a Task cannot be set done without claiming Task ownership");
        }
		setOwner(user);
		setRemainingWork(0);
	}

    /**
     *
     * @param user
     */
    public void setUnDone(User user) {
		setOwner(user);
		setRemainingWork(1);
		getRequirement().setClosed(false);
	}

    /**
     *
     */
    public void setUnOwned() {
		setOwner(null);
		getRequirement().setClosed(false);
	}

    /**
     *
     * @return
     */
    public boolean isClaimed() {
		return !isClosed() && isOwnerSet();
	}

    /**
     *
     * @return
     */
    public boolean isClosed() {
		return getRemainingWork() == 0;
	}

    /**
     *
     * @return
     */
    public String getWorkText() {
		String work;
		int burned = getBurnedWork();
		if (isClosed()) {
			work = String.valueOf(burned);
		} else {
			int remaining = getRemainingWork();
			if (isClaimed()) {
				int total = remaining + burned;
				work = burned + " of " + total;
			} else {
				work = String.valueOf(remaining);
			}
		}
		return work + " hrs";
	}

	@Override
	public String toHtml() {
		return ScrumGwt.toHtml(this, getLabel());
	}

	@Override
	public String toString() {
		return getReference();
	}

    /**
     *
     */
    public void incrementBurnedWork() {
		setBurnedWork(getBurnedWork() + 1);
	}

    /**
     *
     */
    public void decrementBurnedWork() {
        if (getBurnedWork() == 0) {
            return;
        }
		setBurnedWork(getBurnedWork() - 1);
	}

    /**
     *
     * @param burned
     */
    public void adjustRemainingWork(int burned) {
		int remaining = getRemainingWork();
                if (remaining == 0) {
            return;
        }
                remaining -= burned;
                if (remaining < 1) {
            remaining = 1;
        }
		setRemainingWork(remaining);
	}

    /**
     *
     */
    public void incrementRemainingWork() {
		setRemainingWork(getRemainingWork() + 1);
	}

    /**
     *
     */
    public void decrementRemainingWork() {
        int work = getRemainingWork();
		if (work <= 1) {
            return;
        }
		setRemainingWork(work - 1);
	}

    /**
     *
     * @param tasks
     * @return
     */
    public static int sumBurnedWork(Iterable<Task> tasks) {
		int sum = 0;
		for (Task task : tasks) {
			sum += task.getBurnedWork();
		}
		return sum;
	}

    /**
     *
     * @param tasks
     * @return
     */
    public static int sumRemainingWork(Iterable<Task> tasks) {
		int sum = 0;
		for (Task task : tasks) {
			sum += task.getRemainingWork();
		}
		return sum;
	}

    /**
     *
     * @return
     */
    public Project getProject() {
		return getRequirement().getProject();
	}

    /**
     *
     * @return
     */
    @Override
    public boolean isEditable() {
		if (getClosedInPastSprint() != null) {
                    return false;
        }
		if (!isInCurrentSprint()) {
            return false;
        }
		return getProject().isTeamMember(Scope.get().getComponent(Auth.class).getUser());
	}

    /**
     *
     * @return
     */
    @Override
	public Widget createForumItemWidget() {
		return new HyperlinkWidget(new ShowEntityAction(WhiteboardWidget.class, this, getLabel()));
	}

	private transient AFieldModel<String> ownerModel;

    /**
     *
     * @return
     */
        public AFieldModel<String> getOwnerModel() {
            if (ownerModel == null) {
                ownerModel = new AFieldModel<String>() {
                    
                    @Override
                    public String getValue() {
                        User owner = getOwner();
				return owner == null ? null : owner.getName();
			}
		};
        }
		return ownerModel;
	}

        private transient AFieldModel<String> workTextModel;
        
        /**
         *
         * @return
         */
        public AFieldModel<String> getWorkTextModel() {
		if (workTextModel == null) {
            workTextModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				return getWorkText();
			}
		};
        }
		return workTextModel;
	}

}
