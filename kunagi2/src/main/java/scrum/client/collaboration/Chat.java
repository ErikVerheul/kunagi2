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

import generated.client.collaboration.GChat;
import java.util.Collections;
import java.util.LinkedList;

import scrum.client.admin.User;

public class Chat extends GChat {

	private LinkedList<ChatMessage> chatMessages = new LinkedList<ChatMessage>();

	public LinkedList<ChatMessage> getChatMessages() {
		return chatMessages;
	}

	public ChatMessage postSystemMessage(String text, boolean distribute) {
		return postMessage(null, text, distribute);
	}

	public ChatMessage postMessage(String text) {
		return postMessage(auth.getUser(), text, true);
	}

	private ChatMessage postMessage(User author, String text, boolean distribute) {
		ChatMessage msg = new ChatMessage(project, author, text);
		addChatMessage(msg);
		if (distribute) dao.createChatMessage(msg);
		return msg;
	}

	public void addChatMessage(ChatMessage msg) {
		if (project == null || !msg.isProject(project)) return;
		if (chatMessages.contains(msg)) return;
		chatMessages.add(msg);
		cleanupChatMessages();
		projectWorkspaceWidgets.getSidebar().getChat().update();
	}

	private void cleanupChatMessages() {
		// for (Iterator<ChatMessage> it = chatMessages.iterator(); it.hasNext();) {
		// ChatMessage message = it.next();
		// if (message.isOld()) it.remove();
		// }
		while (chatMessages.size() > 100)
			chatMessages.remove(0);
		Collections.sort(chatMessages);
	}
}
