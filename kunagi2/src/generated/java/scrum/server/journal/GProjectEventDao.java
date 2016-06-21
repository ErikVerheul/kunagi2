// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.journal;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GProjectEventDao
            extends ilarkesto.persistence.ADao<ProjectEvent> {

    public final String getEntityName() {
        return ProjectEvent.TYPE;
    }

    public final Class getEntityClass() {
        return ProjectEvent.class;
    }

    public Set<ProjectEvent> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<ProjectEvent>() {
            public boolean test(ProjectEvent e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        projectEventsByProjectCache.clear();
        projectsCache = null;
        projectEventsByLabelCache.clear();
        labelsCache = null;
        projectEventsBySubjectCache.clear();
        subjectsCache = null;
        projectEventsByDateAndTimeCache.clear();
        dateAndTimesCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof ProjectEvent) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof ProjectEvent) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - project
    // -----------------------------------------------------------

    private final Cache<scrum.server.project.Project,Set<ProjectEvent>> projectEventsByProjectCache = new Cache<scrum.server.project.Project,Set<ProjectEvent>>(
            new Cache.Factory<scrum.server.project.Project,Set<ProjectEvent>>() {
                public Set<ProjectEvent> create(scrum.server.project.Project project) {
                    return getEntities(new IsProject(project));
                }
            });

    public final Set<ProjectEvent> getProjectEventsByProject(scrum.server.project.Project project) {
        return new HashSet<ProjectEvent>(projectEventsByProjectCache.get(project));
    }
    private Set<scrum.server.project.Project> projectsCache;

    public final Set<scrum.server.project.Project> getProjects() {
        if (projectsCache == null) {
            projectsCache = new HashSet<scrum.server.project.Project>();
            for (ProjectEvent e : getEntities()) {
                if (e.isProjectSet()) projectsCache.add(e.getProject());
            }
        }
        return projectsCache;
    }

    private static class IsProject implements Predicate<ProjectEvent> {

        private scrum.server.project.Project value;

        public IsProject(scrum.server.project.Project value) {
            this.value = value;
        }

        public boolean test(ProjectEvent e) {
            return e.isProject(value);
        }

    }

    // -----------------------------------------------------------
    // - label
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<ProjectEvent>> projectEventsByLabelCache = new Cache<java.lang.String,Set<ProjectEvent>>(
            new Cache.Factory<java.lang.String,Set<ProjectEvent>>() {
                public Set<ProjectEvent> create(java.lang.String label) {
                    return getEntities(new IsLabel(label));
                }
            });

    public final Set<ProjectEvent> getProjectEventsByLabel(java.lang.String label) {
        return new HashSet<ProjectEvent>(projectEventsByLabelCache.get(label));
    }
    private Set<java.lang.String> labelsCache;

    public final Set<java.lang.String> getLabels() {
        if (labelsCache == null) {
            labelsCache = new HashSet<java.lang.String>();
            for (ProjectEvent e : getEntities()) {
                if (e.isLabelSet()) labelsCache.add(e.getLabel());
            }
        }
        return labelsCache;
    }

    private static class IsLabel implements Predicate<ProjectEvent> {

        private java.lang.String value;

        public IsLabel(java.lang.String value) {
            this.value = value;
        }

        public boolean test(ProjectEvent e) {
            return e.isLabel(value);
        }

    }

    // -----------------------------------------------------------
    // - subject
    // -----------------------------------------------------------

    private final Cache<ilarkesto.persistence.AEntity,Set<ProjectEvent>> projectEventsBySubjectCache = new Cache<ilarkesto.persistence.AEntity,Set<ProjectEvent>>(
            new Cache.Factory<ilarkesto.persistence.AEntity,Set<ProjectEvent>>() {
                public Set<ProjectEvent> create(ilarkesto.persistence.AEntity subject) {
                    return getEntities(new IsSubject(subject));
                }
            });

    public final Set<ProjectEvent> getProjectEventsBySubject(ilarkesto.persistence.AEntity subject) {
        return new HashSet<ProjectEvent>(projectEventsBySubjectCache.get(subject));
    }
    private Set<ilarkesto.persistence.AEntity> subjectsCache;

    public final Set<ilarkesto.persistence.AEntity> getSubjects() {
        if (subjectsCache == null) {
            subjectsCache = new HashSet<ilarkesto.persistence.AEntity>();
            for (ProjectEvent e : getEntities()) {
                if (e.isSubjectSet()) subjectsCache.add(e.getSubject());
            }
        }
        return subjectsCache;
    }

    private static class IsSubject implements Predicate<ProjectEvent> {

        private ilarkesto.persistence.AEntity value;

        public IsSubject(ilarkesto.persistence.AEntity value) {
            this.value = value;
        }

        public boolean test(ProjectEvent e) {
            return e.isSubject(value);
        }

    }

    // -----------------------------------------------------------
    // - dateAndTime
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.DateAndTime,Set<ProjectEvent>> projectEventsByDateAndTimeCache = new Cache<ilarkesto.core.time.DateAndTime,Set<ProjectEvent>>(
            new Cache.Factory<ilarkesto.core.time.DateAndTime,Set<ProjectEvent>>() {
                public Set<ProjectEvent> create(ilarkesto.core.time.DateAndTime dateAndTime) {
                    return getEntities(new IsDateAndTime(dateAndTime));
                }
            });

    public final Set<ProjectEvent> getProjectEventsByDateAndTime(ilarkesto.core.time.DateAndTime dateAndTime) {
        return new HashSet<ProjectEvent>(projectEventsByDateAndTimeCache.get(dateAndTime));
    }
    private Set<ilarkesto.core.time.DateAndTime> dateAndTimesCache;

    public final Set<ilarkesto.core.time.DateAndTime> getDateAndTimes() {
        if (dateAndTimesCache == null) {
            dateAndTimesCache = new HashSet<ilarkesto.core.time.DateAndTime>();
            for (ProjectEvent e : getEntities()) {
                if (e.isDateAndTimeSet()) dateAndTimesCache.add(e.getDateAndTime());
            }
        }
        return dateAndTimesCache;
    }

    private static class IsDateAndTime implements Predicate<ProjectEvent> {

        private ilarkesto.core.time.DateAndTime value;

        public IsDateAndTime(ilarkesto.core.time.DateAndTime value) {
            this.value = value;
        }

        public boolean test(ProjectEvent e) {
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

    scrum.server.project.ProjectDao projectDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public void setProjectDao(scrum.server.project.ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

}