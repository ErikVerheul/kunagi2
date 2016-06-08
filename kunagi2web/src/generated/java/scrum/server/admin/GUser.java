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

public abstract class GUser
            extends AUser
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, ilarkesto.auth.EditProtected<scrum.server.admin.User>, ilarkesto.search.Searchable, java.lang.Comparable<User> {

    // --- AEntity ---

    public final scrum.server.admin.UserDao getDao() {
        return userDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("name", this.name);
        properties.put("publicName", this.publicName);
        properties.put("fullName", this.fullName);
        properties.put("admin", this.admin);
        properties.put("emailVerified", this.emailVerified);
        properties.put("email", this.email);
        properties.put("currentProjectId", this.currentProjectId);
        properties.put("color", this.color);
        properties.put("lastLoginDateAndTime", this.lastLoginDateAndTime == null ? null : this.lastLoginDateAndTime.toString());
        properties.put("registrationDateAndTime", this.registrationDateAndTime == null ? null : this.registrationDateAndTime.toString());
        properties.put("disabled", this.disabled);
        properties.put("hideUserGuideBlog", this.hideUserGuideBlog);
        properties.put("hideUserGuideCalendar", this.hideUserGuideCalendar);
        properties.put("hideUserGuideFiles", this.hideUserGuideFiles);
        properties.put("hideUserGuideForum", this.hideUserGuideForum);
        properties.put("hideUserGuideImpediments", this.hideUserGuideImpediments);
        properties.put("hideUserGuideIssues", this.hideUserGuideIssues);
        properties.put("hideUserGuideJournal", this.hideUserGuideJournal);
        properties.put("hideUserGuideNextSprint", this.hideUserGuideNextSprint);
        properties.put("hideUserGuideProductBacklog", this.hideUserGuideProductBacklog);
        properties.put("hideUserGuideCourtroom", this.hideUserGuideCourtroom);
        properties.put("hideUserGuideQualityBacklog", this.hideUserGuideQualityBacklog);
        properties.put("hideUserGuideReleases", this.hideUserGuideReleases);
        properties.put("hideUserGuideRisks", this.hideUserGuideRisks);
        properties.put("hideUserGuideSprintBacklog", this.hideUserGuideSprintBacklog);
        properties.put("hideUserGuideWhiteboard", this.hideUserGuideWhiteboard);
        properties.put("loginToken", this.loginToken);
        properties.put("openId", this.openId);
    }

    public int compareTo(User other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    public final java.util.Set<scrum.server.project.Project> getProjects() {
        return projectDao.getProjectsByParticipant((User)this);
    }

    public final java.util.Set<scrum.server.sprint.Sprint> getSprints() {
        return sprintDao.getSprintsByProductOwner((User)this);
    }

    public final java.util.Set<scrum.server.collaboration.Emoticon> getEmoticons() {
        return emoticonDao.getEmoticonsByOwner((User)this);
    }

    public final java.util.Set<scrum.server.admin.ProjectUserConfig> getProjectUserConfigs() {
        return projectUserConfigDao.getProjectUserConfigsByUser((User)this);
    }

    public final java.util.Set<scrum.server.issues.Issue> getIssues() {
        return issueDao.getIssuesByCreator((User)this);
    }

    public final java.util.Set<scrum.server.sprint.Task> getTasks() {
        return taskDao.getTasksByOwner((User)this);
    }

    public final java.util.Set<scrum.server.journal.Change> getChanges() {
        return changeDao.getChangesByUser((User)this);
    }

    public final java.util.Set<scrum.server.collaboration.Comment> getComments() {
        return commentDao.getCommentsByAuthor((User)this);
    }

    public final java.util.Set<scrum.server.collaboration.ChatMessage> getChatMessages() {
        return chatMessageDao.getChatMessagesByAuthor((User)this);
    }

    public final java.util.Set<scrum.server.pr.BlogEntry> getBlogEntrys() {
        return blogEntryDao.getBlogEntrysByAuthor((User)this);
    }

    public final java.util.Set<scrum.server.estimation.RequirementEstimationVote> getRequirementEstimationVotes() {
        return requirementEstimationVoteDao.getRequirementEstimationVotesByUser((User)this);
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GUser.class);

    public static final String TYPE = "user";


    // -----------------------------------------------------------
    // - Searchable
    // -----------------------------------------------------------

    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getName(), key)) return true;
        if (matchesKey(getPublicName(), key)) return true;
        if (matchesKey(getFullName(), key)) return true;
        if (matchesKey(getEmail(), key)) return true;
        return false;
    }

    // -----------------------------------------------------------
    // - name
    // -----------------------------------------------------------

    private java.lang.String name;

    public final java.lang.String getName() {
        return name;
    }

    public final void setName(java.lang.String name) {
        name = prepareName(name);
        if (isName(name)) return;
        if (name != null && getDao().getUserByName(name) != null) throw new ilarkesto.persistence.UniqueFieldConstraintException(this, "name", name);
        this.name = name;
        updateLastModified();
        fireModified("name="+name);
    }

    protected java.lang.String prepareName(java.lang.String name) {
        // name = StrExtend.removeUnreadableChars(name);
        return name;
    }

    public final boolean isNameSet() {
        return this.name != null;
    }

    public final boolean isName(java.lang.String name) {
        if (this.name == null && name == null) return true;
        return this.name != null && this.name.equals(name);
    }

    protected final void updateName(Object value) {
        setName((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - publicName
    // -----------------------------------------------------------

    private java.lang.String publicName;

    public final java.lang.String getPublicName() {
        return publicName;
    }

    public final void setPublicName(java.lang.String publicName) {
        publicName = preparePublicName(publicName);
        if (isPublicName(publicName)) return;
        this.publicName = publicName;
        updateLastModified();
        fireModified("publicName="+publicName);
    }

    protected java.lang.String preparePublicName(java.lang.String publicName) {
        // publicName = StrExtend.removeUnreadableChars(publicName);
        return publicName;
    }

    public final boolean isPublicNameSet() {
        return this.publicName != null;
    }

    public final boolean isPublicName(java.lang.String publicName) {
        if (this.publicName == null && publicName == null) return true;
        return this.publicName != null && this.publicName.equals(publicName);
    }

    protected final void updatePublicName(Object value) {
        setPublicName((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - fullName
    // -----------------------------------------------------------

    private java.lang.String fullName;

    public final java.lang.String getFullName() {
        return fullName;
    }

    public final void setFullName(java.lang.String fullName) {
        fullName = prepareFullName(fullName);
        if (isFullName(fullName)) return;
        this.fullName = fullName;
        updateLastModified();
        fireModified("fullName="+fullName);
    }

    protected java.lang.String prepareFullName(java.lang.String fullName) {
        // fullName = StrExtend.removeUnreadableChars(fullName);
        return fullName;
    }

    public final boolean isFullNameSet() {
        return this.fullName != null;
    }

    public final boolean isFullName(java.lang.String fullName) {
        if (this.fullName == null && fullName == null) return true;
        return this.fullName != null && this.fullName.equals(fullName);
    }

    protected final void updateFullName(Object value) {
        setFullName((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - admin
    // -----------------------------------------------------------

    private boolean admin;

    public final boolean isAdmin() {
        return admin;
    }

    public final void setAdmin(boolean admin) {
        admin = prepareAdmin(admin);
        if (isAdmin(admin)) return;
        this.admin = admin;
        updateLastModified();
        fireModified("admin="+admin);
    }

    protected boolean prepareAdmin(boolean admin) {
        return admin;
    }

    public final boolean isAdmin(boolean admin) {
        return this.admin == admin;
    }

    protected final void updateAdmin(Object value) {
        setAdmin((Boolean)value);
    }

    // -----------------------------------------------------------
    // - emailVerified
    // -----------------------------------------------------------

    private boolean emailVerified;

    public final boolean isEmailVerified() {
        return emailVerified;
    }

    public final void setEmailVerified(boolean emailVerified) {
        emailVerified = prepareEmailVerified(emailVerified);
        if (isEmailVerified(emailVerified)) return;
        this.emailVerified = emailVerified;
        updateLastModified();
        fireModified("emailVerified="+emailVerified);
    }

    protected boolean prepareEmailVerified(boolean emailVerified) {
        return emailVerified;
    }

    public final boolean isEmailVerified(boolean emailVerified) {
        return this.emailVerified == emailVerified;
    }

    protected final void updateEmailVerified(Object value) {
        setEmailVerified((Boolean)value);
    }

    // -----------------------------------------------------------
    // - email
    // -----------------------------------------------------------

    private java.lang.String email;

    public final java.lang.String getEmail() {
        return email;
    }

    public final void setEmail(java.lang.String email) {
        email = prepareEmail(email);
        if (isEmail(email)) return;
        if (email != null && getDao().getUserByEmail(email) != null) throw new ilarkesto.persistence.UniqueFieldConstraintException(this, "email", email);
        this.email = email;
        updateLastModified();
        fireModified("email="+email);
    }

    protected java.lang.String prepareEmail(java.lang.String email) {
        // email = StrExtend.removeUnreadableChars(email);
        return email;
    }

    public final boolean isEmailSet() {
        return this.email != null;
    }

    public final boolean isEmail(java.lang.String email) {
        if (this.email == null && email == null) return true;
        return this.email != null && this.email.equals(email);
    }

    protected final void updateEmail(Object value) {
        setEmail((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - currentProject
    // -----------------------------------------------------------

    private String currentProjectId;
    private transient scrum.server.project.Project currentProjectCache;

    private void updateCurrentProjectCache() {
        currentProjectCache = this.currentProjectId == null ? null : (scrum.server.project.Project)projectDao.getById(this.currentProjectId);
    }

    public final String getCurrentProjectId() {
        return this.currentProjectId;
    }

    public final scrum.server.project.Project getCurrentProject() {
        if (currentProjectCache == null) updateCurrentProjectCache();
        return currentProjectCache;
    }

    public final void setCurrentProject(scrum.server.project.Project currentProject) {
        currentProject = prepareCurrentProject(currentProject);
        if (isCurrentProject(currentProject)) return;
        this.currentProjectId = currentProject == null ? null : currentProject.getId();
        currentProjectCache = currentProject;
        updateLastModified();
        fireModified("currentProject="+currentProject);
    }

    protected scrum.server.project.Project prepareCurrentProject(scrum.server.project.Project currentProject) {
        return currentProject;
    }

    protected void repairDeadCurrentProjectReference(String entityId) {
        if (this.currentProjectId == null || entityId.equals(this.currentProjectId)) {
            setCurrentProject(null);
        }
    }

    public final boolean isCurrentProjectSet() {
        return this.currentProjectId != null;
    }

    public final boolean isCurrentProject(scrum.server.project.Project currentProject) {
        if (this.currentProjectId == null && currentProject == null) return true;
        return currentProject != null && currentProject.getId().equals(this.currentProjectId);
    }

    protected final void updateCurrentProject(Object value) {
        setCurrentProject(value == null ? null : (scrum.server.project.Project)projectDao.getById((String)value));
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
    // - lastLoginDateAndTime
    // -----------------------------------------------------------

    private ilarkesto.core.time.DateAndTime lastLoginDateAndTime;

    public final ilarkesto.core.time.DateAndTime getLastLoginDateAndTime() {
        return lastLoginDateAndTime;
    }

    public final void setLastLoginDateAndTime(ilarkesto.core.time.DateAndTime lastLoginDateAndTime) {
        lastLoginDateAndTime = prepareLastLoginDateAndTime(lastLoginDateAndTime);
        if (isLastLoginDateAndTime(lastLoginDateAndTime)) return;
        this.lastLoginDateAndTime = lastLoginDateAndTime;
        updateLastModified();
        fireModified("lastLoginDateAndTime="+lastLoginDateAndTime);
    }

    protected ilarkesto.core.time.DateAndTime prepareLastLoginDateAndTime(ilarkesto.core.time.DateAndTime lastLoginDateAndTime) {
        return lastLoginDateAndTime;
    }

    public final boolean isLastLoginDateAndTimeSet() {
        return this.lastLoginDateAndTime != null;
    }

    public final boolean isLastLoginDateAndTime(ilarkesto.core.time.DateAndTime lastLoginDateAndTime) {
        if (this.lastLoginDateAndTime == null && lastLoginDateAndTime == null) return true;
        return this.lastLoginDateAndTime != null && this.lastLoginDateAndTime.equals(lastLoginDateAndTime);
    }

    protected final void updateLastLoginDateAndTime(Object value) {
        value = value == null ? null : new ilarkesto.core.time.DateAndTime((String)value);
        setLastLoginDateAndTime((ilarkesto.core.time.DateAndTime)value);
    }

    // -----------------------------------------------------------
    // - registrationDateAndTime
    // -----------------------------------------------------------

    private ilarkesto.core.time.DateAndTime registrationDateAndTime;

    public final ilarkesto.core.time.DateAndTime getRegistrationDateAndTime() {
        return registrationDateAndTime;
    }

    public final void setRegistrationDateAndTime(ilarkesto.core.time.DateAndTime registrationDateAndTime) {
        registrationDateAndTime = prepareRegistrationDateAndTime(registrationDateAndTime);
        if (isRegistrationDateAndTime(registrationDateAndTime)) return;
        this.registrationDateAndTime = registrationDateAndTime;
        updateLastModified();
        fireModified("registrationDateAndTime="+registrationDateAndTime);
    }

    protected ilarkesto.core.time.DateAndTime prepareRegistrationDateAndTime(ilarkesto.core.time.DateAndTime registrationDateAndTime) {
        return registrationDateAndTime;
    }

    public final boolean isRegistrationDateAndTimeSet() {
        return this.registrationDateAndTime != null;
    }

    public final boolean isRegistrationDateAndTime(ilarkesto.core.time.DateAndTime registrationDateAndTime) {
        if (this.registrationDateAndTime == null && registrationDateAndTime == null) return true;
        return this.registrationDateAndTime != null && this.registrationDateAndTime.equals(registrationDateAndTime);
    }

    protected final void updateRegistrationDateAndTime(Object value) {
        value = value == null ? null : new ilarkesto.core.time.DateAndTime((String)value);
        setRegistrationDateAndTime((ilarkesto.core.time.DateAndTime)value);
    }

    // -----------------------------------------------------------
    // - disabled
    // -----------------------------------------------------------

    private boolean disabled;

    public final boolean isDisabled() {
        return disabled;
    }

    public final void setDisabled(boolean disabled) {
        disabled = prepareDisabled(disabled);
        if (isDisabled(disabled)) return;
        this.disabled = disabled;
        updateLastModified();
        fireModified("disabled="+disabled);
    }

    protected boolean prepareDisabled(boolean disabled) {
        return disabled;
    }

    public final boolean isDisabled(boolean disabled) {
        return this.disabled == disabled;
    }

    protected final void updateDisabled(Object value) {
        setDisabled((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideBlog
    // -----------------------------------------------------------

    private boolean hideUserGuideBlog;

    public final boolean isHideUserGuideBlog() {
        return hideUserGuideBlog;
    }

    public final void setHideUserGuideBlog(boolean hideUserGuideBlog) {
        hideUserGuideBlog = prepareHideUserGuideBlog(hideUserGuideBlog);
        if (isHideUserGuideBlog(hideUserGuideBlog)) return;
        this.hideUserGuideBlog = hideUserGuideBlog;
        updateLastModified();
        fireModified("hideUserGuideBlog="+hideUserGuideBlog);
    }

    protected boolean prepareHideUserGuideBlog(boolean hideUserGuideBlog) {
        return hideUserGuideBlog;
    }

    public final boolean isHideUserGuideBlog(boolean hideUserGuideBlog) {
        return this.hideUserGuideBlog == hideUserGuideBlog;
    }

    protected final void updateHideUserGuideBlog(Object value) {
        setHideUserGuideBlog((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideCalendar
    // -----------------------------------------------------------

    private boolean hideUserGuideCalendar;

    public final boolean isHideUserGuideCalendar() {
        return hideUserGuideCalendar;
    }

    public final void setHideUserGuideCalendar(boolean hideUserGuideCalendar) {
        hideUserGuideCalendar = prepareHideUserGuideCalendar(hideUserGuideCalendar);
        if (isHideUserGuideCalendar(hideUserGuideCalendar)) return;
        this.hideUserGuideCalendar = hideUserGuideCalendar;
        updateLastModified();
        fireModified("hideUserGuideCalendar="+hideUserGuideCalendar);
    }

    protected boolean prepareHideUserGuideCalendar(boolean hideUserGuideCalendar) {
        return hideUserGuideCalendar;
    }

    public final boolean isHideUserGuideCalendar(boolean hideUserGuideCalendar) {
        return this.hideUserGuideCalendar == hideUserGuideCalendar;
    }

    protected final void updateHideUserGuideCalendar(Object value) {
        setHideUserGuideCalendar((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideFiles
    // -----------------------------------------------------------

    private boolean hideUserGuideFiles;

    public final boolean isHideUserGuideFiles() {
        return hideUserGuideFiles;
    }

    public final void setHideUserGuideFiles(boolean hideUserGuideFiles) {
        hideUserGuideFiles = prepareHideUserGuideFiles(hideUserGuideFiles);
        if (isHideUserGuideFiles(hideUserGuideFiles)) return;
        this.hideUserGuideFiles = hideUserGuideFiles;
        updateLastModified();
        fireModified("hideUserGuideFiles="+hideUserGuideFiles);
    }

    protected boolean prepareHideUserGuideFiles(boolean hideUserGuideFiles) {
        return hideUserGuideFiles;
    }

    public final boolean isHideUserGuideFiles(boolean hideUserGuideFiles) {
        return this.hideUserGuideFiles == hideUserGuideFiles;
    }

    protected final void updateHideUserGuideFiles(Object value) {
        setHideUserGuideFiles((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideForum
    // -----------------------------------------------------------

    private boolean hideUserGuideForum;

    public final boolean isHideUserGuideForum() {
        return hideUserGuideForum;
    }

    public final void setHideUserGuideForum(boolean hideUserGuideForum) {
        hideUserGuideForum = prepareHideUserGuideForum(hideUserGuideForum);
        if (isHideUserGuideForum(hideUserGuideForum)) return;
        this.hideUserGuideForum = hideUserGuideForum;
        updateLastModified();
        fireModified("hideUserGuideForum="+hideUserGuideForum);
    }

    protected boolean prepareHideUserGuideForum(boolean hideUserGuideForum) {
        return hideUserGuideForum;
    }

    public final boolean isHideUserGuideForum(boolean hideUserGuideForum) {
        return this.hideUserGuideForum == hideUserGuideForum;
    }

    protected final void updateHideUserGuideForum(Object value) {
        setHideUserGuideForum((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideImpediments
    // -----------------------------------------------------------

    private boolean hideUserGuideImpediments;

    public final boolean isHideUserGuideImpediments() {
        return hideUserGuideImpediments;
    }

    public final void setHideUserGuideImpediments(boolean hideUserGuideImpediments) {
        hideUserGuideImpediments = prepareHideUserGuideImpediments(hideUserGuideImpediments);
        if (isHideUserGuideImpediments(hideUserGuideImpediments)) return;
        this.hideUserGuideImpediments = hideUserGuideImpediments;
        updateLastModified();
        fireModified("hideUserGuideImpediments="+hideUserGuideImpediments);
    }

    protected boolean prepareHideUserGuideImpediments(boolean hideUserGuideImpediments) {
        return hideUserGuideImpediments;
    }

    public final boolean isHideUserGuideImpediments(boolean hideUserGuideImpediments) {
        return this.hideUserGuideImpediments == hideUserGuideImpediments;
    }

    protected final void updateHideUserGuideImpediments(Object value) {
        setHideUserGuideImpediments((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideIssues
    // -----------------------------------------------------------

    private boolean hideUserGuideIssues;

    public final boolean isHideUserGuideIssues() {
        return hideUserGuideIssues;
    }

    public final void setHideUserGuideIssues(boolean hideUserGuideIssues) {
        hideUserGuideIssues = prepareHideUserGuideIssues(hideUserGuideIssues);
        if (isHideUserGuideIssues(hideUserGuideIssues)) return;
        this.hideUserGuideIssues = hideUserGuideIssues;
        updateLastModified();
        fireModified("hideUserGuideIssues="+hideUserGuideIssues);
    }

    protected boolean prepareHideUserGuideIssues(boolean hideUserGuideIssues) {
        return hideUserGuideIssues;
    }

    public final boolean isHideUserGuideIssues(boolean hideUserGuideIssues) {
        return this.hideUserGuideIssues == hideUserGuideIssues;
    }

    protected final void updateHideUserGuideIssues(Object value) {
        setHideUserGuideIssues((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideJournal
    // -----------------------------------------------------------

    private boolean hideUserGuideJournal;

    public final boolean isHideUserGuideJournal() {
        return hideUserGuideJournal;
    }

    public final void setHideUserGuideJournal(boolean hideUserGuideJournal) {
        hideUserGuideJournal = prepareHideUserGuideJournal(hideUserGuideJournal);
        if (isHideUserGuideJournal(hideUserGuideJournal)) return;
        this.hideUserGuideJournal = hideUserGuideJournal;
        updateLastModified();
        fireModified("hideUserGuideJournal="+hideUserGuideJournal);
    }

    protected boolean prepareHideUserGuideJournal(boolean hideUserGuideJournal) {
        return hideUserGuideJournal;
    }

    public final boolean isHideUserGuideJournal(boolean hideUserGuideJournal) {
        return this.hideUserGuideJournal == hideUserGuideJournal;
    }

    protected final void updateHideUserGuideJournal(Object value) {
        setHideUserGuideJournal((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideNextSprint
    // -----------------------------------------------------------

    private boolean hideUserGuideNextSprint;

    public final boolean isHideUserGuideNextSprint() {
        return hideUserGuideNextSprint;
    }

    public final void setHideUserGuideNextSprint(boolean hideUserGuideNextSprint) {
        hideUserGuideNextSprint = prepareHideUserGuideNextSprint(hideUserGuideNextSprint);
        if (isHideUserGuideNextSprint(hideUserGuideNextSprint)) return;
        this.hideUserGuideNextSprint = hideUserGuideNextSprint;
        updateLastModified();
        fireModified("hideUserGuideNextSprint="+hideUserGuideNextSprint);
    }

    protected boolean prepareHideUserGuideNextSprint(boolean hideUserGuideNextSprint) {
        return hideUserGuideNextSprint;
    }

    public final boolean isHideUserGuideNextSprint(boolean hideUserGuideNextSprint) {
        return this.hideUserGuideNextSprint == hideUserGuideNextSprint;
    }

    protected final void updateHideUserGuideNextSprint(Object value) {
        setHideUserGuideNextSprint((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideProductBacklog
    // -----------------------------------------------------------

    private boolean hideUserGuideProductBacklog;

    public final boolean isHideUserGuideProductBacklog() {
        return hideUserGuideProductBacklog;
    }

    public final void setHideUserGuideProductBacklog(boolean hideUserGuideProductBacklog) {
        hideUserGuideProductBacklog = prepareHideUserGuideProductBacklog(hideUserGuideProductBacklog);
        if (isHideUserGuideProductBacklog(hideUserGuideProductBacklog)) return;
        this.hideUserGuideProductBacklog = hideUserGuideProductBacklog;
        updateLastModified();
        fireModified("hideUserGuideProductBacklog="+hideUserGuideProductBacklog);
    }

    protected boolean prepareHideUserGuideProductBacklog(boolean hideUserGuideProductBacklog) {
        return hideUserGuideProductBacklog;
    }

    public final boolean isHideUserGuideProductBacklog(boolean hideUserGuideProductBacklog) {
        return this.hideUserGuideProductBacklog == hideUserGuideProductBacklog;
    }

    protected final void updateHideUserGuideProductBacklog(Object value) {
        setHideUserGuideProductBacklog((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideCourtroom
    // -----------------------------------------------------------

    private boolean hideUserGuideCourtroom;

    public final boolean isHideUserGuideCourtroom() {
        return hideUserGuideCourtroom;
    }

    public final void setHideUserGuideCourtroom(boolean hideUserGuideCourtroom) {
        hideUserGuideCourtroom = prepareHideUserGuideCourtroom(hideUserGuideCourtroom);
        if (isHideUserGuideCourtroom(hideUserGuideCourtroom)) return;
        this.hideUserGuideCourtroom = hideUserGuideCourtroom;
        updateLastModified();
        fireModified("hideUserGuideCourtroom="+hideUserGuideCourtroom);
    }

    protected boolean prepareHideUserGuideCourtroom(boolean hideUserGuideCourtroom) {
        return hideUserGuideCourtroom;
    }

    public final boolean isHideUserGuideCourtroom(boolean hideUserGuideCourtroom) {
        return this.hideUserGuideCourtroom == hideUserGuideCourtroom;
    }

    protected final void updateHideUserGuideCourtroom(Object value) {
        setHideUserGuideCourtroom((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideQualityBacklog
    // -----------------------------------------------------------

    private boolean hideUserGuideQualityBacklog;

    public final boolean isHideUserGuideQualityBacklog() {
        return hideUserGuideQualityBacklog;
    }

    public final void setHideUserGuideQualityBacklog(boolean hideUserGuideQualityBacklog) {
        hideUserGuideQualityBacklog = prepareHideUserGuideQualityBacklog(hideUserGuideQualityBacklog);
        if (isHideUserGuideQualityBacklog(hideUserGuideQualityBacklog)) return;
        this.hideUserGuideQualityBacklog = hideUserGuideQualityBacklog;
        updateLastModified();
        fireModified("hideUserGuideQualityBacklog="+hideUserGuideQualityBacklog);
    }

    protected boolean prepareHideUserGuideQualityBacklog(boolean hideUserGuideQualityBacklog) {
        return hideUserGuideQualityBacklog;
    }

    public final boolean isHideUserGuideQualityBacklog(boolean hideUserGuideQualityBacklog) {
        return this.hideUserGuideQualityBacklog == hideUserGuideQualityBacklog;
    }

    protected final void updateHideUserGuideQualityBacklog(Object value) {
        setHideUserGuideQualityBacklog((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideReleases
    // -----------------------------------------------------------

    private boolean hideUserGuideReleases;

    public final boolean isHideUserGuideReleases() {
        return hideUserGuideReleases;
    }

    public final void setHideUserGuideReleases(boolean hideUserGuideReleases) {
        hideUserGuideReleases = prepareHideUserGuideReleases(hideUserGuideReleases);
        if (isHideUserGuideReleases(hideUserGuideReleases)) return;
        this.hideUserGuideReleases = hideUserGuideReleases;
        updateLastModified();
        fireModified("hideUserGuideReleases="+hideUserGuideReleases);
    }

    protected boolean prepareHideUserGuideReleases(boolean hideUserGuideReleases) {
        return hideUserGuideReleases;
    }

    public final boolean isHideUserGuideReleases(boolean hideUserGuideReleases) {
        return this.hideUserGuideReleases == hideUserGuideReleases;
    }

    protected final void updateHideUserGuideReleases(Object value) {
        setHideUserGuideReleases((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideRisks
    // -----------------------------------------------------------

    private boolean hideUserGuideRisks;

    public final boolean isHideUserGuideRisks() {
        return hideUserGuideRisks;
    }

    public final void setHideUserGuideRisks(boolean hideUserGuideRisks) {
        hideUserGuideRisks = prepareHideUserGuideRisks(hideUserGuideRisks);
        if (isHideUserGuideRisks(hideUserGuideRisks)) return;
        this.hideUserGuideRisks = hideUserGuideRisks;
        updateLastModified();
        fireModified("hideUserGuideRisks="+hideUserGuideRisks);
    }

    protected boolean prepareHideUserGuideRisks(boolean hideUserGuideRisks) {
        return hideUserGuideRisks;
    }

    public final boolean isHideUserGuideRisks(boolean hideUserGuideRisks) {
        return this.hideUserGuideRisks == hideUserGuideRisks;
    }

    protected final void updateHideUserGuideRisks(Object value) {
        setHideUserGuideRisks((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideSprintBacklog
    // -----------------------------------------------------------

    private boolean hideUserGuideSprintBacklog;

    public final boolean isHideUserGuideSprintBacklog() {
        return hideUserGuideSprintBacklog;
    }

    public final void setHideUserGuideSprintBacklog(boolean hideUserGuideSprintBacklog) {
        hideUserGuideSprintBacklog = prepareHideUserGuideSprintBacklog(hideUserGuideSprintBacklog);
        if (isHideUserGuideSprintBacklog(hideUserGuideSprintBacklog)) return;
        this.hideUserGuideSprintBacklog = hideUserGuideSprintBacklog;
        updateLastModified();
        fireModified("hideUserGuideSprintBacklog="+hideUserGuideSprintBacklog);
    }

    protected boolean prepareHideUserGuideSprintBacklog(boolean hideUserGuideSprintBacklog) {
        return hideUserGuideSprintBacklog;
    }

    public final boolean isHideUserGuideSprintBacklog(boolean hideUserGuideSprintBacklog) {
        return this.hideUserGuideSprintBacklog == hideUserGuideSprintBacklog;
    }

    protected final void updateHideUserGuideSprintBacklog(Object value) {
        setHideUserGuideSprintBacklog((Boolean)value);
    }

    // -----------------------------------------------------------
    // - hideUserGuideWhiteboard
    // -----------------------------------------------------------

    private boolean hideUserGuideWhiteboard;

    public final boolean isHideUserGuideWhiteboard() {
        return hideUserGuideWhiteboard;
    }

    public final void setHideUserGuideWhiteboard(boolean hideUserGuideWhiteboard) {
        hideUserGuideWhiteboard = prepareHideUserGuideWhiteboard(hideUserGuideWhiteboard);
        if (isHideUserGuideWhiteboard(hideUserGuideWhiteboard)) return;
        this.hideUserGuideWhiteboard = hideUserGuideWhiteboard;
        updateLastModified();
        fireModified("hideUserGuideWhiteboard="+hideUserGuideWhiteboard);
    }

    protected boolean prepareHideUserGuideWhiteboard(boolean hideUserGuideWhiteboard) {
        return hideUserGuideWhiteboard;
    }

    public final boolean isHideUserGuideWhiteboard(boolean hideUserGuideWhiteboard) {
        return this.hideUserGuideWhiteboard == hideUserGuideWhiteboard;
    }

    protected final void updateHideUserGuideWhiteboard(Object value) {
        setHideUserGuideWhiteboard((Boolean)value);
    }

    // -----------------------------------------------------------
    // - loginToken
    // -----------------------------------------------------------

    private java.lang.String loginToken;

    public final java.lang.String getLoginToken() {
        return loginToken;
    }

    public final void setLoginToken(java.lang.String loginToken) {
        loginToken = prepareLoginToken(loginToken);
        if (isLoginToken(loginToken)) return;
        if (loginToken != null && getDao().getUserByLoginToken(loginToken) != null) throw new ilarkesto.persistence.UniqueFieldConstraintException(this, "loginToken", loginToken);
        this.loginToken = loginToken;
        updateLastModified();
        fireModified("loginToken="+loginToken);
    }

    protected java.lang.String prepareLoginToken(java.lang.String loginToken) {
        // loginToken = StrExtend.removeUnreadableChars(loginToken);
        return loginToken;
    }

    public final boolean isLoginTokenSet() {
        return this.loginToken != null;
    }

    public final boolean isLoginToken(java.lang.String loginToken) {
        if (this.loginToken == null && loginToken == null) return true;
        return this.loginToken != null && this.loginToken.equals(loginToken);
    }

    protected final void updateLoginToken(Object value) {
        setLoginToken((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - openId
    // -----------------------------------------------------------

    private java.lang.String openId;

    public final java.lang.String getOpenId() {
        return openId;
    }

    public final void setOpenId(java.lang.String openId) {
        openId = prepareOpenId(openId);
        if (isOpenId(openId)) return;
        if (openId != null && getDao().getUserByOpenId(openId) != null) throw new ilarkesto.persistence.UniqueFieldConstraintException(this, "openId", openId);
        this.openId = openId;
        updateLastModified();
        fireModified("openId="+openId);
    }

    protected java.lang.String prepareOpenId(java.lang.String openId) {
        // openId = StrExtend.removeUnreadableChars(openId);
        return openId;
    }

    public final boolean isOpenIdSet() {
        return this.openId != null;
    }

    public final boolean isOpenId(java.lang.String openId) {
        if (this.openId == null && openId == null) return true;
        return this.openId != null && this.openId.equals(openId);
    }

    protected final void updateOpenId(Object value) {
        setOpenId((java.lang.String)value);
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("name")) updateName(value);
            if (property.equals("publicName")) updatePublicName(value);
            if (property.equals("fullName")) updateFullName(value);
            if (property.equals("admin")) updateAdmin(value);
            if (property.equals("emailVerified")) updateEmailVerified(value);
            if (property.equals("email")) updateEmail(value);
            if (property.equals("currentProjectId")) updateCurrentProject(value);
            if (property.equals("color")) updateColor(value);
            if (property.equals("lastLoginDateAndTime")) updateLastLoginDateAndTime(value);
            if (property.equals("registrationDateAndTime")) updateRegistrationDateAndTime(value);
            if (property.equals("disabled")) updateDisabled(value);
            if (property.equals("hideUserGuideBlog")) updateHideUserGuideBlog(value);
            if (property.equals("hideUserGuideCalendar")) updateHideUserGuideCalendar(value);
            if (property.equals("hideUserGuideFiles")) updateHideUserGuideFiles(value);
            if (property.equals("hideUserGuideForum")) updateHideUserGuideForum(value);
            if (property.equals("hideUserGuideImpediments")) updateHideUserGuideImpediments(value);
            if (property.equals("hideUserGuideIssues")) updateHideUserGuideIssues(value);
            if (property.equals("hideUserGuideJournal")) updateHideUserGuideJournal(value);
            if (property.equals("hideUserGuideNextSprint")) updateHideUserGuideNextSprint(value);
            if (property.equals("hideUserGuideProductBacklog")) updateHideUserGuideProductBacklog(value);
            if (property.equals("hideUserGuideCourtroom")) updateHideUserGuideCourtroom(value);
            if (property.equals("hideUserGuideQualityBacklog")) updateHideUserGuideQualityBacklog(value);
            if (property.equals("hideUserGuideReleases")) updateHideUserGuideReleases(value);
            if (property.equals("hideUserGuideRisks")) updateHideUserGuideRisks(value);
            if (property.equals("hideUserGuideSprintBacklog")) updateHideUserGuideSprintBacklog(value);
            if (property.equals("hideUserGuideWhiteboard")) updateHideUserGuideWhiteboard(value);
            if (property.equals("loginToken")) updateLoginToken(value);
            if (property.equals("openId")) updateOpenId(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadCurrentProjectReference(entityId);
    }

    // --- ensure integrity ---

    public void ensureIntegrity() {
        super.ensureIntegrity();
        try {
            getCurrentProject();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead currentProject reference");
            repairDeadCurrentProjectReference(this.currentProjectId);
        }
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    static scrum.server.project.ProjectDao projectDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectDao(scrum.server.project.ProjectDao projectDao) {
        GUser.projectDao = projectDao;
    }

    static scrum.server.admin.UserDao userDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setUserDao(scrum.server.admin.UserDao userDao) {
        GUser.userDao = userDao;
    }

    static scrum.server.sprint.SprintDao sprintDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSprintDao(scrum.server.sprint.SprintDao sprintDao) {
        GUser.sprintDao = sprintDao;
    }

    static scrum.server.collaboration.EmoticonDao emoticonDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setEmoticonDao(scrum.server.collaboration.EmoticonDao emoticonDao) {
        GUser.emoticonDao = emoticonDao;
    }

    static scrum.server.admin.ProjectUserConfigDao projectUserConfigDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setProjectUserConfigDao(scrum.server.admin.ProjectUserConfigDao projectUserConfigDao) {
        GUser.projectUserConfigDao = projectUserConfigDao;
    }

    static scrum.server.issues.IssueDao issueDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setIssueDao(scrum.server.issues.IssueDao issueDao) {
        GUser.issueDao = issueDao;
    }

    static scrum.server.sprint.TaskDao taskDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setTaskDao(scrum.server.sprint.TaskDao taskDao) {
        GUser.taskDao = taskDao;
    }

    static scrum.server.journal.ChangeDao changeDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setChangeDao(scrum.server.journal.ChangeDao changeDao) {
        GUser.changeDao = changeDao;
    }

    static scrum.server.collaboration.CommentDao commentDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setCommentDao(scrum.server.collaboration.CommentDao commentDao) {
        GUser.commentDao = commentDao;
    }

    static scrum.server.collaboration.ChatMessageDao chatMessageDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setChatMessageDao(scrum.server.collaboration.ChatMessageDao chatMessageDao) {
        GUser.chatMessageDao = chatMessageDao;
    }

    static scrum.server.pr.BlogEntryDao blogEntryDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setBlogEntryDao(scrum.server.pr.BlogEntryDao blogEntryDao) {
        GUser.blogEntryDao = blogEntryDao;
    }

    static scrum.server.estimation.RequirementEstimationVoteDao requirementEstimationVoteDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setRequirementEstimationVoteDao(scrum.server.estimation.RequirementEstimationVoteDao requirementEstimationVoteDao) {
        GUser.requirementEstimationVoteDao = requirementEstimationVoteDao;
    }

}