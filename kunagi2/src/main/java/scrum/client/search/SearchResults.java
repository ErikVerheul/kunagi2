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
package scrum.client.search;

import static ilarkesto.core.logging.ClientLog.INFO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import scrum.client.common.AScrumGwtEntity;

public class SearchResults {

	private final Collection<AScrumGwtEntity> entities = new HashSet<AScrumGwtEntity>();

	public boolean isEmpty() {
		return entities.isEmpty();
	}

	public void clear() {
		if (entities.isEmpty()) return;
		entities.clear();
		new SearchResultsChangedEvent().fireInCurrentScope();
	}

	public void addEntities(List<? extends AScrumGwtEntity> entities) {
		boolean changed = this.entities.addAll(entities);
		if (changed) {
			INFO("SearchResults:", this.entities.size());
			new SearchResultsChangedEvent().fireInCurrentScope();
		}
	}

	public Map<String, List<AScrumGwtEntity>> getEntitiesGrouped() {
		Map<String, List<AScrumGwtEntity>> ret = new LinkedHashMap<String, List<AScrumGwtEntity>>();
		for (AScrumGwtEntity entity : entities) {
			String key = entity.getClass().getName();
			List<AScrumGwtEntity> list = ret.get(key);
			if (list == null) {
				list = new ArrayList<AScrumGwtEntity>();
				ret.put(key, list);
			}
			list.add(entity);
		}
		return ret;
	}

}
