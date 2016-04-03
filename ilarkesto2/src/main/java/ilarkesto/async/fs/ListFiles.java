/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.async.fs;

import ilarkesto.async.AJob;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListFiles extends AJob<List<File>> {

	private File rootDir;
	private List<File> result = new ArrayList<>();

	private boolean recurse;
	private boolean includeDirs;

	public ListFiles(File rootDir) {
		this.rootDir = rootDir;
	}

	public ListFiles(String rootPath) {
		this(new File(rootPath));
	}

	@Override
	public List<File> runJob() {
		listFiles(rootDir);
		return result;
	}

	private void listFiles(File dir) {
		if (dir == null) {
                        return;
                }
		if (!dir.isDirectory()) {
                        return;
                }
		File[] files = dir.listFiles();
		if (files == null) {
                        return;
                }
		for (File file : files) {
			if (file.isDirectory()) {
				if (includeDirs) {
                                        result.add(file);
                                }
				if (recurse) {
                                        listFiles(file);
                                }
			} else {
				result.add(file);
			}
		}
	}

	public ListFiles setRecurse(boolean recurse) {
		this.recurse = recurse;
		return this;
	}

	public ListFiles setIncludeDirs(boolean includeDirs) {
		this.includeDirs = includeDirs;
		return this;
	}

	@Override
	public String toString() {
		return toString(rootDir);
	}

}
