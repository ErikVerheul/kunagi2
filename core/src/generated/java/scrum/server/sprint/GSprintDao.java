// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.sprint;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GSprintDao
            extends ilarkesto.persistence.ADao<Sprint> {

    public final String getEntityName() {
        return Sprint.TYPE;
    }

    public final Class getEntityClass() {
        return Sprint.class;
    }

    public Set<Sprint> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<Sprint>() {
            public boolean test(Sprint e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        sprintsByNumberCache.clear();
        numbersCache = null;
        sprintsByProjectCache.clear();
        projectsCache = null;
        sprintsByLabelCache.clear();
        labelsCache = null;
        sprintsByGoalCache.clear();
        goalsCache = null;
        sprintsByBeginCache.clear();
        beginsCache = null;
        sprintsByEndCache.clear();
        endsCache = null;
        sprintsByVelocityCache.clear();
        velocitysCache = null;
        sprintsByCompletedRequirementsDataCache.clear();
        completedRequirementsDatasCache = null;
        sprintsByIncompletedRequirementsDataCache.clear();
        incompletedRequirementsDatasCache = null;
        sprintsByPlanningNoteCache.clear();
        planningNotesCache = null;
        sprintsByReviewNoteCache.clear();
        reviewNotesCache = null;
        sprintsByRetrospectiveNoteCache.clear();
        retrospectiveNotesCache = null;
        sprintsByRequirementsOrderIdCache.clear();
        requirementsOrderIdsCache = null;
        sprintsByProductOwnerCache.clear();
        productOwnersCache = null;
        sprintsByScrumMasterCache.clear();
        scrumMastersCache = null;
        sprintsByTeamMemberCache.clear();
        teamMembersCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof Sprint) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof Sprint) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - number
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Sprint>> sprintsByNumberCache = new Cache<Integer,Set<Sprint>>(
            new Cache.Factory<Integer,Set<Sprint>>() {
                public Set<Sprint> create(Integer number) {
                    return getEntities(new IsNumber(number));
                }
            });

    public final Set<Sprint> getSprintsByNumber(int number) {
        return new HashSet<Sprint>(sprintsByNumberCache.get(number));
    }
    private Set<Integer> numbersCache;

    public final Set<Integer> getNumbers() {
        if (numbersCache == null) {
            numbersCache = new HashSet<Integer>();
            for (Sprint e : getEntities()) {
                numbersCache.add(e.getNumber());
            }
        }
        return numbersCache;
    }

    private static class IsNumber implements Predicate<Sprint> {

        private int value;

        public IsNumber(int value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - project
    // -----------------------------------------------------------

    private final Cache<scrum.server.project.Project,Set<Sprint>> sprintsByProjectCache = new Cache<scrum.server.project.Project,Set<Sprint>>(
            new Cache.Factory<scrum.server.project.Project,Set<Sprint>>() {
                public Set<Sprint> create(scrum.server.project.Project project) {
                    return getEntities(new IsProject(project));
                }
            });

    public final Set<Sprint> getSprintsByProject(scrum.server.project.Project project) {
        return new HashSet<Sprint>(sprintsByProjectCache.get(project));
    }
    private Set<scrum.server.project.Project> projectsCache;

    public final Set<scrum.server.project.Project> getProjects() {
        if (projectsCache == null) {
            projectsCache = new HashSet<scrum.server.project.Project>();
            for (Sprint e : getEntities()) {
                if (e.isProjectSet()) projectsCache.add(e.getProject());
            }
        }
        return projectsCache;
    }

    private static class IsProject implements Predicate<Sprint> {

        private scrum.server.project.Project value;

        public IsProject(scrum.server.project.Project value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isProject(value);
        }

    }

    // -----------------------------------------------------------
    // - label
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Sprint>> sprintsByLabelCache = new Cache<java.lang.String,Set<Sprint>>(
            new Cache.Factory<java.lang.String,Set<Sprint>>() {
                public Set<Sprint> create(java.lang.String label) {
                    return getEntities(new IsLabel(label));
                }
            });

    public final Set<Sprint> getSprintsByLabel(java.lang.String label) {
        return new HashSet<Sprint>(sprintsByLabelCache.get(label));
    }
    private Set<java.lang.String> labelsCache;

    public final Set<java.lang.String> getLabels() {
        if (labelsCache == null) {
            labelsCache = new HashSet<java.lang.String>();
            for (Sprint e : getEntities()) {
                if (e.isLabelSet()) labelsCache.add(e.getLabel());
            }
        }
        return labelsCache;
    }

    private static class IsLabel implements Predicate<Sprint> {

        private java.lang.String value;

        public IsLabel(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isLabel(value);
        }

    }

    // -----------------------------------------------------------
    // - goal
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Sprint>> sprintsByGoalCache = new Cache<java.lang.String,Set<Sprint>>(
            new Cache.Factory<java.lang.String,Set<Sprint>>() {
                public Set<Sprint> create(java.lang.String goal) {
                    return getEntities(new IsGoal(goal));
                }
            });

    public final Set<Sprint> getSprintsByGoal(java.lang.String goal) {
        return new HashSet<Sprint>(sprintsByGoalCache.get(goal));
    }
    private Set<java.lang.String> goalsCache;

    public final Set<java.lang.String> getGoals() {
        if (goalsCache == null) {
            goalsCache = new HashSet<java.lang.String>();
            for (Sprint e : getEntities()) {
                if (e.isGoalSet()) goalsCache.add(e.getGoal());
            }
        }
        return goalsCache;
    }

    private static class IsGoal implements Predicate<Sprint> {

        private java.lang.String value;

        public IsGoal(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isGoal(value);
        }

    }

    // -----------------------------------------------------------
    // - begin
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.Date,Set<Sprint>> sprintsByBeginCache = new Cache<ilarkesto.core.time.Date,Set<Sprint>>(
            new Cache.Factory<ilarkesto.core.time.Date,Set<Sprint>>() {
                public Set<Sprint> create(ilarkesto.core.time.Date begin) {
                    return getEntities(new IsBegin(begin));
                }
            });

    public final Set<Sprint> getSprintsByBegin(ilarkesto.core.time.Date begin) {
        return new HashSet<Sprint>(sprintsByBeginCache.get(begin));
    }
    private Set<ilarkesto.core.time.Date> beginsCache;

    public final Set<ilarkesto.core.time.Date> getBegins() {
        if (beginsCache == null) {
            beginsCache = new HashSet<ilarkesto.core.time.Date>();
            for (Sprint e : getEntities()) {
                if (e.isBeginSet()) beginsCache.add(e.getBegin());
            }
        }
        return beginsCache;
    }

    private static class IsBegin implements Predicate<Sprint> {

        private ilarkesto.core.time.Date value;

        public IsBegin(ilarkesto.core.time.Date value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isBegin(value);
        }

    }

    // -----------------------------------------------------------
    // - end
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.Date,Set<Sprint>> sprintsByEndCache = new Cache<ilarkesto.core.time.Date,Set<Sprint>>(
            new Cache.Factory<ilarkesto.core.time.Date,Set<Sprint>>() {
                public Set<Sprint> create(ilarkesto.core.time.Date end) {
                    return getEntities(new IsEnd(end));
                }
            });

    public final Set<Sprint> getSprintsByEnd(ilarkesto.core.time.Date end) {
        return new HashSet<Sprint>(sprintsByEndCache.get(end));
    }
    private Set<ilarkesto.core.time.Date> endsCache;

    public final Set<ilarkesto.core.time.Date> getEnds() {
        if (endsCache == null) {
            endsCache = new HashSet<ilarkesto.core.time.Date>();
            for (Sprint e : getEntities()) {
                if (e.isEndSet()) endsCache.add(e.getEnd());
            }
        }
        return endsCache;
    }

    private static class IsEnd implements Predicate<Sprint> {

        private ilarkesto.core.time.Date value;

        public IsEnd(ilarkesto.core.time.Date value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isEnd(value);
        }

    }

    // -----------------------------------------------------------
    // - velocity
    // -----------------------------------------------------------

    private final Cache<java.lang.Float,Set<Sprint>> sprintsByVelocityCache = new Cache<java.lang.Float,Set<Sprint>>(
            new Cache.Factory<java.lang.Float,Set<Sprint>>() {
                public Set<Sprint> create(java.lang.Float velocity) {
                    return getEntities(new IsVelocity(velocity));
                }
            });

    public final Set<Sprint> getSprintsByVelocity(java.lang.Float velocity) {
        return new HashSet<Sprint>(sprintsByVelocityCache.get(velocity));
    }
    private Set<java.lang.Float> velocitysCache;

    public final Set<java.lang.Float> getVelocitys() {
        if (velocitysCache == null) {
            velocitysCache = new HashSet<java.lang.Float>();
            for (Sprint e : getEntities()) {
                if (e.isVelocitySet()) velocitysCache.add(e.getVelocity());
            }
        }
        return velocitysCache;
    }

    private static class IsVelocity implements Predicate<Sprint> {

        private java.lang.Float value;

        public IsVelocity(java.lang.Float value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isVelocity(value);
        }

    }

    // -----------------------------------------------------------
    // - completedRequirementsData
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Sprint>> sprintsByCompletedRequirementsDataCache = new Cache<java.lang.String,Set<Sprint>>(
            new Cache.Factory<java.lang.String,Set<Sprint>>() {
                public Set<Sprint> create(java.lang.String completedRequirementsData) {
                    return getEntities(new IsCompletedRequirementsData(completedRequirementsData));
                }
            });

    public final Set<Sprint> getSprintsByCompletedRequirementsData(java.lang.String completedRequirementsData) {
        return new HashSet<Sprint>(sprintsByCompletedRequirementsDataCache.get(completedRequirementsData));
    }
    private Set<java.lang.String> completedRequirementsDatasCache;

    public final Set<java.lang.String> getCompletedRequirementsDatas() {
        if (completedRequirementsDatasCache == null) {
            completedRequirementsDatasCache = new HashSet<java.lang.String>();
            for (Sprint e : getEntities()) {
                if (e.isCompletedRequirementsDataSet()) completedRequirementsDatasCache.add(e.getCompletedRequirementsData());
            }
        }
        return completedRequirementsDatasCache;
    }

    private static class IsCompletedRequirementsData implements Predicate<Sprint> {

        private java.lang.String value;

        public IsCompletedRequirementsData(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isCompletedRequirementsData(value);
        }

    }

    // -----------------------------------------------------------
    // - incompletedRequirementsData
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Sprint>> sprintsByIncompletedRequirementsDataCache = new Cache<java.lang.String,Set<Sprint>>(
            new Cache.Factory<java.lang.String,Set<Sprint>>() {
                public Set<Sprint> create(java.lang.String incompletedRequirementsData) {
                    return getEntities(new IsIncompletedRequirementsData(incompletedRequirementsData));
                }
            });

    public final Set<Sprint> getSprintsByIncompletedRequirementsData(java.lang.String incompletedRequirementsData) {
        return new HashSet<Sprint>(sprintsByIncompletedRequirementsDataCache.get(incompletedRequirementsData));
    }
    private Set<java.lang.String> incompletedRequirementsDatasCache;

    public final Set<java.lang.String> getIncompletedRequirementsDatas() {
        if (incompletedRequirementsDatasCache == null) {
            incompletedRequirementsDatasCache = new HashSet<java.lang.String>();
            for (Sprint e : getEntities()) {
                if (e.isIncompletedRequirementsDataSet()) incompletedRequirementsDatasCache.add(e.getIncompletedRequirementsData());
            }
        }
        return incompletedRequirementsDatasCache;
    }

    private static class IsIncompletedRequirementsData implements Predicate<Sprint> {

        private java.lang.String value;

        public IsIncompletedRequirementsData(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isIncompletedRequirementsData(value);
        }

    }

    // -----------------------------------------------------------
    // - planningNote
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Sprint>> sprintsByPlanningNoteCache = new Cache<java.lang.String,Set<Sprint>>(
            new Cache.Factory<java.lang.String,Set<Sprint>>() {
                public Set<Sprint> create(java.lang.String planningNote) {
                    return getEntities(new IsPlanningNote(planningNote));
                }
            });

    public final Set<Sprint> getSprintsByPlanningNote(java.lang.String planningNote) {
        return new HashSet<Sprint>(sprintsByPlanningNoteCache.get(planningNote));
    }
    private Set<java.lang.String> planningNotesCache;

    public final Set<java.lang.String> getPlanningNotes() {
        if (planningNotesCache == null) {
            planningNotesCache = new HashSet<java.lang.String>();
            for (Sprint e : getEntities()) {
                if (e.isPlanningNoteSet()) planningNotesCache.add(e.getPlanningNote());
            }
        }
        return planningNotesCache;
    }

    private static class IsPlanningNote implements Predicate<Sprint> {

        private java.lang.String value;

        public IsPlanningNote(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isPlanningNote(value);
        }

    }

    // -----------------------------------------------------------
    // - reviewNote
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Sprint>> sprintsByReviewNoteCache = new Cache<java.lang.String,Set<Sprint>>(
            new Cache.Factory<java.lang.String,Set<Sprint>>() {
                public Set<Sprint> create(java.lang.String reviewNote) {
                    return getEntities(new IsReviewNote(reviewNote));
                }
            });

    public final Set<Sprint> getSprintsByReviewNote(java.lang.String reviewNote) {
        return new HashSet<Sprint>(sprintsByReviewNoteCache.get(reviewNote));
    }
    private Set<java.lang.String> reviewNotesCache;

    public final Set<java.lang.String> getReviewNotes() {
        if (reviewNotesCache == null) {
            reviewNotesCache = new HashSet<java.lang.String>();
            for (Sprint e : getEntities()) {
                if (e.isReviewNoteSet()) reviewNotesCache.add(e.getReviewNote());
            }
        }
        return reviewNotesCache;
    }

    private static class IsReviewNote implements Predicate<Sprint> {

        private java.lang.String value;

        public IsReviewNote(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isReviewNote(value);
        }

    }

    // -----------------------------------------------------------
    // - retrospectiveNote
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Sprint>> sprintsByRetrospectiveNoteCache = new Cache<java.lang.String,Set<Sprint>>(
            new Cache.Factory<java.lang.String,Set<Sprint>>() {
                public Set<Sprint> create(java.lang.String retrospectiveNote) {
                    return getEntities(new IsRetrospectiveNote(retrospectiveNote));
                }
            });

    public final Set<Sprint> getSprintsByRetrospectiveNote(java.lang.String retrospectiveNote) {
        return new HashSet<Sprint>(sprintsByRetrospectiveNoteCache.get(retrospectiveNote));
    }
    private Set<java.lang.String> retrospectiveNotesCache;

    public final Set<java.lang.String> getRetrospectiveNotes() {
        if (retrospectiveNotesCache == null) {
            retrospectiveNotesCache = new HashSet<java.lang.String>();
            for (Sprint e : getEntities()) {
                if (e.isRetrospectiveNoteSet()) retrospectiveNotesCache.add(e.getRetrospectiveNote());
            }
        }
        return retrospectiveNotesCache;
    }

    private static class IsRetrospectiveNote implements Predicate<Sprint> {

        private java.lang.String value;

        public IsRetrospectiveNote(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.isRetrospectiveNote(value);
        }

    }

    // -----------------------------------------------------------
    // - requirementsOrderIds
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Sprint>> sprintsByRequirementsOrderIdCache = new Cache<java.lang.String,Set<Sprint>>(
            new Cache.Factory<java.lang.String,Set<Sprint>>() {
                public Set<Sprint> create(java.lang.String requirementsOrderId) {
                    return getEntities(new ContainsRequirementsOrderId(requirementsOrderId));
                }
            });

    public final Set<Sprint> getSprintsByRequirementsOrderId(java.lang.String requirementsOrderId) {
        return new HashSet<Sprint>(sprintsByRequirementsOrderIdCache.get(requirementsOrderId));
    }
    private Set<java.lang.String> requirementsOrderIdsCache;

    public final Set<java.lang.String> getRequirementsOrderIds() {
        if (requirementsOrderIdsCache == null) {
            requirementsOrderIdsCache = new HashSet<java.lang.String>();
            for (Sprint e : getEntities()) {
                requirementsOrderIdsCache.addAll(e.getRequirementsOrderIds());
            }
        }
        return requirementsOrderIdsCache;
    }

    private static class ContainsRequirementsOrderId implements Predicate<Sprint> {

        private java.lang.String value;

        public ContainsRequirementsOrderId(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.containsRequirementsOrderId(value);
        }

    }

    // -----------------------------------------------------------
    // - productOwners
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<Sprint>> sprintsByProductOwnerCache = new Cache<scrum.server.admin.User,Set<Sprint>>(
            new Cache.Factory<scrum.server.admin.User,Set<Sprint>>() {
                public Set<Sprint> create(scrum.server.admin.User productOwner) {
                    return getEntities(new ContainsProductOwner(productOwner));
                }
            });

    public final Set<Sprint> getSprintsByProductOwner(scrum.server.admin.User productOwner) {
        return new HashSet<Sprint>(sprintsByProductOwnerCache.get(productOwner));
    }
    private Set<scrum.server.admin.User> productOwnersCache;

    public final Set<scrum.server.admin.User> getProductOwners() {
        if (productOwnersCache == null) {
            productOwnersCache = new HashSet<scrum.server.admin.User>();
            for (Sprint e : getEntities()) {
                productOwnersCache.addAll(e.getProductOwners());
            }
        }
        return productOwnersCache;
    }

    private static class ContainsProductOwner implements Predicate<Sprint> {

        private scrum.server.admin.User value;

        public ContainsProductOwner(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.containsProductOwner(value);
        }

    }

    // -----------------------------------------------------------
    // - scrumMasters
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<Sprint>> sprintsByScrumMasterCache = new Cache<scrum.server.admin.User,Set<Sprint>>(
            new Cache.Factory<scrum.server.admin.User,Set<Sprint>>() {
                public Set<Sprint> create(scrum.server.admin.User scrumMaster) {
                    return getEntities(new ContainsScrumMaster(scrumMaster));
                }
            });

    public final Set<Sprint> getSprintsByScrumMaster(scrum.server.admin.User scrumMaster) {
        return new HashSet<Sprint>(sprintsByScrumMasterCache.get(scrumMaster));
    }
    private Set<scrum.server.admin.User> scrumMastersCache;

    public final Set<scrum.server.admin.User> getScrumMasters() {
        if (scrumMastersCache == null) {
            scrumMastersCache = new HashSet<scrum.server.admin.User>();
            for (Sprint e : getEntities()) {
                scrumMastersCache.addAll(e.getScrumMasters());
            }
        }
        return scrumMastersCache;
    }

    private static class ContainsScrumMaster implements Predicate<Sprint> {

        private scrum.server.admin.User value;

        public ContainsScrumMaster(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.containsScrumMaster(value);
        }

    }

    // -----------------------------------------------------------
    // - teamMembers
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<Sprint>> sprintsByTeamMemberCache = new Cache<scrum.server.admin.User,Set<Sprint>>(
            new Cache.Factory<scrum.server.admin.User,Set<Sprint>>() {
                public Set<Sprint> create(scrum.server.admin.User teamMember) {
                    return getEntities(new ContainsTeamMember(teamMember));
                }
            });

    public final Set<Sprint> getSprintsByTeamMember(scrum.server.admin.User teamMember) {
        return new HashSet<Sprint>(sprintsByTeamMemberCache.get(teamMember));
    }
    private Set<scrum.server.admin.User> teamMembersCache;

    public final Set<scrum.server.admin.User> getTeamMembers() {
        if (teamMembersCache == null) {
            teamMembersCache = new HashSet<scrum.server.admin.User>();
            for (Sprint e : getEntities()) {
                teamMembersCache.addAll(e.getTeamMembers());
            }
        }
        return teamMembersCache;
    }

    private static class ContainsTeamMember implements Predicate<Sprint> {

        private scrum.server.admin.User value;

        public ContainsTeamMember(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(Sprint e) {
            return e.containsTeamMember(value);
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

    scrum.server.project.ProjectDao projectDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public void setProjectDao(scrum.server.project.ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

}