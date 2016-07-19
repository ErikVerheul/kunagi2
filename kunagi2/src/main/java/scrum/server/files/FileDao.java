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

import ilarkesto.core.time.DateAndTime;
import ilarkesto.fp.Predicate;
import scrum.server.project.Project;

public class FileDao extends GFileDao {

    public File getFilesByName(final String filename, final Project project) {
        return getEntity(new Predicate<File>() {

            @Override
            public boolean test(File e) {
                return e.isProject(project) && e.isFilename(filename);
            }
        });
    }

    public File getFileByNumber(final int number, final Project project) {
        return getEntity(new Predicate<File>() {

            @Override
            public boolean test(File t) {
                return t.isNumber(number) && t.isProject(project);
            }
        });
    }

    public File postFile(java.io.File f, Project project) {
        File file = newEntityInstance();
        file.setProject(project);
        file.setFilename(f.getName());
        file.setLabel(createLabel(f));
        file.setUploadTime(DateAndTime.now());
        file.updateNumber();
        saveEntity(file);
        return file;
    }

    private String createLabel(java.io.File file) {
        String label = file.getName();
        int idx = label.lastIndexOf('.');
        if (idx > 0) {
            label = label.substring(0, idx);
        }
        return label;
    }
}