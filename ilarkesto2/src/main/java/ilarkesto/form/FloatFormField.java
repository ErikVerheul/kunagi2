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

import static java.lang.Float.parseFloat;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

public class FloatFormField extends AFormField {

	private DecimalFormat format = new DecimalFormat("0.##");
	private String sValue;
	private Float fValue;
	private int width = 10;
	private String suffix;

	public FloatFormField(String name) {
		super(name);
	}

	public FloatFormField setSuffix(String suffix) {
		this.suffix = suffix;
		return this;
	}

	public String getSuffix() {
		return suffix;
	}

	public FloatFormField setWidth(int value) {
		this.width = value;
		return this;
	}

	public FloatFormField setValue(Float value) {
		fValue = value;
		updateSValue();
		return this;
	}

	private void updateSValue() {
		if (fValue == null) {
			sValue = null;
			return;
		}
		sValue = format.format(fValue);
	}

	private void updateFValue() {
		if (sValue == null) {
			fValue = null;
			return;
		}
		Float f = parse(sValue);
		if (f != null) {
                        fValue = f;
                }
	}

	private Float parse(String s) {
		s = s.replace(".", "");
		s = s.replace(',', '.');
		try {
			return parseFloat(s);
		} catch (Exception ex) {
			return null;
		}
	}

	public int getWidth() {
		return width;
	}

	@Override
	public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
		sValue = data.get(getName());
		if (sValue != null) {
                        sValue = sValue.trim();
                }
		if (sValue != null && sValue.length() == 0) {
                        sValue = null;
                }
		updateFValue();
	}

	@Override
	public void validate() throws ValidationException {
		if (sValue == null) {
			if (isRequired()) {
                                throw new ValidationException("Eingabe erforderlich");
                        }
		} else {
			if (parse(sValue) == null) {
                                throw new ValidationException("Zahl wurde nicht erkannt: " + sValue);
                        }
		}
	}

	@Override
	public String getValueAsString() {
		return sValue;
	}

	public Float getValue() {
		return fValue;
	}

	public FloatFormField setFormat(DecimalFormat format) {
		this.format = format;
		updateSValue();
		return this;
	}

	public FloatFormField setFormat(String format) {
		return setFormat(new DecimalFormat(format));
	}

}
