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

import ilarkesto.json.JsonObject;
import static ilarkesto.json.jsondb.DocumentReference.getIds;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AJsonDb {

	public abstract JsonObject loadDocumentById(String id);

	public abstract List<DocumentReference> listAllDocuments();

	public abstract void saveDocument(JsonObject document);

	public abstract void deleteDocument(JsonObject document);

	public List<JsonObject> loadDocumentsByFilter(DocumentFilter filter) {
		List<DocumentReference> references = listAllDocuments();
		List<JsonObject> result = new ArrayList<>();
		for (DocumentReference reference : references) {
			JsonObject document = loadDocumentByReference(reference);
			if (filter.matches(document)) {
                                result.add(document);
                        }
		}
		return result;
	}

	public List<JsonObject> loadAllDocuments() {
		List<DocumentReference> references = listAllDocuments();
		return loadDocumentsByReferences(references);
	}

	public void deleteDocumentById(String id) {
		JsonObject document = loadDocumentById(id);
		deleteDocument(document);
	}

	public List<String> listAllDocumentIds() {
		return getIds(listAllDocuments());
	}

	public List<JsonObject> loadDocumentsByIds(Collection<String> ids) {
		List<JsonObject> documents = new ArrayList<>(ids.size());
		for (String id : ids) {
			JsonObject document = loadDocumentById(id);
			documents.add(document);
		}
		return documents;
	}

	public JsonObject loadDocumentByReference(DocumentReference reference) {
		return loadDocumentById(reference.getId());
	}

	public List<JsonObject> loadDocumentsByReferences(Collection<DocumentReference> references) {
		return loadDocumentsByIds(getIds(references));
	}

}
