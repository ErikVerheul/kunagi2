// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.sprint;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GSprintDaySnapshotDao
            extends ilarkesto.persistence.ADao<SprintDaySnapshot> {

    public final String getEntityName() {
        return SprintDaySnapshot.TYPE;
    }

    public final Class getEntityClass() {
        return SprintDaySnapshot.class;
    }

    public Set<SprintDaySnapshot> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<SprintDaySnapshot>() {
            public boolean test(SprintDaySnapshot e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        sprintDaySnapshotsBySprintCache.clear();
        sprintsCache = null;
        sprintDaySnapshotsByDateCache.clear();
        datesCache = null;
        sprintDaySnapshotsByRemainingWorkCache.clear();
        remainingWorksCache = null;
        sprintDaySnapshotsByBurnedWorkCache.clear();
        burnedWorksCache = null;
        sprintDaySnapshotsByBurnedWorkFromDeletedCache.clear();
        burnedWorkFromDeletedsCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof SprintDaySnapshot) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof SprintDaySnapshot) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - sprint
    // -----------------------------------------------------------

    private final Cache<scrum.server.sprint.Sprint,Set<SprintDaySnapshot>> sprintDaySnapshotsBySprintCache = new Cache<scrum.server.sprint.Sprint,Set<SprintDaySnapshot>>(
            new Cache.Factory<scrum.server.sprint.Sprint,Set<SprintDaySnapshot>>() {
                public Set<SprintDaySnapshot> create(scrum.server.sprint.Sprint sprint) {
                    return getEntities(new IsSprint(sprint));
                }
            });

    public final Set<SprintDaySnapshot> getSprintDaySnapshotsBySprint(scrum.server.sprint.Sprint sprint) {
        return new HashSet<SprintDaySnapshot>(sprintDaySnapshotsBySprintCache.get(sprint));
    }
    private Set<scrum.server.sprint.Sprint> sprintsCache;

    public final Set<scrum.server.sprint.Sprint> getSprints() {
        if (sprintsCache == null) {
            sprintsCache = new HashSet<scrum.server.sprint.Sprint>();
            for (SprintDaySnapshot e : getEntities()) {
                if (e.isSprintSet()) sprintsCache.add(e.getSprint());
            }
        }
        return sprintsCache;
    }

    private static class IsSprint implements Predicate<SprintDaySnapshot> {

        private scrum.server.sprint.Sprint value;

        public IsSprint(scrum.server.sprint.Sprint value) {
            this.value = value;
        }

        public boolean test(SprintDaySnapshot e) {
            return e.isSprint(value);
        }

    }

    // -----------------------------------------------------------
    // - date
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.Date,Set<SprintDaySnapshot>> sprintDaySnapshotsByDateCache = new Cache<ilarkesto.core.time.Date,Set<SprintDaySnapshot>>(
            new Cache.Factory<ilarkesto.core.time.Date,Set<SprintDaySnapshot>>() {
                public Set<SprintDaySnapshot> create(ilarkesto.core.time.Date date) {
                    return getEntities(new IsDate(date));
                }
            });

    public final Set<SprintDaySnapshot> getSprintDaySnapshotsByDate(ilarkesto.core.time.Date date) {
        return new HashSet<SprintDaySnapshot>(sprintDaySnapshotsByDateCache.get(date));
    }
    private Set<ilarkesto.core.time.Date> datesCache;

    public final Set<ilarkesto.core.time.Date> getDates() {
        if (datesCache == null) {
            datesCache = new HashSet<ilarkesto.core.time.Date>();
            for (SprintDaySnapshot e : getEntities()) {
                if (e.isDateSet()) datesCache.add(e.getDate());
            }
        }
        return datesCache;
    }

    private static class IsDate implements Predicate<SprintDaySnapshot> {

        private ilarkesto.core.time.Date value;

        public IsDate(ilarkesto.core.time.Date value) {
            this.value = value;
        }

        public boolean test(SprintDaySnapshot e) {
            return e.isDate(value);
        }

    }

    // -----------------------------------------------------------
    // - remainingWork
    // -----------------------------------------------------------

    private final Cache<Integer,Set<SprintDaySnapshot>> sprintDaySnapshotsByRemainingWorkCache = new Cache<Integer,Set<SprintDaySnapshot>>(
            new Cache.Factory<Integer,Set<SprintDaySnapshot>>() {
                public Set<SprintDaySnapshot> create(Integer remainingWork) {
                    return getEntities(new IsRemainingWork(remainingWork));
                }
            });

    public final Set<SprintDaySnapshot> getSprintDaySnapshotsByRemainingWork(int remainingWork) {
        return new HashSet<SprintDaySnapshot>(sprintDaySnapshotsByRemainingWorkCache.get(remainingWork));
    }
    private Set<Integer> remainingWorksCache;

    public final Set<Integer> getRemainingWorks() {
        if (remainingWorksCache == null) {
            remainingWorksCache = new HashSet<Integer>();
            for (SprintDaySnapshot e : getEntities()) {
                remainingWorksCache.add(e.getRemainingWork());
            }
        }
        return remainingWorksCache;
    }

    private static class IsRemainingWork implements Predicate<SprintDaySnapshot> {

        private int value;

        public IsRemainingWork(int value) {
            this.value = value;
        }

        public boolean test(SprintDaySnapshot e) {
            return e.isRemainingWork(value);
        }

    }

    // -----------------------------------------------------------
    // - burnedWork
    // -----------------------------------------------------------

    private final Cache<Integer,Set<SprintDaySnapshot>> sprintDaySnapshotsByBurnedWorkCache = new Cache<Integer,Set<SprintDaySnapshot>>(
            new Cache.Factory<Integer,Set<SprintDaySnapshot>>() {
                public Set<SprintDaySnapshot> create(Integer burnedWork) {
                    return getEntities(new IsBurnedWork(burnedWork));
                }
            });

    public final Set<SprintDaySnapshot> getSprintDaySnapshotsByBurnedWork(int burnedWork) {
        return new HashSet<SprintDaySnapshot>(sprintDaySnapshotsByBurnedWorkCache.get(burnedWork));
    }
    private Set<Integer> burnedWorksCache;

    public final Set<Integer> getBurnedWorks() {
        if (burnedWorksCache == null) {
            burnedWorksCache = new HashSet<Integer>();
            for (SprintDaySnapshot e : getEntities()) {
                burnedWorksCache.add(e.getBurnedWork());
            }
        }
        return burnedWorksCache;
    }

    private static class IsBurnedWork implements Predicate<SprintDaySnapshot> {

        private int value;

        public IsBurnedWork(int value) {
            this.value = value;
        }

        public boolean test(SprintDaySnapshot e) {
            return e.isBurnedWork(value);
        }

    }

    // -----------------------------------------------------------
    // - burnedWorkFromDeleted
    // -----------------------------------------------------------

    private final Cache<Integer,Set<SprintDaySnapshot>> sprintDaySnapshotsByBurnedWorkFromDeletedCache = new Cache<Integer,Set<SprintDaySnapshot>>(
            new Cache.Factory<Integer,Set<SprintDaySnapshot>>() {
                public Set<SprintDaySnapshot> create(Integer burnedWorkFromDeleted) {
                    return getEntities(new IsBurnedWorkFromDeleted(burnedWorkFromDeleted));
                }
            });

    public final Set<SprintDaySnapshot> getSprintDaySnapshotsByBurnedWorkFromDeleted(int burnedWorkFromDeleted) {
        return new HashSet<SprintDaySnapshot>(sprintDaySnapshotsByBurnedWorkFromDeletedCache.get(burnedWorkFromDeleted));
    }
    private Set<Integer> burnedWorkFromDeletedsCache;

    public final Set<Integer> getBurnedWorkFromDeleteds() {
        if (burnedWorkFromDeletedsCache == null) {
            burnedWorkFromDeletedsCache = new HashSet<Integer>();
            for (SprintDaySnapshot e : getEntities()) {
                burnedWorkFromDeletedsCache.add(e.getBurnedWorkFromDeleted());
            }
        }
        return burnedWorkFromDeletedsCache;
    }

    private static class IsBurnedWorkFromDeleted implements Predicate<SprintDaySnapshot> {

        private int value;

        public IsBurnedWorkFromDeleted(int value) {
            this.value = value;
        }

        public boolean test(SprintDaySnapshot e) {
            return e.isBurnedWorkFromDeleted(value);
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

    scrum.server.sprint.SprintDao sprintDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public void setSprintDao(scrum.server.sprint.SprintDao sprintDao) {
        this.sprintDao = sprintDao;
    }

}