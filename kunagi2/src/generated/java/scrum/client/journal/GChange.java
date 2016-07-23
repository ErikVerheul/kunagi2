// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.journal;

import ilarkesto.core.base.KunagiProperties;
import java.util.*;
import static ilarkesto.core.base.Utl.equalObjects;
import static ilarkesto.core.logging.ClientLog.*;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GChange
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public GChange() {
    }

    public GChange(KunagiProperties data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "change";

    @Override
    public final String getEntityType() {
        return ENTITY_TYPE;
    }

    // --- parent ---

    private String parentId;

    public final ilarkesto.gwt.client.AGwtEntity getParent() {
        if (parentId == null) return null;
        return getDao().getEntity(this.parentId);
    }

    public final boolean isParentSet() {
        return parentId != null;
    }

    public final Change setParent(ilarkesto.gwt.client.AGwtEntity parent) {
        String id = parent == null ? null : parent.getId();
        if (equalObjects(this.parentId, id)) return (Change) this;
        this.parentId = id;
        propertyChanged("parentId", this.parentId);
        return (Change)this;
    }

    public final boolean isParent(ilarkesto.gwt.client.AGwtEntity parent) {
        return equalObjects(this.parentId, parent);
    }

    // --- user ---

    private String userId;

    public final scrum.client.admin.User getUser() {
        if (userId == null) return null;
        return getDao().getUser(this.userId);
    }

    public final boolean isUserSet() {
        return userId != null;
    }

    public final Change setUser(scrum.client.admin.User user) {
        String id = user == null ? null : user.getId();
        if (equalObjects(this.userId, id)) return (Change) this;
        this.userId = id;
        propertyChanged("userId", this.userId);
        return (Change)this;
    }

    public final boolean isUser(scrum.client.admin.User user) {
        return equalObjects(this.userId, user);
    }

    // --- dateAndTime ---

    private ilarkesto.core.time.DateAndTime dateAndTime ;

    public final ilarkesto.core.time.DateAndTime getDateAndTime() {
        return this.dateAndTime ;
    }

    public final Change setDateAndTime(ilarkesto.core.time.DateAndTime dateAndTime) {
        if (isDateAndTime(dateAndTime)) return (Change)this;
        if (dateAndTime == null) throw new RuntimeException("Field is mandatory.");
        this.dateAndTime = dateAndTime ;
        propertyChanged("dateAndTime", this.dateAndTime);
        return (Change)this;
    }

    public final boolean isDateAndTime(ilarkesto.core.time.DateAndTime dateAndTime) {
        return equalObjects(this.dateAndTime, dateAndTime);
    }

    private transient DateAndTimeModel dateAndTimeModel;

    public DateAndTimeModel getDateAndTimeModel() {
        if (dateAndTimeModel == null) dateAndTimeModel = createDateAndTimeModel();
        return dateAndTimeModel;
    }

    protected DateAndTimeModel createDateAndTimeModel() { return new DateAndTimeModel(); }

    protected class DateAndTimeModel extends ilarkesto.gwt.client.editor.ADateAndTimeEditorModel {

        @Override
        public String getId() {
            return "Change_dateAndTime";
        }

        @Override
        public ilarkesto.core.time.DateAndTime getValue() {
            return getDateAndTime();
        }

        @Override
        public void setValue(ilarkesto.core.time.DateAndTime value) {
            setDateAndTime(value);
        }

        @Override
        public boolean isMandatory() { return true; }

        @Override
        protected void onChangeValue(ilarkesto.core.time.DateAndTime oldValue, ilarkesto.core.time.DateAndTime newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- key ---

    private java.lang.String key ;

    public final java.lang.String getKey() {
        return this.key ;
    }

    public final Change setKey(java.lang.String key) {
        if (isKey(key)) return (Change)this;
        this.key = key ;
        propertyChanged("key", this.key);
        return (Change)this;
    }

    public final boolean isKey(java.lang.String key) {
        return equalObjects(this.key, key);
    }

    private transient KeyModel keyModel;

    public KeyModel getKeyModel() {
        if (keyModel == null) keyModel = createKeyModel();
        return keyModel;
    }

    protected KeyModel createKeyModel() { return new KeyModel(); }

    protected class KeyModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Change_key";
        }

        @Override
        public java.lang.String getValue() {
            return getKey();
        }

        @Override
        public void setValue(java.lang.String value) {
            setKey(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- oldValue ---

    private java.lang.String oldValue ;

    public final java.lang.String getOldValue() {
        return this.oldValue ;
    }

    public final Change setOldValue(java.lang.String oldValue) {
        if (isOldValue(oldValue)) return (Change)this;
        this.oldValue = oldValue ;
        propertyChanged("oldValue", this.oldValue);
        return (Change)this;
    }

    public final boolean isOldValue(java.lang.String oldValue) {
        return equalObjects(this.oldValue, oldValue);
    }

    private transient OldValueModel oldValueModel;

    public OldValueModel getOldValueModel() {
        if (oldValueModel == null) oldValueModel = createOldValueModel();
        return oldValueModel;
    }

    protected OldValueModel createOldValueModel() { return new OldValueModel(); }

    protected class OldValueModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Change_oldValue";
        }

        @Override
        public java.lang.String getValue() {
            return getOldValue();
        }

        @Override
        public void setValue(java.lang.String value) {
            setOldValue(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- newValue ---

    private java.lang.String newValue ;

    public final java.lang.String getNewValue() {
        return this.newValue ;
    }

    public final Change setNewValue(java.lang.String newValue) {
        if (isNewValue(newValue)) return (Change)this;
        this.newValue = newValue ;
        propertyChanged("newValue", this.newValue);
        return (Change)this;
    }

    public final boolean isNewValue(java.lang.String newValue) {
        return equalObjects(this.newValue, newValue);
    }

    private transient NewValueModel newValueModel;

    public NewValueModel getNewValueModel() {
        if (newValueModel == null) newValueModel = createNewValueModel();
        return newValueModel;
    }

    protected NewValueModel createNewValueModel() { return new NewValueModel(); }

    protected class NewValueModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Change_newValue";
        }

        @Override
        public java.lang.String getValue() {
            return getNewValue();
        }

        @Override
        public void setValue(java.lang.String value) {
            setNewValue(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- comment ---

    private java.lang.String comment ;

    public final java.lang.String getComment() {
        return this.comment ;
    }

    public final Change setComment(java.lang.String comment) {
        if (isComment(comment)) return (Change)this;
        this.comment = comment ;
        propertyChanged("comment", this.comment);
        return (Change)this;
    }

    public final boolean isComment(java.lang.String comment) {
        return equalObjects(this.comment, comment);
    }

    private transient CommentModel commentModel;

    public CommentModel getCommentModel() {
        if (commentModel == null) commentModel = createCommentModel();
        return commentModel;
    }

    protected CommentModel createCommentModel() { return new CommentModel(); }

    protected class CommentModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Change_comment";
        }

        @Override
        public java.lang.String getValue() {
            return getComment();
        }

        @Override
        public void setValue(java.lang.String value) {
            setComment(value);
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

    public void updateProperties(KunagiProperties props) {
        parentId = (String) props.getValue("parentId");
        userId = (String) props.getValue("userId");
        String dateAndTimeAsString = (String) props.getValue("dateAndTime");
        dateAndTime  =  dateAndTimeAsString == null ? null : new ilarkesto.core.time.DateAndTime(dateAndTimeAsString);
        key  = (java.lang.String) props.getValue("key");
        oldValue  = (java.lang.String) props.getValue("oldValue");
        newValue  = (java.lang.String) props.getValue("newValue");
        comment  = (java.lang.String) props.getValue("comment");
        updateLocalModificationTime();
    }

    @Override
    public void storeProperties(KunagiProperties properties) {
        super.storeProperties(properties);
        properties.putValue("parentId", this.parentId);
        properties.putValue("userId", this.userId);
        properties.putValue("dateAndTime", this.dateAndTime == null ? null : this.dateAndTime.toString());
        properties.putValue("key", this.key);
        properties.putValue("oldValue", this.oldValue);
        properties.putValue("newValue", this.newValue);
        properties.putValue("comment", this.comment);
    }

}