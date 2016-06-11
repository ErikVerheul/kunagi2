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

import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.server.admin.User;
import scrum.server.common.Numbered;
import scrum.server.project.Project;

public class Task extends GTask implements Numbered, ReferenceSupport, LabelSupport {

	public String getReferenceAndLabel() {
		return getReference() + " " + getLabel();
	}

	@Override
	public String getReference() {
		return scrum.client.sprint.Task.REFERENCE_PREFIX + getNumber();
	}

	@Override
	public void updateNumber() {
		if (getNumber() == 0) setNumber(getRequirement().getProject().generateTaskNumber());
	}

	public boolean isProject(Project project) {
		return getRequirement().isProject(project);
	}

	public boolean isClosed() {
		return getRemainingWork() == 0;
	}

	public boolean isSprint(Sprint sprint) {
		if (isClosedInPastSprintSet()) return false;
		return getRequirement().isSprint(sprint);
	}

	public void reset() {
		setOwner(null);
		setBurnedWork(0);
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();
		updateNumber();
	}

	public Project getProject() {
		return getRequirement().getProject();
	}

	@Override
	public boolean isVisibleFor(User user) {
		return getProject().isVisibleFor(user);
	}

	@Override
	public String toString() {
		return getReferenceAndLabel();
	}

}
