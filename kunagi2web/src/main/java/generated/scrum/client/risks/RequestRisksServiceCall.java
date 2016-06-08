// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.scrum.client.risks;

public class RequestRisksServiceCall extends generated.scrum.client.core.AServiceCall {

    public  RequestRisksServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().requestRisks(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "RequestRisks";
    }

}

