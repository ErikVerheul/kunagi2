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
package ilarkesto.form;

import ilarkesto.base.StringProvider;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.fileupload.FileItem;

public class MultiCheckboxFormField<T> extends AFormField {

	private Set<T> value;

	private List<T> selectableItems;
	private StringProvider<T> itemTooltipProvider;
	private StringProvider<T> itemImageUrlProvider;

	public MultiCheckboxFormField(String name) {
		super(name);
	}

	public MultiCheckboxFormField<T> setItemTooltipProvider(StringProvider<T> itemTooltipProvider) {
		this.itemTooltipProvider = itemTooltipProvider;
		return this;
	}

	public StringProvider<T> getItemTooltipProvider() {
		return itemTooltipProvider;
	}

	public MultiCheckboxFormField<T> setItemImageUrlProvider(StringProvider<T> itemImageUrlProvider) {
		this.itemImageUrlProvider = itemImageUrlProvider;
		return this;
	}

	public StringProvider<T> getItemImageUrlProvider() {
		return itemImageUrlProvider;
	}

	public List<T> getSelectableItems() {
		return selectableItems;
	}

	public MultiCheckboxFormField<T> setSelectableItems(Collection<T> items) {
		this.selectableItems = items == null ? new ArrayList<T>() : new ArrayList<>(items);
		return this;
	}

	public MultiCheckboxFormField<T> setValue(Set<T> value) {
		if (value == null) {
			this.value = null;
		} else {
			this.value = new HashSet<>(value);
			this.value.retainAll(selectableItems);
		}
		return this;
	}

	public boolean isSelected(T item) {
		return value != null && value.contains(item);
	}

	public Set<T> getValue() {
		return value;
	}

	@Override
	public String getValueAsString() {
		return value == null ? "0" : valueOf(value.size());
	}

	@Override
	public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
		value = new HashSet<>();
		int index = 0;
		for (T item : selectableItems) {
			if (data.containsKey(getName() + '_' + index)) {
                                value.add(item);
                        }
			index++;
		}
	}

	@Override
	public void validate() throws ValidationException {
		if (isRequired() && (value == null || value.isEmpty())) {
                        throw new ValidationException("Hier ist eine Auswahl erforderlich.");
                }
	}

	// --- dependencies ---

}
