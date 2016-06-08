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

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import ilarkesto.base.StrExtend;
import static ilarkesto.base.StrExtend.tokenize;
import ilarkesto.base.UtlExtend;
import static ilarkesto.core.base.Utl.toList;
import ilarkesto.email.EmailAddress;
import java.io.File;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import java.util.List;
import java.util.Properties;

public abstract class APropertiesStore {

	private Properties properties;
	private Properties defaults;

	protected abstract Properties load();

	protected abstract void save(Properties properties);

	// --- String ---

        @SuppressWarnings("NP_LOAD_OF_KNOWN_NULL_VALUE") // defaults can be set after initialization by setDefaults(Properties defaults)
	protected boolean isLoadedPropertiesChanged() {
		return false;
	}

	public final String get(String name, String defaultValue) {
		String value = getProperties().getProperty(name);
		if (value != null) {
                        return value;
                }
		if (defaults != null) {
                        return defaults.getProperty(name, defaultValue);
                }
		return defaultValue;
	}

	public final String get(String name) {
		return get(name, null);
	}

	public final void set(String name, String value) {
		String oldValue = get(name);
		if (value == null ? oldValue == null : value.equals(oldValue)) {
                        return;
                }
		Properties p = getProperties();
		if (value == null) {
			p.remove(name);
		} else {
			p.setProperty(name, value);
		}
		save(p);
	}

	public void remove(String name) {
		Properties p = getProperties();
		p.remove(name);
		save(p);
	}

	// --- List<String> ---

	public final List<String> getList(String name, List<String> defaultValue) {
		String s = get(name);
		return s == null ? defaultValue : toList(tokenize(s, ";"));
	}

	// --- Integer ---

	public final Integer getInteger(String name) {
		String s = get(name);
		if (s == null) {
                        return null;
                }
		return parseInt(s);
	}

	// --- int ---

	public final int getInt(String name, int defaultValue) {
		String s = get(name);
		return s == null ? defaultValue : parseInt(s);
	}

	// --- boolean ---

	public final boolean getBoolean(String name, boolean defaultValue) {
		String s = get(name);
		return s == null ? defaultValue : parseBoolean(s);
	}

	// --- crypt ---

	public final String getCrypted(String name) {
		// TODO decrypt
		return get(name);
	}

	public final String getMandatoryCrypted(String name) {
		String s = getCrypted(name);
		if (s == null) {
                        throw new RuntimeException("Missing mandatory property '" + name + "' in " + this);
                }
		return s;
	}

	public final void setCrypted(String name, String value) {
		// TODO encrypt
		set(name, value);
	}

	// --- File ---

	public final File getFile(String name) {
		return getFile(name, null);
	}

	public final File getFile(String name, File defaultValue) {
		String path = get(name);
		if (path == null) {
                        return defaultValue;
                }
		return new File(path); // TODO cache file
	}

	public final File getMandatoryFile(String name) {
		return new File(getMandatory(name));
	}

	public final void set(String name, File value) {
		set(name, value.getPath());
	}

	// --- EmailAddress ---

	public final EmailAddress getEmailAddress(String name) {
		String s = get(name);
		return s == null ? null : new EmailAddress(s);
	}

	public final EmailAddress getMandatoryEmailAddress(String name) {
		return new EmailAddress(getMandatory(name));
	}

	public final String getMandatory(String name) {
		String value = get(name);
		if (value == null) {
                        throw new RuntimeException("Missing mandatory property '" + name + "' in " + this);
                }
		return value;
	}

	// --- ---

	private Properties getProperties() {
		if (properties == null || isLoadedPropertiesChanged()) {
			properties = load();
		}
		return properties;
	}

	public void setDefaults(Properties defaults) {
		if (properties != null) {
                        throw new IllegalStateException("Can not set defaults. Already loaded.");
                }
		this.defaults = defaults;
	}

}
