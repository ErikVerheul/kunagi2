// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.DaoGenerator










package scrum.server.admin;

import java.util.*;
import ilarkesto.core.logging.Log;
import ilarkesto.auth.Auth;
import ilarkesto.base.Cache;
import ilarkesto.persistence.EntityEvent;
import ilarkesto.fp.Predicate;

public abstract class GSystemConfigDao
            extends ilarkesto.persistence.ADao<SystemConfig> {

    public final String getEntityName() {
        return SystemConfig.TYPE;
    }

    public final Class getEntityClass() {
        return SystemConfig.class;
    }

    public Set<SystemConfig> getEntitiesVisibleForUser(final scrum.server.admin.User user) {
        return getEntities(new Predicate<SystemConfig>() {
            public boolean test(SystemConfig e) {
                return Auth.isVisible(e, user);
            }
        });
    }

    // --- clear caches ---
    public void clearCaches() {
        systemConfigsByUrlCache.clear();
        urlsCache = null;
        systemConfigsByAdminEmailCache.clear();
        adminEmailsCache = null;
        systemConfigsByGoogleAnalyticsIdCache.clear();
        googleAnalyticsIdsCache = null;
        systemConfigsBySmtpServerCache.clear();
        smtpServersCache = null;
        systemConfigsBySmtpPortCache.clear();
        smtpPortsCache = null;
        systemConfigsBySmtpTlsCache.clear();
        systemConfigsBySmtpUserCache.clear();
        smtpUsersCache = null;
        systemConfigsBySmtpPasswordCache.clear();
        smtpPasswordsCache = null;
        systemConfigsBySmtpFromCache.clear();
        smtpFromsCache = null;
        systemConfigsByInstanceNameCache.clear();
        instanceNamesCache = null;
        systemConfigsByLoginPageLogoUrlCache.clear();
        loginPageLogoUrlsCache = null;
        systemConfigsByLoginPageMessageCache.clear();
        loginPageMessagesCache = null;
        systemConfigsByRegisterPageMessageCache.clear();
        registerPageMessagesCache = null;
        systemConfigsByAboutPageMessageCache.clear();
        aboutPageMessagesCache = null;
        systemConfigsByUserEmailMandatoryCache.clear();
        systemConfigsByRegistrationDisabledCache.clear();
        systemConfigsByProjectCreationDisabledCache.clear();
        systemConfigsByDefaultUserPasswordCache.clear();
        defaultUserPasswordsCache = null;
        systemConfigsByOpenIdDisabledCache.clear();
        systemConfigsByOpenIdDomainsCache.clear();
        openIdDomainssCache = null;
        systemConfigsByVersionCheckEnabledCache.clear();
        systemConfigsByLdapEnabledCache.clear();
        systemConfigsByLdapUrlCache.clear();
        ldapUrlsCache = null;
        systemConfigsByLdapUserCache.clear();
        ldapUsersCache = null;
        systemConfigsByLdapPasswordCache.clear();
        ldapPasswordsCache = null;
        systemConfigsByLdapBaseDnCache.clear();
        ldapBaseDnsCache = null;
        systemConfigsByLdapUserFilterRegexCache.clear();
        ldapUserFilterRegexsCache = null;
        systemConfigsByMaxFileSizeCache.clear();
        maxFileSizesCache = null;
        systemConfigsBySubscriptionKeySeedCache.clear();
        subscriptionKeySeedsCache = null;
    }

    @Override
    public void entityDeleted(EntityEvent event) {
        super.entityDeleted(event);
        if (event.getEntity() instanceof SystemConfig) {
            clearCaches();
        }
    }

    @Override
    public void entitySaved(EntityEvent event) {
        super.entitySaved(event);
        if (event.getEntity() instanceof SystemConfig) {
            clearCaches();
        }
    }

    // -----------------------------------------------------------
    // - url
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByUrlCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String url) {
                    return getEntities(new IsUrl(url));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByUrl(java.lang.String url) {
        return new HashSet<SystemConfig>(systemConfigsByUrlCache.get(url));
    }
    private Set<java.lang.String> urlsCache;

    public final Set<java.lang.String> getUrls() {
        if (urlsCache == null) {
            urlsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isUrlSet()) urlsCache.add(e.getUrl());
            }
        }
        return urlsCache;
    }

    private static class IsUrl implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsUrl(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isUrl(value);
        }

    }

    // -----------------------------------------------------------
    // - adminEmail
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByAdminEmailCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String adminEmail) {
                    return getEntities(new IsAdminEmail(adminEmail));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByAdminEmail(java.lang.String adminEmail) {
        return new HashSet<SystemConfig>(systemConfigsByAdminEmailCache.get(adminEmail));
    }
    private Set<java.lang.String> adminEmailsCache;

    public final Set<java.lang.String> getAdminEmails() {
        if (adminEmailsCache == null) {
            adminEmailsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isAdminEmailSet()) adminEmailsCache.add(e.getAdminEmail());
            }
        }
        return adminEmailsCache;
    }

    private static class IsAdminEmail implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsAdminEmail(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isAdminEmail(value);
        }

    }

    // -----------------------------------------------------------
    // - googleAnalyticsId
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByGoogleAnalyticsIdCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String googleAnalyticsId) {
                    return getEntities(new IsGoogleAnalyticsId(googleAnalyticsId));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByGoogleAnalyticsId(java.lang.String googleAnalyticsId) {
        return new HashSet<SystemConfig>(systemConfigsByGoogleAnalyticsIdCache.get(googleAnalyticsId));
    }
    private Set<java.lang.String> googleAnalyticsIdsCache;

    public final Set<java.lang.String> getGoogleAnalyticsIds() {
        if (googleAnalyticsIdsCache == null) {
            googleAnalyticsIdsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isGoogleAnalyticsIdSet()) googleAnalyticsIdsCache.add(e.getGoogleAnalyticsId());
            }
        }
        return googleAnalyticsIdsCache;
    }

    private static class IsGoogleAnalyticsId implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsGoogleAnalyticsId(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isGoogleAnalyticsId(value);
        }

    }

    // -----------------------------------------------------------
    // - smtpServer
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsBySmtpServerCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String smtpServer) {
                    return getEntities(new IsSmtpServer(smtpServer));
                }
            });

    public final Set<SystemConfig> getSystemConfigsBySmtpServer(java.lang.String smtpServer) {
        return new HashSet<SystemConfig>(systemConfigsBySmtpServerCache.get(smtpServer));
    }
    private Set<java.lang.String> smtpServersCache;

    public final Set<java.lang.String> getSmtpServers() {
        if (smtpServersCache == null) {
            smtpServersCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isSmtpServerSet()) smtpServersCache.add(e.getSmtpServer());
            }
        }
        return smtpServersCache;
    }

    private static class IsSmtpServer implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsSmtpServer(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isSmtpServer(value);
        }

    }

    // -----------------------------------------------------------
    // - smtpPort
    // -----------------------------------------------------------

    private final Cache<java.lang.Integer,Set<SystemConfig>> systemConfigsBySmtpPortCache = new Cache<java.lang.Integer,Set<SystemConfig>>(
            new Cache.Factory<java.lang.Integer,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.Integer smtpPort) {
                    return getEntities(new IsSmtpPort(smtpPort));
                }
            });

    public final Set<SystemConfig> getSystemConfigsBySmtpPort(java.lang.Integer smtpPort) {
        return new HashSet<SystemConfig>(systemConfigsBySmtpPortCache.get(smtpPort));
    }
    private Set<java.lang.Integer> smtpPortsCache;

    public final Set<java.lang.Integer> getSmtpPorts() {
        if (smtpPortsCache == null) {
            smtpPortsCache = new HashSet<java.lang.Integer>();
            for (SystemConfig e : getEntities()) {
                if (e.isSmtpPortSet()) smtpPortsCache.add(e.getSmtpPort());
            }
        }
        return smtpPortsCache;
    }

    private static class IsSmtpPort implements Predicate<SystemConfig> {

        private java.lang.Integer value;

        public IsSmtpPort(java.lang.Integer value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isSmtpPort(value);
        }

    }

    // -----------------------------------------------------------
    // - smtpTls
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<SystemConfig>> systemConfigsBySmtpTlsCache = new Cache<Boolean,Set<SystemConfig>>(
            new Cache.Factory<Boolean,Set<SystemConfig>>() {
                public Set<SystemConfig> create(Boolean smtpTls) {
                    return getEntities(new IsSmtpTls(smtpTls));
                }
            });

    public final Set<SystemConfig> getSystemConfigsBySmtpTls(boolean smtpTls) {
        return new HashSet<SystemConfig>(systemConfigsBySmtpTlsCache.get(smtpTls));
    }

    private static class IsSmtpTls implements Predicate<SystemConfig> {

        private boolean value;

        public IsSmtpTls(boolean value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return value == e.isSmtpTls();
        }

    }

    // -----------------------------------------------------------
    // - smtpUser
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsBySmtpUserCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String smtpUser) {
                    return getEntities(new IsSmtpUser(smtpUser));
                }
            });

    public final Set<SystemConfig> getSystemConfigsBySmtpUser(java.lang.String smtpUser) {
        return new HashSet<SystemConfig>(systemConfigsBySmtpUserCache.get(smtpUser));
    }
    private Set<java.lang.String> smtpUsersCache;

    public final Set<java.lang.String> getSmtpUsers() {
        if (smtpUsersCache == null) {
            smtpUsersCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isSmtpUserSet()) smtpUsersCache.add(e.getSmtpUser());
            }
        }
        return smtpUsersCache;
    }

    private static class IsSmtpUser implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsSmtpUser(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isSmtpUser(value);
        }

    }

    // -----------------------------------------------------------
    // - smtpPassword
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsBySmtpPasswordCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String smtpPassword) {
                    return getEntities(new IsSmtpPassword(smtpPassword));
                }
            });

    public final Set<SystemConfig> getSystemConfigsBySmtpPassword(java.lang.String smtpPassword) {
        return new HashSet<SystemConfig>(systemConfigsBySmtpPasswordCache.get(smtpPassword));
    }
    private Set<java.lang.String> smtpPasswordsCache;

    public final Set<java.lang.String> getSmtpPasswords() {
        if (smtpPasswordsCache == null) {
            smtpPasswordsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isSmtpPasswordSet()) smtpPasswordsCache.add(e.getSmtpPassword());
            }
        }
        return smtpPasswordsCache;
    }

    private static class IsSmtpPassword implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsSmtpPassword(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isSmtpPassword(value);
        }

    }

    // -----------------------------------------------------------
    // - smtpFrom
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsBySmtpFromCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String smtpFrom) {
                    return getEntities(new IsSmtpFrom(smtpFrom));
                }
            });

    public final Set<SystemConfig> getSystemConfigsBySmtpFrom(java.lang.String smtpFrom) {
        return new HashSet<SystemConfig>(systemConfigsBySmtpFromCache.get(smtpFrom));
    }
    private Set<java.lang.String> smtpFromsCache;

    public final Set<java.lang.String> getSmtpFroms() {
        if (smtpFromsCache == null) {
            smtpFromsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isSmtpFromSet()) smtpFromsCache.add(e.getSmtpFrom());
            }
        }
        return smtpFromsCache;
    }

    private static class IsSmtpFrom implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsSmtpFrom(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isSmtpFrom(value);
        }

    }

    // -----------------------------------------------------------
    // - instanceName
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByInstanceNameCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String instanceName) {
                    return getEntities(new IsInstanceName(instanceName));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByInstanceName(java.lang.String instanceName) {
        return new HashSet<SystemConfig>(systemConfigsByInstanceNameCache.get(instanceName));
    }
    private Set<java.lang.String> instanceNamesCache;

    public final Set<java.lang.String> getInstanceNames() {
        if (instanceNamesCache == null) {
            instanceNamesCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isInstanceNameSet()) instanceNamesCache.add(e.getInstanceName());
            }
        }
        return instanceNamesCache;
    }

    private static class IsInstanceName implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsInstanceName(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isInstanceName(value);
        }

    }

    // -----------------------------------------------------------
    // - loginPageLogoUrl
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByLoginPageLogoUrlCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String loginPageLogoUrl) {
                    return getEntities(new IsLoginPageLogoUrl(loginPageLogoUrl));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByLoginPageLogoUrl(java.lang.String loginPageLogoUrl) {
        return new HashSet<SystemConfig>(systemConfigsByLoginPageLogoUrlCache.get(loginPageLogoUrl));
    }
    private Set<java.lang.String> loginPageLogoUrlsCache;

    public final Set<java.lang.String> getLoginPageLogoUrls() {
        if (loginPageLogoUrlsCache == null) {
            loginPageLogoUrlsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isLoginPageLogoUrlSet()) loginPageLogoUrlsCache.add(e.getLoginPageLogoUrl());
            }
        }
        return loginPageLogoUrlsCache;
    }

    private static class IsLoginPageLogoUrl implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsLoginPageLogoUrl(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isLoginPageLogoUrl(value);
        }

    }

    // -----------------------------------------------------------
    // - loginPageMessage
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByLoginPageMessageCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String loginPageMessage) {
                    return getEntities(new IsLoginPageMessage(loginPageMessage));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByLoginPageMessage(java.lang.String loginPageMessage) {
        return new HashSet<SystemConfig>(systemConfigsByLoginPageMessageCache.get(loginPageMessage));
    }
    private Set<java.lang.String> loginPageMessagesCache;

    public final Set<java.lang.String> getLoginPageMessages() {
        if (loginPageMessagesCache == null) {
            loginPageMessagesCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isLoginPageMessageSet()) loginPageMessagesCache.add(e.getLoginPageMessage());
            }
        }
        return loginPageMessagesCache;
    }

    private static class IsLoginPageMessage implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsLoginPageMessage(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isLoginPageMessage(value);
        }

    }

    // -----------------------------------------------------------
    // - registerPageMessage
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByRegisterPageMessageCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String registerPageMessage) {
                    return getEntities(new IsRegisterPageMessage(registerPageMessage));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByRegisterPageMessage(java.lang.String registerPageMessage) {
        return new HashSet<SystemConfig>(systemConfigsByRegisterPageMessageCache.get(registerPageMessage));
    }
    private Set<java.lang.String> registerPageMessagesCache;

    public final Set<java.lang.String> getRegisterPageMessages() {
        if (registerPageMessagesCache == null) {
            registerPageMessagesCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isRegisterPageMessageSet()) registerPageMessagesCache.add(e.getRegisterPageMessage());
            }
        }
        return registerPageMessagesCache;
    }

    private static class IsRegisterPageMessage implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsRegisterPageMessage(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isRegisterPageMessage(value);
        }

    }

    // -----------------------------------------------------------
    // - aboutPageMessage
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByAboutPageMessageCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String aboutPageMessage) {
                    return getEntities(new IsAboutPageMessage(aboutPageMessage));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByAboutPageMessage(java.lang.String aboutPageMessage) {
        return new HashSet<SystemConfig>(systemConfigsByAboutPageMessageCache.get(aboutPageMessage));
    }
    private Set<java.lang.String> aboutPageMessagesCache;

    public final Set<java.lang.String> getAboutPageMessages() {
        if (aboutPageMessagesCache == null) {
            aboutPageMessagesCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isAboutPageMessageSet()) aboutPageMessagesCache.add(e.getAboutPageMessage());
            }
        }
        return aboutPageMessagesCache;
    }

    private static class IsAboutPageMessage implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsAboutPageMessage(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isAboutPageMessage(value);
        }

    }

    // -----------------------------------------------------------
    // - userEmailMandatory
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<SystemConfig>> systemConfigsByUserEmailMandatoryCache = new Cache<Boolean,Set<SystemConfig>>(
            new Cache.Factory<Boolean,Set<SystemConfig>>() {
                public Set<SystemConfig> create(Boolean userEmailMandatory) {
                    return getEntities(new IsUserEmailMandatory(userEmailMandatory));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByUserEmailMandatory(boolean userEmailMandatory) {
        return new HashSet<SystemConfig>(systemConfigsByUserEmailMandatoryCache.get(userEmailMandatory));
    }

    private static class IsUserEmailMandatory implements Predicate<SystemConfig> {

        private boolean value;

        public IsUserEmailMandatory(boolean value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return value == e.isUserEmailMandatory();
        }

    }

    // -----------------------------------------------------------
    // - registrationDisabled
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<SystemConfig>> systemConfigsByRegistrationDisabledCache = new Cache<Boolean,Set<SystemConfig>>(
            new Cache.Factory<Boolean,Set<SystemConfig>>() {
                public Set<SystemConfig> create(Boolean registrationDisabled) {
                    return getEntities(new IsRegistrationDisabled(registrationDisabled));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByRegistrationDisabled(boolean registrationDisabled) {
        return new HashSet<SystemConfig>(systemConfigsByRegistrationDisabledCache.get(registrationDisabled));
    }

    private static class IsRegistrationDisabled implements Predicate<SystemConfig> {

        private boolean value;

        public IsRegistrationDisabled(boolean value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return value == e.isRegistrationDisabled();
        }

    }

    // -----------------------------------------------------------
    // - projectCreationDisabled
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<SystemConfig>> systemConfigsByProjectCreationDisabledCache = new Cache<Boolean,Set<SystemConfig>>(
            new Cache.Factory<Boolean,Set<SystemConfig>>() {
                public Set<SystemConfig> create(Boolean projectCreationDisabled) {
                    return getEntities(new IsProjectCreationDisabled(projectCreationDisabled));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByProjectCreationDisabled(boolean projectCreationDisabled) {
        return new HashSet<SystemConfig>(systemConfigsByProjectCreationDisabledCache.get(projectCreationDisabled));
    }

    private static class IsProjectCreationDisabled implements Predicate<SystemConfig> {

        private boolean value;

        public IsProjectCreationDisabled(boolean value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return value == e.isProjectCreationDisabled();
        }

    }

    // -----------------------------------------------------------
    // - defaultUserPassword
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByDefaultUserPasswordCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String defaultUserPassword) {
                    return getEntities(new IsDefaultUserPassword(defaultUserPassword));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByDefaultUserPassword(java.lang.String defaultUserPassword) {
        return new HashSet<SystemConfig>(systemConfigsByDefaultUserPasswordCache.get(defaultUserPassword));
    }
    private Set<java.lang.String> defaultUserPasswordsCache;

    public final Set<java.lang.String> getDefaultUserPasswords() {
        if (defaultUserPasswordsCache == null) {
            defaultUserPasswordsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isDefaultUserPasswordSet()) defaultUserPasswordsCache.add(e.getDefaultUserPassword());
            }
        }
        return defaultUserPasswordsCache;
    }

    private static class IsDefaultUserPassword implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsDefaultUserPassword(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isDefaultUserPassword(value);
        }

    }

    // -----------------------------------------------------------
    // - openIdDisabled
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<SystemConfig>> systemConfigsByOpenIdDisabledCache = new Cache<Boolean,Set<SystemConfig>>(
            new Cache.Factory<Boolean,Set<SystemConfig>>() {
                public Set<SystemConfig> create(Boolean openIdDisabled) {
                    return getEntities(new IsOpenIdDisabled(openIdDisabled));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByOpenIdDisabled(boolean openIdDisabled) {
        return new HashSet<SystemConfig>(systemConfigsByOpenIdDisabledCache.get(openIdDisabled));
    }

    private static class IsOpenIdDisabled implements Predicate<SystemConfig> {

        private boolean value;

        public IsOpenIdDisabled(boolean value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return value == e.isOpenIdDisabled();
        }

    }

    // -----------------------------------------------------------
    // - openIdDomains
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByOpenIdDomainsCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String openIdDomains) {
                    return getEntities(new IsOpenIdDomains(openIdDomains));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByOpenIdDomains(java.lang.String openIdDomains) {
        return new HashSet<SystemConfig>(systemConfigsByOpenIdDomainsCache.get(openIdDomains));
    }
    private Set<java.lang.String> openIdDomainssCache;

    public final Set<java.lang.String> getOpenIdDomainss() {
        if (openIdDomainssCache == null) {
            openIdDomainssCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isOpenIdDomainsSet()) openIdDomainssCache.add(e.getOpenIdDomains());
            }
        }
        return openIdDomainssCache;
    }

    private static class IsOpenIdDomains implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsOpenIdDomains(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isOpenIdDomains(value);
        }

    }

    // -----------------------------------------------------------
    // - versionCheckEnabled
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<SystemConfig>> systemConfigsByVersionCheckEnabledCache = new Cache<Boolean,Set<SystemConfig>>(
            new Cache.Factory<Boolean,Set<SystemConfig>>() {
                public Set<SystemConfig> create(Boolean versionCheckEnabled) {
                    return getEntities(new IsVersionCheckEnabled(versionCheckEnabled));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByVersionCheckEnabled(boolean versionCheckEnabled) {
        return new HashSet<SystemConfig>(systemConfigsByVersionCheckEnabledCache.get(versionCheckEnabled));
    }

    private static class IsVersionCheckEnabled implements Predicate<SystemConfig> {

        private boolean value;

        public IsVersionCheckEnabled(boolean value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return value == e.isVersionCheckEnabled();
        }

    }

    // -----------------------------------------------------------
    // - ldapEnabled
    // -----------------------------------------------------------

    private final Cache<Boolean,Set<SystemConfig>> systemConfigsByLdapEnabledCache = new Cache<Boolean,Set<SystemConfig>>(
            new Cache.Factory<Boolean,Set<SystemConfig>>() {
                public Set<SystemConfig> create(Boolean ldapEnabled) {
                    return getEntities(new IsLdapEnabled(ldapEnabled));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByLdapEnabled(boolean ldapEnabled) {
        return new HashSet<SystemConfig>(systemConfigsByLdapEnabledCache.get(ldapEnabled));
    }

    private static class IsLdapEnabled implements Predicate<SystemConfig> {

        private boolean value;

        public IsLdapEnabled(boolean value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return value == e.isLdapEnabled();
        }

    }

    // -----------------------------------------------------------
    // - ldapUrl
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByLdapUrlCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String ldapUrl) {
                    return getEntities(new IsLdapUrl(ldapUrl));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByLdapUrl(java.lang.String ldapUrl) {
        return new HashSet<SystemConfig>(systemConfigsByLdapUrlCache.get(ldapUrl));
    }
    private Set<java.lang.String> ldapUrlsCache;

    public final Set<java.lang.String> getLdapUrls() {
        if (ldapUrlsCache == null) {
            ldapUrlsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isLdapUrlSet()) ldapUrlsCache.add(e.getLdapUrl());
            }
        }
        return ldapUrlsCache;
    }

    private static class IsLdapUrl implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsLdapUrl(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isLdapUrl(value);
        }

    }

    // -----------------------------------------------------------
    // - ldapUser
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByLdapUserCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String ldapUser) {
                    return getEntities(new IsLdapUser(ldapUser));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByLdapUser(java.lang.String ldapUser) {
        return new HashSet<SystemConfig>(systemConfigsByLdapUserCache.get(ldapUser));
    }
    private Set<java.lang.String> ldapUsersCache;

    public final Set<java.lang.String> getLdapUsers() {
        if (ldapUsersCache == null) {
            ldapUsersCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isLdapUserSet()) ldapUsersCache.add(e.getLdapUser());
            }
        }
        return ldapUsersCache;
    }

    private static class IsLdapUser implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsLdapUser(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isLdapUser(value);
        }

    }

    // -----------------------------------------------------------
    // - ldapPassword
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByLdapPasswordCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String ldapPassword) {
                    return getEntities(new IsLdapPassword(ldapPassword));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByLdapPassword(java.lang.String ldapPassword) {
        return new HashSet<SystemConfig>(systemConfigsByLdapPasswordCache.get(ldapPassword));
    }
    private Set<java.lang.String> ldapPasswordsCache;

    public final Set<java.lang.String> getLdapPasswords() {
        if (ldapPasswordsCache == null) {
            ldapPasswordsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isLdapPasswordSet()) ldapPasswordsCache.add(e.getLdapPassword());
            }
        }
        return ldapPasswordsCache;
    }

    private static class IsLdapPassword implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsLdapPassword(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isLdapPassword(value);
        }

    }

    // -----------------------------------------------------------
    // - ldapBaseDn
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByLdapBaseDnCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String ldapBaseDn) {
                    return getEntities(new IsLdapBaseDn(ldapBaseDn));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByLdapBaseDn(java.lang.String ldapBaseDn) {
        return new HashSet<SystemConfig>(systemConfigsByLdapBaseDnCache.get(ldapBaseDn));
    }
    private Set<java.lang.String> ldapBaseDnsCache;

    public final Set<java.lang.String> getLdapBaseDns() {
        if (ldapBaseDnsCache == null) {
            ldapBaseDnsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isLdapBaseDnSet()) ldapBaseDnsCache.add(e.getLdapBaseDn());
            }
        }
        return ldapBaseDnsCache;
    }

    private static class IsLdapBaseDn implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsLdapBaseDn(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isLdapBaseDn(value);
        }

    }

    // -----------------------------------------------------------
    // - ldapUserFilterRegex
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsByLdapUserFilterRegexCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String ldapUserFilterRegex) {
                    return getEntities(new IsLdapUserFilterRegex(ldapUserFilterRegex));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByLdapUserFilterRegex(java.lang.String ldapUserFilterRegex) {
        return new HashSet<SystemConfig>(systemConfigsByLdapUserFilterRegexCache.get(ldapUserFilterRegex));
    }
    private Set<java.lang.String> ldapUserFilterRegexsCache;

    public final Set<java.lang.String> getLdapUserFilterRegexs() {
        if (ldapUserFilterRegexsCache == null) {
            ldapUserFilterRegexsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isLdapUserFilterRegexSet()) ldapUserFilterRegexsCache.add(e.getLdapUserFilterRegex());
            }
        }
        return ldapUserFilterRegexsCache;
    }

    private static class IsLdapUserFilterRegex implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsLdapUserFilterRegex(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isLdapUserFilterRegex(value);
        }

    }

    // -----------------------------------------------------------
    // - maxFileSize
    // -----------------------------------------------------------

    private final Cache<java.lang.Integer,Set<SystemConfig>> systemConfigsByMaxFileSizeCache = new Cache<java.lang.Integer,Set<SystemConfig>>(
            new Cache.Factory<java.lang.Integer,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.Integer maxFileSize) {
                    return getEntities(new IsMaxFileSize(maxFileSize));
                }
            });

    public final Set<SystemConfig> getSystemConfigsByMaxFileSize(java.lang.Integer maxFileSize) {
        return new HashSet<SystemConfig>(systemConfigsByMaxFileSizeCache.get(maxFileSize));
    }
    private Set<java.lang.Integer> maxFileSizesCache;

    public final Set<java.lang.Integer> getMaxFileSizes() {
        if (maxFileSizesCache == null) {
            maxFileSizesCache = new HashSet<java.lang.Integer>();
            for (SystemConfig e : getEntities()) {
                if (e.isMaxFileSizeSet()) maxFileSizesCache.add(e.getMaxFileSize());
            }
        }
        return maxFileSizesCache;
    }

    private static class IsMaxFileSize implements Predicate<SystemConfig> {

        private java.lang.Integer value;

        public IsMaxFileSize(java.lang.Integer value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isMaxFileSize(value);
        }

    }

    // -----------------------------------------------------------
    // - subscriptionKeySeed
    // -----------------------------------------------------------

    private final Cache<java.lang.String,Set<SystemConfig>> systemConfigsBySubscriptionKeySeedCache = new Cache<java.lang.String,Set<SystemConfig>>(
            new Cache.Factory<java.lang.String,Set<SystemConfig>>() {
                public Set<SystemConfig> create(java.lang.String subscriptionKeySeed) {
                    return getEntities(new IsSubscriptionKeySeed(subscriptionKeySeed));
                }
            });

    public final Set<SystemConfig> getSystemConfigsBySubscriptionKeySeed(java.lang.String subscriptionKeySeed) {
        return new HashSet<SystemConfig>(systemConfigsBySubscriptionKeySeedCache.get(subscriptionKeySeed));
    }
    private Set<java.lang.String> subscriptionKeySeedsCache;

    public final Set<java.lang.String> getSubscriptionKeySeeds() {
        if (subscriptionKeySeedsCache == null) {
            subscriptionKeySeedsCache = new HashSet<java.lang.String>();
            for (SystemConfig e : getEntities()) {
                if (e.isSubscriptionKeySeedSet()) subscriptionKeySeedsCache.add(e.getSubscriptionKeySeed());
            }
        }
        return subscriptionKeySeedsCache;
    }

    private static class IsSubscriptionKeySeed implements Predicate<SystemConfig> {

        private java.lang.String value;

        public IsSubscriptionKeySeed(java.lang.String value) {
            this.value = value;
        }

        public boolean test(SystemConfig e) {
            return e.isSubscriptionKeySeed(value);
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

}