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
package scrum.server.collaboration;

import ilarkesto.core.time.DateAndTime;
import scrum.server.project.Project;

public class ChatMessageDao extends GChatMessageDao {

	public ChatMessage postChatMessage(Project project, String text) {
		return postChatMessage(project, text, DateAndTime.now());
	}

	public ChatMessage postChatMessage(Project project, String text, DateAndTime dateAndTime) {
		ChatMessage msg = newEntityInstance();
		msg.setProject(project);
		msg.setDateAndTime(dateAndTime);
		msg.setText(text);
		saveEntity(msg);
		return msg;
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();
		for (ChatMessage message : getEntities()) {
			deleteEntity(message);
		}
	}

}