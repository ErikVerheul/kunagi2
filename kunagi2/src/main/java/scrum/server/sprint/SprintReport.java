package scrum.server.sprint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import scrum.server.admin.User;
import scrum.server.journal.Change;
import scrum.server.project.Requirement;

public class SprintReport extends GSprintReport {

	public Set<Change> getSprintSwitchRequirementChanges() {
		Set<Change> changes = new HashSet<Change>();
		for (Requirement requirement : getCompletedRequirements()) {
			changes.addAll(requirement.getSprintSwitchChanges());
		}
		for (Requirement requirement : getRejectedRequirements()) {
			changes.addAll(requirement.getSprintSwitchChanges());
		}
		return changes;
	}

	public List<Requirement> getCompletedRequirementsAsList() {
		List<Requirement> requirements = new ArrayList<Requirement>(getCompletedRequirements());
		Collections.sort(requirements, getRequirementsOrderComparator());
		return requirements;
	}

	public List<Requirement> getRejectedRequirementsAsList() {
		List<Requirement> requirements = new ArrayList<Requirement>(getRejectedRequirements());
		Collections.sort(requirements, getRequirementsOrderComparator());
		return requirements;
	}

	@Override
	public boolean isVisibleFor(User user) {
		return getSprint().isVisibleFor(user);
	}

	@Override
	public String toString() {
		Sprint sprint = getSprint();
		return sprint == null ? "! sprint==null" : sprint.toString();
	}

	private transient Comparator<Requirement> requirementsOrderComparator;

	public Comparator<Requirement> getRequirementsOrderComparator() {
		if (requirementsOrderComparator == null) requirementsOrderComparator = new Comparator<Requirement>() {

			@Override
			public int compare(Requirement a, Requirement b) {
				List<String> order = getRequirementsOrderIds();
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
		return requirementsOrderComparator;
	}

	public Set<Task> getClosedTasks(Requirement requirement) {
		Set<Task> tasks = new HashSet<Task>();
		for (Task task : getClosedTasks()) {
			if (task.isRequirement(requirement)) tasks.add(task);
		}
		return tasks;
	}

	public Set<Task> getOpenTasks(Requirement requirement) {
		Set<Task> tasks = new HashSet<Task>();
		for (Task task : getOpenTasks()) {
			if (task.isRequirement(requirement)) tasks.add(task);
		}
		return tasks;
	}

}