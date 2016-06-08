// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.EntityGenerator










package ilarkesto.persistence;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.ADatob;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.AStructure;
import ilarkesto.auth.AUser;
import ilarkesto.persistence.EntityDoesNotExistException;
import ilarkesto.base.StrExtend;

public abstract class GAEntity {

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
    }

    public final java.util.Set<scrum.server.collaboration.Emoticon> getEmoticons() {
        return emoticonDao.getEmoticonsByParent((AEntity)this);
    }

    public final java.util.Set<scrum.server.journal.Change> getChanges() {
        return changeDao.getChangesByParent((AEntity)this);
    }

    public final java.util.Set<scrum.server.collaboration.Comment> getComments() {
        return commentDao.getCommentsByParent((AEntity)this);
    }

    public final java.util.Set<scrum.server.journal.ProjectEvent> getProjectEvents() {
        return projectEventDao.getProjectEventsBySubject((AEntity)this);
    }

    public final scrum.server.pr.Subscription getSubscription() {
        return subscriptionDao.getSubscriptionBySubject((AEntity)this);
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GAEntity.class);

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
        }
    }

    // --- ensure integrity ---

    public void ensureIntegrity() {
        super.ensureIntegrity();
    }

    static scrum.server.collaboration.EmoticonDao emoticonDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setEmoticonDao(scrum.server.collaboration.EmoticonDao emoticonDao) {
        GAEntity.emoticonDao = emoticonDao;
    }

    static scrum.server.journal.ChangeDao changeDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setChangeDao(scrum.server.journal.ChangeDao changeDao) {
        GAEntity.changeDao = changeDao;
    }

    static scrum.server.collaboration.CommentDao commentDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setCommentDao(scrum.server.collaboration.CommentDao commentDao) {
        GAEntity.commentDao = commentDao;
    }

    static scrum.server.journal.ProjectEventDao projectEventDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectEventDao(scrum.server.journal.ProjectEventDao projectEventDao) {
        GAEntity.projectEventDao = projectEventDao;
    }

    static scrum.server.pr.SubscriptionDao subscriptionDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSubscriptionDao(scrum.server.pr.SubscriptionDao subscriptionDao) {
        GAEntity.subscriptionDao = subscriptionDao;
    }

}