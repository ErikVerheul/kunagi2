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
package scrum.client.project;

import ilarkesto.gwt.client.Gwt;

import java.util.List;

public class EstimationBar {

	private int sprintOffset;
	private List<Float> workPerSprint;

	public EstimationBar(int sprintOffset, List<Float> workPerSprint) {
		super();
		this.sprintOffset = sprintOffset;
		this.workPerSprint = workPerSprint;
	}

	public int getSprintOffset() {
		return sprintOffset;
	}

	public int getEndSprintOffset() {
		int offset = workPerSprint.isEmpty() ? 0 : workPerSprint.size() - 1;
		return sprintOffset + offset;
	}

	public List<Float> getWorkPerSprint() {
		return workPerSprint;
	}

	public boolean isCompetedOnSameSprint(EstimationBar previous) {
		return getEndSprintOffset() == previous.getEndSprintOffset();
	}

	@Override
	public int hashCode() {
		return workPerSprint.hashCode() * sprintOffset;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EstimationBar)) return false;
		EstimationBar other = (EstimationBar) obj;
		if (sprintOffset != other.sprintOffset) return false;
		return workPerSprint.equals(other.workPerSprint);
	}

	@Override
	public String toString() {
		return "EstimationBar(" + sprintOffset + ", " + Gwt.toString(workPerSprint) + ")";
	}

}
