package ilarkesto.junit.logging;

import static ilarkesto.base.Sys.isDevelopmentMode;
import ilarkesto.logging.Log;
import junit.framework.TestCase;

public class LoggerTest extends TestCase {

    public void testLogging() {
        Log logger = new Log("TestLogger");

        logger.debug("test debug log");
        logger.info("test info log");
        logger.warn("test warn log");
        logger.error("test error log");
        logger.fatal("test fatal log");
    }
    
    public void testMode() {
        assertTrue(isDevelopmentMode());
    }

}
