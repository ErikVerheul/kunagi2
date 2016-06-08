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
import ilarkesto.gwt.client.TableBuilder;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.Widget;

public class MonthSelectorListWidget extends AScrumWidget {

	private int selectedMonth = Date.today().getMonth();
	private int year = Date.today().getYear();

	private Date firstVisibleDate = Date.today();
	private Date lastVisibleDate = Date.today();

	@Override
	protected Widget onInitialization() {
		return null;
	}

	@Override
	protected void onUpdate() {
		TableBuilder tb = new TableBuilder();
		for (int month = 1; month <= 12; month++) {
			tb.add(new MonthSelectorWidget(year, month, month == selectedMonth, (new Date(year, month, 1)).isBetween(
				firstVisibleDate, lastVisibleDate, true)));
			if (month == 6) tb.nextRow();
		}
		replaceContent(tb.createTable());
		super.onUpdate();
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setSelectedMonth(int selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public int getSelectedMonth() {
		return selectedMonth;
	}

	public void setVisibleRange(Date firstDate, Date lastDate) {
		this.firstVisibleDate = firstDate;
		this.lastVisibleDate = lastDate;
	}

}
