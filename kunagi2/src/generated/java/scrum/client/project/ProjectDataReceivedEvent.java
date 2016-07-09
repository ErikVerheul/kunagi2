// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.project;

import static ilarkesto.core.logging.ClientLog.DEBUG;

public class ProjectDataReceivedEvent extends ilarkesto.core.event.AEvent {

    public  ProjectDataReceivedEvent() {
    }

    public void tryToGetHandled(Object handler) {
        if (handler instanceof ProjectDataReceivedHandler) {
            DEBUG("    " + handler.getClass().getName() + ".onProjectDataReceived(event)");
            ((ProjectDataReceivedHandler)handler).onProjectDataReceived(this);
        }
    }

}

