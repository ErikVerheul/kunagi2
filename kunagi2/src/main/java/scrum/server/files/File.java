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
package scrum.server.files;

import ilarkesto.io.IO;
import scrum.server.admin.User;

/**
 *
 * @author erik
 */
public class File extends GFile {

	// --- dependencies ---

	// --- ---

    /**
     *
     */
    
	public void deleteFile() {
		IO.delete(getJavaFile());
	}

    /**
     *
     * @return
     */
    public java.io.File getJavaFile() {
		return new java.io.File(getProject().getFileRepositoryPath() + "/" + getFilename());
	}

    /**
     *
     */
    public void updateNumber() {
		if (isNumber(0)) {
                    setNumber(getProject().generateFileNumber());
        }
	}

        @Override
	public boolean isVisibleFor(User user) {
		return getProject().isVisibleFor(user);
	}

    /**
     *
     * @return
     */
    public String getReferenceAndLabel() {
		return getReference() + " (" + getLabel() + ")";
	}

    /**
     *
     * @return
     */
    public String getReference() {
		return scrum.client.files.File.REFERENCE_PREFIX + getNumber();
	}

    /**
     *
     * @param user
     * @return
     */
    public boolean isEditableBy(User user) {
		return getProject().isEditableBy(user);
	}

	@Override
	public String toString() {
		return getReferenceAndLabel();
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();
		updateNumber();
	}

}