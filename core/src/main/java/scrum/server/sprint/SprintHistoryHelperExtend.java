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

import java.util.Collection;
import scrum.server.project.Requirement;

public class SprintHistoryHelperExtend extends scrum.client.sprint.SprintHistoryHelper {

	public static String encodeRequirementsAndTasks(Collection<Requirement> requirements) {
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX).append(VERSION).append("\n");
		for (Requirement req : requirements) {
			sb.append(encodeRequirement(req)).append("\n");
			for (Task task : req.getTasksInSprint()) {
				sb.append(encodeTask(task)).append("\n");
			}
		}
		return sb.toString();
	}

	static String encodeRequirement(Requirement req) {
		StringBuilder sb = new StringBuilder();
		sb.append(req.getReference()).append(SEPARATOR);
		sb.append(req.getEstimatedWorkAsString()).append(SEPARATOR);
		sb.append(req.getLabel());
		return sb.toString();
	}

	static String encodeTask(Task tsk) {
		StringBuilder sb = new StringBuilder();
		sb.append(tsk.getReference()).append(SEPARATOR);
		sb.append(tsk.getBurnedWork()).append(SEPARATOR);
		sb.append(tsk.getRemainingWork()).append(SEPARATOR);
		sb.append(tsk.getLabel());
		return sb.toString();
	}

}
