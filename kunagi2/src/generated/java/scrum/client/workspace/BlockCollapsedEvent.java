// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.workspace;

import static ilarkesto.core.logging.ClientLog.DEBUG;

public class BlockCollapsedEvent extends ilarkesto.core.event.AEvent {

    private Object object;

    public  BlockCollapsedEvent(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void tryToGetHandled(Object handler) {
        if (handler instanceof BlockCollapsedHandler) {
            DEBUG("    " + handler.getClass().getName() + ".onBlockCollapsed(event)");
            ((BlockCollapsedHandler)handler).onBlockCollapsed(this);
        }
    }

}

