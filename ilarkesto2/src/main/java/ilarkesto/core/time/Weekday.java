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

public enum Weekday {

	MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(7), SUNDAY(1);

	private final int dayOfWeek;

	Weekday(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public int getDayOfWeekAssumeMondayIs1st() {
		int ret = dayOfWeek - 1;
		if (ret == 0) {
                        ret = 7;
                }
		return ret;
	}

	public static Weekday get(int dayOfWeek) {
		for (Weekday weekday : Weekday.values()) {
			if (weekday.dayOfWeek == dayOfWeek) {
                                return weekday;
                        }
		}
		throw new RuntimeException("Weekday does not exist: " + dayOfWeek);
	}

	public boolean isWeekend() {
		return this == SATURDAY || this == SUNDAY;
	}

	public boolean isWorkday() {
		return !isWeekend();
	}

	public boolean isSunday() {
		return this == SUNDAY;
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
