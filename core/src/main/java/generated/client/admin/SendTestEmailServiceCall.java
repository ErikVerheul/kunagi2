// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.admin;

public class SendTestEmailServiceCall extends generated.client.core.AServiceCall {

    public  SendTestEmailServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().sendTestEmail(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "SendTestEmail";
    }

}

