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
package scrum.client.sprint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.base.Utl;
import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.Date;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.HyperlinkWidget;
import ilarkesto.gwt.client.editor.ATextEditorModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import scrum.client.ScrumGwt;
import scrum.client.admin.Auth;
import scrum.client.admin.User;
import scrum.client.collaboration.ForumSupport;
import scrum.client.collaboration.Wiki;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.common.ShowEntityAction;
import scrum.client.impediments.Impediment;
import scrum.client.project.Project;
import scrum.client.project.Requirement;
import scrum.client.sprint.SprintHistoryHelper.StoryInfo;
import scrum.client.sprint.SprintHistoryHelper.TaskInfo;
import scrum.client.tasks.WhiteboardWidget;

/**
 *
 *
 */
public class Sprint extends GSprint implements ForumSupport, ReferenceSupport, LabelSupport {

    /**
     *
     */
    public static final String REFERENCE_PREFIX = "spr";

	private transient Comparator<Task> tasksOrderComparator;
	private transient RequirementsOrderComparator requirementsOrderComparator;

    /**
     *
     * @param project
     * @param label
     */
    public Sprint(Project project, String label) {
		setProject(project);
		setLabel(label);
	}

    /**
     *
     * @param data
     */
    public Sprint(HashMap<String, Object> data) {
		super(data);
	}

    /**
     *
     */
    public void updateRequirementsOrder() {
		List<Requirement> requirements = getRequirements();
		Collections.sort(requirements, getRequirementsOrderComparator());
		updateRequirementsOrder(requirements);
	}

    /**
     *
     * @param requirements
     */
    public void updateRequirementsOrder(List<Requirement> requirements) {
		setRequirementsOrderIds(Gwt.getIdsAsList(requirements));
	}

    /**
     *
     * @return
     */
    public List<Requirement> getCompletedUnclosedRequirements() {
		List<Requirement> ret = new ArrayList<Requirement>();
		for (Requirement req : getRequirements()) {
			if (req.isTasksClosed() && !req.isClosed() && !req.isRejected()) {
                            ret.add(req);
            }
		}
		return ret;
	}

    /**
     *
     * @return
     */
    public Integer getLengthInDays() {
		TimePeriod lenght = getLength();
		return lenght == null ? null : lenght.toDays();
	}

    /**
     *
     * @return
     */
    public TimePeriod getLength() {
		Date begin = getBegin();
		Date end = getEnd();
		if (begin == null || end == null) {
                    return null;
        }
		return getBegin().getPeriodTo(getEnd()).addDays(1);
	}

    /**
     *
     * @param lenght
     */
    public void setLengthInDays(Integer lenght) {
        if (lenght == null || lenght <= 0) {
            return;
        }
		Date begin = getBegin();
		if (begin == null) {
			begin = getProject().getCurrentSprint().getEnd();
			setBegin(begin);
		}
		Date end = begin.addDays(lenght - 1);
		setEnd(end);
	}

    /**
     *
     * @param impediment
     * @return
     */
    public List<Task> getTasksBlockedBy(Impediment impediment) {
		List<Task> ret = new ArrayList<Task>();
		for (Requirement requirement : getRequirements()) {
			ret.addAll(requirement.getTasksBlockedBy(impediment));
		}
		return ret;
	}

    /**
     *
     * @param width
     * @param height
     * @return
     */
    public String getChartUrl(int width, int height) {
		return GWT.getModuleBaseURL() + "sprintBurndownChart.png?sprintId=" + getId() + "&width=" + width + "&height="
				+ height;
	}

    /**
     *
     * @return
     */
    public boolean isCompleted() {
		return getVelocity() != null;
	}

    /**
     *
     * @return
     */
    public float getEstimatedRequirementWork() {
		float sum = 0;
		for (Requirement requirement : getRequirements()) {
			Float work = requirement.getEstimatedWork();
                        if (work != null) {
                            sum += work;
            }
		}
		return sum;
	}

    /**
     *
     * @return
     */
    public float getCompletedRequirementWork() {
		float sum = 0;
                for (Requirement requirement : getRequirements()) {
			if (!requirement.isClosed()) {
                continue;
            }
                        Float work = requirement.getEstimatedWork();
			if (work != null) {
                sum += work;
            }
		}
		return sum;
	}

    /**
     *
     * @return
     */
    public List<Requirement> getDecidableUndecidedRequirements() {
		List<Requirement> ret = new ArrayList<Requirement>();
                for (Requirement requirement : getRequirements()) {
                    if (requirement.isDecidable() && !requirement.isClosed()) {
                ret.add(requirement);
            }
		}
		return ret;
	}

    /**
     *
     * @param sorted
     * @return
     */
    public List<Task> getUnclaimedTasks(boolean sorted) {
        List<Task> ret = new ArrayList<Task>();
        List<Requirement> requirements = getRequirements();
		if (sorted) {
            Collections.sort(requirements, getRequirementsOrderComparator());
        }
		for (Requirement requirement : requirements) {
			ret.addAll(requirement.getUnclaimedTasks());
		}
		return ret;
	}

    /**
     *
     * @param user
     * @return
     */
    public List<Task> getTasks(User user) {
		List<Task> ret = new ArrayList<Task>();
		for (Requirement requirement : getRequirements()) {
			for (Task task : requirement.getTasksInSprint()) {
				if (user == null) {
					if (!task.isOwnerSet()) {
						ret.add(task);
					}
				} else {
					if (task.isOwner(user)) {
						ret.add(task);
					}
				}
			}
		}
		return ret;
	}

    /**
     *
     * @param user
     * @return
     */
    public List<Task> getClaimedTasks(User user) {
		List<Task> ret = new ArrayList<Task>();
		for (Requirement requirement : getRequirements()) {
			ret.addAll(requirement.getClaimedTasks(user));
		}
		return ret;
	}

    /**
     *
     * @return
     */
    public int getBurnedWorkInClosedTasks() {
		int sum = 0;
		for (Requirement requirement : getRequirements()) {
			sum += requirement.getBurnedWorkInClosedTasks();
		}
		return sum;
	}

    /**
     *
     * @return
     */
    public int getBurnedWork() {
		return Requirement.sumBurnedWork(getRequirements());
	}

    /**
     *
     * @return
     */
    public int getBurnedWorkInClaimedTasks() {
		int sum = 0;
		for (Requirement requirement : getRequirements()) {
			sum += requirement.getBurnedWorkInClaimedTasks();
		}
		return sum;
	}

    /**
     *
     * @return
     */
    public int getRemainingWorkInClaimedTasks() {
		int sum = 0;
		for (Requirement requirement : getRequirements()) {
			sum += requirement.getRemainingWorkInClaimedTasks();
		}
		return sum;
	}

    /**
     *
     * @return
     */
    public int getRemainingWorkInUnclaimedTasks() {
		int sum = 0;
		for (Requirement requirement : getRequirements()) {
			sum += requirement.getRemainingWorkInUnclaimedTasks();
		}
		return sum;
	}

    /**
     *
     * @return
     */
    public int getRemainingWork() {
		int sum = 0;
		for (Requirement requirement : getRequirements()) {
			sum += requirement.getRemainingWork();
		}
		return sum;
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
	public boolean isEditable() {
		if (isCompleted()) {
            return false;
        }
		return getProject().isProductOwner(Scope.get().getComponent(Auth.class).getUser());
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isPlanningEditable() {
		return !isCompleted();
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isRetrospectiveEditable() {
		return getProject().isScrumMaster(Scope.get().getComponent(Auth.class).getUser());
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isReviewEditable() {
            return getProject().isProductOwner(Scope.get().getComponent(Auth.class).getUser());
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isDatesEditable() {
		if (isCompleted()) {
            return false;
        }
		return getProject().isProductOwner(Scope.get().getComponent(Auth.class).getUser());
	}

    /**
     *
     * @return
     */
    @Override
	public String getGoalTemplate() {
		return Scope.get().getComponent(Wiki.class).getTemplate("sprint.goal");
	}

    /**
     *
     * @return
     */
    @Override
	public String getPlanningNoteTemplate() {
		return Scope.get().getComponent(Wiki.class).getTemplate("sprint.planning");
	}

    /**
     *
     * @return
     */
    @Override
	public String getRetrospectiveNoteTemplate() {
		return Scope.get().getComponent(Wiki.class).getTemplate("sprint.retrospective");
	}

    /**
     *
     * @return
     */
    @Override
	public String getReviewNoteTemplate() {
		return Scope.get().getComponent(Wiki.class).getTemplate("sprint.review");
	}

    /**
     *
     * @return
     */
    public boolean isCurrent() {
		return getProject().isCurrentSprint(this);
	}

    /**
     *
     */
    public static final Comparator<Sprint> END_DATE_COMPARATOR = new Comparator<Sprint>() {

		@Override
		public int compare(Sprint a, Sprint b) {
			return Utl.compare(a.getEnd(), b.getEnd());
		}

	};

    /**
     *
     */
    public static final Comparator<Sprint> END_DATE_REVERSE_COMPARATOR = new Comparator<Sprint>() {

		@Override
		public int compare(Sprint a, Sprint b) {
			return Utl.compare(b.getEnd(), a.getEnd());
		}

	};

    /**
     *
     * @return
     */
    @Override
	public Widget createForumItemWidget() {
		String label = isCurrent() ? "Sprint Backlog" : "Sprint";
		return new HyperlinkWidget(new ShowEntityAction(isCurrent() ? WhiteboardWidget.class
				: SprintHistoryWidget.class, this, label));
	}

    /**
     *
     * @return
     */
    @Override
	public String getReference() {
		return REFERENCE_PREFIX + getNumber();
	}

        private transient LengthInDaysModel lengthInDaysModel;
        
        /**
         *
         * @return
     */
    public LengthInDaysModel getLengthInDaysModel() {
		if (lengthInDaysModel == null) {
            lengthInDaysModel = new LengthInDaysModel();
        }
                return lengthInDaysModel;
    }
    
    /**
     *
     * @return
     */
    public RequirementsOrderComparator getRequirementsOrderComparator() {
        if (requirementsOrderComparator == null) {
            requirementsOrderComparator = new RequirementsOrderComparator() {

			@Override
			protected List<String> getOrder() {
                            return getRequirementsOrderIds();
                        }
            };
        }
		return requirementsOrderComparator;
	}

    /**
     *
     * @return
     */
    public Comparator<Task> getTasksOrderComparator() {
        if (tasksOrderComparator == null) {
            tasksOrderComparator = new Comparator<Task>() {
                @Override
                public int compare(Task a, Task b) {
                    Requirement ar = a.getRequirement();
                    Requirement br = b.getRequirement();
                    if (ar != br) {
                        return getRequirementsOrderComparator().compare(ar, br);
                    }
                    List<String> order = ar.getTasksOrderIds();
                    int additional = order.size();
                    int ia = order.indexOf(a.getId());
                    if (ia < 0) {
					ia = additional;
					additional++;
				}
                    int ib = order.indexOf(b.getId());
                    if (ib < 0) {
					ib = additional;
					additional++;
				}
                    return ia - ib;
                }
            };
        }
		return tasksOrderComparator;
	}

    /**
     *
     */
    protected class LengthInDaysModel extends ilarkesto.gwt.client.editor.AIntegerEditorModel {

		@Override
		public String getId() {
			return "Sprint_lengthInDays";
		}

		@Override
		public java.lang.Integer getValue() {
                    Integer length = getLengthInDays();
                    return length == null || length <= 0 ? null : length;
		}

		@Override
		public void setValue(java.lang.Integer value) {
			setLengthInDays(value);
                }
                
                @Override
		public void increment() {
			Integer length = getValue();
			if (length == null) {
                length = 0;
            }
			setLengthInDays(length + 1);
		}

		@Override
		public void decrement() {
			Integer lenght = getValue();
			if (lenght == null || lenght < 2) {
                return;
            }
			setLengthInDays(lenght - 1);
		}

		@Override
		public boolean isEditable() {
			return Sprint.this.isEditable();
		}

		@Override
		public boolean isMandatory() {
			return true;
		}

		@Override
		public String getTooltip() {
			return "The lenght of the sprint in days.";
		}

		@Override
                protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
                    super.onChangeValue(oldValue, newValue);
                    addUndo(this, oldValue);
                }
                
    }
    
    private transient ATextEditorModel completedRequirementLabelsModel;
    
    /**
     *
     * @return
     */
    public ATextEditorModel getCompletedRequirementLabelsModel() {
        if (completedRequirementLabelsModel == null) {
            completedRequirementLabelsModel = new ATextEditorModel() {
                
                @Override
                public String getValue() {
                    StringBuilder sb = new StringBuilder();
                    List<StoryInfo> stories = SprintHistoryHelper.parseRequirementsAndTasks(getCompletedRequirementsData());
                    for (StoryInfo story : stories) {
                        sb.append("\n* ").append(story.getReference()).append(" ").append(story.getLabel());
                        sb.append(" ''").append(story.getEstimatedWorkAsString()).append(", ")
                                .append(story.getBurnedWorkAsString()).append("''");
                        for (TaskInfo task : story.getTasks()) {
						sb.append("\n  * ").append(task.getReference()).append(" ").append(task.getLabel());
						sb.append(" ''").append(task.getBurnedWork()).append(" hrs.''");
					}
				}
				return sb.toString();
			}

			@Override
			public void setValue(String value) {}

			@Override
			public boolean isEditable() {
				return false;
			}
		};
        }
		return completedRequirementLabelsModel;
	}

    /**
     *
     * @return
     */
    public String getReferenceAndLabel() {
		return getReference() + " " + getLabel();
	}
}
