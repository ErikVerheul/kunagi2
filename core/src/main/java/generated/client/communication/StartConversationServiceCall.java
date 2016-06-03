// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.communication;

public class StartConversationServiceCall extends generated.client.core.AServiceCall {

    public  StartConversationServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().startConversation(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "StartConversation";
    }

}

