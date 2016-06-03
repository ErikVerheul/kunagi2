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

public class DaySelectorWidget extends ADateSelectorWidget {

	private Date date;
	private boolean selected;
	private boolean visible;

	public DaySelectorWidget(Date date, boolean selected, boolean visible) {
		this.date = date;
		this.selected = selected;
		this.visible = visible;
	}

	public DaySelectorWidget(Date date) {
		this(date, false, false);
	}

	@Override
	protected AScrumAction getAction() {
		return new DaySelectedAction(date);
	}

	@Override
	protected List<SimpleEvent> getEvents() {
		return Scope.get().getComponent(Calendar.class).getEventsByDate(date);
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
		return Date.today().equals(date);
	}
}
