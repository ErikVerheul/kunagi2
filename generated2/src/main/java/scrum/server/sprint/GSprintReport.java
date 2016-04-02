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

public abstract class GSprintReport
            extends AEntity
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, java.lang.Comparable<SprintReport> {

    // --- AEntity ---

    public final scrum.server.sprint.SprintReportDao getDao() {
        return sprintReportDao;
    }

    protected void repairDeadDatob(ADatob datob) {
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

    public int compareTo(SprintReport other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GSprintReport.class);

    public static final String TYPE = "sprintReport";

    // -----------------------------------------------------------
    // - sprint
    // -----------------------------------------------------------

    private String sprintId;
    private transient scrum.server.sprint.Sprint sprintCache;

    private void updateSprintCache() {
        sprintCache = this.sprintId == null ? null : (scrum.server.sprint.Sprint)sprintDao.getById(this.sprintId);
    }

    public final String getSprintId() {
        return this.sprintId;
    }

    public final scrum.server.sprint.Sprint getSprint() {
        if (sprintCache == null) updateSprintCache();
        return sprintCache;
    }

    public final void setSprint(scrum.server.sprint.Sprint sprint) {
        sprint = prepareSprint(sprint);
        if (isSprint(sprint)) return;
        this.sprintId = sprint == null ? null : sprint.getId();
        sprintCache = sprint;
        updateLastModified();
        fireModified("sprint="+sprint);
    }

    protected scrum.server.sprint.Sprint prepareSprint(scrum.server.sprint.Sprint sprint) {
        return sprint;
    }

    protected void repairDeadSprintReference(String entityId) {
        if (this.sprintId == null || entityId.equals(this.sprintId)) {
            repairMissingMaster();
        }
    }

    public final boolean isSprintSet() {
        return this.sprintId != null;
    }

    public final boolean isSprint(scrum.server.sprint.Sprint sprint) {
        if (this.sprintId == null && sprint == null) return true;
        return sprint != null && sprint.getId().equals(this.sprintId);
    }

    protected final void updateSprint(Object value) {
        setSprint(value == null ? null : (scrum.server.sprint.Sprint)sprintDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - completedRequirements
    // -----------------------------------------------------------

    private java.util.Set<String> completedRequirementsIds = new java.util.HashSet<String>();

    public final java.util.Set<scrum.server.project.Requirement> getCompletedRequirements() {
        return (java.util.Set) requirementDao.getByIdsAsSet(this.completedRequirementsIds);
    }

    public final void setCompletedRequirements(Collection<scrum.server.project.Requirement> completedRequirements) {
        completedRequirements = prepareCompletedRequirements(completedRequirements);
        if (completedRequirements == null) completedRequirements = Collections.emptyList();
        java.util.Set<String> ids = getIdsAsSet(completedRequirements);
        if (this.completedRequirementsIds.equals(ids)) return;
        this.completedRequirementsIds = ids;
        updateLastModified();
        fireModified("completedRequirements="+StrExtend.format(completedRequirements));
    }

    protected Collection<scrum.server.project.Requirement> prepareCompletedRequirements(Collection<scrum.server.project.Requirement> completedRequirements) {
        return completedRequirements;
    }

    protected void repairDeadCompletedRequirementReference(String entityId) {
        if (this.completedRequirementsIds.remove(entityId)) fireModified("completedRequirements-=" + entityId);
    }

    public final boolean containsCompletedRequirement(scrum.server.project.Requirement completedRequirement) {
        if (completedRequirement == null) return false;
        return this.completedRequirementsIds.contains(completedRequirement.getId());
    }

    public final int getCompletedRequirementsCount() {
        return this.completedRequirementsIds.size();
    }

    public final boolean isCompletedRequirementsEmpty() {
        return this.completedRequirementsIds.isEmpty();
    }

    public final boolean addCompletedRequirement(scrum.server.project.Requirement completedRequirement) {
        if (completedRequirement == null) throw new IllegalArgumentException("completedRequirement == null");
        boolean added = this.completedRequirementsIds.add(completedRequirement.getId());
        if (added) updateLastModified();
        if (added) fireModified("completedRequirements+=" + completedRequirement);
        return added;
    }

    public final boolean addCompletedRequirements(Collection<scrum.server.project.Requirement> completedRequirements) {
        if (completedRequirements == null) throw new IllegalArgumentException("completedRequirements == null");
        boolean added = false;
        for (scrum.server.project.Requirement completedRequirement : completedRequirements) {
            added = added | this.completedRequirementsIds.add(completedRequirement.getId());
        }
        return added;
    }

    public final boolean removeCompletedRequirement(scrum.server.project.Requirement completedRequirement) {
        if (completedRequirement == null) throw new IllegalArgumentException("completedRequirement == null");
        if (this.completedRequirementsIds == null) return false;
        boolean removed = this.completedRequirementsIds.remove(completedRequirement.getId());
        if (removed) updateLastModified();
        if (removed) fireModified("completedRequirements-=" + completedRequirement);
        return removed;
    }

    public final boolean removeCompletedRequirements(Collection<scrum.server.project.Requirement> completedRequirements) {
        if (completedRequirements == null) return false;
        if (completedRequirements.isEmpty()) return false;
        boolean removed = false;
        for (scrum.server.project.Requirement _element: completedRequirements) {
            removed = removed | removeCompletedRequirement(_element);
        }
        return removed;
    }

    public final boolean clearCompletedRequirements() {
        if (this.completedRequirementsIds.isEmpty()) return false;
        this.completedRequirementsIds.clear();
        updateLastModified();
        fireModified("completedRequirements cleared");
        return true;
    }

    protected final void updateCompletedRequirements(Object value) {
        Collection<String> ids = (Collection<String>) value;
        setCompletedRequirements((java.util.Set) requirementDao.getByIdsAsSet(ids));
    }

    // -----------------------------------------------------------
    // - rejectedRequirements
    // -----------------------------------------------------------

    private java.util.Set<String> rejectedRequirementsIds = new java.util.HashSet<String>();

    public final java.util.Set<scrum.server.project.Requirement> getRejectedRequirements() {
        return (java.util.Set) requirementDao.getByIdsAsSet(this.rejectedRequirementsIds);
    }

    public final void setRejectedRequirements(Collection<scrum.server.project.Requirement> rejectedRequirements) {
        rejectedRequirements = prepareRejectedRequirements(rejectedRequirements);
        if (rejectedRequirements == null) rejectedRequirements = Collections.emptyList();
        java.util.Set<String> ids = getIdsAsSet(rejectedRequirements);
        if (this.rejectedRequirementsIds.equals(ids)) return;
        this.rejectedRequirementsIds = ids;
        updateLastModified();
        fireModified("rejectedRequirements="+StrExtend.format(rejectedRequirements));
    }

    protected Collection<scrum.server.project.Requirement> prepareRejectedRequirements(Collection<scrum.server.project.Requirement> rejectedRequirements) {
        return rejectedRequirements;
    }

    protected void repairDeadRejectedRequirementReference(String entityId) {
        if (this.rejectedRequirementsIds.remove(entityId)) fireModified("rejectedRequirements-=" + entityId);
    }

    public final boolean containsRejectedRequirement(scrum.server.project.Requirement rejectedRequirement) {
        if (rejectedRequirement == null) return false;
        return this.rejectedRequirementsIds.contains(rejectedRequirement.getId());
    }

    public final int getRejectedRequirementsCount() {
        return this.rejectedRequirementsIds.size();
    }

    public final boolean isRejectedRequirementsEmpty() {
        return this.rejectedRequirementsIds.isEmpty();
    }

    public final boolean addRejectedRequirement(scrum.server.project.Requirement rejectedRequirement) {
        if (rejectedRequirement == null) throw new IllegalArgumentException("rejectedRequirement == null");
        boolean added = this.rejectedRequirementsIds.add(rejectedRequirement.getId());
        if (added) updateLastModified();
        if (added) fireModified("rejectedRequirements+=" + rejectedRequirement);
        return added;
    }

    public final boolean addRejectedRequirements(Collection<scrum.server.project.Requirement> rejectedRequirements) {
        if (rejectedRequirements == null) throw new IllegalArgumentException("rejectedRequirements == null");
        boolean added = false;
        for (scrum.server.project.Requirement rejectedRequirement : rejectedRequirements) {
            added = added | this.rejectedRequirementsIds.add(rejectedRequirement.getId());
        }
        return added;
    }

    public final boolean removeRejectedRequirement(scrum.server.project.Requirement rejectedRequirement) {
        if (rejectedRequirement == null) throw new IllegalArgumentException("rejectedRequirement == null");
        if (this.rejectedRequirementsIds == null) return false;
        boolean removed = this.rejectedRequirementsIds.remove(rejectedRequirement.getId());
        if (removed) updateLastModified();
        if (removed) fireModified("rejectedRequirements-=" + rejectedRequirement);
        return removed;
    }

    public final boolean removeRejectedRequirements(Collection<scrum.server.project.Requirement> rejectedRequirements) {
        if (rejectedRequirements == null) return false;
        if (rejectedRequirements.isEmpty()) return false;
        boolean removed = false;
        for (scrum.server.project.Requirement _element: rejectedRequirements) {
            removed = removed | removeRejectedRequirement(_element);
        }
        return removed;
    }

    public final boolean clearRejectedRequirements() {
        if (this.rejectedRequirementsIds.isEmpty()) return false;
        this.rejectedRequirementsIds.clear();
        updateLastModified();
        fireModified("rejectedRequirements cleared");
        return true;
    }

    protected final void updateRejectedRequirements(Object value) {
        Collection<String> ids = (Collection<String>) value;
        setRejectedRequirements((java.util.Set) requirementDao.getByIdsAsSet(ids));
    }

    // -----------------------------------------------------------
    // - requirementsOrderIds
    // -----------------------------------------------------------

    private java.util.List<java.lang.String> requirementsOrderIds = new java.util.ArrayList<java.lang.String>();

    public final java.util.List<java.lang.String> getRequirementsOrderIds() {
        return new java.util.ArrayList<java.lang.String>(requirementsOrderIds);
    }

    public final void setRequirementsOrderIds(Collection<java.lang.String> requirementsOrderIds) {
        requirementsOrderIds = prepareRequirementsOrderIds(requirementsOrderIds);
        if (requirementsOrderIds == null) requirementsOrderIds = Collections.emptyList();
        if (this.requirementsOrderIds.equals(requirementsOrderIds)) return;
        this.requirementsOrderIds = new java.util.ArrayList<java.lang.String>(requirementsOrderIds);
        updateLastModified();
        fireModified("requirementsOrderIds="+StrExtend.format(requirementsOrderIds));
    }

    protected Collection<java.lang.String> prepareRequirementsOrderIds(Collection<java.lang.String> requirementsOrderIds) {
        return requirementsOrderIds;
    }

    public final boolean containsRequirementsOrderId(java.lang.String requirementsOrderId) {
        if (requirementsOrderId == null) return false;
        return this.requirementsOrderIds.contains(requirementsOrderId);
    }

    public final int getRequirementsOrderIdsCount() {
        return this.requirementsOrderIds.size();
    }

    public final boolean isRequirementsOrderIdsEmpty() {
        return this.requirementsOrderIds.isEmpty();
    }

    public final boolean addRequirementsOrderId(java.lang.String requirementsOrderId) {
        if (requirementsOrderId == null) throw new IllegalArgumentException("requirementsOrderId == null");
        boolean added = this.requirementsOrderIds.add(requirementsOrderId);
        if (added) updateLastModified();
        if (added) fireModified("requirementsOrderIds+=" + requirementsOrderId);
        return added;
    }

    public final boolean addRequirementsOrderIds(Collection<java.lang.String> requirementsOrderIds) {
        if (requirementsOrderIds == null) throw new IllegalArgumentException("requirementsOrderIds == null");
        boolean added = false;
        for (java.lang.String requirementsOrderId : requirementsOrderIds) {
            added = added | this.requirementsOrderIds.add(requirementsOrderId);
        }
        return added;
    }

    public final boolean removeRequirementsOrderId(java.lang.String requirementsOrderId) {
        if (requirementsOrderId == null) throw new IllegalArgumentException("requirementsOrderId == null");
        if (this.requirementsOrderIds == null) return false;
        boolean removed = this.requirementsOrderIds.remove(requirementsOrderId);
        if (removed) updateLastModified();
        if (removed) fireModified("requirementsOrderIds-=" + requirementsOrderId);
        return removed;
    }

    public final boolean removeRequirementsOrderIds(Collection<java.lang.String> requirementsOrderIds) {
        if (requirementsOrderIds == null) return false;
        if (requirementsOrderIds.isEmpty()) return false;
        boolean removed = false;
        for (java.lang.String _element: requirementsOrderIds) {
            removed = removed | removeRequirementsOrderId(_element);
        }
        return removed;
    }

    public final boolean clearRequirementsOrderIds() {
        if (this.requirementsOrderIds.isEmpty()) return false;
        this.requirementsOrderIds.clear();
        updateLastModified();
        fireModified("requirementsOrderIds cleared");
        return true;
    }

    public final String getRequirementsOrderIdsAsCommaSeparatedString() {
        if (this.requirementsOrderIds.isEmpty()) return null;
        return StrExtend.concat(this.requirementsOrderIds,", ");
    }

    public final void setRequirementsOrderIdsAsCommaSeparatedString(String requirementsOrderIds) {
        setRequirementsOrderIds(StrExtend.parseCommaSeparatedString(requirementsOrderIds));
    }

    protected final void updateRequirementsOrderIds(Object value) {
        setRequirementsOrderIds((java.util.List<java.lang.String>) value);
    }

    // -----------------------------------------------------------
    // - closedTasks
    // -----------------------------------------------------------

    private java.util.Set<String> closedTasksIds = new java.util.HashSet<String>();

    public final java.util.Set<scrum.server.sprint.Task> getClosedTasks() {
        return (java.util.Set) taskDao.getByIdsAsSet(this.closedTasksIds);
    }

    public final void setClosedTasks(Collection<scrum.server.sprint.Task> closedTasks) {
        closedTasks = prepareClosedTasks(closedTasks);
        if (closedTasks == null) closedTasks = Collections.emptyList();
        java.util.Set<String> ids = getIdsAsSet(closedTasks);
        if (this.closedTasksIds.equals(ids)) return;
        this.closedTasksIds = ids;
        updateLastModified();
        fireModified("closedTasks="+StrExtend.format(closedTasks));
    }

    protected Collection<scrum.server.sprint.Task> prepareClosedTasks(Collection<scrum.server.sprint.Task> closedTasks) {
        return closedTasks;
    }

    protected void repairDeadClosedTaskReference(String entityId) {
        if (this.closedTasksIds.remove(entityId)) fireModified("closedTasks-=" + entityId);
    }

    public final boolean containsClosedTask(scrum.server.sprint.Task closedTask) {
        if (closedTask == null) return false;
        return this.closedTasksIds.contains(closedTask.getId());
    }

    public final int getClosedTasksCount() {
        return this.closedTasksIds.size();
    }

    public final boolean isClosedTasksEmpty() {
        return this.closedTasksIds.isEmpty();
    }

    public final boolean addClosedTask(scrum.server.sprint.Task closedTask) {
        if (closedTask == null) throw new IllegalArgumentException("closedTask == null");
        boolean added = this.closedTasksIds.add(closedTask.getId());
        if (added) updateLastModified();
        if (added) fireModified("closedTasks+=" + closedTask);
        return added;
    }

    public final boolean addClosedTasks(Collection<scrum.server.sprint.Task> closedTasks) {
        if (closedTasks == null) throw new IllegalArgumentException("closedTasks == null");
        boolean added = false;
        for (scrum.server.sprint.Task closedTask : closedTasks) {
            added = added | this.closedTasksIds.add(closedTask.getId());
        }
        return added;
    }

    public final boolean removeClosedTask(scrum.server.sprint.Task closedTask) {
        if (closedTask == null) throw new IllegalArgumentException("closedTask == null");
        if (this.closedTasksIds == null) return false;
        boolean removed = this.closedTasksIds.remove(closedTask.getId());
        if (removed) updateLastModified();
        if (removed) fireModified("closedTasks-=" + closedTask);
        return removed;
    }

    public final boolean removeClosedTasks(Collection<scrum.server.sprint.Task> closedTasks) {
        if (closedTasks == null) return false;
        if (closedTasks.isEmpty()) return false;
        boolean removed = false;
        for (scrum.server.sprint.Task _element: closedTasks) {
            removed = removed | removeClosedTask(_element);
        }
        return removed;
    }

    public final boolean clearClosedTasks() {
        if (this.closedTasksIds.isEmpty()) return false;
        this.closedTasksIds.clear();
        updateLastModified();
        fireModified("closedTasks cleared");
        return true;
    }

    protected final void updateClosedTasks(Object value) {
        Collection<String> ids = (Collection<String>) value;
        setClosedTasks((java.util.Set) taskDao.getByIdsAsSet(ids));
    }

    // -----------------------------------------------------------
    // - openTasks
    // -----------------------------------------------------------

    private java.util.Set<String> openTasksIds = new java.util.HashSet<String>();

    public final java.util.Set<scrum.server.sprint.Task> getOpenTasks() {
        return (java.util.Set) taskDao.getByIdsAsSet(this.openTasksIds);
    }

    public final void setOpenTasks(Collection<scrum.server.sprint.Task> openTasks) {
        openTasks = prepareOpenTasks(openTasks);
        if (openTasks == null) openTasks = Collections.emptyList();
        java.util.Set<String> ids = getIdsAsSet(openTasks);
        if (this.openTasksIds.equals(ids)) return;
        this.openTasksIds = ids;
        updateLastModified();
        fireModified("openTasks="+StrExtend.format(openTasks));
    }

    protected Collection<scrum.server.sprint.Task> prepareOpenTasks(Collection<scrum.server.sprint.Task> openTasks) {
        return openTasks;
    }

    protected void repairDeadOpenTaskReference(String entityId) {
        if (this.openTasksIds.remove(entityId)) fireModified("openTasks-=" + entityId);
    }

    public final boolean containsOpenTask(scrum.server.sprint.Task openTask) {
        if (openTask == null) return false;
        return this.openTasksIds.contains(openTask.getId());
    }

    public final int getOpenTasksCount() {
        return this.openTasksIds.size();
    }

    public final boolean isOpenTasksEmpty() {
        return this.openTasksIds.isEmpty();
    }

    public final boolean addOpenTask(scrum.server.sprint.Task openTask) {
        if (openTask == null) throw new IllegalArgumentException("openTask == null");
        boolean added = this.openTasksIds.add(openTask.getId());
        if (added) updateLastModified();
        if (added) fireModified("openTasks+=" + openTask);
        return added;
    }

    public final boolean addOpenTasks(Collection<scrum.server.sprint.Task> openTasks) {
        if (openTasks == null) throw new IllegalArgumentException("openTasks == null");
        boolean added = false;
        for (scrum.server.sprint.Task openTask : openTasks) {
            added = added | this.openTasksIds.add(openTask.getId());
        }
        return added;
    }

    public final boolean removeOpenTask(scrum.server.sprint.Task openTask) {
        if (openTask == null) throw new IllegalArgumentException("openTask == null");
        if (this.openTasksIds == null) return false;
        boolean removed = this.openTasksIds.remove(openTask.getId());
        if (removed) updateLastModified();
        if (removed) fireModified("openTasks-=" + openTask);
        return removed;
    }

    public final boolean removeOpenTasks(Collection<scrum.server.sprint.Task> openTasks) {
        if (openTasks == null) return false;
        if (openTasks.isEmpty()) return false;
        boolean removed = false;
        for (scrum.server.sprint.Task _element: openTasks) {
            removed = removed | removeOpenTask(_element);
        }
        return removed;
    }

    public final boolean clearOpenTasks() {
        if (this.openTasksIds.isEmpty()) return false;
        this.openTasksIds.clear();
        updateLastModified();
        fireModified("openTasks cleared");
        return true;
    }

    protected final void updateOpenTasks(Object value) {
        Collection<String> ids = (Collection<String>) value;
        setOpenTasks((java.util.Set) taskDao.getByIdsAsSet(ids));
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

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("sprintId")) updateSprint(value);
            if (property.equals("completedRequirementsIds")) updateCompletedRequirements(value);
            if (property.equals("rejectedRequirementsIds")) updateRejectedRequirements(value);
            if (property.equals("requirementsOrderIds")) updateRequirementsOrderIds(value);
            if (property.equals("closedTasksIds")) updateClosedTasks(value);
            if (property.equals("openTasksIds")) updateOpenTasks(value);
            if (property.equals("burnedWork")) updateBurnedWork(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadSprintReference(entityId);
        if (this.completedRequirementsIds == null) this.completedRequirementsIds = new java.util.HashSet<String>();
        repairDeadCompletedRequirementReference(entityId);
        if (this.rejectedRequirementsIds == null) this.rejectedRequirementsIds = new java.util.HashSet<String>();
        repairDeadRejectedRequirementReference(entityId);
        if (this.requirementsOrderIds == null) this.requirementsOrderIds = new java.util.ArrayList<java.lang.String>();
        if (this.closedTasksIds == null) this.closedTasksIds = new java.util.HashSet<String>();
        repairDeadClosedTaskReference(entityId);
        if (this.openTasksIds == null) this.openTasksIds = new java.util.HashSet<String>();
        repairDeadOpenTaskReference(entityId);
    }

    // --- ensure integrity ---

    public void ensureIntegrity() {
        super.ensureIntegrity();
        if (!isSprintSet()) {
            repairMissingMaster();
            return;
        }
        try {
            getSprint();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead sprint reference");
            repairDeadSprintReference(this.sprintId);
        }
        if (this.completedRequirementsIds == null) this.completedRequirementsIds = new java.util.HashSet<String>();
        Set<String> completedRequirements = new HashSet<String>(this.completedRequirementsIds);
        for (String entityId : completedRequirements) {
            try {
                requirementDao.getById(entityId);
            } catch (EntityDoesNotExistException ex) {
                LOG.info("Repairing dead completedRequirement reference");
                repairDeadCompletedRequirementReference(entityId);
            }
        }
        if (this.rejectedRequirementsIds == null) this.rejectedRequirementsIds = new java.util.HashSet<String>();
        Set<String> rejectedRequirements = new HashSet<String>(this.rejectedRequirementsIds);
        for (String entityId : rejectedRequirements) {
            try {
                requirementDao.getById(entityId);
            } catch (EntityDoesNotExistException ex) {
                LOG.info("Repairing dead rejectedRequirement reference");
                repairDeadRejectedRequirementReference(entityId);
            }
        }
        if (this.requirementsOrderIds == null) this.requirementsOrderIds = new java.util.ArrayList<java.lang.String>();
        if (this.closedTasksIds == null) this.closedTasksIds = new java.util.HashSet<String>();
        Set<String> closedTasks = new HashSet<String>(this.closedTasksIds);
        for (String entityId : closedTasks) {
            try {
                taskDao.getById(entityId);
            } catch (EntityDoesNotExistException ex) {
                LOG.info("Repairing dead closedTask reference");
                repairDeadClosedTaskReference(entityId);
            }
        }
        if (this.openTasksIds == null) this.openTasksIds = new java.util.HashSet<String>();
        Set<String> openTasks = new HashSet<String>(this.openTasksIds);
        for (String entityId : openTasks) {
            try {
                taskDao.getById(entityId);
            } catch (EntityDoesNotExistException ex) {
                LOG.info("Repairing dead openTask reference");
                repairDeadOpenTaskReference(entityId);
            }
        }
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    static scrum.server.sprint.SprintDao sprintDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSprintDao(scrum.server.sprint.SprintDao sprintDao) {
        GSprintReport.sprintDao = sprintDao;
    }

    static scrum.server.project.RequirementDao requirementDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setRequirementDao(scrum.server.project.RequirementDao requirementDao) {
        GSprintReport.requirementDao = requirementDao;
    }

    static scrum.server.sprint.TaskDao taskDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setTaskDao(scrum.server.sprint.TaskDao taskDao) {
        GSprintReport.taskDao = taskDao;
    }

    static scrum.server.sprint.SprintReportDao sprintReportDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSprintReportDao(scrum.server.sprint.SprintReportDao sprintReportDao) {
        GSprintReport.sprintReportDao = sprintReportDao;
    }

}