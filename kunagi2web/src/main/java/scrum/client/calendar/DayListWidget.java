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

import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.Date;
import ilarkesto.gwt.client.Gwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListSelectionManager;
import scrum.client.common.BlockListWidget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DayListWidget extends AScrumWidget {

	private Calendar calendar;

	private int visibleDays = 21;
	private SimplePanel wrapper;
	private BlockListSelectionManager selectionManager;
	private Map<Date, BlockListWidget<SimpleEvent>> lists;
	private Map<Date, Widget> eventLists = new HashMap<Date, Widget>();

	private Date date;
	private Date begin;
	private Date end;

	public DayListWidget() {
		date = Date.today();
		updateBeginAndEnd();
	}

	@Override
	protected Widget onInitialization() {
		calendar = Scope.get().getComponent(Calendar.class);

		selectionManager = new BlockListSelectionManager();
		lists = new HashMap<Date, BlockListWidget<SimpleEvent>>();

		wrapper = new SimplePanel();
		showDate(date);
		return wrapper;
	}

	@Override
	protected void onUpdate() {
		// Log.DEBUG("DayListWidget.onUpdate()", date, lists);
		for (Map.Entry<Date, BlockListWidget<SimpleEvent>> entry : lists.entrySet()) {
			Date entryDate = entry.getKey();
			List<SimpleEvent> events = calendar.getEventsByDate(entryDate);
			BlockListWidget<SimpleEvent> listWidget = entry.getValue();
			listWidget.setObjects(events);
		}
	}

	private void updateBeginAndEnd() {
		begin = date;
		end = begin.addDays(visibleDays);
	}

	public void showDate(Date dateToShow) {
		if (wrapper.getWidget() != null && date.equals(dateToShow)) return;

		this.date = dateToShow;
		updateBeginAndEnd();

		lists.clear();
		selectionManager.clear();
		eventLists.clear();

		FlexTable table = new FlexTable();
		table.setWidth("100%");
		table.setCellPadding(2);
		table.getColumnFormatter().setWidth(0, "25px");
		table.getColumnFormatter().setWidth(1, "40px");
		table.getColumnFormatter().setWidth(2, "30px");
		table.getColumnFormatter().setWidth(3, "40px");

		// table.setBorderWidth(1);
		int row = 0;
		Date d = begin;
		int month = 0;
		int week = 0;
		while (d.compareTo(end) <= 0) {
			int w = d.getWeek();
			if (w != week) {
				week = w;
				Widget weekWidget = Gwt.createDiv("DayListWidget-week", String.valueOf(week));
				table.setWidget(row, 0, weekWidget);
			}
			int m = d.getMonth();
			if (m != month) {
				month = m;
				Widget monthWidget = Gwt.createDiv("DayListWidget-month", Gwt.getMonthShort(month));
				table.setWidget(row, 1, monthWidget);
			}
			table.setWidget(row, 2, Gwt.createDiv("DayListWidget-date", Gwt.formatDay(d.toJavaDate())));
			table.setWidget(row, 3, Gwt.createDiv("DayListWidget-date", Gwt.formatWeekdayShort(d.toJavaDate())));
			table.setWidget(row, 4, createDayContent(d));

			formatRow(table, row);
			d = d.nextDay();
			row++;
		}

		Widget downloadLink = ScrumGwt.createPdfLink("Download as PDF", "calendar", "from", begin.toString(), "to",
			end.toString());
		wrapper.setWidget(Gwt.createFlowPanel(table, Gwt.createSpacer(1, 10), downloadLink));
	}

	private void formatRow(FlexTable table, int row) {
		String border = "1px solid #EEE";
		for (int i = 0; i <= 4; i++) {
			Element element = table.getCellFormatter().getElement(row, i);
			Style style = element.getStyle();
			style.setProperty("borderBottom", border);
			if (row == 0) style.setProperty("borderTop", border);
			if (i < 3 || i == 4) style.setProperty("borderLeft", border);
			if (i == 4) style.setProperty("borderRight", border);
		}
	}

	public void showEvent(SimpleEvent event) {
		if (event == null) return;
		selectionManager.select(event);
	}

	private Widget createDayContent(Date date) {
		FlowPanel panel = new FlowPanel();
		for (String info : calendar.getInfos(date)) {
			panel.add(Gwt.createDiv("DayListWidget-date-info", info));
		}
		panel.add(getEventList(date));
		return panel;
	}

	private Widget getEventList(Date date) {
		Widget eventList = eventLists.get(date);
		if (eventList == null) {
			eventList = createEventList(date);
			eventLists.put(date, eventList);
		}
		return eventList;
	}

	private Widget createEventList(Date date) {
		BlockListWidget<SimpleEvent> list = new BlockListWidget<SimpleEvent>(SimpleEventBlock.FACTORY,
				new ChangeSimpleEventDateDropAction(date));
		list.setSelectionManager(selectionManager);
		list.setAutoSorter(SimpleEvent.TIME_COMPARATOR);
		lists.put(date, list);
		return list;
	}

	public Date getDate() {
		return date;
	}

	public Date getBegin() {
		return begin;
	}

	public Date getEnd() {
		return end;
	}

}
