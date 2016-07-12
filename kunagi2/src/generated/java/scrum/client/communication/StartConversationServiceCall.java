// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.communication;

public class StartConversationServiceCall extends scrum.client.core.AServiceCall {

    public  StartConversationServiceCall() {
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().startConversation(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "StartConversation";
    }

}

