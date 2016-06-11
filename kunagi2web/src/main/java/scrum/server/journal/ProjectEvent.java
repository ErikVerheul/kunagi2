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
package scrum.server.journal;

import scrum.server.admin.User;
import scrum.server.collaboration.ChatMessage;
import scrum.server.collaboration.ChatMessageDao;

public class ProjectEvent extends GProjectEvent implements Comparable<ProjectEvent> {

    // --- dependencies ---
    private static ChatMessageDao chatMessageDao;

    public static void setChatMessageDao(ChatMessageDao chatMessageDao) {
        ProjectEvent.chatMessageDao = chatMessageDao;
    }

    // --- ---
    public ChatMessage postChatMessage() {
        return chatMessageDao.postChatMessage(getProject(), getLabel(), getDateAndTime());
    }

    @Override
    public int compareTo(ProjectEvent other) {
        return other.getDateAndTime().compareTo(getDateAndTime());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProjectEvent)) {
            return false;
        }
        ProjectEvent pe = (ProjectEvent) o;
        return getDateAndTime().equals(pe.getDateAndTime());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (null == getDateAndTime() ? 0 : getDateAndTime().hashCode());
        return hash;
    }

    @Override
    public boolean isVisibleFor(User user) {
        return getProject().isVisibleFor(user);
    }

    public boolean isEditableBy(User user) {
        return false;
    }

    @Override
    public void ensureIntegrity() {
        super.ensureIntegrity();

    }

    @Override
    public String toString() {
        return getLabel();
    }
}