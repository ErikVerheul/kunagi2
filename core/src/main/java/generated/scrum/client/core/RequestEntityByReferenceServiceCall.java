// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.scrum.client.core;

public class RequestEntityByReferenceServiceCall extends generated.scrum.client.core.AServiceCall {

    private String reference;

    public  RequestEntityByReferenceServiceCall(String reference) {
        this.reference = reference;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().requestEntityByReference(serviceCaller.getConversationNumber(), reference, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "RequestEntityByReference";
    }

}
