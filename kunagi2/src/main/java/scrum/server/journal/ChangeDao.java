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
package scrum.server.journal;

import ilarkesto.base.AFactoryCache;
import ilarkesto.base.Cache;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.fp.Predicate;
import ilarkesto.persistence.AEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import scrum.server.admin.User;

public class ChangeDao extends GChangeDao {

	@Override
	public void clearCaches() {
		super.clearCaches();
		changesByParentIdCache.clear();
	}

	public Change postChange(AEntity parent, User user, String key, Object oldValue, Object newValue) {
		Change change = newEntityInstance();
		change.setDateAndTime(DateAndTime.now());
		change.setParent(parent);
		change.setUser(user);
		change.setKey(key);
		change.setOldValue(oldValue == null ? null : oldValue.toString());
		change.setNewValue(newValue == null ? null : newValue.toString());
		saveEntity(change);

		Change.merge(getChangesByParent(parent));

		return change;
	}

	private final Cache<String, Set<Change>> changesByParentIdCache = new AFactoryCache<String, Set<Change>>() {

		@Override
		public Set<Change> create(final String parentId) {
			return getEntities(new Predicate<Change>() {

				@Override
				public boolean test(Change e) {
					return e.getParent().getId().equals(parentId);
				}
			});
		}

	};

	public Set<Change> getChangesByParentId(final String parentId) {
		return changesByParentIdCache.get(parentId);
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();

		Map<AEntity, List<Change>> changesByParent = new HashMap<AEntity, List<Change>>();
		for (Change change : getEntities()) {
			List<Change> list = changesByParent.get(change.getParent());
			if (list == null) {
				list = new ArrayList<Change>();
				changesByParent.put(change.getParent(), list);
			}
			list.add(change);
		}
		for (List<Change> changes : changesByParent.values()) {
			Change.merge(changes);
		}
	}
}