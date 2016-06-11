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
package scrum.server.sprint;

import ilarkesto.base.StrExtend;
import ilarkesto.base.UtlExtend;
import ilarkesto.core.scope.In;
import ilarkesto.core.time.Date;
import ilarkesto.fp.Predicate;

import java.util.Arrays;
import scrum.server.project.Project;
import scrum.server.project.Requirement;
import scrum.server.project.RequirementDao;

public class SprintDao extends GSprintDao {

	@In
	private RequirementDao requirementDao;

	@In
	private TaskDao taskDao;

	public Sprint getSprintByNumber(final int number, final Project project) {
		return getEntity(new Predicate<Sprint>() {

			@Override
			public boolean test(Sprint sprint) {
				return sprint.isNumber(number) && sprint.isProject(project);
			}
		});
	}

	@Override
	public Sprint newEntityInstance() {
		Sprint sprint = super.newEntityInstance();
		sprint.setLabel("New Sprint");
		return sprint;
	}

	// --- test data ---

	public Sprint createTestSprint(Project project) {
		Date begin = Date.beforeDays(15);
		Date end = Date.inDays(15);

		Sprint sprint = newEntityInstance();
		sprint.setProject(project);
		sprint.setLabel("Our first Sprint!");
		sprint.setBegin(begin);
		sprint.setEnd(end);
		if (end.isPast()) sprint.setVelocity(20f);
		saveEntity(sprint);

		project.setCurrentSprint(sprint);

		return sprint;
	}

	public void createTestHistorySprint(Project project, Date begin, Date end) {
		Sprint sprint = newEntityInstance();
		sprint.setProject(project);
		sprint.setLabel(StrExtend.generateRandomSentence());
		sprint.setBegin(begin);
		sprint.setEnd(end);

		for (int i = 0; i < UtlExtend.randomInt(2, 10); i++) {
			Requirement requirement = requirementDao.postRequirement(project, StrExtend.generateRandomSentence(),
				UtlExtend.randomElement(Arrays.asList(scrum.client.project.Requirement.getWorkEstimationFloatValues())));
			requirement.setSprint(sprint);
			for (int j = 0; j < UtlExtend.randomInt(2, 5); j++) {
				Task task = taskDao.postTask(requirement, StrExtend.generateRandomSentence(), 0);
				task.setBurnedWork(UtlExtend.randomInt(2, 10));
			}
			if (i == 0) {
				taskDao.postTask(requirement, "Incomplete task", 1);
			} else {
				requirement.setClosed(true);
			}
		}

		sprint.close();

		saveEntity(sprint);
	}
}
