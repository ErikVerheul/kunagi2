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
 * @author erik
 */
public interface FormField {

    // --- creating ---

    /**
     *
     * @param value
     * @return
     */
    
    FormField setLabel(String value);

    /**
     *
     * @param value
     * @return
     */
    FormField setHintText(String value);

    /**
     *
     * @param required
     * @return
     */
    FormField setRequired(boolean required);

    /**
     *
     * @param listener
     * @return
     */
    FormField addFormFieldChangeListener(FormFieldChangeListener listener);

    // --- rendering ---

    /**
     *
     * @return
     */
    
    String getName();

    /**
     *
     * @return
     */
    String getLabel();

    /**
     *
     * @return
     */
    String getHintText();

    /**
     *
     * @return
     */
    boolean isRequired();

    /**
     *
     * @return
     */
    String getErrorMessage();

    // --- rendering + submitting ---

    /**
     *
     * @return
     */
    
    String getValueAsString();

    // --- submitting ---

    /**
     *
     * @return
     */
    
    Form getForm();

    /**
     *
     * @param value
     */
    void setErrorMessage(String value);

    /**
     *
     * @param data
     * @param uploadedFiles
     */
    void update(Map<String, String> data, Collection<FileItem> uploadedFiles);

    /**
     *
     * @throws ValidationException
     */
    void validate() throws ValidationException;

}
