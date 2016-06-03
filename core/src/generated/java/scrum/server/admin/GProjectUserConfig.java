// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.EntityGenerator










package scrum.server.admin;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.ADatob;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.AStructure;
import ilarkesto.auth.AUser;
import ilarkesto.persistence.EntityDoesNotExistException;
import ilarkesto.base.StrExtend;

public abstract class GProjectUserConfig
            extends AEntity
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, java.lang.Comparable<ProjectUserConfig> {

    // --- AEntity ---

    public final scrum.server.admin.ProjectUserConfigDao getDao() {
        return projectUserConfigDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("projectId", this.projectId);
        properties.put("userId", this.userId);
        properties.put("color", this.color);
        properties.put("receiveEmailsOnProjectEvents", this.receiveEmailsOnProjectEvents);
        properties.put("misconducts", this.misconducts);
        properties.put("richtextAutosaveText", this.richtextAutosaveText);
        properties.put("richtextAutosaveField", this.richtextAutosaveField);
        properties.put("selectedEntitysIds", this.selectedEntitysIds);
        properties.put("online", this.online);
        properties.put("lastActivityDateAndTime", this.lastActivityDateAndTime == null ? null : this.lastActivityDateAndTime.toString());
        properties.put("pblFilterThemes", this.pblFilterThemes);
        properties.put("pblFilterQualitysIds", this.pblFilterQualitysIds);
        properties.put("pblFilterDateFrom", this.pblFilterDateFrom == null ? null : this.pblFilterDateFrom.toString());
        properties.put("pblFilterDateTo", this.pblFilterDateTo == null ? null : this.pblFilterDateTo.toString());
        properties.put("pblFilterEstimationFrom", this.pblFilterEstimationFrom);
        properties.put("pblFilterEstimationTo", this.pblFilterEstimationTo);
        properties.put("pblFilterText", this.pblFilterText);
    }

    public int compareTo(ProjectUserConfig other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GProjectUserConfig.class);

    public static final String TYPE = "projectUserConfig";

    // -----------------------------------------------------------
    // - project
    // -----------------------------------------------------------

    private String projectId;
    private transient scrum.server.project.Project projectCache;

    private void updateProjectCache() {
        projectCache = this.projectId == null ? null : (scrum.server.project.Project)projectDao.getById(this.projectId);
    }

    public final String getProjectId() {
        return this.projectId;
    }

    public final scrum.server.project.Project getProject() {
        if (projectCache == null) updateProjectCache();
        return projectCache;
    }

    public final void setProject(scrum.server.project.Project project) {
        project = prepareProject(project);
        if (isProject(project)) return;
        this.projectId = project == null ? null : project.getId();
        projectCache = project;
        updateLastModified();
        fireModified("project="+project);
    }

    protected scrum.server.project.Project prepareProject(scrum.server.project.Project project) {
        return project;
    }

    protected void repairDeadProjectReference(String entityId) {
        if (this.projectId == null || entityId.equals(this.projectId)) {
            repairMissingMaster();
        }
    }

    public final boolean isProjectSet() {
        return this.projectId != null;
    }

    public final boolean isProject(scrum.server.project.Project project) {
        if (this.projectId == null && project == null) return true;
        return project != null && project.getId().equals(this.projectId);
    }

    protected final void updateProject(Object value) {
        setProject(value == null ? null : (scrum.server.project.Project)projectDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - user
    // -----------------------------------------------------------

    private String userId;
    private transient scrum.server.admin.User userCache;

    private void updateUserCache() {
        userCache = this.userId == null ? null : (scrum.server.admin.User)userDao.getById(this.userId);
    }

    public final String getUserId() {
        return this.userId;
    }

    public final scrum.server.admin.User getUser() {
        if (userCache == null) updateUserCache();
        return userCache;
    }

    public final void setUser(scrum.server.admin.User user) {
        user = prepareUser(user);
        if (isUser(user)) return;
        this.userId = user == null ? null : user.getId();
        userCache = user;
        updateLastModified();
        fireModified("user="+user);
    }

    protected scrum.server.admin.User prepareUser(scrum.server.admin.User user) {
        return user;
    }

    protected void repairDeadUserReference(String entityId) {
        if (this.userId == null || entityId.equals(this.userId)) {
            repairMissingMaster();
        }
    }

    public final boolean isUserSet() {
        return this.userId != null;
    }

    public final boolean isUser(scrum.server.admin.User user) {
        if (this.userId == null && user == null) return true;
        return user != null && user.getId().equals(this.userId);
    }

    protected final void updateUser(Object value) {
        setUser(value == null ? null : (scrum.server.admin.User)userDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - color
    // -----------------------------------------------------------

    private java.lang.String color;

    public final java.lang.String getColor() {
        return color;
    }

    public final void setColor(java.lang.String color) {
        color = prepareColor(color);
        if (isColor(color)) return;
        this.color = color;
        updateLastModified();
        fireModified("color="+color);
    }

    protected java.lang.String prepareColor(java.lang.String color) {
        // color = StrExtend.removeUnreadableChars(color);
        return color;
    }

    public final boolean isColorSet() {
        return this.color != null;
    }

    public final boolean isColor(java.lang.String color) {
        if (this.color == null && color == null) return true;
        return this.color != null && this.color.equals(color);
    }

    protected final void updateColor(Object value) {
        setColor((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - receiveEmailsOnProjectEvents
    // -----------------------------------------------------------

    private boolean receiveEmailsOnProjectEvents;

    public final boolean isReceiveEmailsOnProjectEvents() {
        return receiveEmailsOnProjectEvents;
    }

    public final void setReceiveEmailsOnProjectEvents(boolean receiveEmailsOnProjectEvents) {
        receiveEmailsOnProjectEvents = prepareReceiveEmailsOnProjectEvents(receiveEmailsOnProjectEvents);
        if (isReceiveEmailsOnProjectEvents(receiveEmailsOnProjectEvents)) return;
        this.receiveEmailsOnProjectEvents = receiveEmailsOnProjectEvents;
        updateLastModified();
        fireModified("receiveEmailsOnProjectEvents="+receiveEmailsOnProjectEvents);
    }

    protected boolean prepareReceiveEmailsOnProjectEvents(boolean receiveEmailsOnProjectEvents) {
        return receiveEmailsOnProjectEvents;
    }

    public final boolean isReceiveEmailsOnProjectEvents(boolean receiveEmailsOnProjectEvents) {
        return this.receiveEmailsOnProjectEvents == receiveEmailsOnProjectEvents;
    }

    protected final void updateReceiveEmailsOnProjectEvents(Object value) {
        setReceiveEmailsOnProjectEvents((Boolean)value);
    }

    // -----------------------------------------------------------
    // - misconducts
    // -----------------------------------------------------------

    private int misconducts;

    public final int getMisconducts() {
        return misconducts;
    }

    public final void setMisconducts(int misconducts) {
        misconducts = prepareMisconducts(misconducts);
        if (isMisconducts(misconducts)) return;
        this.misconducts = misconducts;
        updateLastModified();
        fireModified("misconducts="+misconducts);
    }

    protected int prepareMisconducts(int misconducts) {
        return misconducts;
    }

    public final boolean isMisconducts(int misconducts) {
        return this.misconducts == misconducts;
    }

    protected final void updateMisconducts(Object value) {
        setMisconducts((Integer)value);
    }

    // -----------------------------------------------------------
    // - richtextAutosaveText
    // -----------------------------------------------------------

    private java.lang.String richtextAutosaveText;

    public final java.lang.String getRichtextAutosaveText() {
        return richtextAutosaveText;
    }

    public final void setRichtextAutosaveText(java.lang.String richtextAutosaveText) {
        richtextAutosaveText = prepareRichtextAutosaveText(richtextAutosaveText);
        if (isRichtextAutosaveText(richtextAutosaveText)) return;
        this.richtextAutosaveText = richtextAutosaveText;
        updateLastModified();
        fireModified("richtextAutosaveText="+richtextAutosaveText);
    }

    protected java.lang.String prepareRichtextAutosaveText(java.lang.String richtextAutosaveText) {
        // richtextAutosaveText = StrExtend.removeUnreadableChars(richtextAutosaveText);
        return richtextAutosaveText;
    }

    public final boolean isRichtextAutosaveTextSet() {
        return this.richtextAutosaveText != null;
    }

    public final boolean isRichtextAutosaveText(java.lang.String richtextAutosaveText) {
        if (this.richtextAutosaveText == null && richtextAutosaveText == null) return true;
        return this.richtextAutosaveText != null && this.richtextAutosaveText.equals(richtextAutosaveText);
    }

    protected final void updateRichtextAutosaveText(Object value) {
        setRichtextAutosaveText((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - richtextAutosaveField
    // -----------------------------------------------------------

    private java.lang.String richtextAutosaveField;

    public final java.lang.String getRichtextAutosaveField() {
        return richtextAutosaveField;
    }

    public final void setRichtextAutosaveField(java.lang.String richtextAutosaveField) {
        richtextAutosaveField = prepareRichtextAutosaveField(richtextAutosaveField);
        if (isRichtextAutosaveField(richtextAutosaveField)) return;
        this.richtextAutosaveField = richtextAutosaveField;
        updateLastModified();
        fireModified("richtextAutosaveField="+richtextAutosaveField);
    }

    protected java.lang.String prepareRichtextAutosaveField(java.lang.String richtextAutosaveField) {
        // richtextAutosaveField = StrExtend.removeUnreadableChars(richtextAutosaveField);
        return richtextAutosaveField;
    }

    public final boolean isRichtextAutosaveFieldSet() {
        return this.richtextAutosaveField != null;
    }

    public final boolean isRichtextAutosaveField(java.lang.String richtextAutosaveField) {
        if (this.richtextAutosaveField == null && richtextAutosaveField == null) return true;
        return this.richtextAutosaveField != null && this.richtextAutosaveField.equals(richtextAutosaveField);
    }

    protected final void updateRichtextAutosaveField(Object value) {
        setRichtextAutosaveField((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - selectedEntitysIds
    // -----------------------------------------------------------

    private java.util.List<java.lang.String> selectedEntitysIds = new java.util.ArrayList<java.lang.String>();

    public final java.util.List<java.lang.String> getSelectedEntitysIds() {
        return new java.util.ArrayList<java.lang.String>(selectedEntitysIds);
    }

    public final void setSelectedEntitysIds(Collection<java.lang.String> selectedEntitysIds) {
        selectedEntitysIds = prepareSelectedEntitysIds(selectedEntitysIds);
        if (selectedEntitysIds == null) selectedEntitysIds = Collections.emptyList();
        if (this.selectedEntitysIds.equals(selectedEntitysIds)) return;
        this.selectedEntitysIds = new java.util.ArrayList<java.lang.String>(selectedEntitysIds);
        updateLastModified();
    }

    protected Collection<java.lang.String> prepareSelectedEntitysIds(Collection<java.lang.String> selectedEntitysIds) {
        return selectedEntitysIds;
    }

    public final boolean containsSelectedEntitysId(java.lang.String selectedEntitysId) {
        if (selectedEntitysId == null) return false;
        return this.selectedEntitysIds.contains(selectedEntitysId);
    }

    public final int getSelectedEntitysIdsCount() {
        return this.selectedEntitysIds.size();
    }

    public final boolean isSelectedEntitysIdsEmpty() {
        return this.selectedEntitysIds.isEmpty();
    }

    public final boolean addSelectedEntitysId(java.lang.String selectedEntitysId) {
        if (selectedEntitysId == null) throw new IllegalArgumentException("selectedEntitysId == null");
        boolean added = this.selectedEntitysIds.add(selectedEntitysId);
        if (added) updateLastModified();
        return added;
    }

    public final boolean addSelectedEntitysIds(Collection<java.lang.String> selectedEntitysIds) {
        if (selectedEntitysIds == null) throw new IllegalArgumentException("selectedEntitysIds == null");
        boolean added = false;
        for (java.lang.String selectedEntitysId : selectedEntitysIds) {
            added = added | this.selectedEntitysIds.add(selectedEntitysId);
        }
        return added;
    }

    public final boolean removeSelectedEntitysId(java.lang.String selectedEntitysId) {
        if (selectedEntitysId == null) throw new IllegalArgumentException("selectedEntitysId == null");
        if (this.selectedEntitysIds == null) return false;
        boolean removed = this.selectedEntitysIds.remove(selectedEntitysId);
        if (removed) updateLastModified();
        return removed;
    }

    public final boolean removeSelectedEntitysIds(Collection<java.lang.String> selectedEntitysIds) {
        if (selectedEntitysIds == null) return false;
        if (selectedEntitysIds.isEmpty()) return false;
        boolean removed = false;
        for (java.lang.String _element: selectedEntitysIds) {
            removed = removed | removeSelectedEntitysId(_element);
        }
        return removed;
    }

    public final boolean clearSelectedEntitysIds() {
        if (this.selectedEntitysIds.isEmpty()) return false;
        this.selectedEntitysIds.clear();
        updateLastModified();
        return true;
    }

    public final String getSelectedEntitysIdsAsCommaSeparatedString() {
        if (this.selectedEntitysIds.isEmpty()) return null;
        return StrExtend.concat(this.selectedEntitysIds,", ");
    }

    public final void setSelectedEntitysIdsAsCommaSeparatedString(String selectedEntitysIds) {
        setSelectedEntitysIds(StrExtend.parseCommaSeparatedString(selectedEntitysIds));
    }

    protected final void updateSelectedEntitysIds(Object value) {
        setSelectedEntitysIds((java.util.List<java.lang.String>) value);
    }

    // -----------------------------------------------------------
    // - online
    // -----------------------------------------------------------

    private boolean online;

    public final boolean isOnline() {
        return online;
    }

    public final void setOnline(boolean online) {
        online = prepareOnline(online);
        if (isOnline(online)) return;
        this.online = online;
        updateLastModified();
    }

    protected boolean prepareOnline(boolean online) {
        return online;
    }

    public final boolean isOnline(boolean online) {
        return this.online == online;
    }

    protected final void updateOnline(Object value) {
        setOnline((Boolean)value);
    }

    // -----------------------------------------------------------
    // - lastActivityDateAndTime
    // -----------------------------------------------------------

    private ilarkesto.core.time.DateAndTime lastActivityDateAndTime;

    public final ilarkesto.core.time.DateAndTime getLastActivityDateAndTime() {
        return lastActivityDateAndTime;
    }

    public final void setLastActivityDateAndTime(ilarkesto.core.time.DateAndTime lastActivityDateAndTime) {
        lastActivityDateAndTime = prepareLastActivityDateAndTime(lastActivityDateAndTime);
        if (isLastActivityDateAndTime(lastActivityDateAndTime)) return;
        this.lastActivityDateAndTime = lastActivityDateAndTime;
        updateLastModified();
    }

    protected ilarkesto.core.time.DateAndTime prepareLastActivityDateAndTime(ilarkesto.core.time.DateAndTime lastActivityDateAndTime) {
        return lastActivityDateAndTime;
    }

    public final boolean isLastActivityDateAndTimeSet() {
        return this.lastActivityDateAndTime != null;
    }

    public final boolean isLastActivityDateAndTime(ilarkesto.core.time.DateAndTime lastActivityDateAndTime) {
        if (this.lastActivityDateAndTime == null && lastActivityDateAndTime == null) return true;
        return this.lastActivityDateAndTime != null && this.lastActivityDateAndTime.equals(lastActivityDateAndTime);
    }

    protected final void updateLastActivityDateAndTime(Object value) {
        value = value == null ? null : new ilarkesto.core.time.DateAndTime((String)value);
        setLastActivityDateAndTime((ilarkesto.core.time.DateAndTime)value);
    }

    // -----------------------------------------------------------
    // - pblFilterThemes
    // -----------------------------------------------------------

    private java.util.List<java.lang.String> pblFilterThemes = new java.util.ArrayList<java.lang.String>();

    public final java.util.List<java.lang.String> getPblFilterThemes() {
        return new java.util.ArrayList<java.lang.String>(pblFilterThemes);
    }

    public final void setPblFilterThemes(Collection<java.lang.String> pblFilterThemes) {
        pblFilterThemes = preparePblFilterThemes(pblFilterThemes);
        if (pblFilterThemes == null) pblFilterThemes = Collections.emptyList();
        if (this.pblFilterThemes.equals(pblFilterThemes)) return;
        this.pblFilterThemes = new java.util.ArrayList<java.lang.String>(pblFilterThemes);
        updateLastModified();
        fireModified("pblFilterThemes="+StrExtend.format(pblFilterThemes));
    }

    protected Collection<java.lang.String> preparePblFilterThemes(Collection<java.lang.String> pblFilterThemes) {
        return pblFilterThemes;
    }

    public final boolean containsPblFilterTheme(java.lang.String pblFilterTheme) {
        if (pblFilterTheme == null) return false;
        return this.pblFilterThemes.contains(pblFilterTheme);
    }

    public final int getPblFilterThemesCount() {
        return this.pblFilterThemes.size();
    }

    public final boolean isPblFilterThemesEmpty() {
        return this.pblFilterThemes.isEmpty();
    }

    public final boolean addPblFilterTheme(java.lang.String pblFilterTheme) {
        if (pblFilterTheme == null) throw new IllegalArgumentException("pblFilterTheme == null");
        boolean added = this.pblFilterThemes.add(pblFilterTheme);
        if (added) updateLastModified();
        if (added) fireModified("pblFilterThemes+=" + pblFilterTheme);
        return added;
    }

    public final boolean addPblFilterThemes(Collection<java.lang.String> pblFilterThemes) {
        if (pblFilterThemes == null) throw new IllegalArgumentException("pblFilterThemes == null");
        boolean added = false;
        for (java.lang.String pblFilterTheme : pblFilterThemes) {
            added = added | this.pblFilterThemes.add(pblFilterTheme);
        }
        return added;
    }

    public final boolean removePblFilterTheme(java.lang.String pblFilterTheme) {
        if (pblFilterTheme == null) throw new IllegalArgumentException("pblFilterTheme == null");
        if (this.pblFilterThemes == null) return false;
        boolean removed = this.pblFilterThemes.remove(pblFilterTheme);
        if (removed) updateLastModified();
        if (removed) fireModified("pblFilterThemes-=" + pblFilterTheme);
        return removed;
    }

    public final boolean removePblFilterThemes(Collection<java.lang.String> pblFilterThemes) {
        if (pblFilterThemes == null) return false;
        if (pblFilterThemes.isEmpty()) return false;
        boolean removed = false;
        for (java.lang.String _element: pblFilterThemes) {
            removed = removed | removePblFilterTheme(_element);
        }
        return removed;
    }

    public final boolean clearPblFilterThemes() {
        if (this.pblFilterThemes.isEmpty()) return false;
        this.pblFilterThemes.clear();
        updateLastModified();
        fireModified("pblFilterThemes cleared");
        return true;
    }

    public final String getPblFilterThemesAsCommaSeparatedString() {
        if (this.pblFilterThemes.isEmpty()) return null;
        return StrExtend.concat(this.pblFilterThemes,", ");
    }

    public final void setPblFilterThemesAsCommaSeparatedString(String pblFilterThemes) {
        setPblFilterThemes(StrExtend.parseCommaSeparatedString(pblFilterThemes));
    }

    protected final void updatePblFilterThemes(Object value) {
        setPblFilterThemes((java.util.List<java.lang.String>) value);
    }

    // -----------------------------------------------------------
    // - pblFilterQualitys
    // -----------------------------------------------------------

    private java.util.Set<String> pblFilterQualitysIds = new java.util.HashSet<String>();

    public final java.util.Set<scrum.server.project.Quality> getPblFilterQualitys() {
        return (java.util.Set) qualityDao.getByIdsAsSet(this.pblFilterQualitysIds);
    }

    public final void setPblFilterQualitys(Collection<scrum.server.project.Quality> pblFilterQualitys) {
        pblFilterQualitys = preparePblFilterQualitys(pblFilterQualitys);
        if (pblFilterQualitys == null) pblFilterQualitys = Collections.emptyList();
        java.util.Set<String> ids = getIdsAsSet(pblFilterQualitys);
        if (this.pblFilterQualitysIds.equals(ids)) return;
        this.pblFilterQualitysIds = ids;
        updateLastModified();
        fireModified("pblFilterQualitys="+StrExtend.format(pblFilterQualitys));
    }

    protected Collection<scrum.server.project.Quality> preparePblFilterQualitys(Collection<scrum.server.project.Quality> pblFilterQualitys) {
        return pblFilterQualitys;
    }

    protected void repairDeadPblFilterQualityReference(String entityId) {
        if (this.pblFilterQualitysIds.remove(entityId)) fireModified("pblFilterQualitys-=" + entityId);
    }

    public final boolean containsPblFilterQuality(scrum.server.project.Quality pblFilterQuality) {
        if (pblFilterQuality == null) return false;
        return this.pblFilterQualitysIds.contains(pblFilterQuality.getId());
    }

    public final int getPblFilterQualitysCount() {
        return this.pblFilterQualitysIds.size();
    }

    public final boolean isPblFilterQualitysEmpty() {
        return this.pblFilterQualitysIds.isEmpty();
    }

    public final boolean addPblFilterQuality(scrum.server.project.Quality pblFilterQuality) {
        if (pblFilterQuality == null) throw new IllegalArgumentException("pblFilterQuality == null");
        boolean added = this.pblFilterQualitysIds.add(pblFilterQuality.getId());
        if (added) updateLastModified();
        if (added) fireModified("pblFilterQualitys+=" + pblFilterQuality);
        return added;
    }

    public final boolean addPblFilterQualitys(Collection<scrum.server.project.Quality> pblFilterQualitys) {
        if (pblFilterQualitys == null) throw new IllegalArgumentException("pblFilterQualitys == null");
        boolean added = false;
        for (scrum.server.project.Quality pblFilterQuality : pblFilterQualitys) {
            added = added | this.pblFilterQualitysIds.add(pblFilterQuality.getId());
        }
        return added;
    }

    public final boolean removePblFilterQuality(scrum.server.project.Quality pblFilterQuality) {
        if (pblFilterQuality == null) throw new IllegalArgumentException("pblFilterQuality == null");
        if (this.pblFilterQualitysIds == null) return false;
        boolean removed = this.pblFilterQualitysIds.remove(pblFilterQuality.getId());
        if (removed) updateLastModified();
        if (removed) fireModified("pblFilterQualitys-=" + pblFilterQuality);
        return removed;
    }

    public final boolean removePblFilterQualitys(Collection<scrum.server.project.Quality> pblFilterQualitys) {
        if (pblFilterQualitys == null) return false;
        if (pblFilterQualitys.isEmpty()) return false;
        boolean removed = false;
        for (scrum.server.project.Quality _element: pblFilterQualitys) {
            removed = removed | removePblFilterQuality(_element);
        }
        return removed;
    }

    public final boolean clearPblFilterQualitys() {
        if (this.pblFilterQualitysIds.isEmpty()) return false;
        this.pblFilterQualitysIds.clear();
        updateLastModified();
        fireModified("pblFilterQualitys cleared");
        return true;
    }

    protected final void updatePblFilterQualitys(Object value) {
        Collection<String> ids = (Collection<String>) value;
        setPblFilterQualitys((java.util.Set) qualityDao.getByIdsAsSet(ids));
    }

    // -----------------------------------------------------------
    // - pblFilterDateFrom
    // -----------------------------------------------------------

    private ilarkesto.core.time.Date pblFilterDateFrom;

    public final ilarkesto.core.time.Date getPblFilterDateFrom() {
        return pblFilterDateFrom;
    }

    public final void setPblFilterDateFrom(ilarkesto.core.time.Date pblFilterDateFrom) {
        pblFilterDateFrom = preparePblFilterDateFrom(pblFilterDateFrom);
        if (isPblFilterDateFrom(pblFilterDateFrom)) return;
        this.pblFilterDateFrom = pblFilterDateFrom;
        updateLastModified();
        fireModified("pblFilterDateFrom="+pblFilterDateFrom);
    }

    protected ilarkesto.core.time.Date preparePblFilterDateFrom(ilarkesto.core.time.Date pblFilterDateFrom) {
        return pblFilterDateFrom;
    }

    public final boolean isPblFilterDateFromSet() {
        return this.pblFilterDateFrom != null;
    }

    public final boolean isPblFilterDateFrom(ilarkesto.core.time.Date pblFilterDateFrom) {
        if (this.pblFilterDateFrom == null && pblFilterDateFrom == null) return true;
        return this.pblFilterDateFrom != null && this.pblFilterDateFrom.equals(pblFilterDateFrom);
    }

    protected final void updatePblFilterDateFrom(Object value) {
        value = value == null ? null : new ilarkesto.core.time.Date((String)value);
        setPblFilterDateFrom((ilarkesto.core.time.Date)value);
    }

    // -----------------------------------------------------------
    // - pblFilterDateTo
    // -----------------------------------------------------------

    private ilarkesto.core.time.Date pblFilterDateTo;

    public final ilarkesto.core.time.Date getPblFilterDateTo() {
        return pblFilterDateTo;
    }

    public final void setPblFilterDateTo(ilarkesto.core.time.Date pblFilterDateTo) {
        pblFilterDateTo = preparePblFilterDateTo(pblFilterDateTo);
        if (isPblFilterDateTo(pblFilterDateTo)) return;
        this.pblFilterDateTo = pblFilterDateTo;
        updateLastModified();
        fireModified("pblFilterDateTo="+pblFilterDateTo);
    }

    protected ilarkesto.core.time.Date preparePblFilterDateTo(ilarkesto.core.time.Date pblFilterDateTo) {
        return pblFilterDateTo;
    }

    public final boolean isPblFilterDateToSet() {
        return this.pblFilterDateTo != null;
    }

    public final boolean isPblFilterDateTo(ilarkesto.core.time.Date pblFilterDateTo) {
        if (this.pblFilterDateTo == null && pblFilterDateTo == null) return true;
        return this.pblFilterDateTo != null && this.pblFilterDateTo.equals(pblFilterDateTo);
    }

    protected final void updatePblFilterDateTo(Object value) {
        value = value == null ? null : new ilarkesto.core.time.Date((String)value);
        setPblFilterDateTo((ilarkesto.core.time.Date)value);
    }

    // -----------------------------------------------------------
    // - pblFilterEstimationFrom
    // -----------------------------------------------------------

    private java.lang.Float pblFilterEstimationFrom;

    public final java.lang.Float getPblFilterEstimationFrom() {
        return pblFilterEstimationFrom;
    }

    public final void setPblFilterEstimationFrom(java.lang.Float pblFilterEstimationFrom) {
        pblFilterEstimationFrom = preparePblFilterEstimationFrom(pblFilterEstimationFrom);
        if (isPblFilterEstimationFrom(pblFilterEstimationFrom)) return;
        this.pblFilterEstimationFrom = pblFilterEstimationFrom;
        updateLastModified();
        fireModified("pblFilterEstimationFrom="+pblFilterEstimationFrom);
    }

    protected java.lang.Float preparePblFilterEstimationFrom(java.lang.Float pblFilterEstimationFrom) {
        return pblFilterEstimationFrom;
    }

    public final boolean isPblFilterEstimationFromSet() {
        return this.pblFilterEstimationFrom != null;
    }

    public final boolean isPblFilterEstimationFrom(java.lang.Float pblFilterEstimationFrom) {
        if (this.pblFilterEstimationFrom == null && pblFilterEstimationFrom == null) return true;
        return this.pblFilterEstimationFrom != null && this.pblFilterEstimationFrom.equals(pblFilterEstimationFrom);
    }

    protected final void updatePblFilterEstimationFrom(Object value) {
        setPblFilterEstimationFrom((java.lang.Float)value);
    }

    // -----------------------------------------------------------
    // - pblFilterEstimationTo
    // -----------------------------------------------------------

    private java.lang.Float pblFilterEstimationTo;

    public final java.lang.Float getPblFilterEstimationTo() {
        return pblFilterEstimationTo;
    }

    public final void setPblFilterEstimationTo(java.lang.Float pblFilterEstimationTo) {
        pblFilterEstimationTo = preparePblFilterEstimationTo(pblFilterEstimationTo);
        if (isPblFilterEstimationTo(pblFilterEstimationTo)) return;
        this.pblFilterEstimationTo = pblFilterEstimationTo;
        updateLastModified();
        fireModified("pblFilterEstimationTo="+pblFilterEstimationTo);
    }

    protected java.lang.Float preparePblFilterEstimationTo(java.lang.Float pblFilterEstimationTo) {
        return pblFilterEstimationTo;
    }

    public final boolean isPblFilterEstimationToSet() {
        return this.pblFilterEstimationTo != null;
    }

    public final boolean isPblFilterEstimationTo(java.lang.Float pblFilterEstimationTo) {
        if (this.pblFilterEstimationTo == null && pblFilterEstimationTo == null) return true;
        return this.pblFilterEstimationTo != null && this.pblFilterEstimationTo.equals(pblFilterEstimationTo);
    }

    protected final void updatePblFilterEstimationTo(Object value) {
        setPblFilterEstimationTo((java.lang.Float)value);
    }

    // -----------------------------------------------------------
    // - pblFilterText
    // -----------------------------------------------------------

    private java.lang.String pblFilterText;

    public final java.lang.String getPblFilterText() {
        return pblFilterText;
    }

    public final void setPblFilterText(java.lang.String pblFilterText) {
        pblFilterText = preparePblFilterText(pblFilterText);
        if (isPblFilterText(pblFilterText)) return;
        this.pblFilterText = pblFilterText;
        updateLastModified();
        fireModified("pblFilterText="+pblFilterText);
    }

    protected java.lang.String preparePblFilterText(java.lang.String pblFilterText) {
        // pblFilterText = StrExtend.removeUnreadableChars(pblFilterText);
        return pblFilterText;
    }

    public final boolean isPblFilterTextSet() {
        return this.pblFilterText != null;
    }

    public final boolean isPblFilterText(java.lang.String pblFilterText) {
        if (this.pblFilterText == null && pblFilterText == null) return true;
        return this.pblFilterText != null && this.pblFilterText.equals(pblFilterText);
    }

    protected final void updatePblFilterText(Object value) {
        setPblFilterText((java.lang.String)value);
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("projectId")) updateProject(value);
            if (property.equals("userId")) updateUser(value);
            if (property.equals("color")) updateColor(value);
            if (property.equals("receiveEmailsOnProjectEvents")) updateReceiveEmailsOnProjectEvents(value);
            if (property.equals("misconducts")) updateMisconducts(value);
            if (property.equals("richtextAutosaveText")) updateRichtextAutosaveText(value);
            if (property.equals("richtextAutosaveField")) updateRichtextAutosaveField(value);
            if (property.equals("selectedEntitysIds")) updateSelectedEntitysIds(value);
            if (property.equals("online")) updateOnline(value);
            if (property.equals("lastActivityDateAndTime")) updateLastActivityDateAndTime(value);
            if (property.equals("pblFilterThemes")) updatePblFilterThemes(value);
            if (property.equals("pblFilterQualitysIds")) updatePblFilterQualitys(value);
            if (property.equals("pblFilterDateFrom")) updatePblFilterDateFrom(value);
            if (property.equals("pblFilterDateTo")) updatePblFilterDateTo(value);
            if (property.equals("pblFilterEstimationFrom")) updatePblFilterEstimationFrom(value);
            if (property.equals("pblFilterEstimationTo")) updatePblFilterEstimationTo(value);
            if (property.equals("pblFilterText")) updatePblFilterText(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadProjectReference(entityId);
        repairDeadUserReference(entityId);
        if (this.selectedEntitysIds == null) this.selectedEntitysIds = new java.util.ArrayList<java.lang.String>();
        if (this.pblFilterThemes == null) this.pblFilterThemes = new java.util.ArrayList<java.lang.String>();
        if (this.pblFilterQualitysIds == null) this.pblFilterQualitysIds = new java.util.HashSet<String>();
        repairDeadPblFilterQualityReference(entityId);
    }

    // --- ensure integrity ---

    public void ensureIntegrity() {
        super.ensureIntegrity();
        if (!isProjectSet()) {
            repairMissingMaster();
            return;
        }
        try {
            getProject();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead project reference");
            repairDeadProjectReference(this.projectId);
        }
        if (!isUserSet()) {
            repairMissingMaster();
            return;
        }
        try {
            getUser();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead user reference");
            repairDeadUserReference(this.userId);
        }
        if (this.selectedEntitysIds == null) this.selectedEntitysIds = new java.util.ArrayList<java.lang.String>();
        if (this.pblFilterThemes == null) this.pblFilterThemes = new java.util.ArrayList<java.lang.String>();
        if (this.pblFilterQualitysIds == null) this.pblFilterQualitysIds = new java.util.HashSet<String>();
        Set<String> pblFilterQualitys = new HashSet<String>(this.pblFilterQualitysIds);
        for (String entityId : pblFilterQualitys) {
            try {
                qualityDao.getById(entityId);
            } catch (EntityDoesNotExistException ex) {
                LOG.info("Repairing dead pblFilterQuality reference");
                repairDeadPblFilterQualityReference(entityId);
            }
        }
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    static scrum.server.project.ProjectDao projectDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectDao(scrum.server.project.ProjectDao projectDao) {
        GProjectUserConfig.projectDao = projectDao;
    }

    static scrum.server.project.QualityDao qualityDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setQualityDao(scrum.server.project.QualityDao qualityDao) {
        GProjectUserConfig.qualityDao = qualityDao;
    }

    static scrum.server.admin.ProjectUserConfigDao projectUserConfigDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectUserConfigDao(scrum.server.admin.ProjectUserConfigDao projectUserConfigDao) {
        GProjectUserConfig.projectUserConfigDao = projectUserConfigDao;
    }

}