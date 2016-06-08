// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.EntityGenerator










package scrum.server.files;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.ADatob;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.AStructure;
import ilarkesto.auth.AUser;
import ilarkesto.persistence.EntityDoesNotExistException;
import ilarkesto.base.StrExtend;

public abstract class GFile
            extends AEntity
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, ilarkesto.search.Searchable, java.lang.Comparable<File> {

    // --- AEntity ---

    public final scrum.server.files.FileDao getDao() {
        return fileDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("projectId", this.projectId);
        properties.put("filename", this.filename);
        properties.put("uploadTime", this.uploadTime == null ? null : this.uploadTime.toString());
        properties.put("label", this.label);
        properties.put("number", this.number);
        properties.put("note", this.note);
    }

    public int compareTo(File other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GFile.class);

    public static final String TYPE = "file";


    // -----------------------------------------------------------
    // - Searchable
    // -----------------------------------------------------------

    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getFilename(), key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getNote(), key)) return true;
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
    // - filename
    // -----------------------------------------------------------

    private java.lang.String filename;

    public final java.lang.String getFilename() {
        return filename;
    }

    public final void setFilename(java.lang.String filename) {
        filename = prepareFilename(filename);
        if (isFilename(filename)) return;
        this.filename = filename;
        updateLastModified();
        fireModified("filename="+filename);
    }

    protected java.lang.String prepareFilename(java.lang.String filename) {
        // filename = StrExtend.removeUnreadableChars(filename);
        return filename;
    }

    public final boolean isFilenameSet() {
        return this.filename != null;
    }

    public final boolean isFilename(java.lang.String filename) {
        if (this.filename == null && filename == null) return true;
        return this.filename != null && this.filename.equals(filename);
    }

    protected final void updateFilename(Object value) {
        setFilename((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - uploadTime
    // -----------------------------------------------------------

    private ilarkesto.core.time.DateAndTime uploadTime;

    public final ilarkesto.core.time.DateAndTime getUploadTime() {
        return uploadTime;
    }

    public final void setUploadTime(ilarkesto.core.time.DateAndTime uploadTime) {
        uploadTime = prepareUploadTime(uploadTime);
        if (isUploadTime(uploadTime)) return;
        this.uploadTime = uploadTime;
        updateLastModified();
        fireModified("uploadTime="+uploadTime);
    }

    protected ilarkesto.core.time.DateAndTime prepareUploadTime(ilarkesto.core.time.DateAndTime uploadTime) {
        return uploadTime;
    }

    public final boolean isUploadTimeSet() {
        return this.uploadTime != null;
    }

    public final boolean isUploadTime(ilarkesto.core.time.DateAndTime uploadTime) {
        if (this.uploadTime == null && uploadTime == null) return true;
        return this.uploadTime != null && this.uploadTime.equals(uploadTime);
    }

    protected final void updateUploadTime(Object value) {
        value = value == null ? null : new ilarkesto.core.time.DateAndTime((String)value);
        setUploadTime((ilarkesto.core.time.DateAndTime)value);
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
    // - note
    // -----------------------------------------------------------

    private java.lang.String note;

    public final java.lang.String getNote() {
        return note;
    }

    public final void setNote(java.lang.String note) {
        note = prepareNote(note);
        if (isNote(note)) return;
        this.note = note;
        updateLastModified();
        fireModified("note="+note);
    }

    protected java.lang.String prepareNote(java.lang.String note) {
        // note = StrExtend.removeUnreadableChars(note);
        return note;
    }

    public final boolean isNoteSet() {
        return this.note != null;
    }

    public final boolean isNote(java.lang.String note) {
        if (this.note == null && note == null) return true;
        return this.note != null && this.note.equals(note);
    }

    protected final void updateNote(Object value) {
        setNote((java.lang.String)value);
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("projectId")) updateProject(value);
            if (property.equals("filename")) updateFilename(value);
            if (property.equals("uploadTime")) updateUploadTime(value);
            if (property.equals("label")) updateLabel(value);
            if (property.equals("number")) updateNumber(value);
            if (property.equals("note")) updateNote(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadProjectReference(entityId);
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
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    static scrum.server.project.ProjectDao projectDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectDao(scrum.server.project.ProjectDao projectDao) {
        GFile.projectDao = projectDao;
    }

    static scrum.server.files.FileDao fileDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setFileDao(scrum.server.files.FileDao fileDao) {
        GFile.fileDao = fileDao;
    }

}