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
 *
 */
public abstract class ACollectionPropertyModel extends PropertyModel {

    /**
     *
     * @return
     */
    protected abstract Class getCollectionTypeClass();

    /**
     *
     * @return
     */
    protected abstract Class getCollectionImplClass();

    /**
     *
     * @return
     */
    @Override
	public final String getType() {
		return getCollectionType() + "<" + getContentType() + ">";
	}

    /**
     *
     * @return
     */
    @Override
	public String getCollectionType() {
		return getCollectionTypeClass().getName();
	}

    /**
     *
     * @return
     */
    @Override
	public String getCollectionImpl() {
		return getCollectionImplClass().getName();
	}

    /**
     *
     * @return
     */
    @Override
	public String getContentType() {
		return contentType;
	}

    /**
     *
     * @return
     */
    @Override
	public final boolean isCollection() {
		return true;
	}

    /**
     *
     * @return
     */
    @Override
	public String getNameSingular() {
		String name = getName();
		if (!name.endsWith("s")) {
                        throw new RuntimeException("property name must end with 's', but does not: " + name);
                }
		return name.substring(0, name.length() - 1);
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isBoolean() {
		return false;
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isPrimitive() {
		return false;
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isString() {
		return false;
	}

	// --- dependencies ---

	private String contentType;

    /**
     *
     * @param entityModel
     * @param name
     * @param reference
     * @param valueObject
     * @param contentType
     */
    public ACollectionPropertyModel(BeanModel entityModel, String name, boolean reference, boolean valueObject,
			String contentType) {
		super(entityModel, name, reference, valueObject);
		this.contentType = contentType;
	}

    /**
     *
     * @param entityModel
     * @param name
     * @param reference
     * @param valueObject
     * @param contentType
     */
    public ACollectionPropertyModel(BeanModel entityModel, String name, boolean reference, boolean valueObject,
			Class contentType) {
		this(entityModel, name, reference, valueObject, contentType.getName());
	}

}
