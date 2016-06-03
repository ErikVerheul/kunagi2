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

import java.util.ArrayList;
import java.util.List;

public class EstimationBarFactory {

	public static void createEstimationBars(List<Requirement> requirements, Integer velocity) {
		int sprintOffset = 0;
		float remainingCapacityInSprint = velocity == null ? 0 : velocity;
		boolean unestimatedRequirementReached = false;
		for (Requirement requirement : requirements) {
			Float workInRequirement = requirement.getEstimatedWork();
			List<Float> requirementWorkPerSprint = new ArrayList<Float>();

			if (velocity == null || velocity == 0) {
				// no projection bars -> display as all work done in one sprint
				if (workInRequirement != null) requirementWorkPerSprint.add(workInRequirement);
				requirement.setEstimationBar(new EstimationBar(0, requirementWorkPerSprint));
				continue;
			}

			if (workInRequirement == null && !unestimatedRequirementReached) {
				// unestimated Requirement just reached
				unestimatedRequirementReached = true;
				sprintOffset++;
			}

			if (workInRequirement == null) {
				// no estimation -> empty bar
				requirement.setEstimationBar(new EstimationBar(sprintOffset, requirementWorkPerSprint));
				continue;
			}

			float work = workInRequirement;

			if (work > 0 && remainingCapacityInSprint == 0) {
				if (!unestimatedRequirementReached) sprintOffset++;
				remainingCapacityInSprint = velocity;
			}

			while (work > remainingCapacityInSprint) {
				// work is more then capacity in sprint -> add indicator to bar
				requirementWorkPerSprint.add(remainingCapacityInSprint);
				work -= remainingCapacityInSprint;
				if (!unestimatedRequirementReached) sprintOffset++;
				remainingCapacityInSprint = velocity;
			}

			// add remaining work to bar
			requirementWorkPerSprint.add(work);
			remainingCapacityInSprint -= work;

			requirement.setEstimationBar(new EstimationBar(sprintOffset - requirementWorkPerSprint.size() + 1,
					requirementWorkPerSprint));

		}

	}
}
