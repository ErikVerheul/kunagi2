// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.EntityGenerator










package scrum.server.issues;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.ADatob;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.AStructure;
import ilarkesto.auth.AUser;
import ilarkesto.persistence.EntityDoesNotExistException;
import ilarkesto.base.StrExtend;

public abstract class GIssue
            extends AEntity
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, ilarkesto.search.Searchable, java.lang.Comparable<Issue> {

    // --- AEntity ---

    public final scrum.server.issues.IssueDao getDao() {
        return issueDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("projectId", this.projectId);
        properties.put("storyId", this.storyId);
        properties.put("number", this.number);
        properties.put("type", this.type);
        properties.put("date", this.date == null ? null : this.date.toString());
        properties.put("creatorId", this.creatorId);
        properties.put("label", this.label);
        properties.put("description", this.description);
        properties.put("statement", this.statement);
        properties.put("issuerName", this.issuerName);
        properties.put("issuerEmail", this.issuerEmail);
        properties.put("acceptDate", this.acceptDate == null ? null : this.acceptDate.toString());
        properties.put("urgent", this.urgent);
        properties.put("severity", this.severity);
        properties.put("ownerId", this.ownerId);
        properties.put("fixDate", this.fixDate == null ? null : this.fixDate.toString());
        properties.put("closeDate", this.closeDate == null ? null : this.closeDate.toString());
        properties.put("suspendedUntilDate", this.suspendedUntilDate == null ? null : this.suspendedUntilDate.toString());
        properties.put("affectedReleasesIds", this.affectedReleasesIds);
        properties.put("fixReleasesIds", this.fixReleasesIds);
        properties.put("published", this.published);
        properties.put("themes", this.themes);
    }

    public int compareTo(Issue other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    public final java.util.Set<scrum.server.project.Requirement> getRequirements() {
        return requirementDao.getRequirementsByIssue((Issue)this);
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GIssue.class);

    public static final String TYPE = "issue";


    // -----------------------------------------------------------
    // - Searchable
    // -----------------------------------------------------------

    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getDescription(), key)) return true;
        if (matchesKey(getStatement(), key)) return true;
        return false;
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
    // - story
    // -----------------------------------------------------------

    private String storyId;
    private transient scrum.server.project.Requirement storyCache;

    private void updateStoryCache() {
        storyCache = this.storyId == null ? null : (scrum.server.project.Requirement)requirementDao.getById(this.storyId);
    }

    public final String getStoryId() {
        return this.storyId;
    }

    public final scrum.server.project.Requirement getStory() {
        if (storyCache == null) updateStoryCache();
        return storyCache;
    }

    public final void setStory(scrum.server.project.Requirement story) {
        story = prepareStory(story);
        if (isStory(story)) return;
        this.storyId = story == null ? null : story.getId();
        storyCache = story;
        updateLastModified();
        fireModified("story="+story);
    }

    protected scrum.server.project.Requirement prepareStory(scrum.server.project.Requirement story) {
        return story;
    }

    protected void repairDeadStoryReference(String entityId) {
        if (this.storyId == null || entityId.equals(this.storyId)) {
            setStory(null);
        }
    }

    public final boolean isStorySet() {
        return this.storyId != null;
    }

    public final boolean isStory(scrum.server.project.Requirement story) {
        if (this.storyId == null && story == null) return true;
        return story != null && story.getId().equals(this.storyId);
    }

    protected final void updateStory(Object value) {
        setStory(value == null ? null : (scrum.server.project.Requirement)requirementDao.getById((String)value));
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
    // - type
    // -----------------------------------------------------------

    private java.lang.String type;

    public final java.lang.String getType() {
        return type;
    }

    public final void setType(java.lang.String type) {
        type = prepareType(type);
        if (isType(type)) return;
        this.type = type;
        updateLastModified();
        fireModified("type="+type);
    }

    protected java.lang.String prepareType(java.lang.String type) {
        // type = StrExtend.removeUnreadableChars(type);
        return type;
    }

    public final boolean isTypeSet() {
        return this.type != null;
    }

    public final boolean isType(java.lang.String type) {
        if (this.type == null && type == null) return true;
        return this.type != null && this.type.equals(type);
    }

    protected final void updateType(Object value) {
        setType((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - date
    // -----------------------------------------------------------

    private ilarkesto.core.time.DateAndTime date;

    public final ilarkesto.core.time.DateAndTime getDate() {
        return date;
    }

    public final void setDate(ilarkesto.core.time.DateAndTime date) {
        date = prepareDate(date);
        if (isDate(date)) return;
        this.date = date;
        updateLastModified();
        fireModified("date="+date);
    }

    protected ilarkesto.core.time.DateAndTime prepareDate(ilarkesto.core.time.DateAndTime date) {
        return date;
    }

    public final boolean isDateSet() {
        return this.date != null;
    }

    public final boolean isDate(ilarkesto.core.time.DateAndTime date) {
        if (this.date == null && date == null) return true;
        return this.date != null && this.date.equals(date);
    }

    protected final void updateDate(Object value) {
        value = value == null ? null : new ilarkesto.core.time.DateAndTime((String)value);
        setDate((ilarkesto.core.time.DateAndTime)value);
    }

    // -----------------------------------------------------------
    // - creator
    // -----------------------------------------------------------

    private String creatorId;
    private transient scrum.server.admin.User creatorCache;

    private void updateCreatorCache() {
        creatorCache = this.creatorId == null ? null : (scrum.server.admin.User)userDao.getById(this.creatorId);
    }

    public final String getCreatorId() {
        return this.creatorId;
    }

    public final scrum.server.admin.User getCreator() {
        if (creatorCache == null) updateCreatorCache();
        return creatorCache;
    }

    public final void setCreator(scrum.server.admin.User creator) {
        creator = prepareCreator(creator);
        if (isCreator(creator)) return;
        this.creatorId = creator == null ? null : creator.getId();
        creatorCache = creator;
        updateLastModified();
        fireModified("creator="+creator);
    }

    protected scrum.server.admin.User prepareCreator(scrum.server.admin.User creator) {
        return creator;
    }

    protected void repairDeadCreatorReference(String entityId) {
        if (this.creatorId == null || entityId.equals(this.creatorId)) {
            setCreator(null);
        }
    }

    public final boolean isCreatorSet() {
        return this.creatorId != null;
    }

    public final boolean isCreator(scrum.server.admin.User creator) {
        if (this.creatorId == null && creator == null) return true;
        return creator != null && creator.getId().equals(this.creatorId);
    }

    protected final void updateCreator(Object value) {
        setCreator(value == null ? null : (scrum.server.admin.User)userDao.getById((String)value));
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
    // - statement
    // -----------------------------------------------------------

    private java.lang.String statement;

    public final java.lang.String getStatement() {
        return statement;
    }

    public final void setStatement(java.lang.String statement) {
        statement = prepareStatement(statement);
        if (isStatement(statement)) return;
        this.statement = statement;
        updateLastModified();
        fireModified("statement="+statement);
    }

    protected java.lang.String prepareStatement(java.lang.String statement) {
        // statement = StrExtend.removeUnreadableChars(statement);
        return statement;
    }

    public final boolean isStatementSet() {
        return this.statement != null;
    }

    public final boolean isStatement(java.lang.String statement) {
        if (this.statement == null && statement == null) return true;
        return this.statement != null && this.statement.equals(statement);
    }

    protected final void updateStatement(Object value) {
        setStatement((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - issuerName
    // -----------------------------------------------------------

    private java.lang.String issuerName;

    public final java.lang.String getIssuerName() {
        return issuerName;
    }

    public final void setIssuerName(java.lang.String issuerName) {
        issuerName = prepareIssuerName(issuerName);
        if (isIssuerName(issuerName)) return;
        this.issuerName = issuerName;
        updateLastModified();
        fireModified("issuerName="+issuerName);
    }

    protected java.lang.String prepareIssuerName(java.lang.String issuerName) {
        // issuerName = StrExtend.removeUnreadableChars(issuerName);
        return issuerName;
    }

    public final boolean isIssuerNameSet() {
        return this.issuerName != null;
    }

    public final boolean isIssuerName(java.lang.String issuerName) {
        if (this.issuerName == null && issuerName == null) return true;
        return this.issuerName != null && this.issuerName.equals(issuerName);
    }

    protected final void updateIssuerName(Object value) {
        setIssuerName((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - issuerEmail
    // -----------------------------------------------------------

    private java.lang.String issuerEmail;

    public final java.lang.String getIssuerEmail() {
        return issuerEmail;
    }

    public final void setIssuerEmail(java.lang.String issuerEmail) {
        issuerEmail = prepareIssuerEmail(issuerEmail);
        if (isIssuerEmail(issuerEmail)) return;
        this.issuerEmail = issuerEmail;
        updateLastModified();
        fireModified("issuerEmail="+issuerEmail);
    }

    protected java.lang.String prepareIssuerEmail(java.lang.String issuerEmail) {
        // issuerEmail = StrExtend.removeUnreadableChars(issuerEmail);
        return issuerEmail;
    }

    public final boolean isIssuerEmailSet() {
        return this.issuerEmail != null;
    }

    public final boolean isIssuerEmail(java.lang.String issuerEmail) {
        if (this.issuerEmail == null && issuerEmail == null) return true;
        return this.issuerEmail != null && this.issuerEmail.equals(issuerEmail);
    }

    protected final void updateIssuerEmail(Object value) {
        setIssuerEmail((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - acceptDate
    // -----------------------------------------------------------

    private ilarkesto.core.time.Date acceptDate;

    public final ilarkesto.core.time.Date getAcceptDate() {
        return acceptDate;
    }

    public final void setAcceptDate(ilarkesto.core.time.Date acceptDate) {
        acceptDate = prepareAcceptDate(acceptDate);
        if (isAcceptDate(acceptDate)) return;
        this.acceptDate = acceptDate;
        updateLastModified();
        fireModified("acceptDate="+acceptDate);
    }

    protected ilarkesto.core.time.Date prepareAcceptDate(ilarkesto.core.time.Date acceptDate) {
        return acceptDate;
    }

    public final boolean isAcceptDateSet() {
        return this.acceptDate != null;
    }

    public final boolean isAcceptDate(ilarkesto.core.time.Date acceptDate) {
        if (this.acceptDate == null && acceptDate == null) return true;
        return this.acceptDate != null && this.acceptDate.equals(acceptDate);
    }

    protected final void updateAcceptDate(Object value) {
        value = value == null ? null : new ilarkesto.core.time.Date((String)value);
        setAcceptDate((ilarkesto.core.time.Date)value);
    }

    // -----------------------------------------------------------
    // - urgent
    // -----------------------------------------------------------

    private boolean urgent;

    public final boolean isUrgent() {
        return urgent;
    }

    public final void setUrgent(boolean urgent) {
        urgent = prepareUrgent(urgent);
        if (isUrgent(urgent)) return;
        this.urgent = urgent;
        updateLastModified();
        fireModified("urgent="+urgent);
    }

    protected boolean prepareUrgent(boolean urgent) {
        return urgent;
    }

    public final boolean isUrgent(boolean urgent) {
        return this.urgent == urgent;
    }

    protected final void updateUrgent(Object value) {
        setUrgent((Boolean)value);
    }

    // -----------------------------------------------------------
    // - severity
    // -----------------------------------------------------------

    private int severity;

    public final int getSeverity() {
        return severity;
    }

    public final void setSeverity(int severity) {
        severity = prepareSeverity(severity);
        if (isSeverity(severity)) return;
        this.severity = severity;
        updateLastModified();
        fireModified("severity="+severity);
    }

    protected int prepareSeverity(int severity) {
        return severity;
    }

    public final boolean isSeverity(int severity) {
        return this.severity == severity;
    }

    protected final void updateSeverity(Object value) {
        setSeverity((Integer)value);
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
    // - fixDate
    // -----------------------------------------------------------

    private ilarkesto.core.time.Date fixDate;

    public final ilarkesto.core.time.Date getFixDate() {
        return fixDate;
    }

    public final void setFixDate(ilarkesto.core.time.Date fixDate) {
        fixDate = prepareFixDate(fixDate);
        if (isFixDate(fixDate)) return;
        this.fixDate = fixDate;
        updateLastModified();
        fireModified("fixDate="+fixDate);
    }

    protected ilarkesto.core.time.Date prepareFixDate(ilarkesto.core.time.Date fixDate) {
        return fixDate;
    }

    public final boolean isFixDateSet() {
        return this.fixDate != null;
    }

    public final boolean isFixDate(ilarkesto.core.time.Date fixDate) {
        if (this.fixDate == null && fixDate == null) return true;
        return this.fixDate != null && this.fixDate.equals(fixDate);
    }

    protected final void updateFixDate(Object value) {
        value = value == null ? null : new ilarkesto.core.time.Date((String)value);
        setFixDate((ilarkesto.core.time.Date)value);
    }

    // -----------------------------------------------------------
    // - closeDate
    // -----------------------------------------------------------

    private ilarkesto.core.time.Date closeDate;

    public final ilarkesto.core.time.Date getCloseDate() {
        return closeDate;
    }

    public final void setCloseDate(ilarkesto.core.time.Date closeDate) {
        closeDate = prepareCloseDate(closeDate);
        if (isCloseDate(closeDate)) return;
        this.closeDate = closeDate;
        updateLastModified();
        fireModified("closeDate="+closeDate);
    }

    protected ilarkesto.core.time.Date prepareCloseDate(ilarkesto.core.time.Date closeDate) {
        return closeDate;
    }

    public final boolean isCloseDateSet() {
        return this.closeDate != null;
    }

    public final boolean isCloseDate(ilarkesto.core.time.Date closeDate) {
        if (this.closeDate == null && closeDate == null) return true;
        return this.closeDate != null && this.closeDate.equals(closeDate);
    }

    protected final void updateCloseDate(Object value) {
        value = value == null ? null : new ilarkesto.core.time.Date((String)value);
        setCloseDate((ilarkesto.core.time.Date)value);
    }

    // -----------------------------------------------------------
    // - suspendedUntilDate
    // -----------------------------------------------------------

    private ilarkesto.core.time.Date suspendedUntilDate;

    public final ilarkesto.core.time.Date getSuspendedUntilDate() {
        return suspendedUntilDate;
    }

    public final void setSuspendedUntilDate(ilarkesto.core.time.Date suspendedUntilDate) {
        suspendedUntilDate = prepareSuspendedUntilDate(suspendedUntilDate);
        if (isSuspendedUntilDate(suspendedUntilDate)) return;
        this.suspendedUntilDate = suspendedUntilDate;
        updateLastModified();
        fireModified("suspendedUntilDate="+suspendedUntilDate);
    }

    protected ilarkesto.core.time.Date prepareSuspendedUntilDate(ilarkesto.core.time.Date suspendedUntilDate) {
        return suspendedUntilDate;
    }

    public final boolean isSuspendedUntilDateSet() {
        return this.suspendedUntilDate != null;
    }

    public final boolean isSuspendedUntilDate(ilarkesto.core.time.Date suspendedUntilDate) {
        if (this.suspendedUntilDate == null && suspendedUntilDate == null) return true;
        return this.suspendedUntilDate != null && this.suspendedUntilDate.equals(suspendedUntilDate);
    }

    protected final void updateSuspendedUntilDate(Object value) {
        value = value == null ? null : new ilarkesto.core.time.Date((String)value);
        setSuspendedUntilDate((ilarkesto.core.time.Date)value);
    }

    // -----------------------------------------------------------
    // - affectedReleases
    // -----------------------------------------------------------

    private java.util.Set<String> affectedReleasesIds = new java.util.HashSet<String>();

    public final java.util.Set<scrum.server.release.Release> getAffectedReleases() {
        return (java.util.Set) releaseDao.getByIdsAsSet(this.affectedReleasesIds);
    }

    public final void setAffectedReleases(Collection<scrum.server.release.Release> affectedReleases) {
        affectedReleases = prepareAffectedReleases(affectedReleases);
        if (affectedReleases == null) affectedReleases = Collections.emptyList();
        java.util.Set<String> ids = getIdsAsSet(affectedReleases);
        if (this.affectedReleasesIds.equals(ids)) return;
        this.affectedReleasesIds = ids;
        updateLastModified();
        fireModified("affectedReleases="+StrExtend.format(affectedReleases));
    }

    protected Collection<scrum.server.release.Release> prepareAffectedReleases(Collection<scrum.server.release.Release> affectedReleases) {
        return affectedReleases;
    }

    protected void repairDeadAffectedReleaseReference(String entityId) {
        if (this.affectedReleasesIds.remove(entityId)) fireModified("affectedReleases-=" + entityId);
    }

    public final boolean containsAffectedRelease(scrum.server.release.Release affectedRelease) {
        if (affectedRelease == null) return false;
        return this.affectedReleasesIds.contains(affectedRelease.getId());
    }

    public final int getAffectedReleasesCount() {
        return this.affectedReleasesIds.size();
    }

    public final boolean isAffectedReleasesEmpty() {
        return this.affectedReleasesIds.isEmpty();
    }

    public final boolean addAffectedRelease(scrum.server.release.Release affectedRelease) {
        if (affectedRelease == null) throw new IllegalArgumentException("affectedRelease == null");
        boolean added = this.affectedReleasesIds.add(affectedRelease.getId());
        if (added) updateLastModified();
        if (added) fireModified("affectedReleases+=" + affectedRelease);
        return added;
    }

    public final boolean addAffectedReleases(Collection<scrum.server.release.Release> affectedReleases) {
        if (affectedReleases == null) throw new IllegalArgumentException("affectedReleases == null");
        boolean added = false;
        for (scrum.server.release.Release affectedRelease : affectedReleases) {
            added = added | this.affectedReleasesIds.add(affectedRelease.getId());
        }
        return added;
    }

    public final boolean removeAffectedRelease(scrum.server.release.Release affectedRelease) {
        if (affectedRelease == null) throw new IllegalArgumentException("affectedRelease == null");
        if (this.affectedReleasesIds == null) return false;
        boolean removed = this.affectedReleasesIds.remove(affectedRelease.getId());
        if (removed) updateLastModified();
        if (removed) fireModified("affectedReleases-=" + affectedRelease);
        return removed;
    }

    public final boolean removeAffectedReleases(Collection<scrum.server.release.Release> affectedReleases) {
        if (affectedReleases == null) return false;
        if (affectedReleases.isEmpty()) return false;
        boolean removed = false;
        for (scrum.server.release.Release _element: affectedReleases) {
            removed = removed | removeAffectedRelease(_element);
        }
        return removed;
    }

    public final boolean clearAffectedReleases() {
        if (this.affectedReleasesIds.isEmpty()) return false;
        this.affectedReleasesIds.clear();
        updateLastModified();
        fireModified("affectedReleases cleared");
        return true;
    }

    protected final void updateAffectedReleases(Object value) {
        Collection<String> ids = (Collection<String>) value;
        setAffectedReleases((java.util.Set) releaseDao.getByIdsAsSet(ids));
    }

    // -----------------------------------------------------------
    // - fixReleases
    // -----------------------------------------------------------

    private java.util.Set<String> fixReleasesIds = new java.util.HashSet<String>();

    public final java.util.Set<scrum.server.release.Release> getFixReleases() {
        return (java.util.Set) releaseDao.getByIdsAsSet(this.fixReleasesIds);
    }

    public final void setFixReleases(Collection<scrum.server.release.Release> fixReleases) {
        fixReleases = prepareFixReleases(fixReleases);
        if (fixReleases == null) fixReleases = Collections.emptyList();
        java.util.Set<String> ids = getIdsAsSet(fixReleases);
        if (this.fixReleasesIds.equals(ids)) return;
        this.fixReleasesIds = ids;
        updateLastModified();
        fireModified("fixReleases="+StrExtend.format(fixReleases));
    }

    protected Collection<scrum.server.release.Release> prepareFixReleases(Collection<scrum.server.release.Release> fixReleases) {
        return fixReleases;
    }

    protected void repairDeadFixReleaseReference(String entityId) {
        if (this.fixReleasesIds.remove(entityId)) fireModified("fixReleases-=" + entityId);
    }

    public final boolean containsFixRelease(scrum.server.release.Release fixRelease) {
        if (fixRelease == null) return false;
        return this.fixReleasesIds.contains(fixRelease.getId());
    }

    public final int getFixReleasesCount() {
        return this.fixReleasesIds.size();
    }

    public final boolean isFixReleasesEmpty() {
        return this.fixReleasesIds.isEmpty();
    }

    public final boolean addFixRelease(scrum.server.release.Release fixRelease) {
        if (fixRelease == null) throw new IllegalArgumentException("fixRelease == null");
        boolean added = this.fixReleasesIds.add(fixRelease.getId());
        if (added) updateLastModified();
        if (added) fireModified("fixReleases+=" + fixRelease);
        return added;
    }

    public final boolean addFixReleases(Collection<scrum.server.release.Release> fixReleases) {
        if (fixReleases == null) throw new IllegalArgumentException("fixReleases == null");
        boolean added = false;
        for (scrum.server.release.Release fixRelease : fixReleases) {
            added = added | this.fixReleasesIds.add(fixRelease.getId());
        }
        return added;
    }

    public final boolean removeFixRelease(scrum.server.release.Release fixRelease) {
        if (fixRelease == null) throw new IllegalArgumentException("fixRelease == null");
        if (this.fixReleasesIds == null) return false;
        boolean removed = this.fixReleasesIds.remove(fixRelease.getId());
        if (removed) updateLastModified();
        if (removed) fireModified("fixReleases-=" + fixRelease);
        return removed;
    }

    public final boolean removeFixReleases(Collection<scrum.server.release.Release> fixReleases) {
        if (fixReleases == null) return false;
        if (fixReleases.isEmpty()) return false;
        boolean removed = false;
        for (scrum.server.release.Release _element: fixReleases) {
            removed = removed | removeFixRelease(_element);
        }
        return removed;
    }

    public final boolean clearFixReleases() {
        if (this.fixReleasesIds.isEmpty()) return false;
        this.fixReleasesIds.clear();
        updateLastModified();
        fireModified("fixReleases cleared");
        return true;
    }

    protected final void updateFixReleases(Object value) {
        Collection<String> ids = (Collection<String>) value;
        setFixReleases((java.util.Set) releaseDao.getByIdsAsSet(ids));
    }

    // -----------------------------------------------------------
    // - published
    // -----------------------------------------------------------

    private boolean published;

    public final boolean isPublished() {
        return published;
    }

    public final void setPublished(boolean published) {
        published = preparePublished(published);
        if (isPublished(published)) return;
        this.published = published;
        updateLastModified();
        fireModified("published="+published);
    }

    protected boolean preparePublished(boolean published) {
        return published;
    }

    public final boolean isPublished(boolean published) {
        return this.published == published;
    }

    protected final void updatePublished(Object value) {
        setPublished((Boolean)value);
    }

    // -----------------------------------------------------------
    // - themes
    // -----------------------------------------------------------

    private java.util.List<java.lang.String> themes = new java.util.ArrayList<java.lang.String>();

    public final java.util.List<java.lang.String> getThemes() {
        return new java.util.ArrayList<java.lang.String>(themes);
    }

    public final void setThemes(Collection<java.lang.String> themes) {
        themes = prepareThemes(themes);
        if (themes == null) themes = Collections.emptyList();
        if (this.themes.equals(themes)) return;
        this.themes = new java.util.ArrayList<java.lang.String>(themes);
        updateLastModified();
        fireModified("themes="+StrExtend.format(themes));
    }

    protected Collection<java.lang.String> prepareThemes(Collection<java.lang.String> themes) {
        return themes;
    }

    public final boolean containsTheme(java.lang.String theme) {
        if (theme == null) return false;
        return this.themes.contains(theme);
    }

    public final int getThemesCount() {
        return this.themes.size();
    }

    public final boolean isThemesEmpty() {
        return this.themes.isEmpty();
    }

    public final boolean addTheme(java.lang.String theme) {
        if (theme == null) throw new IllegalArgumentException("theme == null");
        boolean added = this.themes.add(theme);
        if (added) updateLastModified();
        if (added) fireModified("themes+=" + theme);
        return added;
    }

    public final boolean addThemes(Collection<java.lang.String> themes) {
        if (themes == null) throw new IllegalArgumentException("themes == null");
        boolean added = false;
        for (java.lang.String theme : themes) {
            added = added | this.themes.add(theme);
        }
        return added;
    }

    public final boolean removeTheme(java.lang.String theme) {
        if (theme == null) throw new IllegalArgumentException("theme == null");
        if (this.themes == null) return false;
        boolean removed = this.themes.remove(theme);
        if (removed) updateLastModified();
        if (removed) fireModified("themes-=" + theme);
        return removed;
    }

    public final boolean removeThemes(Collection<java.lang.String> themes) {
        if (themes == null) return false;
        if (themes.isEmpty()) return false;
        boolean removed = false;
        for (java.lang.String _element: themes) {
            removed = removed | removeTheme(_element);
        }
        return removed;
    }

    public final boolean clearThemes() {
        if (this.themes.isEmpty()) return false;
        this.themes.clear();
        updateLastModified();
        fireModified("themes cleared");
        return true;
    }

    public final String getThemesAsCommaSeparatedString() {
        if (this.themes.isEmpty()) return null;
        return StrExtend.concat(this.themes,", ");
    }

    public final void setThemesAsCommaSeparatedString(String themes) {
        setThemes(StrExtend.parseCommaSeparatedString(themes));
    }

    protected final void updateThemes(Object value) {
        setThemes((java.util.List<java.lang.String>) value);
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("projectId")) updateProject(value);
            if (property.equals("storyId")) updateStory(value);
            if (property.equals("number")) updateNumber(value);
            if (property.equals("type")) updateType(value);
            if (property.equals("date")) updateDate(value);
            if (property.equals("creatorId")) updateCreator(value);
            if (property.equals("label")) updateLabel(value);
            if (property.equals("description")) updateDescription(value);
            if (property.equals("statement")) updateStatement(value);
            if (property.equals("issuerName")) updateIssuerName(value);
            if (property.equals("issuerEmail")) updateIssuerEmail(value);
            if (property.equals("acceptDate")) updateAcceptDate(value);
            if (property.equals("urgent")) updateUrgent(value);
            if (property.equals("severity")) updateSeverity(value);
            if (property.equals("ownerId")) updateOwner(value);
            if (property.equals("fixDate")) updateFixDate(value);
            if (property.equals("closeDate")) updateCloseDate(value);
            if (property.equals("suspendedUntilDate")) updateSuspendedUntilDate(value);
            if (property.equals("affectedReleasesIds")) updateAffectedReleases(value);
            if (property.equals("fixReleasesIds")) updateFixReleases(value);
            if (property.equals("published")) updatePublished(value);
            if (property.equals("themes")) updateThemes(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadProjectReference(entityId);
        repairDeadStoryReference(entityId);
        repairDeadCreatorReference(entityId);
        repairDeadOwnerReference(entityId);
        if (this.affectedReleasesIds == null) this.affectedReleasesIds = new java.util.HashSet<String>();
        repairDeadAffectedReleaseReference(entityId);
        if (this.fixReleasesIds == null) this.fixReleasesIds = new java.util.HashSet<String>();
        repairDeadFixReleaseReference(entityId);
        if (this.themes == null) this.themes = new java.util.ArrayList<java.lang.String>();
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
        try {
            getStory();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead story reference");
            repairDeadStoryReference(this.storyId);
        }
        try {
            getCreator();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead creator reference");
            repairDeadCreatorReference(this.creatorId);
        }
        try {
            getOwner();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead owner reference");
            repairDeadOwnerReference(this.ownerId);
        }
        if (this.affectedReleasesIds == null) this.affectedReleasesIds = new java.util.HashSet<String>();
        Set<String> affectedReleases = new HashSet<String>(this.affectedReleasesIds);
        for (String entityId : affectedReleases) {
            try {
                releaseDao.getById(entityId);
            } catch (EntityDoesNotExistException ex) {
                LOG.info("Repairing dead affectedRelease reference");
                repairDeadAffectedReleaseReference(entityId);
            }
        }
        if (this.fixReleasesIds == null) this.fixReleasesIds = new java.util.HashSet<String>();
        Set<String> fixReleases = new HashSet<String>(this.fixReleasesIds);
        for (String entityId : fixReleases) {
            try {
                releaseDao.getById(entityId);
            } catch (EntityDoesNotExistException ex) {
                LOG.info("Repairing dead fixRelease reference");
                repairDeadFixReleaseReference(entityId);
            }
        }
        if (this.themes == null) this.themes = new java.util.ArrayList<java.lang.String>();
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    static scrum.server.project.ProjectDao projectDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectDao(scrum.server.project.ProjectDao projectDao) {
        GIssue.projectDao = projectDao;
    }

    static scrum.server.project.RequirementDao requirementDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setRequirementDao(scrum.server.project.RequirementDao requirementDao) {
        GIssue.requirementDao = requirementDao;
    }

    static scrum.server.release.ReleaseDao releaseDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setReleaseDao(scrum.server.release.ReleaseDao releaseDao) {
        GIssue.releaseDao = releaseDao;
    }

    static scrum.server.issues.IssueDao issueDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setIssueDao(scrum.server.issues.IssueDao issueDao) {
        GIssue.issueDao = issueDao;
    }

}