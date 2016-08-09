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
package ilarkesto.base;

import java.awt.Color;
import java.io.File;
import static java.lang.System.arraycopy;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static java.lang.Thread.getAllStackTraces;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Utility methods. Randomization, array and collection conversions.
 */
public class UtlExtend extends ilarkesto.core.base.Utl {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			out.println(randomInt(3, 5));
		}
	}

    /**
     *
     * @param elements
     * @return
     */
    public static String getFirstNonDefaultElementAsString(StackTraceElement[] elements) {
		StackTraceElement element = getFirstNonDefaultElement(elements);
		return element == null ? null : element.getClassName() + "." + element.getMethodName();
	}

    /**
     *
     * @param elements
     * @return
     */
    public static StackTraceElement getFirstNonDefaultElement(StackTraceElement[] elements) {
		if (elements == null || elements.length == 0) {
                        return null;
                }
		for (StackTraceElement element : elements) {
			String cls = element.getClassName();
			if (cls.startsWith("java.")) {
                                continue;
                        }
			if (cls.startsWith("sun.")) {
                                continue;
                        }
			return element;
		}
		return elements[elements.length - 1];
	}

    /**
     *
     * @param elements
     * @param separator
     * @return
     */
    public static String formatStackTrace(StackTraceElement[] elements, String separator) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		int len = elements.length;
		for (int i = len - 1; i >= 0; i--) {
			StackTraceElement element = elements[i];
			if (first) {
				first = false;
			} else {
				sb.append(separator);
			}
			sb.append(element.getClassName()).append(".").append(element.getMethodName()).append("()");
			int line = element.getLineNumber();
			if (line >= 0) {
                                sb.append(":").append(line);
                        }
		}
		return sb.toString();
	}

    /**
     *
     * @return
     */
    public static Set<Thread> getAllThreads() {
		return getAllStackTraces().keySet();
	}

    /**
     *
     * @param paths
     * @return
     */
    public static File getFirstExistingFile(String... paths) {
		for (String path : paths) {
			File file = new File(path);
			if (file.exists()) {
                                return file;
                        }
		}
		return null;
	}

    /**
     *
     * @param <T>
     * @param collection
     * @param index
     * @return
     */
    public static <T> T getElement(Collection<T> collection, int index) {
		int i = 0;
		for (T t : collection) {
			if (i == index) {
                                return t;
                        }
			i++;
		}
		return null;
	}

    /**
     *
     * @param color
     * @return
     */
    public static Color parseHtmlColor(String color) {
		return new Color(parseHex(color.substring(1)));
	}

    /**
     *
     * @param ex
     * @return
     */
    public static Exception getRootCause(Exception ex) {
		Throwable cause = ex.getCause();
		return cause == null ? ex : getRootCause((Exception) cause);
	}

	private static final Random random = new Random(currentTimeMillis());

    /**
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
		if (s == null) {
                        return true;
                }
		if (s.length() == 0) {
                        return true;
                }
		return s.trim().length() == 0;
	}

    /**
     *
     * @param <K>
     * @param <V>
     * @param source
     * @param keys
     * @return
     */
    public static <K, V> Map<K, V> subMap(Map<K, V> source, K... keys) {
		Map<K, V> ret = new HashMap<>();
		for (K key : keys) {
                        ret.put(key, source.get(key));
                }
		return ret;
	}

    /**
     *
     * @param o
     * @return
     */
    public static String toStringWithType(Object o) {
		return null == o ? "?: null" : o.getClass().getSimpleName() + ": " + toString(o);
	}

    /**
     *
     * @param o
     * @return
     */
    public static String toString(Object o) {
		if (o == null) {
                        return null;
                }
		try {
			return o.toString();
		} catch (Exception ex) {
			return "<toString() error in" + o.getClass().getSimpleName() + ">";
		}
	}

    /**
     *
     * @param elements
     * @return
     */
    public static String randomElement(String... elements) {
		return elements[random.nextInt(elements.length)];
	}

    /**
     *
     * @param <T>
     * @param elements
     * @return
     */
    public static <T> T randomElement(List<T> elements) {
		return elements.get(random.nextInt(elements.size()));
	}

    /**
     *
     * @return
     */
    public static boolean randomBoolean() {
		return random.nextBoolean();
	}

    /**
     *
     * @param min
     * @param max
     * @return
     */
    public static int randomInt(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}

    /**
     *
     * @param charSet
     * @return
     */
    public static char randomChar(String charSet) {
		int index = randomInt(0, charSet.length() - 1);
		return charSet.charAt(index);
	}

    /**
     *
     * @param elements
     * @return
     */
    public static File[] toFileArray(Collection<File> elements) {
		File[] ret = new File[elements.size()];
		arraycopy(elements.toArray(), 0, ret, 0, ret.length);
		return ret;
	}

    /**
     *
     * @param elements
     * @return
     */
    public static String[] toStringArray(Collection<String> elements) {
		String[] ret = new String[elements.size()];
		arraycopy(elements.toArray(), 0, ret, 0, ret.length);
		return ret;
	}

    /**
     *
     * @param <E>
     * @param elements
     * @return
     */
    public static <E> Set<E> toSet(E... elements) {
		Set<E> ret = new HashSet<>(elements.length);
                ret.addAll(asList(elements));
		return ret;
	}

    /**
     *
     * @param <T>
     * @param enumeration
     * @return
     */
    public static <T> List<T> toList(Enumeration<T> enumeration) {
		List<T> ret = new ArrayList<>();
		while (enumeration.hasMoreElements()) {
			ret.add(enumeration.nextElement());
		}
		return ret;
	}

    /**
     *
     * @param <T>
     * @param collection
     * @param count
     * @param comparator
     * @return
     */
    public static <T> List<T> getHighest(Collection<T> collection, int count, Comparator<T> comparator) {
		// TODO performance optimization: sort not necessary
		List<T> list = sort(collection, comparator);
		List<T> result = new ArrayList<>(count);
		for (int i = 0; i < count && i < list.size(); i++) {
                        result.add(list.get(i));
                }
		return result;
	}

    /**
     *
     * @param millis
     */
    public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

}
