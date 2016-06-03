// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.admin;

public class UpdateSystemMessageServiceCall extends generated.client.core.AServiceCall {

    private scrum.client.admin.SystemMessage systemMessage;

    public  UpdateSystemMessageServiceCall(scrum.client.admin.SystemMessage systemMessage) {
        this.systemMessage = systemMessage;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().updateSystemMessage(serviceCaller.getConversationNumber(), systemMessage, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "UpdateSystemMessage";
    }

}

