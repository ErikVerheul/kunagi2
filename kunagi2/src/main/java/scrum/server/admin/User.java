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

import ilarkesto.auth.PasswordHasher;
import ilarkesto.base.CryptOneWay;
import ilarkesto.base.StrExtend;
import ilarkesto.base.UtlExtend;
import ilarkesto.core.logging.Log;
import ilarkesto.integration.gravatar.Gravatar;
import ilarkesto.integration.gravatar.Profile;
import ilarkesto.integration.gravatar.Profile.Name;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import scrum.server.ScrumWebApplication;
import scrum.server.pr.EmailSender;

public class User extends GUser {

	private static final int HOURS_FOR_EMAIL_VERIFICATION = 48;
	private static final int DAYS_FOR_INACTIVITY = 7;

	private static Log log = Log.get(User.class);

	private String passwordSalt;

	// --- dependencies ---

	private static ScrumWebApplication webApplication;
	private static EmailSender emailSender;

	public static void setEmailSender(EmailSender emailSender) {
		User.emailSender = emailSender;
	}

	public static void setWebApplication(ScrumWebApplication webApplication) {
		User.webApplication = webApplication;
	}

	// --- ---

	public void tryUpdateByGravatar() {
		try {
			updateByGravatar();
		} catch (Throwable ex) {
			log.error("Updating user data by Gravatar failed", ex);
		}
	}

	public void updateByGravatar() {
		Profile profile = Gravatar.loadProfile(getEmail());
		if (profile == null) return;
		Name name = profile.getName();
		if (name != null) {
			if (!isFullNameSet()) setFullName(name.getFormatted());
		}
	}

	@Override
	protected String prepareEmail(String email) {
		return super.prepareEmail(email == null ? null : email.toLowerCase());
	}

	public String getLabel() {
		if (isEmailSet()) return getName() + " (" + getEmail() + ")";
		return getName();
	}

	private String password;

	public void triggerEmailVerification() {
		if (!isEmailSet()) {
			log.info("User has no email. Skipping email verification:", this);
			return;
		}

		String urlBase = webApplication.createUrl(null);
		StringBuilder sb = new StringBuilder();
		sb.append(
			"You have created an account for " + webApplication.getSystemConfig().getInstanceNameWithApplicationLabel())
				.append(urlBase).append("\n");
		sb.append("\n");
		sb.append("Please visit the following link, to confirm your email: ").append(urlBase)
				.append("confirmEmail?user=").append(getId()).append("&email=").append(getEmail()).append("\n");
		sb.append("\n");
		sb.append("Please confirm your email within " + HOURS_FOR_EMAIL_VERIFICATION
				+ " hours, otherwise your account will be deleted.\n");
		try {
			emailSender.sendEmail((String) null, getEmail(), "Kunagi email verification: " + getEmail(), sb.toString());
		} catch (Exception ex) {
			log.error("Sending verification email failed:", getEmail(), ex);
		}
	}

	public void triggerNewPasswordRequest() {
		if (!isEmailSet()) {
			log.info("User has no email. Skipping new password request:", this);
			return;
		}

		String newPassword = StrExtend.generatePassword(8);

		StringBuilder sb = new StringBuilder();
		sb.append("You requested a new password for your Kunagi account on ").append(webApplication.createUrl(null))
				.append("\n");
		sb.append("\n");
		sb.append("Email: ").append(getEmail()).append("\n");
		sb.append("Password: ").append(newPassword).append("\n");
		sb.append("\n");
		sb.append("You sould change this password, since somebody else could read this email.");

		emailSender.sendEmail((String) null, getEmail(), "Kunagi password", sb.toString());

		setPassword(newPassword);
		log.info("Password changed for", this);
	}

	public void triggerPasswordReset() {
		String urlBase = webApplication.createUrl(null);

		String newPassword = StrExtend.generatePassword(8);
		setPassword(newPassword);
		log.info("Password changed for", this);

		StringBuilder sb = new StringBuilder();
		sb.append("An admin created a new password for your Kunagi account on ").append(urlBase).append("\n");
		sb.append("\n");
		sb.append("Email: ").append(getEmail()).append("\n");
		sb.append("Password: ").append(newPassword).append("\n");
		sb.append("\n");
		sb.append("You sould change this password, since somebody else could read this email.");
		emailSender.sendEmail((String) null, getEmail(), "Kunagi password", sb.toString());
	}

	@Override
	public String getRealName() {
		return getName();
	}

	@Override
	public boolean matchesPassword(String password) {
		if (this.password != null && this.password.startsWith(CryptOneWay.DEFAULT_SALT)) {
			boolean success = CryptOneWay.cryptWebPassword(password).equals(this.password);
			if (!success) return false;
			log.warn("Converting old password hash into new:", this);
			setPassword(password);
			return true;
		}
		return hashPassword(password).equals(this.password);
	}

	@Override
	public void setPassword(String value) {
		this.password = hashPassword(value);
		fireModified("password=xxx");
	}

	private String hashPassword(String password) {
		if (passwordSalt == null) {
			passwordSalt = StrExtend.generatePassword(32);
			fireModified("passwordSalt=" + this.passwordSalt);
		}
		return PasswordHasher.hashPassword(password, this.passwordSalt, "SHA-256:");
	}

	@Override
	public String getAutoLoginString() {
		return getLoginToken();
	}

	public void createLoginToken() {
		setLoginToken(UUID.randomUUID().toString());
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();
		if (StrExtend.isBlank(this.password)) setPassword(webApplication.getSystemConfig().getDefaultUserPassword());
		if (!isPublicNameSet()) setPublicName(getName());
		if (!isColorSet()) setColor(getDefaultColor());
		if (!isLoginTokenSet()) createLoginToken();
	}

	@Override
	public boolean isVisibleFor(User user) {
		return true;
	}

	@Override
	public boolean isEditableBy(User user) {
		return user == this || user.isAdmin();
	}

	@Override
	public String toString() {
		return getName();
	}

	public static String getDefaultColor() {
		return UtlExtend.randomElement(getDefaultColors());
	}

	public static List<String> getDefaultColors() {
		List<String> colors = new ArrayList<String>();
		colors.add("black");
		colors.add("darkred");
		colors.add("darkgreen");
		colors.add("darkblue");
		colors.add("darkorange");
		colors.add("darkorchid");
		colors.add("darkslateblue");
		colors.add("orange");
		colors.add("green");
		colors.add("blueviolet");
		colors.add("darkgoldenrod");
		colors.add("darkslategray");
		colors.add("olive");
		return colors;
	}

	public static List<String> getNames(Collection<User> users) {
		List<String> names = new ArrayList<String>(users.size());
		for (User user : users) {
			names.add(user.getName());
		}
		return names;
	}

	public boolean isEmailVerificationOverdue() {
		if (!isRegistrationDateAndTimeSet()) return false;
		if (isEmailVerified()) return false;
		return getRegistrationDateAndTime().getPeriodToNow().abs().toHours() > HOURS_FOR_EMAIL_VERIFICATION;
	}

	public boolean isInactive() {
		if (!isLastLoginDateAndTimeSet()) return false;
		return getLastLoginDateAndTime().getPeriodToNow().abs().toDays() > DAYS_FOR_INACTIVITY;
	}

}
