/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
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
package ilarkesto.properties;

import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;
import static ilarkesto.io.IO.loadProperties;
import static ilarkesto.io.IO.saveProperties;
import static ilarkesto.io.IO.touch;
import java.io.File;
import java.util.Properties;

public class FilePropertiesStore extends APropertiesStore {

	private static final Log LOG = Log.get(FilePropertiesStore.class);
	private static final String CHARSET = IO.UTF_8;

	private String label = getClass().getSimpleName();
	private long lastModifiedOnLoad;

	@Override
	protected boolean isLoadedPropertiesChanged() {
		if (!file.exists()) {
                        return false;
                }
		return file.lastModified() > lastModifiedOnLoad;
	}

	@Override
	protected Properties load() {
		Properties p;
		if (file.exists()) {
			p = loadProperties(file, CHARSET);
			lastModifiedOnLoad = file.lastModified();
			LOG.info("Loaded properties:", file);
		} else {
			p = new Properties();
		}
		return p;
	}

	@Override
	protected void save(Properties properties) {
		saveProperties(properties, label, file);
	}

	@Override
	public String toString() {
		return file.getPath();
	}

	public FilePropertiesStore setLabel(String label) {
		this.label = label;
		return this;
	}

	// --- dependencies ---

	private File file;

	public FilePropertiesStore(File file, boolean createFileIfNotExists) {
		this.file = file;
		if (createFileIfNotExists && !file.exists()) {
			touch(file);
			LOG.info("Properties file created:", file.getPath());
		}
	}

	public FilePropertiesStore(String path, boolean createFileIfNotExists) {
		this(new File(path), createFileIfNotExists);
	}

}
