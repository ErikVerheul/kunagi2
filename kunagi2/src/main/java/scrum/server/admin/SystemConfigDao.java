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
package scrum.server.admin;

import java.util.ArrayList;
import java.util.List;

public class SystemConfigDao extends GSystemConfigDao {

	public SystemConfig getSystemConfig() {
		List<SystemConfig> all = new ArrayList<SystemConfig>(getEntities());

		if (all.isEmpty()) {
			SystemConfig config = newEntityInstance();
			saveEntity(config);
			return config;
		}

		while (all.size() > 1) {
			SystemConfig config = all.get(0);
			deleteEntity(config);
			all.remove(config);
		}

		return all.get(0);
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();
		getSystemConfig();
	}

}