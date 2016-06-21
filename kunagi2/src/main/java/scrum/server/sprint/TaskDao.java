/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.server.sprint;

import ilarkesto.fp.Predicate;

import java.util.Set;

import scrum.server.project.Project;
import scrum.server.project.Requirement;

public class TaskDao extends GTaskDao {

	public Set<Task> getTasksByProject(final Project project) {
		return getEntities(new Predicate<Task>() {

                        @Override
			public boolean test(Task t) {
				return t.isProject(project);
			}
		});
	}

	public Task getTaskByNumber(final int number, final Project project) {
		return getEntity(new Predicate<Task>() {

                        @Override
			public boolean test(Task t) {
				return t.isNumber(number) && t.isProject(project);
			}
		});
	}

	@Override
	public Task newEntityInstance() {
		Task task = super.newEntityInstance();
		task.setRemainingWork(scrum.client.sprint.Task.INIT_EFFORT);
		return task;
	}

	public Set<Task> getTasksBySprint(final Sprint sprint) {
		return getEntities(new Predicate<Task>() {

                        @Override
			public boolean test(Task task) {
				return task.isSprint(sprint);
			}
		});
	}

	// --- test data ---

	public Task postTask(Requirement requirement, String label, int work) {
		Task task = newEntityInstance();
		task.setRequirement(requirement);
		task.setLabel(label);
		task.setRemainingWork(work);
		saveEntity(task);
		return task;
	}
}
