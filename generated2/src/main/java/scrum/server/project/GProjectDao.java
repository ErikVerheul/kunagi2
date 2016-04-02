// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.project;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GProjectDao
            extends ilarkesto.persistence.ADao<Project> {

    public final String getEntityName() {
        return Project.TYPE;
    }

    public final Class getEntityClass() {
        return Project.class;
    }

    public Set<Project> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<Project>() {
            public boolean test(Project e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        labelsCache = null;
        projectsByVisionCache.clear();
        visionsCache = null;
        projectsByProductLabelCache.clear();
        productLabelsCache = null;
        projectsByShortDescriptionCache.clear();
        shortDescriptionsCache = null;
        projectsByDescriptionCache.clear();
        descriptionsCache = null;
        projectsByLongDescriptionCache.clear();
        longDescriptionsCache = null;
        projectsByBeginCache.clear();
        beginsCache = null;
        projectsByEndCache.clear();
        endsCache = null;
        projectsByParticipantCache.clear();
        participantsCache = null;
        projectsByAdminCache.clear();
        adminsCache = null;
        projectsByProductOwnerCache.clear();
        productOwnersCache = null;
        projectsByScrumMasterCache.clear();
        scrumMastersCache = null;
        projectsByTeamMemberCache.clear();
        teamMembersCache = null;
        projectsByCurrentSprintCache.clear();
        currentSprintsCache = null;
        projectsByNextSprintCache.clear();
        nextSprintsCache = null;
        projectsByVelocityCache.clear();
        velocitysCache = null;
        projectsByRequirementsOrderIdCache.clear();
        requirementsOrderIdsCache = null;
        projectsByUrgentIssuesOrderIdCache.clear();
        urgentIssuesOrderIdsCache = null;
        projectsByLastSprintNumberCache.clear();
        lastSprintNumbersCache = null;
        projectsByLastTaskNumberCache.clear();
        lastTaskNumbersCache = null;
        projectsByLastRequirementNumberCache.clear();
        lastRequirementNumbersCache = null;
        projectsByLastQualityNumberCache.clear();
        lastQualityNumbersCache = null;
        projectsByLastRiskNumberCache.clear();
        lastRiskNumbersCache = null;
        projectsByLastIssueNumberCache.clear();
        lastIssueNumbersCache = null;
        projectsByLastImpedimentNumberCache.clear();
        lastImpedimentNumbersCache = null;
        projectsByLastFileNumberCache.clear();
        lastFileNumbersCache = null;
        projectsByLastSubjectNumberCache.clear();
        lastSubjectNumbersCache = null;
        projectsByLastEventNumberCache.clear();
        lastEventNumbersCache = null;
        projectsByLastReleaseNumberCache.clear();
        lastReleaseNumbersCache = null;
        projectsByLastBlogEntryNumberCache.clear();
        lastBlogEntryNumbersCache = null;
        projectsByPunishmentUnitCache.clear();
        punishmentUnitsCache = null;
        projectsByPunishmentFactorCache.clear();
        punishmentFactorsCache = null;
        projectsByHomepageDirCache.clear();
        homepageDirsCache = null;
        projectsByHomepageUrlCache.clear();
        homepageUrlsCache = null;
        projectsByAutoUpdateHomepageCache.clear();
        projectsByReleaseScriptPathCache.clear();
        releaseScriptPathsCache = null;
        projectsBySupportEmailCache.clear();
        supportEmailsCache = null;
        projectsByIssueReplyTemplateCache.clear();
        issueReplyTemplatesCache = null;
        projectsBySubscriberNotificationTemplateCache.clear();
        subscriberNotificationTemplatesCache = null;
        projectsByLastOpenedDateAndTimeCache.clear();
        lastOpenedDateAndTimesCache = null;
        projectsByFreeDaysCache.clear();
        freeDayssCache = null;
        projectsByReleasingInfoCache.clear();
        releasingInfosCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof Project) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof Project) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - label
    // -----------------------------------------------------------

    public final Project getProjectByLabel(java.lang.String label) {
        return getEntity(new IsLabel(label));
    }
    private Set<java.lang.String> labelsCache;

    public final Set<java.lang.String> getLabels() {
        if (labelsCache == null) {
            labelsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isLabelSet()) labelsCache.add(e.getLabel());
            }
        }
        return labelsCache;
    }

    private static class IsLabel implements Predicate<Project> {

        private java.lang.String value;

        public IsLabel(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLabel(value);
        }

    }

    // -----------------------------------------------------------
    // - vision
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByVisionCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String vision) {
                    return getEntities(new IsVision(vision));
                }
            });

    public final Set<Project> getProjectsByVision(java.lang.String vision) {
        return new HashSet<Project>(projectsByVisionCache.get(vision));
    }
    private Set<java.lang.String> visionsCache;

    public final Set<java.lang.String> getVisions() {
        if (visionsCache == null) {
            visionsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isVisionSet()) visionsCache.add(e.getVision());
            }
        }
        return visionsCache;
    }

    private static class IsVision implements Predicate<Project> {

        private java.lang.String value;

        public IsVision(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isVision(value);
        }

    }

    // -----------------------------------------------------------
    // - productLabel
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByProductLabelCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String productLabel) {
                    return getEntities(new IsProductLabel(productLabel));
                }
            });

    public final Set<Project> getProjectsByProductLabel(java.lang.String productLabel) {
        return new HashSet<Project>(projectsByProductLabelCache.get(productLabel));
    }
    private Set<java.lang.String> productLabelsCache;

    public final Set<java.lang.String> getProductLabels() {
        if (productLabelsCache == null) {
            productLabelsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isProductLabelSet()) productLabelsCache.add(e.getProductLabel());
            }
        }
        return productLabelsCache;
    }

    private static class IsProductLabel implements Predicate<Project> {

        private java.lang.String value;

        public IsProductLabel(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isProductLabel(value);
        }

    }

    // -----------------------------------------------------------
    // - shortDescription
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByShortDescriptionCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String shortDescription) {
                    return getEntities(new IsShortDescription(shortDescription));
                }
            });

    public final Set<Project> getProjectsByShortDescription(java.lang.String shortDescription) {
        return new HashSet<Project>(projectsByShortDescriptionCache.get(shortDescription));
    }
    private Set<java.lang.String> shortDescriptionsCache;

    public final Set<java.lang.String> getShortDescriptions() {
        if (shortDescriptionsCache == null) {
            shortDescriptionsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isShortDescriptionSet()) shortDescriptionsCache.add(e.getShortDescription());
            }
        }
        return shortDescriptionsCache;
    }

    private static class IsShortDescription implements Predicate<Project> {

        private java.lang.String value;

        public IsShortDescription(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isShortDescription(value);
        }

    }

    // -----------------------------------------------------------
    // - description
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByDescriptionCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String description) {
                    return getEntities(new IsDescription(description));
                }
            });

    public final Set<Project> getProjectsByDescription(java.lang.String description) {
        return new HashSet<Project>(projectsByDescriptionCache.get(description));
    }
    private Set<java.lang.String> descriptionsCache;

    public final Set<java.lang.String> getDescriptions() {
        if (descriptionsCache == null) {
            descriptionsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isDescriptionSet()) descriptionsCache.add(e.getDescription());
            }
        }
        return descriptionsCache;
    }

    private static class IsDescription implements Predicate<Project> {

        private java.lang.String value;

        public IsDescription(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isDescription(value);
        }

    }

    // -----------------------------------------------------------
    // - longDescription
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByLongDescriptionCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String longDescription) {
                    return getEntities(new IsLongDescription(longDescription));
                }
            });

    public final Set<Project> getProjectsByLongDescription(java.lang.String longDescription) {
        return new HashSet<Project>(projectsByLongDescriptionCache.get(longDescription));
    }
    private Set<java.lang.String> longDescriptionsCache;

    public final Set<java.lang.String> getLongDescriptions() {
        if (longDescriptionsCache == null) {
            longDescriptionsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isLongDescriptionSet()) longDescriptionsCache.add(e.getLongDescription());
            }
        }
        return longDescriptionsCache;
    }

    private static class IsLongDescription implements Predicate<Project> {

        private java.lang.String value;

        public IsLongDescription(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLongDescription(value);
        }

    }

    // -----------------------------------------------------------
    // - begin
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.Date,Set<Project>> projectsByBeginCache = new Cache<ilarkesto.core.time.Date,Set<Project>>(
            new Cache.Factory<ilarkesto.core.time.Date,Set<Project>>() {
                public Set<Project> create(ilarkesto.core.time.Date begin) {
                    return getEntities(new IsBegin(begin));
                }
            });

    public final Set<Project> getProjectsByBegin(ilarkesto.core.time.Date begin) {
        return new HashSet<Project>(projectsByBeginCache.get(begin));
    }
    private Set<ilarkesto.core.time.Date> beginsCache;

    public final Set<ilarkesto.core.time.Date> getBegins() {
        if (beginsCache == null) {
            beginsCache = new HashSet<ilarkesto.core.time.Date>();
            for (Project e : getEntities()) {
                if (e.isBeginSet()) beginsCache.add(e.getBegin());
            }
        }
        return beginsCache;
    }

    private static class IsBegin implements Predicate<Project> {

        private ilarkesto.core.time.Date value;

        public IsBegin(ilarkesto.core.time.Date value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isBegin(value);
        }

    }

    // -----------------------------------------------------------
    // - end
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.Date,Set<Project>> projectsByEndCache = new Cache<ilarkesto.core.time.Date,Set<Project>>(
            new Cache.Factory<ilarkesto.core.time.Date,Set<Project>>() {
                public Set<Project> create(ilarkesto.core.time.Date end) {
                    return getEntities(new IsEnd(end));
                }
            });

    public final Set<Project> getProjectsByEnd(ilarkesto.core.time.Date end) {
        return new HashSet<Project>(projectsByEndCache.get(end));
    }
    private Set<ilarkesto.core.time.Date> endsCache;

    public final Set<ilarkesto.core.time.Date> getEnds() {
        if (endsCache == null) {
            endsCache = new HashSet<ilarkesto.core.time.Date>();
            for (Project e : getEntities()) {
                if (e.isEndSet()) endsCache.add(e.getEnd());
            }
        }
        return endsCache;
    }

    private static class IsEnd implements Predicate<Project> {

        private ilarkesto.core.time.Date value;

        public IsEnd(ilarkesto.core.time.Date value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isEnd(value);
        }

    }

    // -----------------------------------------------------------
    // - participants
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<Project>> projectsByParticipantCache = new Cache<scrum.server.admin.User,Set<Project>>(
            new Cache.Factory<scrum.server.admin.User,Set<Project>>() {
                public Set<Project> create(scrum.server.admin.User participant) {
                    return getEntities(new ContainsParticipant(participant));
                }
            });

    public final Set<Project> getProjectsByParticipant(scrum.server.admin.User participant) {
        return new HashSet<Project>(projectsByParticipantCache.get(participant));
    }
    private Set<scrum.server.admin.User> participantsCache;

    public final Set<scrum.server.admin.User> getParticipants() {
        if (participantsCache == null) {
            participantsCache = new HashSet<scrum.server.admin.User>();
            for (Project e : getEntities()) {
                participantsCache.addAll(e.getParticipants());
            }
        }
        return participantsCache;
    }

    private static class ContainsParticipant implements Predicate<Project> {

        private scrum.server.admin.User value;

        public ContainsParticipant(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.containsParticipant(value);
        }

    }

    // -----------------------------------------------------------
    // - admins
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<Project>> projectsByAdminCache = new Cache<scrum.server.admin.User,Set<Project>>(
            new Cache.Factory<scrum.server.admin.User,Set<Project>>() {
                public Set<Project> create(scrum.server.admin.User admin) {
                    return getEntities(new ContainsAdmin(admin));
                }
            });

    public final Set<Project> getProjectsByAdmin(scrum.server.admin.User admin) {
        return new HashSet<Project>(projectsByAdminCache.get(admin));
    }
    private Set<scrum.server.admin.User> adminsCache;

    public final Set<scrum.server.admin.User> getAdmins() {
        if (adminsCache == null) {
            adminsCache = new HashSet<scrum.server.admin.User>();
            for (Project e : getEntities()) {
                adminsCache.addAll(e.getAdmins());
            }
        }
        return adminsCache;
    }

    private static class ContainsAdmin implements Predicate<Project> {

        private scrum.server.admin.User value;

        public ContainsAdmin(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.containsAdmin(value);
        }

    }

    // -----------------------------------------------------------
    // - productOwners
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<Project>> projectsByProductOwnerCache = new Cache<scrum.server.admin.User,Set<Project>>(
            new Cache.Factory<scrum.server.admin.User,Set<Project>>() {
                public Set<Project> create(scrum.server.admin.User productOwner) {
                    return getEntities(new ContainsProductOwner(productOwner));
                }
            });

    public final Set<Project> getProjectsByProductOwner(scrum.server.admin.User productOwner) {
        return new HashSet<Project>(projectsByProductOwnerCache.get(productOwner));
    }
    private Set<scrum.server.admin.User> productOwnersCache;

    public final Set<scrum.server.admin.User> getProductOwners() {
        if (productOwnersCache == null) {
            productOwnersCache = new HashSet<scrum.server.admin.User>();
            for (Project e : getEntities()) {
                productOwnersCache.addAll(e.getProductOwners());
            }
        }
        return productOwnersCache;
    }

    private static class ContainsProductOwner implements Predicate<Project> {

        private scrum.server.admin.User value;

        public ContainsProductOwner(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.containsProductOwner(value);
        }

    }

    // -----------------------------------------------------------
    // - scrumMasters
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<Project>> projectsByScrumMasterCache = new Cache<scrum.server.admin.User,Set<Project>>(
            new Cache.Factory<scrum.server.admin.User,Set<Project>>() {
                public Set<Project> create(scrum.server.admin.User scrumMaster) {
                    return getEntities(new ContainsScrumMaster(scrumMaster));
                }
            });

    public final Set<Project> getProjectsByScrumMaster(scrum.server.admin.User scrumMaster) {
        return new HashSet<Project>(projectsByScrumMasterCache.get(scrumMaster));
    }
    private Set<scrum.server.admin.User> scrumMastersCache;

    public final Set<scrum.server.admin.User> getScrumMasters() {
        if (scrumMastersCache == null) {
            scrumMastersCache = new HashSet<scrum.server.admin.User>();
            for (Project e : getEntities()) {
                scrumMastersCache.addAll(e.getScrumMasters());
            }
        }
        return scrumMastersCache;
    }

    private static class ContainsScrumMaster implements Predicate<Project> {

        private scrum.server.admin.User value;

        public ContainsScrumMaster(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.containsScrumMaster(value);
        }

    }

    // -----------------------------------------------------------
    // - teamMembers
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<Project>> projectsByTeamMemberCache = new Cache<scrum.server.admin.User,Set<Project>>(
            new Cache.Factory<scrum.server.admin.User,Set<Project>>() {
                public Set<Project> create(scrum.server.admin.User teamMember) {
                    return getEntities(new ContainsTeamMember(teamMember));
                }
            });

    public final Set<Project> getProjectsByTeamMember(scrum.server.admin.User teamMember) {
        return new HashSet<Project>(projectsByTeamMemberCache.get(teamMember));
    }
    private Set<scrum.server.admin.User> teamMembersCache;

    public final Set<scrum.server.admin.User> getTeamMembers() {
        if (teamMembersCache == null) {
            teamMembersCache = new HashSet<scrum.server.admin.User>();
            for (Project e : getEntities()) {
                teamMembersCache.addAll(e.getTeamMembers());
            }
        }
        return teamMembersCache;
    }

    private static class ContainsTeamMember implements Predicate<Project> {

        private scrum.server.admin.User value;

        public ContainsTeamMember(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.containsTeamMember(value);
        }

    }

    // -----------------------------------------------------------
    // - currentSprint
    // -----------------------------------------------------------

    private final Cache<scrum.server.sprint.Sprint,Set<Project>> projectsByCurrentSprintCache = new Cache<scrum.server.sprint.Sprint,Set<Project>>(
            new Cache.Factory<scrum.server.sprint.Sprint,Set<Project>>() {
                public Set<Project> create(scrum.server.sprint.Sprint currentSprint) {
                    return getEntities(new IsCurrentSprint(currentSprint));
                }
            });

    public final Set<Project> getProjectsByCurrentSprint(scrum.server.sprint.Sprint currentSprint) {
        return new HashSet<Project>(projectsByCurrentSprintCache.get(currentSprint));
    }
    private Set<scrum.server.sprint.Sprint> currentSprintsCache;

    public final Set<scrum.server.sprint.Sprint> getCurrentSprints() {
        if (currentSprintsCache == null) {
            currentSprintsCache = new HashSet<scrum.server.sprint.Sprint>();
            for (Project e : getEntities()) {
                if (e.isCurrentSprintSet()) currentSprintsCache.add(e.getCurrentSprint());
            }
        }
        return currentSprintsCache;
    }

    private static class IsCurrentSprint implements Predicate<Project> {

        private scrum.server.sprint.Sprint value;

        public IsCurrentSprint(scrum.server.sprint.Sprint value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isCurrentSprint(value);
        }

    }

    // -----------------------------------------------------------
    // - nextSprint
    // -----------------------------------------------------------

    private final Cache<scrum.server.sprint.Sprint,Set<Project>> projectsByNextSprintCache = new Cache<scrum.server.sprint.Sprint,Set<Project>>(
            new Cache.Factory<scrum.server.sprint.Sprint,Set<Project>>() {
                public Set<Project> create(scrum.server.sprint.Sprint nextSprint) {
                    return getEntities(new IsNextSprint(nextSprint));
                }
            });

    public final Set<Project> getProjectsByNextSprint(scrum.server.sprint.Sprint nextSprint) {
        return new HashSet<Project>(projectsByNextSprintCache.get(nextSprint));
    }
    private Set<scrum.server.sprint.Sprint> nextSprintsCache;

    public final Set<scrum.server.sprint.Sprint> getNextSprints() {
        if (nextSprintsCache == null) {
            nextSprintsCache = new HashSet<scrum.server.sprint.Sprint>();
            for (Project e : getEntities()) {
                if (e.isNextSprintSet()) nextSprintsCache.add(e.getNextSprint());
            }
        }
        return nextSprintsCache;
    }

    private static class IsNextSprint implements Predicate<Project> {

        private scrum.server.sprint.Sprint value;

        public IsNextSprint(scrum.server.sprint.Sprint value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isNextSprint(value);
        }

    }

    // -----------------------------------------------------------
    // - velocity
    // -----------------------------------------------------------

    private final Cache<java.lang.Integer,Set<Project>> projectsByVelocityCache = new Cache<java.lang.Integer,Set<Project>>(
            new Cache.Factory<java.lang.Integer,Set<Project>>() {
                public Set<Project> create(java.lang.Integer velocity) {
                    return getEntities(new IsVelocity(velocity));
                }
            });

    public final Set<Project> getProjectsByVelocity(java.lang.Integer velocity) {
        return new HashSet<Project>(projectsByVelocityCache.get(velocity));
    }
    private Set<java.lang.Integer> velocitysCache;

    public final Set<java.lang.Integer> getVelocitys() {
        if (velocitysCache == null) {
            velocitysCache = new HashSet<java.lang.Integer>();
            for (Project e : getEntities()) {
                if (e.isVelocitySet()) velocitysCache.add(e.getVelocity());
            }
        }
        return velocitysCache;
    }

    private static class IsVelocity implements Predicate<Project> {

        private java.lang.Integer value;

        public IsVelocity(java.lang.Integer value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isVelocity(value);
        }

    }

    // -----------------------------------------------------------
    // - requirementsOrderIds
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByRequirementsOrderIdCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String requirementsOrderId) {
                    return getEntities(new ContainsRequirementsOrderId(requirementsOrderId));
                }
            });

    public final Set<Project> getProjectsByRequirementsOrderId(java.lang.String requirementsOrderId) {
        return new HashSet<Project>(projectsByRequirementsOrderIdCache.get(requirementsOrderId));
    }
    private Set<java.lang.String> requirementsOrderIdsCache;

    public final Set<java.lang.String> getRequirementsOrderIds() {
        if (requirementsOrderIdsCache == null) {
            requirementsOrderIdsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                requirementsOrderIdsCache.addAll(e.getRequirementsOrderIds());
            }
        }
        return requirementsOrderIdsCache;
    }

    private static class ContainsRequirementsOrderId implements Predicate<Project> {

        private java.lang.String value;

        public ContainsRequirementsOrderId(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.containsRequirementsOrderId(value);
        }

    }

    // -----------------------------------------------------------
    // - urgentIssuesOrderIds
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByUrgentIssuesOrderIdCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String urgentIssuesOrderId) {
                    return getEntities(new ContainsUrgentIssuesOrderId(urgentIssuesOrderId));
                }
            });

    public final Set<Project> getProjectsByUrgentIssuesOrderId(java.lang.String urgentIssuesOrderId) {
        return new HashSet<Project>(projectsByUrgentIssuesOrderIdCache.get(urgentIssuesOrderId));
    }
    private Set<java.lang.String> urgentIssuesOrderIdsCache;

    public final Set<java.lang.String> getUrgentIssuesOrderIds() {
        if (urgentIssuesOrderIdsCache == null) {
            urgentIssuesOrderIdsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                urgentIssuesOrderIdsCache.addAll(e.getUrgentIssuesOrderIds());
            }
        }
        return urgentIssuesOrderIdsCache;
    }

    private static class ContainsUrgentIssuesOrderId implements Predicate<Project> {

        private java.lang.String value;

        public ContainsUrgentIssuesOrderId(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.containsUrgentIssuesOrderId(value);
        }

    }

    // -----------------------------------------------------------
    // - lastSprintNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastSprintNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastSprintNumber) {
                    return getEntities(new IsLastSprintNumber(lastSprintNumber));
                }
            });

    public final Set<Project> getProjectsByLastSprintNumber(int lastSprintNumber) {
        return new HashSet<Project>(projectsByLastSprintNumberCache.get(lastSprintNumber));
    }
    private Set<Integer> lastSprintNumbersCache;

    public final Set<Integer> getLastSprintNumbers() {
        if (lastSprintNumbersCache == null) {
            lastSprintNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastSprintNumbersCache.add(e.getLastSprintNumber());
            }
        }
        return lastSprintNumbersCache;
    }

    private static class IsLastSprintNumber implements Predicate<Project> {

        private int value;

        public IsLastSprintNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastSprintNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - lastTaskNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastTaskNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastTaskNumber) {
                    return getEntities(new IsLastTaskNumber(lastTaskNumber));
                }
            });

    public final Set<Project> getProjectsByLastTaskNumber(int lastTaskNumber) {
        return new HashSet<Project>(projectsByLastTaskNumberCache.get(lastTaskNumber));
    }
    private Set<Integer> lastTaskNumbersCache;

    public final Set<Integer> getLastTaskNumbers() {
        if (lastTaskNumbersCache == null) {
            lastTaskNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastTaskNumbersCache.add(e.getLastTaskNumber());
            }
        }
        return lastTaskNumbersCache;
    }

    private static class IsLastTaskNumber implements Predicate<Project> {

        private int value;

        public IsLastTaskNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastTaskNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - lastRequirementNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastRequirementNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastRequirementNumber) {
                    return getEntities(new IsLastRequirementNumber(lastRequirementNumber));
                }
            });

    public final Set<Project> getProjectsByLastRequirementNumber(int lastRequirementNumber) {
        return new HashSet<Project>(projectsByLastRequirementNumberCache.get(lastRequirementNumber));
    }
    private Set<Integer> lastRequirementNumbersCache;

    public final Set<Integer> getLastRequirementNumbers() {
        if (lastRequirementNumbersCache == null) {
            lastRequirementNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastRequirementNumbersCache.add(e.getLastRequirementNumber());
            }
        }
        return lastRequirementNumbersCache;
    }

    private static class IsLastRequirementNumber implements Predicate<Project> {

        private int value;

        public IsLastRequirementNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastRequirementNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - lastQualityNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastQualityNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastQualityNumber) {
                    return getEntities(new IsLastQualityNumber(lastQualityNumber));
                }
            });

    public final Set<Project> getProjectsByLastQualityNumber(int lastQualityNumber) {
        return new HashSet<Project>(projectsByLastQualityNumberCache.get(lastQualityNumber));
    }
    private Set<Integer> lastQualityNumbersCache;

    public final Set<Integer> getLastQualityNumbers() {
        if (lastQualityNumbersCache == null) {
            lastQualityNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastQualityNumbersCache.add(e.getLastQualityNumber());
            }
        }
        return lastQualityNumbersCache;
    }

    private static class IsLastQualityNumber implements Predicate<Project> {

        private int value;

        public IsLastQualityNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastQualityNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - lastRiskNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastRiskNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastRiskNumber) {
                    return getEntities(new IsLastRiskNumber(lastRiskNumber));
                }
            });

    public final Set<Project> getProjectsByLastRiskNumber(int lastRiskNumber) {
        return new HashSet<Project>(projectsByLastRiskNumberCache.get(lastRiskNumber));
    }
    private Set<Integer> lastRiskNumbersCache;

    public final Set<Integer> getLastRiskNumbers() {
        if (lastRiskNumbersCache == null) {
            lastRiskNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastRiskNumbersCache.add(e.getLastRiskNumber());
            }
        }
        return lastRiskNumbersCache;
    }

    private static class IsLastRiskNumber implements Predicate<Project> {

        private int value;

        public IsLastRiskNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastRiskNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - lastIssueNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastIssueNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastIssueNumber) {
                    return getEntities(new IsLastIssueNumber(lastIssueNumber));
                }
            });

    public final Set<Project> getProjectsByLastIssueNumber(int lastIssueNumber) {
        return new HashSet<Project>(projectsByLastIssueNumberCache.get(lastIssueNumber));
    }
    private Set<Integer> lastIssueNumbersCache;

    public final Set<Integer> getLastIssueNumbers() {
        if (lastIssueNumbersCache == null) {
            lastIssueNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastIssueNumbersCache.add(e.getLastIssueNumber());
            }
        }
        return lastIssueNumbersCache;
    }

    private static class IsLastIssueNumber implements Predicate<Project> {

        private int value;

        public IsLastIssueNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastIssueNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - lastImpedimentNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastImpedimentNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastImpedimentNumber) {
                    return getEntities(new IsLastImpedimentNumber(lastImpedimentNumber));
                }
            });

    public final Set<Project> getProjectsByLastImpedimentNumber(int lastImpedimentNumber) {
        return new HashSet<Project>(projectsByLastImpedimentNumberCache.get(lastImpedimentNumber));
    }
    private Set<Integer> lastImpedimentNumbersCache;

    public final Set<Integer> getLastImpedimentNumbers() {
        if (lastImpedimentNumbersCache == null) {
            lastImpedimentNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastImpedimentNumbersCache.add(e.getLastImpedimentNumber());
            }
        }
        return lastImpedimentNumbersCache;
    }

    private static class IsLastImpedimentNumber implements Predicate<Project> {

        private int value;

        public IsLastImpedimentNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastImpedimentNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - lastFileNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastFileNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastFileNumber) {
                    return getEntities(new IsLastFileNumber(lastFileNumber));
                }
            });

    public final Set<Project> getProjectsByLastFileNumber(int lastFileNumber) {
        return new HashSet<Project>(projectsByLastFileNumberCache.get(lastFileNumber));
    }
    private Set<Integer> lastFileNumbersCache;

    public final Set<Integer> getLastFileNumbers() {
        if (lastFileNumbersCache == null) {
            lastFileNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastFileNumbersCache.add(e.getLastFileNumber());
            }
        }
        return lastFileNumbersCache;
    }

    private static class IsLastFileNumber implements Predicate<Project> {

        private int value;

        public IsLastFileNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastFileNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - lastSubjectNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastSubjectNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastSubjectNumber) {
                    return getEntities(new IsLastSubjectNumber(lastSubjectNumber));
                }
            });

    public final Set<Project> getProjectsByLastSubjectNumber(int lastSubjectNumber) {
        return new HashSet<Project>(projectsByLastSubjectNumberCache.get(lastSubjectNumber));
    }
    private Set<Integer> lastSubjectNumbersCache;

    public final Set<Integer> getLastSubjectNumbers() {
        if (lastSubjectNumbersCache == null) {
            lastSubjectNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastSubjectNumbersCache.add(e.getLastSubjectNumber());
            }
        }
        return lastSubjectNumbersCache;
    }

    private static class IsLastSubjectNumber implements Predicate<Project> {

        private int value;

        public IsLastSubjectNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastSubjectNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - lastEventNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastEventNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastEventNumber) {
                    return getEntities(new IsLastEventNumber(lastEventNumber));
                }
            });

    public final Set<Project> getProjectsByLastEventNumber(int lastEventNumber) {
        return new HashSet<Project>(projectsByLastEventNumberCache.get(lastEventNumber));
    }
    private Set<Integer> lastEventNumbersCache;

    public final Set<Integer> getLastEventNumbers() {
        if (lastEventNumbersCache == null) {
            lastEventNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastEventNumbersCache.add(e.getLastEventNumber());
            }
        }
        return lastEventNumbersCache;
    }

    private static class IsLastEventNumber implements Predicate<Project> {

        private int value;

        public IsLastEventNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastEventNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - lastReleaseNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastReleaseNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastReleaseNumber) {
                    return getEntities(new IsLastReleaseNumber(lastReleaseNumber));
                }
            });

    public final Set<Project> getProjectsByLastReleaseNumber(int lastReleaseNumber) {
        return new HashSet<Project>(projectsByLastReleaseNumberCache.get(lastReleaseNumber));
    }
    private Set<Integer> lastReleaseNumbersCache;

    public final Set<Integer> getLastReleaseNumbers() {
        if (lastReleaseNumbersCache == null) {
            lastReleaseNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastReleaseNumbersCache.add(e.getLastReleaseNumber());
            }
        }
        return lastReleaseNumbersCache;
    }

    private static class IsLastReleaseNumber implements Predicate<Project> {

        private int value;

        public IsLastReleaseNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastReleaseNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - lastBlogEntryNumber
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByLastBlogEntryNumberCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer lastBlogEntryNumber) {
                    return getEntities(new IsLastBlogEntryNumber(lastBlogEntryNumber));
                }
            });

    public final Set<Project> getProjectsByLastBlogEntryNumber(int lastBlogEntryNumber) {
        return new HashSet<Project>(projectsByLastBlogEntryNumberCache.get(lastBlogEntryNumber));
    }
    private Set<Integer> lastBlogEntryNumbersCache;

    public final Set<Integer> getLastBlogEntryNumbers() {
        if (lastBlogEntryNumbersCache == null) {
            lastBlogEntryNumbersCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                lastBlogEntryNumbersCache.add(e.getLastBlogEntryNumber());
            }
        }
        return lastBlogEntryNumbersCache;
    }

    private static class IsLastBlogEntryNumber implements Predicate<Project> {

        private int value;

        public IsLastBlogEntryNumber(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastBlogEntryNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - punishmentUnit
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByPunishmentUnitCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String punishmentUnit) {
                    return getEntities(new IsPunishmentUnit(punishmentUnit));
                }
            });

    public final Set<Project> getProjectsByPunishmentUnit(java.lang.String punishmentUnit) {
        return new HashSet<Project>(projectsByPunishmentUnitCache.get(punishmentUnit));
    }
    private Set<java.lang.String> punishmentUnitsCache;

    public final Set<java.lang.String> getPunishmentUnits() {
        if (punishmentUnitsCache == null) {
            punishmentUnitsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isPunishmentUnitSet()) punishmentUnitsCache.add(e.getPunishmentUnit());
            }
        }
        return punishmentUnitsCache;
    }

    private static class IsPunishmentUnit implements Predicate<Project> {

        private java.lang.String value;

        public IsPunishmentUnit(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isPunishmentUnit(value);
        }

    }

    // -----------------------------------------------------------
    // - punishmentFactor
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByPunishmentFactorCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer punishmentFactor) {
                    return getEntities(new IsPunishmentFactor(punishmentFactor));
                }
            });

    public final Set<Project> getProjectsByPunishmentFactor(int punishmentFactor) {
        return new HashSet<Project>(projectsByPunishmentFactorCache.get(punishmentFactor));
    }
    private Set<Integer> punishmentFactorsCache;

    public final Set<Integer> getPunishmentFactors() {
        if (punishmentFactorsCache == null) {
            punishmentFactorsCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                punishmentFactorsCache.add(e.getPunishmentFactor());
            }
        }
        return punishmentFactorsCache;
    }

    private static class IsPunishmentFactor implements Predicate<Project> {

        private int value;

        public IsPunishmentFactor(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isPunishmentFactor(value);
        }

    }

    // -----------------------------------------------------------
    // - homepageDir
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByHomepageDirCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String homepageDir) {
                    return getEntities(new IsHomepageDir(homepageDir));
                }
            });

    public final Set<Project> getProjectsByHomepageDir(java.lang.String homepageDir) {
        return new HashSet<Project>(projectsByHomepageDirCache.get(homepageDir));
    }
    private Set<java.lang.String> homepageDirsCache;

    public final Set<java.lang.String> getHomepageDirs() {
        if (homepageDirsCache == null) {
            homepageDirsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isHomepageDirSet()) homepageDirsCache.add(e.getHomepageDir());
            }
        }
        return homepageDirsCache;
    }

    private static class IsHomepageDir implements Predicate<Project> {

        private java.lang.String value;

        public IsHomepageDir(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isHomepageDir(value);
        }

    }

    // -----------------------------------------------------------
    // - homepageUrl
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByHomepageUrlCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String homepageUrl) {
                    return getEntities(new IsHomepageUrl(homepageUrl));
                }
            });

    public final Set<Project> getProjectsByHomepageUrl(java.lang.String homepageUrl) {
        return new HashSet<Project>(projectsByHomepageUrlCache.get(homepageUrl));
    }
    private Set<java.lang.String> homepageUrlsCache;

    public final Set<java.lang.String> getHomepageUrls() {
        if (homepageUrlsCache == null) {
            homepageUrlsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isHomepageUrlSet()) homepageUrlsCache.add(e.getHomepageUrl());
            }
        }
        return homepageUrlsCache;
    }

    private static class IsHomepageUrl implements Predicate<Project> {

        private java.lang.String value;

        public IsHomepageUrl(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isHomepageUrl(value);
        }

    }

    // -----------------------------------------------------------
    // - autoUpdateHomepage
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<Project>> projectsByAutoUpdateHomepageCache = new Cache<Boolean,Set<Project>>(
            new Cache.Factory<Boolean,Set<Project>>() {
                public Set<Project> create(Boolean autoUpdateHomepage) {
                    return getEntities(new IsAutoUpdateHomepage(autoUpdateHomepage));
                }
            });

    public final Set<Project> getProjectsByAutoUpdateHomepage(boolean autoUpdateHomepage) {
        return new HashSet<Project>(projectsByAutoUpdateHomepageCache.get(autoUpdateHomepage));
    }

    private static class IsAutoUpdateHomepage implements Predicate<Project> {

        private boolean value;

        public IsAutoUpdateHomepage(boolean value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return value == e.isAutoUpdateHomepage();
        }

    }

    // -----------------------------------------------------------
    // - releaseScriptPath
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByReleaseScriptPathCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String releaseScriptPath) {
                    return getEntities(new IsReleaseScriptPath(releaseScriptPath));
                }
            });

    public final Set<Project> getProjectsByReleaseScriptPath(java.lang.String releaseScriptPath) {
        return new HashSet<Project>(projectsByReleaseScriptPathCache.get(releaseScriptPath));
    }
    private Set<java.lang.String> releaseScriptPathsCache;

    public final Set<java.lang.String> getReleaseScriptPaths() {
        if (releaseScriptPathsCache == null) {
            releaseScriptPathsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isReleaseScriptPathSet()) releaseScriptPathsCache.add(e.getReleaseScriptPath());
            }
        }
        return releaseScriptPathsCache;
    }

    private static class IsReleaseScriptPath implements Predicate<Project> {

        private java.lang.String value;

        public IsReleaseScriptPath(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isReleaseScriptPath(value);
        }

    }

    // -----------------------------------------------------------
    // - supportEmail
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsBySupportEmailCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String supportEmail) {
                    return getEntities(new IsSupportEmail(supportEmail));
                }
            });

    public final Set<Project> getProjectsBySupportEmail(java.lang.String supportEmail) {
        return new HashSet<Project>(projectsBySupportEmailCache.get(supportEmail));
    }
    private Set<java.lang.String> supportEmailsCache;

    public final Set<java.lang.String> getSupportEmails() {
        if (supportEmailsCache == null) {
            supportEmailsCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isSupportEmailSet()) supportEmailsCache.add(e.getSupportEmail());
            }
        }
        return supportEmailsCache;
    }

    private static class IsSupportEmail implements Predicate<Project> {

        private java.lang.String value;

        public IsSupportEmail(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isSupportEmail(value);
        }

    }

    // -----------------------------------------------------------
    // - issueReplyTemplate
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByIssueReplyTemplateCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String issueReplyTemplate) {
                    return getEntities(new IsIssueReplyTemplate(issueReplyTemplate));
                }
            });

    public final Set<Project> getProjectsByIssueReplyTemplate(java.lang.String issueReplyTemplate) {
        return new HashSet<Project>(projectsByIssueReplyTemplateCache.get(issueReplyTemplate));
    }
    private Set<java.lang.String> issueReplyTemplatesCache;

    public final Set<java.lang.String> getIssueReplyTemplates() {
        if (issueReplyTemplatesCache == null) {
            issueReplyTemplatesCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isIssueReplyTemplateSet()) issueReplyTemplatesCache.add(e.getIssueReplyTemplate());
            }
        }
        return issueReplyTemplatesCache;
    }

    private static class IsIssueReplyTemplate implements Predicate<Project> {

        private java.lang.String value;

        public IsIssueReplyTemplate(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isIssueReplyTemplate(value);
        }

    }

    // -----------------------------------------------------------
    // - subscriberNotificationTemplate
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsBySubscriberNotificationTemplateCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String subscriberNotificationTemplate) {
                    return getEntities(new IsSubscriberNotificationTemplate(subscriberNotificationTemplate));
                }
            });

    public final Set<Project> getProjectsBySubscriberNotificationTemplate(java.lang.String subscriberNotificationTemplate) {
        return new HashSet<Project>(projectsBySubscriberNotificationTemplateCache.get(subscriberNotificationTemplate));
    }
    private Set<java.lang.String> subscriberNotificationTemplatesCache;

    public final Set<java.lang.String> getSubscriberNotificationTemplates() {
        if (subscriberNotificationTemplatesCache == null) {
            subscriberNotificationTemplatesCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isSubscriberNotificationTemplateSet()) subscriberNotificationTemplatesCache.add(e.getSubscriberNotificationTemplate());
            }
        }
        return subscriberNotificationTemplatesCache;
    }

    private static class IsSubscriberNotificationTemplate implements Predicate<Project> {

        private java.lang.String value;

        public IsSubscriberNotificationTemplate(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isSubscriberNotificationTemplate(value);
        }

    }

    // -----------------------------------------------------------
    // - lastOpenedDateAndTime
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.DateAndTime,Set<Project>> projectsByLastOpenedDateAndTimeCache = new Cache<ilarkesto.core.time.DateAndTime,Set<Project>>(
            new Cache.Factory<ilarkesto.core.time.DateAndTime,Set<Project>>() {
                public Set<Project> create(ilarkesto.core.time.DateAndTime lastOpenedDateAndTime) {
                    return getEntities(new IsLastOpenedDateAndTime(lastOpenedDateAndTime));
                }
            });

    public final Set<Project> getProjectsByLastOpenedDateAndTime(ilarkesto.core.time.DateAndTime lastOpenedDateAndTime) {
        return new HashSet<Project>(projectsByLastOpenedDateAndTimeCache.get(lastOpenedDateAndTime));
    }
    private Set<ilarkesto.core.time.DateAndTime> lastOpenedDateAndTimesCache;

    public final Set<ilarkesto.core.time.DateAndTime> getLastOpenedDateAndTimes() {
        if (lastOpenedDateAndTimesCache == null) {
            lastOpenedDateAndTimesCache = new HashSet<ilarkesto.core.time.DateAndTime>();
            for (Project e : getEntities()) {
                if (e.isLastOpenedDateAndTimeSet()) lastOpenedDateAndTimesCache.add(e.getLastOpenedDateAndTime());
            }
        }
        return lastOpenedDateAndTimesCache;
    }

    private static class IsLastOpenedDateAndTime implements Predicate<Project> {

        private ilarkesto.core.time.DateAndTime value;

        public IsLastOpenedDateAndTime(ilarkesto.core.time.DateAndTime value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isLastOpenedDateAndTime(value);
        }

    }

    // -----------------------------------------------------------
    // - freeDays
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Project>> projectsByFreeDaysCache = new Cache<Integer,Set<Project>>(
            new Cache.Factory<Integer,Set<Project>>() {
                public Set<Project> create(Integer freeDays) {
                    return getEntities(new IsFreeDays(freeDays));
                }
            });

    public final Set<Project> getProjectsByFreeDays(int freeDays) {
        return new HashSet<Project>(projectsByFreeDaysCache.get(freeDays));
    }
    private Set<Integer> freeDayssCache;

    public final Set<Integer> getFreeDayss() {
        if (freeDayssCache == null) {
            freeDayssCache = new HashSet<Integer>();
            for (Project e : getEntities()) {
                freeDayssCache.add(e.getFreeDays());
            }
        }
        return freeDayssCache;
    }

    private static class IsFreeDays implements Predicate<Project> {

        private int value;

        public IsFreeDays(int value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isFreeDays(value);
        }

    }

    // -----------------------------------------------------------
    // - releasingInfo
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Project>> projectsByReleasingInfoCache = new Cache<java.lang.String,Set<Project>>(
            new Cache.Factory<java.lang.String,Set<Project>>() {
                public Set<Project> create(java.lang.String releasingInfo) {
                    return getEntities(new IsReleasingInfo(releasingInfo));
                }
            });

    public final Set<Project> getProjectsByReleasingInfo(java.lang.String releasingInfo) {
        return new HashSet<Project>(projectsByReleasingInfoCache.get(releasingInfo));
    }
    private Set<java.lang.String> releasingInfosCache;

    public final Set<java.lang.String> getReleasingInfos() {
        if (releasingInfosCache == null) {
            releasingInfosCache = new HashSet<java.lang.String>();
            for (Project e : getEntities()) {
                if (e.isReleasingInfoSet()) releasingInfosCache.add(e.getReleasingInfo());
            }
        }
        return releasingInfosCache;
    }

    private static class IsReleasingInfo implements Predicate<Project> {

        private java.lang.String value;

        public IsReleasingInfo(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Project e) {
            return e.isReleasingInfo(value);
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