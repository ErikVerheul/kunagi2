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

public abstract class GSystemConfig
            extends AEntity
            implements ilarkesto.auth.ViewProtected<scrum.server.admin.User>, ilarkesto.auth.EditProtected<scrum.server.admin.User>, java.lang.Comparable<SystemConfig> {

    // --- AEntity ---

    public final scrum.server.admin.SystemConfigDao getDao() {
        return systemConfigDao;
    }

    protected void repairDeadDatob(ADatob datob) {
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

    public int compareTo(SystemConfig other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GSystemConfig.class);

    public static final String TYPE = "systemConfig";

    // -----------------------------------------------------------
    // - url
    // -----------------------------------------------------------

    private java.lang.String url;

    public final java.lang.String getUrl() {
        return url;
    }

    public final void setUrl(java.lang.String url) {
        url = prepareUrl(url);
        if (isUrl(url)) return;
        this.url = url;
        updateLastModified();
        fireModified("url="+url);
    }

    protected java.lang.String prepareUrl(java.lang.String url) {
        // url = StrExtend.removeUnreadableChars(url);
        return url;
    }

    public final boolean isUrlSet() {
        return this.url != null;
    }

    public final boolean isUrl(java.lang.String url) {
        if (this.url == null && url == null) return true;
        return this.url != null && this.url.equals(url);
    }

    protected final void updateUrl(Object value) {
        setUrl((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - adminEmail
    // -----------------------------------------------------------

    private java.lang.String adminEmail;

    public final java.lang.String getAdminEmail() {
        return adminEmail;
    }

    public final void setAdminEmail(java.lang.String adminEmail) {
        adminEmail = prepareAdminEmail(adminEmail);
        if (isAdminEmail(adminEmail)) return;
        this.adminEmail = adminEmail;
        updateLastModified();
        fireModified("adminEmail="+adminEmail);
    }

    protected java.lang.String prepareAdminEmail(java.lang.String adminEmail) {
        // adminEmail = StrExtend.removeUnreadableChars(adminEmail);
        return adminEmail;
    }

    public final boolean isAdminEmailSet() {
        return this.adminEmail != null;
    }

    public final boolean isAdminEmail(java.lang.String adminEmail) {
        if (this.adminEmail == null && adminEmail == null) return true;
        return this.adminEmail != null && this.adminEmail.equals(adminEmail);
    }

    protected final void updateAdminEmail(Object value) {
        setAdminEmail((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - googleAnalyticsId
    // -----------------------------------------------------------

    private java.lang.String googleAnalyticsId;

    public final java.lang.String getGoogleAnalyticsId() {
        return googleAnalyticsId;
    }

    public final void setGoogleAnalyticsId(java.lang.String googleAnalyticsId) {
        googleAnalyticsId = prepareGoogleAnalyticsId(googleAnalyticsId);
        if (isGoogleAnalyticsId(googleAnalyticsId)) return;
        this.googleAnalyticsId = googleAnalyticsId;
        updateLastModified();
        fireModified("googleAnalyticsId="+googleAnalyticsId);
    }

    protected java.lang.String prepareGoogleAnalyticsId(java.lang.String googleAnalyticsId) {
        // googleAnalyticsId = StrExtend.removeUnreadableChars(googleAnalyticsId);
        return googleAnalyticsId;
    }

    public final boolean isGoogleAnalyticsIdSet() {
        return this.googleAnalyticsId != null;
    }

    public final boolean isGoogleAnalyticsId(java.lang.String googleAnalyticsId) {
        if (this.googleAnalyticsId == null && googleAnalyticsId == null) return true;
        return this.googleAnalyticsId != null && this.googleAnalyticsId.equals(googleAnalyticsId);
    }

    protected final void updateGoogleAnalyticsId(Object value) {
        setGoogleAnalyticsId((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - smtpServer
    // -----------------------------------------------------------

    private java.lang.String smtpServer;

    public final java.lang.String getSmtpServer() {
        return smtpServer;
    }

    public final void setSmtpServer(java.lang.String smtpServer) {
        smtpServer = prepareSmtpServer(smtpServer);
        if (isSmtpServer(smtpServer)) return;
        this.smtpServer = smtpServer;
        updateLastModified();
        fireModified("smtpServer="+smtpServer);
    }

    protected java.lang.String prepareSmtpServer(java.lang.String smtpServer) {
        // smtpServer = StrExtend.removeUnreadableChars(smtpServer);
        return smtpServer;
    }

    public final boolean isSmtpServerSet() {
        return this.smtpServer != null;
    }

    public final boolean isSmtpServer(java.lang.String smtpServer) {
        if (this.smtpServer == null && smtpServer == null) return true;
        return this.smtpServer != null && this.smtpServer.equals(smtpServer);
    }

    protected final void updateSmtpServer(Object value) {
        setSmtpServer((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - smtpPort
    // -----------------------------------------------------------

    private java.lang.Integer smtpPort;

    public final java.lang.Integer getSmtpPort() {
        return smtpPort;
    }

    public final void setSmtpPort(java.lang.Integer smtpPort) {
        smtpPort = prepareSmtpPort(smtpPort);
        if (isSmtpPort(smtpPort)) return;
        this.smtpPort = smtpPort;
        updateLastModified();
        fireModified("smtpPort="+smtpPort);
    }

    protected java.lang.Integer prepareSmtpPort(java.lang.Integer smtpPort) {
        return smtpPort;
    }

    public final boolean isSmtpPortSet() {
        return this.smtpPort != null;
    }

    public final boolean isSmtpPort(java.lang.Integer smtpPort) {
        if (this.smtpPort == null && smtpPort == null) return true;
        return this.smtpPort != null && this.smtpPort.equals(smtpPort);
    }

    protected final void updateSmtpPort(Object value) {
        setSmtpPort((java.lang.Integer)value);
    }

    // -----------------------------------------------------------
    // - smtpTls
    // -----------------------------------------------------------

    private boolean smtpTls;

    public final boolean isSmtpTls() {
        return smtpTls;
    }

    public final void setSmtpTls(boolean smtpTls) {
        smtpTls = prepareSmtpTls(smtpTls);
        if (isSmtpTls(smtpTls)) return;
        this.smtpTls = smtpTls;
        updateLastModified();
        fireModified("smtpTls="+smtpTls);
    }

    protected boolean prepareSmtpTls(boolean smtpTls) {
        return smtpTls;
    }

    public final boolean isSmtpTls(boolean smtpTls) {
        return this.smtpTls == smtpTls;
    }

    protected final void updateSmtpTls(Object value) {
        setSmtpTls((Boolean)value);
    }

    // -----------------------------------------------------------
    // - smtpUser
    // -----------------------------------------------------------

    private java.lang.String smtpUser;

    public final java.lang.String getSmtpUser() {
        return smtpUser;
    }

    public final void setSmtpUser(java.lang.String smtpUser) {
        smtpUser = prepareSmtpUser(smtpUser);
        if (isSmtpUser(smtpUser)) return;
        this.smtpUser = smtpUser;
        updateLastModified();
        fireModified("smtpUser="+smtpUser);
    }

    protected java.lang.String prepareSmtpUser(java.lang.String smtpUser) {
        // smtpUser = StrExtend.removeUnreadableChars(smtpUser);
        return smtpUser;
    }

    public final boolean isSmtpUserSet() {
        return this.smtpUser != null;
    }

    public final boolean isSmtpUser(java.lang.String smtpUser) {
        if (this.smtpUser == null && smtpUser == null) return true;
        return this.smtpUser != null && this.smtpUser.equals(smtpUser);
    }

    protected final void updateSmtpUser(Object value) {
        setSmtpUser((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - smtpPassword
    // -----------------------------------------------------------

    private java.lang.String smtpPassword;

    public final java.lang.String getSmtpPassword() {
        return smtpPassword;
    }

    public final void setSmtpPassword(java.lang.String smtpPassword) {
        smtpPassword = prepareSmtpPassword(smtpPassword);
        if (isSmtpPassword(smtpPassword)) return;
        this.smtpPassword = smtpPassword;
        updateLastModified();
        fireModified("smtpPassword="+smtpPassword);
    }

    protected java.lang.String prepareSmtpPassword(java.lang.String smtpPassword) {
        // smtpPassword = StrExtend.removeUnreadableChars(smtpPassword);
        return smtpPassword;
    }

    public final boolean isSmtpPasswordSet() {
        return this.smtpPassword != null;
    }

    public final boolean isSmtpPassword(java.lang.String smtpPassword) {
        if (this.smtpPassword == null && smtpPassword == null) return true;
        return this.smtpPassword != null && this.smtpPassword.equals(smtpPassword);
    }

    protected final void updateSmtpPassword(Object value) {
        setSmtpPassword((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - smtpFrom
    // -----------------------------------------------------------

    private java.lang.String smtpFrom;

    public final java.lang.String getSmtpFrom() {
        return smtpFrom;
    }

    public final void setSmtpFrom(java.lang.String smtpFrom) {
        smtpFrom = prepareSmtpFrom(smtpFrom);
        if (isSmtpFrom(smtpFrom)) return;
        this.smtpFrom = smtpFrom;
        updateLastModified();
        fireModified("smtpFrom="+smtpFrom);
    }

    protected java.lang.String prepareSmtpFrom(java.lang.String smtpFrom) {
        // smtpFrom = StrExtend.removeUnreadableChars(smtpFrom);
        return smtpFrom;
    }

    public final boolean isSmtpFromSet() {
        return this.smtpFrom != null;
    }

    public final boolean isSmtpFrom(java.lang.String smtpFrom) {
        if (this.smtpFrom == null && smtpFrom == null) return true;
        return this.smtpFrom != null && this.smtpFrom.equals(smtpFrom);
    }

    protected final void updateSmtpFrom(Object value) {
        setSmtpFrom((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - instanceName
    // -----------------------------------------------------------

    private java.lang.String instanceName;

    public final java.lang.String getInstanceName() {
        return instanceName;
    }

    public final void setInstanceName(java.lang.String instanceName) {
        instanceName = prepareInstanceName(instanceName);
        if (isInstanceName(instanceName)) return;
        this.instanceName = instanceName;
        updateLastModified();
        fireModified("instanceName="+instanceName);
    }

    protected java.lang.String prepareInstanceName(java.lang.String instanceName) {
        // instanceName = StrExtend.removeUnreadableChars(instanceName);
        return instanceName;
    }

    public final boolean isInstanceNameSet() {
        return this.instanceName != null;
    }

    public final boolean isInstanceName(java.lang.String instanceName) {
        if (this.instanceName == null && instanceName == null) return true;
        return this.instanceName != null && this.instanceName.equals(instanceName);
    }

    protected final void updateInstanceName(Object value) {
        setInstanceName((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - loginPageLogoUrl
    // -----------------------------------------------------------

    private java.lang.String loginPageLogoUrl;

    public final java.lang.String getLoginPageLogoUrl() {
        return loginPageLogoUrl;
    }

    public final void setLoginPageLogoUrl(java.lang.String loginPageLogoUrl) {
        loginPageLogoUrl = prepareLoginPageLogoUrl(loginPageLogoUrl);
        if (isLoginPageLogoUrl(loginPageLogoUrl)) return;
        this.loginPageLogoUrl = loginPageLogoUrl;
        updateLastModified();
        fireModified("loginPageLogoUrl="+loginPageLogoUrl);
    }

    protected java.lang.String prepareLoginPageLogoUrl(java.lang.String loginPageLogoUrl) {
        // loginPageLogoUrl = StrExtend.removeUnreadableChars(loginPageLogoUrl);
        return loginPageLogoUrl;
    }

    public final boolean isLoginPageLogoUrlSet() {
        return this.loginPageLogoUrl != null;
    }

    public final boolean isLoginPageLogoUrl(java.lang.String loginPageLogoUrl) {
        if (this.loginPageLogoUrl == null && loginPageLogoUrl == null) return true;
        return this.loginPageLogoUrl != null && this.loginPageLogoUrl.equals(loginPageLogoUrl);
    }

    protected final void updateLoginPageLogoUrl(Object value) {
        setLoginPageLogoUrl((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - loginPageMessage
    // -----------------------------------------------------------

    private java.lang.String loginPageMessage;

    public final java.lang.String getLoginPageMessage() {
        return loginPageMessage;
    }

    public final void setLoginPageMessage(java.lang.String loginPageMessage) {
        loginPageMessage = prepareLoginPageMessage(loginPageMessage);
        if (isLoginPageMessage(loginPageMessage)) return;
        this.loginPageMessage = loginPageMessage;
        updateLastModified();
        fireModified("loginPageMessage="+loginPageMessage);
    }

    protected java.lang.String prepareLoginPageMessage(java.lang.String loginPageMessage) {
        // loginPageMessage = StrExtend.removeUnreadableChars(loginPageMessage);
        return loginPageMessage;
    }

    public final boolean isLoginPageMessageSet() {
        return this.loginPageMessage != null;
    }

    public final boolean isLoginPageMessage(java.lang.String loginPageMessage) {
        if (this.loginPageMessage == null && loginPageMessage == null) return true;
        return this.loginPageMessage != null && this.loginPageMessage.equals(loginPageMessage);
    }

    protected final void updateLoginPageMessage(Object value) {
        setLoginPageMessage((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - registerPageMessage
    // -----------------------------------------------------------

    private java.lang.String registerPageMessage;

    public final java.lang.String getRegisterPageMessage() {
        return registerPageMessage;
    }

    public final void setRegisterPageMessage(java.lang.String registerPageMessage) {
        registerPageMessage = prepareRegisterPageMessage(registerPageMessage);
        if (isRegisterPageMessage(registerPageMessage)) return;
        this.registerPageMessage = registerPageMessage;
        updateLastModified();
        fireModified("registerPageMessage="+registerPageMessage);
    }

    protected java.lang.String prepareRegisterPageMessage(java.lang.String registerPageMessage) {
        // registerPageMessage = StrExtend.removeUnreadableChars(registerPageMessage);
        return registerPageMessage;
    }

    public final boolean isRegisterPageMessageSet() {
        return this.registerPageMessage != null;
    }

    public final boolean isRegisterPageMessage(java.lang.String registerPageMessage) {
        if (this.registerPageMessage == null && registerPageMessage == null) return true;
        return this.registerPageMessage != null && this.registerPageMessage.equals(registerPageMessage);
    }

    protected final void updateRegisterPageMessage(Object value) {
        setRegisterPageMessage((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - aboutPageMessage
    // -----------------------------------------------------------

    private java.lang.String aboutPageMessage;

    public final java.lang.String getAboutPageMessage() {
        return aboutPageMessage;
    }

    public final void setAboutPageMessage(java.lang.String aboutPageMessage) {
        aboutPageMessage = prepareAboutPageMessage(aboutPageMessage);
        if (isAboutPageMessage(aboutPageMessage)) return;
        this.aboutPageMessage = aboutPageMessage;
        updateLastModified();
        fireModified("aboutPageMessage="+aboutPageMessage);
    }

    protected java.lang.String prepareAboutPageMessage(java.lang.String aboutPageMessage) {
        // aboutPageMessage = StrExtend.removeUnreadableChars(aboutPageMessage);
        return aboutPageMessage;
    }

    public final boolean isAboutPageMessageSet() {
        return this.aboutPageMessage != null;
    }

    public final boolean isAboutPageMessage(java.lang.String aboutPageMessage) {
        if (this.aboutPageMessage == null && aboutPageMessage == null) return true;
        return this.aboutPageMessage != null && this.aboutPageMessage.equals(aboutPageMessage);
    }

    protected final void updateAboutPageMessage(Object value) {
        setAboutPageMessage((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - userEmailMandatory
    // -----------------------------------------------------------

    private boolean userEmailMandatory;

    public final boolean isUserEmailMandatory() {
        return userEmailMandatory;
    }

    public final void setUserEmailMandatory(boolean userEmailMandatory) {
        userEmailMandatory = prepareUserEmailMandatory(userEmailMandatory);
        if (isUserEmailMandatory(userEmailMandatory)) return;
        this.userEmailMandatory = userEmailMandatory;
        updateLastModified();
        fireModified("userEmailMandatory="+userEmailMandatory);
    }

    protected boolean prepareUserEmailMandatory(boolean userEmailMandatory) {
        return userEmailMandatory;
    }

    public final boolean isUserEmailMandatory(boolean userEmailMandatory) {
        return this.userEmailMandatory == userEmailMandatory;
    }

    protected final void updateUserEmailMandatory(Object value) {
        setUserEmailMandatory((Boolean)value);
    }

    // -----------------------------------------------------------
    // - registrationDisabled
    // -----------------------------------------------------------

    private boolean registrationDisabled;

    public final boolean isRegistrationDisabled() {
        return registrationDisabled;
    }

    public final void setRegistrationDisabled(boolean registrationDisabled) {
        registrationDisabled = prepareRegistrationDisabled(registrationDisabled);
        if (isRegistrationDisabled(registrationDisabled)) return;
        this.registrationDisabled = registrationDisabled;
        updateLastModified();
        fireModified("registrationDisabled="+registrationDisabled);
    }

    protected boolean prepareRegistrationDisabled(boolean registrationDisabled) {
        return registrationDisabled;
    }

    public final boolean isRegistrationDisabled(boolean registrationDisabled) {
        return this.registrationDisabled == registrationDisabled;
    }

    protected final void updateRegistrationDisabled(Object value) {
        setRegistrationDisabled((Boolean)value);
    }

    // -----------------------------------------------------------
    // - projectCreationDisabled
    // -----------------------------------------------------------

    private boolean projectCreationDisabled;

    public final boolean isProjectCreationDisabled() {
        return projectCreationDisabled;
    }

    public final void setProjectCreationDisabled(boolean projectCreationDisabled) {
        projectCreationDisabled = prepareProjectCreationDisabled(projectCreationDisabled);
        if (isProjectCreationDisabled(projectCreationDisabled)) return;
        this.projectCreationDisabled = projectCreationDisabled;
        updateLastModified();
        fireModified("projectCreationDisabled="+projectCreationDisabled);
    }

    protected boolean prepareProjectCreationDisabled(boolean projectCreationDisabled) {
        return projectCreationDisabled;
    }

    public final boolean isProjectCreationDisabled(boolean projectCreationDisabled) {
        return this.projectCreationDisabled == projectCreationDisabled;
    }

    protected final void updateProjectCreationDisabled(Object value) {
        setProjectCreationDisabled((Boolean)value);
    }

    // -----------------------------------------------------------
    // - defaultUserPassword
    // -----------------------------------------------------------

    private java.lang.String defaultUserPassword;

    public final java.lang.String getDefaultUserPassword() {
        return defaultUserPassword;
    }

    public final void setDefaultUserPassword(java.lang.String defaultUserPassword) {
        defaultUserPassword = prepareDefaultUserPassword(defaultUserPassword);
        if (isDefaultUserPassword(defaultUserPassword)) return;
        this.defaultUserPassword = defaultUserPassword;
        updateLastModified();
        fireModified("defaultUserPassword="+defaultUserPassword);
    }

    protected java.lang.String prepareDefaultUserPassword(java.lang.String defaultUserPassword) {
        // defaultUserPassword = StrExtend.removeUnreadableChars(defaultUserPassword);
        return defaultUserPassword;
    }

    public final boolean isDefaultUserPasswordSet() {
        return this.defaultUserPassword != null;
    }

    public final boolean isDefaultUserPassword(java.lang.String defaultUserPassword) {
        if (this.defaultUserPassword == null && defaultUserPassword == null) return true;
        return this.defaultUserPassword != null && this.defaultUserPassword.equals(defaultUserPassword);
    }

    protected final void updateDefaultUserPassword(Object value) {
        setDefaultUserPassword((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - openIdDisabled
    // -----------------------------------------------------------

    private boolean openIdDisabled;

    public final boolean isOpenIdDisabled() {
        return openIdDisabled;
    }

    public final void setOpenIdDisabled(boolean openIdDisabled) {
        openIdDisabled = prepareOpenIdDisabled(openIdDisabled);
        if (isOpenIdDisabled(openIdDisabled)) return;
        this.openIdDisabled = openIdDisabled;
        updateLastModified();
        fireModified("openIdDisabled="+openIdDisabled);
    }

    protected boolean prepareOpenIdDisabled(boolean openIdDisabled) {
        return openIdDisabled;
    }

    public final boolean isOpenIdDisabled(boolean openIdDisabled) {
        return this.openIdDisabled == openIdDisabled;
    }

    protected final void updateOpenIdDisabled(Object value) {
        setOpenIdDisabled((Boolean)value);
    }

    // -----------------------------------------------------------
    // - openIdDomains
    // -----------------------------------------------------------

    private java.lang.String openIdDomains;

    public final java.lang.String getOpenIdDomains() {
        return openIdDomains;
    }

    public final void setOpenIdDomains(java.lang.String openIdDomains) {
        openIdDomains = prepareOpenIdDomains(openIdDomains);
        if (isOpenIdDomains(openIdDomains)) return;
        this.openIdDomains = openIdDomains;
        updateLastModified();
        fireModified("openIdDomains="+openIdDomains);
    }

    protected java.lang.String prepareOpenIdDomains(java.lang.String openIdDomains) {
        // openIdDomains = StrExtend.removeUnreadableChars(openIdDomains);
        return openIdDomains;
    }

    public final boolean isOpenIdDomainsSet() {
        return this.openIdDomains != null;
    }

    public final boolean isOpenIdDomains(java.lang.String openIdDomains) {
        if (this.openIdDomains == null && openIdDomains == null) return true;
        return this.openIdDomains != null && this.openIdDomains.equals(openIdDomains);
    }

    protected final void updateOpenIdDomains(Object value) {
        setOpenIdDomains((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - versionCheckEnabled
    // -----------------------------------------------------------

    private boolean versionCheckEnabled;

    public final boolean isVersionCheckEnabled() {
        return versionCheckEnabled;
    }

    public final void setVersionCheckEnabled(boolean versionCheckEnabled) {
        versionCheckEnabled = prepareVersionCheckEnabled(versionCheckEnabled);
        if (isVersionCheckEnabled(versionCheckEnabled)) return;
        this.versionCheckEnabled = versionCheckEnabled;
        updateLastModified();
        fireModified("versionCheckEnabled="+versionCheckEnabled);
    }

    protected boolean prepareVersionCheckEnabled(boolean versionCheckEnabled) {
        return versionCheckEnabled;
    }

    public final boolean isVersionCheckEnabled(boolean versionCheckEnabled) {
        return this.versionCheckEnabled == versionCheckEnabled;
    }

    protected final void updateVersionCheckEnabled(Object value) {
        setVersionCheckEnabled((Boolean)value);
    }

    // -----------------------------------------------------------
    // - ldapEnabled
    // -----------------------------------------------------------

    private boolean ldapEnabled;

    public final boolean isLdapEnabled() {
        return ldapEnabled;
    }

    public final void setLdapEnabled(boolean ldapEnabled) {
        ldapEnabled = prepareLdapEnabled(ldapEnabled);
        if (isLdapEnabled(ldapEnabled)) return;
        this.ldapEnabled = ldapEnabled;
        updateLastModified();
        fireModified("ldapEnabled="+ldapEnabled);
    }

    protected boolean prepareLdapEnabled(boolean ldapEnabled) {
        return ldapEnabled;
    }

    public final boolean isLdapEnabled(boolean ldapEnabled) {
        return this.ldapEnabled == ldapEnabled;
    }

    protected final void updateLdapEnabled(Object value) {
        setLdapEnabled((Boolean)value);
    }

    // -----------------------------------------------------------
    // - ldapUrl
    // -----------------------------------------------------------

    private java.lang.String ldapUrl;

    public final java.lang.String getLdapUrl() {
        return ldapUrl;
    }

    public final void setLdapUrl(java.lang.String ldapUrl) {
        ldapUrl = prepareLdapUrl(ldapUrl);
        if (isLdapUrl(ldapUrl)) return;
        this.ldapUrl = ldapUrl;
        updateLastModified();
        fireModified("ldapUrl="+ldapUrl);
    }

    protected java.lang.String prepareLdapUrl(java.lang.String ldapUrl) {
        // ldapUrl = StrExtend.removeUnreadableChars(ldapUrl);
        return ldapUrl;
    }

    public final boolean isLdapUrlSet() {
        return this.ldapUrl != null;
    }

    public final boolean isLdapUrl(java.lang.String ldapUrl) {
        if (this.ldapUrl == null && ldapUrl == null) return true;
        return this.ldapUrl != null && this.ldapUrl.equals(ldapUrl);
    }

    protected final void updateLdapUrl(Object value) {
        setLdapUrl((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - ldapUser
    // -----------------------------------------------------------

    private java.lang.String ldapUser;

    public final java.lang.String getLdapUser() {
        return ldapUser;
    }

    public final void setLdapUser(java.lang.String ldapUser) {
        ldapUser = prepareLdapUser(ldapUser);
        if (isLdapUser(ldapUser)) return;
        this.ldapUser = ldapUser;
        updateLastModified();
        fireModified("ldapUser="+ldapUser);
    }

    protected java.lang.String prepareLdapUser(java.lang.String ldapUser) {
        // ldapUser = StrExtend.removeUnreadableChars(ldapUser);
        return ldapUser;
    }

    public final boolean isLdapUserSet() {
        return this.ldapUser != null;
    }

    public final boolean isLdapUser(java.lang.String ldapUser) {
        if (this.ldapUser == null && ldapUser == null) return true;
        return this.ldapUser != null && this.ldapUser.equals(ldapUser);
    }

    protected final void updateLdapUser(Object value) {
        setLdapUser((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - ldapPassword
    // -----------------------------------------------------------

    private java.lang.String ldapPassword;

    public final java.lang.String getLdapPassword() {
        return ldapPassword;
    }

    public final void setLdapPassword(java.lang.String ldapPassword) {
        ldapPassword = prepareLdapPassword(ldapPassword);
        if (isLdapPassword(ldapPassword)) return;
        this.ldapPassword = ldapPassword;
        updateLastModified();
        fireModified("ldapPassword="+ldapPassword);
    }

    protected java.lang.String prepareLdapPassword(java.lang.String ldapPassword) {
        // ldapPassword = StrExtend.removeUnreadableChars(ldapPassword);
        return ldapPassword;
    }

    public final boolean isLdapPasswordSet() {
        return this.ldapPassword != null;
    }

    public final boolean isLdapPassword(java.lang.String ldapPassword) {
        if (this.ldapPassword == null && ldapPassword == null) return true;
        return this.ldapPassword != null && this.ldapPassword.equals(ldapPassword);
    }

    protected final void updateLdapPassword(Object value) {
        setLdapPassword((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - ldapBaseDn
    // -----------------------------------------------------------

    private java.lang.String ldapBaseDn;

    public final java.lang.String getLdapBaseDn() {
        return ldapBaseDn;
    }

    public final void setLdapBaseDn(java.lang.String ldapBaseDn) {
        ldapBaseDn = prepareLdapBaseDn(ldapBaseDn);
        if (isLdapBaseDn(ldapBaseDn)) return;
        this.ldapBaseDn = ldapBaseDn;
        updateLastModified();
        fireModified("ldapBaseDn="+ldapBaseDn);
    }

    protected java.lang.String prepareLdapBaseDn(java.lang.String ldapBaseDn) {
        // ldapBaseDn = StrExtend.removeUnreadableChars(ldapBaseDn);
        return ldapBaseDn;
    }

    public final boolean isLdapBaseDnSet() {
        return this.ldapBaseDn != null;
    }

    public final boolean isLdapBaseDn(java.lang.String ldapBaseDn) {
        if (this.ldapBaseDn == null && ldapBaseDn == null) return true;
        return this.ldapBaseDn != null && this.ldapBaseDn.equals(ldapBaseDn);
    }

    protected final void updateLdapBaseDn(Object value) {
        setLdapBaseDn((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - ldapUserFilterRegex
    // -----------------------------------------------------------

    private java.lang.String ldapUserFilterRegex;

    public final java.lang.String getLdapUserFilterRegex() {
        return ldapUserFilterRegex;
    }

    public final void setLdapUserFilterRegex(java.lang.String ldapUserFilterRegex) {
        ldapUserFilterRegex = prepareLdapUserFilterRegex(ldapUserFilterRegex);
        if (isLdapUserFilterRegex(ldapUserFilterRegex)) return;
        this.ldapUserFilterRegex = ldapUserFilterRegex;
        updateLastModified();
        fireModified("ldapUserFilterRegex="+ldapUserFilterRegex);
    }

    protected java.lang.String prepareLdapUserFilterRegex(java.lang.String ldapUserFilterRegex) {
        // ldapUserFilterRegex = StrExtend.removeUnreadableChars(ldapUserFilterRegex);
        return ldapUserFilterRegex;
    }

    public final boolean isLdapUserFilterRegexSet() {
        return this.ldapUserFilterRegex != null;
    }

    public final boolean isLdapUserFilterRegex(java.lang.String ldapUserFilterRegex) {
        if (this.ldapUserFilterRegex == null && ldapUserFilterRegex == null) return true;
        return this.ldapUserFilterRegex != null && this.ldapUserFilterRegex.equals(ldapUserFilterRegex);
    }

    protected final void updateLdapUserFilterRegex(Object value) {
        setLdapUserFilterRegex((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - maxFileSize
    // -----------------------------------------------------------

    private java.lang.Integer maxFileSize;

    public final java.lang.Integer getMaxFileSize() {
        return maxFileSize;
    }

    public final void setMaxFileSize(java.lang.Integer maxFileSize) {
        maxFileSize = prepareMaxFileSize(maxFileSize);
        if (isMaxFileSize(maxFileSize)) return;
        this.maxFileSize = maxFileSize;
        updateLastModified();
        fireModified("maxFileSize="+maxFileSize);
    }

    protected java.lang.Integer prepareMaxFileSize(java.lang.Integer maxFileSize) {
        return maxFileSize;
    }

    public final boolean isMaxFileSizeSet() {
        return this.maxFileSize != null;
    }

    public final boolean isMaxFileSize(java.lang.Integer maxFileSize) {
        if (this.maxFileSize == null && maxFileSize == null) return true;
        return this.maxFileSize != null && this.maxFileSize.equals(maxFileSize);
    }

    protected final void updateMaxFileSize(Object value) {
        setMaxFileSize((java.lang.Integer)value);
    }

    // -----------------------------------------------------------
    // - subscriptionKeySeed
    // -----------------------------------------------------------

    private java.lang.String subscriptionKeySeed;

    public final java.lang.String getSubscriptionKeySeed() {
        return subscriptionKeySeed;
    }

    public final void setSubscriptionKeySeed(java.lang.String subscriptionKeySeed) {
        subscriptionKeySeed = prepareSubscriptionKeySeed(subscriptionKeySeed);
        if (isSubscriptionKeySeed(subscriptionKeySeed)) return;
        this.subscriptionKeySeed = subscriptionKeySeed;
        updateLastModified();
        fireModified("subscriptionKeySeed="+subscriptionKeySeed);
    }

    protected java.lang.String prepareSubscriptionKeySeed(java.lang.String subscriptionKeySeed) {
        // subscriptionKeySeed = StrExtend.removeUnreadableChars(subscriptionKeySeed);
        return subscriptionKeySeed;
    }

    public final boolean isSubscriptionKeySeedSet() {
        return this.subscriptionKeySeed != null;
    }

    public final boolean isSubscriptionKeySeed(java.lang.String subscriptionKeySeed) {
        if (this.subscriptionKeySeed == null && subscriptionKeySeed == null) return true;
        return this.subscriptionKeySeed != null && this.subscriptionKeySeed.equals(subscriptionKeySeed);
    }

    protected final void updateSubscriptionKeySeed(Object value) {
        setSubscriptionKeySeed((java.lang.String)value);
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("url")) updateUrl(value);
            if (property.equals("adminEmail")) updateAdminEmail(value);
            if (property.equals("googleAnalyticsId")) updateGoogleAnalyticsId(value);
            if (property.equals("smtpServer")) updateSmtpServer(value);
            if (property.equals("smtpPort")) updateSmtpPort(value);
            if (property.equals("smtpTls")) updateSmtpTls(value);
            if (property.equals("smtpUser")) updateSmtpUser(value);
            if (property.equals("smtpPassword")) updateSmtpPassword(value);
            if (property.equals("smtpFrom")) updateSmtpFrom(value);
            if (property.equals("instanceName")) updateInstanceName(value);
            if (property.equals("loginPageLogoUrl")) updateLoginPageLogoUrl(value);
            if (property.equals("loginPageMessage")) updateLoginPageMessage(value);
            if (property.equals("registerPageMessage")) updateRegisterPageMessage(value);
            if (property.equals("aboutPageMessage")) updateAboutPageMessage(value);
            if (property.equals("userEmailMandatory")) updateUserEmailMandatory(value);
            if (property.equals("registrationDisabled")) updateRegistrationDisabled(value);
            if (property.equals("projectCreationDisabled")) updateProjectCreationDisabled(value);
            if (property.equals("defaultUserPassword")) updateDefaultUserPassword(value);
            if (property.equals("openIdDisabled")) updateOpenIdDisabled(value);
            if (property.equals("openIdDomains")) updateOpenIdDomains(value);
            if (property.equals("versionCheckEnabled")) updateVersionCheckEnabled(value);
            if (property.equals("ldapEnabled")) updateLdapEnabled(value);
            if (property.equals("ldapUrl")) updateLdapUrl(value);
            if (property.equals("ldapUser")) updateLdapUser(value);
            if (property.equals("ldapPassword")) updateLdapPassword(value);
            if (property.equals("ldapBaseDn")) updateLdapBaseDn(value);
            if (property.equals("ldapUserFilterRegex")) updateLdapUserFilterRegex(value);
            if (property.equals("maxFileSize")) updateMaxFileSize(value);
            if (property.equals("subscriptionKeySeed")) updateSubscriptionKeySeed(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
    }

    // --- ensure integrity ---

    public void ensureIntegrity() {
        super.ensureIntegrity();
    }

    static scrum.server.admin.SystemConfigDao systemConfigDao;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings("URF_UNREAD_FIELD")
    public static final void setSystemConfigDao(scrum.server.admin.SystemConfigDao systemConfigDao) {
        GSystemConfig.systemConfigDao = systemConfigDao;
    }

}