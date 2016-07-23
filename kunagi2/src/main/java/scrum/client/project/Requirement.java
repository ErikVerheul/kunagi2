/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.project;

import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.KunagiProperties;
import ilarkesto.core.base.Str;
import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.Date;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.HyperlinkWidget;
import ilarkesto.gwt.client.editor.AFieldModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import scrum.client.ScrumGwt;
import scrum.client.admin.Auth;
import scrum.client.admin.User;
import scrum.client.collaboration.ForumSupport;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.common.ShowEntityAction;
import scrum.client.common.ThemesContainer;
import scrum.client.estimation.RequirementEstimationVote;
import scrum.client.impediments.Impediment;
import scrum.client.issues.Issue;
import scrum.client.journal.Change;
import scrum.client.sprint.Sprint;
import scrum.client.sprint.Task;
import scrum.client.tasks.WhiteboardWidget;

/**
 *
 * @author erik
 */
public class Requirement extends GRequirement implements ReferenceSupport, LabelSupport, ForumSupport, ThemesContainer {

    /**
     *
     */
    public static final String REFERENCE_PREFIX = "sto";
	private static final String[] WORK_ESTIMATION_VALUES = new String[] { "", "0", "0.5", "1", "2", "3", "5", "8", "13", "20", "40", "100" };
	private static final Float[] WORK_ESTIMATION_FLOAT_VALUES = new Float[] { 0.5f, 0f, 1f, 2f, 3f, 5f, 8f, 13f, 20f, 40f, 100f };

	private transient EstimationBar estimationBar;
	private transient AFieldModel<String> taskStatusLabelModel;
	private transient AFieldModel<String> themesAsStringModel;
	private transient AFieldModel<String> estimatedWorkWithUnitModel;
        
    /**
     *
     * @return
     */
    public static String[] getWorkEstimationValues() {
            return makeCopy(WORK_ESTIMATION_VALUES);
        }
        
    /**
     *
     * @return
     */
    public static Float[] getWorkEstimationFloatValues() {
            return makeCopy(WORK_ESTIMATION_FLOAT_VALUES);
        }

    /**
     *
     * @param project
     */
    public Requirement(Project project) {
		setProject(project);
		setDirty(true);
	}

    /**
     *
     * @param data
     */
    public Requirement(KunagiProperties data) {
		super(data);
	}
        
        private static String[] makeCopy(String[] orig) {
            String[] copy = new String[orig.length];
            System.arraycopy(orig, 0, copy, 0, orig.length);
            return copy;
        }
        
        private static Float[] makeCopy(Float[] orig) {
            Float[] copy = new Float[orig.length];
            System.arraycopy(orig, 0, copy, 0, orig.length);
            return copy;
        }

    /**
     *
     * @param sprint
     * @return
     */
    public String getHistoryLabel(final Sprint sprint) {
		List<Change> changes = getDao().getChangesByParent(Requirement.this);
		for (Change change : changes) {
			String key = change.getKey();
			if (!change.isNewValue(sprint.getId())) {
                            continue;
            }
			if (Change.REQ_COMPLETED_IN_SPRINT.equals(key) || Change.REQ_REJECTED_IN_SPRINT.equals(key)) {
                            return change.getOldValue();
            }
		}
		return getLabel();
	}

    /**
     *
     * @return
     */
    public boolean isBlocked() {
		return getImpediment() != null;
	}

    /**
     *
     * @return
     */
    public Impediment getImpediment() {
		for (Task task : getTasksInSprint()) {
                    if (task.isBlocked()) {
                        return task.getImpediment();
            }
		}
		return null;
	}

    /**
     *
     * @return
     */
    public Set<Impediment> getImpediments() {
		Set<Impediment> impediments = new HashSet<Impediment>();
                for (Task task : getTasksInSprint()) {
                    if (task.isBlocked()) {
                        impediments.add(task.getImpediment());
            }
		}
		return impediments;
	}

    /**
     *
     * @param theme
     */
    public void addTheme(String theme) {
        List<String> themes = getThemes();
		if (!themes.contains(theme)) {
            themes.add(theme);
        }
		setThemes(themes);
	}

    /**
     *
     * @return
     */
    @Override
	public List<String> getAvailableThemes() {
		return getProject().getThemes();
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isThemesEditable() {
		return getLabelModel().isEditable();
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isThemesCreatable() {
		return ScrumGwt.isCurrentUserProductOwner();
	}

    /**
     *
     * @return
     */
    public List<Requirement> getRelatedRequirements() {
		List<Requirement> ret = getProject().getRequirementsByThemes(getThemes());
		ret.remove(this);
		return ret;
	}

    /**
     *
     * @return
     */
    public List<Issue> getRelatedIssues() {
		return getProject().getIssuesByThemes(getThemes());
	}

    /**
     *
     */
    public void removeFromSprint() {
		setSprint(null);
		for (Task task : getTasksInSprint()) {
			task.setOwner(null);
			task.setBurnedWork(0);
		}
	}

    /**
     *
     * @return
     */
    public List<Task> getTasksInSprint() {
		return getTasksInSprint(getProject().getCurrentSprint());
	}

    /**
     *
     * @param sprint
     * @return
     */
    public List<Task> getTasksInSprint(Sprint sprint) {
		List<Task> tasks = getTasks();
		Iterator<Task> iterator = tasks.iterator();
		while (iterator.hasNext()) {
                    Task task = iterator.next();
                    if (task.isClosedInPastSprintSet() || !sprint.equals(task.getSprint())) {
                iterator.remove();
            }
		}
                return tasks;
    }
    
    /**
     *
     * @return
     */
    public boolean isDecidable() {
		if (getRejectDate() != null) {
                    return false;
                }
		return isTasksClosed();
	}

    /**
     *
     * @return
     */
    public boolean isRejected() {
        if (isClosed()) {
            return false;
        }
		if (!isTasksClosed()) {
            return false;
        }
		if (!isInCurrentSprint()) {
            return false;
        }
		return getRejectDate() != null;
	}

    /**
     *
     */
    public void reject() {
		setRejectDate(Date.today());
	}

    /**
     *
     */
    public void fix() {
		setRejectDate(null);
	}

    /**
     *
     * @return
     */
    public String getEstimatedWorkAsString() {
		return ScrumGwt.getEstimationAsString(getEstimatedWork());
	}

    /**
     *
     * @return
     */
    public String getEstimatedWorkWithUnit() {
		return ScrumGwt.getEstimationAsString(getEstimatedWork(), getProject().getEffortUnit());
	}

    /**
     *
     * @return
     */
    public List<RequirementEstimationVote> getEstimationVotes() {
		return getDao().getRequirementEstimationVotesByRequirement(this);
	}

    /**
     *
     * @return
     */
    public boolean containsWorkEstimationVotes() {
		for (RequirementEstimationVote vote : getEstimationVotes()) {
			if (vote.getEstimatedWork() != null) {
                return true;
            }
		}
		return false;
	}

    /**
     *
     * @param user
     * @return
     */
    public RequirementEstimationVote getEstimationVote(User user) {
		for (RequirementEstimationVote vote : getEstimationVotes()) {
			if (vote.isUser(user)) {
                return vote;
            }
		}
		return null;
	}

    /**
     *
     * @param estimatedWork
     */
    public void setVote(Float estimatedWork) {
        RequirementEstimationVote vote = getEstimationVote(Scope.get().getComponent(Auth.class).getUser());
		if (vote == null) {
                    throw new IllegalStateException("vote == null");
        }
		vote.setEstimatedWork(estimatedWork);
		if (estimatedWork != null && isWorkEstimationVotingComplete()) {
            activateWorkEstimationVotingShowoff();
        }
		updateLocalModificationTime();
	}

    /**
     *
     * @return
     */
    public boolean isWorkEstimationVotingComplete() {
        for (User user : getProject().getTeamMembers()) {
			RequirementEstimationVote vote = getEstimationVote(user);
			if (vote == null || vote.getEstimatedWork() == null) {
                return false;
            }
		}
		return true;
	}

    /**
     *
     */
    public void deactivateWorkEstimationVoting() {
		setWorkEstimationVotingActive(false);
	}

    /**
     *
     */
    public void activateWorkEstimationVotingShowoff() {
		setWorkEstimationVotingShowoff(true);
	}

    /**
     *
     * @return
     */
    public String getTaskStatusLabel() {
        List<Task> tasks = getTasksInSprint();
        int burned = Task.sumBurnedWork(tasks);
		int remaining = Task.sumRemainingWork(getTasksInSprint());
		if (remaining == 0) {
            return tasks.isEmpty() ? "no tasks planned yet" : "100% completed, " + burned + " hrs burned";
        }
		int burnedPercent = Gwt.percent(burned + remaining, burned);
		return burnedPercent + "% completed, " + remaining + " hrs left";
	}

    /**
     *
     * @param estimationBar
     */
    public void setEstimationBar(EstimationBar estimationBar) {
		this.estimationBar = estimationBar;
		updateLocalModificationTime();
	}

    /**
     *
     * @return
     */
    public EstimationBar getEstimationBar() {
		return estimationBar;
	}

    /**
     *
     * @return
     */
    public boolean isValidForSprint() {
		return isEstimatedWorkValid();
	}

    /**
     *
     * @return
     */
    public boolean isEstimatedWorkValid() {
        return !isDirty() && getEstimatedWork() != null;
    }
    
    /**
     *
     * @return
     */
    public String getLongLabel() {
        StringBuilder sb = new StringBuilder();
		sb.append(getLabel());
		if (!isEstimatedWorkValid()) {
            sb.append(" [requires estimation]");
        }
		if (isInCurrentSprint()) {
            sb.append(" [In Sprint]");
        }
		return sb.toString();
	}

    /**
     *
     * @return
     */
    public boolean isInCurrentSprint() {
		return isSprintSet() && getProject().isCurrentSprint(getSprint());
	}

    /**
     *
     * @return
     */
    public String getReferenceAndLabel() {
		return getReference() + " " + getLabel();
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
	 * No tasks created yet.
     * @return 
	 */
	public boolean isPlanned() {
            return !getTasksInSprint().isEmpty();
	}

	/**
	 * All tasks are done. Not closed yet.
         * @return 
	 */
	public boolean isTasksClosed() {
		Collection<Task> tasks = getTasksInSprint();
		if (tasks.isEmpty()) {
            return false;
        }
		for (Task task : tasks) {
			if (!task.isClosed()) {
                return false;
                        }
                }
                return true;
        }
        
        /**
         * Summary to show in the product backlog.
     * @return 
	 */
        public String getProductBacklogSummary() {
            String summary = isDirty() ? "[dirty] " : "[not dirty] ";
            if (isClosed()) {
                return summary += "Closed.";
        }
		if (isTasksClosed()) {
            return summary += "Done. Test required.";
        }
		if (getEstimatedWork() == null) {
            return summary += "No effort estimated.";
        }
		if (!isSprintSet()) {
                    return summary += getEstimatedWorkWithUnit() + " to do. No sprint assigned.";
                }
                Sprint sprint = getSprint();
                return summary += getEstimatedWorkWithUnit() + " to do in sprint " + sprint.getLabel() + ".";
	}

	/**
	 * Summary to show in the sprint backlog.
     * @return 
	 */
	public String getSprintBacklogSummary() {
		if (isClosed()) {
            return "Closed.";
        }
		if (!isPlanned()) {
            return "Not planned yet.";
        }
		if (isTasksClosed()) {
            return "Done. Test required.";
        }
		int taskCount = 0;
		int openTaskCount = 0;
		int effort = 0;
		for (Task task : getTasksInSprint()) {
			taskCount++;
			if (!task.isClosed()) {
				openTaskCount++;
				effort += task.getRemainingWork();
			}
		}
		return openTaskCount + " of " + taskCount + " Tasks open. About " + effort + " hours to do.";
	}

    /**
     *
     * @return
     */
    public int getBurnedWorkInClosedTasks() {
		return Task.sumBurnedWork(getClosedTasks());
	}

    /**
     *
     * @return
     */
    public int getBurnedWork() {
		return Task.sumBurnedWork(getTasksInSprint());
	}

    /**
     *
     * @return
     */
    public int getBurnedWorkInClaimedTasks() {
		return Task.sumBurnedWork(getClaimedTasks());
	}

    /**
     *
     * @return
     */
    public int getRemainingWorkInClaimedTasks() {
		return Task.sumRemainingWork(getClaimedTasks());
	}

    /**
     *
     * @return
     */
    public int getRemainingWorkInUnclaimedTasks() {
        return Task.sumRemainingWork(getUnclaimedTasks());
	}

    /**
     *
     * @return
     */
    public int getRemainingWork() {
		return Task.sumRemainingWork(getTasksInSprint());
	}

    /**
     *
     * @return
     */
    public List<Task> getClaimedTasks() {
		List<Task> ret = new ArrayList<Task>();
                for (Task task : getTasksInSprint()) {
                    if (task.isOwnerSet() && !task.isClosed()) {
                ret.add(task);
            }
		}
		return ret;
	}

    /**
     *
     * @param owner
     * @return
     */
    public List<Task> getClaimedTasks(User owner) {
        List<Task> ret = new ArrayList<Task>();
		for (Task task : getTasksInSprint()) {
			if (task.isOwner(owner) && !task.isClosed()) {
                ret.add(task);
            }
		}
		return ret;
	}

    /**
     *
     * @return
     */
    public List<Task> getClosedTasks() {
		List<Task> ret = new ArrayList<Task>();
		for (Task task : getTasksInSprint()) {
			if (task.isClosed()) {
                ret.add(task);
            }
		}
		return ret;
	}

    /**
     *
     * @return
     */
    public List<Task> getUnclaimedTasks() {
		List<Task> ret = new ArrayList<Task>();
                for (Task task : getTasksInSprint()) {
			if (task.isClosed() || task.isOwnerSet()) {
                continue;
            }
			ret.add(task);
		}
		return ret;
	}

    /**
     *
     * @param impediment
     * @return
     */
    public List<Task> getTasksBlockedBy(Impediment impediment) {
		List<Task> ret = new ArrayList<Task>();
		for (Task task : getTasksInSprint()) {
			if (task.isImpediment(impediment)) {
                ret.add(task);
            }
		}
		return ret;
	}

    /**
     *
     * @param requirements
     * @return
     */
    public static int sumBurnedWork(Iterable<Requirement> requirements) {
		int sum = 0;
		for (Requirement requirement : requirements) {
                    sum += requirement.getBurnedWork();
		}
		return sum;
	}

    /**
     *
     * @return
     */
    public Task createNewTask() {
		Task task = new Task(this);
		getDao().createTask(task);
		updateTasksOrder();
		return task;
	}

    /**
     *
     * @param task
     */
    public void deleteTask(Task task) {
		getDao().deleteTask(task);
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isEditable() {
		if (isClosed()) {
            return false;
        }
		if (isInCurrentSprint()) {
            return false;
        }
		return getProject().isProductOwner(Scope.get().getComponent(Auth.class).getUser());
	}

	@Override
	public String toHtml() {
		return ScrumGwt.toHtml(this, getLabel());
	}

	@Override
	public String toString() {
		return getReferenceAndLabel();
	}

    /**
     *
     * @return
     */
    @Override
	public Widget createForumItemWidget() {
		return new HyperlinkWidget(new ShowEntityAction(isInCurrentSprint() ? WhiteboardWidget.class
				: ProductBacklogWidget.class, this, getLabel()));
	}

	private void updateTasksOrder() {
		List<Task> tasks = getTasksInSprint();
		Collections.sort(tasks, getTasksOrderComparator());
		updateTasksOrder(tasks);
	}

    /**
     *
     * @param tasks
     */
    public void updateTasksOrder(List<Task> tasks) {
        setTasksOrderIds(Gwt.getIdsAsList(tasks));
    }
    
    /**
     *
     * @return
     */
    public String getThemesAsString() {
        return Str.concat(getThemes(), ", ");
    }
    
    /**
     *
     * @return
     */
    public Comparator<Task> getTasksOrderComparator() {
		return getSprint().getTasksOrderComparator();
	}

    /**
     *
     * @return
     */
    public AFieldModel<String> getEstimatedWorkWithUnitModel() {
        if (estimatedWorkWithUnitModel == null) {
            estimatedWorkWithUnitModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				return getEstimatedWorkWithUnit();
			}
		};
        }
        return estimatedWorkWithUnitModel;
    }
    
    /**
     *
     * @return
     */
    public AFieldModel<String> getTaskStatusLabelModel() {
        if (taskStatusLabelModel == null) {
            taskStatusLabelModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				return getTaskStatusLabel();
			}
		};
        }
		return taskStatusLabelModel;
	}

    /**
     *
     * @return
     */
    public AFieldModel<String> getThemesAsStringModel() {
		if (themesAsStringModel == null) {
            themesAsStringModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				return getThemesAsString();
			}
		};
        }
		return themesAsStringModel;
	}

    /**
     *
     * @param sprint
     * @return
     */
    public AFieldModel<String> getHistoryLabelModel(final Sprint sprint) {
		return new AFieldModel<String>() {

			@Override
			public String getValue() {
				return getHistoryLabel(sprint);
			}
		};
	}

}
