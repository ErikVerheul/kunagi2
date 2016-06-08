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
package ilarkesto.webapp.jsonapi;

import ilarkesto.json.JsonObject;
import ilarkesto.webapp.RequestWrapper;

public abstract class AJsonApi {

	// --- dependencies ---

	protected RequestWrapper request;
	private String path;

	public void init(RequestWrapper request, String path) {
		this.request = request;
		this.path = path;
	}

	// --- ---

	protected void onGet(JsonObject json, String path) {}

	protected void onPost(JsonObject json, String path) {
		throw new RuntimeException("POST not supported");
	}

	protected void onBinaryGet(String path) {}

	public final JsonObject doGet() {
		JsonObject json = new JsonObject();
		onGet(json, path);
		return json;
	}

	public final void doPost(JsonObject update) {
		onPost(update, path);
	}

	public final boolean doBinaryGet() {
		onBinaryGet(path);
		return request.isResponseServed();
	}

}
