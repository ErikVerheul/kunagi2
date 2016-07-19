package scrum.client.common;

/**
 * A class to provide access to global variables. Avoid unnecessary blocking by
 * applying the Double Checked Locking pattern as described by Mark Grand in his
 * book Patterns in Java Volume 2.
 *
 * @author erik
 */
public class GlobalsSingleton {

    private static GlobalsSingleton _instance = null;
    private boolean dragging = false;

    /**
     *
     * @return
     */
    @SuppressWarnings("DC_DOUBLECHECK")
    public static GlobalsSingleton getInstance() {
        if (_instance == null) {
            synchronized (GlobalsSingleton.class) {
                if (_instance == null) {
                    _instance = new GlobalsSingleton();
                }
            }
        }
        return _instance;
    }

    /**
     *
     * @param dragging
     */
    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    /**
     *
     * @return
     */
    public boolean isDragging() {
        return dragging == true;
    }
}
