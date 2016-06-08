/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
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
package ilarkesto.core.time;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import static ilarkesto.core.time.Date.today;
import static ilarkesto.core.time.Tm.HOUR;
import static ilarkesto.core.time.Tm.MINUTE;
import static ilarkesto.core.time.Tm.createDate;
import static ilarkesto.core.time.Tm.createDate;
import static ilarkesto.core.time.Tm.getNowAsDate;
import java.io.Serializable;

public class DateAndTime implements Comparable<DateAndTime>, Serializable {
        private static final long serialVersionUID = 1L; //warning: this addition could make existing projects unreadable!
	protected Date date;
	protected Time time;
        
        @SuppressWarnings(value = "SE_TRANSIENT_FIELD_NOT_RESTORED", justification = "No need to serialize this field")
	private transient int hashCode;

	public DateAndTime(java.util.Date javaDate) {
		this(new Date(javaDate), new Time(javaDate));
	}

	public DateAndTime(String s) {
		assert s != null;
		s = s.trim();
		int idx = s.indexOf(' ');

		if (idx > 0) {
			String sDate = s.substring(0, idx);
			String sTime = s.substring(idx + 1);
			date = new Date(sDate);
			time = new Time(sTime);
		} else {
			if (s.indexOf('.') > 0) {
				date = new Date(s);
				time = new Time("0");
			} else {
				date = today();
				time = new Time(s);
			}
		}
	}

	public DateAndTime(Date date, Time time) {
		assert date != null && time != null;
		this.date = date;
		this.time = time;
	}

	public DateAndTime(int year, int month, int day, int hour, int minute, int second) {
		this(new Date(year, month, day), new Time(hour, minute, second));
	}

	public DateAndTime(long millis) {
		this(createDate(millis));
	}

	public DateAndTime() {
		this(getNowAsDate());
	}

	// ---

	public DateAndTime addDays(int days) {
		return new DateAndTime(Tm.addDays(toJavaDate(), days));
	}

	public DateAndTime addHours(int hours) {
		return new DateAndTime(toMillis() + (hours * HOUR));
	}

	public DateAndTime addMinutes(int minutes) {
		return new DateAndTime(toMillis() + (minutes * MINUTE));
	}

	public TimePeriod getPeriodTo(DateAndTime other) {
		return new TimePeriod(other.toMillis() - toMillis());
	}

	public TimePeriod getPeriodToNow() {
		return getPeriodTo(now());
	}

	public TimePeriod getPeriodFromNow() {
		return now().getPeriodTo(this);
	}

	public final boolean isPast() {
		return isBefore(now());
	}

	public final boolean isFuture() {
		return isAfter(now());
	}

	public final boolean isBefore(Date other) {
		return getDate().isBefore(other);
	}

	public final boolean isBefore(DateAndTime other) {
		return compareTo(other) < 0;
	}

	public boolean isBeforeOrSame(DateAndTime other) {
		return compareTo(other) <= 0;
	}

	public final boolean isAfter(DateAndTime other) {
		return compareTo(other) > 0;
	}

	public boolean isAfterOrSame(DateAndTime other) {
		return compareTo(other) >= 0;
	}

	public final Date getDate() {
		return date;
	}

	public final Time getTime() {
		return time;
	}

	public final java.util.Date toJavaDate() {
		return createDate(date.toMillis() + time.toMillis());
	}

	public final long toMillis() {
		return date.toMillis() + time.toMillis();
	}

	public String formatLog() {
		return date.formatYearMonthDay() + "_" + time.formatLog();
	}

	public String formatDayMonthYearHourMinute() {
		return date.formatDayMonthYear() + " " + time.formatHourMinute();
	}

	public String formatYearMonthDayHourMinute() {
		return date.formatYearMonthDay() + " " + time.formatHourMinute();
	}

	@Override
	public final String toString() {
		return date + " " + time;
	}

	@Override
	public final int hashCode() {
		if (hashCode == 0) {
			hashCode = 23;
			hashCode = hashCode * 37 + date.hashCode();
			hashCode = hashCode * 37 + time.hashCode();
		}
		return hashCode;
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == null) {
                        return false;
                }
		if (!(obj instanceof DateAndTime)) {
                        return false;
                }
		return date.equals(((DateAndTime) obj).date) && time.equals(((DateAndTime) obj).time);
	}

	@Override
	public final int compareTo(DateAndTime o) {
		int i = date.compareTo(o.date);
		return i == 0 ? time.compareTo(o.time) : i;
	}

	// --- static ---

	public static DateAndTime now() {
		return new DateAndTime();
	}

}