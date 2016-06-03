// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.admin;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GProjectUserConfigDao
            extends ilarkesto.persistence.ADao<ProjectUserConfig> {

    public final String getEntityName() {
        return ProjectUserConfig.TYPE;
    }

    public final Class getEntityClass() {
        return ProjectUserConfig.class;
    }

    public Set<ProjectUserConfig> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<ProjectUserConfig>() {
            public boolean test(ProjectUserConfig e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        projectUserConfigsByProjectCache.clear();
        projectsCache = null;
        projectUserConfigsByUserCache.clear();
        usersCache = null;
        projectUserConfigsByColorCache.clear();
        colorsCache = null;
        projectUserConfigsByReceiveEmailsOnProjectEventsCache.clear();
        projectUserConfigsByMisconductsCache.clear();
        misconductssCache = null;
        projectUserConfigsByRichtextAutosaveTextCache.clear();
        richtextAutosaveTextsCache = null;
        projectUserConfigsByRichtextAutosaveFieldCache.clear();
        richtextAutosaveFieldsCache = null;
        projectUserConfigsBySelectedEntitysIdCache.clear();
        selectedEntitysIdsCache = null;
        projectUserConfigsByOnlineCache.clear();
        projectUserConfigsByLastActivityDateAndTimeCache.clear();
        lastActivityDateAndTimesCache = null;
        projectUserConfigsByPblFilterThemeCache.clear();
        pblFilterThemesCache = null;
        projectUserConfigsByPblFilterQualityCache.clear();
        pblFilterQualitysCache = null;
        projectUserConfigsByPblFilterDateFromCache.clear();
        pblFilterDateFromsCache = null;
        projectUserConfigsByPblFilterDateToCache.clear();
        pblFilterDateTosCache = null;
        projectUserConfigsByPblFilterEstimationFromCache.clear();
        pblFilterEstimationFromsCache = null;
        projectUserConfigsByPblFilterEstimationToCache.clear();
        pblFilterEstimationTosCache = null;
        projectUserConfigsByPblFilterTextCache.clear();
        pblFilterTextsCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof ProjectUserConfig) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof ProjectUserConfig) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - project
    // -----------------------------------------------------------

    private final Cache<scrum.server.project.Project,Set<ProjectUserConfig>> projectUserConfigsByProjectCache = new Cache<scrum.server.project.Project,Set<ProjectUserConfig>>(
            new Cache.Factory<scrum.server.project.Project,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(scrum.server.project.Project project) {
                    return getEntities(new IsProject(project));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByProject(scrum.server.project.Project project) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByProjectCache.get(project));
    }
    private Set<scrum.server.project.Project> projectsCache;

    public final Set<scrum.server.project.Project> getProjects() {
        if (projectsCache == null) {
            projectsCache = new HashSet<scrum.server.project.Project>();
            for (ProjectUserConfig e : getEntities()) {
                if (e.isProjectSet()) projectsCache.add(e.getProject());
            }
        }
        return projectsCache;
    }

    private static class IsProject implements Predicate<ProjectUserConfig> {

        private scrum.server.project.Project value;

        public IsProject(scrum.server.project.Project value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isProject(value);
        }

    }

    // -----------------------------------------------------------
    // - user
    // -----------------------------------------------------------

    private final Cache<scrum.server.admin.User,Set<ProjectUserConfig>> projectUserConfigsByUserCache = new Cache<scrum.server.admin.User,Set<ProjectUserConfig>>(
            new Cache.Factory<scrum.server.admin.User,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(scrum.server.admin.User user) {
                    return getEntities(new IsUser(user));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByUser(scrum.server.admin.User user) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByUserCache.get(user));
    }
    private Set<scrum.server.admin.User> usersCache;

    public final Set<scrum.server.admin.User> getUsers() {
        if (usersCache == null) {
            usersCache = new HashSet<scrum.server.admin.User>();
            for (ProjectUserConfig e : getEntities()) {
                if (e.isUserSet()) usersCache.add(e.getUser());
            }
        }
        return usersCache;
    }

    private static class IsUser implements Predicate<ProjectUserConfig> {

        private scrum.server.admin.User value;

        public IsUser(scrum.server.admin.User value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isUser(value);
        }

    }

    // -----------------------------------------------------------
    // - color
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<ProjectUserConfig>> projectUserConfigsByColorCache = new Cache<java.lang.String,Set<ProjectUserConfig>>(
            new Cache.Factory<java.lang.String,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(java.lang.String color) {
                    return getEntities(new IsColor(color));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByColor(java.lang.String color) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByColorCache.get(color));
    }
    private Set<java.lang.String> colorsCache;

    public final Set<java.lang.String> getColors() {
        if (colorsCache == null) {
            colorsCache = new HashSet<java.lang.String>();
            for (ProjectUserConfig e : getEntities()) {
                if (e.isColorSet()) colorsCache.add(e.getColor());
            }
        }
        return colorsCache;
    }

    private static class IsColor implements Predicate<ProjectUserConfig> {

        private java.lang.String value;

        public IsColor(java.lang.String value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isColor(value);
        }

    }

    // -----------------------------------------------------------
    // - receiveEmailsOnProjectEvents
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<ProjectUserConfig>> projectUserConfigsByReceiveEmailsOnProjectEventsCache = new Cache<Boolean,Set<ProjectUserConfig>>(
            new Cache.Factory<Boolean,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(Boolean receiveEmailsOnProjectEvents) {
                    return getEntities(new IsReceiveEmailsOnProjectEvents(receiveEmailsOnProjectEvents));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByReceiveEmailsOnProjectEvents(boolean receiveEmailsOnProjectEvents) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByReceiveEmailsOnProjectEventsCache.get(receiveEmailsOnProjectEvents));
    }

    private static class IsReceiveEmailsOnProjectEvents implements Predicate<ProjectUserConfig> {

        private boolean value;

        public IsReceiveEmailsOnProjectEvents(boolean value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return value == e.isReceiveEmailsOnProjectEvents();
        }

    }

    // -----------------------------------------------------------
    // - misconducts
    // -----------------------------------------------------------

    private final Cache<Integer,Set<ProjectUserConfig>> projectUserConfigsByMisconductsCache = new Cache<Integer,Set<ProjectUserConfig>>(
            new Cache.Factory<Integer,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(Integer misconducts) {
                    return getEntities(new IsMisconducts(misconducts));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByMisconducts(int misconducts) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByMisconductsCache.get(misconducts));
    }
    private Set<Integer> misconductssCache;

    public final Set<Integer> getMisconductss() {
        if (misconductssCache == null) {
            misconductssCache = new HashSet<Integer>();
            for (ProjectUserConfig e : getEntities()) {
                misconductssCache.add(e.getMisconducts());
            }
        }
        return misconductssCache;
    }

    private static class IsMisconducts implements Predicate<ProjectUserConfig> {

        private int value;

        public IsMisconducts(int value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isMisconducts(value);
        }

    }

    // -----------------------------------------------------------
    // - richtextAutosaveText
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<ProjectUserConfig>> projectUserConfigsByRichtextAutosaveTextCache = new Cache<java.lang.String,Set<ProjectUserConfig>>(
            new Cache.Factory<java.lang.String,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(java.lang.String richtextAutosaveText) {
                    return getEntities(new IsRichtextAutosaveText(richtextAutosaveText));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByRichtextAutosaveText(java.lang.String richtextAutosaveText) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByRichtextAutosaveTextCache.get(richtextAutosaveText));
    }
    private Set<java.lang.String> richtextAutosaveTextsCache;

    public final Set<java.lang.String> getRichtextAutosaveTexts() {
        if (richtextAutosaveTextsCache == null) {
            richtextAutosaveTextsCache = new HashSet<java.lang.String>();
            for (ProjectUserConfig e : getEntities()) {
                if (e.isRichtextAutosaveTextSet()) richtextAutosaveTextsCache.add(e.getRichtextAutosaveText());
            }
        }
        return richtextAutosaveTextsCache;
    }

    private static class IsRichtextAutosaveText implements Predicate<ProjectUserConfig> {

        private java.lang.String value;

        public IsRichtextAutosaveText(java.lang.String value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isRichtextAutosaveText(value);
        }

    }

    // -----------------------------------------------------------
    // - richtextAutosaveField
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<ProjectUserConfig>> projectUserConfigsByRichtextAutosaveFieldCache = new Cache<java.lang.String,Set<ProjectUserConfig>>(
            new Cache.Factory<java.lang.String,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(java.lang.String richtextAutosaveField) {
                    return getEntities(new IsRichtextAutosaveField(richtextAutosaveField));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByRichtextAutosaveField(java.lang.String richtextAutosaveField) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByRichtextAutosaveFieldCache.get(richtextAutosaveField));
    }
    private Set<java.lang.String> richtextAutosaveFieldsCache;

    public final Set<java.lang.String> getRichtextAutosaveFields() {
        if (richtextAutosaveFieldsCache == null) {
            richtextAutosaveFieldsCache = new HashSet<java.lang.String>();
            for (ProjectUserConfig e : getEntities()) {
                if (e.isRichtextAutosaveFieldSet()) richtextAutosaveFieldsCache.add(e.getRichtextAutosaveField());
            }
        }
        return richtextAutosaveFieldsCache;
    }

    private static class IsRichtextAutosaveField implements Predicate<ProjectUserConfig> {

        private java.lang.String value;

        public IsRichtextAutosaveField(java.lang.String value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isRichtextAutosaveField(value);
        }

    }

    // -----------------------------------------------------------
    // - selectedEntitysIds
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<ProjectUserConfig>> projectUserConfigsBySelectedEntitysIdCache = new Cache<java.lang.String,Set<ProjectUserConfig>>(
            new Cache.Factory<java.lang.String,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(java.lang.String selectedEntitysId) {
                    return getEntities(new ContainsSelectedEntitysId(selectedEntitysId));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsBySelectedEntitysId(java.lang.String selectedEntitysId) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsBySelectedEntitysIdCache.get(selectedEntitysId));
    }
    private Set<java.lang.String> selectedEntitysIdsCache;

    public final Set<java.lang.String> getSelectedEntitysIds() {
        if (selectedEntitysIdsCache == null) {
            selectedEntitysIdsCache = new HashSet<java.lang.String>();
            for (ProjectUserConfig e : getEntities()) {
                selectedEntitysIdsCache.addAll(e.getSelectedEntitysIds());
            }
        }
        return selectedEntitysIdsCache;
    }

    private static class ContainsSelectedEntitysId implements Predicate<ProjectUserConfig> {

        private java.lang.String value;

        public ContainsSelectedEntitysId(java.lang.String value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.containsSelectedEntitysId(value);
        }

    }

    // -----------------------------------------------------------
    // - online
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<ProjectUserConfig>> projectUserConfigsByOnlineCache = new Cache<Boolean,Set<ProjectUserConfig>>(
            new Cache.Factory<Boolean,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(Boolean online) {
                    return getEntities(new IsOnline(online));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByOnline(boolean online) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByOnlineCache.get(online));
    }

    private static class IsOnline implements Predicate<ProjectUserConfig> {

        private boolean value;

        public IsOnline(boolean value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return value == e.isOnline();
        }

    }

    // -----------------------------------------------------------
    // - lastActivityDateAndTime
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.DateAndTime,Set<ProjectUserConfig>> projectUserConfigsByLastActivityDateAndTimeCache = new Cache<ilarkesto.core.time.DateAndTime,Set<ProjectUserConfig>>(
            new Cache.Factory<ilarkesto.core.time.DateAndTime,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(ilarkesto.core.time.DateAndTime lastActivityDateAndTime) {
                    return getEntities(new IsLastActivityDateAndTime(lastActivityDateAndTime));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByLastActivityDateAndTime(ilarkesto.core.time.DateAndTime lastActivityDateAndTime) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByLastActivityDateAndTimeCache.get(lastActivityDateAndTime));
    }
    private Set<ilarkesto.core.time.DateAndTime> lastActivityDateAndTimesCache;

    public final Set<ilarkesto.core.time.DateAndTime> getLastActivityDateAndTimes() {
        if (lastActivityDateAndTimesCache == null) {
            lastActivityDateAndTimesCache = new HashSet<ilarkesto.core.time.DateAndTime>();
            for (ProjectUserConfig e : getEntities()) {
                if (e.isLastActivityDateAndTimeSet()) lastActivityDateAndTimesCache.add(e.getLastActivityDateAndTime());
            }
        }
        return lastActivityDateAndTimesCache;
    }

    private static class IsLastActivityDateAndTime implements Predicate<ProjectUserConfig> {

        private ilarkesto.core.time.DateAndTime value;

        public IsLastActivityDateAndTime(ilarkesto.core.time.DateAndTime value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isLastActivityDateAndTime(value);
        }

    }

    // -----------------------------------------------------------
    // - pblFilterThemes
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<ProjectUserConfig>> projectUserConfigsByPblFilterThemeCache = new Cache<java.lang.String,Set<ProjectUserConfig>>(
            new Cache.Factory<java.lang.String,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(java.lang.String pblFilterTheme) {
                    return getEntities(new ContainsPblFilterTheme(pblFilterTheme));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByPblFilterTheme(java.lang.String pblFilterTheme) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByPblFilterThemeCache.get(pblFilterTheme));
    }
    private Set<java.lang.String> pblFilterThemesCache;

    public final Set<java.lang.String> getPblFilterThemes() {
        if (pblFilterThemesCache == null) {
            pblFilterThemesCache = new HashSet<java.lang.String>();
            for (ProjectUserConfig e : getEntities()) {
                pblFilterThemesCache.addAll(e.getPblFilterThemes());
            }
        }
        return pblFilterThemesCache;
    }

    private static class ContainsPblFilterTheme implements Predicate<ProjectUserConfig> {

        private java.lang.String value;

        public ContainsPblFilterTheme(java.lang.String value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.containsPblFilterTheme(value);
        }

    }

    // -----------------------------------------------------------
    // - pblFilterQualitys
    // -----------------------------------------------------------

    private final Cache<scrum.server.project.Quality,Set<ProjectUserConfig>> projectUserConfigsByPblFilterQualityCache = new Cache<scrum.server.project.Quality,Set<ProjectUserConfig>>(
            new Cache.Factory<scrum.server.project.Quality,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(scrum.server.project.Quality pblFilterQuality) {
                    return getEntities(new ContainsPblFilterQuality(pblFilterQuality));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByPblFilterQuality(scrum.server.project.Quality pblFilterQuality) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByPblFilterQualityCache.get(pblFilterQuality));
    }
    private Set<scrum.server.project.Quality> pblFilterQualitysCache;

    public final Set<scrum.server.project.Quality> getPblFilterQualitys() {
        if (pblFilterQualitysCache == null) {
            pblFilterQualitysCache = new HashSet<scrum.server.project.Quality>();
            for (ProjectUserConfig e : getEntities()) {
                pblFilterQualitysCache.addAll(e.getPblFilterQualitys());
            }
        }
        return pblFilterQualitysCache;
    }

    private static class ContainsPblFilterQuality implements Predicate<ProjectUserConfig> {

        private scrum.server.project.Quality value;

        public ContainsPblFilterQuality(scrum.server.project.Quality value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.containsPblFilterQuality(value);
        }

    }

    // -----------------------------------------------------------
    // - pblFilterDateFrom
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.Date,Set<ProjectUserConfig>> projectUserConfigsByPblFilterDateFromCache = new Cache<ilarkesto.core.time.Date,Set<ProjectUserConfig>>(
            new Cache.Factory<ilarkesto.core.time.Date,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(ilarkesto.core.time.Date pblFilterDateFrom) {
                    return getEntities(new IsPblFilterDateFrom(pblFilterDateFrom));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByPblFilterDateFrom(ilarkesto.core.time.Date pblFilterDateFrom) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByPblFilterDateFromCache.get(pblFilterDateFrom));
    }
    private Set<ilarkesto.core.time.Date> pblFilterDateFromsCache;

    public final Set<ilarkesto.core.time.Date> getPblFilterDateFroms() {
        if (pblFilterDateFromsCache == null) {
            pblFilterDateFromsCache = new HashSet<ilarkesto.core.time.Date>();
            for (ProjectUserConfig e : getEntities()) {
                if (e.isPblFilterDateFromSet()) pblFilterDateFromsCache.add(e.getPblFilterDateFrom());
            }
        }
        return pblFilterDateFromsCache;
    }

    private static class IsPblFilterDateFrom implements Predicate<ProjectUserConfig> {

        private ilarkesto.core.time.Date value;

        public IsPblFilterDateFrom(ilarkesto.core.time.Date value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isPblFilterDateFrom(value);
        }

    }

    // -----------------------------------------------------------
    // - pblFilterDateTo
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.Date,Set<ProjectUserConfig>> projectUserConfigsByPblFilterDateToCache = new Cache<ilarkesto.core.time.Date,Set<ProjectUserConfig>>(
            new Cache.Factory<ilarkesto.core.time.Date,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(ilarkesto.core.time.Date pblFilterDateTo) {
                    return getEntities(new IsPblFilterDateTo(pblFilterDateTo));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByPblFilterDateTo(ilarkesto.core.time.Date pblFilterDateTo) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByPblFilterDateToCache.get(pblFilterDateTo));
    }
    private Set<ilarkesto.core.time.Date> pblFilterDateTosCache;

    public final Set<ilarkesto.core.time.Date> getPblFilterDateTos() {
        if (pblFilterDateTosCache == null) {
            pblFilterDateTosCache = new HashSet<ilarkesto.core.time.Date>();
            for (ProjectUserConfig e : getEntities()) {
                if (e.isPblFilterDateToSet()) pblFilterDateTosCache.add(e.getPblFilterDateTo());
            }
        }
        return pblFilterDateTosCache;
    }

    private static class IsPblFilterDateTo implements Predicate<ProjectUserConfig> {

        private ilarkesto.core.time.Date value;

        public IsPblFilterDateTo(ilarkesto.core.time.Date value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isPblFilterDateTo(value);
        }

    }

    // -----------------------------------------------------------
    // - pblFilterEstimationFrom
    // -----------------------------------------------------------

    private final Cache<java.lang.Float,Set<ProjectUserConfig>> projectUserConfigsByPblFilterEstimationFromCache = new Cache<java.lang.Float,Set<ProjectUserConfig>>(
            new Cache.Factory<java.lang.Float,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(java.lang.Float pblFilterEstimationFrom) {
                    return getEntities(new IsPblFilterEstimationFrom(pblFilterEstimationFrom));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByPblFilterEstimationFrom(java.lang.Float pblFilterEstimationFrom) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByPblFilterEstimationFromCache.get(pblFilterEstimationFrom));
    }
    private Set<java.lang.Float> pblFilterEstimationFromsCache;

    public final Set<java.lang.Float> getPblFilterEstimationFroms() {
        if (pblFilterEstimationFromsCache == null) {
            pblFilterEstimationFromsCache = new HashSet<java.lang.Float>();
            for (ProjectUserConfig e : getEntities()) {
                if (e.isPblFilterEstimationFromSet()) pblFilterEstimationFromsCache.add(e.getPblFilterEstimationFrom());
            }
        }
        return pblFilterEstimationFromsCache;
    }

    private static class IsPblFilterEstimationFrom implements Predicate<ProjectUserConfig> {

        private java.lang.Float value;

        public IsPblFilterEstimationFrom(java.lang.Float value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isPblFilterEstimationFrom(value);
        }

    }

    // -----------------------------------------------------------
    // - pblFilterEstimationTo
    // -----------------------------------------------------------

    private final Cache<java.lang.Float,Set<ProjectUserConfig>> projectUserConfigsByPblFilterEstimationToCache = new Cache<java.lang.Float,Set<ProjectUserConfig>>(
            new Cache.Factory<java.lang.Float,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(java.lang.Float pblFilterEstimationTo) {
                    return getEntities(new IsPblFilterEstimationTo(pblFilterEstimationTo));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByPblFilterEstimationTo(java.lang.Float pblFilterEstimationTo) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByPblFilterEstimationToCache.get(pblFilterEstimationTo));
    }
    private Set<java.lang.Float> pblFilterEstimationTosCache;

    public final Set<java.lang.Float> getPblFilterEstimationTos() {
        if (pblFilterEstimationTosCache == null) {
            pblFilterEstimationTosCache = new HashSet<java.lang.Float>();
            for (ProjectUserConfig e : getEntities()) {
                if (e.isPblFilterEstimationToSet()) pblFilterEstimationTosCache.add(e.getPblFilterEstimationTo());
            }
        }
        return pblFilterEstimationTosCache;
    }

    private static class IsPblFilterEstimationTo implements Predicate<ProjectUserConfig> {

        private java.lang.Float value;

        public IsPblFilterEstimationTo(java.lang.Float value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isPblFilterEstimationTo(value);
        }

    }

    // -----------------------------------------------------------
    // - pblFilterText
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<ProjectUserConfig>> projectUserConfigsByPblFilterTextCache = new Cache<java.lang.String,Set<ProjectUserConfig>>(
            new Cache.Factory<java.lang.String,Set<ProjectUserConfig>>() {
                public Set<ProjectUserConfig> create(java.lang.String pblFilterText) {
                    return getEntities(new IsPblFilterText(pblFilterText));
                }
            });

    public final Set<ProjectUserConfig> getProjectUserConfigsByPblFilterText(java.lang.String pblFilterText) {
        return new HashSet<ProjectUserConfig>(projectUserConfigsByPblFilterTextCache.get(pblFilterText));
    }
    private Set<java.lang.String> pblFilterTextsCache;

    public final Set<java.lang.String> getPblFilterTexts() {
        if (pblFilterTextsCache == null) {
            pblFilterTextsCache = new HashSet<java.lang.String>();
            for (ProjectUserConfig e : getEntities()) {
                if (e.isPblFilterTextSet()) pblFilterTextsCache.add(e.getPblFilterText());
            }
        }
        return pblFilterTextsCache;
    }

    private static class IsPblFilterText implements Predicate<ProjectUserConfig> {

        private java.lang.String value;

        public IsPblFilterText(java.lang.String value) {
            this.value = value;
        }

        public boolean test(ProjectUserConfig e) {
            return e.isPblFilterText(value);
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

    scrum.server.project.QualityDao qualityDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public void setQualityDao(scrum.server.project.QualityDao qualityDao) {
        this.qualityDao = qualityDao;
    }

}