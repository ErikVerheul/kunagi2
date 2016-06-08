package ilarkesto.core.json;

public class ParseException extends RuntimeException {

	public ParseException(String message, String json, int idx) {
		super(message + "\n" + idx + ":" + json);
	}

}
