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
import static ilarkesto.core.time.Tm.getNowAsDate;
import java.io.Serializable;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;

@SuppressWarnings("SE_NO_SERIALVERSIONID")
public class Time implements Comparable<Time>, Serializable {

	protected int hour;
	protected int minute;
	protected int second;

	private transient int hashCode;

	public Time(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public Time(String timeString) {
		int idx = timeString.indexOf(':');
		if (idx >= 0) {
			hour = parseInt(timeString.substring(0, idx));
			timeString = timeString.substring(idx + 1);
		} else {
			if (timeString.trim().length() == 0) {
                                return; // 0:00:00
                        }
			hour = parseInt(timeString);
			return;
		}
		idx = timeString.indexOf(':');
		if (idx >= 0) {
			minute = parseInt(timeString.substring(0, idx));
			timeString = timeString.substring(idx + 1);
		} else {
			minute = parseInt(timeString);
			return;
		}
		second = parseInt(timeString);
	}

	public Time(int hour, int minute) {
		this(hour, minute, 0);
	}

	public Time(java.util.Date javaDate) {
		this(Tm.getHour(javaDate), Tm.getMinute(javaDate), Tm.getSecond(javaDate));
	}

	public Time(long millis) {
		this(new java.util.Date(millis));
	}

	public Time() {
		this(getNowAsDate());
	}

	// ---

	public final boolean isBefore(Time other) {
		return compareTo(other) < 0;
	}

	public final boolean isBeforeOrSame(Time other) {
		return compareTo(other) <= 0;
	}

	public final boolean isAfter(Time other) {
		return compareTo(other) > 0;
	}

	public final boolean isAfterOrSame(Time other) {
		return compareTo(other) >= 0;
	}

	public final int getHour() {
		return hour;
	}

	public final int getMinute() {
		return minute;
	}

	public final int getSecond() {
		return second;
	}

	public final java.util.Date getJavaDateOn(Date day) {
		return day.toJavaDate(this);
	}

	public final long toMillis() {
		return toSeconds() * 1000;
	}

	public final long toSeconds() {
		return second + (minute * 60) + (hour * 3600);
	}

	public TimePeriod getPeriodTo(Time other) {
		return new TimePeriod(other.toMillis() - toMillis());
	}

	@Override
	public final int hashCode() {
                int hC = hashCode;
		if (hC == 0) {
			hC = 23;
			hC = hC * 37 + hour;
			hC = hC * 37 + minute;
			hC = hC * 37 + second;
		}
		return hC;
	}

	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof Time)) {
                        return false;
                }
		Time other = (Time) obj;
		return hour == other.hour && minute == other.minute && second == other.second;
	}

	public final String toHourMinuteString() {
		return toString(false);
	}

	public String formatLog() {
		StringBuilder sb = new StringBuilder();
		formatHour(sb);
		sb.append("-");
		formatMinute(sb);
		sb.append("-");
		formatSecond(sb);
		return sb.toString();
	}

	public String formatHourMinute() {
		StringBuilder sb = new StringBuilder();
		formatHour(sb);
		sb.append(":");
		formatMinute(sb);
		return sb.toString();
	}

	public String formatHourMinuteSecond() {
		return formatHourMinuteSecond(true);
	}

	public String formatHourMinuteSecond(boolean skipSecondIfZero) {
		StringBuilder sb = new StringBuilder();
		formatHour(sb);
		sb.append(":");
		formatMinute(sb);
		if (skipSecondIfZero && second == 0) {
                        return sb.toString();
                }
		sb.append(":");
		formatSecond(sb);
		return sb.toString();
	}

	@Deprecated
	public final String toString(boolean includeSeconds) {
		return includeSeconds ? formatHourMinuteSecond() : formatHourMinute();
	}

	public void formatSecond(StringBuilder sb) {
		if (second < 10) {
                        sb.append("0");
                }
		sb.append(second);
	}

	public void formatMinute(StringBuilder sb) {
		if (minute < 10) {
                        sb.append("0");
                }
		sb.append(minute);
	}

	public void formatHour(StringBuilder sb) {
		if (hour < 10) {
                        sb.append("0");
                }
		sb.append(hour);
	}

	@Override
	public final String toString() {
		return formatHourMinuteSecond(true);
	}

	@Override
	public final int compareTo(Time other) {
		if (hour > other.hour) {
                        return 1;
                }
		if (hour < other.hour) {
                        return -1;
                }
		if (minute > other.minute) {
                        return 1;
                }
		if (minute < other.minute) {
                        return -1;
                }
		if (second > other.second) {
                        return 1;
                }
		if (second < other.second) {
                        return -1;
                }
		return 0;
	}

	// --- static ---

	public static Time now() {
		return new Time();
	}

	public static String toStringBeginEnd(Time begin, Time end) {
		if (begin == null && end == null) {
                        return null;
                }
		StringBuilder sb = new StringBuilder();
		if (begin != null) {
			sb.append(begin);
		}
		if (end != null) {
			sb.append("-");
			sb.append(end);
		}
		return sb.toString();
	}

}
