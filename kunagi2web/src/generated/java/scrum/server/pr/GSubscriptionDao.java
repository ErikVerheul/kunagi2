// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.pr;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GSubscriptionDao
            extends ilarkesto.persistence.ADao<Subscription> {

    public final String getEntityName() {
        return Subscription.TYPE;
    }

    public final Class getEntityClass() {
        return Subscription.class;
    }

    public Set<Subscription> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<Subscription>() {
            public boolean test(Subscription e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        subjectsCache = null;
        subscriptionsBySubscribersEmailCache.clear();
        subscribersEmailsCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof Subscription) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof Subscription) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - subject
    // -----------------------------------------------------------

    public final Subscription getSubscriptionBySubject(ilarkesto.persistence.AEntity subject) {
        return getEntity(new IsSubject(subject));
    }
    private Set<ilarkesto.persistence.AEntity> subjectsCache;

    public final Set<ilarkesto.persistence.AEntity> getSubjects() {
        if (subjectsCache == null) {
            subjectsCache = new HashSet<ilarkesto.persistence.AEntity>();
            for (Subscription e : getEntities()) {
                if (e.isSubjectSet()) subjectsCache.add(e.getSubject());
            }
        }
        return subjectsCache;
    }

    private static class IsSubject implements Predicate<Subscription> {

        private ilarkesto.persistence.AEntity value;

        public IsSubject(ilarkesto.persistence.AEntity value) {
            this.value = value;
        }

        public boolean test(Subscription e) {
            return e.isSubject(value);
        }

    }

    // -----------------------------------------------------------
    // - subscribersEmails
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Subscription>> subscriptionsBySubscribersEmailCache = new Cache<java.lang.String,Set<Subscription>>(
            new Cache.Factory<java.lang.String,Set<Subscription>>() {
                public Set<Subscription> create(java.lang.String subscribersEmail) {
                    return getEntities(new ContainsSubscribersEmail(subscribersEmail));
                }
            });

    public final Set<Subscription> getSubscriptionsBySubscribersEmail(java.lang.String subscribersEmail) {
        return new HashSet<Subscription>(subscriptionsBySubscribersEmailCache.get(subscribersEmail));
    }
    private Set<java.lang.String> subscribersEmailsCache;

    public final Set<java.lang.String> getSubscribersEmails() {
        if (subscribersEmailsCache == null) {
            subscribersEmailsCache = new HashSet<java.lang.String>();
            for (Subscription e : getEntities()) {
                subscribersEmailsCache.addAll(e.getSubscribersEmails());
            }
        }
        return subscribersEmailsCache;
    }

    private static class ContainsSubscribersEmail implements Predicate<Subscription> {

        private java.lang.String value;

        public ContainsSubscribersEmail(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Subscription e) {
            return e.containsSubscribersEmail(value);
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