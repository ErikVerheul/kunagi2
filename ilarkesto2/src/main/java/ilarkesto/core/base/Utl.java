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
package ilarkesto.core.base;

import ilarkesto.gwt.client.AGwtEntity;
import static java.lang.Integer.parseInt;
import static java.lang.System.arraycopy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author erik
 */
public class Utl {

    private static String language = "en";

    /**
     *
     * @param exceptionType
     * @param ex
     * @return
     */
    public static boolean isRootCause(Class<? extends Exception> exceptionType, Exception ex) {
        Exception cause = getRootCause(ex);
        return cause.getClass().equals(exceptionType);
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

    /**
     *
     * @param ex
     * @return
     */
    public static String getUserMessage(Exception ex) {
        if (ex instanceof NullPointerException) {
            return "NullPointerException";
        }
        if (ex instanceof RuntimeException) {
            return ex.getMessage();
        }
        return Str.getSimpleName(ex.getClass()) + ": " + ex.getMessage();
    }

    /**
     *
     * @param ex
     * @return
     */
    public static String getUserMessageStack(Exception ex) {
        return getUserMessageStack(ex, " -> ");
    }

    /**
     *
     * @param ex
     * @param separator
     * @return
     */
    public static String getUserMessageStack(Exception ex, String separator) {
        StringBuilder sb = null;
        while (ex != null) {
            if (sb == null) {
                sb = new StringBuilder();
            } else {
                sb.append(separator);
            }
            sb.append(getUserMessage(ex));
            ex = (Exception) ex.getCause();
        }
        return sb == null ? null : sb.toString();
    }

    /**
     *
     * @param arrays
     * @return
     */
    public static String[] concat(String[]... arrays) {
        int len = 0;
        for (String[] array : arrays) {
            len += array.length;
        }
        String[] ret = new String[len];
        int offset = 0;
        for (String[] array : arrays) {
            arraycopy(array, 0, ret, offset, array.length);
            offset += array.length;
        }
        return ret;
    }

    /**
     *
     * @param <T>
     * @param collection
     * @return
     */
    public static <T extends Comparable> List<T> sort(Collection<T> collection) {
        List<T> result = new ArrayList<T>(collection);
        Collections.sort(result);
        return result;
    }

    /**
     *
     * @param <T>
     * @param collection
     * @param comparator
     * @return
     */
    public static <T> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        List<T> result = new ArrayList<T>(collection);
        Collections.sort(result, comparator);
        return result;
    }

    /**
     *
     * @param objects
     * @return
     */
    public static int hashCode(Object... objects) {
        int hashCode = 23;
        for (Object object : objects) {
            if (object == null) {
                continue;
            }
            if (object instanceof Object[]) {
                hashCode = hashCode * 37 + hashCode((Object[]) object);
            } else {
                hashCode = hashCode * 37 + object.hashCode();
            }
        }
        return hashCode;
    }

    /**
     *
     * @param language
     */
    public static void setLanguage(String language) {
        Utl.language = language;
    }

    /**
     *
     * @return
     */
    public static String getLanguage() {
        return language;
    }

    /**
     *
     * @param collection
     */
    public static void removeDuplicates(Collection collection) {
        Set set = new HashSet(collection.size());
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (!set.add(object)) {
                iterator.remove();
            }
        }
    }

    /**
     *
     * @param <T>
     * @param collection
     * @return
     */
    public static <T> T getFirstElement(Collection<T> collection) {
        return collection.iterator().next();
    }

    /**
     *
     * @param <T>
     * @param elements
     * @return
     */
    public static <T> List<T> toList(T... elements) {
        if (elements == null) {
            return null;
        }
        List<T> ret = new ArrayList<T>(elements.length);
        for (T element : elements) {
            if (element == null) {
                continue;
            }
            ret.add(element);
        }
        return ret;
    }

    /**
     * Check if the first given parameter equalObjects at least one of the other parameters
     * @return 
     */
    public static boolean equalsAny(Object o, Object... others) {
        for (Object other : others) {
            if (o.equals(other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check for equality of two objects. Handle special cases as this method is used for kinds of objects in the source generator.
     *
     * @param a any object
     * @param b any object
     * @return true if equal
     */
    public static boolean equalObjects(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        // compare arrays of objects
        if (a instanceof Object[] && b instanceof Object[]) {
            Object[] aa = (Object[]) a;
            Object[] ba = (Object[]) b;
            if (aa.length != ba.length) {
                return false;
            }
            for (int i = 0; i < aa.length; i++) {
                if (!equalObjects(aa[i], ba[i])) {
                    return false;
                }
            }
            return true;
        }
        
        // a special case, not an array 
        if (a instanceof String && b instanceof AGwtEntity) {
            return a.equals(((AGwtEntity) b).getId());
        }
        
        return a.equals(b);
    }

    /**
     *
     * @param i1
     * @param i2
     * @return
     */
    public static int compare(int i1, int i2) {
        if (i1 > i2) {
            return 1;
        }
        if (i1 < i2) {
            return -1;
        }
        return 0;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static int compare(Comparable a, Comparable b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }
        return a.compareTo(b);
    }

    /**
     *
     * @param hex
     * @return
     */
    public static int parseHex(String hex) {
        return parseInt(hex, 16);
    }

    /**
     *
     * @param items
     * @param separator
     * @return
     */
    public static String concatToHtml(Collection<? extends ToHtmlSupport> items, String separator) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (ToHtmlSupport entity : items) {
            if (first) {
                first = false;
            } else {
                sb.append(separator);
            }
            sb.append(entity.toHtml());
        }
        return sb.toString();
    }

    /**
     *
     * @param type
     * @return
     * @deprecated
     */
    @Deprecated
    public static String getSimpleName(Class type) {
        return Str.getSimpleName(type);
    }

    /**
     *
     * @param <T>
     * @param e
     * @return
     */
    public static <T> List<T> toList(Enumeration<T> e) {
        if (e == null) {
            return null;
        }
        List<T> ret = new ArrayList<T>();
        while (e.hasMoreElements()) {
            ret.add(e.nextElement());
        }
        return ret;
    }

    /**
     *
     * @param <T>
     * @param list
     * @param count
     */
    public static <T> void removeFirstElements(List<T> list, int count) {
        for (int i = 0; i < count; i++) {
            list.remove(0);
        }
    }

    /**
     *
     * @param <T>
     * @param list
     * @return
     */
    public static final <T extends Comparable> List<T> sortReverse(List<T> list) {
        Collections.sort(list, REVERSE_COMPARATOR);
        return list;
    }

    /**
     *
     */
    public static final Comparator REVERSE_COMPARATOR = new Comparator<Comparable>() {

        @Override
        public int compare(Comparable a, Comparable b) {
            return b.compareTo(a);
        }
    };

}
