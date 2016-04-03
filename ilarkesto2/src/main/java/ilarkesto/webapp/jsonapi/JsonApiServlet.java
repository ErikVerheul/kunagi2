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

import static ilarkesto.core.base.Str.cutFrom;
import static ilarkesto.core.base.Str.isBlank;
import static ilarkesto.core.base.Str.removeSuffix;
import ilarkesto.core.logging.Log;
import ilarkesto.json.JsonObject;
import ilarkesto.webapp.AServlet;
import ilarkesto.webapp.AWebApplication;
import ilarkesto.webapp.AWebSession;
import ilarkesto.webapp.RequestWrapper;
import java.io.IOException;

public class JsonApiServlet extends AServlet<AWebApplication, AWebSession> {

	private static final Log log = Log.get(JsonApiServlet.class);

	@Override
	protected void onGet(RequestWrapper req) throws IOException {
		AJsonApi api = createApi(req);
		boolean binary = api.doBinaryGet();
		if (binary) {
                        return;
                }
		writeGet(req, api);
	}

	@Override
	protected void onPost(RequestWrapper req) throws IOException {
		AJsonApi api = createApi(req);
		update(req, api);
		writeGet(req, api);
	}

	private void update(RequestWrapper req, AJsonApi api) {
		JsonObject json = req.readContentToJson();
		log.info(json.toFormatedString());
		api.doPost(json);
	}

	private void writeGet(RequestWrapper req, AJsonApi api) throws IOException {
		JsonObject result = api.doGet();
		req.write(result);
	}

	private AJsonApi createApi(RequestWrapper req) {
		String path = cutFrom(req.getUriWithoutContext(), "api/");
		log.info(path);
		String subpath = null;
		int idx = path.indexOf('/');
		if (idx >= 0) {
			subpath = path.substring(idx + 1);
			subpath = removeSuffix(subpath, "/");
			if (isBlank(subpath)) {
                                subpath = null;
                        }
			path = path.substring(0, idx);
		}
		AJsonApi api = webApplication.getRestApiFactory().createApi(req, path);
		api.init(req, subpath);
		return api;
	}

}
