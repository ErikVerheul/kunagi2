// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.admin;

public class UpdateSystemMessageServiceCall extends scrum.client.core.AServiceCall {

    private ilarkesto.gwt.client.SystemMessage systemMessage;

    public  UpdateSystemMessageServiceCall(ilarkesto.gwt.client.SystemMessage systemMessage) {
        this.systemMessage = systemMessage;
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().updateSystemMessage(serviceCaller.getConversationNumber(), systemMessage, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "UpdateSystemMessage";
    }

}

