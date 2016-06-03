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
package ilarkesto.core.base;

public class ChangeIndicator {

	private Object cachedObject;

	public ChangeIndicator() {}

	public ChangeIndicator(Object cachedObject) {
		super();
		this.cachedObject = cachedObject;
	}

	public boolean update(Object newObject) {
		if (Utl.equals(newObject, cachedObject)) {
                        return false;
                }
		cachedObject = newObject;
		return true;
	}

	@Override
	public String toString() {
		return "ChangeIndicator: " + cachedObject;
	}

}
