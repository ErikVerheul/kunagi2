// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.estimation;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GRequirementEstimationVoteDao
            extends ilarkesto.persistence.ADao<RequirementEstimationVote> {

    public final String getEntityName() {
        return RequirementEstimationVote.TYPE;
    }

    public final Class getEntityClass() {
        return RequirementEstimationVote.class;
    }

    public Set<RequirementEstimationVote> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<RequirementEstimationVote>() {
            public boolean test(RequirementEstimationVote e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        requirementEstimationVotesByRequirementCache.clear();
        requirementsCache = null;
        requirementEstimationVotesByUserCache.clear();
        usersCache = null;
        requirementEstimationVotesByEstimatedWorkCache.clear();
        estimatedWorksCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof RequirementEstimationVote) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof RequirementEstimationVote) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - requirement
    // -----------------------------------------------------------

    private final Cache<scrum.server.project.Requirement,Set<RequirementEstimationVote>> requirementEstimationVotesByRequirementCache = new Cache<scrum.server.project.Requirement,Set<RequirementEstimationVote>>(
            new Cache.Factory<scrum.server.project.Requirement,Set<RequirementEstimationVote>>() {
                public Set<RequirementEstimationVote> create(scrum.server.project.Requirement requirement) {
                    return getEntities(new IsRequirement(requirement));
                }
            });

    public final Set<RequirementEstimationVote> getRequirementEstimationVotesByRequirement(scrum.server.project.Requirement requirement) {
        return new HashSet<RequirementEstimationVote>(requirementEstimationVotesByRequirementCache.get(requirement));
    }
    private Set<scrum.server.project.Requirement> requirementsCache;

    public final Set<scrum.server.project.Requirement> getRequirements() {
        if (requirementsCache == null) {
            requirementsCache = new HashSet<scrum.server.project.Requirement>();
            for (RequirementEstimationVote e : getEntities()) {
                if (e.isRequirementSet()) requirementsCache.add(e.getRequirement());
            }
        }
        return requirementsCache;
    }

    private static class IsRequirement implements Predicate<RequirementEstimationVote> {

        private scrum.server.project.Requirement value;

        public IsRequirement(scrum.server.project.Requirement value) {
            this.value = value;
        }

        public boolean test(RequirementEstimationVote e) {
            return e.isRequirement(value);
        }

    }

    // -----------------------------------------------------------
    // - user
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<RequirementEstimationVote>> requirementEstimationVotesByUserCache = new Cache<scrum.server.admin.User,Set<RequirementEstimationVote>>(
            new Cache.Factory<scrum.server.admin.User,Set<RequirementEstimationVote>>() {
                public Set<RequirementEstimationVote> create(scrum.server.admin.User user) {
                    return getEntities(new IsUser(user));
                }
            });

    public final Set<RequirementEstimationVote> getRequirementEstimationVotesByUser(scrum.server.admin.User user) {
        return new HashSet<RequirementEstimationVote>(requirementEstimationVotesByUserCache.get(user));
    }
    private Set<scrum.server.admin.User> usersCache;

    public final Set<scrum.server.admin.User> getUsers() {
        if (usersCache == null) {
            usersCache = new HashSet<scrum.server.admin.User>();
            for (RequirementEstimationVote e : getEntities()) {
                if (e.isUserSet()) usersCache.add(e.getUser());
            }
        }
        return usersCache;
    }

    private static class IsUser implements Predicate<RequirementEstimationVote> {

        private scrum.server.admin.User value;

        public IsUser(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(RequirementEstimationVote e) {
            return e.isUser(value);
        }

    }

    // -----------------------------------------------------------
    // - estimatedWork
    // -----------------------------------------------------------

    private final Cache<java.lang.Float,Set<RequirementEstimationVote>> requirementEstimationVotesByEstimatedWorkCache = new Cache<java.lang.Float,Set<RequirementEstimationVote>>(
            new Cache.Factory<java.lang.Float,Set<RequirementEstimationVote>>() {
                public Set<RequirementEstimationVote> create(java.lang.Float estimatedWork) {
                    return getEntities(new IsEstimatedWork(estimatedWork));
                }
            });

    public final Set<RequirementEstimationVote> getRequirementEstimationVotesByEstimatedWork(java.lang.Float estimatedWork) {
        return new HashSet<RequirementEstimationVote>(requirementEstimationVotesByEstimatedWorkCache.get(estimatedWork));
    }
    private Set<java.lang.Float> estimatedWorksCache;

    public final Set<java.lang.Float> getEstimatedWorks() {
        if (estimatedWorksCache == null) {
            estimatedWorksCache = new HashSet<java.lang.Float>();
            for (RequirementEstimationVote e : getEntities()) {
                if (e.isEstimatedWorkSet()) estimatedWorksCache.add(e.getEstimatedWork());
            }
        }
        return estimatedWorksCache;
    }

    private static class IsEstimatedWork implements Predicate<RequirementEstimationVote> {

        private java.lang.Float value;

        public IsEstimatedWork(java.lang.Float value) {
            this.value = value;
        }

        public boolean test(RequirementEstimationVote e) {
            return e.isEstimatedWork(value);
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

    scrum.server.project.RequirementDao requirementDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public void setRequirementDao(scrum.server.project.RequirementDao requirementDao) {
        this.requirementDao = requirementDao;
    }

}