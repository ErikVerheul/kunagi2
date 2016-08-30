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

import ilarkesto.auth.OpenId;
import ilarkesto.base.StrExtend;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.integration.gravatar.Gravatar;
import ilarkesto.integration.gravatar.Profile;
import ilarkesto.logging.Log;
import scrum.server.ScrumWebApplication;

/**
 *
 *
 */
public class UserDao extends GUserDao {

	private static final Log LOG = Log.get(UserDao.class);

	@Override
	public User postUser(String name, String password) {
		return postUser(null, name, password);
	}

    /**
     *
     * @param email
     * @param name
     * @param password
     * @return
     */
    public User postUser(String email, String name, String password) {
		User user = newEntityInstance();
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.tryUpdateByGravatar();
		saveEntity(user);
		LOG.info("postUser: User created:", user);
		return user;
	}

    /**
     *
     * @param name
     * @return
     */
    public User postUserWithDefaultPassword(String name) {
		return postUser(name, getDefaultPassword());
	}

    /**
     *
     * @param openId
     * @param nickname
     * @param fullname
     * @param email
     * @return
     */
    public User postUserWithOpenId(String openId, String nickname, String fullname, String email) {
		String name = null;

		if (nickname != null) {
			if (getUserByName(nickname) == null) {
                            name = nickname;
            }
		}

		if (name == null && email != null) {
			Profile profile = Gravatar.loadProfile(email);
			if (profile != null) {
                            name = profile.getPreferredUsername();
            }
		}

		if (name == null && email != null) {
			name = StrExtend.cutTo(email, "@");
		}

		if (name == null) {
			name = OpenId.cutUsername(openId);
			name = StrExtend.removePrefix(name, "AItOaw");
                        if (name.length() > 10) {
                            name = name.substring(0, 9);
            }
		}

		String namePrefix = name;
		int suffix = 1;
		while (getUserByName(name) != null) {
			suffix++;
			name = namePrefix + suffix;
		}

		User user = newEntityInstance();
		user.setName(name);
		user.setFullName(fullname);
		user.setOpenId(openId);
		if (!StrExtend.isBlank(email)) {
			user.setEmail(email);
			user.setEmailVerified(true);
		}
		user.setPassword(StrExtend.generatePassword(10));
		user.tryUpdateByGravatar();
		saveEntity(user);
		LOG.info("postUserWithOpenId: User created:", user);
		return user;
	}

    /**
     *
     * @return
     */
    @Override
	public User newEntityInstance() {
		User user = super.newEntityInstance();
		user.setPassword(getDefaultPassword());
		user.setRegistrationDateAndTime(DateAndTime.now());
		return user;
	}

	private String getDefaultPassword() {
		return ScrumWebApplication.get().getSystemConfig().getDefaultUserPassword();
	}

	// --- test data ---

    /**
     *
     * @param name
     * @return
     */
    
	public User getTestUser(String name) {
            User user = getUserByName(name);
            if (user == null) {
            user = postUserWithDefaultPassword(name);
        }
		return user;
	}

}
