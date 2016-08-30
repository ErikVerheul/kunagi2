package ilarkesto.core.logging;

/**
 *
 *
 */
import com.allen_sauer.gwt.log.client.Log;

/**
 *
 *
 */
public class ClientLog {

    /**
     *
     * @param s
     */
    public static void DEBUG(Object... s) {
        Log.debug(concat(s));
    }

    /**
     *
     * @param s
     */
    public static void ERROR(Object... s) {
        Log.error(concat(s));
    }

    /**
     *
     * @param s
     */
    public static void WARN(Object... s) {
        Log.warn(concat(s));
    }

    /**
     *
     * @param s
     */
    public static void INFO(Object... s) {
        Log.info(concat(s));
    }

    private static String concat(Object... values) {
        if (values.length == 0) {
            return "ClientLog|concat: No values supplied.";
        }
        StringBuilder sb = new StringBuilder();
        for (Object s : values) {
            sb.append(s.toString());
        }
        return sb.toString();
    }
}
