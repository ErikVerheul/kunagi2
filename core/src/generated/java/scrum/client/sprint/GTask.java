// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.sprint;

import java.util.*;
import ilarkesto.core.logging.Log;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GTask
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public abstract boolean isEditable();

    public GTask() {
    }

    public GTask(Map data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "task";

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

    public final Task setRequirement(scrum.client.project.Requirement requirement) {
        String id = requirement == null ? null : requirement.getId();
        if (equals(this.requirementId, id)) return (Task) this;
        this.requirementId = id;
        propertyChanged("requirementId", this.requirementId);
        return (Task)this;
    }

    public final boolean isRequirement(scrum.client.project.Requirement requirement) {
        return equals(this.requirementId, requirement);
    }

    // --- number ---

    private int number ;

    public final int getNumber() {
        return this.number ;
    }

    public final Task setNumber(int number) {
        if (isNumber(number)) return (Task)this;
        this.number = number ;
        propertyChanged("number", this.number);
        return (Task)this;
    }

    public final boolean isNumber(int number) {
        return equals(this.number, number);
    }

    private transient NumberModel numberModel;

    public NumberModel getNumberModel() {
        if (numberModel == null) numberModel = createNumberModel();
        return numberModel;
    }

    protected NumberModel createNumberModel() { return new NumberModel(); }

    protected class NumberModel extends ilarkesto.gwt.client.editor.AIntegerEditorModel {

        @Override
        public String getId() {
            return "Task_number";
        }

        @Override
        public java.lang.Integer getValue() {
            return getNumber();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setNumber(value);
        }

            @Override
            public void increment() {
                setNumber(getNumber() + 1);
            }

            @Override
            public void decrement() {
                setNumber(getNumber() - 1);
            }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- label ---

    private java.lang.String label ;

    public final java.lang.String getLabel() {
        return this.label ;
    }

    public final Task setLabel(java.lang.String label) {
        if (isLabel(label)) return (Task)this;
        if (ilarkesto.core.base.Str.isBlank(label)) throw new RuntimeException("Field is mandatory.");
        this.label = label ;
        propertyChanged("label", this.label);
        return (Task)this;
    }

    public final boolean isLabel(java.lang.String label) {
        return equals(this.label, label);
    }

    private transient LabelModel labelModel;

    public LabelModel getLabelModel() {
        if (labelModel == null) labelModel = createLabelModel();
        return labelModel;
    }

    protected LabelModel createLabelModel() { return new LabelModel(); }

    protected class LabelModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Task_label";
        }

        @Override
        public java.lang.String getValue() {
            return getLabel();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLabel(value);
        }

        @Override
        public boolean isMandatory() { return true; }

        @Override
        public boolean isEditable() { return GTask.this.isEditable(); }
        @Override
        public String getTooltip() { return "The label should be short (as it appears where the Task is referenced), yet give a hint strong enough to make the content of it come to mind."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- description ---

    private java.lang.String description ;

    public final java.lang.String getDescription() {
        return this.description ;
    }

    public final Task setDescription(java.lang.String description) {
        if (isDescription(description)) return (Task)this;
        this.description = description ;
        propertyChanged("description", this.description);
        return (Task)this;
    }

    public final boolean isDescription(java.lang.String description) {
        return equals(this.description, description);
    }

    private transient DescriptionModel descriptionModel;

    public DescriptionModel getDescriptionModel() {
        if (descriptionModel == null) descriptionModel = createDescriptionModel();
        return descriptionModel;
    }

    protected DescriptionModel createDescriptionModel() { return new DescriptionModel(); }

    protected class DescriptionModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Task_description";
        }

        @Override
        public java.lang.String getValue() {
            return getDescription();
        }

        @Override
        public void setValue(java.lang.String value) {
            setDescription(value);
        }

        @Override
        public boolean isEditable() { return GTask.this.isEditable(); }

        @Override
        public boolean isRichtext() { return true; }
        @Override
        public String getTooltip() { return "The description of a Task may be used to give information that is important, but cannot be inferred from the Label. As Tasks are small units of work, the Label might be sufficient."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- remainingWork ---

    private int remainingWork ;

    public final int getRemainingWork() {
        return this.remainingWork ;
    }

    public final Task setRemainingWork(int remainingWork) {
        if (isRemainingWork(remainingWork)) return (Task)this;
        this.remainingWork = remainingWork ;
        propertyChanged("remainingWork", this.remainingWork);
        return (Task)this;
    }

    public final boolean isRemainingWork(int remainingWork) {
        return equals(this.remainingWork, remainingWork);
    }

    private transient RemainingWorkModel remainingWorkModel;

    public RemainingWorkModel getRemainingWorkModel() {
        if (remainingWorkModel == null) remainingWorkModel = createRemainingWorkModel();
        return remainingWorkModel;
    }

    protected RemainingWorkModel createRemainingWorkModel() { return new RemainingWorkModel(); }

    protected class RemainingWorkModel extends ilarkesto.gwt.client.editor.AIntegerEditorModel {

        @Override
        public String getId() {
            return "Task_remainingWork";
        }

        @Override
        public java.lang.Integer getValue() {
            return getRemainingWork();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setRemainingWork(value);
        }

            @Override
            public void increment() {
                setRemainingWork(getRemainingWork() + 1);
            }

            @Override
            public void decrement() {
                setRemainingWork(getRemainingWork() - 1);
            }

        @Override
        public boolean isEditable() { return GTask.this.isEditable(); }
        @Override
        public String getTooltip() { return "The remaining time needed to get this Task done. If the remaining time is high, it might be an indication (but is not necessarily the case) that splitting the Task is a good idea."; }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- burnedWork ---

    private int burnedWork ;

    public final int getBurnedWork() {
        return this.burnedWork ;
    }

    public final Task setBurnedWork(int burnedWork) {
        if (isBurnedWork(burnedWork)) return (Task)this;
        this.burnedWork = burnedWork ;
        propertyChanged("burnedWork", this.burnedWork);
        return (Task)this;
    }

    public final boolean isBurnedWork(int burnedWork) {
        return equals(this.burnedWork, burnedWork);
    }

    private transient BurnedWorkModel burnedWorkModel;

    public BurnedWorkModel getBurnedWorkModel() {
        if (burnedWorkModel == null) burnedWorkModel = createBurnedWorkModel();
        return burnedWorkModel;
    }

    protected BurnedWorkModel createBurnedWorkModel() { return new BurnedWorkModel(); }

    protected class BurnedWorkModel extends ilarkesto.gwt.client.editor.AIntegerEditorModel {

        @Override
        public String getId() {
            return "Task_burnedWork";
        }

        @Override
        public java.lang.Integer getValue() {
            return getBurnedWork();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setBurnedWork(value);
        }

            @Override
            public void increment() {
                setBurnedWork(getBurnedWork() + 1);
            }

            @Override
            public void decrement() {
                setBurnedWork(getBurnedWork() - 1);
            }

        @Override
        public boolean isEditable() { return GTask.this.isEditable(); }
        @Override
        public String getTooltip() { return "Time already invested working on this Task."; }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- owner ---

    private String ownerId;

    public final scrum.client.admin.User getOwner() {
        if (ownerId == null) return null;
        return getDao().getUser(this.ownerId);
    }

    public final boolean isOwnerSet() {
        return ownerId != null;
    }

    public final Task setOwner(scrum.client.admin.User owner) {
        String id = owner == null ? null : owner.getId();
        if (equals(this.ownerId, id)) return (Task) this;
        this.ownerId = id;
        propertyChanged("ownerId", this.ownerId);
        return (Task)this;
    }

    public final boolean isOwner(scrum.client.admin.User owner) {
        return equals(this.ownerId, owner);
    }

    // --- impediment ---

    private String impedimentId;

    public final scrum.client.impediments.Impediment getImpediment() {
        if (impedimentId == null) return null;
        return getDao().getImpediment(this.impedimentId);
    }

    public final boolean isImpedimentSet() {
        return impedimentId != null;
    }

    public final Task setImpediment(scrum.client.impediments.Impediment impediment) {
        String id = impediment == null ? null : impediment.getId();
        if (equals(this.impedimentId, id)) return (Task) this;
        this.impedimentId = id;
        propertyChanged("impedimentId", this.impedimentId);
        return (Task)this;
    }

    public final boolean isImpediment(scrum.client.impediments.Impediment impediment) {
        return equals(this.impedimentId, impediment);
    }

    // --- closedInPastSprint ---

    private String closedInPastSprintId;

    public final scrum.client.sprint.Sprint getClosedInPastSprint() {
        if (closedInPastSprintId == null) return null;
        return getDao().getSprint(this.closedInPastSprintId);
    }

    public final boolean isClosedInPastSprintSet() {
        return closedInPastSprintId != null;
    }

    public final Task setClosedInPastSprint(scrum.client.sprint.Sprint closedInPastSprint) {
        String id = closedInPastSprint == null ? null : closedInPastSprint.getId();
        if (equals(this.closedInPastSprintId, id)) return (Task) this;
        this.closedInPastSprintId = id;
        propertyChanged("closedInPastSprintId", this.closedInPastSprintId);
        return (Task)this;
    }

    public final boolean isClosedInPastSprint(scrum.client.sprint.Sprint closedInPastSprint) {
        return equals(this.closedInPastSprintId, closedInPastSprint);
    }

    // --- update properties by map ---

    public void updateProperties(Map props) {
        requirementId = (String) props.get("requirementId");
        number  = (Integer) props.get("number");
        label  = (java.lang.String) props.get("label");
        description  = (java.lang.String) props.get("description");
        remainingWork  = (Integer) props.get("remainingWork");
        burnedWork  = (Integer) props.get("burnedWork");
        ownerId = (String) props.get("ownerId");
        impedimentId = (String) props.get("impedimentId");
        closedInPastSprintId = (String) props.get("closedInPastSprintId");
        updateLocalModificationTime();
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("requirementId", this.requirementId);
        properties.put("number", this.number);
        properties.put("label", this.label);
        properties.put("description", this.description);
        properties.put("remainingWork", this.remainingWork);
        properties.put("burnedWork", this.burnedWork);
        properties.put("ownerId", this.ownerId);
        properties.put("impedimentId", this.impedimentId);
        properties.put("closedInPastSprintId", this.closedInPastSprintId);
    }

    public final java.util.List<scrum.client.sprint.SprintReport> getSprintReports() {
        return getDao().getSprintReportsByClosedTask((Task)this);
    }

    @Override
    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getDescription(), key)) return true;
        return false;
    }

}