// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.sprint;

public class RequestHistoryServiceCall extends generated.client.core.AServiceCall {

    public  RequestHistoryServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().requestHistory(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "RequestHistory";
    }

}

