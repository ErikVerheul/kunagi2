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

public abstract class GSprint
            extends AEntity
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, ilarkesto.search.Searchable, java.lang.Comparable<Sprint> {

    // --- AEntity ---

    public final scrum.server.sprint.SprintDao getDao() {
        return sprintDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("number", this.number);
        properties.put("projectId", this.projectId);
        properties.put("label", this.label);
        properties.put("goal", this.goal);
        properties.put("begin", this.begin == null ? null : this.begin.toString());
        properties.put("end", this.end == null ? null : this.end.toString());
        properties.put("velocity", this.velocity);
        properties.put("completedRequirementsData", this.completedRequirementsData);
        properties.put("incompletedRequirementsData", this.incompletedRequirementsData);
        properties.put("planningNote", this.planningNote);
        properties.put("reviewNote", this.reviewNote);
        properties.put("retrospectiveNote", this.retrospectiveNote);
        properties.put("requirementsOrderIds", this.requirementsOrderIds);
        properties.put("productOwnersIds", this.productOwnersIds);
        properties.put("scrumMastersIds", this.scrumMastersIds);
        properties.put("teamMembersIds", this.teamMembersIds);
    }

    public int compareTo(Sprint other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    public final java.util.Set<scrum.server.project.Project> getCurrentSprintProjects() {
        return projectDao.getProjectsByCurrentSprint((Sprint)this);
    }

    public final java.util.Set<scrum.server.project.Project> getNextSprintProjects() {
        return projectDao.getProjectsByNextSprint((Sprint)this);
    }

    public final java.util.Set<scrum.server.sprint.SprintDaySnapshot> getSprintDaySnapshots() {
        return sprintDaySnapshotDao.getSprintDaySnapshotsBySprint((Sprint)this);
    }

    public final scrum.server.sprint.SprintReport getSprintReport() {
        return sprintReportDao.getSprintReportBySprint((Sprint)this);
    }

    public final java.util.Set<scrum.server.project.Requirement> getRequirements() {
        return requirementDao.getRequirementsBySprint((Sprint)this);
    }

    public final java.util.Set<scrum.server.release.Release> getReleases() {
        return releaseDao.getReleasesBySprint((Sprint)this);
    }

    public final java.util.Set<scrum.server.sprint.Task> getClosedTasksInPasts() {
        return taskDao.getTasksByClosedInPastSprint((Sprint)this);
    }

    public final scrum.server.project.ProjectSprintSnapshot getProjectSprintSnapshot() {
        return projectSprintSnapshotDao.getProjectSprintSnapshotBySprint((Sprint)this);
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GSprint.class);

    public static final String TYPE = "sprint";


    // -----------------------------------------------------------
    // - Searchable
    // -----------------------------------------------------------

    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getGoal(), key)) return true;
        if (matchesKey(getPlanningNote(), key)) return true;
        if (matchesKey(getReviewNote(), key)) return true;
        if (matchesKey(getRetrospectiveNote(), key)) return true;
        return false;
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
    // - project
    // -----------------------------------------------------------

    private String projectId;
    private transient scrum.server.project.Project projectCache;

    private void updateProjectCache() {
        projectCache = this.projectId == null ? null : (scrum.server.project.Project)projectDao.getById(this.projectId);
    }

    public final String getProjectId() {
        return this.projectId;
    }

    public final scrum.server.project.Project getProject() {
        if (projectCache == null) updateProjectCache();
        return projectCache;
    }

    public final void setProject(scrum.server.project.Project project) {
        project = prepareProject(project);
        if (isProject(project)) return;
        this.projectId = project == null ? null : project.getId();
        projectCache = project;
        updateLastModified();
        fireModified("project="+project);
    }

    protected scrum.server.project.Project prepareProject(scrum.server.project.Project project) {
        return project;
    }

    protected void repairDeadProjectReference(String entityId) {
        if (this.projectId == null || entityId.equals(this.projectId)) {
            repairMissingMaster();
        }
    }

    public final boolean isProjectSet() {
        return this.projectId != null;
    }

    public final boolean isProject(scrum.server.project.Project project) {
        if (this.projectId == null && project == null) return true;
        return project != null && project.getId().equals(this.projectId);
    }

    protected final void updateProject(Object value) {
        setProject(value == null ? null : (scrum.server.project.Project)projectDao.getById((String)value));
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
    // - goal
    // -----------------------------------------------------------

    private java.lang.String goal;

    public final java.lang.String getGoal() {
        return goal;
    }

    public final void setGoal(java.lang.String goal) {
        goal = prepareGoal(goal);
        if (isGoal(goal)) return;
        this.goal = goal;
        updateLastModified();
        fireModified("goal="+goal);
    }

    protected java.lang.String prepareGoal(java.lang.String goal) {
        // goal = StrExtend.removeUnreadableChars(goal);
        return goal;
    }

    public final boolean isGoalSet() {
        return this.goal != null;
    }

    public final boolean isGoal(java.lang.String goal) {
        if (this.goal == null && goal == null) return true;
        return this.goal != null && this.goal.equals(goal);
    }

    protected final void updateGoal(Object value) {
        setGoal((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - begin
    // -----------------------------------------------------------

    private ilarkesto.core.time.Date begin;

    public final ilarkesto.core.time.Date getBegin() {
        return begin;
    }

    public final void setBegin(ilarkesto.core.time.Date begin) {
        begin = prepareBegin(begin);
        if (isBegin(begin)) return;
        this.begin = begin;
        updateLastModified();
        fireModified("begin="+begin);
    }

    protected ilarkesto.core.time.Date prepareBegin(ilarkesto.core.time.Date begin) {
        return begin;
    }

    public final boolean isBeginSet() {
        return this.begin != null;
    }

    public final boolean isBegin(ilarkesto.core.time.Date begin) {
        if (this.begin == null && begin == null) return true;
        return this.begin != null && this.begin.equals(begin);
    }

    protected final void updateBegin(Object value) {
        value = value == null ? null : new ilarkesto.core.time.Date((String)value);
        setBegin((ilarkesto.core.time.Date)value);
    }

    // -----------------------------------------------------------
    // - end
    // -----------------------------------------------------------

    private ilarkesto.core.time.Date end;

    public final ilarkesto.core.time.Date getEnd() {
        return end;
    }

    public final void setEnd(ilarkesto.core.time.Date end) {
        end = prepareEnd(end);
        if (isEnd(end)) return;
        this.end = end;
        updateLastModified();
        fireModified("end="+end);
    }

    protected ilarkesto.core.time.Date prepareEnd(ilarkesto.core.time.Date end) {
        return end;
    }

    public final boolean isEndSet() {
        return this.end != null;
    }

    public final boolean isEnd(ilarkesto.core.time.Date end) {
        if (this.end == null && end == null) return true;
        return this.end != null && this.end.equals(end);
    }

    protected final void updateEnd(Object value) {
        value = value == null ? null : new ilarkesto.core.time.Date((String)value);
        setEnd((ilarkesto.core.time.Date)value);
    }

    // -----------------------------------------------------------
    // - velocity
    // -----------------------------------------------------------

    private java.lang.Float velocity;

    public final java.lang.Float getVelocity() {
        return velocity;
    }

    public final void setVelocity(java.lang.Float velocity) {
        velocity = prepareVelocity(velocity);
        if (isVelocity(velocity)) return;
        this.velocity = velocity;
        updateLastModified();
        fireModified("velocity="+velocity);
    }

    protected java.lang.Float prepareVelocity(java.lang.Float velocity) {
        return velocity;
    }

    public final boolean isVelocitySet() {
        return this.velocity != null;
    }

    public final boolean isVelocity(java.lang.Float velocity) {
        if (this.velocity == null && velocity == null) return true;
        return this.velocity != null && this.velocity.equals(velocity);
    }

    protected final void updateVelocity(Object value) {
        setVelocity((java.lang.Float)value);
    }

    // -----------------------------------------------------------
    // - completedRequirementsData
    // -----------------------------------------------------------

    private java.lang.String completedRequirementsData;

    public final java.lang.String getCompletedRequirementsData() {
        return completedRequirementsData;
    }

    public final void setCompletedRequirementsData(java.lang.String completedRequirementsData) {
        completedRequirementsData = prepareCompletedRequirementsData(completedRequirementsData);
        if (isCompletedRequirementsData(completedRequirementsData)) return;
        this.completedRequirementsData = completedRequirementsData;
        updateLastModified();
        fireModified("completedRequirementsData="+completedRequirementsData);
    }

    protected java.lang.String prepareCompletedRequirementsData(java.lang.String completedRequirementsData) {
        // completedRequirementsData = StrExtend.removeUnreadableChars(completedRequirementsData);
        return completedRequirementsData;
    }

    public final boolean isCompletedRequirementsDataSet() {
        return this.completedRequirementsData != null;
    }

    public final boolean isCompletedRequirementsData(java.lang.String completedRequirementsData) {
        if (this.completedRequirementsData == null && completedRequirementsData == null) return true;
        return this.completedRequirementsData != null && this.completedRequirementsData.equals(completedRequirementsData);
    }

    protected final void updateCompletedRequirementsData(Object value) {
        setCompletedRequirementsData((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - incompletedRequirementsData
    // -----------------------------------------------------------

    private java.lang.String incompletedRequirementsData;

    public final java.lang.String getIncompletedRequirementsData() {
        return incompletedRequirementsData;
    }

    public final void setIncompletedRequirementsData(java.lang.String incompletedRequirementsData) {
        incompletedRequirementsData = prepareIncompletedRequirementsData(incompletedRequirementsData);
        if (isIncompletedRequirementsData(incompletedRequirementsData)) return;
        this.incompletedRequirementsData = incompletedRequirementsData;
        updateLastModified();
        fireModified("incompletedRequirementsData="+incompletedRequirementsData);
    }

    protected java.lang.String prepareIncompletedRequirementsData(java.lang.String incompletedRequirementsData) {
        // incompletedRequirementsData = StrExtend.removeUnreadableChars(incompletedRequirementsData);
        return incompletedRequirementsData;
    }

    public final boolean isIncompletedRequirementsDataSet() {
        return this.incompletedRequirementsData != null;
    }

    public final boolean isIncompletedRequirementsData(java.lang.String incompletedRequirementsData) {
        if (this.incompletedRequirementsData == null && incompletedRequirementsData == null) return true;
        return this.incompletedRequirementsData != null && this.incompletedRequirementsData.equals(incompletedRequirementsData);
    }

    protected final void updateIncompletedRequirementsData(Object value) {
        setIncompletedRequirementsData((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - planningNote
    // -----------------------------------------------------------

    private java.lang.String planningNote;

    public final java.lang.String getPlanningNote() {
        return planningNote;
    }

    public final void setPlanningNote(java.lang.String planningNote) {
        planningNote = preparePlanningNote(planningNote);
        if (isPlanningNote(planningNote)) return;
        this.planningNote = planningNote;
        updateLastModified();
        fireModified("planningNote="+planningNote);
    }

    protected java.lang.String preparePlanningNote(java.lang.String planningNote) {
        // planningNote = StrExtend.removeUnreadableChars(planningNote);
        return planningNote;
    }

    public final boolean isPlanningNoteSet() {
        return this.planningNote != null;
    }

    public final boolean isPlanningNote(java.lang.String planningNote) {
        if (this.planningNote == null && planningNote == null) return true;
        return this.planningNote != null && this.planningNote.equals(planningNote);
    }

    protected final void updatePlanningNote(Object value) {
        setPlanningNote((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - reviewNote
    // -----------------------------------------------------------

    private java.lang.String reviewNote;

    public final java.lang.String getReviewNote() {
        return reviewNote;
    }

    public final void setReviewNote(java.lang.String reviewNote) {
        reviewNote = prepareReviewNote(reviewNote);
        if (isReviewNote(reviewNote)) return;
        this.reviewNote = reviewNote;
        updateLastModified();
        fireModified("reviewNote="+reviewNote);
    }

    protected java.lang.String prepareReviewNote(java.lang.String reviewNote) {
        // reviewNote = StrExtend.removeUnreadableChars(reviewNote);
        return reviewNote;
    }

    public final boolean isReviewNoteSet() {
        return this.reviewNote != null;
    }

    public final boolean isReviewNote(java.lang.String reviewNote) {
        if (this.reviewNote == null && reviewNote == null) return true;
        return this.reviewNote != null && this.reviewNote.equals(reviewNote);
    }

    protected final void updateReviewNote(Object value) {
        setReviewNote((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - retrospectiveNote
    // -----------------------------------------------------------

    private java.lang.String retrospectiveNote;

    public final java.lang.String getRetrospectiveNote() {
        return retrospectiveNote;
    }

    public final void setRetrospectiveNote(java.lang.String retrospectiveNote) {
        retrospectiveNote = prepareRetrospectiveNote(retrospectiveNote);
        if (isRetrospectiveNote(retrospectiveNote)) return;
        this.retrospectiveNote = retrospectiveNote;
        updateLastModified();
        fireModified("retrospectiveNote="+retrospectiveNote);
    }

    protected java.lang.String prepareRetrospectiveNote(java.lang.String retrospectiveNote) {
        // retrospectiveNote = StrExtend.removeUnreadableChars(retrospectiveNote);
        return retrospectiveNote;
    }

    public final boolean isRetrospectiveNoteSet() {
        return this.retrospectiveNote != null;
    }

    public final boolean isRetrospectiveNote(java.lang.String retrospectiveNote) {
        if (this.retrospectiveNote == null && retrospectiveNote == null) return true;
        return this.retrospectiveNote != null && this.retrospectiveNote.equals(retrospectiveNote);
    }

    protected final void updateRetrospectiveNote(Object value) {
        setRetrospectiveNote((java.lang.String)value);
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
    // - productOwners
    // -----------------------------------------------------------

    private java.util.Set<String> productOwnersIds = new java.util.HashSet<String>();

    public final java.util.Set<scrum.server.admin.User> getProductOwners() {
        return (java.util.Set) userDao.getByIdsAsSet(this.productOwnersIds);
    }

    public final void setProductOwners(Collection<scrum.server.admin.User> productOwners) {
        productOwners = prepareProductOwners(productOwners);
        if (productOwners == null) productOwners = Collections.emptyList();
        java.util.Set<String> ids = getIdsAsSet(productOwners);
        if (this.productOwnersIds.equals(ids)) return;
        this.productOwnersIds = ids;
        updateLastModified();
        fireModified("productOwners="+StrExtend.format(productOwners));
    }

    protected Collection<scrum.server.admin.User> prepareProductOwners(Collection<scrum.server.admin.User> productOwners) {
        return productOwners;
    }

    protected void repairDeadProductOwnerReference(String entityId) {
        if (this.productOwnersIds.remove(entityId)) fireModified("productOwners-=" + entityId);
    }

    public final boolean containsProductOwner(scrum.server.admin.User productOwner) {
        if (productOwner == null) return false;
        return this.productOwnersIds.contains(productOwner.getId());
    }

    public final int getProductOwnersCount() {
        return this.productOwnersIds.size();
    }

    public final boolean isProductOwnersEmpty() {
        return this.productOwnersIds.isEmpty();
    }

    public final boolean addProductOwner(scrum.server.admin.User productOwner) {
        if (productOwner == null) throw new IllegalArgumentException("productOwner == null");
        boolean added = this.productOwnersIds.add(productOwner.getId());
        if (added) updateLastModified();
        if (added) fireModified("productOwners+=" + productOwner);
        return added;
    }

    public final boolean addProductOwners(Collection<scrum.server.admin.User> productOwners) {
        if (productOwners == null) throw new IllegalArgumentException("productOwners == null");
        boolean added = false;
        for (scrum.server.admin.User productOwner : productOwners) {
            added = added | this.productOwnersIds.add(productOwner.getId());
        }
        return added;
    }

    public final boolean removeProductOwner(scrum.server.admin.User productOwner) {
        if (productOwner == null) throw new IllegalArgumentException("productOwner == null");
        if (this.productOwnersIds == null) return false;
        boolean removed = this.productOwnersIds.remove(productOwner.getId());
        if (removed) updateLastModified();
        if (removed) fireModified("productOwners-=" + productOwner);
        return removed;
    }

    public final boolean removeProductOwners(Collection<scrum.server.admin.User> productOwners) {
        if (productOwners == null) return false;
        if (productOwners.isEmpty()) return false;
        boolean removed = false;
        for (scrum.server.admin.User _element: productOwners) {
            removed = removed | removeProductOwner(_element);
        }
        return removed;
    }

    public final boolean clearProductOwners() {
        if (this.productOwnersIds.isEmpty()) return false;
        this.productOwnersIds.clear();
        updateLastModified();
        fireModified("productOwners cleared");
        return true;
    }

    protected final void updateProductOwners(Object value) {
        Collection<String> ids = (Collection<String>) value;
        setProductOwners((java.util.Set) userDao.getByIdsAsSet(ids));
    }

    // -----------------------------------------------------------
    // - scrumMasters
    // -----------------------------------------------------------

    private java.util.Set<String> scrumMastersIds = new java.util.HashSet<String>();

    public final java.util.Set<scrum.server.admin.User> getScrumMasters() {
        return (java.util.Set) userDao.getByIdsAsSet(this.scrumMastersIds);
    }

    public final void setScrumMasters(Collection<scrum.server.admin.User> scrumMasters) {
        scrumMasters = prepareScrumMasters(scrumMasters);
        if (scrumMasters == null) scrumMasters = Collections.emptyList();
        java.util.Set<String> ids = getIdsAsSet(scrumMasters);
        if (this.scrumMastersIds.equals(ids)) return;
        this.scrumMastersIds = ids;
        updateLastModified();
        fireModified("scrumMasters="+StrExtend.format(scrumMasters));
    }

    protected Collection<scrum.server.admin.User> prepareScrumMasters(Collection<scrum.server.admin.User> scrumMasters) {
        return scrumMasters;
    }

    protected void repairDeadScrumMasterReference(String entityId) {
        if (this.scrumMastersIds.remove(entityId)) fireModified("scrumMasters-=" + entityId);
    }

    public final boolean containsScrumMaster(scrum.server.admin.User scrumMaster) {
        if (scrumMaster == null) return false;
        return this.scrumMastersIds.contains(scrumMaster.getId());
    }

    public final int getScrumMastersCount() {
        return this.scrumMastersIds.size();
    }

    public final boolean isScrumMastersEmpty() {
        return this.scrumMastersIds.isEmpty();
    }

    public final boolean addScrumMaster(scrum.server.admin.User scrumMaster) {
        if (scrumMaster == null) throw new IllegalArgumentException("scrumMaster == null");
        boolean added = this.scrumMastersIds.add(scrumMaster.getId());
        if (added) updateLastModified();
        if (added) fireModified("scrumMasters+=" + scrumMaster);
        return added;
    }

    public final boolean addScrumMasters(Collection<scrum.server.admin.User> scrumMasters) {
        if (scrumMasters == null) throw new IllegalArgumentException("scrumMasters == null");
        boolean added = false;
        for (scrum.server.admin.User scrumMaster : scrumMasters) {
            added = added | this.scrumMastersIds.add(scrumMaster.getId());
        }
        return added;
    }

    public final boolean removeScrumMaster(scrum.server.admin.User scrumMaster) {
        if (scrumMaster == null) throw new IllegalArgumentException("scrumMaster == null");
        if (this.scrumMastersIds == null) return false;
        boolean removed = this.scrumMastersIds.remove(scrumMaster.getId());
        if (removed) updateLastModified();
        if (removed) fireModified("scrumMasters-=" + scrumMaster);
        return removed;
    }

    public final boolean removeScrumMasters(Collection<scrum.server.admin.User> scrumMasters) {
        if (scrumMasters == null) return false;
        if (scrumMasters.isEmpty()) return false;
        boolean removed = false;
        for (scrum.server.admin.User _element: scrumMasters) {
            removed = removed | removeScrumMaster(_element);
        }
        return removed;
    }

    public final boolean clearScrumMasters() {
        if (this.scrumMastersIds.isEmpty()) return false;
        this.scrumMastersIds.clear();
        updateLastModified();
        fireModified("scrumMasters cleared");
        return true;
    }

    protected final void updateScrumMasters(Object value) {
        Collection<String> ids = (Collection<String>) value;
        setScrumMasters((java.util.Set) userDao.getByIdsAsSet(ids));
    }

    // -----------------------------------------------------------
    // - teamMembers
    // -----------------------------------------------------------

    private java.util.Set<String> teamMembersIds = new java.util.HashSet<String>();

    public final java.util.Set<scrum.server.admin.User> getTeamMembers() {
        return (java.util.Set) userDao.getByIdsAsSet(this.teamMembersIds);
    }

    public final void setTeamMembers(Collection<scrum.server.admin.User> teamMembers) {
        teamMembers = prepareTeamMembers(teamMembers);
        if (teamMembers == null) teamMembers = Collections.emptyList();
        java.util.Set<String> ids = getIdsAsSet(teamMembers);
        if (this.teamMembersIds.equals(ids)) return;
        this.teamMembersIds = ids;
        updateLastModified();
        fireModified("teamMembers="+StrExtend.format(teamMembers));
    }

    protected Collection<scrum.server.admin.User> prepareTeamMembers(Collection<scrum.server.admin.User> teamMembers) {
        return teamMembers;
    }

    protected void repairDeadTeamMemberReference(String entityId) {
        if (this.teamMembersIds.remove(entityId)) fireModified("teamMembers-=" + entityId);
    }

    public final boolean containsTeamMember(scrum.server.admin.User teamMember) {
        if (teamMember == null) return false;
        return this.teamMembersIds.contains(teamMember.getId());
    }

    public final int getTeamMembersCount() {
        return this.teamMembersIds.size();
    }

    public final boolean isTeamMembersEmpty() {
        return this.teamMembersIds.isEmpty();
    }

    public final boolean addTeamMember(scrum.server.admin.User teamMember) {
        if (teamMember == null) throw new IllegalArgumentException("teamMember == null");
        boolean added = this.teamMembersIds.add(teamMember.getId());
        if (added) updateLastModified();
        if (added) fireModified("teamMembers+=" + teamMember);
        return added;
    }

    public final boolean addTeamMembers(Collection<scrum.server.admin.User> teamMembers) {
        if (teamMembers == null) throw new IllegalArgumentException("teamMembers == null");
        boolean added = false;
        for (scrum.server.admin.User teamMember : teamMembers) {
            added = added | this.teamMembersIds.add(teamMember.getId());
        }
        return added;
    }

    public final boolean removeTeamMember(scrum.server.admin.User teamMember) {
        if (teamMember == null) throw new IllegalArgumentException("teamMember == null");
        if (this.teamMembersIds == null) return false;
        boolean removed = this.teamMembersIds.remove(teamMember.getId());
        if (removed) updateLastModified();
        if (removed) fireModified("teamMembers-=" + teamMember);
        return removed;
    }

    public final boolean removeTeamMembers(Collection<scrum.server.admin.User> teamMembers) {
        if (teamMembers == null) return false;
        if (teamMembers.isEmpty()) return false;
        boolean removed = false;
        for (scrum.server.admin.User _element: teamMembers) {
            removed = removed | removeTeamMember(_element);
        }
        return removed;
    }

    public final boolean clearTeamMembers() {
        if (this.teamMembersIds.isEmpty()) return false;
        this.teamMembersIds.clear();
        updateLastModified();
        fireModified("teamMembers cleared");
        return true;
    }

    protected final void updateTeamMembers(Object value) {
        Collection<String> ids = (Collection<String>) value;
        setTeamMembers((java.util.Set) userDao.getByIdsAsSet(ids));
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("number")) updateNumber(value);
            if (property.equals("projectId")) updateProject(value);
            if (property.equals("label")) updateLabel(value);
            if (property.equals("goal")) updateGoal(value);
            if (property.equals("begin")) updateBegin(value);
            if (property.equals("end")) updateEnd(value);
            if (property.equals("velocity")) updateVelocity(value);
            if (property.equals("completedRequirementsData")) updateCompletedRequirementsData(value);
            if (property.equals("incompletedRequirementsData")) updateIncompletedRequirementsData(value);
            if (property.equals("planningNote")) updatePlanningNote(value);
            if (property.equals("reviewNote")) updateReviewNote(value);
            if (property.equals("retrospectiveNote")) updateRetrospectiveNote(value);
            if (property.equals("requirementsOrderIds")) updateRequirementsOrderIds(value);
            if (property.equals("productOwnersIds")) updateProductOwners(value);
            if (property.equals("scrumMastersIds")) updateScrumMasters(value);
            if (property.equals("teamMembersIds")) updateTeamMembers(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadProjectReference(entityId);
        if (this.requirementsOrderIds == null) this.requirementsOrderIds = new java.util.ArrayList<java.lang.String>();
        if (this.productOwnersIds == null) this.productOwnersIds = new java.util.HashSet<String>();
        repairDeadProductOwnerReference(entityId);
        if (this.scrumMastersIds == null) this.scrumMastersIds = new java.util.HashSet<String>();
        repairDeadScrumMasterReference(entityId);
        if (this.teamMembersIds == null) this.teamMembersIds = new java.util.HashSet<String>();
        repairDeadTeamMemberReference(entityId);
    }

    // --- ensure integrity ---

    public void ensureIntegrity() {
        super.ensureIntegrity();
        if (!isProjectSet()) {
            repairMissingMaster();
            return;
        }
        try {
            getProject();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead project reference");
            repairDeadProjectReference(this.projectId);
        }
        if (this.requirementsOrderIds == null) this.requirementsOrderIds = new java.util.ArrayList<java.lang.String>();
        if (this.productOwnersIds == null) this.productOwnersIds = new java.util.HashSet<String>();
        Set<String> productOwners = new HashSet<String>(this.productOwnersIds);
        for (String entityId : productOwners) {
            try {
                userDao.getById(entityId);
            } catch (EntityDoesNotExistException ex) {
                LOG.info("Repairing dead productOwner reference");
                repairDeadProductOwnerReference(entityId);
            }
        }
        if (this.scrumMastersIds == null) this.scrumMastersIds = new java.util.HashSet<String>();
        Set<String> scrumMasters = new HashSet<String>(this.scrumMastersIds);
        for (String entityId : scrumMasters) {
            try {
                userDao.getById(entityId);
            } catch (EntityDoesNotExistException ex) {
                LOG.info("Repairing dead scrumMaster reference");
                repairDeadScrumMasterReference(entityId);
            }
        }
        if (this.teamMembersIds == null) this.teamMembersIds = new java.util.HashSet<String>();
        Set<String> teamMembers = new HashSet<String>(this.teamMembersIds);
        for (String entityId : teamMembers) {
            try {
                userDao.getById(entityId);
            } catch (EntityDoesNotExistException ex) {
                LOG.info("Repairing dead teamMember reference");
                repairDeadTeamMemberReference(entityId);
            }
        }
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    static scrum.server.project.ProjectDao projectDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectDao(scrum.server.project.ProjectDao projectDao) {
        GSprint.projectDao = projectDao;
    }

    static scrum.server.sprint.SprintDao sprintDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSprintDao(scrum.server.sprint.SprintDao sprintDao) {
        GSprint.sprintDao = sprintDao;
    }

    static scrum.server.sprint.SprintDaySnapshotDao sprintDaySnapshotDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSprintDaySnapshotDao(scrum.server.sprint.SprintDaySnapshotDao sprintDaySnapshotDao) {
        GSprint.sprintDaySnapshotDao = sprintDaySnapshotDao;
    }

    static scrum.server.sprint.SprintReportDao sprintReportDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSprintReportDao(scrum.server.sprint.SprintReportDao sprintReportDao) {
        GSprint.sprintReportDao = sprintReportDao;
    }

    static scrum.server.project.RequirementDao requirementDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setRequirementDao(scrum.server.project.RequirementDao requirementDao) {
        GSprint.requirementDao = requirementDao;
    }

    static scrum.server.release.ReleaseDao releaseDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setReleaseDao(scrum.server.release.ReleaseDao releaseDao) {
        GSprint.releaseDao = releaseDao;
    }

    static scrum.server.sprint.TaskDao taskDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setTaskDao(scrum.server.sprint.TaskDao taskDao) {
        GSprint.taskDao = taskDao;
    }

    static scrum.server.project.ProjectSprintSnapshotDao projectSprintSnapshotDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectSprintSnapshotDao(scrum.server.project.ProjectSprintSnapshotDao projectSprintSnapshotDao) {
        GSprint.projectSprintSnapshotDao = projectSprintSnapshotDao;
    }

}