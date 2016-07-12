// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.core;

public class RequestEntityServiceCall extends scrum.client.core.AServiceCall {

    private String entityId;

    public  RequestEntityServiceCall(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().requestEntity(serviceCaller.getConversationNumber(), entityId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "RequestEntity";
    }

}

