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
package ilarkesto.form;


import static ilarkesto.form.Form.BUTTON_PREFIX;
import static ilarkesto.form.Form.CLEAR_ITEM_BUTTON_NAME_PREFIX;
import static ilarkesto.form.Form.SELECT_ITEM_BUTTON_NAME_PREFIX;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.fileupload.FileItem;

/**
 *
 *
 * @param <T>
 */
public class ItemFormField<T> extends AFormField {

    private T value;

    private ClearButton clearButton;

    private SelectButton selectButton;

    private Collection<T> selectableItems;

    private boolean autoTrigger;

    /**
     *
     * @param name
     */
    public ItemFormField(String name) {
        super(name);

        clearButton = (ClearButton) new ClearButton(BUTTON_PREFIX + CLEAR_ITEM_BUTTON_NAME_PREFIX + name)
                .setValidateForm(false).setLabel("Entfernen").setIcon("delete");

        selectButton = (SelectButton) new SelectButton(BUTTON_PREFIX + SELECT_ITEM_BUTTON_NAME_PREFIX + name)
                .setValidateForm(false).setLabel("Auswahl...").setIcon("edit");
    }

    /**
     *
     * @param autoTrigger
     * @return
     */
    public ItemFormField setAutoTrigger(boolean autoTrigger) {
        this.autoTrigger = autoTrigger;
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isAutoTrigger() {
        return autoTrigger && value == null;
    }

    /**
     *
     * @param items
     * @return
     */
    public ItemFormField setSelectableItems(Collection<T> items) {
        this.selectableItems = items;
        return this;
    }

    /**
     *
     * @return
     */
    public Collection getSelectableItems() {
        Set<T> result = new HashSet<>(selectableItems);
        if (value != null) {
                result.remove(value);
        }
        return result;
    }

    /**
     *
     * @return
     */
    public ClearButton getClearButton() {
        if (isRequired() || value == null) {
                return null;
        }
        return clearButton;
    }

    /**
     *
     * @return
     */
    public SelectButton getSelectButton() {
        return selectButton;
    }

    /**
     *
     * @param value
     * @return
     */
    public ItemFormField setValue(T value) {
        this.value = value;
        fireFieldValueChanged();
        return this;
    }

    /**
     *
     * @return
     */
    public T getValue() {
        return value;
    }

    /**
     *
     * @return
     */
    @Override
    public String getValueAsString() {
        return value == null ? null : value.toString();
    }

    /**
     *
     * @param data
     * @param uploadedFiles
     */
    @Override
    public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {}

    /**
     *
     * @throws ValidationException
     */
    @Override
    public void validate() throws ValidationException {
        if (isRequired() && value == null) {
                throw new ValidationException("Hier ist eine Auswahl erforderlich.");
        }
    }

    /**
     *
     */
    public class SelectButton extends FormButton {

        /**
         *
         * @param name
         */
        public SelectButton(String name) {
            super(name);
        }

        /**
         *
         * @return
         */
        public ItemFormField getField() {
            return ItemFormField.this;
        }

    }

    /**
     *
     */
    public class ClearButton extends FormButton {

        /**
         *
         * @param name
         */
        public ClearButton(String name) {
            super(name);
        }

        /**
         *
         * @return
         */
        public ItemFormField getField() {
            return ItemFormField.this;
        }

    }

}
