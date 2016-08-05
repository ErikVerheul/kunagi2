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
package scrum.client.collaboration;

import ilarkesto.core.time.Date;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.Time;
import java.util.HashMap;
import scrum.client.admin.User;
import scrum.client.project.Project;

/**
 *
 * @author erik
 */
public class ChatMessage extends GChatMessage implements Comparable<ChatMessage> {

    /**
     *
     * @param project
     * @param author
     * @param text
     */
    public ChatMessage(Project project, User author, String text) {
        setProject(project);
        setAuthor(author);
        setText(text);
        setDateAndTime(DateAndTime.now());
    }

    /**
     *
     * @param data
     */
    public ChatMessage(HashMap<String, Object> data) {
        super(data);
    }

    @Override
    public String toString() {
        return getAuthor() + ": " + getText();
    }

    /**
     *
     * @return
     */
    public boolean isOld() {
        DateAndTime dt = getDateAndTime();

        Date today = Date.today();
        if (!dt.getDate().equals(today)) {
            return true;
        }

        return Time.now().toSeconds() - dt.getTime().toSeconds() > 900;
    }

    @Override
    public int compareTo(ChatMessage o) {
        return getDateAndTime().compareTo(o.getDateAndTime());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChatMessage)) {
            return false;
        }
        ChatMessage cm = (ChatMessage) o;
        return getDateAndTime().equals(cm.getDateAndTime());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (null == getDateAndTime() ? 0 : getDateAndTime().hashCode());
        return hash;
    }
    
}
