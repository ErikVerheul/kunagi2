/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package ilarkesto.integration.gravatar;

import ilarkesto.json.AJsonWrapper;
import ilarkesto.json.JsonObject;
import java.util.List;

public class Profile extends AJsonWrapper {

	public Profile(JsonObject json) {
		super(json);
	}

	public String getId() {
		return json.getString("id");
	}

	public String getHash() {
		return json.getString("hash");
	}

	public String getRequestHash() {
		return json.getString("requestHash");
	}

	public String getProfileUrl() {
		return json.getString("profileUrl");
	}

	public String getPreferredUsername() {
		return json.getString("preferredUsername");
	}

	public String getThumbnailUrl() {
		return json.getString("thumbnailUrl");
	}

	public List<Photo> getPhotos() {
		return createFromArray("photos", Photo.class);
	}

	public Name getName() {
		return createFromObject("name", Name.class);
	}

	public String getDisplayName() {
		return json.getString("displayName");
	}

	public String getAboutMe() {
		return json.getString("aboutMe");
	}

	public String getCurrentLocation() {
		return json.getString("currentLocation");
	}

	public List<Email> getEmails() {
		return createFromArray("emails", Email.class);
	}

	public List<Im> getIms() {
		return createFromArray("ims", Im.class);
	}

	public List<Url> getUrls() {
		return createFromArray("urls", Url.class);
	}

	public List<Account> getAccounts() {
		return createFromArray("accounts", Account.class);
	}

	public static class Account extends AJsonWrapper {

		public static final String SHORTNAME_TWITTER = "twitter";
		public static final String SHORTNAME_LINKEDIN = "linkedin";
		public static final String SHORTNAME_YAHOO = "yahoo";
		public static final String SHORTNAME_GOOGLE = "google";
		public static final String SHORTNAME_FRIENDFEED = "friendfeed";
		public static final String SHORTNAME_FACEBOOK = "facebook";

		public Account(JsonObject json) {
			super(json);
		}

		public String getDomain() {
			return json.getString("domain");
		}

		public String getDisplay() {
			return json.getString("display");
		}

		public String getUrl() {
			return json.getString("url");
		}

		public String getUserId() {
			return json.getString("userid");
		}

		public String getUsername() {
			return json.getString("username");
		}

		public String getShortname() {
			return json.getString("shortname");
		}

	}

	public static class Url extends AJsonWrapper {

		public Url(JsonObject json) {
			super(json);
		}

		public String getValue() {
			return json.getString("value");
		}

		public String getTitle() {
			return json.getString("title");
		}

	}

	public static class Im extends AJsonWrapper {

		public static final String TYPE_ICQ = "icq";
		public static final String TYPE_YAHOO = "yahoo";
		public static final String TYPE_SKYPE = "skype";

		public Im(JsonObject json) {
			super(json);
		}

		public String getType() {
			return json.getString("type");
		}

		public String getValue() {
			return json.getString("value");
		}

	}

	public static class Email extends AJsonWrapper {

		public Email(JsonObject json) {
			super(json);
		}

		public String getValue() {
			return json.getString("value");
		}

		public boolean isPrimary() {
			String value = json.getString("primary");
			return "true".equals(value);
		}

	}

	public static class Name extends AJsonWrapper {

		public Name(JsonObject json) {
			super(json);
		}

		public String getGivenName() {
			return json.getString("givenName");
		}

		public String getFamilyName() {
			return json.getString("familyName");
		}

		public String getFormatted() {
			return json.getString("formatted");
		}

	}

	public static class Photo extends AJsonWrapper {

		public static final String TYPE_THUMBNAIL = "thumbnail";

		public Photo(JsonObject json) {
			super(json);
		}

		public String getValue() {
			return json.getString("value");
		}

		public String getType() {
			return json.getString("type");
		}

	}

}
