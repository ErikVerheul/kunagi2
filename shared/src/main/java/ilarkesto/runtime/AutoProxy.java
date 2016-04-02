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
package ilarkesto.runtime;

import ilarkesto.base.Sys;
import static ilarkesto.base.Sys.isDevelopmentMode;
import static ilarkesto.base.Sys.setHttpProxy;
import ilarkesto.io.IO;
import static ilarkesto.io.IO.downloadUrlToString;

public class AutoProxy {

	public static void update() {
		if (!isDevelopmentMode()) {
                        return;
                }
		if (isHis()) {
			setHttpProxy("83.246.65.146", 80);
		}
	}

	private static boolean isHis() {
		try {
			return downloadUrlToString("http://xqisdev.his.de").contains("<title>qisdev</title>");
		} catch (Throwable ex) {
			return false;
		}
	}

}
