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
package scrum.server.journal;

import ilarkesto.core.time.DateAndTime;
import ilarkesto.persistence.AEntity;
import scrum.server.project.Project;

public class ProjectEventDao extends GProjectEventDao {

	@Override
	public ProjectEvent newEntityInstance() {
		ProjectEvent event = super.newEntityInstance();
		event.setDateAndTime(DateAndTime.now());
		return event;
	}

	public ProjectEvent postEvent(Project project, String label, AEntity subject) {
		ProjectEvent event = newEntityInstance();
		event.setProject(project);
		event.setLabel(label);
		event.setSubject(subject);
		saveEntity(event);
		return event;
	}

}