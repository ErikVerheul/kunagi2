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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erik
 */
public abstract class AFormField implements FormField {

    private String name;

    private String label;

    private String hintText;

    private boolean required = false;

    private String errorMessage;

    private Form form;

    private List<FormFieldChangeListener> listeners;

    /**
     *
     * @param name
     */
    public AFormField(String name) {
        this.name = name;
        this.label = name;
    }

    /**
     *
     * @param listener
     * @return
     */
    @Override
    public final FormField addFormFieldChangeListener(FormFieldChangeListener listener) {
        if (listeners == null) {
                listeners = new ArrayList(1);
        }
        listeners.add(listener);
        return this;
    }

    /**
     *
     */
    protected final void fireFieldValueChanged() {
        if (listeners == null) {
                return;
        }
        for (FormFieldChangeListener listener : listeners) {
                listener.fieldValueChanged(this);
        }
    }

    /**
     *
     * @param value
     * @return
     */
    @Override
    public final FormField setLabel(String value) {
        this.label = value;
        return this;
    }

    /**
     *
     * @param value
     * @return
     */
    @Override
    public final FormField setHintText(String value) {
        this.hintText = value;
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public final String getErrorMessage() {
        return errorMessage;
    }

    /**
     *
     * @param errorMessage
     */
    @Override
    public final void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     *
     * @return
     */
    @Override
    public final String getLabel() {
        return label;
    }

    /**
     *
     * @return
     */
    @Override
    public final String getHintText() {
        return hintText;
    }

    /**
     *
     * @return
     */
    @Override
    public final boolean isRequired() {
        return required;
    }

    /**
     *
     * @param required
     * @return
     */
    @Override
    public final AFormField setRequired(boolean required) {
        this.required = required;
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public final String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    @Override
    public final Form getForm() {
        return form;
    }

    /**
     *
     * @param form
     */
    public final void setForm(Form form) {
        this.form = form;
    }

}
