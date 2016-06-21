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

public class YearSelectorListWidget extends AScrumWidget {

	private int selectedYear = Date.today().getYear();
	private int from = selectedYear - 1;
	private int to = from + 4;

	private Date firstVisibleDate = Date.today();
	private Date lastVisibleDate = Date.today();

	@Override
	protected Widget onInitialization() {
		return null;
	}

	@Override
	protected void onUpdate() {
		TableBuilder tb = new TableBuilder();
		for (int year = from; year <= to; year++) {
			tb.add(new YearSelectorWidget(year, year == selectedYear, (new Date(year, 1, 1).isBetween(firstVisibleDate,
				lastVisibleDate, true))));
		}
		replaceContent(tb.createTable());
		super.onUpdate();
	}

	public int getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(int selectedYear) {
		this.selectedYear = selectedYear;
		this.from = selectedYear - 1;
		this.to = from + 4;
	}

	public void setVisibleRange(Date firstDate, Date lastDate) {
		this.firstVisibleDate = firstDate;
		this.lastVisibleDate = lastDate;
	}

}
