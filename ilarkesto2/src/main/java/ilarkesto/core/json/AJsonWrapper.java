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
package ilarkesto.core.json;

import static ilarkesto.core.base.Str.isBlank;
import java.util.ArrayList;
import java.util.List;

public abstract class AJsonWrapper {

	protected final JsonObject json;

	public AJsonWrapper(JsonObject json) {
		if (json == null) {
                        throw new IllegalArgumentException("json == null");
                }
		this.json = json;
	}

	protected void putArray(String name, Iterable<? extends AJsonWrapper> wrappers) {
		json.put(name, getJsonObjects(wrappers));
	}

	protected static List<JsonObject> getJsonObjects(Iterable<? extends AJsonWrapper> wrappers) {
		List<JsonObject> ret = new ArrayList<JsonObject>();
		for (AJsonWrapper wrapper : wrappers) {
                        ret.add(wrapper.getJson());
                }
		return ret;
	}

	protected void putOrRemove(String name, String value) {
		if (isBlank(value)) {
			json.remove(name);
		} else {
			json.put(name, value);
		}
	}

	protected void putOrRemove(String name, boolean value) {
		if (value) {
			json.put(name, value);
		} else {
			json.remove(name);
		}
	}
        
//      not used and commented ou because GWT does not recognize java.lang.reflect.Constructor
//	protected <T extends AJsonWrapper> List<T> createFromArray(String name, Class<T> type) {
//		List<JsonObject> array = json.getArrayOfObjects(name);
//		if (array == null || array.isEmpty()) return Collections.emptyList();
//
//		List<T> wrappers = new ArrayList<T>(array.size());
//		for (JsonObject object : array) {
//			T wrapper = createWrapper(object, type);
//			wrappers.add(wrapper);
//		}
//
//		return wrappers;
//	}
//
//	private <T extends AJsonWrapper> T createWrapper(JsonObject json, Class<T> type) {
//		Constructor<T> constructor;
//		try {
//			constructor = type.getConstructor(JsonObject.class);
//		} catch (Exception ex) {
//			throw new RuntimeException("Loading constructor for " + type.getName() + " failed.", ex);
//		}
//		T wrapper;
//		try {
//			wrapper = constructor.newInstance(json);
//		} catch (Exception ex) {
//			throw new RuntimeException("Instantiating " + type.getName() + " failed.", ex);
//		}
//		return wrapper;
//	}

	public JsonObject getJson() {
		return json;
	}

}
