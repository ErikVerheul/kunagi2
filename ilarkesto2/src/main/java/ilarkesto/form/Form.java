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

import ilarkesto.base.MissingDependencyException;
import static ilarkesto.base.StrExtend.lowercaseFirstLetter;
import ilarkesto.di.BeanProvider;
import ilarkesto.locale.Localizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 *
 *
 */
public class Form {

    /**
     *
     */
    public static final String BUTTON_PREFIX = "_button_";

    /**
     *
     */
    public static final String CLEAR_ITEM_BUTTON_NAME_PREFIX = "_clearItem_";

    /**
     *
     */
    public static final String SELECT_ITEM_BUTTON_NAME_PREFIX = "_selectItem_";

    /**
     *
     */
    public static final String INPUTASSISTANT_BUTTON_NAME_PREFIX = "_inputAssistant_";

    /**
     *
     */
    public static final String SELECT_TEXTFIELD_ITEM_BUTTON_NAME_PREFIX = "_selectTextfieldItem_";

    /**
     *
     */
    public static final String REMOVE_ITEM_BUTTON_NAME_PREFIX = "_removeItem_";

    /**
     *
     */
    public static final String ADD_ITEM_BUTTON_NAME_PREFIX = "_addItem_";

    /**
     *
     */
    public static final String REMOVE_ALLITEMS_BUTTON_NAME_PREFIX = "_removeAllItems_";

    /**
     *
     */
    public static final String REMOVE_COMPLEX_BUTTON_NAME_PREFIX = "_removeComplex_";

    /**
     *
     */
    public static final String EDIT_COMPLEX_BUTTON_NAME_PREFIX = "_editComplex_";

    /**
     *
     */
    public static final String ADD_COMPLEX_BUTTON_NAME_PREFIX = "_addComplex_";

    /**
     *
     */
    public static final String ABORT_BUTTON_NAME = "_abort";

	private String formName;
	private String title;
	private List<FormField> visibleFields = new ArrayList<>();
	private List<HiddenFormField> hiddenFields = new ArrayList<>();
	private List<FormButton> submitButtons = new ArrayList<>();
	private String errorMessage;
	private boolean multipart = false;
	private FormButton defaultButton;
	private boolean autoLocalize = true;
	private String stringKeyPrefix;
	private boolean initialized;
	private List<FormPlugin> footerPlugins = new ArrayList<>(1);

    /**
     *
     */
    protected void initializeForm() {}

    /**
     *
     */
    public final void initialize() {
		if (initialized) {
                        return;
                }
		initialized = true;
		initializeForm();
	}

    /**
     *
     * @param plugin
     * @return
     */
    public Form addFooterPlugin(FormPlugin plugin) {
		footerPlugins.add(plugin);
		return this;
	}

    /**
     *
     * @return
     */
    public List<FormPlugin> getFooterPlugins() {
		return footerPlugins;
	}

    /**
     *
     * @return
     */
    public boolean isFooterPluginsAvailable() {
		return footerPlugins != null && !footerPlugins.isEmpty();
	}

    /**
     *
     * @return
     */
    public List<FormField> getAutoTriggerFields() {
		List<FormField> result = new ArrayList<>();
		for (FormField field : visibleFields) {
			if (field instanceof ItemFormField) {
				if (((ItemFormField) field).isAutoTrigger()) {
                                        result.add(field);
                                }
			}
		}
		return result;
	}

    /**
     *
     * @return
     */
    public FormButton getMainSubmitButton() {
		if (submitButtons.isEmpty()) {
                        return null;
                }
		return submitButtons.get(0);
	}

    /**
     *
     * @param autoLocalize
     */
    public final void setAutoLocalize(boolean autoLocalize) {
		this.autoLocalize = autoLocalize;
	}

    /**
     *
     * @param stringKeyPrefix
     */
    public final void setStringKeyPrefix(String stringKeyPrefix) {
		this.stringKeyPrefix = stringKeyPrefix;
	}

    /**
     *
     * @return
     */
    public String getStringKeyPrefix() {
		if (stringKeyPrefix == null) {
			stringKeyPrefix = getName().replace('_', '.');
		}
		return stringKeyPrefix;
	}

    /**
     *
     * @return
     */
    public FormButton getDefaultButton() {
		return defaultButton;
	}

    /**
     *
     * @return
     */
    public boolean isMultipart() {
		return multipart;
	}

    /**
     *
     * @param name
     */
    public void setFormName(String name) {
		this.formName = name;
	}

    /**
     *
     * @return
     */
    public String getName() {
		if (formName == null) {
                        formName = lowercaseFirstLetter(getClass().getSimpleName());
                }
		return formName;
	}

    /**
     *
     * @return
     */
    public String getErrorMessage() {
		return errorMessage;
	}

    /**
     *
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

    /**
     *
     * @param name
     * @return
     */
    public OutputFormField addOutput(String name) {
		return addVisibleField(name, new OutputFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public RadioSelectFormField addRadioSelect(String name) {
		return addVisibleField(name, new RadioSelectFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public UploadFormField addUpload(String name) {
		multipart = true;
		return addVisibleField(name, new UploadFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public FileFormField addFile(String name) {
		return addVisibleField(name, new FileFormField(name));
	}

    /**
     *
     * @param name
     * @param elementFormClass
     * @return
     */
    public MultiComplexFormField addMultiComplex(String name, Class<? extends BeanForm> elementFormClass) {
		return addVisibleField(name, new MultiComplexFormField(name, elementFormClass));
	}

    /**
     *
     * @param name
     * @return
     */
    public MultiItemFormField addMultiItem(String name) {
		return addVisibleField(name, new MultiItemFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public ItemFormField addItem(String name) {
		return addVisibleField(name, new ItemFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public DropdownFormField addDropdown(String name) {
		return addVisibleField(name, new DropdownFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public CheckboxFormField addCheckbox(String name) {
		return addVisibleField(name, new CheckboxFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public MultiCheckboxFormField addMultiCheckbox(String name) {
		return addVisibleField(name, new MultiCheckboxFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public TextFormField addText(String name) {
		return addVisibleField(name, new TextFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public FloatFormField addFloat(String name) {
		return addVisibleField(name, new FloatFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public IntegerFormField addInteger(String name) {
		return addVisibleField(name, new IntegerFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public PasswordFormField addPassword(String name) {
		return addVisibleField(name, new PasswordFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public TextareaFormField addTextarea(String name) {
		return addVisibleField(name, new TextareaFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public MoneyFormField addMoney(String name) {
		return addVisibleField(name, new MoneyFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public EmailAddressFormField addEmailAddress(String name) {
		return addVisibleField(name, new EmailAddressFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public DateFormField addDate(String name) {
		return addVisibleField(name, new DateFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public TimeFormField addTime(String name) {
		return addVisibleField(name, new TimeFormField(name));
	}

    /**
     *
     * @param name
     * @return
     */
    public TimePeriodFormField addTimePeriod(String name) {
		return addVisibleField(name, new TimePeriodFormField(name));
	}

	private <F extends AFormField> F addVisibleField(String name, F field) {
		field.setForm(this);
		visibleFields.add(field);
		if (autoLocalize) {
			field.setLabel(getFieldLabel(name));
			field.setHintText(getFieldTooltip(name));
		}
		if (beanProvider != null) {
                        beanProvider.autowire(field);
                }
		return field;
	}

    /**
     *
     * @param name
     * @return
     */
    public HiddenFormField addHidden(String name) {
		HiddenFormField field = new HiddenFormField(name);
		field.setForm(this);
		hiddenFields.add(field);
		if (beanProvider != null) {
                        beanProvider.autowire(field);
                }
		return field;
	}

    /**
     *
     * @param name
     * @return
     */
    public FormButton addSubmitButton(String name) {
		FormButton button = new FormButton(name);
		button.setIcon("submit");
		submitButtons.add(button);
		defaultButton = button;
		if (autoLocalize) {
			button.setLabel(getFieldLabel("button." + name));
			// button.setHintText(getFieldHintText("button." + name));
		}
		if (beanProvider != null) {
                        beanProvider.autowire(button);
                }
		return button;
	}

    /**
     *
     * @return
     */
    public FormButton addAbortSubmitButton() {
		return addSubmitButton(ABORT_BUTTON_NAME).setUpdateFields(false).setValidateForm(false).setIcon("abort");
	}

    /**
     *
     * @return
     */
    public String getFormTitle() {
		if (title == null && autoLocalize) {
			title = localizer.string(getStringKeyPrefix() + ".formTitle");
		}
		return title;
	}

    /**
     *
     * @param value
     */
    public void setFormTitle(String value) {
		this.title = value;
	}

    /**
     *
     * @return
     */
    public List<FormButton> getSubmitButtons() {
		return submitButtons;
	}

    /**
     *
     * @param name
     * @return
     */
    public FormButton getButton(String name) {

		// textField
		if (name.startsWith(INPUTASSISTANT_BUTTON_NAME_PREFIX)) {
			String fieldName = name.substring(INPUTASSISTANT_BUTTON_NAME_PREFIX.length());
			return ((TextFormField) getField(fieldName)).getInputAssistantButton();
		}

		// itemField
		if (name.startsWith(CLEAR_ITEM_BUTTON_NAME_PREFIX)) {
			String fieldName = name.substring(CLEAR_ITEM_BUTTON_NAME_PREFIX.length());
			return ((ItemFormField) getField(fieldName)).getClearButton();
		}
		if (name.startsWith(SELECT_ITEM_BUTTON_NAME_PREFIX)) {
			String fieldName = name.substring(SELECT_ITEM_BUTTON_NAME_PREFIX.length());
			return ((ItemFormField) getField(fieldName)).getSelectButton();
		}

		// multiItemField
		if (name.startsWith(ADD_ITEM_BUTTON_NAME_PREFIX)) {
			String fieldName = name.substring(ADD_ITEM_BUTTON_NAME_PREFIX.length());
			return ((MultiItemFormField) getField(fieldName)).getAddButton();
		}
		if (name.startsWith(REMOVE_ITEM_BUTTON_NAME_PREFIX)) {
			int idx = name.lastIndexOf('_');
			if (idx < REMOVE_ITEM_BUTTON_NAME_PREFIX.length()) {
                                throw new RuntimeException("Unexpected button name: " + name);
                        }
			String fieldName = name.substring(REMOVE_ITEM_BUTTON_NAME_PREFIX.length(), idx);
			String id = name.substring(idx + 1);
			return ((MultiItemFormField) getField(fieldName)).getRemoveButton(id);
		}
		if (name.startsWith(REMOVE_ALLITEMS_BUTTON_NAME_PREFIX)) {
			String fieldName = name.substring(REMOVE_ALLITEMS_BUTTON_NAME_PREFIX.length());
			return ((MultiItemFormField) getField(fieldName)).getRemoveAllButton();
		}

		// multiComplexField
		if (name.startsWith(ADD_COMPLEX_BUTTON_NAME_PREFIX)) {
			String fieldName = name.substring(ADD_COMPLEX_BUTTON_NAME_PREFIX.length());
			// DEBUG.out("form:", getName(), ", ", "fieldName: " + fieldName);
			return ((MultiComplexFormField) getField(fieldName)).getAddButton();
		}
		if (name.startsWith(EDIT_COMPLEX_BUTTON_NAME_PREFIX)) {
			int idx = name.lastIndexOf('_');
			if (idx < EDIT_COMPLEX_BUTTON_NAME_PREFIX.length()) {
                                throw new RuntimeException("Unexpected button name: " + name);
                        }
			String fieldName = name.substring(EDIT_COMPLEX_BUTTON_NAME_PREFIX.length(), idx);
			String id = name.substring(idx + 1);
			return ((MultiComplexFormField) getField(fieldName)).getEditButton(id);
		}
		if (name.startsWith(REMOVE_COMPLEX_BUTTON_NAME_PREFIX)) {
			int idx = name.lastIndexOf('_');
			if (idx < REMOVE_COMPLEX_BUTTON_NAME_PREFIX.length()) {
                                throw new RuntimeException("Unexpected button name: " + name);
                        }
			String fieldName = name.substring(REMOVE_COMPLEX_BUTTON_NAME_PREFIX.length(), idx);
			String id = name.substring(idx + 1);
			// DEBUG.out("fieldName: " + fieldName);
			// DEBUG.out("id: " + id);
			return ((MultiComplexFormField) getField(fieldName)).getRemoveButton(id);
		}

                // submitButtons
                for (FormButton button : submitButtons) {
                        if (button.getName().equals(name)) {
                                return button;
                        }
                }

		throw new RuntimeException("button does not exist: " + name);
	}

    /**
     *
     * @return
     */
    public List<FormField> getVisibleFields() {
		return visibleFields;
	}

    /**
     *
     * @return
     */
    public List<HiddenFormField> getHiddenFields() {
		return hiddenFields;
	}

    /**
     *
     * @param data
     * @param uploadedFiles
     */
    public void update(Map<String, String> data, java.util.Collection<FileItem> uploadedFiles) {
		if (uploadedFiles == null) {
                        return;
                }
		for (FormField field : visibleFields) {
                        field.update(data, uploadedFiles);
                }
		for (FormField field : hiddenFields) {
                        field.update(data, uploadedFiles);
                }
	}

    /**
     *
     */
    protected final static String ERROR_MSG = "Offensichtlich hast Du Bl\u00F6dsinn eingetippt. Du darfst Dich korrigieren.";

    /**
     *
     * @throws ValidationException
     */
    public void validate() throws ValidationException {
		for (FormField field : visibleFields) {
			try {
				field.validate();
				field.setErrorMessage(null);
			} catch (ValidationException ex) {
				field.setErrorMessage(ex.getMessage());
				throw new ValidationException(ERROR_MSG);
			}
		}
		errorMessage = null;
	}

    /**
     *
     * @param name
     * @return
     */
    public FormField getField(String name) {
		for (FormField field : visibleFields) {
			if (field.getName().equals(name)) {
                                return field;
                        }
		}
		for (FormField field : hiddenFields) {
			if (field.getName().equals(name)) {
                                return field;
                        }
		}
		return null;
	}

    /**
     *
     * @param fieldName
     * @return
     */
    public String getFieldValue(String fieldName) {
		return getField(fieldName).getValueAsString();
	}

    /**
     *
     * @return
     */
    public boolean isKeepAlive() {
		return keepAlive;
	}

	// --- helper ---

    /**
     *
     * @param name
     * @return
     */
    
	protected String getFieldLabel(String name) {
		if (localizer == null) {
                        throw new MissingDependencyException("localizer");
                }
		return localizer.string(getStringKeyPrefix() + "." + name);
	}

    /**
     *
     * @param name
     * @return
     */
    protected String getFieldTooltip(String name) {
		if (localizer == null) {
                        throw new MissingDependencyException("localizer");
                }
		return localizer.string(getStringKeyPrefix() + "." + name + ".hint");
	}

	Localizer getLocalizer() {
		return localizer;
	}

	// --- Object ---

	@Override
	public String toString() {
		return formName;
	}

	// --- dependencies ---

    /**
     *
     */
    
	protected Localizer localizer;

    /**
     *
     * @param stringProvider
     */
    public final void setLocalizer(Localizer stringProvider) {
		this.localizer = stringProvider;
	}

	private BeanProvider beanProvider;

    /**
     *
     * @param beanProvider
     */
    public void setBeanProvider(BeanProvider beanProvider) {
		this.beanProvider = beanProvider;
	}

	private boolean keepAlive = true;

    /**
     *
     * @param keepAlive
     */
    public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

}
