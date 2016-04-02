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
package ilarkesto.core.scope;

import ilarkesto.core.base.Utl;
import static ilarkesto.core.base.Utl.getSimpleName;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

public class ScopeUtil {

	public static String getComponentName(Class componentType) {
		String name = getSimpleName(componentType);
		name = toLowerCase(name.charAt(0)) + name.substring(1);
		return name;
	}

	public static String getComponentSimpleClassName(String name) {
		return toUpperCase(name.charAt(0)) + name.substring(1);
	}

}
