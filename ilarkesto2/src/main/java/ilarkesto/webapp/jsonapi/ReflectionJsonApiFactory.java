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

import ilarkesto.base.Reflect;
import static ilarkesto.base.Reflect.newInstance;
import ilarkesto.base.StrExtend;
import static ilarkesto.base.StrExtend.uppercaseFirstLetter;
import ilarkesto.webapp.AWebApplication;
import ilarkesto.webapp.AWebSession;
import ilarkesto.webapp.RequestWrapper;
import static java.lang.Class.forName;
import java.util.ArrayList;
import java.util.List;

public class ReflectionJsonApiFactory implements JsonApiFactory {

	private List<String> packages = new ArrayList<>();

	public ReflectionJsonApiFactory(AWebApplication webApplication) {
		addPackage(webApplication.getClass().getPackage());
	}

	public void addPackage(Package pkg) {
		addPackage(pkg.getName());
	}

	public void addPackage(String packageName) {
		packages.add(packageName);
	}

	@Override
	public AJsonApi createApi(RequestWrapper req, String path) {
		AJsonApi api = createApiInstance(path);
		if (api == null) {
                        return null;
                }

		AWebSession session = req.getSession();
		session.getContext().autowire(api);

		return api;
	}

	private AJsonApi createApiInstance(String path) {
		String classSimpleName = path.isEmpty() ? "RootApi" : uppercaseFirstLetter(path) + "Api";
		for (String pkg : packages) {
			String className = pkg + "." + classSimpleName;
			Class<? extends AJsonApi> type;
			try {
				type = (Class<? extends AJsonApi>) forName(className);
				return newInstance(type);
			} catch (ClassNotFoundException ex) {
			}
		}
		return null;
	}

}
