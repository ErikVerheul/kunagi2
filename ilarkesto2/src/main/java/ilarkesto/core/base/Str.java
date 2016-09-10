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

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import static ilarkesto.core.base.Utl.toList;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isLetterOrDigit;

/**
 *
 *
 */
public class Str {

    /**
     * Characters used in Germany
     */
    public static final char UESMALL = '\u00FC';
    public static final char UE = '\u00DC';
    public static final char OESMALL = '\u00F6';
    public static final char OE = '\u00D6';
    public static final char AESMALL = '\u00E4';
    public static final char AE = '\u00C4';
    public static final char SZ = '\u00DF';
    public static final char EUR = '\u0080';

    /**
     *
     * @param s
     * @return
     */
    public static String encodeUrlParameter(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            switch (c) {
                case '$':
                    sb.append("%24");
                    break;
                case '&':
                    sb.append("%26");
                    break;
                case '+':
                    sb.append("%2B");
                    break;
                case ',':
                    sb.append("%2C");
                    break;
                case '/':
                    sb.append("%2F");
                    break;
                case ':':
                    sb.append("%3A");
                    break;
                case ';':
                    sb.append("%3B");
                    break;
                case '=':
                    sb.append("%3D");
                    break;
                case '?':
                    sb.append("%3F");
                    break;
                case '@':
                    sb.append("%40");
                    break;
                case ' ':
                    sb.append("%20");
                    break;
                case '"':
                    sb.append("%22");
                    break;
                case '<':
                    sb.append("%3C");
                    break;
                case '>':
                    sb.append("%3E");
                    break;
                case '#':
                    sb.append("%23");
                    break;
                case '%':
                    sb.append("%25");
                    break;
                case '{':
                    sb.append("7B%");
                    break;
                case '}':
                    sb.append("7D%");
                    break;
                case '|':
                    sb.append("%7C");
                    break;
                case '\\':
                    sb.append("%5C");
                    break;
                case '^':
                    sb.append("%5E");
                    break;
                case '~':
                    sb.append("%7E");
                    break;
                case '[':
                    sb.append("%5B");
                    break;
                case ']':
                    sb.append("%5D");
                    break;
                case '`':
                    sb.append("%60");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param c
     * @return
     */
    public static String[] toStringArray(Collection<String> c) {
        return toStringArray(c.toArray());
    }

    /**
     *
     * @param oa
     * @return
     */
    public static String[] toStringArray(Object[] oa) {
        String[] sa = new String[oa.length];
        for (int i = 0; i < oa.length; i++) {
            sa[i] = oa[i] == null ? null : oa[i].toString();
        }
        return sa;
    }

    /**
     *
     * @param thiz
     * @param properties
     * @return
     */
    public static String toStringHelper(Object thiz, Object... properties) {
        return toStringHelper(getSimpleName(thiz.getClass()), properties);
    }

    /**
     *
     * @param name
     * @param properties
     * @return
     */
    public static String toStringHelper(String name, Object... properties) {
        return concat(name + "(", ")", ", ", properties);
    }

    /**
     *
     * @param prefix
     * @param suffix
     * @param delimiter
     * @param objects
     * @return
     */
    public static String concat(String prefix, String suffix, String delimiter, Object... objects) {
        StringBuilder sb = new StringBuilder();
        if (prefix != null) {
            sb.append(prefix);
        }
        for (int i = 0; i < objects.length; i++) {
            sb.append(objects[i]);
            if (delimiter != null && i < objects.length - 1) {
                sb.append(delimiter);
            }
        }
        if (suffix != null) {
            sb.append(suffix);
        }
        return sb.toString();
    }

    /**
     *
     * @param strings
     * @param delimiter
     * @return
     */
    public static String concat(Collection strings, String delimiter) {
        if (strings == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Object s : strings) {
            if (first) {
                first = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * Removes a suffix from a string, if it exists.
     *
     * @param s
     * @param suffixToRemove
     * @return
     */
    public static String removeSuffix(String s, String suffixToRemove) {
        if (s == null) {
            return null;
        }
        if (!s.endsWith(suffixToRemove)) {
            return s;
        }
        return s.substring(0, s.length() - suffixToRemove.length());
    }

    /**
     *
     * @param s
     * @param prefixToRemove
     * @return
     */
    public static String removePrefix(String s, String prefixToRemove) {
        if (s == null) {
            return null;
        }
        if (!s.startsWith(prefixToRemove)) {
            return s;
        }
        return s.substring(prefixToRemove.length());
    }

    /**
     *
     * @param s
     * @return
     */
    public static String getFirstParagraph(String s) {
        return getFirstParagraph(s, "\n\n");
    }

    /**
     *
     * @param s
     * @param paragraphEndIndicator
     * @return
     */
    public static String getFirstParagraph(String s, String paragraphEndIndicator) {
        if (s == null) {
            return null;
        }
        int idx = s.indexOf(paragraphEndIndicator);
        if (idx <= 0) {
            return s;
        }
        return s.substring(0, idx);
    }

    /**
     *
     * @param s
     * @param suffix
     * @return
     */
    public static String appendIfNotBlank(String s, String suffix) {
        if (isBlank(s)) {
            return s;
        }
        return s + suffix;
    }

    /**
     * Convert text to HTML
     * 
     * @see http://stackoverflow.com/questions/5134959/convert-plain-text-to-html-text-in-java
     * @param s
     * @return
     */
    public static String toHtml(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch (c) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                case '"':
                    builder.append("&quot;");
                    break;
                case '\n':
                    builder.append("<br>");
                    break;
                case AESMALL:
                    builder.append("&auml;");
                    break;
                case UESMALL:
                    builder.append("&uuml;");
                    break;
                case OESMALL:
                    builder.append("&ouml;");
                    break;
                case AE:
                    builder.append("&Auml;");
                    break;
                case UE:
                    builder.append("&Uuml;");
                    break;
                case OE:
                    builder.append("&Ouml;");
                    break;
                case SZ:
                    builder.append("&szlig;");
                    break;
                case EUR:
                    builder.append("&euro;");
                    break;

                // We need Tab support here, because we print StackTraces as HTML
                case '\t':
                    builder.append("&nbsp; &nbsp; &nbsp;");
                    break;
                default:
                    builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     *
     * @param s
     * @return
     */
    public static String getLeadingSpaces(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) != ' ') {
                break;
            }
            sb.append(' ');
        }
        return sb.toString();
    }

    /**
     *
     * @param s
     * @param from
     * @param to
     * @return
     */
    public static String cutFromTo(String s, String from, String to) {
        if (s == null) {
            return null;
        }
        s = cutFrom(s, from);
        s = cutTo(s, to);
        return s;
    }

    /**
     *
     * @param s
     * @param from
     * @return
     */
    public static String cutFrom(String s, String from) {
        if (s == null) {
            return null;
        }
        int fromIdx = s.indexOf(from);
        if (fromIdx < 0) {
            return null;
        }
        fromIdx += from.length();
        return s.substring(fromIdx);
    }

    /**
     *
     * @param s
     * @param to
     * @return
     */
    public static String cutTo(String s, String to) {
        if (s == null) {
            return null;
        }
        int toIdx = s.indexOf(to);
        if (toIdx < 0) {
            return null;
        }
        return s.substring(0, toIdx);
    }

    /**
     *
     * @param objects
     * @return
     */
    public static String toHtmlId(Object... objects) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Object object : objects) {
            if (first) {
                first = false;
            } else {
                sb.append("_");
            }
            if (object == null) {
                sb.append("null");
                continue;
            }
            sb.append(toHtmlId(object.toString()));
        }
        return sb.toString();
    }

    /**
     *
     * @param s
     * @return
     */
    public static String toHtmlId(String s) {
        int len = s.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (isLetter(ch) || (isDigit(ch) && i > 0)) {
                sb.append(ch);
                continue;
            }
            sb.append('_');
        }
        return sb.toString();
    }

    /**
     *
     * @param s
     * @param maxlength
     * @return
     */
    public static String cutLeft(String s, int maxlength) {
        if (s.length() > maxlength) {
            return s.substring(s.length() - maxlength);
        } else {
            return s;
        }
    }

    /**
     *
     * @param s
     * @param filler
     * @param minLength
     * @return
     */
    public static String fillUpRight(String s, String filler, int minLength) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < minLength) {
            sb.append(filler);
        }
        return sb.toString();
    }

    /**
     *
     * @param s
     * @return
     */
    public static boolean isEmail(String s) {
        if (isBlank(s)) {
            return false;
        }
        boolean at = false;
        boolean dot = false;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '@') {
                if (at) {
                    return false;
                }
                at = true;
                continue;
            }
            if (c == '.') {
                dot = true;
                continue;
            }
            if (isLetterOrDigit(c) || c == '-' || c == '_') {
                continue;
            }
            return false;
        }
        return !(!dot || !at);
    }

    /**
     *
     * @param s
     * @return
     */
    public static boolean isTrue(String s) {
        if (s == null) {
            return false;
        }
        s = s.toLowerCase();
        if (s.equals("true")) {
            return true;
        }
        if (s.equals("yes")) {
            return true;
        }
        if (s.equals("y")) {
            return true;
        }
        if (s.equals("1")) {
            return true;
        }
        if (s.equals("ja")) {
            return true;
        }
        return s.equals("j");
    }

    /**
     *
     * @param text
     * @param toFind
     * @param startIdx
     * @return
     */
    public static int indexOf(String text, String[] toFind, int startIdx) {
        int firstIdx = -1;
        for (String toFind1 : toFind) {
            int idx = text.indexOf(toFind1, startIdx);
            if (firstIdx < 0 || (idx >= 0 && idx < firstIdx)) {
                firstIdx = idx;
            }
        }
        return firstIdx;
    }

    /**
     *
     * @param o
     * @return
     */
    public static String format(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Object[]) {
            return formatObjectArray((Object[]) o);
        }
        if (o instanceof Map) {
            return formatMap((Map) o);
        }
        if (o instanceof Collection) {
            formatCollection((Collection) o);
        }
        if (o instanceof Enumeration) {
            return formatEnumeration((Enumeration) o);
        }
        if (o instanceof Exception) {
            return formatException((Exception) o);
        }
        return o.toString();
    }

    private static boolean isWrapperException(Exception ex) {
        if (getSimpleName(ex.getClass()).equals("RuntimeException")) {
            return true;
        }
        if (getSimpleName(ex.getClass()).equals("ExecutionException")) {
            return true;
        }
        return getSimpleName(ex.getClass()).equals("UmbrellaException");
    }

    /**
     *
     * @param e
     * @return
     */
    public static String formatEnumeration(Enumeration<String> e) {
        return formatCollection(toList(e));
    }

    /**
     *
     * @param c
     * @return
     */
    public static String formatCollection(Collection c) {
        return formatObjectArray(c.toArray());
    }

    /**
     *
     * @param map
     * @return
     */
    public static String formatMap(Map map) {
        StringBuilder sb = new StringBuilder();
        sb.append("map[");
        sb.append(map.size());
        sb.append("]={");
        boolean following = false;
        Set<Map.Entry> entries = map.entrySet();
        for (Map.Entry entry : entries) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (following) {
                sb.append(',');
            }
            following = true;
            sb.append('"');
            sb.append(format(key));
            sb.append("\"=\"");
            sb.append(format(value));
            sb.append('"');
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * A simple stack trace to String convertor.
     *
     * @param ex
     * @return
     */
    public static String formatException(Exception ex) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     *
     * @param trace
     * @return
     */
    public static String formatStackTrace(StackTraceElement[] trace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : trace) {
            sb.append("    at ").append(element).append("\n");
        }
        return sb.toString();
    }

    /**
     *
     * @param t
     * @return
     */
    public static String getStackTrace(Exception t) {
        StringBuilder sb = new StringBuilder();
        sb.append(t.toString()).append("\n");
        sb.append(formatStackTrace(t.getStackTrace()));

        Exception cause = (Exception) t.getCause();
        if (cause == null) {
            return sb.toString();
        }
        sb.append("Caused by: ").append(getStackTrace(cause));

        return sb.toString();
    }

    /**
     *
     * @param oa
     * @return
     */
    public static String formatObjectArray(Object[] oa) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < oa.length; i++) {
            if (oa[i] != null) {
                sb.append('<');
                sb.append(format(oa[i]));
                sb.append('>');
            }
            if (i != oa.length - 1) {
                sb.append(',');
            }
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     *
     * @param type
     * @return
     */
    public static String getSimpleName(Class type) {
        String name = type.getName();
        int idx = name.lastIndexOf('.');
        if (idx > 0) {
            name = name.substring(idx + 1);
        }
        if (null == name) {
            name = "getSimpleName: unknown class";
        }
        return name;
    }

    /**
     *
     * @param s
     * @return
     */
    public static boolean isBlank(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }
}
