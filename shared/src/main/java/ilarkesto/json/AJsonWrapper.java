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
package ilarkesto.json;

import static ilarkesto.core.base.Str.isBlank;
import ilarkesto.json.Json.JsonWrapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import static java.util.Collections.emptyList;
import java.util.List;

public abstract class AJsonWrapper implements JsonWrapper {

	protected final JsonObject json;

	public AJsonWrapper(JsonObject json) {
		if (json == null) {
                        throw new IllegalArgumentException("json == null");
                }
		this.json = json;
	}

	public AJsonWrapper() {
		this(new JsonObject());
	}

	protected void putArray(String name, Iterable<? extends AJsonWrapper> wrappers) {
		json.put(name, getJsonObjects(wrappers));
	}

	protected static List<JsonObject> getJsonObjects(Iterable<? extends AJsonWrapper> wrappers) {
		List<JsonObject> ret = new ArrayList<>();
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

	protected <T extends AJsonWrapper> List<T> createFromArray(String name, Class<T> type) {
		List<JsonObject> array = json.getArrayOfObjects(name);
		if (array == null || array.isEmpty()) {
                        return emptyList();
                }

		List<T> wrappers = new ArrayList<>(array.size());
		for (JsonObject object : array) {
			T wrapper = createWrapper(object, type);
			wrappers.add(wrapper);
		}

		return wrappers;
	}

	protected <T extends AJsonWrapper> T createFromObject(String name, Class<T> type) {
		JsonObject object = json.getObject(name);
		if (object == null) {
                        return null;
                }
		return createWrapper(object, type);
	}

	private <T extends AJsonWrapper> T createWrapper(JsonObject json, Class<T> type) {
		Constructor<T> constructor;
		try {
			constructor = type.getConstructor(JsonObject.class);
		} catch (NoSuchMethodException | SecurityException ex) {
			throw new RuntimeException("Loading constructor for " + type.getName() + " failed.", ex);
		}
		T wrapper;
		try {
			wrapper = constructor.newInstance(json);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new RuntimeException("Instantiating " + type.getName() + " failed.", ex);
		}
		return wrapper;
	}

	@Override
	public JsonObject getJson() {
		return json;
	}

	@Override
	public String toString() {
		return json.toFormatedString();
	}

}
