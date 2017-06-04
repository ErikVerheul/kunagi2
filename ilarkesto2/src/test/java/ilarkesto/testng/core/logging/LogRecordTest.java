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
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package ilarkesto.testNG.core.logging;

import static ilarkesto.core.time.Tm.getCurrentTimeMillis;
import static ilarkesto.logging.Log.Level.INFO;
import ilarkesto.logging.LogRecord;
import ilarkesto.testng.ATest;
import org.testng.annotations.Test;

public class LogRecordTest extends ATest {

    @Test
    public void hash() {
        LogRecord a = new LogRecord(getCurrentTimeMillis(), "A", INFO, "hello", "logger");
        LogRecord b = new LogRecord(getCurrentTimeMillis(), "A", INFO, "hello", "logger");
        LogRecord c = new LogRecord(getCurrentTimeMillis(), "A", INFO, "hello", "world");
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a.hashCode(), c.hashCode());
    }

    @Test
    public void equals() {
        LogRecord a = new LogRecord(getCurrentTimeMillis(), "A", INFO, "hello", "logger");
        LogRecord b = new LogRecord(getCurrentTimeMillis(), "A", INFO, "hello", "logger");
        LogRecord c = new LogRecord(getCurrentTimeMillis(), "A", INFO, "hello", "world");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, c);
    }

}
