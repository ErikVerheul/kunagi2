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

import ilarkesto.auth.Auth;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.AEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import scrum.server.admin.User;
import scrum.server.project.Requirement;

public class Change extends GChange implements Comparable<Change> {

    private static final Log log = Log.get(Change.class);

    @Override
    public boolean isVisibleFor(User user) {
        return Auth.isVisible(getParent(), user);
    }

    public boolean isEditableBy(User user) {
        return false;
    }

    public void mergeTo(Change other) {
        log.info("Merging", this, "to", other);
        other.setOldValue(getOldValue());
        if (isCommentSet()) {
            if (other.isCommentSet()) {
                other.setComment(getComment() + "\n\n" + other.getComment());
            } else {
                other.setComment(getComment());
            }
        }
        getDao().deleteEntity(this);
    }

    public boolean isMergableWith(Change other) {
        if (getParent() instanceof Requirement && isKey("sprintId")) {
            return false;
        }
        if (getKey().startsWith("@")) {
            return false;
        }
        if (!isParent(other.getParent())) {
            return false;
        }
        if (!isUser(other.getUser())) {
            return false;
        }
        if (!isKey(other.getKey())) {
            return false;
        }
        return getDateAndTime().getPeriodTo(other.getDateAndTime()).abs().toMinutes() <= 60;
    }

    @Override
    public int compareTo(Change other) {
        return getDateAndTime().compareTo(other.getDateAndTime());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Change) || getDateAndTime() == null) {
            return false;
        }
        Change ch = (Change) o;
        return getDateAndTime().equals(ch.getDateAndTime());
    }


    @Override
    public String toString() {
        AEntity parent;
        try {
            parent = getParent();
        } catch (Throwable ex) {
            parent = null;
        }
        return "Change(" + getUser() + "," + getDateAndTime() + ", " + parent + "." + getKey() + ")";
    }

    public static void merge(Collection<Change> changes) {
        List<Change> list = new ArrayList<Change>(changes);
        Collections.sort(list);

        Change previous = null;
        for (Change change : list) {
            if (previous == null) {
                previous = change;
                continue;
            }
            if (previous.isMergableWith(change)) {
                previous.mergeTo(change);
            }
            previous = change;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
}
