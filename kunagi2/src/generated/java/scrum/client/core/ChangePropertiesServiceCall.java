// // ----------> GENERATED FILE - DON'T TOUCH! <----------
package scrum.client.core;

import ilarkesto.core.base.KunagiProperties;
import static ilarkesto.core.logging.ClientLog.DEBUG;
import java.io.Serializable;

public class ChangePropertiesServiceCall extends scrum.client.core.AServiceCall {

    private final String entityId;

    private KunagiProperties props = null;

    public ChangePropertiesServiceCall(String entityId, KunagiProperties properties) {
        this.entityId = entityId;
        this.props = properties;      
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        DEBUG("ConversationNumber = " + serviceCaller.getConversationNumber());
        DEBUG("entityId = " + entityId);
        DEBUG("Is properties serializable? = " + (props instanceof Serializable));
        serviceCaller.getService().changeProperties(serviceCaller.getConversationNumber(), 
                entityId, 
                props, 
                new DefaultCallback(this, returnHandler));
    }

    @Override
    public boolean isDispensable() {
        return ilarkesto.core.scope.Scope.get().getComponent(scrum.client.Dao.class).getEntity(entityId) instanceof scrum.client.admin.ProjectUserConfig && (props.containsKey("selectedEntitysIds") || props.containsKey("richtextAutosaveText") || props.containsKey("richtextAutosaveField") || props.containsKey("online"));
    }

    @Override
    public String toString() {
        return "ChangeProperties";
    }

}
