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
package scrum.server.admin;

import ilarkesto.base.StrExtend;
import scrum.server.KunagiRootConfig;
import scrum.server.ScrumWebApplication;

public class SystemConfig extends GSystemConfig {

	// --- dependencies ---

	private static transient ScrumWebApplication webApplication;

	public static void setWebApplication(ScrumWebApplication webApplication) {
		SystemConfig.webApplication = webApplication;
	}

	// --- ---

	public String getInstanceNameWithApplicationLabel() {
		if (!isInstanceNameSet()) return webApplication.getApplicationLabel();
		return webApplication.getApplicationLabel() + " @ " + getInstanceName();
	}

	@Override
	public boolean isVisibleFor(User user) {
		return true;
	}

	@Override
	public boolean isEditableBy(User user) {
		if (user == null) return false;
		return user.isAdmin();
	}

	@Override
	protected String prepareUrl(String url) {
		if (url != null && !url.endsWith("/")) url += "/";
		return super.prepareUrl(url);
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();
		if (!isDefaultUserPasswordSet()) setDefaultUserPassword(config.getInitialPassword());
		if (!isMaxFileSizeSet()) setMaxFileSize(20);
		if (!isSubscriptionKeySeedSet()) setSubscriptionKeySeed(StrExtend.generatePassword(32));
		if (!isSmtpServerSet()) updateSmtp();
	}

	private void updateSmtp() {
		setSmtpServer(config.getSmtpServer());
		if (!StrExtend.isBlank(config.getSmtpUser())) setSmtpUser(config.getSmtpUser());
		if (!StrExtend.isBlank(config.getSmtpPassword())) setSmtpPassword(config.getSmtpPassword());
	}

	public boolean isOpenIdDomainAllowed(String openId) {
		if (!isOpenIdDomainsSet()) return true;
		String[] domains = StrExtend.tokenize(getOpenIdDomains(), ",; ");
		for (String domain : domains) {
			if (openId.contains(domain)) return true;
		}
		return false;
	}

	// --- dependencies ---

	private transient static KunagiRootConfig config;

	public static void setConfig(KunagiRootConfig config) {
		SystemConfig.config = config;
	}

	// --- ---
}