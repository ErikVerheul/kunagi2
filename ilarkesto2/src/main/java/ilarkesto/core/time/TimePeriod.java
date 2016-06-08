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

import ilarkesto.core.base.Utl;
import static ilarkesto.core.base.Utl.getLanguage;
import static ilarkesto.core.time.Tm.DAY;
import static ilarkesto.core.time.Tm.HOUR;
import static ilarkesto.core.time.Tm.MINUTE;
import static ilarkesto.core.time.Tm.MONTH;
import static ilarkesto.core.time.Tm.SECOND;
import static ilarkesto.core.time.Tm.WEEK;
import static ilarkesto.core.time.Tm.YEAR;
import static ilarkesto.core.time.Tm.getLocalizer;
import java.io.Serializable;
import static java.lang.Long.parseLong;
import static java.lang.Long.parseLong;
import static java.lang.Long.parseLong;
import static java.lang.String.valueOf;

public class TimePeriod implements Comparable<TimePeriod>, Serializable {

	public static final TimePeriod ZERO = new TimePeriod(0);

	private long millis;

	public TimePeriod(long millis) {
		this.millis = Math.abs(millis);
	}

	public TimePeriod(String s) {
		int idx = s.indexOf(':');
		if (idx >= 0) {
			millis = 0;
			int i = 0;
			while (idx >= 0) {
				String num = s.substring(0, idx);
				long l = parseLong(num);
				switch (i) {
					case 0:
						millis += l * HOUR;
						break;
					case 1:
						millis += l * MINUTE;
						break;
					case 2:
						millis += l * SECOND;
						break;
					default:
						throw new IllegalArgumentException(s);
				}
				i++;
				s = s.substring(idx + 1);
				idx = s.indexOf(':');
			}
			long l = parseLong(s);
			switch (i) {
				case 0:
					millis += l * HOUR;
					break;
				case 1:
					millis += l * MINUTE;
					break;
				case 2:
					millis += l * SECOND;
					break;
				default:
					millis += l;
			}
		} else {
			this.millis = parseLong(s);
		}
	}

	public TimePeriod() {
		this(0);
	}

	protected TimePeriod newTimePeriod(long millis) {
		return new TimePeriod(millis);
	}

	// ---

	public TimePeriod subtract(TimePeriod tp) {
		if (tp == null) {
                        return this;
                }
		return newTimePeriod(millis - tp.millis);
	}

	public TimePeriod add(TimePeriod tp) {
		if (tp == null) {
                        return this;
                }
		return newTimePeriod(millis + tp.millis);
	}

	public TimePeriod addDays(int days) {
		return newTimePeriod(millis + DAY);
	}

	public TimePeriod multiplyBy(int factor) {
		return newTimePeriod(millis * factor);
	}

	public TimePeriod divide(int divisor) {
		return newTimePeriod(millis / divisor);
	}

	public TimePeriod getPeriodTo(Time other) {
		return newTimePeriod(other.toMillis() - toMillis());
	}

	public TimePeriod abs() {
		return millis < 0 ? new TimePeriod(-millis) : this;
	}

	public final long toMillis() {
		return millis;
	}

	public final long toSeconds() {
		return millis / SECOND;
	}

	public final long toMinutes() {
		return millis / MINUTE;
	}

	public final long toHours() {
		return millis / HOUR;
	}

	public final int toDays() {
		return (int) (millis / DAY);
	}

	public final int toWeeks() {
		return (int) (millis / WEEK);
	}

	public final int toMonths() {
		return (int) (millis / MONTH);
	}

	public final int toYears() {
		return (int) (millis / YEAR);
	}

	public final float toDecimalDays() {
		return (float) millis / (float) DAY;
	}

	public final boolean isNegative() {
		return millis < 0;
	}

	public final boolean isPositive() {
		return millis > 0;
	}

	public String toHoursAndMinutesString() {
		long hours = toHours();
		long minutes = toMinutes() - (hours * 60);
		StringBuilder sb = new StringBuilder();
		sb.append(hours);
		sb.append(':');
		if (minutes < 10) {
                        sb.append('0');
                }
		sb.append(minutes);
		return sb.toString();
	}

	public String toShortestString() {
		return toShortestString(getLanguage());
	}

	public final String toShortestString(String language) {
		TmLocalizer loc = getLocalizer(language);
		StringBuilder sb = new StringBuilder();
		long m = millis >= 0 ? millis : -millis;
		if (m >= (YEAR * 2)) {
			int i = toYears();
			sb.append(i);
			sb.append(" ").append(loc.years(i));
		} else if (m >= (MONTH * 2)) {
			int i = toMonths();
			sb.append(i);
			sb.append(" ").append(loc.months(i));
		} else if (m >= (WEEK * 2)) {
			int i = toWeeks();
			sb.append(i);
			sb.append(" ").append(loc.weeks(i));
		} else if (m >= DAY) {
			int i = toDays();
			sb.append(i);
			sb.append(" ").append(loc.days(i));
		} else if (m >= ((HOUR * 2) - (MINUTE - 20))) {
			long l = toHours();
			sb.append(l);
			sb.append(" ").append(loc.hours(l));
		} else if (m >= MINUTE) {
			long l = toMinutes();
			sb.append(l);
			sb.append(" ").append(loc.minutes(l));
		} else if (m >= SECOND) {
			long l = toSeconds();
			sb.append(l);
			sb.append(" ").append(loc.seconds(l));
		} else {
			sb.append(m);
			sb.append(" ").append(loc.millis(m));
		}
		return sb.toString();
	}

	public final String toHoursAndMinutes() {
		long hours = toHours();
		long remainingMillis = millis - (hours * 3600000);
		long minutes = remainingMillis / 60000;
		return hours + (minutes > 9 ? ":" : ":0") + minutes;
	}

	public boolean isGreaterThen(TimePeriod other) {
		return millis > other.millis;
	}

	public boolean isLessThen(TimePeriod other) {
		return millis < other.millis;
	}

	@Override
	public final int compareTo(TimePeriod o) {
		if (millis == o.millis) {
                        return 0;
                }
		return millis > o.millis ? 1 : -1;
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == null) {
                        return false;
                }
		if (!(obj instanceof TimePeriod)) {
                        return false;
                }
		return millis == ((TimePeriod) obj).millis;
	}

        @Override
        public int hashCode() {
                int hash = 7;
                hash = 61 * hash + (int) (this.millis ^ (this.millis >>> 32));
                return hash;
        }

	@Override
	public final String toString() {
		return valueOf(millis);
	}

	// --- static ---

	public static TimePeriod seconds(int seconds) {
		return new TimePeriod(seconds * SECOND);
	}

	public static TimePeriod minutes(int minutes) {
		return new TimePeriod(minutes * MINUTE);
	}

	public static TimePeriod hours(int hours) {
		return new TimePeriod(hours * HOUR);
	}

	public static TimePeriod days(int days) {
		return new TimePeriod(days * DAY);
	}

	public static TimePeriod weeks(int weeks) {
		return new TimePeriod(weeks * WEEK);
	}

}
