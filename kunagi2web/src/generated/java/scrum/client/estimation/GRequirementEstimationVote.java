// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.estimation;

import java.util.*;
import ilarkesto.core.logging.Log;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GRequirementEstimationVote
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public GRequirementEstimationVote() {
    }

    public GRequirementEstimationVote(Map data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "requirementEstimationVote";

    @Override
    public final String getEntityType() {
        return ENTITY_TYPE;
    }

    // --- requirement ---

    private String requirementId;

    public final scrum.client.project.Requirement getRequirement() {
        if (requirementId == null) return null;
        return getDao().getRequirement(this.requirementId);
    }

    public final boolean isRequirementSet() {
        return requirementId != null;
    }

    public final RequirementEstimationVote setRequirement(scrum.client.project.Requirement requirement) {
        String id = requirement == null ? null : requirement.getId();
        if (equals(this.requirementId, id)) return (RequirementEstimationVote) this;
        this.requirementId = id;
        propertyChanged("requirementId", this.requirementId);
        return (RequirementEstimationVote)this;
    }

    public final boolean isRequirement(scrum.client.project.Requirement requirement) {
        return equals(this.requirementId, requirement);
    }

    // --- user ---

    private String userId;

    public final scrum.client.admin.User getUser() {
        if (userId == null) return null;
        return getDao().getUser(this.userId);
    }

    public final boolean isUserSet() {
        return userId != null;
    }

    public final RequirementEstimationVote setUser(scrum.client.admin.User user) {
        String id = user == null ? null : user.getId();
        if (equals(this.userId, id)) return (RequirementEstimationVote) this;
        this.userId = id;
        propertyChanged("userId", this.userId);
        return (RequirementEstimationVote)this;
    }

    public final boolean isUser(scrum.client.admin.User user) {
        return equals(this.userId, user);
    }

    // --- estimatedWork ---

    private java.lang.Float estimatedWork ;

    public final java.lang.Float getEstimatedWork() {
        return this.estimatedWork ;
    }

    public final RequirementEstimationVote setEstimatedWork(java.lang.Float estimatedWork) {
        if (isEstimatedWork(estimatedWork)) return (RequirementEstimationVote)this;
        this.estimatedWork = estimatedWork ;
        propertyChanged("estimatedWork", this.estimatedWork);
        return (RequirementEstimationVote)this;
    }

    public final boolean isEstimatedWork(java.lang.Float estimatedWork) {
        return equals(this.estimatedWork, estimatedWork);
    }

    private transient EstimatedWorkModel estimatedWorkModel;

    public EstimatedWorkModel getEstimatedWorkModel() {
        if (estimatedWorkModel == null) estimatedWorkModel = createEstimatedWorkModel();
        return estimatedWorkModel;
    }

    protected EstimatedWorkModel createEstimatedWorkModel() { return new EstimatedWorkModel(); }

    protected class EstimatedWorkModel extends ilarkesto.gwt.client.editor.AFloatEditorModel {

        @Override
        public String getId() {
            return "RequirementEstimationVote_estimatedWork";
        }

        @Override
        public java.lang.Float getValue() {
            return getEstimatedWork();
        }

        @Override
        public void setValue(java.lang.Float value) {
            setEstimatedWork(value);
        }

        @Override
        protected void onChangeValue(java.lang.Float oldValue, java.lang.Float newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- update properties by map ---

    public void updateProperties(Map props) {
        requirementId = (String) props.get("requirementId");
        userId = (String) props.get("userId");
        estimatedWork  = (java.lang.Float) props.get("estimatedWork");
        updateLocalModificationTime();
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("requirementId", this.requirementId);
        properties.put("userId", this.userId);
        properties.put("estimatedWork", this.estimatedWork);
    }

}