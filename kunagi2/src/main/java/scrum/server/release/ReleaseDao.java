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
package scrum.server.release;

import scrum.server.release.GReleaseDao;
import ilarkesto.core.time.Date;
import ilarkesto.fp.Predicate;

import java.util.Set;

import scrum.server.project.Project;

public class ReleaseDao extends GReleaseDao {

	public Release getReleaseByNumber(final int number, final Project project) {
		return getEntity(new Predicate<Release>() {

			@Override
			public boolean test(Release t) {
				return t.isNumber(number) && t.isProject(project);
			}
		});
	}

	public Release getNextRelease(Project project) {
		Release next = null;
		Set<Release> releases = getReleasesByProject(project);
		for (Release release : releases) {
			if (release.isReleased()) continue;
			if (next == null || release.getReleaseDate().isBefore(next.getReleaseDate())) {
				next = release;
			}
		}
		return next;
	}

	public Release getCurrentRelease(Project project) {
		Release latest = null;
		Set<Release> releases = getReleasesByProject(project);
		for (Release release : releases) {
			if (!release.isReleased()) continue;
			if (latest == null || release.getReleaseDate().isAfter(latest.getReleaseDate())) {
				latest = release;
			}
		}
		return latest;
	}

	public void resetScripts() {
		for (Release release : getEntities()) {
			release.setScriptRunning(false);
		}
	}

	public Release postRelease(Project project, Date releaseDate, String label) {
		Release release = newEntityInstance();
		release.setProject(project);
		release.setReleaseDate(releaseDate);
		release.setLabel(label);
		saveEntity(release);
		return release;
	}
}