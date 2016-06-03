/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.admin;

import ilarkesto.gwt.client.AOutputViewEditWidget;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.TableBuilder;
import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SystemConfigWidget extends AScrumWidget {

	@Override
	protected Widget onInitialization() {
		SystemConfig config = getDao().getSystemConfig();

		PagePanel page = new PagePanel();

		page.addHeader("Login page");
		TableBuilder tbLogin = ScrumGwt.createFieldTable();
		tbLogin.addFieldRow("Instance name", config.getInstanceNameModel());
		tbLogin.addFieldRow("Custom logo", config.getLoginPageLogoUrlModel());
		tbLogin.addFieldRow("Login message", config.getLoginPageMessageModel());
		tbLogin.addFieldRow("Register message", config.getRegisterPageMessageModel());
		// tbMessages.addFieldRow("About Page", config.getAboutPageMessageModel());
		page.addSection(tbLogin.createTable());

		page.addHeader("Authentication", new ButtonWidget(new TestLdapAction()));
		TableBuilder tbAuth = ScrumGwt.createFieldTable();
		tbAuth.addFieldRow("OpenID disabled", config.getOpenIdDisabledModel());
		tbAuth.addFieldRow("Allowed OpenID Domains", config.getOpenIdDomainsModel());
		tbAuth.addFieldRow("LDAP authentication enabled", config.getLdapEnabledModel());
		tbAuth.addFieldRow("LDAP URL", config.getLdapUrlModel());
		tbAuth.addFieldRow("LDAP user", config.getLdapUserModel());
		tbAuth.addFieldRow("LDAP password", config.getLdapPasswordModel());
		tbAuth.addFieldRow("LDAP base DN", config.getLdapBaseDnModel());
		tbAuth.addFieldRow("LDAP user filter RegEx", config.getLdapUserFilterRegexModel());
		page.addSection(tbAuth.createTable());

		page.addHeader("Installation");
		TableBuilder tbKunagi = ScrumGwt.createFieldTable();
		tbKunagi.addFieldRow("Public URL", config.getUrlModel());
		tbKunagi.addFieldRow("Disable registration page", config.getRegistrationDisabledModel());
		tbKunagi.addFieldRow("Disable project creation", config.getProjectCreationDisabledModel());
		tbKunagi.addFieldRow("Users email is mandatory", config.getUserEmailMandatoryModel());
		tbKunagi.addFieldRow("Default user password", config.getDefaultUserPasswordModel());
		tbKunagi.addFieldRow("Check for updates", config.getVersionCheckEnabledModel());
		tbKunagi.addFieldRow("Data path", new AOutputViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewer(new Label(getApp().getApplicationInfo().getDataPath()));
			}
		});
		tbKunagi.addFieldRow("Maximum file size (MB)", config.getMaxFileSizeModel());
		page.addSection(tbKunagi.createTable());

		page.addHeader("Email", new ButtonWidget(new SendTestEmailAction()));
		TableBuilder tbEmail = ScrumGwt.createFieldTable();
		tbEmail.addFieldRow("SMTP server", config.getSmtpServerModel());
		tbEmail.addFieldRow("SMTP port", config.getSmtpPortModel());
		tbEmail.addFieldRow("SMTP TLS", config.getSmtpTlsModel());
		tbEmail.addFieldRow("SMTP user", config.getSmtpUserModel());
		tbEmail.addFieldRow("SMTP password", config.getSmtpPasswordModel());
		tbEmail.addFieldRow("Sender email", config.getSmtpFromModel());
		tbEmail.addFieldRow("Administrator email", config.getAdminEmailModel());
		page.addSection(tbEmail.createTable());

		page.addHeader("Google Integration");
		TableBuilder tbGoogle = ScrumGwt.createFieldTable();
		tbGoogle.addFieldRow("Analytics Id", config.getGoogleAnalyticsIdModel());
		page.addSection(tbGoogle.createTable());

		return page;
	}
}
