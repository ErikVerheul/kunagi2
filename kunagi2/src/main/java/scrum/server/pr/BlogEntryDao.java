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
package scrum.server.pr;

import ilarkesto.core.time.DateAndTime;
import ilarkesto.fp.Predicate;
import scrum.server.project.Project;

public class BlogEntryDao extends GBlogEntryDao {

	public BlogEntry getBlogEntryByNumber(final int number, final Project project) {
		return getEntity(new Predicate<BlogEntry>() {

                        @Override
			public boolean test(BlogEntry t) {
				return t.isNumber(number) && t.isProject(project);
			}
		});
	}

	@Override
	public BlogEntry newEntityInstance() {
		BlogEntry entry = super.newEntityInstance();
		entry.setDateAndTime(DateAndTime.now());
		return entry;
	}

}