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
package ilarkesto.form.validator;

import ilarkesto.form.ValidationException;
import static java.lang.Double.parseDouble;

/**
 * @author <A href="mailto:wi@koczewski.de">Witoslaw Koczewski </A>
 * @version 1 $Revision: 1.2 $ $Author: wko $ $Date: 2006/09/13 19:07:31 $
 */
public class DecimalValidator implements Validator {

    private double min;
    private double max;

    public DecimalValidator(double min, double max) {
        this.min = min;
        this.max = max;
    }

    // overriding
    @Override
    public String validate(String text) throws ValidationException {
        text = text.trim();
        double i;
        try {
            i =         parseDouble(text.replace(',', '.'));
        } catch (NumberFormatException ex) {
            throw new ValidationException("Die Eingabe mu\u00DF eine Zahl sein. " + getMessage());
        }
        if (i < min || i > max) {
                throw new ValidationException(getMessage());
        }
        return text;
    }

    public String getMessage() {
        return "Der Wert mu\u00DF zwischen " + min + " und " + max + " liegen.";
    }

}
