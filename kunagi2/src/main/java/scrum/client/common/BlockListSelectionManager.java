/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.common;

import java.util.HashSet;
import java.util.Set;

public class BlockListSelectionManager {

	private Set<BlockListWidget> lists = new HashSet<BlockListWidget>();

	public void clear() {
		lists.clear();
	}

	public void add(BlockListWidget list) {
		if (list == null) return;
		lists.add(list);
	}

	public void remove(BlockListWidget list) {
		if (list == null) return;
		lists.remove(list);
	}

	public void deselectAll() {
		for (BlockListWidget list : lists)
			list.collapseAll();
	}

	public boolean select(Object object) {
		for (BlockListWidget list : lists) {
			if (list.contains(object) && list.showObject(object)) return true;
		}
		return false;
	}
}
