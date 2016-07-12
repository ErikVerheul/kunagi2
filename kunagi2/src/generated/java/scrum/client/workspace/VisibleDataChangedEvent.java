// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.workspace;

public class VisibleDataChangedEvent extends ilarkesto.core.event.AEvent {

    public  VisibleDataChangedEvent() {
    }

    public void tryToGetHandled(Object handler) {
        if (handler instanceof VisibleDataChangedHandler) {
            ((VisibleDataChangedHandler)handler).onVisibleDataChanged(this);
        }
    }

}

