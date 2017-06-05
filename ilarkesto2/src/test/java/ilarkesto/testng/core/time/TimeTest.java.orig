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
import ilarkesto.testng.ATest;
import org.testng.annotations.Test;

/**
 *
 *
 */
public class TimeTest extends ATest {

    /**
     *
     */
    @Test
    public void constructionAndToString() {
        assertEquals(new Time(10, 9), new Time("10:09"));
    }

    /**
     *
     */
    @Test
    public void isAfter() {
        assertTrue(new Time(10, 10).isAfter(new Time(10, 9)));
        assertFalse(new Time(10, 10).isAfter(new Time(10, 10)));
        assertTrue(new Time(10, 10).isAfterOrSame(new Time(10, 10)));
    }

    /**
     *
     */
    @Test
    public void isBefore() {
        assertTrue(new Time(10, 10).isBefore(new Time(10, 11)));
        assertFalse(new Time(10, 10).isBefore(new Time(10, 10)));
        assertTrue(new Time(10, 10).isBeforeOrSame(new Time(10, 10)));
    }

}
