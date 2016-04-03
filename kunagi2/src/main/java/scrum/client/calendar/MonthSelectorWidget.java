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

import java.util.List;

import scrum.client.common.AScrumAction;

public class MonthSelectorWidget extends ADateSelectorWidget {

	private int month;
	private int year;
	private boolean selected;
	private boolean visible;

	public MonthSelectorWidget(int year, int month, boolean selected, boolean visible) {
		this.month = month;
		this.year = year;
		this.selected = selected;
		this.visible = visible;
	}

	public MonthSelectorWidget(int year, int month) {
		this(year, month, false, false);
	}

	@Override
	protected AScrumAction getAction() {
		return new MonthSelectedAction(month);
	}

	@Override
	protected List<SimpleEvent> getEvents() {
		return Scope.get().getComponent(Calendar.class).getEventsByMonth(year, month);
	}

	@Override
	protected boolean isSelected() {
		return selected;
	}

	@Override
	protected boolean isVisibleInList() {
		return visible;
	}

	@Override
	protected boolean isToday() {
		Date today = Date.today();
		return today.getMonth() == month && today.getYear() == year;
	}
}
