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

/**
 *
 * @author erik
 */
public class Profile extends AJsonWrapper {

    /**
     *
     * @param json
     */
    public Profile(JsonObject json) {
		super(json);
	}

    /**
     *
     * @return
     */
    public String getId() {
		return json.getString("id");
	}

    /**
     *
     * @return
     */
    public String getHash() {
		return json.getString("hash");
	}

    /**
     *
     * @return
     */
    public String getRequestHash() {
		return json.getString("requestHash");
	}

    /**
     *
     * @return
     */
    public String getProfileUrl() {
		return json.getString("profileUrl");
	}

    /**
     *
     * @return
     */
    public String getPreferredUsername() {
		return json.getString("preferredUsername");
	}

    /**
     *
     * @return
     */
    public String getThumbnailUrl() {
		return json.getString("thumbnailUrl");
	}

    /**
     *
     * @return
     */
    public List<Photo> getPhotos() {
		return createFromArray("photos", Photo.class);
	}

    /**
     *
     * @return
     */
    public Name getName() {
		return createFromObject("name", Name.class);
	}

    /**
     *
     * @return
     */
    public String getDisplayName() {
		return json.getString("displayName");
	}

    /**
     *
     * @return
     */
    public String getAboutMe() {
		return json.getString("aboutMe");
	}

    /**
     *
     * @return
     */
    public String getCurrentLocation() {
		return json.getString("currentLocation");
	}

    /**
     *
     * @return
     */
    public List<Email> getEmails() {
		return createFromArray("emails", Email.class);
	}

    /**
     *
     * @return
     */
    public List<Im> getIms() {
		return createFromArray("ims", Im.class);
	}

    /**
     *
     * @return
     */
    public List<Url> getUrls() {
		return createFromArray("urls", Url.class);
	}

    /**
     *
     * @return
     */
    public List<Account> getAccounts() {
		return createFromArray("accounts", Account.class);
	}

    /**
     *
     */
    public static class Account extends AJsonWrapper {

        /**
         *
         */
        public static final String SHORTNAME_TWITTER = "twitter";

        /**
         *
         */
        public static final String SHORTNAME_LINKEDIN = "linkedin";

        /**
         *
         */
        public static final String SHORTNAME_YAHOO = "yahoo";

        /**
         *
         */
        public static final String SHORTNAME_GOOGLE = "google";

        /**
         *
         */
        public static final String SHORTNAME_FRIENDFEED = "friendfeed";

        /**
         *
         */
        public static final String SHORTNAME_FACEBOOK = "facebook";

        /**
         *
         * @param json
         */
        public Account(JsonObject json) {
			super(json);
		}

        /**
         *
         * @return
         */
        public String getDomain() {
			return json.getString("domain");
		}

        /**
         *
         * @return
         */
        public String getDisplay() {
			return json.getString("display");
		}

        /**
         *
         * @return
         */
        public String getUrl() {
			return json.getString("url");
		}

        /**
         *
         * @return
         */
        public String getUserId() {
			return json.getString("userid");
		}

        /**
         *
         * @return
         */
        public String getUsername() {
			return json.getString("username");
		}

        /**
         *
         * @return
         */
        public String getShortname() {
			return json.getString("shortname");
		}

	}

    /**
     *
     */
    public static class Url extends AJsonWrapper {

        /**
         *
         * @param json
         */
        public Url(JsonObject json) {
			super(json);
		}

        /**
         *
         * @return
         */
        public String getValue() {
			return json.getString("value");
		}

        /**
         *
         * @return
         */
        public String getTitle() {
			return json.getString("title");
		}

	}

    /**
     *
     */
    public static class Im extends AJsonWrapper {

        /**
         *
         */
        public static final String TYPE_ICQ = "icq";

        /**
         *
         */
        public static final String TYPE_YAHOO = "yahoo";

        /**
         *
         */
        public static final String TYPE_SKYPE = "skype";

        /**
         *
         * @param json
         */
        public Im(JsonObject json) {
			super(json);
		}

        /**
         *
         * @return
         */
        public String getType() {
			return json.getString("type");
		}

        /**
         *
         * @return
         */
        public String getValue() {
			return json.getString("value");
		}

	}

    /**
     *
     */
    public static class Email extends AJsonWrapper {

        /**
         *
         * @param json
         */
        public Email(JsonObject json) {
			super(json);
		}

        /**
         *
         * @return
         */
        public String getValue() {
			return json.getString("value");
		}

        /**
         *
         * @return
         */
        public boolean isPrimary() {
			String value = json.getString("primary");
			return "true".equals(value);
		}

	}

    /**
     *
     */
    public static class Name extends AJsonWrapper {

        /**
         *
         * @param json
         */
        public Name(JsonObject json) {
			super(json);
		}

        /**
         *
         * @return
         */
        public String getGivenName() {
			return json.getString("givenName");
		}

        /**
         *
         * @return
         */
        public String getFamilyName() {
			return json.getString("familyName");
		}

        /**
         *
         * @return
         */
        public String getFormatted() {
			return json.getString("formatted");
		}

	}

    /**
     *
     */
    public static class Photo extends AJsonWrapper {

        /**
         *
         */
        public static final String TYPE_THUMBNAIL = "thumbnail";

        /**
         *
         * @param json
         */
        public Photo(JsonObject json) {
			super(json);
		}

        /**
         *
         * @return
         */
        public String getValue() {
			return json.getString("value");
		}

        /**
         *
         * @return
         */
        public String getType() {
			return json.getString("type");
		}

	}

}
