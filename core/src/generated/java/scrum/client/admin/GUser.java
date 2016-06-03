// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.admin;

import java.util.*;
import ilarkesto.core.logging.Log;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GUser
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public GUser() {
    }

    public GUser(Map data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "user";

    @Override
    public final String getEntityType() {
        return ENTITY_TYPE;
    }

    // --- name ---

    private java.lang.String name ;

    public final java.lang.String getName() {
        return this.name ;
    }

    public final User setName(java.lang.String name) {
        if (isName(name)) return (User)this;
        if (ilarkesto.core.base.Str.isBlank(name)) throw new RuntimeException("Field is mandatory.");
        if (name != null && getDao().getUserByName(name) != null) throw new RuntimeException("\"" + name + "\" already exists.");
        this.name = name ;
        propertyChanged("name", this.name);
        return (User)this;
    }

    public final boolean isName(java.lang.String name) {
        return equals(this.name, name);
    }

    private transient NameModel nameModel;

    public NameModel getNameModel() {
        if (nameModel == null) nameModel = createNameModel();
        return nameModel;
    }

    protected NameModel createNameModel() { return new NameModel(); }

    protected class NameModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "User_name";
        }

        @Override
        public java.lang.String getValue() {
            return getName();
        }

        @Override
        public void setValue(java.lang.String value) {
            setName(value);
        }

        @Override
        public boolean isMandatory() { return true; }
        @Override
        public String getTooltip() { return "Login name."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- publicName ---

    private java.lang.String publicName ;

    public final java.lang.String getPublicName() {
        return this.publicName ;
    }

    public final User setPublicName(java.lang.String publicName) {
        if (isPublicName(publicName)) return (User)this;
        this.publicName = publicName ;
        propertyChanged("publicName", this.publicName);
        return (User)this;
    }

    public final boolean isPublicName(java.lang.String publicName) {
        return equals(this.publicName, publicName);
    }

    private transient PublicNameModel publicNameModel;

    public PublicNameModel getPublicNameModel() {
        if (publicNameModel == null) publicNameModel = createPublicNameModel();
        return publicNameModel;
    }

    protected PublicNameModel createPublicNameModel() { return new PublicNameModel(); }

    protected class PublicNameModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "User_publicName";
        }

        @Override
        public java.lang.String getValue() {
            return getPublicName();
        }

        @Override
        public void setValue(java.lang.String value) {
            setPublicName(value);
        }
        @Override
        public String getTooltip() { return "Name, which is displayed to the public on blog entries or emails."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- fullName ---

    private java.lang.String fullName ;

    public final java.lang.String getFullName() {
        return this.fullName ;
    }

    public final User setFullName(java.lang.String fullName) {
        if (isFullName(fullName)) return (User)this;
        this.fullName = fullName ;
        propertyChanged("fullName", this.fullName);
        return (User)this;
    }

    public final boolean isFullName(java.lang.String fullName) {
        return equals(this.fullName, fullName);
    }

    private transient FullNameModel fullNameModel;

    public FullNameModel getFullNameModel() {
        if (fullNameModel == null) fullNameModel = createFullNameModel();
        return fullNameModel;
    }

    protected FullNameModel createFullNameModel() { return new FullNameModel(); }

    protected class FullNameModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "User_fullName";
        }

        @Override
        public java.lang.String getValue() {
            return getFullName();
        }

        @Override
        public void setValue(java.lang.String value) {
            setFullName(value);
        }
        @Override
        public String getTooltip() { return "Full name of the person."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- admin ---

    private boolean admin ;

    public final boolean isAdmin() {
        return this.admin ;
    }

    public final User setAdmin(boolean admin) {
        if (isAdmin(admin)) return (User)this;
        this.admin = admin ;
        propertyChanged("admin", this.admin);
        return (User)this;
    }

    public final boolean isAdmin(boolean admin) {
        return equals(this.admin, admin);
    }

    private transient AdminModel adminModel;

    public AdminModel getAdminModel() {
        if (adminModel == null) adminModel = createAdminModel();
        return adminModel;
    }

    protected AdminModel createAdminModel() { return new AdminModel(); }

    protected class AdminModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_admin";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isAdmin();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setAdmin(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- emailVerified ---

    private boolean emailVerified ;

    public final boolean isEmailVerified() {
        return this.emailVerified ;
    }

    public final User setEmailVerified(boolean emailVerified) {
        if (isEmailVerified(emailVerified)) return (User)this;
        this.emailVerified = emailVerified ;
        propertyChanged("emailVerified", this.emailVerified);
        return (User)this;
    }

    public final boolean isEmailVerified(boolean emailVerified) {
        return equals(this.emailVerified, emailVerified);
    }

    private transient EmailVerifiedModel emailVerifiedModel;

    public EmailVerifiedModel getEmailVerifiedModel() {
        if (emailVerifiedModel == null) emailVerifiedModel = createEmailVerifiedModel();
        return emailVerifiedModel;
    }

    protected EmailVerifiedModel createEmailVerifiedModel() { return new EmailVerifiedModel(); }

    protected class EmailVerifiedModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_emailVerified";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isEmailVerified();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setEmailVerified(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- email ---

    private java.lang.String email ;

    public final java.lang.String getEmail() {
        return this.email ;
    }

    public final User setEmail(java.lang.String email) {
        if (isEmail(email)) return (User)this;
        if (email != null && getDao().getUserByEmail(email) != null) throw new RuntimeException("\"" + email + "\" already exists.");
        this.email = email ;
        propertyChanged("email", this.email);
        return (User)this;
    }

    public final boolean isEmail(java.lang.String email) {
        return equals(this.email, email);
    }

    private transient EmailModel emailModel;

    public EmailModel getEmailModel() {
        if (emailModel == null) emailModel = createEmailModel();
        return emailModel;
    }

    protected EmailModel createEmailModel() { return new EmailModel(); }

    protected class EmailModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "User_email";
        }

        @Override
        public java.lang.String getValue() {
            return getEmail();
        }

        @Override
        public void setValue(java.lang.String value) {
            setEmail(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- currentProject ---

    private String currentProjectId;

    public final scrum.client.project.Project getCurrentProject() {
        if (currentProjectId == null) return null;
        return getDao().getProject(this.currentProjectId);
    }

    public final boolean isCurrentProjectSet() {
        return currentProjectId != null;
    }

    public final User setCurrentProject(scrum.client.project.Project currentProject) {
        String id = currentProject == null ? null : currentProject.getId();
        if (equals(this.currentProjectId, id)) return (User) this;
        this.currentProjectId = id;
        propertyChanged("currentProjectId", this.currentProjectId);
        return (User)this;
    }

    public final boolean isCurrentProject(scrum.client.project.Project currentProject) {
        return equals(this.currentProjectId, currentProject);
    }

    // --- color ---

    private java.lang.String color ;

    public final java.lang.String getColor() {
        return this.color ;
    }

    public final User setColor(java.lang.String color) {
        if (isColor(color)) return (User)this;
        this.color = color ;
        propertyChanged("color", this.color);
        return (User)this;
    }

    public final boolean isColor(java.lang.String color) {
        return equals(this.color, color);
    }

    private transient ColorModel colorModel;

    public ColorModel getColorModel() {
        if (colorModel == null) colorModel = createColorModel();
        return colorModel;
    }

    protected ColorModel createColorModel() { return new ColorModel(); }

    protected class ColorModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "User_color";
        }

        @Override
        public java.lang.String getValue() {
            return getColor();
        }

        @Override
        public void setValue(java.lang.String value) {
            setColor(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- lastLoginDateAndTime ---

    private ilarkesto.core.time.DateAndTime lastLoginDateAndTime ;

    public final ilarkesto.core.time.DateAndTime getLastLoginDateAndTime() {
        return this.lastLoginDateAndTime ;
    }

    public final User setLastLoginDateAndTime(ilarkesto.core.time.DateAndTime lastLoginDateAndTime) {
        if (isLastLoginDateAndTime(lastLoginDateAndTime)) return (User)this;
        this.lastLoginDateAndTime = lastLoginDateAndTime ;
        propertyChanged("lastLoginDateAndTime", this.lastLoginDateAndTime);
        return (User)this;
    }

    public final boolean isLastLoginDateAndTime(ilarkesto.core.time.DateAndTime lastLoginDateAndTime) {
        return equals(this.lastLoginDateAndTime, lastLoginDateAndTime);
    }

    private transient LastLoginDateAndTimeModel lastLoginDateAndTimeModel;

    public LastLoginDateAndTimeModel getLastLoginDateAndTimeModel() {
        if (lastLoginDateAndTimeModel == null) lastLoginDateAndTimeModel = createLastLoginDateAndTimeModel();
        return lastLoginDateAndTimeModel;
    }

    protected LastLoginDateAndTimeModel createLastLoginDateAndTimeModel() { return new LastLoginDateAndTimeModel(); }

    protected class LastLoginDateAndTimeModel extends ilarkesto.gwt.client.editor.ADateAndTimeEditorModel {

        @Override
        public String getId() {
            return "User_lastLoginDateAndTime";
        }

        @Override
        public ilarkesto.core.time.DateAndTime getValue() {
            return getLastLoginDateAndTime();
        }

        @Override
        public void setValue(ilarkesto.core.time.DateAndTime value) {
            setLastLoginDateAndTime(value);
        }

        @Override
        protected void onChangeValue(ilarkesto.core.time.DateAndTime oldValue, ilarkesto.core.time.DateAndTime newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- registrationDateAndTime ---

    private ilarkesto.core.time.DateAndTime registrationDateAndTime ;

    public final ilarkesto.core.time.DateAndTime getRegistrationDateAndTime() {
        return this.registrationDateAndTime ;
    }

    public final User setRegistrationDateAndTime(ilarkesto.core.time.DateAndTime registrationDateAndTime) {
        if (isRegistrationDateAndTime(registrationDateAndTime)) return (User)this;
        this.registrationDateAndTime = registrationDateAndTime ;
        propertyChanged("registrationDateAndTime", this.registrationDateAndTime);
        return (User)this;
    }

    public final boolean isRegistrationDateAndTime(ilarkesto.core.time.DateAndTime registrationDateAndTime) {
        return equals(this.registrationDateAndTime, registrationDateAndTime);
    }

    private transient RegistrationDateAndTimeModel registrationDateAndTimeModel;

    public RegistrationDateAndTimeModel getRegistrationDateAndTimeModel() {
        if (registrationDateAndTimeModel == null) registrationDateAndTimeModel = createRegistrationDateAndTimeModel();
        return registrationDateAndTimeModel;
    }

    protected RegistrationDateAndTimeModel createRegistrationDateAndTimeModel() { return new RegistrationDateAndTimeModel(); }

    protected class RegistrationDateAndTimeModel extends ilarkesto.gwt.client.editor.ADateAndTimeEditorModel {

        @Override
        public String getId() {
            return "User_registrationDateAndTime";
        }

        @Override
        public ilarkesto.core.time.DateAndTime getValue() {
            return getRegistrationDateAndTime();
        }

        @Override
        public void setValue(ilarkesto.core.time.DateAndTime value) {
            setRegistrationDateAndTime(value);
        }

        @Override
        protected void onChangeValue(ilarkesto.core.time.DateAndTime oldValue, ilarkesto.core.time.DateAndTime newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- disabled ---

    private boolean disabled ;

    public final boolean isDisabled() {
        return this.disabled ;
    }

    public final User setDisabled(boolean disabled) {
        if (isDisabled(disabled)) return (User)this;
        this.disabled = disabled ;
        propertyChanged("disabled", this.disabled);
        return (User)this;
    }

    public final boolean isDisabled(boolean disabled) {
        return equals(this.disabled, disabled);
    }

    private transient DisabledModel disabledModel;

    public DisabledModel getDisabledModel() {
        if (disabledModel == null) disabledModel = createDisabledModel();
        return disabledModel;
    }

    protected DisabledModel createDisabledModel() { return new DisabledModel(); }

    protected class DisabledModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_disabled";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isDisabled();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setDisabled(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideBlog ---

    private boolean hideUserGuideBlog ;

    public final boolean isHideUserGuideBlog() {
        return this.hideUserGuideBlog ;
    }

    public final User setHideUserGuideBlog(boolean hideUserGuideBlog) {
        if (isHideUserGuideBlog(hideUserGuideBlog)) return (User)this;
        this.hideUserGuideBlog = hideUserGuideBlog ;
        propertyChanged("hideUserGuideBlog", this.hideUserGuideBlog);
        return (User)this;
    }

    public final boolean isHideUserGuideBlog(boolean hideUserGuideBlog) {
        return equals(this.hideUserGuideBlog, hideUserGuideBlog);
    }

    private transient HideUserGuideBlogModel hideUserGuideBlogModel;

    public HideUserGuideBlogModel getHideUserGuideBlogModel() {
        if (hideUserGuideBlogModel == null) hideUserGuideBlogModel = createHideUserGuideBlogModel();
        return hideUserGuideBlogModel;
    }

    protected HideUserGuideBlogModel createHideUserGuideBlogModel() { return new HideUserGuideBlogModel(); }

    protected class HideUserGuideBlogModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideBlog";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideBlog();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideBlog(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideCalendar ---

    private boolean hideUserGuideCalendar ;

    public final boolean isHideUserGuideCalendar() {
        return this.hideUserGuideCalendar ;
    }

    public final User setHideUserGuideCalendar(boolean hideUserGuideCalendar) {
        if (isHideUserGuideCalendar(hideUserGuideCalendar)) return (User)this;
        this.hideUserGuideCalendar = hideUserGuideCalendar ;
        propertyChanged("hideUserGuideCalendar", this.hideUserGuideCalendar);
        return (User)this;
    }

    public final boolean isHideUserGuideCalendar(boolean hideUserGuideCalendar) {
        return equals(this.hideUserGuideCalendar, hideUserGuideCalendar);
    }

    private transient HideUserGuideCalendarModel hideUserGuideCalendarModel;

    public HideUserGuideCalendarModel getHideUserGuideCalendarModel() {
        if (hideUserGuideCalendarModel == null) hideUserGuideCalendarModel = createHideUserGuideCalendarModel();
        return hideUserGuideCalendarModel;
    }

    protected HideUserGuideCalendarModel createHideUserGuideCalendarModel() { return new HideUserGuideCalendarModel(); }

    protected class HideUserGuideCalendarModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideCalendar";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideCalendar();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideCalendar(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideFiles ---

    private boolean hideUserGuideFiles ;

    public final boolean isHideUserGuideFiles() {
        return this.hideUserGuideFiles ;
    }

    public final User setHideUserGuideFiles(boolean hideUserGuideFiles) {
        if (isHideUserGuideFiles(hideUserGuideFiles)) return (User)this;
        this.hideUserGuideFiles = hideUserGuideFiles ;
        propertyChanged("hideUserGuideFiles", this.hideUserGuideFiles);
        return (User)this;
    }

    public final boolean isHideUserGuideFiles(boolean hideUserGuideFiles) {
        return equals(this.hideUserGuideFiles, hideUserGuideFiles);
    }

    private transient HideUserGuideFilesModel hideUserGuideFilesModel;

    public HideUserGuideFilesModel getHideUserGuideFilesModel() {
        if (hideUserGuideFilesModel == null) hideUserGuideFilesModel = createHideUserGuideFilesModel();
        return hideUserGuideFilesModel;
    }

    protected HideUserGuideFilesModel createHideUserGuideFilesModel() { return new HideUserGuideFilesModel(); }

    protected class HideUserGuideFilesModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideFiles";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideFiles();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideFiles(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideForum ---

    private boolean hideUserGuideForum ;

    public final boolean isHideUserGuideForum() {
        return this.hideUserGuideForum ;
    }

    public final User setHideUserGuideForum(boolean hideUserGuideForum) {
        if (isHideUserGuideForum(hideUserGuideForum)) return (User)this;
        this.hideUserGuideForum = hideUserGuideForum ;
        propertyChanged("hideUserGuideForum", this.hideUserGuideForum);
        return (User)this;
    }

    public final boolean isHideUserGuideForum(boolean hideUserGuideForum) {
        return equals(this.hideUserGuideForum, hideUserGuideForum);
    }

    private transient HideUserGuideForumModel hideUserGuideForumModel;

    public HideUserGuideForumModel getHideUserGuideForumModel() {
        if (hideUserGuideForumModel == null) hideUserGuideForumModel = createHideUserGuideForumModel();
        return hideUserGuideForumModel;
    }

    protected HideUserGuideForumModel createHideUserGuideForumModel() { return new HideUserGuideForumModel(); }

    protected class HideUserGuideForumModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideForum";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideForum();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideForum(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideImpediments ---

    private boolean hideUserGuideImpediments ;

    public final boolean isHideUserGuideImpediments() {
        return this.hideUserGuideImpediments ;
    }

    public final User setHideUserGuideImpediments(boolean hideUserGuideImpediments) {
        if (isHideUserGuideImpediments(hideUserGuideImpediments)) return (User)this;
        this.hideUserGuideImpediments = hideUserGuideImpediments ;
        propertyChanged("hideUserGuideImpediments", this.hideUserGuideImpediments);
        return (User)this;
    }

    public final boolean isHideUserGuideImpediments(boolean hideUserGuideImpediments) {
        return equals(this.hideUserGuideImpediments, hideUserGuideImpediments);
    }

    private transient HideUserGuideImpedimentsModel hideUserGuideImpedimentsModel;

    public HideUserGuideImpedimentsModel getHideUserGuideImpedimentsModel() {
        if (hideUserGuideImpedimentsModel == null) hideUserGuideImpedimentsModel = createHideUserGuideImpedimentsModel();
        return hideUserGuideImpedimentsModel;
    }

    protected HideUserGuideImpedimentsModel createHideUserGuideImpedimentsModel() { return new HideUserGuideImpedimentsModel(); }

    protected class HideUserGuideImpedimentsModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideImpediments";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideImpediments();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideImpediments(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideIssues ---

    private boolean hideUserGuideIssues ;

    public final boolean isHideUserGuideIssues() {
        return this.hideUserGuideIssues ;
    }

    public final User setHideUserGuideIssues(boolean hideUserGuideIssues) {
        if (isHideUserGuideIssues(hideUserGuideIssues)) return (User)this;
        this.hideUserGuideIssues = hideUserGuideIssues ;
        propertyChanged("hideUserGuideIssues", this.hideUserGuideIssues);
        return (User)this;
    }

    public final boolean isHideUserGuideIssues(boolean hideUserGuideIssues) {
        return equals(this.hideUserGuideIssues, hideUserGuideIssues);
    }

    private transient HideUserGuideIssuesModel hideUserGuideIssuesModel;

    public HideUserGuideIssuesModel getHideUserGuideIssuesModel() {
        if (hideUserGuideIssuesModel == null) hideUserGuideIssuesModel = createHideUserGuideIssuesModel();
        return hideUserGuideIssuesModel;
    }

    protected HideUserGuideIssuesModel createHideUserGuideIssuesModel() { return new HideUserGuideIssuesModel(); }

    protected class HideUserGuideIssuesModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideIssues";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideIssues();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideIssues(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideJournal ---

    private boolean hideUserGuideJournal ;

    public final boolean isHideUserGuideJournal() {
        return this.hideUserGuideJournal ;
    }

    public final User setHideUserGuideJournal(boolean hideUserGuideJournal) {
        if (isHideUserGuideJournal(hideUserGuideJournal)) return (User)this;
        this.hideUserGuideJournal = hideUserGuideJournal ;
        propertyChanged("hideUserGuideJournal", this.hideUserGuideJournal);
        return (User)this;
    }

    public final boolean isHideUserGuideJournal(boolean hideUserGuideJournal) {
        return equals(this.hideUserGuideJournal, hideUserGuideJournal);
    }

    private transient HideUserGuideJournalModel hideUserGuideJournalModel;

    public HideUserGuideJournalModel getHideUserGuideJournalModel() {
        if (hideUserGuideJournalModel == null) hideUserGuideJournalModel = createHideUserGuideJournalModel();
        return hideUserGuideJournalModel;
    }

    protected HideUserGuideJournalModel createHideUserGuideJournalModel() { return new HideUserGuideJournalModel(); }

    protected class HideUserGuideJournalModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideJournal";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideJournal();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideJournal(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideNextSprint ---

    private boolean hideUserGuideNextSprint ;

    public final boolean isHideUserGuideNextSprint() {
        return this.hideUserGuideNextSprint ;
    }

    public final User setHideUserGuideNextSprint(boolean hideUserGuideNextSprint) {
        if (isHideUserGuideNextSprint(hideUserGuideNextSprint)) return (User)this;
        this.hideUserGuideNextSprint = hideUserGuideNextSprint ;
        propertyChanged("hideUserGuideNextSprint", this.hideUserGuideNextSprint);
        return (User)this;
    }

    public final boolean isHideUserGuideNextSprint(boolean hideUserGuideNextSprint) {
        return equals(this.hideUserGuideNextSprint, hideUserGuideNextSprint);
    }

    private transient HideUserGuideNextSprintModel hideUserGuideNextSprintModel;

    public HideUserGuideNextSprintModel getHideUserGuideNextSprintModel() {
        if (hideUserGuideNextSprintModel == null) hideUserGuideNextSprintModel = createHideUserGuideNextSprintModel();
        return hideUserGuideNextSprintModel;
    }

    protected HideUserGuideNextSprintModel createHideUserGuideNextSprintModel() { return new HideUserGuideNextSprintModel(); }

    protected class HideUserGuideNextSprintModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideNextSprint";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideNextSprint();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideNextSprint(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideProductBacklog ---

    private boolean hideUserGuideProductBacklog ;

    public final boolean isHideUserGuideProductBacklog() {
        return this.hideUserGuideProductBacklog ;
    }

    public final User setHideUserGuideProductBacklog(boolean hideUserGuideProductBacklog) {
        if (isHideUserGuideProductBacklog(hideUserGuideProductBacklog)) return (User)this;
        this.hideUserGuideProductBacklog = hideUserGuideProductBacklog ;
        propertyChanged("hideUserGuideProductBacklog", this.hideUserGuideProductBacklog);
        return (User)this;
    }

    public final boolean isHideUserGuideProductBacklog(boolean hideUserGuideProductBacklog) {
        return equals(this.hideUserGuideProductBacklog, hideUserGuideProductBacklog);
    }

    private transient HideUserGuideProductBacklogModel hideUserGuideProductBacklogModel;

    public HideUserGuideProductBacklogModel getHideUserGuideProductBacklogModel() {
        if (hideUserGuideProductBacklogModel == null) hideUserGuideProductBacklogModel = createHideUserGuideProductBacklogModel();
        return hideUserGuideProductBacklogModel;
    }

    protected HideUserGuideProductBacklogModel createHideUserGuideProductBacklogModel() { return new HideUserGuideProductBacklogModel(); }

    protected class HideUserGuideProductBacklogModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideProductBacklog";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideProductBacklog();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideProductBacklog(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideCourtroom ---

    private boolean hideUserGuideCourtroom ;

    public final boolean isHideUserGuideCourtroom() {
        return this.hideUserGuideCourtroom ;
    }

    public final User setHideUserGuideCourtroom(boolean hideUserGuideCourtroom) {
        if (isHideUserGuideCourtroom(hideUserGuideCourtroom)) return (User)this;
        this.hideUserGuideCourtroom = hideUserGuideCourtroom ;
        propertyChanged("hideUserGuideCourtroom", this.hideUserGuideCourtroom);
        return (User)this;
    }

    public final boolean isHideUserGuideCourtroom(boolean hideUserGuideCourtroom) {
        return equals(this.hideUserGuideCourtroom, hideUserGuideCourtroom);
    }

    private transient HideUserGuideCourtroomModel hideUserGuideCourtroomModel;

    public HideUserGuideCourtroomModel getHideUserGuideCourtroomModel() {
        if (hideUserGuideCourtroomModel == null) hideUserGuideCourtroomModel = createHideUserGuideCourtroomModel();
        return hideUserGuideCourtroomModel;
    }

    protected HideUserGuideCourtroomModel createHideUserGuideCourtroomModel() { return new HideUserGuideCourtroomModel(); }

    protected class HideUserGuideCourtroomModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideCourtroom";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideCourtroom();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideCourtroom(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideQualityBacklog ---

    private boolean hideUserGuideQualityBacklog ;

    public final boolean isHideUserGuideQualityBacklog() {
        return this.hideUserGuideQualityBacklog ;
    }

    public final User setHideUserGuideQualityBacklog(boolean hideUserGuideQualityBacklog) {
        if (isHideUserGuideQualityBacklog(hideUserGuideQualityBacklog)) return (User)this;
        this.hideUserGuideQualityBacklog = hideUserGuideQualityBacklog ;
        propertyChanged("hideUserGuideQualityBacklog", this.hideUserGuideQualityBacklog);
        return (User)this;
    }

    public final boolean isHideUserGuideQualityBacklog(boolean hideUserGuideQualityBacklog) {
        return equals(this.hideUserGuideQualityBacklog, hideUserGuideQualityBacklog);
    }

    private transient HideUserGuideQualityBacklogModel hideUserGuideQualityBacklogModel;

    public HideUserGuideQualityBacklogModel getHideUserGuideQualityBacklogModel() {
        if (hideUserGuideQualityBacklogModel == null) hideUserGuideQualityBacklogModel = createHideUserGuideQualityBacklogModel();
        return hideUserGuideQualityBacklogModel;
    }

    protected HideUserGuideQualityBacklogModel createHideUserGuideQualityBacklogModel() { return new HideUserGuideQualityBacklogModel(); }

    protected class HideUserGuideQualityBacklogModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideQualityBacklog";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideQualityBacklog();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideQualityBacklog(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideReleases ---

    private boolean hideUserGuideReleases ;

    public final boolean isHideUserGuideReleases() {
        return this.hideUserGuideReleases ;
    }

    public final User setHideUserGuideReleases(boolean hideUserGuideReleases) {
        if (isHideUserGuideReleases(hideUserGuideReleases)) return (User)this;
        this.hideUserGuideReleases = hideUserGuideReleases ;
        propertyChanged("hideUserGuideReleases", this.hideUserGuideReleases);
        return (User)this;
    }

    public final boolean isHideUserGuideReleases(boolean hideUserGuideReleases) {
        return equals(this.hideUserGuideReleases, hideUserGuideReleases);
    }

    private transient HideUserGuideReleasesModel hideUserGuideReleasesModel;

    public HideUserGuideReleasesModel getHideUserGuideReleasesModel() {
        if (hideUserGuideReleasesModel == null) hideUserGuideReleasesModel = createHideUserGuideReleasesModel();
        return hideUserGuideReleasesModel;
    }

    protected HideUserGuideReleasesModel createHideUserGuideReleasesModel() { return new HideUserGuideReleasesModel(); }

    protected class HideUserGuideReleasesModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideReleases";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideReleases();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideReleases(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideRisks ---

    private boolean hideUserGuideRisks ;

    public final boolean isHideUserGuideRisks() {
        return this.hideUserGuideRisks ;
    }

    public final User setHideUserGuideRisks(boolean hideUserGuideRisks) {
        if (isHideUserGuideRisks(hideUserGuideRisks)) return (User)this;
        this.hideUserGuideRisks = hideUserGuideRisks ;
        propertyChanged("hideUserGuideRisks", this.hideUserGuideRisks);
        return (User)this;
    }

    public final boolean isHideUserGuideRisks(boolean hideUserGuideRisks) {
        return equals(this.hideUserGuideRisks, hideUserGuideRisks);
    }

    private transient HideUserGuideRisksModel hideUserGuideRisksModel;

    public HideUserGuideRisksModel getHideUserGuideRisksModel() {
        if (hideUserGuideRisksModel == null) hideUserGuideRisksModel = createHideUserGuideRisksModel();
        return hideUserGuideRisksModel;
    }

    protected HideUserGuideRisksModel createHideUserGuideRisksModel() { return new HideUserGuideRisksModel(); }

    protected class HideUserGuideRisksModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideRisks";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideRisks();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideRisks(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideSprintBacklog ---

    private boolean hideUserGuideSprintBacklog ;

    public final boolean isHideUserGuideSprintBacklog() {
        return this.hideUserGuideSprintBacklog ;
    }

    public final User setHideUserGuideSprintBacklog(boolean hideUserGuideSprintBacklog) {
        if (isHideUserGuideSprintBacklog(hideUserGuideSprintBacklog)) return (User)this;
        this.hideUserGuideSprintBacklog = hideUserGuideSprintBacklog ;
        propertyChanged("hideUserGuideSprintBacklog", this.hideUserGuideSprintBacklog);
        return (User)this;
    }

    public final boolean isHideUserGuideSprintBacklog(boolean hideUserGuideSprintBacklog) {
        return equals(this.hideUserGuideSprintBacklog, hideUserGuideSprintBacklog);
    }

    private transient HideUserGuideSprintBacklogModel hideUserGuideSprintBacklogModel;

    public HideUserGuideSprintBacklogModel getHideUserGuideSprintBacklogModel() {
        if (hideUserGuideSprintBacklogModel == null) hideUserGuideSprintBacklogModel = createHideUserGuideSprintBacklogModel();
        return hideUserGuideSprintBacklogModel;
    }

    protected HideUserGuideSprintBacklogModel createHideUserGuideSprintBacklogModel() { return new HideUserGuideSprintBacklogModel(); }

    protected class HideUserGuideSprintBacklogModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideSprintBacklog";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideSprintBacklog();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideSprintBacklog(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- hideUserGuideWhiteboard ---

    private boolean hideUserGuideWhiteboard ;

    public final boolean isHideUserGuideWhiteboard() {
        return this.hideUserGuideWhiteboard ;
    }

    public final User setHideUserGuideWhiteboard(boolean hideUserGuideWhiteboard) {
        if (isHideUserGuideWhiteboard(hideUserGuideWhiteboard)) return (User)this;
        this.hideUserGuideWhiteboard = hideUserGuideWhiteboard ;
        propertyChanged("hideUserGuideWhiteboard", this.hideUserGuideWhiteboard);
        return (User)this;
    }

    public final boolean isHideUserGuideWhiteboard(boolean hideUserGuideWhiteboard) {
        return equals(this.hideUserGuideWhiteboard, hideUserGuideWhiteboard);
    }

    private transient HideUserGuideWhiteboardModel hideUserGuideWhiteboardModel;

    public HideUserGuideWhiteboardModel getHideUserGuideWhiteboardModel() {
        if (hideUserGuideWhiteboardModel == null) hideUserGuideWhiteboardModel = createHideUserGuideWhiteboardModel();
        return hideUserGuideWhiteboardModel;
    }

    protected HideUserGuideWhiteboardModel createHideUserGuideWhiteboardModel() { return new HideUserGuideWhiteboardModel(); }

    protected class HideUserGuideWhiteboardModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "User_hideUserGuideWhiteboard";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isHideUserGuideWhiteboard();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setHideUserGuideWhiteboard(value);
        }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- loginToken ---

    private java.lang.String loginToken ;

    public final java.lang.String getLoginToken() {
        return this.loginToken ;
    }

    public final User setLoginToken(java.lang.String loginToken) {
        if (isLoginToken(loginToken)) return (User)this;
        if (loginToken != null && getDao().getUserByLoginToken(loginToken) != null) throw new RuntimeException("\"" + loginToken + "\" already exists.");
        this.loginToken = loginToken ;
        propertyChanged("loginToken", this.loginToken);
        return (User)this;
    }

    public final boolean isLoginToken(java.lang.String loginToken) {
        return equals(this.loginToken, loginToken);
    }

    private transient LoginTokenModel loginTokenModel;

    public LoginTokenModel getLoginTokenModel() {
        if (loginTokenModel == null) loginTokenModel = createLoginTokenModel();
        return loginTokenModel;
    }

    protected LoginTokenModel createLoginTokenModel() { return new LoginTokenModel(); }

    protected class LoginTokenModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "User_loginToken";
        }

        @Override
        public java.lang.String getValue() {
            return getLoginToken();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLoginToken(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- openId ---

    private java.lang.String openId ;

    public final java.lang.String getOpenId() {
        return this.openId ;
    }

    public final User setOpenId(java.lang.String openId) {
        if (isOpenId(openId)) return (User)this;
        if (openId != null && getDao().getUserByOpenId(openId) != null) throw new RuntimeException("\"" + openId + "\" already exists.");
        this.openId = openId ;
        propertyChanged("openId", this.openId);
        return (User)this;
    }

    public final boolean isOpenId(java.lang.String openId) {
        return equals(this.openId, openId);
    }

    private transient OpenIdModel openIdModel;

    public OpenIdModel getOpenIdModel() {
        if (openIdModel == null) openIdModel = createOpenIdModel();
        return openIdModel;
    }

    protected OpenIdModel createOpenIdModel() { return new OpenIdModel(); }

    protected class OpenIdModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "User_openId";
        }

        @Override
        public java.lang.String getValue() {
            return getOpenId();
        }

        @Override
        public void setValue(java.lang.String value) {
            setOpenId(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- update properties by map ---

    public void updateProperties(Map props) {
        name  = (java.lang.String) props.get("name");
        publicName  = (java.lang.String) props.get("publicName");
        fullName  = (java.lang.String) props.get("fullName");
        admin  = (Boolean) props.get("admin");
        emailVerified  = (Boolean) props.get("emailVerified");
        email  = (java.lang.String) props.get("email");
        currentProjectId = (String) props.get("currentProjectId");
        color  = (java.lang.String) props.get("color");
        String lastLoginDateAndTimeAsString = (String) props.get("lastLoginDateAndTime");
        lastLoginDateAndTime  =  lastLoginDateAndTimeAsString == null ? null : new ilarkesto.core.time.DateAndTime(lastLoginDateAndTimeAsString);
        String registrationDateAndTimeAsString = (String) props.get("registrationDateAndTime");
        registrationDateAndTime  =  registrationDateAndTimeAsString == null ? null : new ilarkesto.core.time.DateAndTime(registrationDateAndTimeAsString);
        disabled  = (Boolean) props.get("disabled");
        hideUserGuideBlog  = (Boolean) props.get("hideUserGuideBlog");
        hideUserGuideCalendar  = (Boolean) props.get("hideUserGuideCalendar");
        hideUserGuideFiles  = (Boolean) props.get("hideUserGuideFiles");
        hideUserGuideForum  = (Boolean) props.get("hideUserGuideForum");
        hideUserGuideImpediments  = (Boolean) props.get("hideUserGuideImpediments");
        hideUserGuideIssues  = (Boolean) props.get("hideUserGuideIssues");
        hideUserGuideJournal  = (Boolean) props.get("hideUserGuideJournal");
        hideUserGuideNextSprint  = (Boolean) props.get("hideUserGuideNextSprint");
        hideUserGuideProductBacklog  = (Boolean) props.get("hideUserGuideProductBacklog");
        hideUserGuideCourtroom  = (Boolean) props.get("hideUserGuideCourtroom");
        hideUserGuideQualityBacklog  = (Boolean) props.get("hideUserGuideQualityBacklog");
        hideUserGuideReleases  = (Boolean) props.get("hideUserGuideReleases");
        hideUserGuideRisks  = (Boolean) props.get("hideUserGuideRisks");
        hideUserGuideSprintBacklog  = (Boolean) props.get("hideUserGuideSprintBacklog");
        hideUserGuideWhiteboard  = (Boolean) props.get("hideUserGuideWhiteboard");
        loginToken  = (java.lang.String) props.get("loginToken");
        openId  = (java.lang.String) props.get("openId");
        updateLocalModificationTime();
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

    public final java.util.List<scrum.client.project.Project> getProjects() {
        return getDao().getProjectsByParticipant((User)this);
    }

    public final java.util.List<scrum.client.sprint.Sprint> getSprints() {
        return getDao().getSprintsByProductOwner((User)this);
    }

    public final java.util.List<scrum.client.collaboration.Emoticon> getEmoticons() {
        return getDao().getEmoticonsByOwner((User)this);
    }

    public final java.util.List<scrum.client.admin.ProjectUserConfig> getProjectUserConfigs() {
        return getDao().getProjectUserConfigsByUser((User)this);
    }

    public final java.util.List<scrum.client.issues.Issue> getIssues() {
        return getDao().getIssuesByCreator((User)this);
    }

    public final java.util.List<scrum.client.sprint.Task> getTasks() {
        return getDao().getTasksByOwner((User)this);
    }

    public final java.util.List<scrum.client.journal.Change> getChanges() {
        return getDao().getChangesByUser((User)this);
    }

    public final java.util.List<scrum.client.collaboration.Comment> getComments() {
        return getDao().getCommentsByAuthor((User)this);
    }

    public final java.util.List<scrum.client.collaboration.ChatMessage> getChatMessages() {
        return getDao().getChatMessagesByAuthor((User)this);
    }

    public final java.util.List<scrum.client.pr.BlogEntry> getBlogEntrys() {
        return getDao().getBlogEntrysByAuthor((User)this);
    }

    public final java.util.List<scrum.client.estimation.RequirementEstimationVote> getRequirementEstimationVotes() {
        return getDao().getRequirementEstimationVotesByUser((User)this);
    }

    @Override
    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getName(), key)) return true;
        if (matchesKey(getPublicName(), key)) return true;
        if (matchesKey(getFullName(), key)) return true;
        if (matchesKey(getEmail(), key)) return true;
        return false;
    }

}