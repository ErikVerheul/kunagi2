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

import java.util.Collection;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 *
 *
 */
public class HiddenFormField implements FormField {

    private String name;
    private String value;
    private Form form;
    private boolean required;

    /**
     *
     * @param listener
     * @return
     */
    @Override
    public FormField addFormFieldChangeListener(FormFieldChangeListener listener) {
        throw new RuntimeException("Not implemented yet!");
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isRequired() {
        return required;
    }

    /**
     *
     * @param required
     * @return
     */
    @Override
    public HiddenFormField setRequired(boolean required) {
        this.required = required;
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public Form getForm() {
        return form;
    }

    /**
     *
     * @param form
     */
    public void setForm(Form form) {
        this.form = form;
    }

    /**
     *
     * @param name
     */
    public HiddenFormField(String name) {
        this.name = name;
    }

    /**
     *
     * @param value
     * @return
     */
    @Override
    public FormField setLabel(String value) {
        // nop;
        return this;
    }

    /**
     *
     * @param value
     * @return
     */
    @Override
    public FormField setHintText(String value) {
        // nop;
        return this;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    @Override
    public String getLabel() {
        return name;
    }

    /**
     *
     * @return
     */
    @Override
    public String getHintText() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public String getErrorMessage() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public String getValueAsString() {
        return value;
    }

    /**
     *
     * @param value
     */
    @Override
    public void setErrorMessage(String value) {
    // nop
    }

    /**
     *
     * @param data
     * @param uploadedFiles
     */
    @Override
    public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
        value = data.get(name);
    }

    /**
     *
     */
    @Override
    public void validate() {}

}
