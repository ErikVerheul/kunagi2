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

import ilarkesto.auth.Auth;
import ilarkesto.base.UtlExtend;
import ilarkesto.core.time.DateAndTime;
import scrum.server.admin.User;

public class Comment extends GComment implements Comparable<Comment> {

    public String getAuthorLabel() {
        if (isAuthorSet()) {
            return getAuthor().getPublicName();
        }
        if (isAuthorNameSet()) {
            return getAuthorName();
        }
        return "anonymous";
    }

    @Override
    public void ensureIntegrity() {
        super.ensureIntegrity();
        if (!isDateAndTimeSet()) {
            setDateAndTime(DateAndTime.now());
        }
        if (UtlExtend.isEmpty(getText()) && getDateAndTime().getPeriodToNow().toHours() > 1) {
            getDao().deleteEntity(this);
        }
        if (isAuthorSet()) {
            setAuthorName(getAuthor().getPublicName());
        }

    }

    @Override
    public boolean isVisibleFor(User user) {
        return Auth.isVisible(getParent(), user);
    }

    public boolean isEditableBy(User user) {
        return isAuthor(user);
    }

    @Override
    public int compareTo(Comment other) {
        return UtlExtend.compare(getDateAndTime(), other.getDateAndTime());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Comment)) {
            return false;
        }
        Comment com = (Comment) o;
        if (getDateAndTime() == null) {
            return false;
        } else {
            return getDateAndTime().equals(com.getDateAndTime());
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (null == getDateAndTime() ? 0 : getDateAndTime().hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "[" + getAuthorName() + "@" + getDateAndTime() + "] " + getText();
    }
}