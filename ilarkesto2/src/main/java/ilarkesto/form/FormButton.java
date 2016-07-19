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

import static ilarkesto.form.Form.ABORT_BUTTON_NAME;

/**
 *
 * @author erik
 */
public class FormButton  {

    private String name;
    private String label;
    private boolean updateFields = true;
    private boolean validateForm = true;
    private Character accessKey;
    private String icon;

    /**
     *
     * @param name
     */
    public FormButton(String name) {
        this.name = name;
        this.label = name;
    }

    /**
     *
     * @param icon
     * @return
     */
    public FormButton setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    /**
     *
     * @return
     */
    public String getIcon() {
        return icon;
    }

    /**
     *
     * @param accessKey
     * @return
     */
    public FormButton setAccessKey(Character accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    /**
     *
     * @return
     */
    public Character getAccessKey() {
        return accessKey;
    }

    /**
     *
     * @param value
     * @return
     */
    public FormButton setLabel(String value) {
        this.label = value;
        return this;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @return
     */
    public boolean isUpdateFields() {
        return updateFields;
    }

    /**
     *
     * @param updateFields
     * @return
     */
    public FormButton setUpdateFields(boolean updateFields) {
        this.updateFields = updateFields;
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isValidateForm() {
        return validateForm;
    }

    /**
     *
     * @param validateForm
     * @return
     */
    public FormButton setValidateForm(boolean validateForm) {
        this.validateForm = validateForm;
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isAbort() {
        return  ABORT_BUTTON_NAME.equals(name);
    }

    @Override
    public String toString() {
        return name;
    }

}
