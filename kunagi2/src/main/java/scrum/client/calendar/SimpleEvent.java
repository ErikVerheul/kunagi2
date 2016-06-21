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

import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.time.Date;
import ilarkesto.core.time.Time;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.gwt.client.HyperlinkWidget;
import java.util.Comparator;
import java.util.Map;
import scrum.client.collaboration.ForumSupport;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.common.ShowEntityAction;
import scrum.client.project.Project;

public class SimpleEvent extends GSimpleEvent implements ForumSupport, ReferenceSupport, LabelSupport {

	public static final String REFERENCE_PREFIX = "evt";

	public SimpleEvent(Project project, Date date) {
		super();
		setDate(date);
		setProject(project);
	}

	public SimpleEvent(Map data) {
		super(data);
	}

	public String getTimeAsString() {
		Time time = getTime();
		return time == null ? null : time.toHourMinuteString();
	}

	public String getDurationAsString() {
		Integer duration = getDuration();
		return duration == null ? null : TimePeriod.minutes(duration).toHoursAndMinutes();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getReference()).append(" ");
		sb.append(getDate().toString());
		Time time = getTime();
		if (time != null) {
			sb.append(" ").append(time.toHourMinuteString());
		}
		String location = getLocation();
		if (location != null) {
			sb.append(" @ ").append(location);
		}
		sb.append(" ").append(getLabel());
		return sb.toString();
	}

	@Override
	public Widget createForumItemWidget() {
		return new HyperlinkWidget(new ShowEntityAction(CalendarWidget.class, this, getLabel()));
	}

	@Override
	public String getReference() {
		return REFERENCE_PREFIX + getNumber();
	}

	public final static Comparator<SimpleEvent> TIME_COMPARATOR = new Comparator<SimpleEvent>() {

		@Override
		public int compare(SimpleEvent a, SimpleEvent b) {
			Time at = a.getTime();
			Time bt = b.getTime();
			if (at == null && bt == null) return 0;
			if (at == null) return -1;
			if (bt == null) return 1;
			return at.compareTo(bt);
		}
	};
}