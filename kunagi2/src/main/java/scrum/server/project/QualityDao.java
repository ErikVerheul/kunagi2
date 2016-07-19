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

public class QualityDao extends GQualityDao {

	public Quality getQualityByNumber(final int number, final Project project) {
		return getEntity(new Predicate<Quality>() {

                        @Override
			public boolean test(Quality t) {
				return t.isNumber(number) && t.isProject(project);
			}
		});
	}

	@Override
	public Quality newEntityInstance() {
		Quality quality = super.newEntityInstance();
		quality.setLabel("New Quality");
		return quality;
	}

	// --- test data ---

	public Quality postQuality(Project project, String label) {
		Quality quality = newEntityInstance();
		quality.setProject(project);
		quality.setLabel(label);
		saveEntity(quality);
		return quality;
	}
}