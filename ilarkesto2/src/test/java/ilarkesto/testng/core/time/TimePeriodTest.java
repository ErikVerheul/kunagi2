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

import ilarkesto.core.time.Time;
import ilarkesto.core.time.TimePeriod;
import static ilarkesto.core.time.TimePeriod.days;
import static ilarkesto.core.time.TimePeriod.hours;
import static ilarkesto.core.time.TimePeriod.minutes;
import static ilarkesto.core.time.TimePeriod.seconds;
import static ilarkesto.core.time.TimePeriod.weeks;
import static ilarkesto.core.time.Tm.DAY;
import static ilarkesto.core.time.Tm.HOUR;
import static ilarkesto.core.time.Tm.MINUTE;
import static ilarkesto.core.time.Tm.MONTH;
import static ilarkesto.core.time.Tm.SECOND;
import static ilarkesto.core.time.Tm.WEEK;
import ilarkesto.testng.ATest;
import org.testng.annotations.Test;

/**
 *
 *
 */
public class TimePeriodTest extends ATest {

    /**
     *
     */
    @Test
    public void subtract() {
        assertEquals(hours(8).subtract(hours(1)), new TimePeriod("7:00"));
        assertEquals(hours(8).subtract(minutes(30)), new TimePeriod("7:30"));
    }

    /**
     *
     */
    @Test
    public void getPeriodTo() {
        Time begin = new Time("9:00");
        Time end = new Time("17:30");

        assertEquals(begin.getPeriodTo(end), new TimePeriod(8 * HOUR + 30 * MINUTE));
    }

    /**
     *
     */
    @Test
    public void constructors() {
        assertEquals(seconds(1).toMillis(), 1000);
        assertEquals(minutes(1).toMillis(), 60000);
        assertEquals(hours(1).toMillis(), 3600000);

        assertEquals(days(1).toMillis(), 86400000);
        assertEquals(days(2).toMillis(), 86400000l * 2);
        assertEquals(days(5).toMillis(), 86400000l * 5);
        assertEquals(days(15).toMillis(), 86400000l * 15);
        assertEquals(days(24).toMillis(), 86400000l * 24);
        assertEquals(days(25).toMillis(), 86400000l * 25);
        assertEquals(days(30).toMillis(), 86400000l * 30);

        assertEquals(weeks(1).toMillis(), 604800000);
    }

    /**
     *
     */
    @Test
    public void toDecimalDays() {
        assertEquals(days(1).toDecimalDays(), 1.0f, 0.0001f);
        assertEquals(hours(12).toDecimalDays(), 0.5f, 0.0001f);
    }

    /**
     *
     */
    @Test
    public void toDays() {
        assertEquals(new TimePeriod(DAY).toDays(), 1);
        assertEquals(new TimePeriod(DAY * 2).toDays(), 2);
        assertEquals(new TimePeriod(DAY * 13).toDays(), 13);
        assertEquals(new TimePeriod(DAY * 512).toDays(), 512);
    }

    /**
     *
     */
    @Test
    public void toShortestString() {
        assertEquals(new TimePeriod(SECOND * 20).toShortestString("en"), "20 seconds");
        assertEquals(new TimePeriod(MINUTE).toShortestString("en"), "1 minute");
        assertEquals(new TimePeriod(HOUR * 3).toShortestString("en"), "3 hours");
        assertEquals(new TimePeriod(DAY * 7).toShortestString("en"), "7 days");
        assertEquals(new TimePeriod(WEEK * 2).toShortestString("en"), "2 weeks");
        assertEquals(new TimePeriod(MONTH * 11).toShortestString("en"), "11 months");
    }

    /**
     *
     */
    @Test
    public void constructorMillis() {
        assertEquals(new TimePeriod("1").toMillis(), 1);
    }

    /**
     *
     */
    @Test
    public void constructorTime() {
        assertEquals(new TimePeriod("0:0:0:1").toMillis(), 1);
    }

}
