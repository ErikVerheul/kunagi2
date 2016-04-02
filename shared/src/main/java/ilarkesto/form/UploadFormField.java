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

import ilarkesto.base.Bytes;
import ilarkesto.id.CountingIdGenerator;
import ilarkesto.id.IdGenerator;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;
import static org.mortbay.log.Log.warn;

public class UploadFormField extends AFormField {

    private File file;
    private Integer maxFilesize = 10000000;

    public UploadFormField(String name) {
        super(name);
        setRequired(true);
    }

        @Override
    public String getValueAsString() {
        return file == null ? null : file.getName();
    }

    private boolean maxFileSizeExceeded;

        @Override
        public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
                maxFileSizeExceeded = false;
                for (FileItem item : uploadedFiles) {
                        if (item.getFieldName().equals(getName())) {
                                if (item.getSize() == 0) {
                                        file = null;
                                        return;
                                }
                                if (maxFilesize != null && item.getSize() > maxFilesize) {
                                        maxFileSizeExceeded = true;
                                        return;
                                }
                                file = new File(applicationTempDir + "/uploadedFiles/" + folderIdGenerator.generateId() + "/"
                                        + item.getName());
                                if (file.getParentFile().mkdirs()) {
                                        try {
                                                item.write(file);
                                        } catch (Exception ex) {
                                                throw new RuntimeException(ex);
                                        }
                                } warn("Can not create directory");
                        }
                }
        }

    public File getValue() {
        return file;
    }

        @Override
    public void validate() throws ValidationException {
        if (maxFileSizeExceeded) { throw new ValidationException(
                "Die Datei ist zu gro\u00DF. Maximale Dateigr\u00F6\u00DFe: "
                        + new Bytes(maxFilesize).toRoundedString()); }
        if (file == null && isRequired()) { throw new ValidationException("Eingabe erforderlich."); }
    }

    public Integer getMaxFilesize() {
        return maxFilesize;
    }

    public UploadFormField setMaxFilesize(Integer maxFilesize) {
        this.maxFilesize = maxFilesize;
        return this;
    }

    // --- dependencies ---

    private static final IdGenerator folderIdGenerator = new CountingIdGenerator(UploadFormField.class.getSimpleName());

    private String applicationTempDir = "";

    public void setApplicationTempDir(String applicationTempDir) {
        this.applicationTempDir = applicationTempDir;
    }

}
