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
package scrum.server.admin;

import ilarkesto.fp.Predicate;
import scrum.server.project.Project;

public class ProjectUserConfigDao extends GProjectUserConfigDao {

	public ProjectUserConfig getProjectUserConfig(final Project project, final User user) {
		ProjectUserConfig projectUserConfig = getEntity(new Predicate<ProjectUserConfig>() {

                        @Override
			public boolean test(ProjectUserConfig e) {
				return e.isProject(project) && e.isUser(user);
			}
		});

		if (projectUserConfig == null) {
			projectUserConfig = new ProjectUserConfig();
			projectUserConfig.setProject(project);
			projectUserConfig.setUser(user);
			projectUserConfig.setColor(user.getColor());
			saveEntity(projectUserConfig);
		}

		return projectUserConfig;
	}

}