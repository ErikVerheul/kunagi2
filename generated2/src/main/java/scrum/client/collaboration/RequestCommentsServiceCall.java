// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.collaboration;

public class RequestCommentsServiceCall extends scrum.client.core.AServiceCall {

    private String parentId;

    public  RequestCommentsServiceCall(String parentId) {
        this.parentId = parentId;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().requestComments(serviceCaller.getConversationNumber(), parentId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "RequestComments";
    }

}

