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
package ilarkesto.base;

import static ilarkesto.core.time.Tm.getCurrentTimeMillis;
import ilarkesto.logging.Log;
import static java.lang.String.valueOf;

/**
 * Simple tool for measuring time between calls.
 */
public class TimeMonitor {

    private static final Log LOG = Log.get(Cache.class);

    private final long start = getCurrentTimeMillis();

    /**
     *
     * @return
     */
    public long getTime() {
        return getCurrentTimeMillis() - start;
    }

    /**
     *
     * @param name
     */
    public void debugOut(String name) {
        if (name == null) {
            name = "TimeMonitor";
        }
        LOG.debug(name, "->", this);
    }

    @Override
    public String toString() {
        return valueOf(getTime()) + " ms.";
    }

}
