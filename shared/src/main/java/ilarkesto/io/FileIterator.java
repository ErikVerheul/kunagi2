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
package ilarkesto.io;


import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import static ilarkesto.io.IO.delete;
import java.io.File;
import java.util.Iterator;

/**
 * Iterator for files that includes sub dirs.
 */
public class FileIterator implements Iterator<File> {

	private File[] files;
	private int index = 0;
	private File currentFile;
	private FileIterator iterator;

        @SuppressWarnings("EI_EXPOSE_REP2")
	public void setFiles(File[] files) {
		this.files = files;
	}

	public void setDir(File file) {
		setFiles(file.listFiles());
	}

        @Override
	public boolean hasNext() {
		if (iterator != null) {
			boolean hasNext = iterator.hasNext();
			if (hasNext) {
                                return true;
                        }
			iterator = null;
		}
		if (files == null) {
                        return false;
                }
		return index < files.length;
	}

        @Override
	public File next() {
		if (iterator != null) {
                        return iterator.next();
                }
		currentFile = files[index++];
		if (currentFile.isDirectory()) {
			iterator = new FileIterator();
			iterator.setFiles(currentFile.listFiles());
			if (hasNext()) {
                                return next();
                        }
		}
		return currentFile;
	}

        @Override
	public void remove() {
		delete(currentFile);
	}

}
