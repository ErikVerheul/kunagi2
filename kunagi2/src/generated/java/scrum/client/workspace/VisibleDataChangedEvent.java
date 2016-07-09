// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.workspace;

import static ilarkesto.core.logging.ClientLog.DEBUG;

public class VisibleDataChangedEvent extends ilarkesto.core.event.AEvent {

    public  VisibleDataChangedEvent() {
    }

    public void tryToGetHandled(Object handler) {
        if (handler instanceof VisibleDataChangedHandler) {
            DEBUG("    " + handler.getClass().getName() + ".onVisibleDataChanged(event)");
            ((VisibleDataChangedHandler)handler).onVisibleDataChanged(this);
        }
    }

}

