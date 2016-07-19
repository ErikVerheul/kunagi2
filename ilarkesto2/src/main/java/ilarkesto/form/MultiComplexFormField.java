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

import ilarkesto.base.Factory;
import static ilarkesto.base.Reflect.newInstance;
import static ilarkesto.form.Form.ADD_COMPLEX_BUTTON_NAME_PREFIX;
import static ilarkesto.form.Form.BUTTON_PREFIX;
import static ilarkesto.form.Form.EDIT_COMPLEX_BUTTON_NAME_PREFIX;
import static ilarkesto.form.Form.REMOVE_COMPLEX_BUTTON_NAME_PREFIX;
import ilarkesto.id.CountingIdGenerator;
import ilarkesto.id.IdGenerator;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Collections.sort;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author erik
 */
public class MultiComplexFormField extends AFormField {

	private Set<Object> value;
	private AddButton addButton;
	private Map<Object, RemoveButton> removeButtons = new HashMap<>();
	private Map<Object, EditButton> editButtons = new HashMap<>();
	private Factory itemFactory;
	private Class<? extends BeanForm> elementFormClass;
	private Comparator comparator;

    /**
     *
     * @param name
     * @param elementFormClass
     */
    public MultiComplexFormField(String name, Class<? extends BeanForm> elementFormClass) {
		super(name);
		this.elementFormClass = elementFormClass;

		addButton = (AddButton) new AddButton().setValidateForm(false).setLabel("Hinzuf\u00FCgen...").setIcon("add");
	}

    /**
     *
     * @return
     */
    public BeanForm createSubform() {
		// String formBeanName = getForm().getName() + Str.uppercaseFirstLetter(getName()) + "Form";
		// BeanForm form = (BeanForm) beanProvider.getBean(formBeanName);
		// if (form == null) throw new RuntimeException("Form bean does not exist: " + formBeanName);
		BeanForm form = newInstance(elementFormClass);
		form.setStringKeyPrefix(getForm().getStringKeyPrefix());
		return form;
	}

    /**
     *
     * @return
     */
    public Factory getItemFactory() {
		return itemFactory;
	}

    /**
     *
     * @param factory
     * @return
     */
    public MultiComplexFormField setItemFactory(Factory factory) {
		this.itemFactory = factory;
		return this;
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
     * @param item
     * @return
     */
    public RemoveButton getRemoveButton(Object item) {
		return removeButtons.get(item);
	}

    /**
     *
     * @param id
     * @return
     */
    public RemoveButton getRemoveButton(String id) {
		for (RemoveButton button : removeButtons.values()) {
			if (button.getId().equals(id)) {
                                return button;
                        }
		}
		throw new RuntimeException("remove button does not exist: " + id);
	}

    /**
     *
     * @param item
     * @return
     */
    public EditButton getEditButton(Object item) {
		return editButtons.get(item);
	}

    /**
     *
     * @param id
     * @return
     */
    public EditButton getEditButton(String id) {
		for (EditButton button : editButtons.values()) {
			if (button.getId().equals(id)) {
                                return button;
                        }
		}
		throw new RuntimeException("edit button does not exist: " + id);
	}

    /**
     *
     * @param value
     * @return
     */
    public MultiComplexFormField setValue(Set<Object> value) {
		this.value = value;
		removeButtons.clear();
		for (Object item : value) {
			RemoveButton button = (RemoveButton) new RemoveButton(item).setLabel("Entfernen").setIcon("delete");
			removeButtons.put(item, button);
		}
		editButtons.clear();
		for (Object item : value) {
			EditButton button = (EditButton) new EditButton(item).setLabel("Bearbeiten").setIcon("edit");
			editButtons.put(item, button);
		}
		return this;
	}

    /**
     *
     * @param item
     */
    public void removeValueItem(Object item) {
		value.remove(item);
		setValue(value);
	}

    /**
     *
     * @param item
     */
    public void addValueItem(Object item) {
		value.add(item);
		setValue(value);
	}

    /**
     *
     * @return
     */
    public Collection<Object> getValue() {
		if (comparator != null) {
			List list = new ArrayList(value);
			sort(list, comparator);
			return list;
		}
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
                        throw new ValidationException("Auswahl erforderlich!");
                }
	}

    /**
     *
     * @param comparator
     * @return
     */
    public MultiComplexFormField setComparator(Comparator comparator) {
		this.comparator = comparator;
		return this;
	}

    /**
     *
     */
    public class AddButton extends FormButton {

        /**
         *
         */
        public AddButton() {
			super(BUTTON_PREFIX + ADD_COMPLEX_BUTTON_NAME_PREFIX + MultiComplexFormField.this.getName());
		}

        /**
         *
         * @return
         */
        public MultiComplexFormField getField() {
			return MultiComplexFormField.this;
		}

	}

    /**
     *
     */
    public class RemoveButton extends FormButton {

		private String id;

		private Object item;

        /**
         *
         * @param item
         */
        public RemoveButton(Object item) {
			this(item, buttonIdGenerator.generateId());
		}

		private RemoveButton(Object item, String id) {
			super(BUTTON_PREFIX + REMOVE_COMPLEX_BUTTON_NAME_PREFIX + MultiComplexFormField.this.getName()
					+ "_" + id);
			this.item = item;
			this.id = id;

			setValidateForm(false);
		}

        /**
         *
         * @return
         */
        public MultiComplexFormField getField() {
			return MultiComplexFormField.this;
		}

        /**
         *
         * @return
         */
        public Object getItem() {
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
     */
    public class EditButton extends FormButton {

		private String id;

		private Object item;

        /**
         *
         * @param item
         */
        public EditButton(Object item) {
			this(item, buttonIdGenerator.generateId());
		}

		private EditButton(Object item, String id) {
			super(BUTTON_PREFIX + EDIT_COMPLEX_BUTTON_NAME_PREFIX + MultiComplexFormField.this.getName()
					+ "_" + id);
			this.item = item;
			this.id = id;

			setValidateForm(false);
		}

        /**
         *
         * @return
         */
        public MultiComplexFormField getField() {
			return MultiComplexFormField.this;
		}

        /**
         *
         * @return
         */
        public Object getItem() {
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

	private static IdGenerator buttonIdGenerator = new CountingIdGenerator(MultiComplexFormField.class.getSimpleName());

}
