package scrum.client.sprint;

import ilarkesto.core.base.KunagiProperties;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import scrum.client.project.Requirement;

/**
 *
 * @author erik
 */
public class SprintReport extends GSprintReport {

	private transient RequirementsOrderComparator requirementsOrderComparator;

    /**
     *
     * @param data
     */
    public SprintReport(KunagiProperties data) {
		super(data);
	}

    /**
     *
     * @return
     */
    public Set<Requirement> getAllRequirements() {
		Set<Requirement> requirements = new HashSet<>();
		requirements.addAll(getCompletedRequirements());
		requirements.addAll(getRejectedRequirements());
		return requirements;
	}

    /**
     *
     * @param requirement
     * @return
     */
    public Set<Task> getClosedTasks(Requirement requirement) {
		Set<Task> tasks = getClosedTasks();
		Iterator<Task> iterator = tasks.iterator();
		while (iterator.hasNext()) {
			Task task = iterator.next();
			if (!requirement.equals(task.getRequirement())) {
                            iterator.remove();
            }
		}
		return tasks;
	}

    /**
     *
     * @param requirement
     * @return
     */
    public Set<Task> getOpenTasks(Requirement requirement) {
		Set<Task> tasks = getOpenTasks();
		Iterator<Task> iterator = tasks.iterator();
		while (iterator.hasNext()) {
			Task task = iterator.next();
			if (!requirement.equals(task.getRequirement())) {
                            iterator.remove();
            }
		}
		return tasks;
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

}