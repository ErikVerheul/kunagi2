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
package ilarkesto.testNG.core.time;

import ilarkesto.core.time.Date;
import static ilarkesto.core.time.TimePeriod.days;
import static ilarkesto.core.time.Weekday.FRIDAY;
import ilarkesto.testng.ATest;
import static java.util.Calendar.DAY_OF_YEAR;
import java.util.GregorianCalendar;
import org.testng.annotations.Test;

public class DateTest extends ATest {

	private static final Date BIRTHDAY = new Date(1979, 8, 3);

	@Test
	public void getWeek() {
		assertEquals(new Date(2011, 4, 6).getWeek(), 14);
	}

	@Test
	public void getPeriodTo() {
		assertEquals(new Date(2011, 3, 1).getPeriodTo(new Date(2011, 3, 2)), days(1));
		assertEquals(new Date(2011, 3, 1).getPeriodTo(new Date(2011, 3, 1)), days(0));
		assertEquals(new Date(2011, 3, 1).getPeriodTo(new Date(2011, 3, 20)), days(19));
		assertEquals(new Date(2011, 3, 1).getPeriodTo(new Date(2011, 3, 30)), days(29));
	}

	@Test
	public void getWeekday() {
		assertEquals(BIRTHDAY.getWeekday(), FRIDAY);
	}

	@Test
	public void addDays() {
		assertEquals(BIRTHDAY.addDays(1), new Date(1979, 8, 4));
		assertEquals(BIRTHDAY.addDays(-1), new Date(1979, 8, 2));
		assertEquals(BIRTHDAY.addDays(0), BIRTHDAY);

		assertEquals(new Date(2010, 12, 31).addDays(10), new Date(2011, 1, 10));

		assertEquals(new Date(2011, 2, 28).addDays(1), new Date(2011, 3, 1));
		assertEquals(new Date(2008, 2, 28).addDays(1), new Date(2008, 2, 29));

		assertEquals(new Date(2011, 3, 1).addDays(28), new Date(2011, 3, 29));
	}

	@Test
	public void addMonths() {
		assertEquals(BIRTHDAY.addMonths(12), new Date(1980, 8, 3));
		assertEquals(BIRTHDAY.addMonths(-12), new Date(1978, 8, 3));
		assertEquals(BIRTHDAY.addMonths(4), new Date(1979, 12, 3));

		assertEquals(new Date(2011, 1, 1).addMonths(-1), new Date(2010, 12, 1));
		assertEquals(new Date(2011, 1, 31).addMonths(1), new Date(2011, 2, 28));
	}

	@Test
	public void addYears() {
		assertEquals(BIRTHDAY.addYears(10), new Date(1989, 8, 3));
		assertEquals(BIRTHDAY.addYears(-10), new Date(1969, 8, 3));

		assertEquals(new Date(2012, 2, 29).addYears(1), new Date(2013, 2, 28));
	}

	@Test
	public void addDaysWithCalendar() {
		Date date = new Date(2010, 1, 1);
		for (int i = -10000; i < 10000; i++) {
			assertAddDays(date, i);
		}
	}

	private void assertAddDays(Date begin, int days) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(begin.toMillis());
		assertEquals(begin, new Date(calendar.getTime()));

		calendar.add(DAY_OF_YEAR, days);

		assertEquals(begin.addDays(days), new Date(calendar.getTime()));
	}

	@Test
	public void isToday() {
		assertTrue(new Date().isToday());
		assertFalse(BIRTHDAY.isToday());
	}

	@Test
	public void isBetween() {
		assertTrue(BIRTHDAY.isBetween(new Date(1979, 8, 2), new Date(1979, 8, 4), true));

		assertTrue(BIRTHDAY.isBetween(new Date(1979, 8, 3), new Date(1979, 8, 3), true));
		assertFalse(BIRTHDAY.isBetween(new Date(1979, 8, 3), new Date(1979, 8, 3), false));
	}

	@Test
	public void isBefore() {
		assertTrue(BIRTHDAY.isBefore(new Date(1979, 8, 4)));
		assertFalse(BIRTHDAY.isBefore(new Date(1979, 8, 2)));
		assertFalse(BIRTHDAY.isBefore(new Date(1979, 8, 3)));
	}

	@Test
	public void isAfter() {
		assertTrue(BIRTHDAY.isAfter(new Date(1979, 8, 2)));
		assertFalse(BIRTHDAY.isAfter(new Date(1979, 8, 4)));
		assertFalse(BIRTHDAY.isAfter(new Date(1979, 8, 3)));
	}
}
