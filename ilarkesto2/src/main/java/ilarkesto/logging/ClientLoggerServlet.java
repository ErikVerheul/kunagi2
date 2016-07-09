package ilarkesto.logging;

/**
 *
 * @author erik
 */
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.apache.log4j.Logger;
import com.google.gwt.logging.shared.RemoteLoggingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ClientLoggerServlet extends RemoteServiceServlet implements RemoteLoggingService {

    private static final Logger REMOTELOG = Logger.getLogger(ClientLoggerServlet.class);
    
    public static Logger getClientLogger(){
        return REMOTELOG;
    }

    @Override
    public String logOnServer(LogRecord record) {
        Level level = record.getLevel();
        String message = record.getMessage();

        if (Level.INFO.equals(level)) {
            REMOTELOG.info(message);
        } else if (Level.SEVERE.equals(level)) {
            REMOTELOG.error(message);
        } else if (Level.WARNING.equals(level)) {
            REMOTELOG.warn(message);
        } else if (Level.FINE.equals(level)) {
            REMOTELOG.debug(message);
        }

        return null;
    }
}
