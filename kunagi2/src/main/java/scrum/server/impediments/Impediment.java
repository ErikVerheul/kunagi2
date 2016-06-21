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
package scrum.server.impediments;

import ilarkesto.core.base.Utl;
import ilarkesto.core.time.Date;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.server.admin.User;
import scrum.server.common.Numbered;

public class Impediment extends GImpediment implements Numbered, ReferenceSupport, LabelSupport, Comparable<Impediment> {

    @Override
    public void updateNumber() {
        if (getNumber() == 0) {
            setNumber(getProject().generateImpedimentNumber());
        }
    }

    public String getReferenceAndLabel() {
        return getReference() + " " + getLabel();
    }

    @Override
    public String getReference() {
        return scrum.client.impediments.Impediment.REFERENCE_PREFIX + getNumber();
    }

    @Override
    public boolean isVisibleFor(User user) {
        return getProject().isVisibleFor(user);
    }

    public boolean isEditableBy(User user) {
        return getProject().isEditableBy(user);
    }

    @Override
    public void ensureIntegrity() {
        super.ensureIntegrity();
        updateNumber();
        if (!isDateSet()) {
            setDate(Date.today());
        }

        // delete when closed and older than 4 weeks
	if (isClosed() && getDate().getPeriodToToday().toWeeks() > 4) getDao().deleteEntity(this);

    }

    @Override
    public int compareTo(Impediment other) {
        return Utl.compare(getDate(), other.getDate());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Impediment)) {
            return false;
        }
        Impediment other = (Impediment)o;
        return super.equals(o) && Utl.compare(getDate(), other.getDate()) == 0;
    }

    @Override
    public String toString() {
        return getReferenceAndLabel();
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 31 * hash + (null == getDate() ? 0 : getDate().hashCode());
        return hash;
    }
}
