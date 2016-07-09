
package ilarkesto.core.logging;

/**
 *
 * @author erik
 */
import static ilarkesto.logging.ClientLoggerServlet.getClientLogger;

public class ClientLog {

    public static void DEBUG(Object... s) {
        getClientLogger().debug(s);
    }
    
    public static void ERROR(Object... s) {
        getClientLogger().debug(s);
    }

    public static void WARN(Object... s) {
        getClientLogger().debug(s);
    }

    public static void INFO(Object... s) {
        getClientLogger().debug(s);
    }
}
