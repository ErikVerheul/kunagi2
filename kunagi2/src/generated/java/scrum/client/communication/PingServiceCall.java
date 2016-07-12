// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.communication;

public class PingServiceCall extends scrum.client.core.AServiceCall {

    public  PingServiceCall() {
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().ping(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public boolean isDispensable() {
        return true;
    }

    @Override
    public String toString() {
        return "Ping";
    }

}

