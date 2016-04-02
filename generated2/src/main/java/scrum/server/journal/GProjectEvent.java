// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.EntityGenerator










package scrum.server.journal;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.ADatob;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.AStructure;
import ilarkesto.auth.AUser;
import ilarkesto.persistence.EntityDoesNotExistException;
import ilarkesto.base.StrExtend;

public abstract class GProjectEvent
            extends AEntity
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, ilarkesto.search.Searchable, java.lang.Comparable<ProjectEvent> {

    // --- AEntity ---

    public final scrum.server.journal.ProjectEventDao getDao() {
        return projectEventDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("projectId", this.projectId);
        properties.put("label", this.label);
        properties.put("subjectId", this.subjectId);
        properties.put("dateAndTime", this.dateAndTime == null ? null : this.dateAndTime.toString());
    }

    public int compareTo(ProjectEvent other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GProjectEvent.class);

    public static final String TYPE = "projectEvent";


    // -----------------------------------------------------------
    // - Searchable
    // -----------------------------------------------------------

    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
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
    // - subject
    // -----------------------------------------------------------

    private String subjectId;
    private transient ilarkesto.persistence.AEntity subjectCache;

    private void updateSubjectCache() {
        subjectCache = this.subjectId == null ? null : (ilarkesto.persistence.AEntity)getDaoService().getById(this.subjectId);
    }

    public final String getSubjectId() {
        return this.subjectId;
    }

    public final ilarkesto.persistence.AEntity getSubject() {
        if (subjectCache == null) updateSubjectCache();
        return subjectCache;
    }

    public final void setSubject(ilarkesto.persistence.AEntity subject) {
        subject = prepareSubject(subject);
        if (isSubject(subject)) return;
        this.subjectId = subject == null ? null : subject.getId();
        subjectCache = subject;
        updateLastModified();
        fireModified("subject="+subject);
    }

    protected ilarkesto.persistence.AEntity prepareSubject(ilarkesto.persistence.AEntity subject) {
        return subject;
    }

    protected void repairDeadSubjectReference(String entityId) {
        if (this.subjectId == null || entityId.equals(this.subjectId)) {
            setSubject(null);
        }
    }

    public final boolean isSubjectSet() {
        return this.subjectId != null;
    }

    public final boolean isSubject(ilarkesto.persistence.AEntity subject) {
        if (this.subjectId == null && subject == null) return true;
        return subject != null && subject.getId().equals(this.subjectId);
    }

    protected final void updateSubject(Object value) {
        setSubject(value == null ? null : (ilarkesto.persistence.AEntity)getDaoService().getById((String)value));
    }

    // -----------------------------------------------------------
    // - dateAndTime
    // -----------------------------------------------------------

    private ilarkesto.core.time.DateAndTime dateAndTime;

    public final ilarkesto.core.time.DateAndTime getDateAndTime() {
        return dateAndTime;
    }

    public final void setDateAndTime(ilarkesto.core.time.DateAndTime dateAndTime) {
        dateAndTime = prepareDateAndTime(dateAndTime);
        if (isDateAndTime(dateAndTime)) return;
        this.dateAndTime = dateAndTime;
        updateLastModified();
        fireModified("dateAndTime="+dateAndTime);
    }

    protected ilarkesto.core.time.DateAndTime prepareDateAndTime(ilarkesto.core.time.DateAndTime dateAndTime) {
        return dateAndTime;
    }

    public final boolean isDateAndTimeSet() {
        return this.dateAndTime != null;
    }

    public final boolean isDateAndTime(ilarkesto.core.time.DateAndTime dateAndTime) {
        if (this.dateAndTime == null && dateAndTime == null) return true;
        return this.dateAndTime != null && this.dateAndTime.equals(dateAndTime);
    }

    protected final void updateDateAndTime(Object value) {
        value = value == null ? null : new ilarkesto.core.time.DateAndTime((String)value);
        setDateAndTime((ilarkesto.core.time.DateAndTime)value);
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("projectId")) updateProject(value);
            if (property.equals("label")) updateLabel(value);
            if (property.equals("subjectId")) updateSubject(value);
            if (property.equals("dateAndTime")) updateDateAndTime(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadProjectReference(entityId);
        repairDeadSubjectReference(entityId);
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
            getSubject();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead subject reference");
            repairDeadSubjectReference(this.subjectId);
        }
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    static scrum.server.project.ProjectDao projectDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectDao(scrum.server.project.ProjectDao projectDao) {
        GProjectEvent.projectDao = projectDao;
    }

    static scrum.server.journal.ProjectEventDao projectEventDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectEventDao(scrum.server.journal.ProjectEventDao projectEventDao) {
        GProjectEvent.projectEventDao = projectEventDao;
    }

}