// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.admin;

public class SendTestEmailServiceCall extends scrum.client.core.AServiceCall {

    public  SendTestEmailServiceCall() {
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().sendTestEmail(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "SendTestEmail";
    }

}

