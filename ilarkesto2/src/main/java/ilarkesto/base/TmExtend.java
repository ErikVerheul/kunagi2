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
package ilarkesto.base;

import static ilarkesto.core.time.Date.today;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import static java.util.Locale.ENGLISH;
import java.util.TimeZone;
import static java.util.TimeZone.getDefault;
import static java.util.TimeZone.getTimeZone;

/**
 * Utility methods for dealing with date and time. Current month, year. Date comparisons.
 */
public final class TmExtend extends ilarkesto.core.time.Tm {

	public static final Format FORMAT_HOUR_MINUTE_SECOND = new Format("HH:mm:ss");
	public static final Format FORMAT_HOUR_MINUTE_SECOND_NOSEP = new Format("HHmmss");
	public static final Format FORMAT_DAY_MONTH_SHORTYEAR = new Format("dd.MM.yy");
	public static final Format FORMAT_DAY_MONTH_YEAR = new Format("dd.MM.yyyy");
	public static final Format FORMAT_LONGMONTH_DAY_YEAR = new Format("MMMM d, yyyy");
	public static final Format FORMAT_DAY_MONTH = new Format("dd.MM.");
	public static final Format FORMAT_WEEKDAY_DAY_MONTH = new Format("EEEE, dd.MM.");
	public static final Format FORMAT_DAY_LONGMONTH_YEAR = new Format("dd. MMMM yyyy");
	public static final Format FORMAT_WEEKDAY_DAY_LONGMONTH_YEAR = new Format("EEEE, dd. MMMM yyyy");
	public static final Format FORMAT_SHORTWEEKDAY_DAY_MONTH_YEAR = new Format("EE, dd.MM.yyyy");
	public static final Format FORMAT_SHORTWEEKDAY_SHORTMONTH_DAY = new Format("EE, MMM dd");
	public static final Format FORMAT_LONGMONTH = new Format("MMMM");
	public static final Format FORMAT_LONGMONTH_YEAR = new Format("MMMM yyyy");
	public static final Format FORMAT_YEAR_MONTH_DAY = new Format("yyyy-MM-dd");
	public static final Format FORMAT_YEAR_MONTH = new Format("yyyy-MM");
	public static final Format FORMAT_YEAR_LONGMONTH = new Format("yyyy-MMMM");
	public static final Format FORMAT_YEAR_MONTH_DAY_NOSEP = new Format("yyyyMMdd");
	public static final Format FORMAT_WEEKDAY = new Format("EEEE");
	public static final Format DATE_DE = new Format("dd.MM.yyyy");
	public static final Format DATE_WITH_SHORT_WEEKDAY_DE = new Format("EE, dd.MM.yyyy");
	public static final Format DATE_LONG_DE = new Format("dd. MMMM yyyy");
	public static final Format DATE_VERY_LONG_DE = new Format("EEEE, dd. MMMM yyyy");
	public static final Format DATE_TIME_DE = new Format("dd.MM.yyyy HH:mm:ss");
	public static final Format DATE_ISO = new Format("yyyy-MM-dd");
	public static final Format DATE_TIME_ISO = new Format("yyyy-MM-dd HH:mm:ss");
	public static final Format DATE_TIME_LOGFILE = new Format("yyyy-MM-dd_HH-mm-ss");
	public static final Format TIME_SHORT_DE = new Format("HH:mm");
	public static final TimeZone TZ_BERLIN = getTimeZone("Europe/Berlin");
	public static final TimeZone TZ_GMT = getTimeZone("GMT");
	public static final transient Format FORMAT_WEEKDAY_DAY_LONGMONTH_YEAR_HOUR_MINUTE = new Format(
			"EEE, dd. MMMM yyyy, HH:mm");
	public static final Format FORMAT_WEEKDAY_LONGMONTH_DAY_YEAR_HOUR_MINUTE = new Format("EEE, MMM d, yyyy, HH:mm");
	public static final Format FORMAT_DAY_MONTH_YEAR_HOUR_MINUTE = new Format("dd.MM.yyyy, HH:mm");
	public static final Format FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = new Format("yyyy-MM-dd HH:mm:ss");
	public static final Format FORMAT_LOG = new Format("yyyy-MM-dd_HH-mm-ss");

	public static final Format FORMAT_RFC822 = new Format("EEE, d MMM yyyy HH:mm:ss z", ENGLISH);

	public static Date toUtc(Date date) {
		return toUtc(date, getDefault());
	}

	public static Date toUtc(Date date, TimeZone timeZone) {
		long millis = date.getTime();
		int offset = timeZone.getOffset(millis);
		return new Date(millis - offset);
	}

	public static Date toTimeZone(Date date, TimeZone timeZone) {
		long millis = date.getTime();
		int offset = timeZone.getOffset(millis);
		return new Date(millis + offset);
	}

	public static Date toLocalTime(Date date) {
		return toTimeZone(date, TZ_GMT);
	}

	public static boolean isSameDay(Date day1, Date day2) {
		return getDayBegin(day1).equals(getDayBegin(day2));
	}

	public static boolean isSameDayIgnoreYear(Date day1, Date day2) {
		GregorianCalendar cal = new GregorianCalendar();

		cal.setTime(day1);
		cal.set(GregorianCalendar.YEAR, 0);
		day1 = cal.getTime();

		cal.setTime(day2);
		cal.set(GregorianCalendar.YEAR, 0);
		day2 = cal.getTime();

		return getDayBegin(day1).equals(getDayBegin(day2));
	}

	public static Date getDayBegin(Date day) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(day);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(GregorianCalendar.MINUTE, 0);
		cal.set(GregorianCalendar.SECOND, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getDayEnd(Date day) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(day);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 23);
		cal.set(GregorianCalendar.MINUTE, 59);
		cal.set(GregorianCalendar.SECOND, 59);
		cal.set(GregorianCalendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static String formatDateTimeShortDe(Date time) {
		StringBuilder sb = new StringBuilder();
		sb.append(DATE_DE.format(time));
		if (getMillisInDay(time) > 0) {
			sb.append(TIME_SHORT_DE.format(time));
		}
		return sb.toString();
	}

	public static long getMillisInDay(Date time) {
		return time.getTime() - getDayBegin(time).getTime();
	}

	public static int getCurrentYear() {
		GregorianCalendar gc = new GregorianCalendar();
		return gc.get(GregorianCalendar.YEAR);
	}

	public static int getCurrentMonth() {
		GregorianCalendar gc = new GregorianCalendar();
		return gc.get(GregorianCalendar.MONTH) + 1;
	}

	public static int year(int year) {
		if (year < (getCurrentYear() + 23 - 2000)) {
                        return year + 2000;
                }
		if (year < 1000) {
                        return year + 1900;
                }
		return year;
	}

	public static int countYearsSince(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return getCurrentYear() - cal.get(GregorianCalendar.YEAR);
	}

	public static class Format {

		private SimpleDateFormat format;

		public Format(String pattern) {
			this.format = new SimpleDateFormat(pattern);
		}

		public Format(String pattern, Locale locale) {
			this.format = new SimpleDateFormat(pattern, locale);
		}

		public synchronized String format(Date date) {
			if (date == null) {
                                return null;
                        }
			return format.format(date);
		}

		public String format(ilarkesto.core.time.Date date) {
			if (date == null) {
                                return null;
                        }
			return format(date.toJavaDate());
		}

		public String format(DateAndTime dateAndTime) {
			if (dateAndTime == null) {
                                return null;
                        }
			return format(dateAndTime.toJavaDate());
		}

		public String format(Time time) {
			if (time == null) {
                                return null;
                        }
			return format(time.getJavaDateOn(today()));
		}

		public SimpleDateFormat getFormat() {
			return format;
		}

	}

}