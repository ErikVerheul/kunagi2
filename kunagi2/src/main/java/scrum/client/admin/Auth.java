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
package scrum.client.admin;

import static ilarkesto.core.logging.ClientLog.INFO;
import ilarkesto.gwt.client.DataTransferObject;
import scrum.client.ScrumScopeManager;
import scrum.client.communication.ServerDataReceivedEvent;
import scrum.client.communication.ServerDataReceivedHandler;

/**
 *
 *
 */
public class Auth extends GAuth implements ServerDataReceivedHandler {

	private User user;

    /**
     *
     * @param event
     */
    @Override
	public void onServerDataReceived(ServerDataReceivedEvent event) {
		DataTransferObject data = event.getData();
		if (data.isUserSet()) {
			user = dao.getUser(data.getUserId());
			INFO("User logged in:", user);
			ScrumScopeManager.createUserScope(user);
		}
	}

    /**
     *
     */
    public void logout() {
		user = null;
	}

    /**
     *
     * @return
     */
    public boolean isUserLoggedIn() {
		return user != null;
	}

    /**
     *
     * @return
     */
    public User getUser() {
		return user;
	}

}
