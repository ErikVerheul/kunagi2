// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.sprint;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GSprintReportDao
            extends ilarkesto.persistence.ADao<SprintReport> {

    public final String getEntityName() {
        return SprintReport.TYPE;
    }

    public final Class getEntityClass() {
        return SprintReport.class;
    }

    public Set<SprintReport> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<SprintReport>() {
            public boolean test(SprintReport e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        sprintsCache = null;
        sprintReportsByCompletedRequirementCache.clear();
        completedRequirementsCache = null;
        sprintReportsByRejectedRequirementCache.clear();
        rejectedRequirementsCache = null;
        sprintReportsByRequirementsOrderIdCache.clear();
        requirementsOrderIdsCache = null;
        sprintReportsByClosedTaskCache.clear();
        closedTasksCache = null;
        sprintReportsByOpenTaskCache.clear();
        openTasksCache = null;
        sprintReportsByBurnedWorkCache.clear();
        burnedWorksCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof SprintReport) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof SprintReport) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - sprint
    // -----------------------------------------------------------

    public final SprintReport getSprintReportBySprint(scrum.server.sprint.Sprint sprint) {
        return getEntity(new IsSprint(sprint));
    }
    private Set<scrum.server.sprint.Sprint> sprintsCache;

    public final Set<scrum.server.sprint.Sprint> getSprints() {
        if (sprintsCache == null) {
            sprintsCache = new HashSet<scrum.server.sprint.Sprint>();
            for (SprintReport e : getEntities()) {
                if (e.isSprintSet()) sprintsCache.add(e.getSprint());
            }
        }
        return sprintsCache;
    }

    private static class IsSprint implements Predicate<SprintReport> {

        private scrum.server.sprint.Sprint value;

        public IsSprint(scrum.server.sprint.Sprint value) {
            this.value = value;
        }

        public boolean test(SprintReport e) {
            return e.isSprint(value);
        }

    }

    // -----------------------------------------------------------
    // - completedRequirements
    // -----------------------------------------------------------

    private final Cache<scrum.server.project.Requirement,Set<SprintReport>> sprintReportsByCompletedRequirementCache = new Cache<scrum.server.project.Requirement,Set<SprintReport>>(
            new Cache.Factory<scrum.server.project.Requirement,Set<SprintReport>>() {
                public Set<SprintReport> create(scrum.server.project.Requirement completedRequirement) {
                    return getEntities(new ContainsCompletedRequirement(completedRequirement));
                }
            });

    public final Set<SprintReport> getSprintReportsByCompletedRequirement(scrum.server.project.Requirement completedRequirement) {
        return new HashSet<SprintReport>(sprintReportsByCompletedRequirementCache.get(completedRequirement));
    }
    private Set<scrum.server.project.Requirement> completedRequirementsCache;

    public final Set<scrum.server.project.Requirement> getCompletedRequirements() {
        if (completedRequirementsCache == null) {
            completedRequirementsCache = new HashSet<scrum.server.project.Requirement>();
            for (SprintReport e : getEntities()) {
                completedRequirementsCache.addAll(e.getCompletedRequirements());
            }
        }
        return completedRequirementsCache;
    }

    private static class ContainsCompletedRequirement implements Predicate<SprintReport> {

        private scrum.server.project.Requirement value;

        public ContainsCompletedRequirement(scrum.server.project.Requirement value) {
            this.value = value;
        }

        public boolean test(SprintReport e) {
            return e.containsCompletedRequirement(value);
        }

    }

    // -----------------------------------------------------------
    // - rejectedRequirements
    // -----------------------------------------------------------

    private final Cache<scrum.server.project.Requirement,Set<SprintReport>> sprintReportsByRejectedRequirementCache = new Cache<scrum.server.project.Requirement,Set<SprintReport>>(
            new Cache.Factory<scrum.server.project.Requirement,Set<SprintReport>>() {
                public Set<SprintReport> create(scrum.server.project.Requirement rejectedRequirement) {
                    return getEntities(new ContainsRejectedRequirement(rejectedRequirement));
                }
            });

    public final Set<SprintReport> getSprintReportsByRejectedRequirement(scrum.server.project.Requirement rejectedRequirement) {
        return new HashSet<SprintReport>(sprintReportsByRejectedRequirementCache.get(rejectedRequirement));
    }
    private Set<scrum.server.project.Requirement> rejectedRequirementsCache;

    public final Set<scrum.server.project.Requirement> getRejectedRequirements() {
        if (rejectedRequirementsCache == null) {
            rejectedRequirementsCache = new HashSet<scrum.server.project.Requirement>();
            for (SprintReport e : getEntities()) {
                rejectedRequirementsCache.addAll(e.getRejectedRequirements());
            }
        }
        return rejectedRequirementsCache;
    }

    private static class ContainsRejectedRequirement implements Predicate<SprintReport> {

        private scrum.server.project.Requirement value;

        public ContainsRejectedRequirement(scrum.server.project.Requirement value) {
            this.value = value;
        }

        public boolean test(SprintReport e) {
            return e.containsRejectedRequirement(value);
        }

    }

    // -----------------------------------------------------------
    // - requirementsOrderIds
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SprintReport>> sprintReportsByRequirementsOrderIdCache = new Cache<java.lang.String,Set<SprintReport>>(
            new Cache.Factory<java.lang.String,Set<SprintReport>>() {
                public Set<SprintReport> create(java.lang.String requirementsOrderId) {
                    return getEntities(new ContainsRequirementsOrderId(requirementsOrderId));
                }
            });

    public final Set<SprintReport> getSprintReportsByRequirementsOrderId(java.lang.String requirementsOrderId) {
        return new HashSet<SprintReport>(sprintReportsByRequirementsOrderIdCache.get(requirementsOrderId));
    }
    private Set<java.lang.String> requirementsOrderIdsCache;

    public final Set<java.lang.String> getRequirementsOrderIds() {
        if (requirementsOrderIdsCache == null) {
            requirementsOrderIdsCache = new HashSet<java.lang.String>();
            for (SprintReport e : getEntities()) {
                requirementsOrderIdsCache.addAll(e.getRequirementsOrderIds());
            }
        }
        return requirementsOrderIdsCache;
    }

    private static class ContainsRequirementsOrderId implements Predicate<SprintReport> {

        private java.lang.String value;

        public ContainsRequirementsOrderId(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SprintReport e) {
            return e.containsRequirementsOrderId(value);
        }

    }

    // -----------------------------------------------------------
    // - closedTasks
    // -----------------------------------------------------------

    private final Cache<scrum.server.sprint.Task,Set<SprintReport>> sprintReportsByClosedTaskCache = new Cache<scrum.server.sprint.Task,Set<SprintReport>>(
            new Cache.Factory<scrum.server.sprint.Task,Set<SprintReport>>() {
                public Set<SprintReport> create(scrum.server.sprint.Task closedTask) {
                    return getEntities(new ContainsClosedTask(closedTask));
                }
            });

    public final Set<SprintReport> getSprintReportsByClosedTask(scrum.server.sprint.Task closedTask) {
        return new HashSet<SprintReport>(sprintReportsByClosedTaskCache.get(closedTask));
    }
    private Set<scrum.server.sprint.Task> closedTasksCache;

    public final Set<scrum.server.sprint.Task> getClosedTasks() {
        if (closedTasksCache == null) {
            closedTasksCache = new HashSet<scrum.server.sprint.Task>();
            for (SprintReport e : getEntities()) {
                closedTasksCache.addAll(e.getClosedTasks());
            }
        }
        return closedTasksCache;
    }

    private static class ContainsClosedTask implements Predicate<SprintReport> {

        private scrum.server.sprint.Task value;

        public ContainsClosedTask(scrum.server.sprint.Task value) {
            this.value = value;
        }

        public boolean test(SprintReport e) {
            return e.containsClosedTask(value);
        }

    }

    // -----------------------------------------------------------
    // - openTasks
    // -----------------------------------------------------------

    private final Cache<scrum.server.sprint.Task,Set<SprintReport>> sprintReportsByOpenTaskCache = new Cache<scrum.server.sprint.Task,Set<SprintReport>>(
            new Cache.Factory<scrum.server.sprint.Task,Set<SprintReport>>() {
                public Set<SprintReport> create(scrum.server.sprint.Task openTask) {
                    return getEntities(new ContainsOpenTask(openTask));
                }
            });

    public final Set<SprintReport> getSprintReportsByOpenTask(scrum.server.sprint.Task openTask) {
        return new HashSet<SprintReport>(sprintReportsByOpenTaskCache.get(openTask));
    }
    private Set<scrum.server.sprint.Task> openTasksCache;

    public final Set<scrum.server.sprint.Task> getOpenTasks() {
        if (openTasksCache == null) {
            openTasksCache = new HashSet<scrum.server.sprint.Task>();
            for (SprintReport e : getEntities()) {
                openTasksCache.addAll(e.getOpenTasks());
            }
        }
        return openTasksCache;
    }

    private static class ContainsOpenTask implements Predicate<SprintReport> {

        private scrum.server.sprint.Task value;

        public ContainsOpenTask(scrum.server.sprint.Task value) {
            this.value = value;
        }

        public boolean test(SprintReport e) {
            return e.containsOpenTask(value);
        }

    }

    // -----------------------------------------------------------
    // - burnedWork
    // -----------------------------------------------------------

    private final Cache<Integer,Set<SprintReport>> sprintReportsByBurnedWorkCache = new Cache<Integer,Set<SprintReport>>(
            new Cache.Factory<Integer,Set<SprintReport>>() {
                public Set<SprintReport> create(Integer burnedWork) {
                    return getEntities(new IsBurnedWork(burnedWork));
                }
            });

    public final Set<SprintReport> getSprintReportsByBurnedWork(int burnedWork) {
        return new HashSet<SprintReport>(sprintReportsByBurnedWorkCache.get(burnedWork));
    }
    private Set<Integer> burnedWorksCache;

    public final Set<Integer> getBurnedWorks() {
        if (burnedWorksCache == null) {
            burnedWorksCache = new HashSet<Integer>();
            for (SprintReport e : getEntities()) {
                burnedWorksCache.add(e.getBurnedWork());
            }
        }
        return burnedWorksCache;
    }

    private static class IsBurnedWork implements Predicate<SprintReport> {

        private int value;

        public IsBurnedWork(int value) {
            this.value = value;
        }

        public boolean test(SprintReport e) {
            return e.isBurnedWork(value);
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

    scrum.server.project.RequirementDao requirementDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public void setRequirementDao(scrum.server.project.RequirementDao requirementDao) {
        this.requirementDao = requirementDao;
    }

    scrum.server.sprint.TaskDao taskDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public void setTaskDao(scrum.server.sprint.TaskDao taskDao) {
        this.taskDao = taskDao;
    }

}