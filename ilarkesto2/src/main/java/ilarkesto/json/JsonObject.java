package ilarkesto.json;

import static ilarkesto.json.Json.convertValue;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonObject {

	public static void main(String[] args) {
		JsonObject json = new JsonObject();
		json.put("name", "Witek");
		json.put("gender", "male");

		json.putNewObject("subobject").put("a", "1");
		json.addToArray("array", "1");
		json.addToArray("array", "2");
		json.addToArray("array", new JsonObject());

		String s = json.toFormatedString();
		out.println(s);

		new JsonObject(s);
	}

	private Map<String, Object> elements = new LinkedHashMap<>();
	private int idx = -1;

	public JsonObject() {}

	private JsonObject(String json, int offset) {
		parse(json, offset);
	}

	public JsonObject(String json) {
		this(json, 0);
	}

	public JsonObject(Map<?, ?> map) {
		for (Map.Entry entry : map.entrySet()) {
			String name = entry.getKey().toString();
			Object value = convertValue(entry.getValue());
			put(name, value);
		}
	}

	// --- inspecting ---

	public Object get(String name) {
		return elements.get(name);
	}

	public boolean contains(String name) {
		return elements.containsKey(name);
	}

	public boolean containsString(String name, String expected) {
		String value = getString(name);
		return (value == null ? expected == null : value.equals(expected)) || (value != null && value.equals(expected));
	}

	public String getString(String name) {
		return (String) get(name);
	}

	public String getString(String name, String defaultValue) {
		String value = getString(name);
		return value == null ? defaultValue : value;
	}

	public JsonObject getObject(String name) {
		return (JsonObject) get(name);
	}

	public List getArray(String name) {
		return (List) get(name);
	}

	public Number getNumber(String name) {
		return (Number) get(name);
	}

	public Integer getInteger(String name) {
		Number value = getNumber(name);
		if (value == null) {
                        return null;
                }
		if (value instanceof Integer) {
                        return (Integer) value;
                }
		return value.intValue();
	}

	public Long getLong(String name) {
		Number value = getNumber(name);
		if (value == null) {
                        return null;
                }
		if (value instanceof Long) {
                        return (Long) value;
                }
		return value.longValue();
	}

	public Double getDouble(String name) {
		Number value = getNumber(name);
		if (value == null) {
                        return null;
                }
		if (value instanceof Double) {
                        return (Double) value;
                }
		return value.doubleValue();
	}

	public Float getFloat(String name) {
		Number value = getNumber(name);
		if (value == null) {
                        return null;
                }
		if (value instanceof Float) {
                        return (Float) value;
                }
		return value.floatValue();
	}

	public Byte getByte(String name) {
		Number value = getNumber(name);
		if (value == null) {
                        return null;
                }
		if (value instanceof Byte) {
                        return (Byte) value;
                }
		return value.byteValue();
	}

	public Boolean getBoolean(String name) {
		return (Boolean) get(name);
	}

	public boolean isTrue(String name) {
		Boolean value = getBoolean(name);
		if (value == null) {
                        return false;
                }
		return value;
	}

	public List<String> getArrayOfStrings(String name) {
		return (List<String>) get(name);
	}

	public List<JsonObject> getArrayOfObjects(String name) {
		return (List<JsonObject>) get(name);
	}

	// --- manipulating ---

	public <V> V put(String name, V value) {
		if (name == null || name.length() == 0) {
                        throw new RuntimeException("name required");
                }
		elements.put(name, convertValue(value));
		return value;
	}

	public List addToArray(String name, Object value) {
		List array = getArray(name);
		if (array == null) {
			array = new ArrayList();
			put(name, array);
		}
		array.add(value);
		return array;
	}

	public Object remove(String name) {
		if (name == null || name.length() == 0) {
                        throw new RuntimeException("name required");
                }
		return elements.remove(name);
	}

	public JsonObject putNewObject(String name) {
		return put(name, new JsonObject());
	}

	// --- formating ---

	String toString(int indentation) {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		if (indentation >= 0) {
                        indentation++;
                }
		boolean first = true;
		for (Map.Entry<String, Object> element : elements.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			if (indentation >= 0) {
                                sb.append('\n');
                        }
			Json.indent(sb, indentation);
			sb.append('"').append(Json.escapeString(element.getKey())).append("\":");
			if (indentation >= 0) {
                                sb.append(' ');
                        }
			sb.append(Json.valueToString(element.getValue(), indentation));
		}
		if (indentation >= 0) {
			indentation--;
			sb.append('\n');
			Json.indent(sb, indentation);
		}
		sb.append('}');
		return sb.toString();
	}

	public String toFormatedString() {
		return toString(0);
	}

	@Override
	public String toString() {
		return toString(-1);
	}

	// --- parsing ---

	private int parse(String json, int offset) {
		idx = offset;
		parseWhitespace(json, "'{'");
		if (json.charAt(idx) != '{') {
                        throw new ParseException("Expecting '{'", json, idx);
                }
		idx++;
		parseWhitespace(json, "elements or '}'");
		boolean first = true;
		while (json.charAt(idx) != '}') {
			if (first) {
				first = false;
			} else {
				if (json.charAt(idx) == ',') {
					idx++;
				} else {
					throw new ParseException("Expecting ','", json, idx);
				}
			}
			parseElement(json);
			parseWhitespace(json, "',' or '}'");
		}
		idx++;
		return idx;
	}

	private void parseWhitespace(String json, String expectation) {
		idx = Json.getFirstNonWhitespaceIndex(json, idx);
		if (idx < 0) {
                        throw new ParseException("Expecting " + expectation, json, idx);
                }
	}

	private void parseElement(String json) {
		parseWhitespace(json, "\"");
		if (json.charAt(idx) != '"') {
                        throw new ParseException("Expecting '\"'", json, idx);
                }
		idx++;
		int nameEndIdx = Json.getFirstQuoting(json, idx);
		if (nameEndIdx < 0) {
                        throw new ParseException("Unclosed element name", json, idx);
                }
		String name = json.substring(idx, nameEndIdx);
		idx = nameEndIdx + 1;
		parseWhitespace(json, "':'");
		if (json.charAt(idx) != ':') {
                        throw new ParseException("Expecting ':'", json, idx);
                }
		idx++;
		parseWhitespace(json, "element value");
		Object value = parseValue(json);
		put(name, value);
	}

	private Object parseValue(String json) {
		if (json.startsWith("null", idx)) {
			idx += 4;
			return null;
		} else if (json.startsWith("true", idx)) {
			idx += 4;
			return true;
		} else if (json.startsWith("false", idx)) {
			idx += 5;
			return false;
		} else if (json.charAt(idx) == '"') {
			idx++;
			int valueEndIdx = Json.getFirstQuoting(json, idx);
			if (valueEndIdx < 0) {
                                throw new ParseException("Unclosed element string value", json, idx);
                        }
			String value = json.substring(idx, valueEndIdx);
			idx = valueEndIdx + 1;
			return Json.parseString(value);
		} else if (json.charAt(idx) == '{') {
			JsonObject value = new JsonObject(json, idx);
			idx = value.idx;
			return value;
		} else if (json.charAt(idx) == '[') {
			List list = new ArrayList();
			idx++;
			while (true) {
				parseWhitespace(json, "array");
				if (json.charAt(idx) == ']') {
                                        break;
                                }
				Object value = parseValue(json);
				list.add(value);
				parseWhitespace(json, "array");
				if (json.charAt(idx) == ']') {
                                        break;
                                }
				if (json.charAt(idx) != ',') {
                                        throw new ParseException("Expecting array separator ','", json, idx);
                                }
				idx++;
			}
			idx++;
			return list;
		} else {
			int len = json.length();
			int endIdx = idx;
			while (endIdx < len) {
				endIdx++;
				char endCh = json.charAt(endIdx);
				if (endCh == ',' || endCh == '}' || endCh == ']' || Json.isWhitespace(endCh)) {
					break;
				}
			}
			String sNumber = json.substring(idx, endIdx);
			Number number;
			try {
				number = Json.parseNumber(sNumber);
			} catch (NumberFormatException ex) {
				throw new ParseException("Expecting number in <" + sNumber + ">", json, idx);
			}
			idx = endIdx;
			return number;
		}
	}
}
