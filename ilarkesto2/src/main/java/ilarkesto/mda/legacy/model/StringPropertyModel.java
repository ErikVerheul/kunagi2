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
package ilarkesto.mda.legacy.model;

/**
 *
 * @author erik
 */
public class StringPropertyModel extends SimplePropertyModel {

	private boolean richtext;
	private Integer maxLenght;
	private boolean templateAvailable;
	private boolean masked;

    /**
     *
     * @param entityModel
     * @param name
     */
    public StringPropertyModel(BeanModel entityModel, String name) {
		super(entityModel, name, false, false, String.class.getName());
	}

    /**
     *
     * @param masked
     * @return
     */
    public StringPropertyModel setMasked(boolean masked) {
		this.masked = masked;
		return this;
	}

    /**
     *
     * @return
     */
    public boolean isMasked() {
		return masked;
	}

    /**
     *
     * @param multiline
     * @return
     */
    public StringPropertyModel setRichtext(boolean multiline) {
		this.richtext = multiline;
		return this;
	}

    /**
     *
     * @return
     */
    public boolean isRichtext() {
		return richtext;
	}

    /**
     *
     * @param maxLenght
     * @return
     */
    public StringPropertyModel setMaxLenght(Integer maxLenght) {
		this.maxLenght = maxLenght;
		return this;
	}

    /**
     *
     * @return
     */
    public Integer getMaxLenght() {
		return maxLenght;
	}

    /**
     *
     * @return
     */
    public boolean isMaxLengthSet() {
		return maxLenght != null;
	}

    /**
     *
     * @param template
     * @return
     */
    public StringPropertyModel setTemplateAvailable(boolean template) {
		this.templateAvailable = template;
		return this;
	}

    /**
     *
     * @return
     */
    public boolean isTemplateAvailable() {
		return templateAvailable;
	}

}
