// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.communication;

public class PingServiceCall extends generated.client.core.AServiceCall {

    public  PingServiceCall() {
    }

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

