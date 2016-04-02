// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.calendar;

import java.util.*;
import ilarkesto.core.logging.Log;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GSimpleEvent
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public GSimpleEvent() {
    }

    public GSimpleEvent(Map data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "simpleEvent";

    @Override
    public final String getEntityType() {
        return ENTITY_TYPE;
    }

    // --- project ---

    private String projectId;

    public final scrum.client.project.Project getProject() {
        if (projectId == null) return null;
        return getDao().getProject(this.projectId);
    }

    public final boolean isProjectSet() {
        return projectId != null;
    }

    public final SimpleEvent setProject(scrum.client.project.Project project) {
        String id = project == null ? null : project.getId();
        if (equals(this.projectId, id)) return (SimpleEvent) this;
        this.projectId = id;
        propertyChanged("projectId", this.projectId);
        return (SimpleEvent)this;
    }

    public final boolean isProject(scrum.client.project.Project project) {
        return equals(this.projectId, project);
    }

    // --- label ---

    private java.lang.String label ;

    public final java.lang.String getLabel() {
        return this.label ;
    }

    public final SimpleEvent setLabel(java.lang.String label) {
        if (isLabel(label)) return (SimpleEvent)this;
        if (ilarkesto.core.base.Str.isBlank(label)) throw new RuntimeException("Field is mandatory.");
        this.label = label ;
        propertyChanged("label", this.label);
        return (SimpleEvent)this;
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
            return "SimpleEvent_label";
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
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- number ---

    private int number ;

    public final int getNumber() {
        return this.number ;
    }

    public final SimpleEvent setNumber(int number) {
        if (isNumber(number)) return (SimpleEvent)this;
        this.number = number ;
        propertyChanged("number", this.number);
        return (SimpleEvent)this;
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
            return "SimpleEvent_number";
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
        public boolean isMandatory() { return true; }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- date ---

    private ilarkesto.core.time.Date date ;

    public final ilarkesto.core.time.Date getDate() {
        return this.date ;
    }

    public final SimpleEvent setDate(ilarkesto.core.time.Date date) {
        if (isDate(date)) return (SimpleEvent)this;
        this.date = date ;
        propertyChanged("date", this.date);
        return (SimpleEvent)this;
    }

    public final boolean isDate(ilarkesto.core.time.Date date) {
        return equals(this.date, date);
    }

    private transient DateModel dateModel;

    public DateModel getDateModel() {
        if (dateModel == null) dateModel = createDateModel();
        return dateModel;
    }

    protected DateModel createDateModel() { return new DateModel(); }

    protected class DateModel extends ilarkesto.gwt.client.editor.ADateEditorModel {

        @Override
        public String getId() {
            return "SimpleEvent_date";
        }

        @Override
        public ilarkesto.core.time.Date getValue() {
            return getDate();
        }

        @Override
        public void setValue(ilarkesto.core.time.Date value) {
            setDate(value);
        }

        @Override
        protected void onChangeValue(ilarkesto.core.time.Date oldValue, ilarkesto.core.time.Date newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- time ---

    private ilarkesto.core.time.Time time ;

    public final ilarkesto.core.time.Time getTime() {
        return this.time ;
    }

    public final SimpleEvent setTime(ilarkesto.core.time.Time time) {
        if (isTime(time)) return (SimpleEvent)this;
        this.time = time ;
        propertyChanged("time", this.time);
        return (SimpleEvent)this;
    }

    public final boolean isTime(ilarkesto.core.time.Time time) {
        return equals(this.time, time);
    }

    private transient TimeModel timeModel;

    public TimeModel getTimeModel() {
        if (timeModel == null) timeModel = createTimeModel();
        return timeModel;
    }

    protected TimeModel createTimeModel() { return new TimeModel(); }

    protected class TimeModel extends ilarkesto.gwt.client.editor.ATimeEditorModel {

        @Override
        public String getId() {
            return "SimpleEvent_time";
        }

        @Override
        public ilarkesto.core.time.Time getValue() {
            return getTime();
        }

        @Override
        public void setValue(ilarkesto.core.time.Time value) {
            setTime(value);
        }

        @Override
        protected void onChangeValue(ilarkesto.core.time.Time oldValue, ilarkesto.core.time.Time newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- location ---

    private java.lang.String location ;

    public final java.lang.String getLocation() {
        return this.location ;
    }

    public final SimpleEvent setLocation(java.lang.String location) {
        if (isLocation(location)) return (SimpleEvent)this;
        this.location = location ;
        propertyChanged("location", this.location);
        return (SimpleEvent)this;
    }

    public final boolean isLocation(java.lang.String location) {
        return equals(this.location, location);
    }

    private transient LocationModel locationModel;

    public LocationModel getLocationModel() {
        if (locationModel == null) locationModel = createLocationModel();
        return locationModel;
    }

    protected LocationModel createLocationModel() { return new LocationModel(); }

    protected class LocationModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SimpleEvent_location";
        }

        @Override
        public java.lang.String getValue() {
            return getLocation();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLocation(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- duration ---

    private java.lang.Integer duration ;

    public final java.lang.Integer getDuration() {
        return this.duration ;
    }

    public final SimpleEvent setDuration(java.lang.Integer duration) {
        if (isDuration(duration)) return (SimpleEvent)this;
        this.duration = duration ;
        propertyChanged("duration", this.duration);
        return (SimpleEvent)this;
    }

    public final boolean isDuration(java.lang.Integer duration) {
        return equals(this.duration, duration);
    }

    private transient DurationModel durationModel;

    public DurationModel getDurationModel() {
        if (durationModel == null) durationModel = createDurationModel();
        return durationModel;
    }

    protected DurationModel createDurationModel() { return new DurationModel(); }

    protected class DurationModel extends ilarkesto.gwt.client.editor.AIntegerEditorModel {

        @Override
        public String getId() {
            return "SimpleEvent_duration";
        }

        @Override
        public java.lang.Integer getValue() {
            return getDuration();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setDuration(value);
        }

            @Override
            public void increment() {
                setDuration(getDuration() + 1);
            }

            @Override
            public void decrement() {
                setDuration(getDuration() - 1);
            }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- agenda ---

    private java.lang.String agenda ;

    public final java.lang.String getAgenda() {
        return this.agenda ;
    }

    public final SimpleEvent setAgenda(java.lang.String agenda) {
        if (isAgenda(agenda)) return (SimpleEvent)this;
        this.agenda = agenda ;
        propertyChanged("agenda", this.agenda);
        return (SimpleEvent)this;
    }

    public final boolean isAgenda(java.lang.String agenda) {
        return equals(this.agenda, agenda);
    }

    private transient AgendaModel agendaModel;

    public AgendaModel getAgendaModel() {
        if (agendaModel == null) agendaModel = createAgendaModel();
        return agendaModel;
    }

    protected AgendaModel createAgendaModel() { return new AgendaModel(); }

    protected class AgendaModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SimpleEvent_agenda";
        }

        @Override
        public java.lang.String getValue() {
            return getAgenda();
        }

        @Override
        public void setValue(java.lang.String value) {
            setAgenda(value);
        }

        @Override
        public boolean isRichtext() { return true; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- note ---

    private java.lang.String note ;

    public final java.lang.String getNote() {
        return this.note ;
    }

    public final SimpleEvent setNote(java.lang.String note) {
        if (isNote(note)) return (SimpleEvent)this;
        this.note = note ;
        propertyChanged("note", this.note);
        return (SimpleEvent)this;
    }

    public final boolean isNote(java.lang.String note) {
        return equals(this.note, note);
    }

    private transient NoteModel noteModel;

    public NoteModel getNoteModel() {
        if (noteModel == null) noteModel = createNoteModel();
        return noteModel;
    }

    protected NoteModel createNoteModel() { return new NoteModel(); }

    protected class NoteModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SimpleEvent_note";
        }

        @Override
        public java.lang.String getValue() {
            return getNote();
        }

        @Override
        public void setValue(java.lang.String value) {
            setNote(value);
        }

        @Override
        public boolean isRichtext() { return true; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- update properties by map ---

    public void updateProperties(Map props) {
        projectId = (String) props.get("projectId");
        label  = (java.lang.String) props.get("label");
        number  = (Integer) props.get("number");
        String dateAsString = (String) props.get("date");
        date  =  dateAsString == null ? null : new ilarkesto.core.time.Date(dateAsString);
        String timeAsString = (String) props.get("time");
        time  =  timeAsString == null ? null : new ilarkesto.core.time.Time(timeAsString);
        location  = (java.lang.String) props.get("location");
        duration  = (java.lang.Integer) props.get("duration");
        agenda  = (java.lang.String) props.get("agenda");
        note  = (java.lang.String) props.get("note");
        updateLocalModificationTime();
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

    @Override
    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getLocation(), key)) return true;
        if (matchesKey(getAgenda(), key)) return true;
        if (matchesKey(getNote(), key)) return true;
        return false;
    }

}