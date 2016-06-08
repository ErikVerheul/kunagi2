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
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package ilarkesto.base;

import static ilarkesto.base.StrExtend.tokenize;
import static ilarkesto.base.TmExtend.year;
import ilarkesto.core.time.Date;
import static ilarkesto.core.time.Date.today;
import ilarkesto.core.time.DateAndTime;
import static java.lang.Integer.parseInt;
import java.text.DateFormat;
import java.text.ParseException;

public class DateParser {

	public static Date parseDate(String s) throws ParseException {
		if (s == null) {
                        return null;
                }
		s = s.trim();
		String[] sa = tokenize(s, ".,- ");
		if (sa.length == 0) {
                        throw new ParseException("Not a Date: " + s, -1);
                }
		if (sa.length > 3) {
                        throw new ParseException("Not a Date: " + s, -1);
                }
		int[] ia = new int[sa.length];
		for (int i = 0; i < ia.length; i++) {
			try {
				ia[i] = parseInt(sa[i]);
			} catch (NumberFormatException e) {
				throw new ParseException("Not a Date: " + s, -1);
			}
		}

		if (ia.length == 3) {
                        return new Date(year(ia[2]), ia[1], ia[0]);
                }

		Date today = today();
		int todayDay = today.getDay();
		int todayMonth = today.getMonth();
		int todayYear = today.getYear();

		if (ia.length == 2) {
			if (ia[1] > 12) {
                                return new Date(year(ia[1]), ia[0], todayDay);
                        }
			return new Date(todayYear, ia[1], ia[0]);
		}

		if (ia[0] > 31) {
                        return new Date(year(ia[0]), todayMonth, todayDay);
                }
		return new Date(todayYear, todayMonth, ia[0]);
	}

	public static DateAndTime parseDateAndTime(String s, DateFormat... formats) throws ParseException {
		ParseException ex = null;
		for (DateFormat format : formats) {
			try {
				return new DateAndTime(format.parse(s));
			} catch (ParseException e) {
				ex = e;
			}
		}
		throw ex;
	}

}
