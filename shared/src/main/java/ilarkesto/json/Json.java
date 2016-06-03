package ilarkesto.json;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Json {

	public static String valueToString(Object value, int indentation) {
		if (value == null) {
                        return "null";
                }
		if (value instanceof String) {
                        return '"' + escapeString((String) value) + '"';
                }
		if (value instanceof Iterable) {
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			if (indentation >= 0) {
                                indentation++;
                        }
			Iterable list = (Iterable) value;
			boolean first = true;
			for (Object element : list) {
				if (first) {
					first = false;
				} else {
					sb.append(',');
				}
				sb.append(valueToString(element, indentation));
			}
			sb.append(']');
			if (indentation >= 0) {
                                indentation--;
                        }
			return sb.toString();
		}
		if (value instanceof JsonObject) {
                        return ((JsonObject) value).toString(indentation);
                }
		if (value instanceof JsonWrapper) {
                        return ((JsonWrapper) value).getJson().toString(indentation);
                }
		return value.toString();
	}

	static void indent(StringBuilder sb, int indentation) {
		for (int i = 0; i < indentation; i++) {
			sb.append('\t');
		}
	}

	public static String escapeString(String s) {
		if (s == null) {
                        return "";
                }
		s = s.replace("\"", "\\\"");
		s = s.replace("\t", "\\\t");
		s = s.replace("\r", "\\\r");
		s = s.replace("\n", "\\\n");
		s = s.replace("/", "\\/");
		s = s.replace("\\", "\\\\");
		return s;
	}

	public static String parseString(String s) {
		int idx = s.indexOf("\\u");
		while (idx >= 0) {
			String code = s.substring(idx + 2, idx + 6);
			char ch = (char) parseInt(code, 16);
			s = s.replace("\\u" + code, valueOf(ch));
			idx = s.indexOf("\\u", idx);
		}
		s = s.replace("\\\\", "\\");
		s = s.replace("\\\"", "\"");
		s = s.replace("\\t", "\t");
		s = s.replace("\\r", "\r");
		s = s.replace("\\n", "\n");
		s = s.replace("\\/", "/");
		return s;
	}

	public static Number parseNumber(String s) throws NumberFormatException {
		if (s.contains(".")) {
                        return parseDouble(s);
                }
		return parseLong(s);
	}

	public static boolean isWhitespace(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}

	static Object convertValue(Object value) {
		if (value == null) {
                        return null;
                }
		if (value instanceof JsonWrapper) {
                        return ((JsonWrapper) value).getJson();
                }
		if (value instanceof JsonObject) {
                        return value;
                }
		if (value instanceof String) {
                        return value;
                }
		if (value instanceof List) {
                        return value;
                }
		if (value instanceof Boolean) {
                        return value;
                }
		if (value instanceof Number) {
                        return value;
                }
		if (value instanceof Collection) {
                        return new ArrayList((Collection) value);
                }
		if (value instanceof Iterable) {
			List ret = new ArrayList();
			for (Object element : (Iterable) value) {
				ret.add(element);
			}
			return ret;
		}
		if (value instanceof Map) {
                        return new JsonObject((Map) value);
                }
		return value.toString();
	}

	static int getFirstNonWhitespaceIndex(String s, int offset) {
		int len = s.length();
		for (int i = offset; i < len; i++) {
			if (!isWhitespace(s.charAt(i))) {
                                return i;
                        }
		}
		return -1;
	}

	static int getFirstQuoting(String s, int offset) {
		int len = s.length();
		for (int i = offset; i < len; i++) {
			char ch = s.charAt(i);
			if (ch == '\\') {
				i++;
				continue;
			}
			if (ch == '"') {
                                return i;
                        }
		}
		return -1;
	}

	static interface JsonWrapper {

		JsonObject getJson();
	}

}
