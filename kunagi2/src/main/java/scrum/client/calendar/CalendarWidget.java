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
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;
import scrum.client.common.AScrumWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Widget;

public class CalendarWidget extends AScrumWidget {

	private DayListWidget dayList;
	private YearSelectorListWidget yearSelector;
	private MonthSelectorListWidget monthSelector;
	private DaySelectorListWidget daySelector;

	@Override
	protected Widget onInitialization() {

		dayList = new DayListWidget();

		yearSelector = new YearSelectorListWidget();
		monthSelector = new MonthSelectorListWidget();
		daySelector = new DaySelectorListWidget();

		PagePanel left = new PagePanel();
		left.addHeader("Project Calendar", new ButtonWidget(new CreateSimpleEventAction()));
		left.addSection(dayList);
		left.addSection(new UserGuideWidget(getLocalizer().views().calendar(), getCurrentProject().getSimpleEvents()
				.size() < 5, getCurrentUser().getHideUserGuideCalendarModel()));

		PagePanel right = new PagePanel();
		right.addHeader("Years");
		right.addSection(yearSelector);
		right.addHeader("Months");
		right.addSection(monthSelector);
		right.addHeader("Days");
		right.addSection(daySelector);

		yearSelector.setSelectedYear(dayList.getDate().getYear());
		yearSelector.setVisibleRange(dayList.getBegin(), dayList.getEnd());
		monthSelector.setYear(dayList.getDate().getYear());
		monthSelector.setSelectedMonth(dayList.getDate().getMonth());
		monthSelector.setVisibleRange(dayList.getBegin(), dayList.getEnd());
		daySelector.setSelectedDate(dayList.getDate());
		daySelector.setVisibleRange(dayList.getBegin(), dayList.getEnd());

		TableBuilder tb = new TableBuilder();
		tb.setColumnWidths("", "10px", "270px");
		tb.addRow(left, Gwt.createSpacer(10, 1), right);

		return tb.createTable();
	}

	public Date getSelectedDate() {
		return new Date(yearSelector.getSelectedYear(), monthSelector.getSelectedMonth(), daySelector.getSelectedDay());
	}

	public void showDate(Date date) {
		dayList.showDate(date);
		yearSelector.setSelectedYear(date.getYear());
		yearSelector.setVisibleRange(dayList.getBegin(), dayList.getEnd());
		monthSelector.setYear(date.getYear());
		monthSelector.setSelectedMonth(date.getMonth());
		monthSelector.setVisibleRange(dayList.getBegin(), dayList.getEnd());
		daySelector.setSelectedDate(date);
		daySelector.setVisibleRange(dayList.getBegin(), dayList.getEnd());
		update();
	}

	public void showEvent(SimpleEvent event) {
		if (event == null) return;
		showDate(event.getDate());
		dayList.showEvent(event);
	}

}
