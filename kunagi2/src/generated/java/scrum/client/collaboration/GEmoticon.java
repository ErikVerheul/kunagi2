// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.collaboration;

import ilarkesto.core.KunagiProperties;
import java.util.*;
import static ilarkesto.core.base.Utl.equalObjects;
import static ilarkesto.core.logging.ClientLog.*;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GEmoticon
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public GEmoticon() {
    }

    public GEmoticon(KunagiProperties data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "emoticon";

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

    public final Emoticon setParent(ilarkesto.gwt.client.AGwtEntity parent) {
        String id = parent == null ? null : parent.getId();
        if (equalObjects(this.parentId, id)) return (Emoticon) this;
        this.parentId = id;
        propertyChanged("parentId", this.parentId);
        return (Emoticon)this;
    }

    public final boolean isParent(ilarkesto.gwt.client.AGwtEntity parent) {
        return equalObjects(this.parentId, parent);
    }

    // --- owner ---

    private String ownerId;

    public final scrum.client.admin.User getOwner() {
        if (ownerId == null) return null;
        return getDao().getUser(this.ownerId);
    }

    public final boolean isOwnerSet() {
        return ownerId != null;
    }

    public final Emoticon setOwner(scrum.client.admin.User owner) {
        String id = owner == null ? null : owner.getId();
        if (equalObjects(this.ownerId, id)) return (Emoticon) this;
        this.ownerId = id;
        propertyChanged("ownerId", this.ownerId);
        return (Emoticon)this;
    }

    public final boolean isOwner(scrum.client.admin.User owner) {
        return equalObjects(this.ownerId, owner);
    }

    // --- emotion ---

    private java.lang.String emotion ;

    public final java.lang.String getEmotion() {
        return this.emotion ;
    }

    public final Emoticon setEmotion(java.lang.String emotion) {
        if (isEmotion(emotion)) return (Emoticon)this;
        this.emotion = emotion ;
        propertyChanged("emotion", this.emotion);
        return (Emoticon)this;
    }

    public final boolean isEmotion(java.lang.String emotion) {
        return equalObjects(this.emotion, emotion);
    }

    private transient EmotionModel emotionModel;

    public EmotionModel getEmotionModel() {
        if (emotionModel == null) emotionModel = createEmotionModel();
        return emotionModel;
    }

    protected EmotionModel createEmotionModel() { return new EmotionModel(); }

    protected class EmotionModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "Emoticon_emotion";
        }

        @Override
        public java.lang.String getValue() {
            return getEmotion();
        }

        @Override
        public void setValue(java.lang.String value) {
            setEmotion(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- update properties by map ---

    public void updateProperties(KunagiProperties props) {
        parentId = (String) props.getValue("parentId");
        ownerId = (String) props.getValue("ownerId");
        emotion  = (java.lang.String) props.getValue("emotion");
        updateLocalModificationTime();
    }

    @Override
    public void storeProperties(KunagiProperties properties) {
        super.storeProperties(properties);
        properties.putValue("parentId", this.parentId);
        properties.putValue("ownerId", this.ownerId);
        properties.putValue("emotion", this.emotion);
    }

}