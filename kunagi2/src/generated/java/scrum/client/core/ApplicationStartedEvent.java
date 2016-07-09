// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.core;

import static ilarkesto.core.logging.ClientLog.DEBUG;

public class ApplicationStartedEvent extends ilarkesto.core.event.AEvent {

    public  ApplicationStartedEvent() {
    }

    public void tryToGetHandled(Object handler) {
        if (handler instanceof ApplicationStartedHandler) {
            DEBUG("    " + handler.getClass().getName() + ".onApplicationStarted(event)");
            ((ApplicationStartedHandler)handler).onApplicationStarted(this);
        }
    }

}

