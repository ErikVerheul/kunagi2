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

import static java.lang.System.currentTimeMillis;
import java.util.Date;

public class Tm {

	public static final long SECOND = 1000l;
	public static final long MINUTE = 60000l;
	public static final long HOUR = 3600000l;
	public static final long DAY = 86400000l;
	public static final long WEEK = 604800000l;

	public static final long MONTH = DAY * 30;
	public static final long YEAR = MONTH * 12;

	public static final int SUNDAY = 1;
	public static final int MONDAY = 2;
	public static final int TUESDAY = 3;
	public static final int WEDNESDAY = 4;
	public static final int THURSDAY = 5;
	public static final int FRIDAY = 6;
	public static final int SATURDAY = 7;

	private static TmLocalizer tmLocalizer;
	private static TmLocalizerDe tmLocalizerDe;

	private static long timeOffset = 0;

	public static long getCurrentTimeMillis() {
		return currentTimeMillis() + timeOffset;
	}

	public static void setTimeOffset(long timeOffset) {
		timeOffset = timeOffset;
	}

	public static Date getDateAndTime(Date date, long time) {
		date = copyDate(date);
		resetTime(date);
		date.setTime(date.getTime() + time);
		return date;
	}

	@SuppressWarnings("deprecation")
	public static Date getDateOfFirstWeek(int year) {
		Date january4th = createDate(year, 1, 4, 12, 0, 0);
		int weekday = getWeekday(january4th);
		if (weekday == MONDAY) {
			january4th.setHours(0);
			return january4th;
		}
		if (weekday == SUNDAY) {
			addDaysToDate(january4th, -6);
			january4th.setHours(0);
			return january4th;
		}
		addDaysToDate(january4th, 2 - weekday);
		january4th.setHours(0);
		return january4th;
	}

	public static int getWeek(Date date) {
		int year = getYear(date);
		if (getMonth(date) == 12 && getDay(date) > 28) {
			Date dateOfFirstWeek = getDateOfFirstWeek(year + 1);
			if (getYear(dateOfFirstWeek) == year) {
                                return 1;
                        }
		}

		Date dateOfFirstWeek = getDateOfFirstWeek(year);
		int days = getDaysBetweenDates(dateOfFirstWeek, date);
		return (days / 7) + 1;
	}

	public static TmLocalizer getLocalizer(String language) {
		if (language.equals("de")) {
			if (tmLocalizerDe == null) {
                                tmLocalizerDe = new TmLocalizerDe();
                        }
			return tmLocalizerDe;
		}
		if (tmLocalizer == null) {
                        tmLocalizer = new TmLocalizer();
                }
		return tmLocalizer;
	}

	@SuppressWarnings("deprecation")
	public static Date createDate(int year, int month, int day, int hour, int min, int sec) {
		return new Date(year - 1900, month - 1, day, hour, min, sec);
	}

	public static Date createDate(int year, int month, int day) {
		return createDate(year, month, day, 0, 0, 0);
	}

	public static Date addDays(Date date, int days) {
		date = new Date(date.getTime());
		addDaysToDate(date, days);
		return date;
	}

	@SuppressWarnings("deprecation")
	public static void addDaysToDate(Date date, int days) {
		date.setDate(date.getDate() + days);
	}

	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && year % 100 != 0) {
			return true;
		} else return year % 400 == 0;
	}

	public static int getDaysInYear(int year) {
		return isLeapYear(year) ? 356 : 355;
	}

	public static int getDaysInMonth(int year, int month) {
		switch (month) {
			case 1:
				return 31;
			case 2:
				return isLeapYear(year) ? 29 : 28;
			case 3:
				return 31;
			case 4:
				return 30;
			case 5:
				return 31;
			case 6:
				return 30;
			case 7:
				return 31;
			case 8:
				return 31;
			case 9:
				return 30;
			case 10:
				return 31;
			case 11:
				return 30;
			case 12:
				return 31;
		}
		throw new IllegalArgumentException("Illegal month: " + month);
	}

	public static int getDaysBetweenDates(Date start, Date finish) {
		start = copyDate(start);
		resetTime(start);
		finish = copyDate(finish);
		resetTime(finish);

		long aTime = start.getTime();
		long bTime = finish.getTime();

		long adjust = 60 * 60 * 1000;
		adjust = (bTime > aTime) ? adjust : -adjust;

		return (int) ((bTime - aTime + adjust) / DAY);
	}

	public static Date copyDate(Date date) {
		if (date == null) { return null; }
		Date newDate = new Date();
		newDate.setTime(date.getTime());
		return newDate;
	}

	@SuppressWarnings("deprecation")
	private static void resetTime(Date date) {
		long msec = date.getTime();
		msec = (msec / 1000) * 1000;
		date.setTime(msec);

		// Daylight savings time occurs at midnight in some time zones, so we reset
		// the time to noon instead.
		date.setHours(12);
		date.setMinutes(0);
		date.setSeconds(0);
	}

	public static Date createDate(long millis) {
		return new Date(millis);
	}

	@SuppressWarnings("deprecation")
	public static int getMonth(Date date) {
		return date.getMonth() + 1;
	}

	@SuppressWarnings("deprecation")
	public static int getWeekday(Date date) {
		return date.getDay() + 1;
	}

	@SuppressWarnings("deprecation")
	public static int getDay(Date date) {
		return date.getDate();
	}

	@SuppressWarnings("deprecation")
	public static int getYear(Date date) {
		return date.getYear() + 1900;
	}

	@SuppressWarnings("deprecation")
	public static int getHour(Date date) {
		return date.getHours();
	}

	@SuppressWarnings("deprecation")
	public static int getMinute(Date date) {
		return date.getMinutes();
	}

	@SuppressWarnings("deprecation")
	public static int getSecond(Date date) {
		return date.getSeconds();
	}

	public static Date getNowAsDate() {
		return createDate(getCurrentTimeMillis());
	}

}