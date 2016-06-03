// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.core;

public class ChangePropertiesServiceCall extends scrum.client.core.AServiceCall {

    private String entityId;

    private java.util.Map properties;

    public  ChangePropertiesServiceCall(String entityId, java.util.Map properties) {
        this.entityId = entityId;
        this.properties = properties;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().changeProperties(serviceCaller.getConversationNumber(), entityId, properties, new DefaultCallback(this, returnHandler));
    }

    @Override
    public boolean isDispensable() {
        return ilarkesto.core.scope.Scope.get().getComponent(scrum.client.Dao.class).getEntity(entityId) instanceof scrum.client.admin.ProjectUserConfig && (properties.containsKey("selectedEntitysIds") || properties.containsKey("richtextAutosaveText") || properties.containsKey("richtextAutosaveField") || properties.containsKey("online"));
    }

    @Override
    public String toString() {
        return "ChangeProperties";
    }

}

