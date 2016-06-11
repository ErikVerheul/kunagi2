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
package scrum.server.calendar;

import ilarkesto.core.base.Utl;
import ilarkesto.core.time.Date;
import scrum.server.admin.User;
import scrum.server.common.Numbered;

public class SimpleEvent extends GSimpleEvent implements Numbered, Comparable<SimpleEvent> {

    public String getReferenceAndLabel() {
        return getReference() + " " + getLabel();
    }

    public String getReference() {
        return scrum.client.calendar.SimpleEvent.REFERENCE_PREFIX + getNumber();
    }

    @Override
    public void updateNumber() {
        if (getNumber() == 0) {
            setNumber(getProject().generateEventNumber());
        }
    }

    @Override
    public boolean isVisibleFor(User user) {
        return getProject().isVisibleFor(user);
    }

    @Override
    public void ensureIntegrity() {
        if (!isDateSet()) {
            setDate(Date.today());
        }
        updateNumber();
        super.ensureIntegrity();
    }

    @Override
    public int compareTo(SimpleEvent other) {
        return Utl.compare(getDate(), other.getDate());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SimpleEvent)) {
            return false;
        }
        SimpleEvent se = (SimpleEvent) o;
        if (getDate() == null) {
            return false;
        } else {
            return getDate().equals(se.getDate());
        }
    }

//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 31 * hash + (null == getDate() ? 0 : getDate().hashCode());
//        return hash;
//    }

    @Override
    public String toString() {
        return getReferenceAndLabel();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
}