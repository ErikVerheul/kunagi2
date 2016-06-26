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
package ilarkesto.testNG.core.base;

import ilarkesto.core.base.Utl;
import static ilarkesto.core.base.Utl.toList;
import ilarkesto.testng.ATest;
import java.util.List;
import org.testng.annotations.Test;

public class UtlTest extends ATest {

	@Test
	public void equals() {
		Object[] a = { "hello", "equals" };
		Object[] b = { "hello", "equals" };
		Object[] c = { "hello", "world" };
		assertTrue(Utl.equalObjects(a, b));
		assertFalse(Utl.equalObjects(a, c));
	}

	@Test
	public void removeDuplicates() {
		List<String> list = toList("a", "b", "b", "a");
		Utl.removeDuplicates(list);
		assertEquals(list, toList("a", "b"));
	}

}
