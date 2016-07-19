package ilarkesto.json;

/**
 *
 * @author erik
 */
public class ParseException extends RuntimeException {

    /**
     *
     * @param message
     * @param json
     * @param idx
     */
    public ParseException(String message, String json, int idx) {
		super(message + "\n" + idx + ":" + json);
	}

}
