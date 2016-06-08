// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.EntityGenerator










package scrum.server.sprint;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.ADatob;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.AStructure;
import ilarkesto.auth.AUser;
import ilarkesto.persistence.EntityDoesNotExistException;
import ilarkesto.base.StrExtend;

public abstract class GSprintDaySnapshot
            extends AEntity
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, java.lang.Comparable<SprintDaySnapshot> {

    // --- AEntity ---

    public final scrum.server.sprint.SprintDaySnapshotDao getDao() {
        return sprintDaySnapshotDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("sprintId", this.sprintId);
        properties.put("date", this.date == null ? null : this.date.toString());
        properties.put("remainingWork", this.remainingWork);
        properties.put("burnedWork", this.burnedWork);
        properties.put("burnedWorkFromDeleted", this.burnedWorkFromDeleted);
    }

    public int compareTo(SprintDaySnapshot other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GSprintDaySnapshot.class);

    public static final String TYPE = "sprintDaySnapshot";

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
    // - date
    // -----------------------------------------------------------

    private ilarkesto.core.time.Date date;

    public final ilarkesto.core.time.Date getDate() {
        return date;
    }

    public final void setDate(ilarkesto.core.time.Date date) {
        date = prepareDate(date);
        if (isDate(date)) return;
        this.date = date;
        updateLastModified();
        fireModified("date="+date);
    }

    protected ilarkesto.core.time.Date prepareDate(ilarkesto.core.time.Date date) {
        return date;
    }

    public final boolean isDateSet() {
        return this.date != null;
    }

    public final boolean isDate(ilarkesto.core.time.Date date) {
        if (this.date == null && date == null) return true;
        return this.date != null && this.date.equals(date);
    }

    protected final void updateDate(Object value) {
        value = value == null ? null : new ilarkesto.core.time.Date((String)value);
        setDate((ilarkesto.core.time.Date)value);
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

    // -----------------------------------------------------------
    // - burnedWorkFromDeleted
    // -----------------------------------------------------------

    private int burnedWorkFromDeleted;

    public final int getBurnedWorkFromDeleted() {
        return burnedWorkFromDeleted;
    }

    public final void setBurnedWorkFromDeleted(int burnedWorkFromDeleted) {
        burnedWorkFromDeleted = prepareBurnedWorkFromDeleted(burnedWorkFromDeleted);
        if (isBurnedWorkFromDeleted(burnedWorkFromDeleted)) return;
        this.burnedWorkFromDeleted = burnedWorkFromDeleted;
        updateLastModified();
        fireModified("burnedWorkFromDeleted="+burnedWorkFromDeleted);
    }

    protected int prepareBurnedWorkFromDeleted(int burnedWorkFromDeleted) {
        return burnedWorkFromDeleted;
    }

    public final boolean isBurnedWorkFromDeleted(int burnedWorkFromDeleted) {
        return this.burnedWorkFromDeleted == burnedWorkFromDeleted;
    }

    protected final void updateBurnedWorkFromDeleted(Object value) {
        setBurnedWorkFromDeleted((Integer)value);
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("sprintId")) updateSprint(value);
            if (property.equals("date")) updateDate(value);
            if (property.equals("remainingWork")) updateRemainingWork(value);
            if (property.equals("burnedWork")) updateBurnedWork(value);
            if (property.equals("burnedWorkFromDeleted")) updateBurnedWorkFromDeleted(value);
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
        GSprintDaySnapshot.sprintDao = sprintDao;
    }

    static scrum.server.sprint.SprintDaySnapshotDao sprintDaySnapshotDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSprintDaySnapshotDao(scrum.server.sprint.SprintDaySnapshotDao sprintDaySnapshotDao) {
        GSprintDaySnapshot.sprintDaySnapshotDao = sprintDaySnapshotDao;
    }

}