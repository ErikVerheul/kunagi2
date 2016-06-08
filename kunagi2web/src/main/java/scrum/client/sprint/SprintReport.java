package scrum.client.sprint;

import generated.scrum.client.sprint.GSprintReport;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import scrum.client.project.Requirement;

public class SprintReport extends GSprintReport {

	private transient RequirementsOrderComparator requirementsOrderComparator;

	public SprintReport(Map data) {
		super(data);
	}

	public Set<Requirement> getAllRequirements() {
		Set<Requirement> requirements = new HashSet<Requirement>();
		requirements.addAll(getCompletedRequirements());
		requirements.addAll(getRejectedRequirements());
		return requirements;
	}

	public Set<Task> getClosedTasks(Requirement requirement) {
		Set<Task> tasks = getClosedTasks();
		Iterator<Task> iterator = tasks.iterator();
		while (iterator.hasNext()) {
			Task task = iterator.next();
			if (!requirement.equals(task.getRequirement())) iterator.remove();
		}
		return tasks;
	}

	public Set<Task> getOpenTasks(Requirement requirement) {
		Set<Task> tasks = getOpenTasks();
		Iterator<Task> iterator = tasks.iterator();
		while (iterator.hasNext()) {
			Task task = iterator.next();
			if (!requirement.equals(task.getRequirement())) iterator.remove();
		}
		return tasks;
	}

	public RequirementsOrderComparator getRequirementsOrderComparator() {
		if (requirementsOrderComparator == null) requirementsOrderComparator = new RequirementsOrderComparator() {

			@Override
			protected List<String> getOrder() {
				return getRequirementsOrderIds();
			}

		};
		return requirementsOrderComparator;
	}

}