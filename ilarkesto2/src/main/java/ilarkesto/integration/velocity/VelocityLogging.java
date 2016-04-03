/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.integration.velocity;

import ilarkesto.core.logging.Log;
import ilarkesto.core.logging.Log.Level;
import static ilarkesto.core.logging.Log.Level.DEBUG;
import static ilarkesto.core.logging.Log.Level.ERROR;
import static ilarkesto.core.logging.Log.Level.INFO;
import static ilarkesto.core.logging.Log.Level.WARN;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;

public class VelocityLogging implements LogChute {

	private static final Log log = Log.get(VelocityLogging.class);

	@Override
	public void init(RuntimeServices rs) throws Exception {
		log.info("init", rs);
	}

	@Override
	public void log(int level, String message) {
		log.log(mapLevel(level), message);
	}

	@Override
	public void log(int level, String message, Throwable t) {
		log.log(mapLevel(level), message, t);
	}

	@Override
	public boolean isLevelEnabled(int level) {
		if (level > DEBUG_ID) {
                        return true;
                }
		return log.isDebugEnabled();
	}

	private Level mapLevel(int level) {
		switch (level) {
			case ERROR_ID:
				return ERROR;
			case WARN_ID:
				return WARN;
			case INFO_ID:
				return INFO;
			default:
				return DEBUG;
		}
	}

}
