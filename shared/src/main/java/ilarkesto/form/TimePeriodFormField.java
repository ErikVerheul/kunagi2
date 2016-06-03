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
package ilarkesto.form;

import ilarkesto.core.time.TimePeriod;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

public class TimePeriodFormField extends AFormField {

	private String value;

	public TimePeriodFormField(String name) {
		super(name);
	}

	public TimePeriodFormField setValue(TimePeriod value) {
		this.value = value == null ? null : value.toHoursAndMinutesString();
		return this;
	}

	@Override
	public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
		String newValue = prepareValue(data.get(getName()));
		if (value == null ? newValue == null : value.equals(newValue)) {
                        return;
                }
		value = newValue;
		fireFieldValueChanged();

	}

	private static String prepareValue(String s) {
		if (s == null) {
                        return null;
                }
		s = s.trim();
		if (s.length() == 0) {
                        return null;
                }
		if (s.indexOf(':') < 0) {
                        s += ":00";
                }
		try {
			return new TimePeriod(s).toHoursAndMinutesString();
		} catch (Throwable ex) {
			return s;
		}
	}

	@Override
	public void validate() throws ValidationException {
		if (value == null) {
			if (isRequired()) { throw new ValidationException("Eingabe erforderlich"); }
		} else {
			try {
				new TimePeriod(value);
			} catch (Throwable ex) {
				throw new ValidationException("Eingabe muss eine Uhrzeit sein. " + ex.getMessage());
			}
		}

	}

	@Override
	public String getValueAsString() {
		return value;
	}

	public TimePeriod getValueAsTimePeriod() {
		return value == null ? null : new TimePeriod(value);
	}

}
