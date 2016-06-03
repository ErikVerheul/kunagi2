// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityGenerator










package scrum.client.admin;

import java.util.*;
import ilarkesto.core.logging.Log;
import scrum.client.common.*;
import ilarkesto.gwt.client.*;

public abstract class GSystemConfig
            extends scrum.client.common.AScrumGwtEntity {

    protected scrum.client.Dao getDao() {
        return scrum.client.Dao.get();
    }

    public GSystemConfig() {
    }

    public GSystemConfig(Map data) {
        super(data);
        updateProperties(data);
    }

    public static final String ENTITY_TYPE = "systemConfig";

    @Override
    public final String getEntityType() {
        return ENTITY_TYPE;
    }

    // --- url ---

    private java.lang.String url ;

    public final java.lang.String getUrl() {
        return this.url ;
    }

    public final SystemConfig setUrl(java.lang.String url) {
        if (isUrl(url)) return (SystemConfig)this;
        this.url = url ;
        propertyChanged("url", this.url);
        return (SystemConfig)this;
    }

    public final boolean isUrl(java.lang.String url) {
        return equals(this.url, url);
    }

    private transient UrlModel urlModel;

    public UrlModel getUrlModel() {
        if (urlModel == null) urlModel = createUrlModel();
        return urlModel;
    }

    protected UrlModel createUrlModel() { return new UrlModel(); }

    protected class UrlModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_url";
        }

        @Override
        public java.lang.String getValue() {
            return getUrl();
        }

        @Override
        public void setValue(java.lang.String value) {
            setUrl(value);
        }
        @Override
        public String getTooltip() { return "URL, on which this Kunagi instance is installed. It will be used in emails."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- adminEmail ---

    private java.lang.String adminEmail ;

    public final java.lang.String getAdminEmail() {
        return this.adminEmail ;
    }

    public final SystemConfig setAdminEmail(java.lang.String adminEmail) {
        if (isAdminEmail(adminEmail)) return (SystemConfig)this;
        this.adminEmail = adminEmail ;
        propertyChanged("adminEmail", this.adminEmail);
        return (SystemConfig)this;
    }

    public final boolean isAdminEmail(java.lang.String adminEmail) {
        return equals(this.adminEmail, adminEmail);
    }

    private transient AdminEmailModel adminEmailModel;

    public AdminEmailModel getAdminEmailModel() {
        if (adminEmailModel == null) adminEmailModel = createAdminEmailModel();
        return adminEmailModel;
    }

    protected AdminEmailModel createAdminEmailModel() { return new AdminEmailModel(); }

    protected class AdminEmailModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_adminEmail";
        }

        @Override
        public java.lang.String getValue() {
            return getAdminEmail();
        }

        @Override
        public void setValue(java.lang.String value) {
            setAdminEmail(value);
        }
        @Override
        public String getTooltip() { return "Email of the administrator of this Kunagi instance."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- googleAnalyticsId ---

    private java.lang.String googleAnalyticsId ;

    public final java.lang.String getGoogleAnalyticsId() {
        return this.googleAnalyticsId ;
    }

    public final SystemConfig setGoogleAnalyticsId(java.lang.String googleAnalyticsId) {
        if (isGoogleAnalyticsId(googleAnalyticsId)) return (SystemConfig)this;
        this.googleAnalyticsId = googleAnalyticsId ;
        propertyChanged("googleAnalyticsId", this.googleAnalyticsId);
        return (SystemConfig)this;
    }

    public final boolean isGoogleAnalyticsId(java.lang.String googleAnalyticsId) {
        return equals(this.googleAnalyticsId, googleAnalyticsId);
    }

    private transient GoogleAnalyticsIdModel googleAnalyticsIdModel;

    public GoogleAnalyticsIdModel getGoogleAnalyticsIdModel() {
        if (googleAnalyticsIdModel == null) googleAnalyticsIdModel = createGoogleAnalyticsIdModel();
        return googleAnalyticsIdModel;
    }

    protected GoogleAnalyticsIdModel createGoogleAnalyticsIdModel() { return new GoogleAnalyticsIdModel(); }

    protected class GoogleAnalyticsIdModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_googleAnalyticsId";
        }

        @Override
        public java.lang.String getValue() {
            return getGoogleAnalyticsId();
        }

        @Override
        public void setValue(java.lang.String value) {
            setGoogleAnalyticsId(value);
        }
        @Override
        public String getTooltip() { return "Google Web Property ID, so you can log to Google Analytics."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- smtpServer ---

    private java.lang.String smtpServer ;

    public final java.lang.String getSmtpServer() {
        return this.smtpServer ;
    }

    public final SystemConfig setSmtpServer(java.lang.String smtpServer) {
        if (isSmtpServer(smtpServer)) return (SystemConfig)this;
        this.smtpServer = smtpServer ;
        propertyChanged("smtpServer", this.smtpServer);
        return (SystemConfig)this;
    }

    public final boolean isSmtpServer(java.lang.String smtpServer) {
        return equals(this.smtpServer, smtpServer);
    }

    private transient SmtpServerModel smtpServerModel;

    public SmtpServerModel getSmtpServerModel() {
        if (smtpServerModel == null) smtpServerModel = createSmtpServerModel();
        return smtpServerModel;
    }

    protected SmtpServerModel createSmtpServerModel() { return new SmtpServerModel(); }

    protected class SmtpServerModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_smtpServer";
        }

        @Override
        public java.lang.String getValue() {
            return getSmtpServer();
        }

        @Override
        public void setValue(java.lang.String value) {
            setSmtpServer(value);
        }
        @Override
        public String getTooltip() { return "Hostname of your SMTP email server."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- smtpPort ---

    private java.lang.Integer smtpPort ;

    public final java.lang.Integer getSmtpPort() {
        return this.smtpPort ;
    }

    public final SystemConfig setSmtpPort(java.lang.Integer smtpPort) {
        if (isSmtpPort(smtpPort)) return (SystemConfig)this;
        this.smtpPort = smtpPort ;
        propertyChanged("smtpPort", this.smtpPort);
        return (SystemConfig)this;
    }

    public final boolean isSmtpPort(java.lang.Integer smtpPort) {
        return equals(this.smtpPort, smtpPort);
    }

    private transient SmtpPortModel smtpPortModel;

    public SmtpPortModel getSmtpPortModel() {
        if (smtpPortModel == null) smtpPortModel = createSmtpPortModel();
        return smtpPortModel;
    }

    protected SmtpPortModel createSmtpPortModel() { return new SmtpPortModel(); }

    protected class SmtpPortModel extends ilarkesto.gwt.client.editor.AIntegerEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_smtpPort";
        }

        @Override
        public java.lang.Integer getValue() {
            return getSmtpPort();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setSmtpPort(value);
        }

            @Override
            public void increment() {
                setSmtpPort(getSmtpPort() + 1);
            }

            @Override
            public void decrement() {
                setSmtpPort(getSmtpPort() - 1);
            }
        @Override
        public String getTooltip() { return "Port of your SMTP email server."; }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- smtpTls ---

    private boolean smtpTls ;

    public final boolean isSmtpTls() {
        return this.smtpTls ;
    }

    public final SystemConfig setSmtpTls(boolean smtpTls) {
        if (isSmtpTls(smtpTls)) return (SystemConfig)this;
        this.smtpTls = smtpTls ;
        propertyChanged("smtpTls", this.smtpTls);
        return (SystemConfig)this;
    }

    public final boolean isSmtpTls(boolean smtpTls) {
        return equals(this.smtpTls, smtpTls);
    }

    private transient SmtpTlsModel smtpTlsModel;

    public SmtpTlsModel getSmtpTlsModel() {
        if (smtpTlsModel == null) smtpTlsModel = createSmtpTlsModel();
        return smtpTlsModel;
    }

    protected SmtpTlsModel createSmtpTlsModel() { return new SmtpTlsModel(); }

    protected class SmtpTlsModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_smtpTls";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isSmtpTls();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setSmtpTls(value);
        }
        @Override
        public String getTooltip() { return "Activate this, if your SMTP email server requires TLS. Gmail requires this."; }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- smtpUser ---

    private java.lang.String smtpUser ;

    public final java.lang.String getSmtpUser() {
        return this.smtpUser ;
    }

    public final SystemConfig setSmtpUser(java.lang.String smtpUser) {
        if (isSmtpUser(smtpUser)) return (SystemConfig)this;
        this.smtpUser = smtpUser ;
        propertyChanged("smtpUser", this.smtpUser);
        return (SystemConfig)this;
    }

    public final boolean isSmtpUser(java.lang.String smtpUser) {
        return equals(this.smtpUser, smtpUser);
    }

    private transient SmtpUserModel smtpUserModel;

    public SmtpUserModel getSmtpUserModel() {
        if (smtpUserModel == null) smtpUserModel = createSmtpUserModel();
        return smtpUserModel;
    }

    protected SmtpUserModel createSmtpUserModel() { return new SmtpUserModel(); }

    protected class SmtpUserModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_smtpUser";
        }

        @Override
        public java.lang.String getValue() {
            return getSmtpUser();
        }

        @Override
        public void setValue(java.lang.String value) {
            setSmtpUser(value);
        }
        @Override
        public String getTooltip() { return "Username, if your SMTP email server requires authentication."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- smtpPassword ---

    private java.lang.String smtpPassword ;

    public final java.lang.String getSmtpPassword() {
        return this.smtpPassword ;
    }

    public final SystemConfig setSmtpPassword(java.lang.String smtpPassword) {
        if (isSmtpPassword(smtpPassword)) return (SystemConfig)this;
        this.smtpPassword = smtpPassword ;
        propertyChanged("smtpPassword", this.smtpPassword);
        return (SystemConfig)this;
    }

    public final boolean isSmtpPassword(java.lang.String smtpPassword) {
        return equals(this.smtpPassword, smtpPassword);
    }

    private transient SmtpPasswordModel smtpPasswordModel;

    public SmtpPasswordModel getSmtpPasswordModel() {
        if (smtpPasswordModel == null) smtpPasswordModel = createSmtpPasswordModel();
        return smtpPasswordModel;
    }

    protected SmtpPasswordModel createSmtpPasswordModel() { return new SmtpPasswordModel(); }

    protected class SmtpPasswordModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_smtpPassword";
        }

        @Override
        public java.lang.String getValue() {
            return getSmtpPassword();
        }

        @Override
        public void setValue(java.lang.String value) {
            setSmtpPassword(value);
        }

        @Override
        public boolean isMasked() { return true; }
        @Override
        public String getTooltip() { return "Password, if your SMTP email server requires authentication."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- smtpFrom ---

    private java.lang.String smtpFrom ;

    public final java.lang.String getSmtpFrom() {
        return this.smtpFrom ;
    }

    public final SystemConfig setSmtpFrom(java.lang.String smtpFrom) {
        if (isSmtpFrom(smtpFrom)) return (SystemConfig)this;
        this.smtpFrom = smtpFrom ;
        propertyChanged("smtpFrom", this.smtpFrom);
        return (SystemConfig)this;
    }

    public final boolean isSmtpFrom(java.lang.String smtpFrom) {
        return equals(this.smtpFrom, smtpFrom);
    }

    private transient SmtpFromModel smtpFromModel;

    public SmtpFromModel getSmtpFromModel() {
        if (smtpFromModel == null) smtpFromModel = createSmtpFromModel();
        return smtpFromModel;
    }

    protected SmtpFromModel createSmtpFromModel() { return new SmtpFromModel(); }

    protected class SmtpFromModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_smtpFrom";
        }

        @Override
        public java.lang.String getValue() {
            return getSmtpFrom();
        }

        @Override
        public void setValue(java.lang.String value) {
            setSmtpFrom(value);
        }
        @Override
        public String getTooltip() { return "Email address, which is used as sender, when Kunagi sends Emails."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- instanceName ---

    private java.lang.String instanceName ;

    public final java.lang.String getInstanceName() {
        return this.instanceName ;
    }

    public final SystemConfig setInstanceName(java.lang.String instanceName) {
        if (isInstanceName(instanceName)) return (SystemConfig)this;
        this.instanceName = instanceName ;
        propertyChanged("instanceName", this.instanceName);
        return (SystemConfig)this;
    }

    public final boolean isInstanceName(java.lang.String instanceName) {
        return equals(this.instanceName, instanceName);
    }

    private transient InstanceNameModel instanceNameModel;

    public InstanceNameModel getInstanceNameModel() {
        if (instanceNameModel == null) instanceNameModel = createInstanceNameModel();
        return instanceNameModel;
    }

    protected InstanceNameModel createInstanceNameModel() { return new InstanceNameModel(); }

    protected class InstanceNameModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_instanceName";
        }

        @Override
        public java.lang.String getValue() {
            return getInstanceName();
        }

        @Override
        public void setValue(java.lang.String value) {
            setInstanceName(value);
        }
        @Override
        public String getTooltip() { return "Name of this Kunagi installation instance. For identification in the title."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- loginPageLogoUrl ---

    private java.lang.String loginPageLogoUrl ;

    public final java.lang.String getLoginPageLogoUrl() {
        return this.loginPageLogoUrl ;
    }

    public final SystemConfig setLoginPageLogoUrl(java.lang.String loginPageLogoUrl) {
        if (isLoginPageLogoUrl(loginPageLogoUrl)) return (SystemConfig)this;
        this.loginPageLogoUrl = loginPageLogoUrl ;
        propertyChanged("loginPageLogoUrl", this.loginPageLogoUrl);
        return (SystemConfig)this;
    }

    public final boolean isLoginPageLogoUrl(java.lang.String loginPageLogoUrl) {
        return equals(this.loginPageLogoUrl, loginPageLogoUrl);
    }

    private transient LoginPageLogoUrlModel loginPageLogoUrlModel;

    public LoginPageLogoUrlModel getLoginPageLogoUrlModel() {
        if (loginPageLogoUrlModel == null) loginPageLogoUrlModel = createLoginPageLogoUrlModel();
        return loginPageLogoUrlModel;
    }

    protected LoginPageLogoUrlModel createLoginPageLogoUrlModel() { return new LoginPageLogoUrlModel(); }

    protected class LoginPageLogoUrlModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_loginPageLogoUrl";
        }

        @Override
        public java.lang.String getValue() {
            return getLoginPageLogoUrl();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLoginPageLogoUrl(value);
        }
        @Override
        public String getTooltip() { return "If you wand your custom logo on the login page, type the URL to the image here."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- loginPageMessage ---

    private java.lang.String loginPageMessage ;

    public final java.lang.String getLoginPageMessage() {
        return this.loginPageMessage ;
    }

    public final SystemConfig setLoginPageMessage(java.lang.String loginPageMessage) {
        if (isLoginPageMessage(loginPageMessage)) return (SystemConfig)this;
        this.loginPageMessage = loginPageMessage ;
        propertyChanged("loginPageMessage", this.loginPageMessage);
        return (SystemConfig)this;
    }

    public final boolean isLoginPageMessage(java.lang.String loginPageMessage) {
        return equals(this.loginPageMessage, loginPageMessage);
    }

    private transient LoginPageMessageModel loginPageMessageModel;

    public LoginPageMessageModel getLoginPageMessageModel() {
        if (loginPageMessageModel == null) loginPageMessageModel = createLoginPageMessageModel();
        return loginPageMessageModel;
    }

    protected LoginPageMessageModel createLoginPageMessageModel() { return new LoginPageMessageModel(); }

    protected class LoginPageMessageModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_loginPageMessage";
        }

        @Override
        public java.lang.String getValue() {
            return getLoginPageMessage();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLoginPageMessage(value);
        }

        @Override
        public boolean isRichtext() { return true; }
        @Override
        public String getTooltip() { return "Message in HTML, which is displayed on the login page."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- registerPageMessage ---

    private java.lang.String registerPageMessage ;

    public final java.lang.String getRegisterPageMessage() {
        return this.registerPageMessage ;
    }

    public final SystemConfig setRegisterPageMessage(java.lang.String registerPageMessage) {
        if (isRegisterPageMessage(registerPageMessage)) return (SystemConfig)this;
        this.registerPageMessage = registerPageMessage ;
        propertyChanged("registerPageMessage", this.registerPageMessage);
        return (SystemConfig)this;
    }

    public final boolean isRegisterPageMessage(java.lang.String registerPageMessage) {
        return equals(this.registerPageMessage, registerPageMessage);
    }

    private transient RegisterPageMessageModel registerPageMessageModel;

    public RegisterPageMessageModel getRegisterPageMessageModel() {
        if (registerPageMessageModel == null) registerPageMessageModel = createRegisterPageMessageModel();
        return registerPageMessageModel;
    }

    protected RegisterPageMessageModel createRegisterPageMessageModel() { return new RegisterPageMessageModel(); }

    protected class RegisterPageMessageModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_registerPageMessage";
        }

        @Override
        public java.lang.String getValue() {
            return getRegisterPageMessage();
        }

        @Override
        public void setValue(java.lang.String value) {
            setRegisterPageMessage(value);
        }

        @Override
        public boolean isRichtext() { return true; }
        @Override
        public String getTooltip() { return "Message in HTML, which is displayed on the registration page for new users."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- aboutPageMessage ---

    private java.lang.String aboutPageMessage ;

    public final java.lang.String getAboutPageMessage() {
        return this.aboutPageMessage ;
    }

    public final SystemConfig setAboutPageMessage(java.lang.String aboutPageMessage) {
        if (isAboutPageMessage(aboutPageMessage)) return (SystemConfig)this;
        this.aboutPageMessage = aboutPageMessage ;
        propertyChanged("aboutPageMessage", this.aboutPageMessage);
        return (SystemConfig)this;
    }

    public final boolean isAboutPageMessage(java.lang.String aboutPageMessage) {
        return equals(this.aboutPageMessage, aboutPageMessage);
    }

    private transient AboutPageMessageModel aboutPageMessageModel;

    public AboutPageMessageModel getAboutPageMessageModel() {
        if (aboutPageMessageModel == null) aboutPageMessageModel = createAboutPageMessageModel();
        return aboutPageMessageModel;
    }

    protected AboutPageMessageModel createAboutPageMessageModel() { return new AboutPageMessageModel(); }

    protected class AboutPageMessageModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_aboutPageMessage";
        }

        @Override
        public java.lang.String getValue() {
            return getAboutPageMessage();
        }

        @Override
        public void setValue(java.lang.String value) {
            setAboutPageMessage(value);
        }

        @Override
        public boolean isRichtext() { return true; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- userEmailMandatory ---

    private boolean userEmailMandatory ;

    public final boolean isUserEmailMandatory() {
        return this.userEmailMandatory ;
    }

    public final SystemConfig setUserEmailMandatory(boolean userEmailMandatory) {
        if (isUserEmailMandatory(userEmailMandatory)) return (SystemConfig)this;
        this.userEmailMandatory = userEmailMandatory ;
        propertyChanged("userEmailMandatory", this.userEmailMandatory);
        return (SystemConfig)this;
    }

    public final boolean isUserEmailMandatory(boolean userEmailMandatory) {
        return equals(this.userEmailMandatory, userEmailMandatory);
    }

    private transient UserEmailMandatoryModel userEmailMandatoryModel;

    public UserEmailMandatoryModel getUserEmailMandatoryModel() {
        if (userEmailMandatoryModel == null) userEmailMandatoryModel = createUserEmailMandatoryModel();
        return userEmailMandatoryModel;
    }

    protected UserEmailMandatoryModel createUserEmailMandatoryModel() { return new UserEmailMandatoryModel(); }

    protected class UserEmailMandatoryModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_userEmailMandatory";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isUserEmailMandatory();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setUserEmailMandatory(value);
        }
        @Override
        public String getTooltip() { return "Activate this, if you want the email field on the registration page for new users to be mandatory."; }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- registrationDisabled ---

    private boolean registrationDisabled ;

    public final boolean isRegistrationDisabled() {
        return this.registrationDisabled ;
    }

    public final SystemConfig setRegistrationDisabled(boolean registrationDisabled) {
        if (isRegistrationDisabled(registrationDisabled)) return (SystemConfig)this;
        this.registrationDisabled = registrationDisabled ;
        propertyChanged("registrationDisabled", this.registrationDisabled);
        return (SystemConfig)this;
    }

    public final boolean isRegistrationDisabled(boolean registrationDisabled) {
        return equals(this.registrationDisabled, registrationDisabled);
    }

    private transient RegistrationDisabledModel registrationDisabledModel;

    public RegistrationDisabledModel getRegistrationDisabledModel() {
        if (registrationDisabledModel == null) registrationDisabledModel = createRegistrationDisabledModel();
        return registrationDisabledModel;
    }

    protected RegistrationDisabledModel createRegistrationDisabledModel() { return new RegistrationDisabledModel(); }

    protected class RegistrationDisabledModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_registrationDisabled";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isRegistrationDisabled();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setRegistrationDisabled(value);
        }
        @Override
        public String getTooltip() { return "Acitviate this, if you want to disable the registration page for new users."; }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- projectCreationDisabled ---

    private boolean projectCreationDisabled ;

    public final boolean isProjectCreationDisabled() {
        return this.projectCreationDisabled ;
    }

    public final SystemConfig setProjectCreationDisabled(boolean projectCreationDisabled) {
        if (isProjectCreationDisabled(projectCreationDisabled)) return (SystemConfig)this;
        this.projectCreationDisabled = projectCreationDisabled ;
        propertyChanged("projectCreationDisabled", this.projectCreationDisabled);
        return (SystemConfig)this;
    }

    public final boolean isProjectCreationDisabled(boolean projectCreationDisabled) {
        return equals(this.projectCreationDisabled, projectCreationDisabled);
    }

    private transient ProjectCreationDisabledModel projectCreationDisabledModel;

    public ProjectCreationDisabledModel getProjectCreationDisabledModel() {
        if (projectCreationDisabledModel == null) projectCreationDisabledModel = createProjectCreationDisabledModel();
        return projectCreationDisabledModel;
    }

    protected ProjectCreationDisabledModel createProjectCreationDisabledModel() { return new ProjectCreationDisabledModel(); }

    protected class ProjectCreationDisabledModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_projectCreationDisabled";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isProjectCreationDisabled();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setProjectCreationDisabled(value);
        }
        @Override
        public String getTooltip() { return "Activate this, to prevent users from creating projects."; }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- defaultUserPassword ---

    private java.lang.String defaultUserPassword ;

    public final java.lang.String getDefaultUserPassword() {
        return this.defaultUserPassword ;
    }

    public final SystemConfig setDefaultUserPassword(java.lang.String defaultUserPassword) {
        if (isDefaultUserPassword(defaultUserPassword)) return (SystemConfig)this;
        this.defaultUserPassword = defaultUserPassword ;
        propertyChanged("defaultUserPassword", this.defaultUserPassword);
        return (SystemConfig)this;
    }

    public final boolean isDefaultUserPassword(java.lang.String defaultUserPassword) {
        return equals(this.defaultUserPassword, defaultUserPassword);
    }

    private transient DefaultUserPasswordModel defaultUserPasswordModel;

    public DefaultUserPasswordModel getDefaultUserPasswordModel() {
        if (defaultUserPasswordModel == null) defaultUserPasswordModel = createDefaultUserPasswordModel();
        return defaultUserPasswordModel;
    }

    protected DefaultUserPasswordModel createDefaultUserPasswordModel() { return new DefaultUserPasswordModel(); }

    protected class DefaultUserPasswordModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_defaultUserPassword";
        }

        @Override
        public java.lang.String getValue() {
            return getDefaultUserPassword();
        }

        @Override
        public void setValue(java.lang.String value) {
            setDefaultUserPassword(value);
        }

        @Override
        public boolean isMasked() { return true; }
        @Override
        public String getTooltip() { return "Default password, which is assigned to new users, which are created by the admin."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- openIdDisabled ---

    private boolean openIdDisabled ;

    public final boolean isOpenIdDisabled() {
        return this.openIdDisabled ;
    }

    public final SystemConfig setOpenIdDisabled(boolean openIdDisabled) {
        if (isOpenIdDisabled(openIdDisabled)) return (SystemConfig)this;
        this.openIdDisabled = openIdDisabled ;
        propertyChanged("openIdDisabled", this.openIdDisabled);
        return (SystemConfig)this;
    }

    public final boolean isOpenIdDisabled(boolean openIdDisabled) {
        return equals(this.openIdDisabled, openIdDisabled);
    }

    private transient OpenIdDisabledModel openIdDisabledModel;

    public OpenIdDisabledModel getOpenIdDisabledModel() {
        if (openIdDisabledModel == null) openIdDisabledModel = createOpenIdDisabledModel();
        return openIdDisabledModel;
    }

    protected OpenIdDisabledModel createOpenIdDisabledModel() { return new OpenIdDisabledModel(); }

    protected class OpenIdDisabledModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_openIdDisabled";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isOpenIdDisabled();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setOpenIdDisabled(value);
        }
        @Override
        public String getTooltip() { return "Activate this, if you want to disable logins with OpenID."; }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- openIdDomains ---

    private java.lang.String openIdDomains ;

    public final java.lang.String getOpenIdDomains() {
        return this.openIdDomains ;
    }

    public final SystemConfig setOpenIdDomains(java.lang.String openIdDomains) {
        if (isOpenIdDomains(openIdDomains)) return (SystemConfig)this;
        this.openIdDomains = openIdDomains ;
        propertyChanged("openIdDomains", this.openIdDomains);
        return (SystemConfig)this;
    }

    public final boolean isOpenIdDomains(java.lang.String openIdDomains) {
        return equals(this.openIdDomains, openIdDomains);
    }

    private transient OpenIdDomainsModel openIdDomainsModel;

    public OpenIdDomainsModel getOpenIdDomainsModel() {
        if (openIdDomainsModel == null) openIdDomainsModel = createOpenIdDomainsModel();
        return openIdDomainsModel;
    }

    protected OpenIdDomainsModel createOpenIdDomainsModel() { return new OpenIdDomainsModel(); }

    protected class OpenIdDomainsModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_openIdDomains";
        }

        @Override
        public java.lang.String getValue() {
            return getOpenIdDomains();
        }

        @Override
        public void setValue(java.lang.String value) {
            setOpenIdDomains(value);
        }
        @Override
        public String getTooltip() { return "Limits accepted OpenID domains for new users. Multiple domains separated by commas allowed. Leave empty to allow all domains."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- versionCheckEnabled ---

    private boolean versionCheckEnabled ;

    public final boolean isVersionCheckEnabled() {
        return this.versionCheckEnabled ;
    }

    public final SystemConfig setVersionCheckEnabled(boolean versionCheckEnabled) {
        if (isVersionCheckEnabled(versionCheckEnabled)) return (SystemConfig)this;
        this.versionCheckEnabled = versionCheckEnabled ;
        propertyChanged("versionCheckEnabled", this.versionCheckEnabled);
        return (SystemConfig)this;
    }

    public final boolean isVersionCheckEnabled(boolean versionCheckEnabled) {
        return equals(this.versionCheckEnabled, versionCheckEnabled);
    }

    private transient VersionCheckEnabledModel versionCheckEnabledModel;

    public VersionCheckEnabledModel getVersionCheckEnabledModel() {
        if (versionCheckEnabledModel == null) versionCheckEnabledModel = createVersionCheckEnabledModel();
        return versionCheckEnabledModel;
    }

    protected VersionCheckEnabledModel createVersionCheckEnabledModel() { return new VersionCheckEnabledModel(); }

    protected class VersionCheckEnabledModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_versionCheckEnabled";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isVersionCheckEnabled();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setVersionCheckEnabled(value);
        }
        @Override
        public String getTooltip() { return "Acitvate this, if you want Kunagi to check for new versions and display a small Icon, when available."; }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- ldapEnabled ---

    private boolean ldapEnabled ;

    public final boolean isLdapEnabled() {
        return this.ldapEnabled ;
    }

    public final SystemConfig setLdapEnabled(boolean ldapEnabled) {
        if (isLdapEnabled(ldapEnabled)) return (SystemConfig)this;
        this.ldapEnabled = ldapEnabled ;
        propertyChanged("ldapEnabled", this.ldapEnabled);
        return (SystemConfig)this;
    }

    public final boolean isLdapEnabled(boolean ldapEnabled) {
        return equals(this.ldapEnabled, ldapEnabled);
    }

    private transient LdapEnabledModel ldapEnabledModel;

    public LdapEnabledModel getLdapEnabledModel() {
        if (ldapEnabledModel == null) ldapEnabledModel = createLdapEnabledModel();
        return ldapEnabledModel;
    }

    protected LdapEnabledModel createLdapEnabledModel() { return new LdapEnabledModel(); }

    protected class LdapEnabledModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_ldapEnabled";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isLdapEnabled();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setLdapEnabled(value);
        }
        @Override
        public String getTooltip() { return "Enable LDAP authentication. Kunagi will check username and password against a LDAP server."; }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- ldapUrl ---

    private java.lang.String ldapUrl ;

    public final java.lang.String getLdapUrl() {
        return this.ldapUrl ;
    }

    public final SystemConfig setLdapUrl(java.lang.String ldapUrl) {
        if (isLdapUrl(ldapUrl)) return (SystemConfig)this;
        this.ldapUrl = ldapUrl ;
        propertyChanged("ldapUrl", this.ldapUrl);
        return (SystemConfig)this;
    }

    public final boolean isLdapUrl(java.lang.String ldapUrl) {
        return equals(this.ldapUrl, ldapUrl);
    }

    private transient LdapUrlModel ldapUrlModel;

    public LdapUrlModel getLdapUrlModel() {
        if (ldapUrlModel == null) ldapUrlModel = createLdapUrlModel();
        return ldapUrlModel;
    }

    protected LdapUrlModel createLdapUrlModel() { return new LdapUrlModel(); }

    protected class LdapUrlModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_ldapUrl";
        }

        @Override
        public java.lang.String getValue() {
            return getLdapUrl();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLdapUrl(value);
        }
        @Override
        public String getTooltip() { return "URL for the LDAP server. Example: ldap://127.0.0.1:389/"; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- ldapUser ---

    private java.lang.String ldapUser ;

    public final java.lang.String getLdapUser() {
        return this.ldapUser ;
    }

    public final SystemConfig setLdapUser(java.lang.String ldapUser) {
        if (isLdapUser(ldapUser)) return (SystemConfig)this;
        this.ldapUser = ldapUser ;
        propertyChanged("ldapUser", this.ldapUser);
        return (SystemConfig)this;
    }

    public final boolean isLdapUser(java.lang.String ldapUser) {
        return equals(this.ldapUser, ldapUser);
    }

    private transient LdapUserModel ldapUserModel;

    public LdapUserModel getLdapUserModel() {
        if (ldapUserModel == null) ldapUserModel = createLdapUserModel();
        return ldapUserModel;
    }

    protected LdapUserModel createLdapUserModel() { return new LdapUserModel(); }

    protected class LdapUserModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_ldapUser";
        }

        @Override
        public java.lang.String getValue() {
            return getLdapUser();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLdapUser(value);
        }
        @Override
        public String getTooltip() { return "Username which is required to connect to the LDAP server."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- ldapPassword ---

    private java.lang.String ldapPassword ;

    public final java.lang.String getLdapPassword() {
        return this.ldapPassword ;
    }

    public final SystemConfig setLdapPassword(java.lang.String ldapPassword) {
        if (isLdapPassword(ldapPassword)) return (SystemConfig)this;
        this.ldapPassword = ldapPassword ;
        propertyChanged("ldapPassword", this.ldapPassword);
        return (SystemConfig)this;
    }

    public final boolean isLdapPassword(java.lang.String ldapPassword) {
        return equals(this.ldapPassword, ldapPassword);
    }

    private transient LdapPasswordModel ldapPasswordModel;

    public LdapPasswordModel getLdapPasswordModel() {
        if (ldapPasswordModel == null) ldapPasswordModel = createLdapPasswordModel();
        return ldapPasswordModel;
    }

    protected LdapPasswordModel createLdapPasswordModel() { return new LdapPasswordModel(); }

    protected class LdapPasswordModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_ldapPassword";
        }

        @Override
        public java.lang.String getValue() {
            return getLdapPassword();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLdapPassword(value);
        }

        @Override
        public boolean isMasked() { return true; }
        @Override
        public String getTooltip() { return "Password which is required to connect to the LDAP server."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- ldapBaseDn ---

    private java.lang.String ldapBaseDn ;

    public final java.lang.String getLdapBaseDn() {
        return this.ldapBaseDn ;
    }

    public final SystemConfig setLdapBaseDn(java.lang.String ldapBaseDn) {
        if (isLdapBaseDn(ldapBaseDn)) return (SystemConfig)this;
        this.ldapBaseDn = ldapBaseDn ;
        propertyChanged("ldapBaseDn", this.ldapBaseDn);
        return (SystemConfig)this;
    }

    public final boolean isLdapBaseDn(java.lang.String ldapBaseDn) {
        return equals(this.ldapBaseDn, ldapBaseDn);
    }

    private transient LdapBaseDnModel ldapBaseDnModel;

    public LdapBaseDnModel getLdapBaseDnModel() {
        if (ldapBaseDnModel == null) ldapBaseDnModel = createLdapBaseDnModel();
        return ldapBaseDnModel;
    }

    protected LdapBaseDnModel createLdapBaseDnModel() { return new LdapBaseDnModel(); }

    protected class LdapBaseDnModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_ldapBaseDn";
        }

        @Override
        public java.lang.String getValue() {
            return getLdapBaseDn();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLdapBaseDn(value);
        }
        @Override
        public String getTooltip() { return "Example: dc=mydomain,dc=com"; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- ldapUserFilterRegex ---

    private java.lang.String ldapUserFilterRegex ;

    public final java.lang.String getLdapUserFilterRegex() {
        return this.ldapUserFilterRegex ;
    }

    public final SystemConfig setLdapUserFilterRegex(java.lang.String ldapUserFilterRegex) {
        if (isLdapUserFilterRegex(ldapUserFilterRegex)) return (SystemConfig)this;
        this.ldapUserFilterRegex = ldapUserFilterRegex ;
        propertyChanged("ldapUserFilterRegex", this.ldapUserFilterRegex);
        return (SystemConfig)this;
    }

    public final boolean isLdapUserFilterRegex(java.lang.String ldapUserFilterRegex) {
        return equals(this.ldapUserFilterRegex, ldapUserFilterRegex);
    }

    private transient LdapUserFilterRegexModel ldapUserFilterRegexModel;

    public LdapUserFilterRegexModel getLdapUserFilterRegexModel() {
        if (ldapUserFilterRegexModel == null) ldapUserFilterRegexModel = createLdapUserFilterRegexModel();
        return ldapUserFilterRegexModel;
    }

    protected LdapUserFilterRegexModel createLdapUserFilterRegexModel() { return new LdapUserFilterRegexModel(); }

    protected class LdapUserFilterRegexModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_ldapUserFilterRegex";
        }

        @Override
        public java.lang.String getValue() {
            return getLdapUserFilterRegex();
        }

        @Override
        public void setValue(java.lang.String value) {
            setLdapUserFilterRegex(value);
        }
        @Override
        public String getTooltip() { return "Example: (&(objectClass=user)(sAMAccountName=%u))"; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- maxFileSize ---

    private java.lang.Integer maxFileSize ;

    public final java.lang.Integer getMaxFileSize() {
        return this.maxFileSize ;
    }

    public final SystemConfig setMaxFileSize(java.lang.Integer maxFileSize) {
        if (isMaxFileSize(maxFileSize)) return (SystemConfig)this;
        this.maxFileSize = maxFileSize ;
        propertyChanged("maxFileSize", this.maxFileSize);
        return (SystemConfig)this;
    }

    public final boolean isMaxFileSize(java.lang.Integer maxFileSize) {
        return equals(this.maxFileSize, maxFileSize);
    }

    private transient MaxFileSizeModel maxFileSizeModel;

    public MaxFileSizeModel getMaxFileSizeModel() {
        if (maxFileSizeModel == null) maxFileSizeModel = createMaxFileSizeModel();
        return maxFileSizeModel;
    }

    protected MaxFileSizeModel createMaxFileSizeModel() { return new MaxFileSizeModel(); }

    protected class MaxFileSizeModel extends ilarkesto.gwt.client.editor.AIntegerEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_maxFileSize";
        }

        @Override
        public java.lang.Integer getValue() {
            return getMaxFileSize();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setMaxFileSize(value);
        }

            @Override
            public void increment() {
                setMaxFileSize(getMaxFileSize() + 1);
            }

            @Override
            public void decrement() {
                setMaxFileSize(getMaxFileSize() - 1);
            }
        @Override
        public String getTooltip() { return "Maximum size in megabytes for uploaded files."; }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- subscriptionKeySeed ---

    private java.lang.String subscriptionKeySeed ;

    public final java.lang.String getSubscriptionKeySeed() {
        return this.subscriptionKeySeed ;
    }

    public final SystemConfig setSubscriptionKeySeed(java.lang.String subscriptionKeySeed) {
        if (isSubscriptionKeySeed(subscriptionKeySeed)) return (SystemConfig)this;
        this.subscriptionKeySeed = subscriptionKeySeed ;
        propertyChanged("subscriptionKeySeed", this.subscriptionKeySeed);
        return (SystemConfig)this;
    }

    public final boolean isSubscriptionKeySeed(java.lang.String subscriptionKeySeed) {
        return equals(this.subscriptionKeySeed, subscriptionKeySeed);
    }

    private transient SubscriptionKeySeedModel subscriptionKeySeedModel;

    public SubscriptionKeySeedModel getSubscriptionKeySeedModel() {
        if (subscriptionKeySeedModel == null) subscriptionKeySeedModel = createSubscriptionKeySeedModel();
        return subscriptionKeySeedModel;
    }

    protected SubscriptionKeySeedModel createSubscriptionKeySeedModel() { return new SubscriptionKeySeedModel(); }

    protected class SubscriptionKeySeedModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "SystemConfig_subscriptionKeySeed";
        }

        @Override
        public java.lang.String getValue() {
            return getSubscriptionKeySeed();
        }

        @Override
        public void setValue(java.lang.String value) {
            setSubscriptionKeySeed(value);
        }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    // --- update properties by map ---

    public void updateProperties(Map props) {
        url  = (java.lang.String) props.get("url");
        adminEmail  = (java.lang.String) props.get("adminEmail");
        googleAnalyticsId  = (java.lang.String) props.get("googleAnalyticsId");
        smtpServer  = (java.lang.String) props.get("smtpServer");
        smtpPort  = (java.lang.Integer) props.get("smtpPort");
        smtpTls  = (Boolean) props.get("smtpTls");
        smtpUser  = (java.lang.String) props.get("smtpUser");
        smtpPassword  = (java.lang.String) props.get("smtpPassword");
        smtpFrom  = (java.lang.String) props.get("smtpFrom");
        instanceName  = (java.lang.String) props.get("instanceName");
        loginPageLogoUrl  = (java.lang.String) props.get("loginPageLogoUrl");
        loginPageMessage  = (java.lang.String) props.get("loginPageMessage");
        registerPageMessage  = (java.lang.String) props.get("registerPageMessage");
        aboutPageMessage  = (java.lang.String) props.get("aboutPageMessage");
        userEmailMandatory  = (Boolean) props.get("userEmailMandatory");
        registrationDisabled  = (Boolean) props.get("registrationDisabled");
        projectCreationDisabled  = (Boolean) props.get("projectCreationDisabled");
        defaultUserPassword  = (java.lang.String) props.get("defaultUserPassword");
        openIdDisabled  = (Boolean) props.get("openIdDisabled");
        openIdDomains  = (java.lang.String) props.get("openIdDomains");
        versionCheckEnabled  = (Boolean) props.get("versionCheckEnabled");
        ldapEnabled  = (Boolean) props.get("ldapEnabled");
        ldapUrl  = (java.lang.String) props.get("ldapUrl");
        ldapUser  = (java.lang.String) props.get("ldapUser");
        ldapPassword  = (java.lang.String) props.get("ldapPassword");
        ldapBaseDn  = (java.lang.String) props.get("ldapBaseDn");
        ldapUserFilterRegex  = (java.lang.String) props.get("ldapUserFilterRegex");
        maxFileSize  = (java.lang.Integer) props.get("maxFileSize");
        subscriptionKeySeed  = (java.lang.String) props.get("subscriptionKeySeed");
        updateLocalModificationTime();
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("url", this.url);
        properties.put("adminEmail", this.adminEmail);
        properties.put("googleAnalyticsId", this.googleAnalyticsId);
        properties.put("smtpServer", this.smtpServer);
        properties.put("smtpPort", this.smtpPort);
        properties.put("smtpTls", this.smtpTls);
        properties.put("smtpUser", this.smtpUser);
        properties.put("smtpPassword", this.smtpPassword);
        properties.put("smtpFrom", this.smtpFrom);
        properties.put("instanceName", this.instanceName);
        properties.put("loginPageLogoUrl", this.loginPageLogoUrl);
        properties.put("loginPageMessage", this.loginPageMessage);
        properties.put("registerPageMessage", this.registerPageMessage);
        properties.put("aboutPageMessage", this.aboutPageMessage);
        properties.put("userEmailMandatory", this.userEmailMandatory);
        properties.put("registrationDisabled", this.registrationDisabled);
        properties.put("projectCreationDisabled", this.projectCreationDisabled);
        properties.put("defaultUserPassword", this.defaultUserPassword);
        properties.put("openIdDisabled", this.openIdDisabled);
        properties.put("openIdDomains", this.openIdDomains);
        properties.put("versionCheckEnabled", this.versionCheckEnabled);
        properties.put("ldapEnabled", this.ldapEnabled);
        properties.put("ldapUrl", this.ldapUrl);
        properties.put("ldapUser", this.ldapUser);
        properties.put("ldapPassword", this.ldapPassword);
        properties.put("ldapBaseDn", this.ldapBaseDn);
        properties.put("ldapUserFilterRegex", this.ldapUserFilterRegex);
        properties.put("maxFileSize", this.maxFileSize);
        properties.put("subscriptionKeySeed", this.subscriptionKeySeed);
    }

}