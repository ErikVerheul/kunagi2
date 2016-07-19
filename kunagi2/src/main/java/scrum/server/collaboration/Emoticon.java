/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.server.collaboration;

import ilarkesto.auth.Auth;
import scrum.server.admin.User;

public class Emoticon extends GEmoticon {

        @Override
	public boolean isVisibleFor(User user) {
		return Auth.isVisible(getParent(), user);
	}

	public boolean isEditableBy(User user) {
		return isOwner(user);
	}

	@Override
	public void ensureIntegrity() {
		if (isEmotionSet() && !scrum.client.collaboration.Emoticon.EMOTIONS.contains(getEmotion())) {
			getDao().deleteEntity(this);
			return;
		}
		super.ensureIntegrity();
	}

}