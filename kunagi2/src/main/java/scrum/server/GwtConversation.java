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
package scrum.server;

import ilarkesto.auth.Auth;
import ilarkesto.core.KunagiProperties;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.gwt.server.AGwtConversation;
import ilarkesto.logging.Log;
import ilarkesto.persistence.AEntity;
import scrum.client.DataTransferObject;
import scrum.client.communication.Pinger;
import scrum.server.admin.ProjectUserConfig;
import scrum.server.admin.SystemConfig;
import scrum.server.admin.User;
import scrum.server.collaboration.Emoticon;
import scrum.server.collaboration.EmoticonDao;
import scrum.server.project.Project;

public class GwtConversation extends AGwtConversation {

	private static final Log LOG = Log.get(GwtConversation.class);
	private final TimePeriod TIMEOUT = new TimePeriod(Pinger.MAX_DELAY * 10);

	private Project project;

	// --- dependencies ---

	private EmoticonDao emoticonDao;

	public synchronized void setEmoticonDao(EmoticonDao emoticonDao) {
		this.emoticonDao = emoticonDao;
	}

	// --- ---

	public GwtConversation(WebSession session, int number) {
		super(session, number);
	}

	@Override
	protected void filterEntityProperties(AEntity entity, KunagiProperties propertiesMap) {
		super.filterEntityProperties(entity, propertiesMap);
		User user = getSession().getUser();

		if (entity instanceof SystemConfig) {
			if (user == null || !user.isAdmin()) {
				propertiesMap.remove("smtpPassword");
			}
		} else if (entity instanceof User) {
			if (user == null || (user != entity && !user.isAdmin())) {
				propertiesMap.remove("password");
				propertiesMap.remove("email");
				propertiesMap.remove("loginToken");
			}
		}

	}

	@Override
	protected boolean isEntityVisible(AEntity entity) {
		return Auth.isVisible(entity, getSession().getUser());
	}

	public void sendUserScopeDataToClient(User user) {
		getNextData().setUserId(user.getId());
		ScrumWebApplication app = ScrumWebApplication.get();
		getNextData().systemMessage = app.getSystemMessage();
		sendToClient(user);
		sendToClient(app.getProjectDao().getEntitiesVisibleForUser(user)); // all projects
		sendToClient(app.getUserDao().getEntitiesVisibleForUser(user)); // all users
	}

	@Override
	public synchronized void sendToClient(AEntity entity) {
		super.sendToClient(entity);
		for (Emoticon emoticon : emoticonDao.getEmoticonsByParent(entity)) {
			super.sendToClient(emoticon);
		}
	}

	@Override
	public void invalidate() {
		WebSession session = getSession();
		User user = session.getUser();
		if (user != null && project != null && session.getGwtConversations().size() < 2) {
			ProjectUserConfig config = project.getUserConfig(user);
			config.reset();
			ScrumWebApplication.get().sendToOtherConversationsByProject(this, config);
		}
		super.invalidate();
	}

	@Override
	protected DataTransferObject createDataTransferObject() {
		return new DataTransferObject();
	}

	@Override
	public WebSession getSession() {
		return (WebSession) super.getSession();
	}

	@Override
	public DataTransferObject getNextData() {
		return (DataTransferObject) super.getNextData();
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		LOG.info("Project selected:", project);
		this.project = project;
	}

	@Override
	protected TimePeriod getTimeout() {
		return TIMEOUT;
	}

}
