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

import ilarkesto.core.base.Str;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.Time;
import ilarkesto.gwt.client.Gwt;
import scrum.client.common.TooltipBuilder;
import scrum.client.journal.ProjectEvent;

public class PublishSimpleEventAction extends GPublishSimpleEventAction {

	public PublishSimpleEventAction(scrum.client.calendar.SimpleEvent simpleEvent) {
		super(simpleEvent);
	}

	@Override
	public String getLabel() {
		return "Publish Notification";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Add a notification for this event to the project journal.");
	}

	@Override
	protected void onExecute() {
		String suffix = Gwt.formatWeekdayMonthDay(simpleEvent.getDate().toJavaDate());
		Time time = simpleEvent.getTime();
		if (time != null)
			suffix += ", " + Gwt.formatHourMinute(new DateAndTime(simpleEvent.getDate(), time).toJavaDate());
		String location = simpleEvent.getLocation();
		if (!Str.isBlank(location)) suffix += " @ " + location;
		getDao().createProjectEvent(
			new ProjectEvent(getCurrentProject(), simpleEvent.getLabel() + " scheduled to " + suffix));
	}

}