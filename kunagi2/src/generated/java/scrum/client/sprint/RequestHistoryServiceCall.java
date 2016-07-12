// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.sprint;

public class RequestHistoryServiceCall extends scrum.client.core.AServiceCall {

    public  RequestHistoryServiceCall() {
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().requestHistory(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "RequestHistory";
    }

}

