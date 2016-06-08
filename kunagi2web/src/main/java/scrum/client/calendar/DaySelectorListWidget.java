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
import ilarkesto.core.time.Weekday;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;

import java.util.List;

import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.Widget;

public class DaySelectorListWidget extends AScrumWidget {

	private Date selectedDate = Date.today();

	private Date firstVisibleDate = Date.today();
	private Date lastVisibleDate = Date.today();

	@Override
	protected Widget onInitialization() {
		return null;
	}

	@Override
	protected void onUpdate() {
		TableBuilder tb = new TableBuilder();
		int count = 0;

		tb.setColumnWidths(30, 30, 30, 30, 30, 30, 30, 30);

		tb.add(Gwt.createDiv("DateSelectorWidget-spacer", ""));
		// Header with weekdays
		for (int i = 1; i < 8; i++) {
			// 2009-11-23 is a Monday
			Date weekday = new Date(2009, 11, 22 + i);
			tb.add(Gwt.createDiv("DateSelectorWidget-weekday", weekday.getWeekday().toLocalString()));
		}
		tb.nextRow();

		tb.add(Gwt.createDiv("DateSelectorWidget-spacer", ""));
		tb.nextRow();

		List<Date> month = Date.getDaysInMonth(selectedDate.getYear(), selectedDate.getMonth());

		tb.add(Gwt.createDiv("DateSelectorWidget-weeknumber", "" + month.get(0).getWeek()));
		count++;

		Weekday weekday = month.get(0).getWeekday();

		for (int i = 1; i < weekday.getDayOfWeek() - 1; i++) {
			count++;
			tb.add(Gwt.createDiv("DateSelectorWidget-spacer", ""));
		}

		for (Date date : month) {
			if (count == 8) {
				tb.nextRow();
				tb.add(Gwt.createDiv("DateSelectorWidget-weeknumber", "" + date.getWeek()));
				count = 1;
			}

			tb.add(new DaySelectorWidget(date, date.equals(selectedDate), date.isBetween(firstVisibleDate,
				lastVisibleDate, true)));
			count++;
		}

		replaceContent(tb.createTable());
		super.onUpdate();
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public int getSelectedDay() {
		return this.selectedDate.getDay();
	}

	public void setVisibleRange(Date firstDate, Date lastDate) {
		this.firstVisibleDate = firstDate;
		this.lastVisibleDate = lastDate;
	}

}
