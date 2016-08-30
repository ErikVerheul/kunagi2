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

import static ilarkesto.base.StrExtend.lowercaseFirstLetter;

/**
 *
 *
 */
public abstract class PropertyModel {

    /**
     *
     * @return
     */
    public abstract String getNameSingular();

    /**
     *
     * @return
     */
    public abstract String getType();

    /**
     *
     * @return
     */
    public abstract String getContentType();

    /**
     *
     * @return
     */
    public abstract String getCollectionType();

    /**
     *
     * @return
     */
    public abstract String getCollectionImpl();

    /**
     *
     * @return
     */
    public abstract boolean isCollection();

    /**
     *
     * @return
     */
    public abstract boolean isPrimitive();

    /**
     *
     * @return
     */
    public abstract boolean isBoolean();

    /**
     *
     * @return
     */
    public abstract boolean isString();

	private boolean mandatory;

	private String editablePredicate;

	private String tooltip;

	private boolean fireModified = true;

	private boolean virtual;

    /**
     *
     * @param tooltip
     * @return
     */
    public PropertyModel setTooltip(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

    /**
     *
     * @return
     */
    public boolean isVirtual() {
		return virtual;
	}

    /**
     *
     * @param virtual
     * @return
     */
    public PropertyModel setVirtual(boolean virtual) {
		this.virtual = virtual;
		return this;
	}

    /**
     *
     * @return
     */
    public boolean isFireModified() {
		return fireModified;
	}

    /**
     *
     * @param fireModified
     * @return
     */
    public PropertyModel setFireModified(boolean fireModified) {
		this.fireModified = fireModified;
		return this;
	}

    /**
     *
     * @return
     */
    public String getTooltip() {
		return tooltip;
	}

    /**
     *
     * @param editablePredicate
     * @return
     */
    public PropertyModel setEditablePredicate(String editablePredicate) {
		this.editablePredicate = editablePredicate;
		return this;
	}

    /**
     *
     * @return
     */
    public String getEditablePredicate() {
		return editablePredicate;
	}

    /**
     *
     * @return
     */
    public boolean isOptionRestricted() {
		return false;
	}

    /**
     *
     * @return
     */
    public boolean isMandatory() {
		return mandatory;
	}

    /**
     *
     * @param mandatory
     * @return
     */
    public PropertyModel setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
		return this;
	}

    /**
     *
     * @return
     */
    public final String getName() {
		return name;
	}

	private boolean reference;

    /**
     *
     * @return
     */
    public boolean isReference() {
		return reference;
	}

	private boolean valueObject;

    /**
     *
     * @return
     */
    public boolean isValueObject() {
		return valueObject;
	}

    /**
     *
     * @return
     */
    public String getDaoName() {
		if (!isReference()) {
                        throw new UnsupportedOperationException("not a reference: " + getContentType());
                }

		return lowercaseFirstLetter(getContentTypeName()) + "Dao";
	}

    /**
     *
     * @return
     */
    public String getContentTypeName() {
		String type = getContentType();
		int idx = type.lastIndexOf('.');
		return type.substring(idx + 1);
	}

    /**
     *
     * @return
     */
    public final boolean isSearchable() {
		return searchable;
	}

    /**
     *
     * @return
     */
    public boolean isAbstract() {
		return _abstract;
	}

    /**
     *
     * @return
     */
    public BeanModel getBean() {
		return beanModel;
	}

    /**
     *
     * @return
     */
    public EntityModel getEntity() {
		return (EntityModel) getBean();
	}

	// --- dependencies ---

	private BeanModel beanModel;

	private String name;

    /**
     *
     * @param beanModel
     * @param name
     * @param reference
     * @param valueObject
     */
    public PropertyModel(BeanModel beanModel, String name, boolean reference, boolean valueObject) {
		this.beanModel = beanModel;
		this.name = name;
		this.reference = reference;
		this.valueObject = valueObject;
	}

	private boolean _abstract;

    /**
     *
     * @param _abstract
     */
    public void setAbstract(boolean _abstract) {
		this._abstract = _abstract;
	}

	private boolean searchable;

    /**
     *
     * @param searchable
     * @return
     */
    public PropertyModel setSearchable(boolean searchable) {
		this.searchable = searchable;
		return this;
	}

	private boolean unique;

    /**
     *
     * @return
     */
    public final boolean isUnique() {
		return unique;
	}

    /**
     *
     * @param unique
     * @return
     */
    public final PropertyModel setUnique(boolean unique) {
		this.unique = unique;
		return this;
	}

}
