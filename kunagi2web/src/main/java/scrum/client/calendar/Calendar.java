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
package scrum.client.calendar;

import ilarkesto.core.time.Date;

import java.util.ArrayList;
import java.util.List;

import scrum.client.sprint.Sprint;

public class Calendar extends GCalendar {

	public List<SimpleEvent> getEventsByDate(Date date) {
		List<SimpleEvent> ret = new ArrayList<SimpleEvent>();
		for (SimpleEvent event : project.getSimpleEvents()) {
			if (event.isDate(date)) ret.add(event);
		}
		return ret;
	}

	public List<SimpleEvent> getEventsByYear(int year) {
		List<SimpleEvent> ret = new ArrayList<SimpleEvent>();
		for (SimpleEvent event : project.getSimpleEvents()) {
			if (event.getDate().getYear() == year) ret.add(event);
		}
		return ret;
	}

	public List<SimpleEvent> getEventsByMonth(int year, int month) {
		List<SimpleEvent> ret = new ArrayList<SimpleEvent>();
		for (SimpleEvent event : project.getSimpleEvents()) {
			Date date = event.getDate();
			if (date.getYear() == year && date.getMonth() == month) ret.add(event);
		}
		return ret;
	}

	public void showYear(int year) {
		Date prev = projectWorkspaceWidgets.getCalendar().getSelectedDate();
		Date date = new Date(year, prev.getMonth(), prev.getDay());
		projectWorkspaceWidgets.getCalendar().showDate(date);
	}

	public void showMonth(int month) {
		Date prev = projectWorkspaceWidgets.getCalendar().getSelectedDate();
		Date date = new Date(prev.getYear(), month, prev.getDay());
		projectWorkspaceWidgets.getCalendar().showDate(date);
	}

	public void showDay(int day) {
		Date prev = projectWorkspaceWidgets.getCalendar().getSelectedDate();
		Date date = new Date(prev.getYear(), prev.getMonth(), day);
		projectWorkspaceWidgets.getCalendar().showDate(date);
	}

	public void showDate(Date date) {
		projectWorkspaceWidgets.getCalendar().showDate(date);
	}

	public List<String> getInfos(Date date) {
		List<String> ret = new ArrayList<String>();
		for (Sprint sprint : project.getSprints()) {
			if (sprint.isEnd(date)) ret.add(0, "End of Sprint: " + sprint.getLabel());
			if (sprint.isBegin(date)) ret.add("Begin of Sprint: " + sprint.getLabel());
		}
		return ret;
	}
}
