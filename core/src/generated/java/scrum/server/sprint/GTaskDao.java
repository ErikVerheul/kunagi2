// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.sprint;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GTaskDao
            extends ilarkesto.persistence.ADao<Task> {

    public final String getEntityName() {
        return Task.TYPE;
    }

    public final Class getEntityClass() {
        return Task.class;
    }

    public Set<Task> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<Task>() {
            public boolean test(Task e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        tasksByRequirementCache.clear();
        requirementsCache = null;
        tasksByNumberCache.clear();
        numbersCache = null;
        tasksByLabelCache.clear();
        labelsCache = null;
        tasksByDescriptionCache.clear();
        descriptionsCache = null;
        tasksByRemainingWorkCache.clear();
        remainingWorksCache = null;
        tasksByBurnedWorkCache.clear();
        burnedWorksCache = null;
        tasksByOwnerCache.clear();
        ownersCache = null;
        tasksByImpedimentCache.clear();
        impedimentsCache = null;
        tasksByClosedInPastSprintCache.clear();
        closedInPastSprintsCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof Task) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof Task) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - requirement
    // -----------------------------------------------------------

    private final Cache<scrum.server.project.Requirement,Set<Task>> tasksByRequirementCache = new Cache<scrum.server.project.Requirement,Set<Task>>(
            new Cache.Factory<scrum.server.project.Requirement,Set<Task>>() {
                public Set<Task> create(scrum.server.project.Requirement requirement) {
                    return getEntities(new IsRequirement(requirement));
                }
            });

    public final Set<Task> getTasksByRequirement(scrum.server.project.Requirement requirement) {
        return new HashSet<Task>(tasksByRequirementCache.get(requirement));
    }
    private Set<scrum.server.project.Requirement> requirementsCache;

    public final Set<scrum.server.project.Requirement> getRequirements() {
        if (requirementsCache == null) {
            requirementsCache = new HashSet<scrum.server.project.Requirement>();
            for (Task e : getEntities()) {
                if (e.isRequirementSet()) requirementsCache.add(e.getRequirement());
            }
        }
        return requirementsCache;
    }

    private static class IsRequirement implements Predicate<Task> {

        private scrum.server.project.Requirement value;

        public IsRequirement(scrum.server.project.Requirement value) {
            this.value = value;
        }

        public boolean test(Task e) {
            return e.isRequirement(value);
        }

    }

    // -----------------------------------------------------------
    // - number
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Task>> tasksByNumberCache = new Cache<Integer,Set<Task>>(
            new Cache.Factory<Integer,Set<Task>>() {
                public Set<Task> create(Integer number) {
                    return getEntities(new IsNumber(number));
                }
            });

    public final Set<Task> getTasksByNumber(int number) {
        return new HashSet<Task>(tasksByNumberCache.get(number));
    }
    private Set<Integer> numbersCache;

    public final Set<Integer> getNumbers() {
        if (numbersCache == null) {
            numbersCache = new HashSet<Integer>();
            for (Task e : getEntities()) {
                numbersCache.add(e.getNumber());
            }
        }
        return numbersCache;
    }

    private static class IsNumber implements Predicate<Task> {

        private int value;

        public IsNumber(int value) {
            this.value = value;
        }

        public boolean test(Task e) {
            return e.isNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - label
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Task>> tasksByLabelCache = new Cache<java.lang.String,Set<Task>>(
            new Cache.Factory<java.lang.String,Set<Task>>() {
                public Set<Task> create(java.lang.String label) {
                    return getEntities(new IsLabel(label));
                }
            });

    public final Set<Task> getTasksByLabel(java.lang.String label) {
        return new HashSet<Task>(tasksByLabelCache.get(label));
    }
    private Set<java.lang.String> labelsCache;

    public final Set<java.lang.String> getLabels() {
        if (labelsCache == null) {
            labelsCache = new HashSet<java.lang.String>();
            for (Task e : getEntities()) {
                if (e.isLabelSet()) labelsCache.add(e.getLabel());
            }
        }
        return labelsCache;
    }

    private static class IsLabel implements Predicate<Task> {

        private java.lang.String value;

        public IsLabel(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Task e) {
            return e.isLabel(value);
        }

    }

    // -----------------------------------------------------------
    // - description
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Task>> tasksByDescriptionCache = new Cache<java.lang.String,Set<Task>>(
            new Cache.Factory<java.lang.String,Set<Task>>() {
                public Set<Task> create(java.lang.String description) {
                    return getEntities(new IsDescription(description));
                }
            });

    public final Set<Task> getTasksByDescription(java.lang.String description) {
        return new HashSet<Task>(tasksByDescriptionCache.get(description));
    }
    private Set<java.lang.String> descriptionsCache;

    public final Set<java.lang.String> getDescriptions() {
        if (descriptionsCache == null) {
            descriptionsCache = new HashSet<java.lang.String>();
            for (Task e : getEntities()) {
                if (e.isDescriptionSet()) descriptionsCache.add(e.getDescription());
            }
        }
        return descriptionsCache;
    }

    private static class IsDescription implements Predicate<Task> {

        private java.lang.String value;

        public IsDescription(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Task e) {
            return e.isDescription(value);
        }

    }

    // -----------------------------------------------------------
    // - remainingWork
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Task>> tasksByRemainingWorkCache = new Cache<Integer,Set<Task>>(
            new Cache.Factory<Integer,Set<Task>>() {
                public Set<Task> create(Integer remainingWork) {
                    return getEntities(new IsRemainingWork(remainingWork));
                }
            });

    public final Set<Task> getTasksByRemainingWork(int remainingWork) {
        return new HashSet<Task>(tasksByRemainingWorkCache.get(remainingWork));
    }
    private Set<Integer> remainingWorksCache;

    public final Set<Integer> getRemainingWorks() {
        if (remainingWorksCache == null) {
            remainingWorksCache = new HashSet<Integer>();
            for (Task e : getEntities()) {
                remainingWorksCache.add(e.getRemainingWork());
            }
        }
        return remainingWorksCache;
    }

    private static class IsRemainingWork implements Predicate<Task> {

        private int value;

        public IsRemainingWork(int value) {
            this.value = value;
        }

        public boolean test(Task e) {
            return e.isRemainingWork(value);
        }

    }

    // -----------------------------------------------------------
    // - burnedWork
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Task>> tasksByBurnedWorkCache = new Cache<Integer,Set<Task>>(
            new Cache.Factory<Integer,Set<Task>>() {
                public Set<Task> create(Integer burnedWork) {
                    return getEntities(new IsBurnedWork(burnedWork));
                }
            });

    public final Set<Task> getTasksByBurnedWork(int burnedWork) {
        return new HashSet<Task>(tasksByBurnedWorkCache.get(burnedWork));
    }
    private Set<Integer> burnedWorksCache;

    public final Set<Integer> getBurnedWorks() {
        if (burnedWorksCache == null) {
            burnedWorksCache = new HashSet<Integer>();
            for (Task e : getEntities()) {
                burnedWorksCache.add(e.getBurnedWork());
            }
        }
        return burnedWorksCache;
    }

    private static class IsBurnedWork implements Predicate<Task> {

        private int value;

        public IsBurnedWork(int value) {
            this.value = value;
        }

        public boolean test(Task e) {
            return e.isBurnedWork(value);
        }

    }

    // -----------------------------------------------------------
    // - owner
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<Task>> tasksByOwnerCache = new Cache<scrum.server.admin.User,Set<Task>>(
            new Cache.Factory<scrum.server.admin.User,Set<Task>>() {
                public Set<Task> create(scrum.server.admin.User owner) {
                    return getEntities(new IsOwner(owner));
                }
            });

    public final Set<Task> getTasksByOwner(scrum.server.admin.User owner) {
        return new HashSet<Task>(tasksByOwnerCache.get(owner));
    }
    private Set<scrum.server.admin.User> ownersCache;

    public final Set<scrum.server.admin.User> getOwners() {
        if (ownersCache == null) {
            ownersCache = new HashSet<scrum.server.admin.User>();
            for (Task e : getEntities()) {
                if (e.isOwnerSet()) ownersCache.add(e.getOwner());
            }
        }
        return ownersCache;
    }

    private static class IsOwner implements Predicate<Task> {

        private scrum.server.admin.User value;

        public IsOwner(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(Task e) {
            return e.isOwner(value);
        }

    }

    // -----------------------------------------------------------
    // - impediment
    // -----------------------------------------------------------

    private final Cache<scrum.server.impediments.Impediment,Set<Task>> tasksByImpedimentCache = new Cache<scrum.server.impediments.Impediment,Set<Task>>(
            new Cache.Factory<scrum.server.impediments.Impediment,Set<Task>>() {
                public Set<Task> create(scrum.server.impediments.Impediment impediment) {
                    return getEntities(new IsImpediment(impediment));
                }
            });

    public final Set<Task> getTasksByImpediment(scrum.server.impediments.Impediment impediment) {
        return new HashSet<Task>(tasksByImpedimentCache.get(impediment));
    }
    private Set<scrum.server.impediments.Impediment> impedimentsCache;

    public final Set<scrum.server.impediments.Impediment> getImpediments() {
        if (impedimentsCache == null) {
            impedimentsCache = new HashSet<scrum.server.impediments.Impediment>();
            for (Task e : getEntities()) {
                if (e.isImpedimentSet()) impedimentsCache.add(e.getImpediment());
            }
        }
        return impedimentsCache;
    }

    private static class IsImpediment implements Predicate<Task> {

        private scrum.server.impediments.Impediment value;

        public IsImpediment(scrum.server.impediments.Impediment value) {
            this.value = value;
        }

        public boolean test(Task e) {
            return e.isImpediment(value);
        }

    }

    // -----------------------------------------------------------
    // - closedInPastSprint
    // -----------------------------------------------------------

    private final Cache<scrum.server.sprint.Sprint,Set<Task>> tasksByClosedInPastSprintCache = new Cache<scrum.server.sprint.Sprint,Set<Task>>(
            new Cache.Factory<scrum.server.sprint.Sprint,Set<Task>>() {
                public Set<Task> create(scrum.server.sprint.Sprint closedInPastSprint) {
                    return getEntities(new IsClosedInPastSprint(closedInPastSprint));
                }
            });

    public final Set<Task> getTasksByClosedInPastSprint(scrum.server.sprint.Sprint closedInPastSprint) {
        return new HashSet<Task>(tasksByClosedInPastSprintCache.get(closedInPastSprint));
    }
    private Set<scrum.server.sprint.Sprint> closedInPastSprintsCache;

    public final Set<scrum.server.sprint.Sprint> getClosedInPastSprints() {
        if (closedInPastSprintsCache == null) {
            closedInPastSprintsCache = new HashSet<scrum.server.sprint.Sprint>();
            for (Task e : getEntities()) {
                if (e.isClosedInPastSprintSet()) closedInPastSprintsCache.add(e.getClosedInPastSprint());
            }
        }
        return closedInPastSprintsCache;
    }

    private static class IsClosedInPastSprint implements Predicate<Task> {

        private scrum.server.sprint.Sprint value;

        public IsClosedInPastSprint(scrum.server.sprint.Sprint value) {
            this.value = value;
        }

        public boolean test(Task e) {
            return e.isClosedInPastSprint(value);
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

    scrum.server.impediments.ImpedimentDao impedimentDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public void setImpedimentDao(scrum.server.impediments.ImpedimentDao impedimentDao) {
        this.impedimentDao = impedimentDao;
    }

    scrum.server.sprint.SprintDao sprintDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public void setSprintDao(scrum.server.sprint.SprintDao sprintDao) {
        this.sprintDao = sprintDao;
    }

}