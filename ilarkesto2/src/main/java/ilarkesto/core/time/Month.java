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
package ilarkesto.core.time;

import ilarkesto.core.base.Utl;

public enum Month {

	JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10), NOVEMBER(
			11), DECEMBER(12);

	private final int monthOfYear;

	Month(int monthOfYear) {
		this.monthOfYear = monthOfYear;
	}

	public int getMonthOfYear() {
		return monthOfYear;
	}

	public static Month get(int monthOfYear) {
		for (Month month : Month.values()) {
			if (month.monthOfYear == monthOfYear) {
                                return month;
                        }
		}
		throw new RuntimeException("Month does not exist: " + monthOfYear);
	}

	public String toString(String language) {
		return Tm.getLocalizer(language).full(this);
	}

	public String toLocalString() {
		return toString(Utl.getLanguage());
	}

	@Override
	public String toString() {
		return toString("en");
	}

}
