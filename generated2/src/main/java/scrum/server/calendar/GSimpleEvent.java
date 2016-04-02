// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.EntityGenerator










package scrum.server.calendar;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.ADatob;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.AStructure;
import ilarkesto.auth.AUser;
import ilarkesto.persistence.EntityDoesNotExistException;
import ilarkesto.base.StrExtend;

public abstract class GSimpleEvent
            extends AEntity
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, ilarkesto.search.Searchable, java.lang.Comparable<SimpleEvent> {

    // --- AEntity ---

    public final scrum.server.calendar.SimpleEventDao getDao() {
        return simpleEventDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("projectId", this.projectId);
        properties.put("label", this.label);
        properties.put("number", this.number);
        properties.put("date", this.date == null ? null : this.date.toString());
        properties.put("time", this.time == null ? null : this.time.toString());
        properties.put("location", this.location);
        properties.put("duration", this.duration);
        properties.put("agenda", this.agenda);
        properties.put("note", this.note);
    }

    public int compareTo(SimpleEvent other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GSimpleEvent.class);

    public static final String TYPE = "simpleEvent";


    // -----------------------------------------------------------
    // - Searchable
    // -----------------------------------------------------------

    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getLocation(), key)) return true;
        if (matchesKey(getAgenda(), key)) return true;
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
    // - date
    // -----------------------------------------------------------

    private ilarkesto.core.time.Date date;

    public final ilarkesto.core.time.Date getDate() {
        return date;
    }

    public final void setDate(ilarkesto.core.time.Date date) {
        date = prepareDate(date);
        if (isDate(date)) return;
        this.date = date;
        updateLastModified();
        fireModified("date="+date);
    }

    protected ilarkesto.core.time.Date prepareDate(ilarkesto.core.time.Date date) {
        return date;
    }

    public final boolean isDateSet() {
        return this.date != null;
    }

    public final boolean isDate(ilarkesto.core.time.Date date) {
        if (this.date == null && date == null) return true;
        return this.date != null && this.date.equals(date);
    }

    protected final void updateDate(Object value) {
        value = value == null ? null : new ilarkesto.core.time.Date((String)value);
        setDate((ilarkesto.core.time.Date)value);
    }

    // -----------------------------------------------------------
    // - time
    // -----------------------------------------------------------

    private ilarkesto.core.time.Time time;

    public final ilarkesto.core.time.Time getTime() {
        return time;
    }

    public final void setTime(ilarkesto.core.time.Time time) {
        time = prepareTime(time);
        if (isTime(time)) return;
        this.time = time;
        updateLastModified();
        fireModified("time="+time);
    }

    protected ilarkesto.core.time.Time prepareTime(ilarkesto.core.time.Time time) {
        return time;
    }

    public final boolean isTimeSet() {
        return this.time != null;
    }

    public final boolean isTime(ilarkesto.core.time.Time time) {
        if (this.time == null && time == null) return true;
        return this.time != null && this.time.equals(time);
    }

    protected final void updateTime(Object value) {
        value = value == null ? null : new ilarkesto.core.time.Time((String)value);
        setTime((ilarkesto.core.time.Time)value);
    }

    // -----------------------------------------------------------
    // - location
    // -----------------------------------------------------------

    private java.lang.String location;

    public final java.lang.String getLocation() {
        return location;
    }

    public final void setLocation(java.lang.String location) {
        location = prepareLocation(location);
        if (isLocation(location)) return;
        this.location = location;
        updateLastModified();
        fireModified("location="+location);
    }

    protected java.lang.String prepareLocation(java.lang.String location) {
        // location = StrExtend.removeUnreadableChars(location);
        return location;
    }

    public final boolean isLocationSet() {
        return this.location != null;
    }

    public final boolean isLocation(java.lang.String location) {
        if (this.location == null && location == null) return true;
        return this.location != null && this.location.equals(location);
    }

    protected final void updateLocation(Object value) {
        setLocation((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - duration
    // -----------------------------------------------------------

    private java.lang.Integer duration;

    public final java.lang.Integer getDuration() {
        return duration;
    }

    public final void setDuration(java.lang.Integer duration) {
        duration = prepareDuration(duration);
        if (isDuration(duration)) return;
        this.duration = duration;
        updateLastModified();
        fireModified("duration="+duration);
    }

    protected java.lang.Integer prepareDuration(java.lang.Integer duration) {
        return duration;
    }

    public final boolean isDurationSet() {
        return this.duration != null;
    }

    public final boolean isDuration(java.lang.Integer duration) {
        if (this.duration == null && duration == null) return true;
        return this.duration != null && this.duration.equals(duration);
    }

    protected final void updateDuration(Object value) {
        setDuration((java.lang.Integer)value);
    }

    // -----------------------------------------------------------
    // - agenda
    // -----------------------------------------------------------

    private java.lang.String agenda;

    public final java.lang.String getAgenda() {
        return agenda;
    }

    public final void setAgenda(java.lang.String agenda) {
        agenda = prepareAgenda(agenda);
        if (isAgenda(agenda)) return;
        this.agenda = agenda;
        updateLastModified();
        fireModified("agenda="+agenda);
    }

    protected java.lang.String prepareAgenda(java.lang.String agenda) {
        // agenda = StrExtend.removeUnreadableChars(agenda);
        return agenda;
    }

    public final boolean isAgendaSet() {
        return this.agenda != null;
    }

    public final boolean isAgenda(java.lang.String agenda) {
        if (this.agenda == null && agenda == null) return true;
        return this.agenda != null && this.agenda.equals(agenda);
    }

    protected final void updateAgenda(Object value) {
        setAgenda((java.lang.String)value);
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
            if (property.equals("label")) updateLabel(value);
            if (property.equals("number")) updateNumber(value);
            if (property.equals("date")) updateDate(value);
            if (property.equals("time")) updateTime(value);
            if (property.equals("location")) updateLocation(value);
            if (property.equals("duration")) updateDuration(value);
            if (property.equals("agenda")) updateAgenda(value);
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
        GSimpleEvent.projectDao = projectDao;
    }

    static scrum.server.calendar.SimpleEventDao simpleEventDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSimpleEventDao(scrum.server.calendar.SimpleEventDao simpleEventDao) {
        GSimpleEvent.simpleEventDao = simpleEventDao;
    }

}