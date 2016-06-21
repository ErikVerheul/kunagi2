// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.sprint;

import java.util.*;
import ilarkesto.core.logging.Log;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GSprintReport
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public GSprintReport() {
    }

    public GSprintReport(Map data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "sprintReport";

    @Override
    public final String getEntityType() {
        return ENTITY_TYPE;
    }

    // --- sprint ---

    private String sprintId;

    public final scrum.client.sprint.Sprint getSprint() {
        if (sprintId == null) return null;
        return getDao().getSprint(this.sprintId);
    }

    public final boolean isSprintSet() {
        return sprintId != null;
    }

    public final SprintReport setSprint(scrum.client.sprint.Sprint sprint) {
        String id = sprint == null ? null : sprint.getId();
        if (equals(this.sprintId, id)) return (SprintReport) this;
        this.sprintId = id;
        propertyChanged("sprintId", this.sprintId);
        return (SprintReport)this;
    }

    public final boolean isSprint(scrum.client.sprint.Sprint sprint) {
        return equals(this.sprintId, sprint);
    }

    // --- completedRequirements ---

    private Set<String> completedRequirementsIds = new HashSet<String>();

    public final java.util.Set<scrum.client.project.Requirement> getCompletedRequirements() {
        if ( completedRequirementsIds.isEmpty()) return Collections.emptySet();
        return getDao().getRequirements(this.completedRequirementsIds);
    }

    public final void setCompletedRequirements(Collection<scrum.client.project.Requirement> values) {
        completedRequirementsIds = ilarkesto.gwt.client.Gwt.getIdsAsSet(values);
        propertyChanged("completedRequirementsIds", this.completedRequirementsIds);
    }

    public final void addCompletedRequirement(scrum.client.project.Requirement completedRequirement) {
        String id = completedRequirement.getId();
        if (completedRequirementsIds.contains(id)) return;
        completedRequirementsIds.add(id);
        propertyChanged("completedRequirementsIds", this.completedRequirementsIds);
    }

    public final void removeCompletedRequirement(scrum.client.project.Requirement completedRequirement) {
        String id = completedRequirement.getId();
        if (!completedRequirementsIds.contains(id)) return;
        completedRequirementsIds.remove(id);
        propertyChanged("completedRequirementsIds", this.completedRequirementsIds);
    }

    public final boolean containsCompletedRequirement(scrum.client.project.Requirement completedRequirement) {
        return completedRequirementsIds.contains(completedRequirement.getId());
    }


    // --- rejectedRequirements ---

    private Set<String> rejectedRequirementsIds = new HashSet<String>();

    public final java.util.Set<scrum.client.project.Requirement> getRejectedRequirements() {
        if ( rejectedRequirementsIds.isEmpty()) return Collections.emptySet();
        return getDao().getRequirements(this.rejectedRequirementsIds);
    }

    public final void setRejectedRequirements(Collection<scrum.client.project.Requirement> values) {
        rejectedRequirementsIds = ilarkesto.gwt.client.Gwt.getIdsAsSet(values);
        propertyChanged("rejectedRequirementsIds", this.rejectedRequirementsIds);
    }

    public final void addRejectedRequirement(scrum.client.project.Requirement rejectedRequirement) {
        String id = rejectedRequirement.getId();
        if (rejectedRequirementsIds.contains(id)) return;
        rejectedRequirementsIds.add(id);
        propertyChanged("rejectedRequirementsIds", this.rejectedRequirementsIds);
    }

    public final void removeRejectedRequirement(scrum.client.project.Requirement rejectedRequirement) {
        String id = rejectedRequirement.getId();
        if (!rejectedRequirementsIds.contains(id)) return;
        rejectedRequirementsIds.remove(id);
        propertyChanged("rejectedRequirementsIds", this.rejectedRequirementsIds);
    }

    public final boolean containsRejectedRequirement(scrum.client.project.Requirement rejectedRequirement) {
        return rejectedRequirementsIds.contains(rejectedRequirement.getId());
    }


    // --- requirementsOrderIds ---

    private java.util.List<java.lang.String> requirementsOrderIds = new java.util.ArrayList<java.lang.String>();

    public final java.util.List<java.lang.String> getRequirementsOrderIds() {
        return new java.util.ArrayList<java.lang.String>(requirementsOrderIds);
    }

    public final void setRequirementsOrderIds(java.util.List<java.lang.String> requirementsOrderIds) {
        if (requirementsOrderIds == null) throw new IllegalArgumentException("null is not allowed");
        if (this.requirementsOrderIds.equals(requirementsOrderIds)) return;
        this.requirementsOrderIds = new java.util.ArrayList<java.lang.String>(requirementsOrderIds);
        propertyChanged("requirementsOrderIds", this.requirementsOrderIds);
    }

    public final boolean containsRequirementsOrderId(java.lang.String requirementsOrderId) {
        return requirementsOrderIds.contains(requirementsOrderId);
    }


    // --- closedTasks ---

    private Set<String> closedTasksIds = new HashSet<String>();

    public final java.util.Set<scrum.client.sprint.Task> getClosedTasks() {
        if ( closedTasksIds.isEmpty()) return Collections.emptySet();
        return getDao().getTasks(this.closedTasksIds);
    }

    public final void setClosedTasks(Collection<scrum.client.sprint.Task> values) {
        closedTasksIds = ilarkesto.gwt.client.Gwt.getIdsAsSet(values);
        propertyChanged("closedTasksIds", this.closedTasksIds);
    }

    public final void addClosedTask(scrum.client.sprint.Task closedTask) {
        String id = closedTask.getId();
        if (closedTasksIds.contains(id)) return;
        closedTasksIds.add(id);
        propertyChanged("closedTasksIds", this.closedTasksIds);
    }

    public final void removeClosedTask(scrum.client.sprint.Task closedTask) {
        String id = closedTask.getId();
        if (!closedTasksIds.contains(id)) return;
        closedTasksIds.remove(id);
        propertyChanged("closedTasksIds", this.closedTasksIds);
    }

    public final boolean containsClosedTask(scrum.client.sprint.Task closedTask) {
        return closedTasksIds.contains(closedTask.getId());
    }


    // --- openTasks ---

    private Set<String> openTasksIds = new HashSet<String>();

    public final java.util.Set<scrum.client.sprint.Task> getOpenTasks() {
        if ( openTasksIds.isEmpty()) return Collections.emptySet();
        return getDao().getTasks(this.openTasksIds);
    }

    public final void setOpenTasks(Collection<scrum.client.sprint.Task> values) {
        openTasksIds = ilarkesto.gwt.client.Gwt.getIdsAsSet(values);
        propertyChanged("openTasksIds", this.openTasksIds);
    }

    public final void addOpenTask(scrum.client.sprint.Task openTask) {
        String id = openTask.getId();
        if (openTasksIds.contains(id)) return;
        openTasksIds.add(id);
        propertyChanged("openTasksIds", this.openTasksIds);
    }

    public final void removeOpenTask(scrum.client.sprint.Task openTask) {
        String id = openTask.getId();
        if (!openTasksIds.contains(id)) return;
        openTasksIds.remove(id);
        propertyChanged("openTasksIds", this.openTasksIds);
    }

    public final boolean containsOpenTask(scrum.client.sprint.Task openTask) {
        return openTasksIds.contains(openTask.getId());
    }


    // --- burnedWork ---

    private int burnedWork ;

    public final int getBurnedWork() {
        return this.burnedWork ;
    }

    public final SprintReport setBurnedWork(int burnedWork) {
        if (isBurnedWork(burnedWork)) return (SprintReport)this;
        this.burnedWork = burnedWork ;
        propertyChanged("burnedWork", this.burnedWork);
        return (SprintReport)this;
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
            return "SprintReport_burnedWork";
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
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- update properties by map ---

    public void updateProperties(Map props) {
        sprintId = (String) props.get("sprintId");
        completedRequirementsIds = (Set<String>) props.get("completedRequirementsIds");
        rejectedRequirementsIds = (Set<String>) props.get("rejectedRequirementsIds");
        requirementsOrderIds  = (java.util.List<java.lang.String>) props.get("requirementsOrderIds");
        closedTasksIds = (Set<String>) props.get("closedTasksIds");
        openTasksIds = (Set<String>) props.get("openTasksIds");
        burnedWork  = (Integer) props.get("burnedWork");
        updateLocalModificationTime();
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("sprintId", this.sprintId);
        properties.put("completedRequirementsIds", this.completedRequirementsIds);
        properties.put("rejectedRequirementsIds", this.rejectedRequirementsIds);
        properties.put("requirementsOrderIds", this.requirementsOrderIds);
        properties.put("closedTasksIds", this.closedTasksIds);
        properties.put("openTasksIds", this.openTasksIds);
        properties.put("burnedWork", this.burnedWork);
    }

}