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
package ilarkesto.scope;

/**
 *
 *
 */
public class ComponentInstantiationFailedException extends RuntimeException {

    /**
     *
     * @param name
     * @param type
     * @param cause
     */
    public ComponentInstantiationFailedException(String name, Class type, Exception cause) {
		super("Instantiating component failed: " + name + " -> " + type.getName(), cause);
	}

}
