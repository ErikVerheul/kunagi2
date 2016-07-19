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
package scrum.server.calendar;

import ilarkesto.core.time.Date;
import ilarkesto.core.time.Time;
import ilarkesto.fp.Predicate;
import scrum.server.project.Project;

public class SimpleEventDao extends GSimpleEventDao {

	public SimpleEvent getSimpleEventByNumber(final int number, final Project project) {
		return getEntity(new Predicate<SimpleEvent>() {

			@Override
			public boolean test(SimpleEvent t) {
				return t.isNumber(number) && t.isProject(project);
			}
		});
	}

	@Override
	public SimpleEvent newEntityInstance() {
		SimpleEvent event = super.newEntityInstance();
		event.setDate(Date.today());
		return event;
	}

	public SimpleEvent postEvent(Project project, String label, Date date, Time time, Integer duration) {
		SimpleEvent event = newEntityInstance();
		event.setProject(project);
		event.setLabel(label);
		event.setDate(date);
		event.setTime(time);
		event.setDuration(duration);
		saveEntity(event);
		return event;
	}

	public void createTestEvent(Project project, int variant) {
		switch (variant) {
			case 1:
				postEvent(project, "Review Meeting", Date.inDays(10), null, null);
				break;
			case 2:
				postEvent(project, "Punishment Execution", Date.inDays(2), new Time(9, 0), 360);
				break;
			default:
				postEvent(project, "Party", Date.inDays(2), new Time(8, 0), 30);
				break;
		}
	}

}