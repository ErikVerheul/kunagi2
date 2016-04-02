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
package ilarkesto.base.time;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import static ilarkesto.base.StrExtend.tokenize;
import static ilarkesto.base.TmExtend.year;
import static ilarkesto.base.UtlExtend.randomInt;
import ilarkesto.base.time.TimePeriodExtend;
import static ilarkesto.core.time.Tm.DAY;
import static ilarkesto.core.time.Weekday.MONDAY;
import static java.lang.Integer.parseInt;
import static java.lang.System.currentTimeMillis;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.GregorianCalendar;
import java.util.Locale;
import static java.util.Locale.GERMANY;

@SuppressWarnings("SE_NO_SERIALVERSIONID")
public final class DateExtend extends ilarkesto.core.time.Date {

	public static final transient SimpleDateFormat FORMAT_DAY_MONTH_SHORTYEAR = new SimpleDateFormat("dd.MM.yy");
	public static final transient SimpleDateFormat FORMAT_DAY_MONTH_YEAR = new SimpleDateFormat("dd.MM.yyyy");
	public static final transient SimpleDateFormat FORMAT_LONGMONTH_DAY_YEAR = new SimpleDateFormat("MMMM d, yyyy");
	public static final transient SimpleDateFormat FORMAT_DAY_MONTH = new SimpleDateFormat("dd.MM.");
	public static final transient SimpleDateFormat FORMAT_WEEKDAY_DAY_MONTH = new SimpleDateFormat("EEEE, dd.MM.");
	public static final transient SimpleDateFormat FORMAT_DAY_LONGMONTH_YEAR = new SimpleDateFormat("dd. MMMM yyyy");
	public static final transient SimpleDateFormat FORMAT_WEEKDAY_DAY_LONGMONTH_YEAR = new SimpleDateFormat(
			"EEEE, dd. MMMM yyyy");
	public static final transient SimpleDateFormat FORMAT_SHORTWEEKDAY_DAY_MONTH_YEAR = new SimpleDateFormat(
			"EE, dd.MM.yyyy");
	public static final transient SimpleDateFormat FORMAT_SHORTWEEKDAY_SHORTMONTH_DAY = new SimpleDateFormat(
			"EE, MMM dd");
	public static final transient SimpleDateFormat FORMAT_LONGMONTH = new SimpleDateFormat("MMMM");
	public static final transient SimpleDateFormat FORMAT_LONGMONTH_YEAR = new SimpleDateFormat("MMMM yyyy");

	public static final transient SimpleDateFormat FORMAT_YEAR_MONTH_DAY = new SimpleDateFormat("yyyy-MM-dd");
	public static final transient SimpleDateFormat FORMAT_YEAR_MONTH = new SimpleDateFormat("yyyy-MM");
	public static final transient SimpleDateFormat FORMAT_YEAR_LONGMONTH = new SimpleDateFormat("yyyy-MMMM");
	public static final transient SimpleDateFormat FORMAT_YEAR_MONTH_DAY_NOSEP = new SimpleDateFormat("yyyyMMdd");

	public static final transient SimpleDateFormat FORMAT_WEEKDAY = new SimpleDateFormat("EEEE");

	public DateExtend() {
		super();
	}

	public DateExtend(java.util.Date javaDate) {
		super(javaDate);
	}

	public DateExtend(long millis) {
		super(millis);
	}

	public DateExtend(int year, int month, int day) {
		super(year, month, day);
	}

	public DateExtend(String date) {
		super(date);
	}

	public DateExtend(GregorianCalendar calendar) {
		this(calendar.getTime());
	}

	@Override
	protected DateExtend newDate(java.util.Date javaDate) {
		return new DateExtend(javaDate);
	}

	public GregorianCalendar getGregorianCalendar() {
		return new GregorianCalendar(year, month - 1, day);
	}

        @Override
	public DateExtend getMondayOfWeek() {
		if (getWeekday() == MONDAY) {
                        return this;
                }
		return addDays(-1).getMondayOfWeek();
	}

        @Override
	public DateExtend getFirstDateOfMonth() {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(toJavaDate());
		c.set(DAY_OF_MONTH, 1);
		return new DateExtend(c);
	}

        @Override
	public DateExtend getLastDateOfMonth() {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(toJavaDate());
		c.set(DAY_OF_MONTH, c.getActualMaximum(DAY_OF_MONTH));
		return new DateExtend(c);
	}

        @Override
	public DateExtend addMonths(int count) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(toJavaDate());
		c.add(MONTH, count);
		return new DateExtend(c);
	}

        @Override
	public DateExtend addYears(int count) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(toJavaDate());
		c.add(YEAR, count);
		return new DateExtend(c);
	}

        @Override
	public int getDaysInMonth() {
		return getGregorianCalendar().getActualMaximum(DAY_OF_MONTH);
	}

	public TimePeriodExtend getPeriodTo(DateExtend other) {
		return new TimePeriodExtend(other.toMillis() - toMillis());
	}

	public TimePeriodExtend getPeriodToNow() {
		return getPeriodTo(today());
	}

	public int getPeriodToInYears(DateExtend other) {
		int years = other.year - year;
		if (month > other.month) {
			years--;
		} else if (month == other.month && day > other.day) {
			years--;
		}
		return years;
	}

	public int getPeriodToInMonths(DateExtend other) {
		int years = other.year - year;
		int months = other.month - month;
		return (years * 12) + months;
	}

	public int getPeriodToNowInMonths() {
		return getPeriodToInMonths(today());
	}

	private String toDe() {
		StringBuilder sb = new StringBuilder();
		if (day < 10) {
                        sb.append('0');
                }
		sb.append(day);
		sb.append(".");
		if (month < 10) {
                        sb.append('0');
                }
		sb.append(month);
		sb.append(".");
		sb.append(year);
		return sb.toString();
	}

	private String toInt() {
		return toString();
	}

	public String toString(DateFormat format) {
		return format.format(toJavaDate());
	}

	public String toString(Locale locale) {
		if (locale.equals(GERMANY)) {
                        return toDe();
                }
		return toInt();
	}

	@Override
	public DateExtend addDays(int days) {
		return (DateExtend) super.addDays(days);
	}

	@Override
	public DateExtend prevDay() {
		return addDays(-1);
	}

	@Override
	public DateExtend nextDay() {
		return addDays(1);
	}

	// --- static ---

	private static transient DateExtend today;

	private static transient long todayInvalidTime;

	public static DateExtend latest(DateExtend... dates) {
		DateExtend latest = null;
		for (DateExtend date : dates) {
			if (latest == null || date.isAfter(latest)) {
                                latest = date;
                        }
		}
		return latest;
	}

	public static DateExtend earliest(DateExtend... dates) {
		DateExtend earliest = null;
		for (DateExtend date : dates) {
			if (earliest == null || date.isBefore(earliest)) {
                                earliest = date;
                        }
		}
		return earliest;
	}

	public static DateExtend today() {
		if (today == null || currentTimeMillis() > todayInvalidTime) {
			today = new DateExtend();
			todayInvalidTime = tomorrow().toJavaDate().getTime() - 1;
		}
		return today;
	}

	public static DateExtend tomorrow() {
		return new DateExtend(currentTimeMillis() + DAY);
	}

	public static DateExtend inDays(int numberOfDays) {
		return new DateExtend(currentTimeMillis() + (DAY * numberOfDays));
	}

	public static DateExtend beforeDays(int numberOfDays) {
		return new DateExtend(currentTimeMillis() - (DAY * numberOfDays));
	}

	public static DateExtend randomPast(int beforeMaxDays) {
		return beforeDays(randomInt(0, beforeMaxDays));
	}

	public static DateExtend parseTolerant(String s) throws ParseException {
		s = s.trim();
		String[] sa = tokenize(s, ".,- ");
		if (sa.length == 0) {
                        throw new ParseException("Not a Date: " + s, -1);
                }
		if (sa.length > 3) {
                        throw new ParseException("Not a Date: " + s, -1);
                }
		int[] ia = new int[sa.length];
		for (int i = 0; i < ia.length; i++) {
			try {
				ia[i] = parseInt(sa[i]);
			} catch (NumberFormatException e) {
				throw new ParseException("Not a Date: " + s, -1);
			}
		}

		if (ia.length == 3) {
                        return new DateExtend(year(ia[2]), ia[1], ia[0]);
                }

		DateExtend local_today = today();
		if (ia.length == 2) {
			if (ia[1] > 12) {
                                return new DateExtend(year(ia[1]), ia[0], local_today.day);
                        }
			return new DateExtend(local_today.year, ia[1], ia[0]);
		}

		if (ia[0] > 31) {
                        return new DateExtend(year(ia[0]), local_today.month, local_today.day);
                }
		return new DateExtend(local_today.year, local_today.month, ia[0]);
	}

	// --- Object ---

	public boolean equalsIgnoreYear(DateExtend d) {
		if (d == null) {
                        return false;
                }
		return d.day == day && d.month == month;
	}

	public boolean equalsIgnoreDay(DateExtend d) {
		if (d == null) {
                        return false;
                }
		return d.year == year && d.month == month;
	}

}
