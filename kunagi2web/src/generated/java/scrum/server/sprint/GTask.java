// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.EntityGenerator










package scrum.server.sprint;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.ADatob;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.AStructure;
import ilarkesto.auth.AUser;
import ilarkesto.persistence.EntityDoesNotExistException;
import ilarkesto.base.StrExtend;

public abstract class GTask
            extends AEntity
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, ilarkesto.search.Searchable, java.lang.Comparable<Task> {

    // --- AEntity ---

    public final scrum.server.sprint.TaskDao getDao() {
        return taskDao;
    }

    protected void repairDeadDatob(ADatob datob) {
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

    public int compareTo(Task other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    public final java.util.Set<scrum.server.sprint.SprintReport> getSprintReports() {
        return sprintReportDao.getSprintReportsByClosedTask((Task)this);
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GTask.class);

    public static final String TYPE = "task";


    // -----------------------------------------------------------
    // - Searchable
    // -----------------------------------------------------------

    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getDescription(), key)) return true;
        return false;
    }

    // -----------------------------------------------------------
    // - requirement
    // -----------------------------------------------------------

    private String requirementId;
    private transient scrum.server.project.Requirement requirementCache;

    private void updateRequirementCache() {
        requirementCache = this.requirementId == null ? null : (scrum.server.project.Requirement)requirementDao.getById(this.requirementId);
    }

    public final String getRequirementId() {
        return this.requirementId;
    }

    public final scrum.server.project.Requirement getRequirement() {
        if (requirementCache == null) updateRequirementCache();
        return requirementCache;
    }

    public final void setRequirement(scrum.server.project.Requirement requirement) {
        requirement = prepareRequirement(requirement);
        if (isRequirement(requirement)) return;
        this.requirementId = requirement == null ? null : requirement.getId();
        requirementCache = requirement;
        updateLastModified();
        fireModified("requirement="+requirement);
    }

    protected scrum.server.project.Requirement prepareRequirement(scrum.server.project.Requirement requirement) {
        return requirement;
    }

    protected void repairDeadRequirementReference(String entityId) {
        if (this.requirementId == null || entityId.equals(this.requirementId)) {
            repairMissingMaster();
        }
    }

    public final boolean isRequirementSet() {
        return this.requirementId != null;
    }

    public final boolean isRequirement(scrum.server.project.Requirement requirement) {
        if (this.requirementId == null && requirement == null) return true;
        return requirement != null && requirement.getId().equals(this.requirementId);
    }

    protected final void updateRequirement(Object value) {
        setRequirement(value == null ? null : (scrum.server.project.Requirement)requirementDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - number
    // -----------------------------------------------------------

    private int number;

    public final int getNumber() {
        return number;
    }

    public final void setNumber(int number) {
        number = prepareNumber(number);
        if (isNumber(number)) return;
        this.number = number;
        updateLastModified();
        fireModified("number="+number);
    }

    protected int prepareNumber(int number) {
        return number;
    }

    public final boolean isNumber(int number) {
        return this.number == number;
    }

    protected final void updateNumber(Object value) {
        setNumber((Integer)value);
    }

    // -----------------------------------------------------------
    // - label
    // -----------------------------------------------------------

    private java.lang.String label;

    public final java.lang.String getLabel() {
        return label;
    }

    public final void setLabel(java.lang.String label) {
        label = prepareLabel(label);
        if (isLabel(label)) return;
        this.label = label;
        updateLastModified();
        fireModified("label="+label);
    }

    protected java.lang.String prepareLabel(java.lang.String label) {
        // label = StrExtend.removeUnreadableChars(label);
        return label;
    }

    public final boolean isLabelSet() {
        return this.label != null;
    }

    public final boolean isLabel(java.lang.String label) {
        if (this.label == null && label == null) return true;
        return this.label != null && this.label.equals(label);
    }

    protected final void updateLabel(Object value) {
        setLabel((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - description
    // -----------------------------------------------------------

    private java.lang.String description;

    public final java.lang.String getDescription() {
        return description;
    }

    public final void setDescription(java.lang.String description) {
        description = prepareDescription(description);
        if (isDescription(description)) return;
        this.description = description;
        updateLastModified();
        fireModified("description="+description);
    }

    protected java.lang.String prepareDescription(java.lang.String description) {
        // description = StrExtend.removeUnreadableChars(description);
        return description;
    }

    public final boolean isDescriptionSet() {
        return this.description != null;
    }

    public final boolean isDescription(java.lang.String description) {
        if (this.description == null && description == null) return true;
        return this.description != null && this.description.equals(description);
    }

    protected final void updateDescription(Object value) {
        setDescription((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - remainingWork
    // -----------------------------------------------------------

    private int remainingWork;

    public final int getRemainingWork() {
        return remainingWork;
    }

    public final void setRemainingWork(int remainingWork) {
        remainingWork = prepareRemainingWork(remainingWork);
        if (isRemainingWork(remainingWork)) return;
        this.remainingWork = remainingWork;
        updateLastModified();
        fireModified("remainingWork="+remainingWork);
    }

    protected int prepareRemainingWork(int remainingWork) {
        return remainingWork;
    }

    public final boolean isRemainingWork(int remainingWork) {
        return this.remainingWork == remainingWork;
    }

    protected final void updateRemainingWork(Object value) {
        setRemainingWork((Integer)value);
    }

    // -----------------------------------------------------------
    // - burnedWork
    // -----------------------------------------------------------

    private int burnedWork;

    public final int getBurnedWork() {
        return burnedWork;
    }

    public final void setBurnedWork(int burnedWork) {
        burnedWork = prepareBurnedWork(burnedWork);
        if (isBurnedWork(burnedWork)) return;
        this.burnedWork = burnedWork;
        updateLastModified();
        fireModified("burnedWork="+burnedWork);
    }

    protected int prepareBurnedWork(int burnedWork) {
        return burnedWork;
    }

    public final boolean isBurnedWork(int burnedWork) {
        return this.burnedWork == burnedWork;
    }

    protected final void updateBurnedWork(Object value) {
        setBurnedWork((Integer)value);
    }

    // -----------------------------------------------------------
    // - owner
    // -----------------------------------------------------------

    private String ownerId;
    private transient scrum.server.admin.User ownerCache;

    private void updateOwnerCache() {
        ownerCache = this.ownerId == null ? null : (scrum.server.admin.User)userDao.getById(this.ownerId);
    }

    public final String getOwnerId() {
        return this.ownerId;
    }

    public final scrum.server.admin.User getOwner() {
        if (ownerCache == null) updateOwnerCache();
        return ownerCache;
    }

    public final void setOwner(scrum.server.admin.User owner) {
        owner = prepareOwner(owner);
        if (isOwner(owner)) return;
        this.ownerId = owner == null ? null : owner.getId();
        ownerCache = owner;
        updateLastModified();
        fireModified("owner="+owner);
    }

    protected scrum.server.admin.User prepareOwner(scrum.server.admin.User owner) {
        return owner;
    }

    protected void repairDeadOwnerReference(String entityId) {
        if (this.ownerId == null || entityId.equals(this.ownerId)) {
            setOwner(null);
        }
    }

    public final boolean isOwnerSet() {
        return this.ownerId != null;
    }

    public final boolean isOwner(scrum.server.admin.User owner) {
        if (this.ownerId == null && owner == null) return true;
        return owner != null && owner.getId().equals(this.ownerId);
    }

    protected final void updateOwner(Object value) {
        setOwner(value == null ? null : (scrum.server.admin.User)userDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - impediment
    // -----------------------------------------------------------

    private String impedimentId;
    private transient scrum.server.impediments.Impediment impedimentCache;

    private void updateImpedimentCache() {
        impedimentCache = this.impedimentId == null ? null : (scrum.server.impediments.Impediment)impedimentDao.getById(this.impedimentId);
    }

    public final String getImpedimentId() {
        return this.impedimentId;
    }

    public final scrum.server.impediments.Impediment getImpediment() {
        if (impedimentCache == null) updateImpedimentCache();
        return impedimentCache;
    }

    public final void setImpediment(scrum.server.impediments.Impediment impediment) {
        impediment = prepareImpediment(impediment);
        if (isImpediment(impediment)) return;
        this.impedimentId = impediment == null ? null : impediment.getId();
        impedimentCache = impediment;
        updateLastModified();
        fireModified("impediment="+impediment);
    }

    protected scrum.server.impediments.Impediment prepareImpediment(scrum.server.impediments.Impediment impediment) {
        return impediment;
    }

    protected void repairDeadImpedimentReference(String entityId) {
        if (this.impedimentId == null || entityId.equals(this.impedimentId)) {
            setImpediment(null);
        }
    }

    public final boolean isImpedimentSet() {
        return this.impedimentId != null;
    }

    public final boolean isImpediment(scrum.server.impediments.Impediment impediment) {
        if (this.impedimentId == null && impediment == null) return true;
        return impediment != null && impediment.getId().equals(this.impedimentId);
    }

    protected final void updateImpediment(Object value) {
        setImpediment(value == null ? null : (scrum.server.impediments.Impediment)impedimentDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - closedInPastSprint
    // -----------------------------------------------------------

    private String closedInPastSprintId;
    private transient scrum.server.sprint.Sprint closedInPastSprintCache;

    private void updateClosedInPastSprintCache() {
        closedInPastSprintCache = this.closedInPastSprintId == null ? null : (scrum.server.sprint.Sprint)sprintDao.getById(this.closedInPastSprintId);
    }

    public final String getClosedInPastSprintId() {
        return this.closedInPastSprintId;
    }

    public final scrum.server.sprint.Sprint getClosedInPastSprint() {
        if (closedInPastSprintCache == null) updateClosedInPastSprintCache();
        return closedInPastSprintCache;
    }

    public final void setClosedInPastSprint(scrum.server.sprint.Sprint closedInPastSprint) {
        closedInPastSprint = prepareClosedInPastSprint(closedInPastSprint);
        if (isClosedInPastSprint(closedInPastSprint)) return;
        this.closedInPastSprintId = closedInPastSprint == null ? null : closedInPastSprint.getId();
        closedInPastSprintCache = closedInPastSprint;
        updateLastModified();
        fireModified("closedInPastSprint="+closedInPastSprint);
    }

    protected scrum.server.sprint.Sprint prepareClosedInPastSprint(scrum.server.sprint.Sprint closedInPastSprint) {
        return closedInPastSprint;
    }

    protected void repairDeadClosedInPastSprintReference(String entityId) {
        if (this.closedInPastSprintId == null || entityId.equals(this.closedInPastSprintId)) {
            setClosedInPastSprint(null);
        }
    }

    public final boolean isClosedInPastSprintSet() {
        return this.closedInPastSprintId != null;
    }

    public final boolean isClosedInPastSprint(scrum.server.sprint.Sprint closedInPastSprint) {
        if (this.closedInPastSprintId == null && closedInPastSprint == null) return true;
        return closedInPastSprint != null && closedInPastSprint.getId().equals(this.closedInPastSprintId);
    }

    protected final void updateClosedInPastSprint(Object value) {
        setClosedInPastSprint(value == null ? null : (scrum.server.sprint.Sprint)sprintDao.getById((String)value));
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("requirementId")) updateRequirement(value);
            if (property.equals("number")) updateNumber(value);
            if (property.equals("label")) updateLabel(value);
            if (property.equals("description")) updateDescription(value);
            if (property.equals("remainingWork")) updateRemainingWork(value);
            if (property.equals("burnedWork")) updateBurnedWork(value);
            if (property.equals("ownerId")) updateOwner(value);
            if (property.equals("impedimentId")) updateImpediment(value);
            if (property.equals("closedInPastSprintId")) updateClosedInPastSprint(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadRequirementReference(entityId);
        repairDeadOwnerReference(entityId);
        repairDeadImpedimentReference(entityId);
        repairDeadClosedInPastSprintReference(entityId);
    }

    // --- ensure integrity ---

    public void ensureIntegrity() {
        super.ensureIntegrity();
        if (!isRequirementSet()) {
            repairMissingMaster();
            return;
        }
        try {
            getRequirement();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead requirement reference");
            repairDeadRequirementReference(this.requirementId);
        }
        try {
            getOwner();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead owner reference");
            repairDeadOwnerReference(this.ownerId);
        }
        try {
            getImpediment();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead impediment reference");
            repairDeadImpedimentReference(this.impedimentId);
        }
        try {
            getClosedInPastSprint();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead closedInPastSprint reference");
            repairDeadClosedInPastSprintReference(this.closedInPastSprintId);
        }
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    static scrum.server.project.RequirementDao requirementDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setRequirementDao(scrum.server.project.RequirementDao requirementDao) {
        GTask.requirementDao = requirementDao;
    }

    static scrum.server.impediments.ImpedimentDao impedimentDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setImpedimentDao(scrum.server.impediments.ImpedimentDao impedimentDao) {
        GTask.impedimentDao = impedimentDao;
    }

    static scrum.server.sprint.SprintDao sprintDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSprintDao(scrum.server.sprint.SprintDao sprintDao) {
        GTask.sprintDao = sprintDao;
    }

    static scrum.server.sprint.TaskDao taskDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setTaskDao(scrum.server.sprint.TaskDao taskDao) {
        GTask.taskDao = taskDao;
    }

    static scrum.server.sprint.SprintReportDao sprintReportDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSprintReportDao(scrum.server.sprint.SprintReportDao sprintReportDao) {
        GTask.sprintReportDao = sprintReportDao;
    }

}