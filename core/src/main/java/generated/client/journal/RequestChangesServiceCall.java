// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.journal;

public class RequestChangesServiceCall extends generated.client.core.AServiceCall {

    private String parentId;

    public  RequestChangesServiceCall(String parentId) {
        this.parentId = parentId;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().requestChanges(serviceCaller.getConversationNumber(), parentId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "RequestChanges";
    }

}

