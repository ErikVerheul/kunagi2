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
package ilarkesto.json.jsondb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DocumentReference {

	private String id;

	public DocumentReference(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return id;
	}

	public static List<String> getIds(Collection<DocumentReference> references) {
		List<String> ids = new ArrayList<>(references.size());
		for (DocumentReference reference : references) {
			ids.add(reference.getId());
		}
		return ids;
	}

}
