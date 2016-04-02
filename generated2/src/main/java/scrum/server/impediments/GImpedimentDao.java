// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.impediments;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GImpedimentDao
            extends ilarkesto.persistence.ADao<Impediment> {

    public final String getEntityName() {
        return Impediment.TYPE;
    }

    public final Class getEntityClass() {
        return Impediment.class;
    }

    public Set<Impediment> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<Impediment>() {
            public boolean test(Impediment e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        impedimentsByProjectCache.clear();
        projectsCache = null;
        impedimentsByNumberCache.clear();
        numbersCache = null;
        impedimentsByLabelCache.clear();
        labelsCache = null;
        impedimentsByDateCache.clear();
        datesCache = null;
        impedimentsByDescriptionCache.clear();
        descriptionsCache = null;
        impedimentsBySolutionCache.clear();
        solutionsCache = null;
        impedimentsByClosedCache.clear();
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof Impediment) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof Impediment) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - project
    // -----------------------------------------------------------

    private final Cache<scrum.server.project.Project,Set<Impediment>> impedimentsByProjectCache = new Cache<scrum.server.project.Project,Set<Impediment>>(
            new Cache.Factory<scrum.server.project.Project,Set<Impediment>>() {
                public Set<Impediment> create(scrum.server.project.Project project) {
                    return getEntities(new IsProject(project));
                }
            });

    public final Set<Impediment> getImpedimentsByProject(scrum.server.project.Project project) {
        return new HashSet<Impediment>(impedimentsByProjectCache.get(project));
    }
    private Set<scrum.server.project.Project> projectsCache;

    public final Set<scrum.server.project.Project> getProjects() {
        if (projectsCache == null) {
            projectsCache = new HashSet<scrum.server.project.Project>();
            for (Impediment e : getEntities()) {
                if (e.isProjectSet()) projectsCache.add(e.getProject());
            }
        }
        return projectsCache;
    }

    private static class IsProject implements Predicate<Impediment> {

        private scrum.server.project.Project value;

        public IsProject(scrum.server.project.Project value) {
            this.value = value;
        }

        public boolean test(Impediment e) {
            return e.isProject(value);
        }

    }

    // -----------------------------------------------------------
    // - number
    // -----------------------------------------------------------

    private final Cache<Integer,Set<Impediment>> impedimentsByNumberCache = new Cache<Integer,Set<Impediment>>(
            new Cache.Factory<Integer,Set<Impediment>>() {
                public Set<Impediment> create(Integer number) {
                    return getEntities(new IsNumber(number));
                }
            });

    public final Set<Impediment> getImpedimentsByNumber(int number) {
        return new HashSet<Impediment>(impedimentsByNumberCache.get(number));
    }
    private Set<Integer> numbersCache;

    public final Set<Integer> getNumbers() {
        if (numbersCache == null) {
            numbersCache = new HashSet<Integer>();
            for (Impediment e : getEntities()) {
                numbersCache.add(e.getNumber());
            }
        }
        return numbersCache;
    }

    private static class IsNumber implements Predicate<Impediment> {

        private int value;

        public IsNumber(int value) {
            this.value = value;
        }

        public boolean test(Impediment e) {
            return e.isNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - label
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Impediment>> impedimentsByLabelCache = new Cache<java.lang.String,Set<Impediment>>(
            new Cache.Factory<java.lang.String,Set<Impediment>>() {
                public Set<Impediment> create(java.lang.String label) {
                    return getEntities(new IsLabel(label));
                }
            });

    public final Set<Impediment> getImpedimentsByLabel(java.lang.String label) {
        return new HashSet<Impediment>(impedimentsByLabelCache.get(label));
    }
    private Set<java.lang.String> labelsCache;

    public final Set<java.lang.String> getLabels() {
        if (labelsCache == null) {
            labelsCache = new HashSet<java.lang.String>();
            for (Impediment e : getEntities()) {
                if (e.isLabelSet()) labelsCache.add(e.getLabel());
            }
        }
        return labelsCache;
    }

    private static class IsLabel implements Predicate<Impediment> {

        private java.lang.String value;

        public IsLabel(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Impediment e) {
            return e.isLabel(value);
        }

    }

    // -----------------------------------------------------------
    // - date
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.Date,Set<Impediment>> impedimentsByDateCache = new Cache<ilarkesto.core.time.Date,Set<Impediment>>(
            new Cache.Factory<ilarkesto.core.time.Date,Set<Impediment>>() {
                public Set<Impediment> create(ilarkesto.core.time.Date date) {
                    return getEntities(new IsDate(date));
                }
            });

    public final Set<Impediment> getImpedimentsByDate(ilarkesto.core.time.Date date) {
        return new HashSet<Impediment>(impedimentsByDateCache.get(date));
    }
    private Set<ilarkesto.core.time.Date> datesCache;

    public final Set<ilarkesto.core.time.Date> getDates() {
        if (datesCache == null) {
            datesCache = new HashSet<ilarkesto.core.time.Date>();
            for (Impediment e : getEntities()) {
                if (e.isDateSet()) datesCache.add(e.getDate());
            }
        }
        return datesCache;
    }

    private static class IsDate implements Predicate<Impediment> {

        private ilarkesto.core.time.Date value;

        public IsDate(ilarkesto.core.time.Date value) {
            this.value = value;
        }

        public boolean test(Impediment e) {
            return e.isDate(value);
        }

    }

    // -----------------------------------------------------------
    // - description
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Impediment>> impedimentsByDescriptionCache = new Cache<java.lang.String,Set<Impediment>>(
            new Cache.Factory<java.lang.String,Set<Impediment>>() {
                public Set<Impediment> create(java.lang.String description) {
                    return getEntities(new IsDescription(description));
                }
            });

    public final Set<Impediment> getImpedimentsByDescription(java.lang.String description) {
        return new HashSet<Impediment>(impedimentsByDescriptionCache.get(description));
    }
    private Set<java.lang.String> descriptionsCache;

    public final Set<java.lang.String> getDescriptions() {
        if (descriptionsCache == null) {
            descriptionsCache = new HashSet<java.lang.String>();
            for (Impediment e : getEntities()) {
                if (e.isDescriptionSet()) descriptionsCache.add(e.getDescription());
            }
        }
        return descriptionsCache;
    }

    private static class IsDescription implements Predicate<Impediment> {

        private java.lang.String value;

        public IsDescription(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Impediment e) {
            return e.isDescription(value);
        }

    }

    // -----------------------------------------------------------
    // - solution
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<Impediment>> impedimentsBySolutionCache = new Cache<java.lang.String,Set<Impediment>>(
            new Cache.Factory<java.lang.String,Set<Impediment>>() {
                public Set<Impediment> create(java.lang.String solution) {
                    return getEntities(new IsSolution(solution));
                }
            });

    public final Set<Impediment> getImpedimentsBySolution(java.lang.String solution) {
        return new HashSet<Impediment>(impedimentsBySolutionCache.get(solution));
    }
    private Set<java.lang.String> solutionsCache;

    public final Set<java.lang.String> getSolutions() {
        if (solutionsCache == null) {
            solutionsCache = new HashSet<java.lang.String>();
            for (Impediment e : getEntities()) {
                if (e.isSolutionSet()) solutionsCache.add(e.getSolution());
            }
        }
        return solutionsCache;
    }

    private static class IsSolution implements Predicate<Impediment> {

        private java.lang.String value;

        public IsSolution(java.lang.String value) {
            this.value = value;
        }

        public boolean test(Impediment e) {
            return e.isSolution(value);
        }

    }

    // -----------------------------------------------------------
    // - closed
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<Impediment>> impedimentsByClosedCache = new Cache<Boolean,Set<Impediment>>(
            new Cache.Factory<Boolean,Set<Impediment>>() {
                public Set<Impediment> create(Boolean closed) {
                    return getEntities(new IsClosed(closed));
                }
            });

    public final Set<Impediment> getImpedimentsByClosed(boolean closed) {
        return new HashSet<Impediment>(impedimentsByClosedCache.get(closed));
    }

    private static class IsClosed implements Predicate<Impediment> {

        private boolean value;

        public IsClosed(boolean value) {
            this.value = value;
        }

        public boolean test(Impediment e) {
            return value == e.isClosed();
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