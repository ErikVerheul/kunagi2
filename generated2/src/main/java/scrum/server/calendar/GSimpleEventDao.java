// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.calendar;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GSimpleEventDao
            extends ilarkesto.persistence.ADao<SimpleEvent> {

    public final String getEntityName() {
        return SimpleEvent.TYPE;
    }

    public final Class getEntityClass() {
        return SimpleEvent.class;
    }

    public Set<SimpleEvent> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<SimpleEvent>() {
            public boolean test(SimpleEvent e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        simpleEventsByProjectCache.clear();
        projectsCache = null;
        simpleEventsByLabelCache.clear();
        labelsCache = null;
        simpleEventsByNumberCache.clear();
        numbersCache = null;
        simpleEventsByDateCache.clear();
        datesCache = null;
        simpleEventsByTimeCache.clear();
        timesCache = null;
        simpleEventsByLocationCache.clear();
        locationsCache = null;
        simpleEventsByDurationCache.clear();
        durationsCache = null;
        simpleEventsByAgendaCache.clear();
        agendasCache = null;
        simpleEventsByNoteCache.clear();
        notesCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof SimpleEvent) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof SimpleEvent) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - project
    // -----------------------------------------------------------

    private final Cache<scrum.server.project.Project,Set<SimpleEvent>> simpleEventsByProjectCache = new Cache<scrum.server.project.Project,Set<SimpleEvent>>(
            new Cache.Factory<scrum.server.project.Project,Set<SimpleEvent>>() {
                public Set<SimpleEvent> create(scrum.server.project.Project project) {
                    return getEntities(new IsProject(project));
                }
            });

    public final Set<SimpleEvent> getSimpleEventsByProject(scrum.server.project.Project project) {
        return new HashSet<SimpleEvent>(simpleEventsByProjectCache.get(project));
    }
    private Set<scrum.server.project.Project> projectsCache;

    public final Set<scrum.server.project.Project> getProjects() {
        if (projectsCache == null) {
            projectsCache = new HashSet<scrum.server.project.Project>();
            for (SimpleEvent e : getEntities()) {
                if (e.isProjectSet()) projectsCache.add(e.getProject());
            }
        }
        return projectsCache;
    }

    private static class IsProject implements Predicate<SimpleEvent> {

        private scrum.server.project.Project value;

        public IsProject(scrum.server.project.Project value) {
            this.value = value;
        }

        public boolean test(SimpleEvent e) {
            return e.isProject(value);
        }

    }

    // -----------------------------------------------------------
    // - label
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SimpleEvent>> simpleEventsByLabelCache = new Cache<java.lang.String,Set<SimpleEvent>>(
            new Cache.Factory<java.lang.String,Set<SimpleEvent>>() {
                public Set<SimpleEvent> create(java.lang.String label) {
                    return getEntities(new IsLabel(label));
                }
            });

    public final Set<SimpleEvent> getSimpleEventsByLabel(java.lang.String label) {
        return new HashSet<SimpleEvent>(simpleEventsByLabelCache.get(label));
    }
    private Set<java.lang.String> labelsCache;

    public final Set<java.lang.String> getLabels() {
        if (labelsCache == null) {
            labelsCache = new HashSet<java.lang.String>();
            for (SimpleEvent e : getEntities()) {
                if (e.isLabelSet()) labelsCache.add(e.getLabel());
            }
        }
        return labelsCache;
    }

    private static class IsLabel implements Predicate<SimpleEvent> {

        private java.lang.String value;

        public IsLabel(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SimpleEvent e) {
            return e.isLabel(value);
        }

    }

    // -----------------------------------------------------------
    // - number
    // -----------------------------------------------------------

    private final Cache<Integer,Set<SimpleEvent>> simpleEventsByNumberCache = new Cache<Integer,Set<SimpleEvent>>(
            new Cache.Factory<Integer,Set<SimpleEvent>>() {
                public Set<SimpleEvent> create(Integer number) {
                    return getEntities(new IsNumber(number));
                }
            });

    public final Set<SimpleEvent> getSimpleEventsByNumber(int number) {
        return new HashSet<SimpleEvent>(simpleEventsByNumberCache.get(number));
    }
    private Set<Integer> numbersCache;

    public final Set<Integer> getNumbers() {
        if (numbersCache == null) {
            numbersCache = new HashSet<Integer>();
            for (SimpleEvent e : getEntities()) {
                numbersCache.add(e.getNumber());
            }
        }
        return numbersCache;
    }

    private static class IsNumber implements Predicate<SimpleEvent> {

        private int value;

        public IsNumber(int value) {
            this.value = value;
        }

        public boolean test(SimpleEvent e) {
            return e.isNumber(value);
        }

    }

    // -----------------------------------------------------------
    // - date
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.Date,Set<SimpleEvent>> simpleEventsByDateCache = new Cache<ilarkesto.core.time.Date,Set<SimpleEvent>>(
            new Cache.Factory<ilarkesto.core.time.Date,Set<SimpleEvent>>() {
                public Set<SimpleEvent> create(ilarkesto.core.time.Date date) {
                    return getEntities(new IsDate(date));
                }
            });

    public final Set<SimpleEvent> getSimpleEventsByDate(ilarkesto.core.time.Date date) {
        return new HashSet<SimpleEvent>(simpleEventsByDateCache.get(date));
    }
    private Set<ilarkesto.core.time.Date> datesCache;

    public final Set<ilarkesto.core.time.Date> getDates() {
        if (datesCache == null) {
            datesCache = new HashSet<ilarkesto.core.time.Date>();
            for (SimpleEvent e : getEntities()) {
                if (e.isDateSet()) datesCache.add(e.getDate());
            }
        }
        return datesCache;
    }

    private static class IsDate implements Predicate<SimpleEvent> {

        private ilarkesto.core.time.Date value;

        public IsDate(ilarkesto.core.time.Date value) {
            this.value = value;
        }

        public boolean test(SimpleEvent e) {
            return e.isDate(value);
        }

    }

    // -----------------------------------------------------------
    // - time
    // -----------------------------------------------------------

    private final Cache<ilarkesto.core.time.Time,Set<SimpleEvent>> simpleEventsByTimeCache = new Cache<ilarkesto.core.time.Time,Set<SimpleEvent>>(
            new Cache.Factory<ilarkesto.core.time.Time,Set<SimpleEvent>>() {
                public Set<SimpleEvent> create(ilarkesto.core.time.Time time) {
                    return getEntities(new IsTime(time));
                }
            });

    public final Set<SimpleEvent> getSimpleEventsByTime(ilarkesto.core.time.Time time) {
        return new HashSet<SimpleEvent>(simpleEventsByTimeCache.get(time));
    }
    private Set<ilarkesto.core.time.Time> timesCache;

    public final Set<ilarkesto.core.time.Time> getTimes() {
        if (timesCache == null) {
            timesCache = new HashSet<ilarkesto.core.time.Time>();
            for (SimpleEvent e : getEntities()) {
                if (e.isTimeSet()) timesCache.add(e.getTime());
            }
        }
        return timesCache;
    }

    private static class IsTime implements Predicate<SimpleEvent> {

        private ilarkesto.core.time.Time value;

        public IsTime(ilarkesto.core.time.Time value) {
            this.value = value;
        }

        public boolean test(SimpleEvent e) {
            return e.isTime(value);
        }

    }

    // -----------------------------------------------------------
    // - location
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SimpleEvent>> simpleEventsByLocationCache = new Cache<java.lang.String,Set<SimpleEvent>>(
            new Cache.Factory<java.lang.String,Set<SimpleEvent>>() {
                public Set<SimpleEvent> create(java.lang.String location) {
                    return getEntities(new IsLocation(location));
                }
            });

    public final Set<SimpleEvent> getSimpleEventsByLocation(java.lang.String location) {
        return new HashSet<SimpleEvent>(simpleEventsByLocationCache.get(location));
    }
    private Set<java.lang.String> locationsCache;

    public final Set<java.lang.String> getLocations() {
        if (locationsCache == null) {
            locationsCache = new HashSet<java.lang.String>();
            for (SimpleEvent e : getEntities()) {
                if (e.isLocationSet()) locationsCache.add(e.getLocation());
            }
        }
        return locationsCache;
    }

    private static class IsLocation implements Predicate<SimpleEvent> {

        private java.lang.String value;

        public IsLocation(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SimpleEvent e) {
            return e.isLocation(value);
        }

    }

    // -----------------------------------------------------------
    // - duration
    // -----------------------------------------------------------

    private final Cache<java.lang.Integer,Set<SimpleEvent>> simpleEventsByDurationCache = new Cache<java.lang.Integer,Set<SimpleEvent>>(
            new Cache.Factory<java.lang.Integer,Set<SimpleEvent>>() {
                public Set<SimpleEvent> create(java.lang.Integer duration) {
                    return getEntities(new IsDuration(duration));
                }
            });

    public final Set<SimpleEvent> getSimpleEventsByDuration(java.lang.Integer duration) {
        return new HashSet<SimpleEvent>(simpleEventsByDurationCache.get(duration));
    }
    private Set<java.lang.Integer> durationsCache;

    public final Set<java.lang.Integer> getDurations() {
        if (durationsCache == null) {
            durationsCache = new HashSet<java.lang.Integer>();
            for (SimpleEvent e : getEntities()) {
                if (e.isDurationSet()) durationsCache.add(e.getDuration());
            }
        }
        return durationsCache;
    }

    private static class IsDuration implements Predicate<SimpleEvent> {

        private java.lang.Integer value;

        public IsDuration(java.lang.Integer value) {
            this.value = value;
        }

        public boolean test(SimpleEvent e) {
            return e.isDuration(value);
        }

    }

    // -----------------------------------------------------------
    // - agenda
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SimpleEvent>> simpleEventsByAgendaCache = new Cache<java.lang.String,Set<SimpleEvent>>(
            new Cache.Factory<java.lang.String,Set<SimpleEvent>>() {
                public Set<SimpleEvent> create(java.lang.String agenda) {
                    return getEntities(new IsAgenda(agenda));
                }
            });

    public final Set<SimpleEvent> getSimpleEventsByAgenda(java.lang.String agenda) {
        return new HashSet<SimpleEvent>(simpleEventsByAgendaCache.get(agenda));
    }
    private Set<java.lang.String> agendasCache;

    public final Set<java.lang.String> getAgendas() {
        if (agendasCache == null) {
            agendasCache = new HashSet<java.lang.String>();
            for (SimpleEvent e : getEntities()) {
                if (e.isAgendaSet()) agendasCache.add(e.getAgenda());
            }
        }
        return agendasCache;
    }

    private static class IsAgenda implements Predicate<SimpleEvent> {

        private java.lang.String value;

        public IsAgenda(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SimpleEvent e) {
            return e.isAgenda(value);
        }

    }

    // -----------------------------------------------------------
    // - note
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SimpleEvent>> simpleEventsByNoteCache = new Cache<java.lang.String,Set<SimpleEvent>>(
            new Cache.Factory<java.lang.String,Set<SimpleEvent>>() {
                public Set<SimpleEvent> create(java.lang.String note) {
                    return getEntities(new IsNote(note));
                }
            });

    public final Set<SimpleEvent> getSimpleEventsByNote(java.lang.String note) {
        return new HashSet<SimpleEvent>(simpleEventsByNoteCache.get(note));
    }
    private Set<java.lang.String> notesCache;

    public final Set<java.lang.String> getNotes() {
        if (notesCache == null) {
            notesCache = new HashSet<java.lang.String>();
            for (SimpleEvent e : getEntities()) {
                if (e.isNoteSet()) notesCache.add(e.getNote());
            }
        }
        return notesCache;
    }

    private static class IsNote implements Predicate<SimpleEvent> {

        private java.lang.String value;

        public IsNote(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SimpleEvent e) {
            return e.isNote(value);
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