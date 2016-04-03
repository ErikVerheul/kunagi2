/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
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

import ilarkesto.pdf.APdfContainerElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import scrum.server.common.APdfCreator;
import scrum.server.project.Project;

public class ReleasePlanPdfCreator extends APdfCreator {

	public ReleasePlanPdfCreator(Project project) {
		super(project);
	}

	@Override
	protected void build(APdfContainerElement pdf) {
		reportHeader(pdf, "Release plan", project.getLabel());

		List<Release> releases = new ArrayList<Release>(project.getReleases());
		Collections.sort(releases, Release.DATE_COMPARATOR);
		for (Release release : releases) {
			if (release.isReleased()) continue;
			release(pdf, release);
		}
	}

	@Override
	protected String getFilename() {
		return "releaseplan";
	}

}
