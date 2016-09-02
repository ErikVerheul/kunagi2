// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.communication;

import ilarkesto.gwt.client.DataTransferObject;

public class ServerDataReceivedEvent extends ilarkesto.core.event.AEvent implements ilarkesto.core.event.Quiet {

    private DataTransferObject data;

    public  ServerDataReceivedEvent(DataTransferObject data) {
        this.data = data;
    }

    public DataTransferObject getData() {
        return data;
    }

    public void tryToGetHandled(Object handler) {
        if (handler instanceof ServerDataReceivedHandler) {
            ((ServerDataReceivedHandler)handler).onServerDataReceived(this);
        }
    }

}

