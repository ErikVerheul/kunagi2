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
package scrum.client.collaboration;

import ilarkesto.gwt.client.AGwtEntity;
import ilarkesto.gwt.client.EntityDoesNotExistException;

import java.util.ArrayList;
import java.util.List;

import scrum.client.admin.User;
import scrum.client.common.AScrumGwtEntity;
import scrum.client.core.RequestEntityServiceCall;
import scrum.client.workspace.BlockCollapsedEvent;
import scrum.client.workspace.BlockCollapsedHandler;
import scrum.client.workspace.BlockExpandedEvent;
import scrum.client.workspace.BlockExpandedHandler;

public class UsersStatus extends GUsersStatus implements BlockCollapsedHandler, BlockExpandedHandler {

	@Override
	public void onBlockExpanded(BlockExpandedEvent event) {
		Object object = event.getObject();
		if (object instanceof AGwtEntity) {
			addSelectedEntity((AGwtEntity) object);
		}
	}

	@Override
	public void onBlockCollapsed(BlockCollapsedEvent event) {
		Object object = event.getObject();
		if (object instanceof AGwtEntity) {
			removeSelectedEntity((AGwtEntity) object);
		}
	}

	public List<User> getOnlineUsers() {
		List<User> ret = new ArrayList<User>();
		for (User user : project.getParticipants()) {
			if (isOnline(user)) ret.add(user);
		}
		return ret;
	}

	public boolean isOnline(User user) {
		return project.getUserConfig(user).isOnline();
	}

	public boolean isIdle(User user) {
		return project.getUserConfig(user).isIdle();
	}

	private void addSelectedEntity(AGwtEntity entity) {
		project.getUserConfig(getCurrentUser()).addSelectedEntityId(entity.getId());
	}

	private void removeSelectedEntity(AGwtEntity entity) {
		project.getUserConfig(getCurrentUser()).removeSelectedEntityId(entity.getId());
	}

	public List<AScrumGwtEntity> getSelectedEntities(User user) {
		List<String> ids = project.getUserConfig(user).getSelectedEntitysIds();
		List<AScrumGwtEntity> ret = new ArrayList<AScrumGwtEntity>(ids.size());
		for (String id : ids) {
			try {
				AGwtEntity entity = dao.getEntity(id);
				ret.add((AScrumGwtEntity) entity);
			} catch (EntityDoesNotExistException ex) {
				new RequestEntityServiceCall(id).execute();
			}
		}
		return ret;
	}

	private User getCurrentUser() {
		return auth.getUser();
	}

}