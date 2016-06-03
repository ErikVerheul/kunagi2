// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.pr;

import java.util.*;
import ilarkesto.core.logging.Log;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GSubscription
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public GSubscription() {
    }

    public GSubscription(Map data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "subscription";

    @Override
    public final String getEntityType() {
        return ENTITY_TYPE;
    }

    // --- subject ---

    private String subjectId;

    public final ilarkesto.gwt.client.AGwtEntity getSubject() {
        if (subjectId == null) return null;
        return getDao().getEntity(this.subjectId);
    }

    public final boolean isSubjectSet() {
        return subjectId != null;
    }

    public final Subscription setSubject(ilarkesto.gwt.client.AGwtEntity subject) {
        String id = subject == null ? null : subject.getId();
        if (equals(this.subjectId, id)) return (Subscription) this;
        this.subjectId = id;
        propertyChanged("subjectId", this.subjectId);
        return (Subscription)this;
    }

    public final boolean isSubject(ilarkesto.gwt.client.AGwtEntity subject) {
        return equals(this.subjectId, subject);
    }

    // --- subscribersEmails ---

    private java.util.Set<java.lang.String> subscribersEmails = new java.util.HashSet<java.lang.String>();

    public final java.util.Set<java.lang.String> getSubscribersEmails() {
        return new java.util.HashSet<java.lang.String>(subscribersEmails);
    }

    public final void setSubscribersEmails(java.util.Set<java.lang.String> subscribersEmails) {
        if (subscribersEmails == null) throw new IllegalArgumentException("null is not allowed");
        if (this.subscribersEmails.equals(subscribersEmails)) return;
        this.subscribersEmails = new java.util.HashSet<java.lang.String>(subscribersEmails);
        propertyChanged("subscribersEmails", this.subscribersEmails);
    }

    public final boolean containsSubscribersEmail(java.lang.String subscribersEmail) {
        return subscribersEmails.contains(subscribersEmail);
    }


    // --- update properties by map ---

    public void updateProperties(Map props) {
        subjectId = (String) props.get("subjectId");
        subscribersEmails  = (java.util.Set<java.lang.String>) props.get("subscribersEmails");
        updateLocalModificationTime();
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("subjectId", this.subjectId);
        properties.put("subscribersEmails", this.subscribersEmails);
    }

}