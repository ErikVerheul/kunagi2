// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.core;

import java.util.HashMap;

public class CreateEntityServiceCall extends scrum.client.core.AServiceCall {

    private String type;

    private HashMap<String, Object> properties;

    public  CreateEntityServiceCall(String type, HashMap<String, Object> properties) {
        this.type = type;
        this.properties = properties;
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().createEntity(serviceCaller.getConversationNumber(), type, properties, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "CreateEntity";
    }

}

