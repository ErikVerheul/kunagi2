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
package scrum.server.project;

import ilarkesto.fp.Predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectSprintSnapshotDao extends GProjectSprintSnapshotDao {

	public List<ProjectSprintSnapshot> getProjectSprintSnapshotsByProject(final Project project) {
		List<ProjectSprintSnapshot> ret = new ArrayList<ProjectSprintSnapshot>(
				getEntities(new Predicate<ProjectSprintSnapshot>() {

                                        @Override
					public boolean test(ProjectSprintSnapshot e) {
						return e.isProject(project);
					}
				}));
		Collections.sort(ret);
		return ret;
	}

}
