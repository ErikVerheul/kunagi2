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
package ilarkesto.junit;


import static org.junit.Assert.*;
import java.util.Collection;

/**
 *
 *
 */
public class AjunitTest {

    /**
     *
     */
    public static final String OUTPUT_DIR = "target/test-output";

    /**
     *
     */
    public static final String INPUT_DIR = "test-input";

    /**
     *
     * @param a
     * @param b
     */
    public static void assertNotEquals(Object a, Object b) {
		assertFalse("Objects expected not to be equal: <" + a + "> and <" + b + ">", a.equals(b));
	}

    /**
     *
     * @param collection
     * @param expectedSize
     */
    public static void assertSize(Collection collection, int expectedSize) {
		assertNotNull("Collection expected to be not null", collection);
		assertEquals("Collection size expected to be <" + expectedSize + ">, but is <"
				+ collection.size() + ">: <" + collection + ">", collection.size(), expectedSize);
	}

    /**
     *
     * @param <T>
     * @param string
     * @param substring
     */
    public static <T> void assertContains(String string, String substring) {
		assertTrue("<" + string + "> expected to contain <" + substring + ">", string.contains(substring));
	}

    /**
     *
     * @param <T>
     * @param collection
     * @param element
     */
    public static <T> void assertContains(Collection<T> collection, T element) {
		assertTrue("Collection expected to contain <" + element + ">", collection.contains(element));
	}

    /**
     *
     * @param collection
     */
    public static void assertNotEmpty(Collection collection) {
		assertFalse("Collection expected to be not empty, but it is", collection.isEmpty());
	}

    /**
     *
     * @param actual
     * @param expectedPrefix
     */
    public static void assertStartsWith(String expectedPrefix, String actual) {
		assertTrue("<" + actual + "> expected to start with <" + expectedPrefix
				+ "> |", actual.startsWith(expectedPrefix));
	}

}
