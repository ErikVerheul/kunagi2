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
package scrum.client.common;

import ilarkesto.core.base.Str;
import ilarkesto.core.base.Utl;

import java.util.ArrayList;
import java.util.List;

public class WeekdaySelector {

	public static final int IDX_SUN = 1;
	public static final int IDX_MON = 2;
	public static final int IDX_TUE = 3;
	public static final int IDX_WED = 4;
	public static final int IDX_THU = 5;
	public static final int IDX_FRI = 6;
	public static final int IDX_SAT = 7;
	public static final List<Integer> INDEXES = Utl.toList(IDX_MON, IDX_TUE, IDX_WED, IDX_THU, IDX_FRI, IDX_SAT,
		IDX_SUN);

	private boolean sun;
	private boolean mon;
	private boolean tue;
	private boolean wed;
	private boolean thu;
	private boolean fri;
	private boolean sat;

	public WeekdaySelector(int mask) {
		super();
		sun = (mask & 1) == 1;
		mon = (mask & 2) == 2;
		tue = (mask & 4) == 4;
		wed = (mask & 8) == 8;
		thu = (mask & 16) == 16;
		fri = (mask & 32) == 32;
		sat = (mask & 64) == 64;
	}

	public WeekdaySelector(List<Integer> weekdayIndexes) {
		for (Integer weekday : weekdayIndexes) {
			setWeekday(weekday, true);
		}
	}

	public boolean isFree(int dayOfWeek) {
		switch (dayOfWeek) {
			case 1:
				return sun;
			case 2:
				return mon;
			case 3:
				return tue;
			case 4:
				return wed;
			case 5:
				return thu;
			case 6:
				return fri;
			case 7:
				return sat;
		}
		return false;
	}

	public void setWeekday(int dayOfWeek, boolean value) {
		switch (dayOfWeek) {
			case IDX_SUN:
				sun = true;
				return;
			case IDX_MON:
				mon = true;
				return;
			case IDX_TUE:
				tue = true;
				return;
			case IDX_WED:
				wed = true;
				return;
			case IDX_THU:
				thu = true;
				return;
			case IDX_FRI:
				fri = true;
				return;
			case IDX_SAT:
				sat = true;
				return;
		}
	}

	public int createBitmask() {
		int mask = 0;
		if (sun) mask |= 1;
		if (mon) mask |= 2;
		if (tue) mask |= 4;
		if (wed) mask |= 8;
		if (thu) mask |= 16;
		if (fri) mask |= 32;
		if (sat) mask |= 64;
		return mask;
	}

	public List<String> getWeekdaysShort() {
		List<String> ret = new ArrayList<String>(7);
		if (mon) ret.add("Mon");
		if (tue) ret.add("Tue");
		if (wed) ret.add("Wed");
		if (thu) ret.add("Thu");
		if (fri) ret.add("Fri");
		if (sat) ret.add("Sat");
		if (sun) ret.add("Sun");
		return ret;
	}

	public List<String> getWeekdaysLong() {
		List<String> ret = new ArrayList<String>(7);
		if (mon) ret.add("Monday");
		if (tue) ret.add("Tuesday");
		if (wed) ret.add("Wednesday");
		if (thu) ret.add("Thursday");
		if (fri) ret.add("Friday");
		if (sat) ret.add("Saturday");
		if (sun) ret.add("Sunday");
		return ret;
	}

	public List<Integer> getWeekdaysIndexes() {
		List<Integer> ret = new ArrayList<Integer>(7);
		if (mon) ret.add(IDX_MON);
		if (tue) ret.add(IDX_TUE);
		if (wed) ret.add(IDX_WED);
		if (thu) ret.add(IDX_THU);
		if (fri) ret.add(IDX_FRI);
		if (sat) ret.add(IDX_SAT);
		if (sun) ret.add(IDX_SUN);
		return ret;
	}

	public void setSun(boolean sun) {
		this.sun = sun;
	}

	public void setMon(boolean mon) {
		this.mon = mon;
	}

	public void setTue(boolean tue) {
		this.tue = tue;
	}

	public void setWed(boolean wed) {
		this.wed = wed;
	}

	public void setThu(boolean thu) {
		this.thu = thu;
	}

	public void setFri(boolean fri) {
		this.fri = fri;
	}

	public void setSat(boolean sat) {
		this.sat = sat;
	}

	public boolean isSun() {
		return sun;
	}

	public boolean isMon() {
		return mon;
	}

	public boolean isTue() {
		return tue;
	}

	public boolean isWed() {
		return wed;
	}

	public boolean isThu() {
		return thu;
	}

	public boolean isFri() {
		return fri;
	}

	public boolean isSat() {
		return sat;
	}

	@Override
	public String toString() {
		return Str.concat(getWeekdaysShort(), ", ");
	}

	public boolean isSelectedAll() {
		return sun && mon && tue && wed && thu && fri && sat;
	}

	public boolean isSelectedSome() {
		return sun || mon || tue || wed || thu || fri || sat;
	}

	public boolean isSelectedNone() {
		return !isSelectedSome();
	}
}
