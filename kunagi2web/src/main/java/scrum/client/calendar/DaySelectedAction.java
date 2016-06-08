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
import scrum.client.common.AScrumAction;

public class DaySelectedAction extends AScrumAction {

	private Date date;

	public DaySelectedAction(Date date) {
		this.date = date;
	}

	@Override
	public String getLabel() {
		return "" + date.getDay();
	}

	@Override
	protected void onExecute() {
		Scope.get().getComponent(Calendar.class).showDate(date);
	}

}
