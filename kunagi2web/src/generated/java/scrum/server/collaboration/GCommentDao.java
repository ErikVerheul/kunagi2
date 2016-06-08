// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.collaboration;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GCommentDao
            extends ilarkesto.persistence.ADao<Comment> {

    public final String getEntityName() {
        return Comment.TYPE;
    }

    public final Class getEntityClass() {
        return Comment.class;
    }

    public Set<Comment> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<Comment>() {
            public boolean test(Comment e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        commentsByParentCache.clear();
        parentsCache = null;
        commentsByAuthorCache.clear();
        authorsCache = null;
        commentsByPublishedCache.clear();
        commentsByAuthorNameCache.clear();
        authorNamesCache = null;
        commentsByAuthorEmailCache.clear();
        authorEmailsCache = null;
        commentsByAuthorNameVisibleCache.clear();
        commentsByTextCache.clear();
        textsCache = null;
        commentsByDateAndTimeCache.clear();
        dateAndTimesCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof Comment) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof Comment) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - parent
    // -----------------------------------------------------------

    private final Cache<ilarkesto.persistence.AEntity,Set<Comment>> commentsByParentCache = new Cache<ilarkesto.persistence.AEntity,Set<Comment>>(
            new Cache.Factory<ilarkesto.persistence.AEntity,Set<Comment>>() {
                public Set<Comment> create(ilarkesto.persistence.AEntity parent) {
                    return getEntities(new IsParent(parent));
                }
            });

    public final Set<Comment> getCommentsByParent(ilarkesto.persistence.AEntity parent) {
        return new HashSet<Comment>(commentsByParentCache.get(parent));
    }
    private Set<ilarkesto.persistence.AEntity> parentsCache;

    public final Set<ilarkesto.persistence.AEntity> getParents() {
        if (parentsCache == null) {
            parentsCache = new HashSet<ilarkesto.persistence.AEntity>();
            for (Comment e : getEntities()) {
                if (e.isParentSet()) parentsCache.add(e.getParent());
            }
        }
        return parentsCache;
    }

    private static class IsParent implements Predicate<Comment> {

        private ilarkesto.persistence.AEntity value;

        public IsParent(ilarkesto.persistence.AEntity value) {
            this.value = value;
        }

        public boolean test(Comment e) {
            return e.isParent(value);
        }

    }

    // -----------------------------------------------------------
    // - author
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<Comment>> commentsByAuthorCache = new Cache<scrum.server.admin.User,Set<Comment>>(
            new Cache.Factory<scrum.server.admin.User,Set<Comment>>() {
                public Set<Comment> create(scrum.server.admin.User author) {
                    return getEntities(new IsAuthor(author));
                }
            });

    public final Set<Comment> getCommentsByAuthor(scrum.server.admin.User author) {
        return new HashSet<Comment>(commentsByAuthorCache.get(author));
    }
    private Set<scrum.server.admin.User> authorsCache;

    public final Set<scrum.server.admin.User> getAuthors() {
        if (authorsCache == null) {
            authorsCache = new HashSet<scrum.server.admin.User>();
            for (Comment e : getEntities()) {
                if (e.isAuthorSet()) authorsCache.add(e.getAuthor());
            }
        }
        return authorsCache;
    }

    private static class IsAuthor implements Predicate<Comment> {

        private scrum.server.admin.User value;

        public IsAuthor(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(Comment e) {
            return e.isAuthor(value);
        }

    }

    // -----------------------------------------------------------
    // - published
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<Comment>> commentsByPublishedCache = new Cache<Boolean,Set<Comment>>(
            new Cache.Factory<Boolean,Set<Comment>>() {
                public Set<Comment> create(Boolean published) {
                    return getEntities(new IsPublished(published));
                }
            });

    public final Set<Comment> getCommentsByPublished(boolean published) {
        return new HashSet<Comment>(commentsByPublishedCache.get(published));
    }

    private static class IsPublished implements Predicate<Comment> {

        private boolean value;

        public IsPublished(boolean value) {
            this.value = value;
        }

        public boolean test(Comment e) {
            return value == e.isPublished();
        }

    }

    // -----------------------------------------------------------
    // - authorName
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Comment>> commentsByAuthorNameCache = new Cache<java.lang.String,Set<Comment>>(
            new Cache.Factory<java.lang.String,Set<Comment>>() {
                public Set<Comment> create(java.lang.String authorName) {
                    return getEntities(new IsAuthorName(authorName));
                }
            });

    public final Set<Comment> getCommentsByAuthorName(java.lang.String authorName) {
        return new HashSet<Comment>(commentsByAuthorNameCache.get(authorName));
    }
    private Set<java.lang.String> authorNamesCache;

    public final Set<java.lang.String> getAuthorNames() {
        if (authorNamesCache == null) {
            authorNamesCache = new HashSet<java.lang.String>();
            for (Comment e : getEntities()) {
                if (e.isAuthorNameSet()) authorNamesCache.add(e.getAuthorName());
            }
        }
        return authorNamesCache;
    }

    private static class IsAuthorName implements Predicate<Comment> {

        private java.lang.String value;

        public IsAuthorName(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Comment e) {
            return e.isAuthorName(value);
        }

    }

    // -----------------------------------------------------------
    // - authorEmail
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Comment>> commentsByAuthorEmailCache = new Cache<java.lang.String,Set<Comment>>(
            new Cache.Factory<java.lang.String,Set<Comment>>() {
                public Set<Comment> create(java.lang.String authorEmail) {
                    return getEntities(new IsAuthorEmail(authorEmail));
                }
            });

    public final Set<Comment> getCommentsByAuthorEmail(java.lang.String authorEmail) {
        return new HashSet<Comment>(commentsByAuthorEmailCache.get(authorEmail));
    }
    private Set<java.lang.String> authorEmailsCache;

    public final Set<java.lang.String> getAuthorEmails() {
        if (authorEmailsCache == null) {
            authorEmailsCache = new HashSet<java.lang.String>();
            for (Comment e : getEntities()) {
                if (e.isAuthorEmailSet()) authorEmailsCache.add(e.getAuthorEmail());
            }
        }
        return authorEmailsCache;
    }

    private static class IsAuthorEmail implements Predicate<Comment> {

        private java.lang.String value;

        public IsAuthorEmail(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Comment e) {
            return e.isAuthorEmail(value);
        }

    }

    // -----------------------------------------------------------
    // - authorNameVisible
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<Comment>> commentsByAuthorNameVisibleCache = new Cache<Boolean,Set<Comment>>(
            new Cache.Factory<Boolean,Set<Comment>>() {
                public Set<Comment> create(Boolean authorNameVisible) {
                    return getEntities(new IsAuthorNameVisible(authorNameVisible));
                }
            });

    public final Set<Comment> getCommentsByAuthorNameVisible(boolean authorNameVisible) {
        return new HashSet<Comment>(commentsByAuthorNameVisibleCache.get(authorNameVisible));
    }

    private static class IsAuthorNameVisible implements Predicate<Comment> {

        private boolean value;

        public IsAuthorNameVisible(boolean value) {
            this.value = value;
        }

        public boolean test(Comment e) {
            return value == e.isAuthorNameVisible();
        }

    }

    // -----------------------------------------------------------
    // - text
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Comment>> commentsByTextCache = new Cache<java.lang.String,Set<Comment>>(
            new Cache.Factory<java.lang.String,Set<Comment>>() {
                public Set<Comment> create(java.lang.String text) {
                    return getEntities(new IsText(text));
                }
            });

    public final Set<Comment> getCommentsByText(java.lang.String text) {
        return new HashSet<Comment>(commentsByTextCache.get(text));
    }
    private Set<java.lang.String> textsCache;

    public final Set<java.lang.String> getTexts() {
        if (textsCache == null) {
            textsCache = new HashSet<java.lang.String>();
            for (Comment e : getEntities()) {
                if (e.isTextSet()) textsCache.add(e.getText());
            }
        }
        return textsCache;
    }

    private static class IsText implements Predicate<Comment> {

        private java.lang.String value;

        public IsText(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Comment e) {
            return e.isText(value);
        }

    }

    // -----------------------------------------------------------
    // - dateAndTime
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.DateAndTime,Set<Comment>> commentsByDateAndTimeCache = new Cache<ilarkesto.core.time.DateAndTime,Set<Comment>>(
            new Cache.Factory<ilarkesto.core.time.DateAndTime,Set<Comment>>() {
                public Set<Comment> create(ilarkesto.core.time.DateAndTime dateAndTime) {
                    return getEntities(new IsDateAndTime(dateAndTime));
                }
            });

    public final Set<Comment> getCommentsByDateAndTime(ilarkesto.core.time.DateAndTime dateAndTime) {
        return new HashSet<Comment>(commentsByDateAndTimeCache.get(dateAndTime));
    }
    private Set<ilarkesto.core.time.DateAndTime> dateAndTimesCache;

    public final Set<ilarkesto.core.time.DateAndTime> getDateAndTimes() {
        if (dateAndTimesCache == null) {
            dateAndTimesCache = new HashSet<ilarkesto.core.time.DateAndTime>();
            for (Comment e : getEntities()) {
                if (e.isDateAndTimeSet()) dateAndTimesCache.add(e.getDateAndTime());
            }
        }
        return dateAndTimesCache;
    }

    private static class IsDateAndTime implements Predicate<Comment> {

        private ilarkesto.core.time.DateAndTime value;

        public IsDateAndTime(ilarkesto.core.time.DateAndTime value) {
            this.value = value;
        }

        public boolean test(Comment e) {
            return e.isDateAndTime(value);
        }

    }

    // --- valueObject classes ---
    @Override
    protected Set<Class> getValueObjectClasses() {
        Set<Class> ret = new HashSet<Class>(super.getValueObjectClasses());
        return ret;
    }

    @Override
    public Map<String, Class> getAliases() {
        Map<String, Class> aliases = new HashMap<String, Class>(super.getAliases());
        return aliases;
    }

    // --- dependencies ---

}