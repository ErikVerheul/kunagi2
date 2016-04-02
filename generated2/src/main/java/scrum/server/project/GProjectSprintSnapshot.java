// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.EntityGenerator










package scrum.server.project;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.ADatob;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.AStructure;
import ilarkesto.auth.AUser;
import ilarkesto.persistence.EntityDoesNotExistException;
import ilarkesto.base.StrExtend;

public abstract class GProjectSprintSnapshot
            extends AEntity
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, java.lang.Comparable<ProjectSprintSnapshot> {

    // --- AEntity ---

    public final scrum.server.project.ProjectSprintSnapshotDao getDao() {
        return projectSprintSnapshotDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("sprintId", this.sprintId);
        properties.put("remainingWork", this.remainingWork);
        properties.put("burnedWork", this.burnedWork);
    }

    public int compareTo(ProjectSprintSnapshot other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GProjectSprintSnapshot.class);

    public static final String TYPE = "projectSprintSnapshot";

    // -----------------------------------------------------------
    // - sprint
    // -----------------------------------------------------------

    private String sprintId;
    private transient scrum.server.sprint.Sprint sprintCache;

    private void updateSprintCache() {
        sprintCache = this.sprintId == null ? null : (scrum.server.sprint.Sprint)sprintDao.getById(this.sprintId);
    }

    public final String getSprintId() {
        return this.sprintId;
    }

    public final scrum.server.sprint.Sprint getSprint() {
        if (sprintCache == null) updateSprintCache();
        return sprintCache;
    }

    public final void setSprint(scrum.server.sprint.Sprint sprint) {
        sprint = prepareSprint(sprint);
        if (isSprint(sprint)) return;
        this.sprintId = sprint == null ? null : sprint.getId();
        sprintCache = sprint;
        updateLastModified();
        fireModified("sprint="+sprint);
    }

    protected scrum.server.sprint.Sprint prepareSprint(scrum.server.sprint.Sprint sprint) {
        return sprint;
    }

    protected void repairDeadSprintReference(String entityId) {
        if (this.sprintId == null || entityId.equals(this.sprintId)) {
            repairMissingMaster();
        }
    }

    public final boolean isSprintSet() {
        return this.sprintId != null;
    }

    public final boolean isSprint(scrum.server.sprint.Sprint sprint) {
        if (this.sprintId == null && sprint == null) return true;
        return sprint != null && sprint.getId().equals(this.sprintId);
    }

    protected final void updateSprint(Object value) {
        setSprint(value == null ? null : (scrum.server.sprint.Sprint)sprintDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - remainingWork
    // -----------------------------------------------------------

    private int remainingWork;

    public final int getRemainingWork() {
        return remainingWork;
    }

    public final void setRemainingWork(int remainingWork) {
        remainingWork = prepareRemainingWork(remainingWork);
        if (isRemainingWork(remainingWork)) return;
        this.remainingWork = remainingWork;
        updateLastModified();
        fireModified("remainingWork="+remainingWork);
    }

    protected int prepareRemainingWork(int remainingWork) {
        return remainingWork;
    }

    public final boolean isRemainingWork(int remainingWork) {
        return this.remainingWork == remainingWork;
    }

    protected final void updateRemainingWork(Object value) {
        setRemainingWork((Integer)value);
    }

    // -----------------------------------------------------------
    // - burnedWork
    // -----------------------------------------------------------

    private int burnedWork;

    public final int getBurnedWork() {
        return burnedWork;
    }

    public final void setBurnedWork(int burnedWork) {
        burnedWork = prepareBurnedWork(burnedWork);
        if (isBurnedWork(burnedWork)) return;
        this.burnedWork = burnedWork;
        updateLastModified();
        fireModified("burnedWork="+burnedWork);
    }

    protected int prepareBurnedWork(int burnedWork) {
        return burnedWork;
    }

    public final boolean isBurnedWork(int burnedWork) {
        return this.burnedWork == burnedWork;
    }

    protected final void updateBurnedWork(Object value) {
        setBurnedWork((Integer)value);
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("sprintId")) updateSprint(value);
            if (property.equals("remainingWork")) updateRemainingWork(value);
            if (property.equals("burnedWork")) updateBurnedWork(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadSprintReference(entityId);
    }

    // --- ensure integrity ---

    public void ensureIntegrity() {
        super.ensureIntegrity();
        if (!isSprintSet()) {
            repairMissingMaster();
            return;
        }
        try {
            getSprint();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead sprint reference");
            repairDeadSprintReference(this.sprintId);
        }
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    static scrum.server.sprint.SprintDao sprintDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSprintDao(scrum.server.sprint.SprintDao sprintDao) {
        GProjectSprintSnapshot.sprintDao = sprintDao;
    }

    static scrum.server.project.ProjectSprintSnapshotDao projectSprintSnapshotDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectSprintSnapshotDao(scrum.server.project.ProjectSprintSnapshotDao projectSprintSnapshotDao) {
        GProjectSprintSnapshot.projectSprintSnapshotDao = projectSprintSnapshotDao;
    }

}