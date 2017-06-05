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
package ilarkesto.testng.core.time;

import static ilarkesto.core.base.Utl.toList;
import ilarkesto.core.time.Tm;
import static ilarkesto.core.time.Tm.createDate;
import ilarkesto.testng.ATest;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TmTest extends ATest {

    private java.util.Date birthday;
    private java.util.Date armageddon;
    private List<Integer> leapYears;

    @BeforeMethod
    public void init() {
        birthday = createDate(1979, 8, 3);
        armageddon = createDate(2012, 12, 21);
        leapYears = toList(1904, 1908, 1912, 1916, 1920, 1924, 1928, 1932, 1936, 1940, 1944, 1948, 1952, 1956,
                1960, 1964, 1968, 1972, 1976, 1980, 1984, 1988, 1992, 1996, 2000, 2004, 2008, 2012, 2016, 2020, 2024, 2028,
                2032, 2036, 2040, 2044, 2048, 2052, 2056, 2060, 2064, 2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096);
    }

    @Test
    public void getDateOfFirstWeek() {
        assertEquals(Tm.getDateOfFirstWeek(2005), createDate(2005, 1, 3));
        assertEquals(Tm.getDateOfFirstWeek(2006), createDate(2006, 1, 2));
        assertEquals(Tm.getDateOfFirstWeek(2007), createDate(2007, 1, 1));
        assertEquals(Tm.getDateOfFirstWeek(2008), createDate(2007, 12, 31));
        assertEquals(Tm.getDateOfFirstWeek(2009), createDate(2008, 12, 29));
        assertEquals(Tm.getDateOfFirstWeek(2010), createDate(2010, 1, 4));
        assertEquals(Tm.getDateOfFirstWeek(2011), createDate(2011, 1, 3));
        assertEquals(Tm.getDateOfFirstWeek(2012), createDate(2012, 1, 2));
    }

    @Test
    public void isLeapYear() {
        for (int i = 1900; i < 2099; i++) {
            boolean leapYear = leapYears.contains(i);
            assertEquals(Tm.isLeapYear(i), leapYear);
        }
    }

    @Test
    public void getDaysInMonth() {
        assertEquals(Tm.getDaysInMonth(2001, 12), 31);
    }

    @Test
    public void getWeek() {
        assertEquals(Tm.getWeek(createDate(2011, 4, 6)), 14);
        assertEquals(Tm.getWeek(createDate(2007, 12, 31)), 1);
        for (int i = 2000; i < 2100; i++) {
            assertEquals(Tm.getWeek(createDate(i, 1, 4)), 1);
        }
    }

    @Test
    public void getDaysBetweenDates() {
        assertEquals(Tm.getDaysBetweenDates(createDate(2011, 3, 1), createDate(2011, 3, 2)), 1);
        assertEquals(Tm.getDaysBetweenDates(createDate(2011, 3, 1), createDate(2011, 3, 1)), 0);
        assertEquals(Tm.getDaysBetweenDates(createDate(2011, 3, 1), createDate(2011, 3, 30)), 29);
    }

    @Test
    public void getWeekday() {
        assertEquals(Tm.getWeekday(birthday), FRIDAY);
        assertEquals(Tm.getWeekday(armageddon), FRIDAY);
    }

    @Test
    public void preconditions() {
        Date date = createDate(1979, 8, 3);
        assertEquals(date, birthday);
        assertEquals(Tm.getYear(date), 1979);
        assertEquals(Tm.getMonth(date), 8);
        assertEquals(Tm.getDay(date), 3);
    }

    @Test
    public void addDays() {
        assertEquals(Tm.addDays(birthday, 1), createDate(1979, 8, 4));
        assertEquals(Tm.addDays(birthday, -1), createDate(1979, 8, 2));

        assertEquals(Tm.addDays(createDate(2011, 2, 28), 1), createDate(2011, 3, 1));
        assertEquals(Tm.addDays(createDate(2012, 2, 28), 1), createDate(2012, 2, 29));
        assertEquals(Tm.addDays(createDate(2012, 2, 29), 1), createDate(2012, 3, 1));

        assertEquals(Tm.addDays(createDate(2012, 12, 1), 31), createDate(2013, 1, 1));
    }

    @Test
    public void getDay() {
        assertEquals(Tm.getDay(birthday), 3);
        assertEquals(Tm.getDay(armageddon), 21);
    }

    @Test
    public void getMonth() {
        assertEquals(Tm.getMonth(birthday), 8);
        assertEquals(Tm.getMonth(armageddon), 12);
    }

    @Test
    public void getYear() {
        assertEquals(Tm.getYear(birthday), 1979);
        assertEquals(Tm.getYear(armageddon), 2012);
    }

    @Test
    public void createDateFuture() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(createDate(2066, 10, 23));
        assertEquals(cal.get(YEAR), 2066);
        assertEquals(cal.get(MONTH), 9);
        assertEquals(cal.get(DAY_OF_MONTH), 23);
        assertEquals(cal.get(HOUR_OF_DAY), 0);
        assertEquals(cal.get(MINUTE), 0);
    }

    @Test
    public void createDatePast() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(createDate(1979, 8, 3));
        assertEquals(cal.get(YEAR), 1979);
        assertEquals(cal.get(MONTH), 7);
        assertEquals(cal.get(DAY_OF_MONTH), 3);
        assertEquals(cal.get(HOUR_OF_DAY), 0);
        assertEquals(cal.get(MINUTE), 0);
    }

}
