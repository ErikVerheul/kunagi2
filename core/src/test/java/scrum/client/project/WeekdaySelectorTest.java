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
package scrum.client.project;

import ilarkesto.testng.ATest;

import org.testng.annotations.Test;

import scrum.client.common.WeekdaySelector;

public class WeekdaySelectorTest extends ATest {

	@Test
	public void none() {
		WeekdaySelector days = new WeekdaySelector(0);
		assertFalse(days.isSun());
		assertFalse(days.isMon());
		assertFalse(days.isTue());
		assertFalse(days.isWed());
		assertFalse(days.isThu());
		assertFalse(days.isFri());
		assertFalse(days.isSat());
	}

	@Test
	public void all() {
		WeekdaySelector days = new WeekdaySelector(0);
		days.setSun(true);
		days.setMon(true);
		days.setTue(true);
		days.setWed(true);
		days.setThu(true);
		days.setFri(true);
		days.setSat(true);
		int bitmask = days.createBitmask();
		assertEquals(bitmask, 127);
		days = new WeekdaySelector(bitmask);
		assertTrue(days.isSun());
		assertTrue(days.isMon());
		assertTrue(days.isTue());
		assertTrue(days.isWed());
		assertTrue(days.isThu());
		assertTrue(days.isFri());
		assertTrue(days.isSat());
	}

	@Test
	public void some() {
		WeekdaySelector days = new WeekdaySelector(0);
		days.setSun(true);
		days.setMon(false);
		days.setTue(false);
		days.setWed(true);
		days.setThu(false);
		days.setFri(false);
		days.setSat(true);
		int bitmask = days.createBitmask();
		assertEquals(bitmask, 73);
		days = new WeekdaySelector(bitmask);
		assertTrue(days.isSun());
		assertFalse(days.isMon());
		assertFalse(days.isTue());
		assertTrue(days.isWed());
		assertFalse(days.isThu());
		assertFalse(days.isFri());
		assertTrue(days.isSat());
	}

}
