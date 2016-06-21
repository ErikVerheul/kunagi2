// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.collaboration;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GSubjectDao
            extends ilarkesto.persistence.ADao<Subject> {

    public final String getEntityName() {
        return Subject.TYPE;
    }

    public final Class getEntityClass() {
        return Subject.class;
    }

    public Set<Subject> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<Subject>() {
            public boolean test(Subject e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        subjectsByProjectCache.clear();
        projectsCache = null;
        subjectsByLabelCache.clear();
        labelsCache = null;
        subjectsByTextCache.clear();
        textsCache = null;
        subjectsByNumberCache.clear();
        numbersCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof Subject) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof Subject) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - project
    // -----------------------------------------------------------

    private final Cache<scrum.server.project.Project,Set<Subject>> subjectsByProjectCache = new Cache<scrum.server.project.Project,Set<Subject>>(
            new Cache.Factory<scrum.server.project.Project,Set<Subject>>() {
                public Set<Subject> create(scrum.server.project.Project project) {
                    return getEntities(new IsProject(project));
                }
            });

    public final Set<Subject> getSubjectsByProject(scrum.server.project.Project project) {
        return new HashSet<Subject>(subjectsByProjectCache.get(project));
    }
    private Set<scrum.server.project.Project> projectsCache;

    public final Set<scrum.server.project.Project> getProjects() {
        if (projectsCache == null) {
            projectsCache = new HashSet<scrum.server.project.Project>();
            for (Subject e : getEntities()) {
                if (e.isProjectSet()) projectsCache.add(e.getProject());
            }
        }
        return projectsCache;
    }

    private static class IsProject implements Predicate<Subject> {

        private scrum.server.project.Project value;

        public IsProject(scrum.server.project.Project value) {
            this.value = value;
        }

        public boolean test(Subject e) {
            return e.isProject(value);
        }

    }

    // -----------------------------------------------------------
    // - label
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Subject>> subjectsByLabelCache = new Cache<java.lang.String,Set<Subject>>(
            new Cache.Factory<java.lang.String,Set<Subject>>() {
                public Set<Subject> create(java.lang.String label) {
                    return getEntities(new IsLabel(label));
                }
            });

    public final Set<Subject> getSubjectsByLabel(java.lang.String label) {
        return new HashSet<Subject>(subjectsByLabelCache.get(label));
    }
    private Set<java.lang.String> labelsCache;

    public final Set<java.lang.String> getLabels() {
        if (labelsCache == null) {
            labelsCache = new HashSet<java.lang.String>();
            for (Subject e : getEntities()) {
                if (e.isLabelSet()) labelsCache.add(e.getLabel());
            }
        }
        return labelsCache;
    }

    private static class IsLabel implements Predicate<Subject> {

        private java.lang.String value;

        public IsLabel(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Subject e) {
            return e.isLabel(value);
        }

    }

    // -----------------------------------------------------------
    // - text
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Subject>> subjectsByTextCache = new Cache<java.lang.String,Set<Subject>>(
            new Cache.Factory<java.lang.String,Set<Subject>>() {
                public Set<Subject> create(java.lang.String text) {
                    return getEntities(new IsText(text));
                }
            });

    public final Set<Subject> getSubjectsByText(java.lang.String text) {
        return new HashSet<Subject>(subjectsByTextCache.get(text));
    }
    private Set<java.lang.String> textsCache;

    public final Set<java.lang.String> getTexts() {
        if (textsCache == null) {
            textsCache = new HashSet<java.lang.String>();
            for (Subject e : getEntities()) {
                if (e.isTextSet()) textsCache.add(e.getText());
            }
        }
        return textsCache;
    }

    private static class IsText implements Predicate<Subject> {

        private java.lang.String value;

        public IsText(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Subject e) {
            return e.isText(value);
        }

    }

    // -----------------------------------------------------------
    // - number
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Subject>> subjectsByNumberCache = new Cache<Integer,Set<Subject>>(
            new Cache.Factory<Integer,Set<Subject>>() {
                public Set<Subject> create(Integer number) {
                    return getEntities(new IsNumber(number));
                }
            });

    public final Set<Subject> getSubjectsByNumber(int number) {
        return new HashSet<Subject>(subjectsByNumberCache.get(number));
    }
    private Set<Integer> numbersCache;

    public final Set<Integer> getNumbers() {
        if (numbersCache == null) {
            numbersCache = new HashSet<Integer>();
            for (Subject e : getEntities()) {
                numbersCache.add(e.getNumber());
            }
        }
        return numbersCache;
    }

    private static class IsNumber implements Predicate<Subject> {

        private int value;

        public IsNumber(int value) {
            this.value = value;
        }

        public boolean test(Subject e) {
            return e.isNumber(value);
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