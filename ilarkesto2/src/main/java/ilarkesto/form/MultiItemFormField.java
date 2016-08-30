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
import static ilarkesto.form.Form.ADD_ITEM_BUTTON_NAME_PREFIX;
import static ilarkesto.form.Form.BUTTON_PREFIX;
import static ilarkesto.form.Form.REMOVE_ALLITEMS_BUTTON_NAME_PREFIX;
import static ilarkesto.form.Form.REMOVE_ITEM_BUTTON_NAME_PREFIX;
import ilarkesto.id.CountingIdGenerator;
import ilarkesto.id.IdGenerator;
import static java.lang.String.valueOf;
import java.util.Collection;
import static java.util.Collections.emptyList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.fileupload.FileItem;

/**
 *
 *
 * @param <T>
 */
public class MultiItemFormField<T> extends AFormField {

	private Set<T> value;

	private AddButton addButton;

	private RemoveAllButton removeAllButton;

	private Collection<T> selectableItems;

	private Collection<MultiItem<T>> selectableMultiItems = emptyList();

	private Map<T, RemoveButton<T>> removeButtons = new HashMap<>();

	private StringProvider<T> itemTooltipProvider;

	private StringProvider<T> itemLabelProvider;

	private StringProvider<T> itemImageUrlProvider;

	private String noSelectionLabel;

    /**
     *
     * @param name
     */
    public MultiItemFormField(String name) {
		super(name);

		addButton = (AddButton) new AddButton().setValidateForm(false).setLabel("Hinzuf\u00FCgen...").setIcon("add");
		removeAllButton = (RemoveAllButton) new RemoveAllButton().setValidateForm(false).setLabel("Alle entferenen")
				.setIcon("delete");
	}

    /**
     *
     * @param noSelectionLabel
     * @return
     */
    public MultiItemFormField<T> setNoSelectionLabel(String noSelectionLabel) {
		this.noSelectionLabel = noSelectionLabel;
		return this;
	}

    /**
     *
     * @return
     */
    public String getNoSelectionLabel() {
		return noSelectionLabel;
	}

    /**
     *
     * @param itemLabelProvider
     * @return
     */
    public MultiItemFormField<T> setItemLabelProvider(StringProvider<T> itemLabelProvider) {
		this.itemLabelProvider = itemLabelProvider;
		return this;
	}

    /**
     *
     * @return
     */
    public StringProvider<T> getItemLabelProvider() {
		return itemLabelProvider;
	}

    /**
     *
     * @param imageUrlProvider
     */
    public void setItemImageUrlProvider(StringProvider<T> imageUrlProvider) {
		this.itemImageUrlProvider = imageUrlProvider;
	}

    /**
     *
     * @return
     */
    public StringProvider<T> getItemImageUrlProvider() {
		return itemImageUrlProvider;
	}

    /**
     *
     * @param itemTooltipProvider
     * @return
     */
    public MultiItemFormField<T> setItemTooltipProvider(StringProvider<T> itemTooltipProvider) {
		this.itemTooltipProvider = itemTooltipProvider;
		return this;
	}

    /**
     *
     * @return
     */
    public StringProvider<T> getItemTooltipProvider() {
		return itemTooltipProvider;
	}

    /**
     *
     * @param items
     * @return
     */
    public MultiItemFormField<T> setSelectableItems(Collection<T> items) {
		this.selectableItems = items == null ? new HashSet<T>() : items;
		return this;
	}

    /**
     *
     * @return
     */
    public Collection<T> getSelectableItems() {
		Set<T> result = new HashSet<>(selectableItems);
		result.removeAll(value);
		return result;
	}

    /**
     *
     * @param multiItmes
     * @return
     */
    public MultiItemFormField<T> setSelectableMultiItems(Collection<MultiItem<T>> multiItmes) {
		this.selectableMultiItems = multiItmes;
		return this;
	}

    /**
     *
     * @return
     */
    public Collection<MultiItem<T>> getSelectableMultiItems() {
		return selectableMultiItems;
	}

    /**
     *
     * @return
     */
    public AddButton getAddButton() {
		return addButton;
	}

    /**
     *
     * @return
     */
    public RemoveAllButton getRemoveAllButton() {
		return removeAllButton;
	}

    /**
     *
     * @param item
     * @return
     */
    public RemoveButton<T> getRemoveButton(T item) {
		return removeButtons.get(item);
	}

    /**
     *
     * @param id
     * @return
     */
    public RemoveButton<T> getRemoveButton(String id) {
		for (RemoveButton<T> button : removeButtons.values()) {
			if (button.getId().equals(id)) {
                                return button;
                        }
		}
		throw new RuntimeException("button does not exist: " + id);
	}

    /**
     *
     * @param value
     * @return
     */
    public MultiItemFormField<T> setValue(Collection<T> value) {
		removeButtons.clear();
		if (value == null) {
			this.value = null;
			return this;
		}
		this.value = new HashSet<>(value);
		for (T item : value) {
			RemoveButton button = (RemoveButton) new RemoveButton(item).setLabel("Entfernen").setIcon("delete");
			removeButtons.put(item, button);
		}
		return this;
	}

    /**
     *
     * @param item
     */
    public void removeValueItem(T item) {
		value.remove(item);
		setValue(value);
	}

    /**
     *
     */
    public void removeAllItems() {
		value.clear();
		setValue(value);
	}

    /**
     *
     * @param item
     */
    public void addValueItem(T item) {
		value.add(item);
		setValue(value);
	}

    /**
     *
     * @param items
     */
    public void addValueItems(Collection<T> items) {
		value.addAll(items);
		setValue(value);
	}

    /**
     *
     * @return
     */
    public Set<T> getValue() {
		return value;
	}

    /**
     *
     * @return
     */
    @Override
	public String getValueAsString() {
		return value == null ? "0" : valueOf(value.size());
	}

    /**
     *
     * @param data
     * @param uploadedFiles
     */
    @Override
	public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
		// nop
	}

    /**
     *
     * @throws ValidationException
     */
    @Override
	public void validate() throws ValidationException {
		if (isRequired() && (value == null || value.isEmpty())) {
                        throw new ValidationException("Hier ist eine Auswahl erforderlich.");
                }
	}

    /**
     *
     */
    public class AddButton extends FormButton {

        /**
         *
         */
        public AddButton() {
			super(BUTTON_PREFIX + ADD_ITEM_BUTTON_NAME_PREFIX + MultiItemFormField.this.getName());
		}

        /**
         *
         * @return
         */
        public MultiItemFormField getField() {
			return MultiItemFormField.this;
		}

	}

    /**
     *
     */
    public class RemoveAllButton extends FormButton {

        /**
         *
         */
        public RemoveAllButton() {
			super(BUTTON_PREFIX + REMOVE_ALLITEMS_BUTTON_NAME_PREFIX + MultiItemFormField.this.getName());
		}

        /**
         *
         * @return
         */
        public MultiItemFormField getField() {
			return MultiItemFormField.this;
		}

	}

    /**
     *
     * @param <T>
     */
    public class RemoveButton<T> extends FormButton {

		private String id;

		private T item;

        /**
         *
         * @param item
         */
        public RemoveButton(T item) {
			this(item, buttonIdGenerator.generateId());
		}

		private RemoveButton(T item, String id) {
			super(BUTTON_PREFIX + REMOVE_ITEM_BUTTON_NAME_PREFIX + MultiItemFormField.this.getName() + "_"
					+ id);
			this.item = item;
			this.id = id;

			setValidateForm(false);
		}

        /**
         *
         * @return
         */
        public MultiItemFormField<T> getField() {
			return (MultiItemFormField<T>) MultiItemFormField.this;
		}

        /**
         *
         * @return
         */
        public T getItem() {
			return item;
		}

        /**
         *
         * @return
         */
        public String getId() {
			return id;
		}

	}

    /**
     *
     * @param <T>
     */
    public class EditButton<T> extends FormButton {

		private String id;

		private T item;

        /**
         *
         * @param item
         */
        public EditButton(T item) {
			this(item, buttonIdGenerator.generateId());
		}

		private EditButton(T item, String id) {
			super(BUTTON_PREFIX + REMOVE_ITEM_BUTTON_NAME_PREFIX + MultiItemFormField.this.getName() + "_"
					+ id);
			this.item = item;
			this.id = id;

			setValidateForm(false);
		}

        /**
         *
         * @return
         */
        public MultiItemFormField<T> getField() {
			return (MultiItemFormField<T>) MultiItemFormField.this;
		}

        /**
         *
         * @return
         */
        public T getItem() {
			return item;
		}

        /**
         *
         * @return
         */
        public String getId() {
			return id;
		}

	}

	// --- dependencies ---

	private static IdGenerator buttonIdGenerator = new CountingIdGenerator(MultiItemFormField.class.getSimpleName());

	// --- ---

}
