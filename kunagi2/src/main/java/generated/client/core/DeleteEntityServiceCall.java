// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.core;

public class DeleteEntityServiceCall extends generated.client.core.AServiceCall {

    private String entityId;

    public  DeleteEntityServiceCall(String entityId) {
        this.entityId = entityId;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().deleteEntity(serviceCaller.getConversationNumber(), entityId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "DeleteEntity";
    }

}

