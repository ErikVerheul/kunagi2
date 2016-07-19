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
package scrum.server.risks;

import ilarkesto.core.base.Utl;
import scrum.client.risks.RiskComputer;
import scrum.server.admin.User;
import scrum.server.common.Numbered;

public class Risk extends GRisk implements Numbered, Comparable<Risk> {

    public int getPriority() {
        return RiskComputer.getPriority(getImpact(), getProbability());
    }

    public String getPriorityLabel() {
        return RiskComputer.getPriorityLabel(getImpact(), getProbability());
    }

    public String getProbabilityLabel() {
        return RiskComputer.getProbabilityLabel(getProbability());
    }

    public String getImpactLabel() {
        return RiskComputer.getImpactLabel(getImpact());
    }

    @Override
    public void updateNumber() {
        if (getNumber() == 0) {
            setNumber(getProject().generateRiskNumber());
        }
    }

    public String getReferenceAndLabel() {
        return getReference() + " " + getLabel();
    }

    public String getReference() {
        return scrum.client.risks.Risk.REFERENCE_PREFIX + getNumber();
    }

    @Override
    public void ensureIntegrity() {
        super.ensureIntegrity();
        updateNumber();

    }

    @Override
    public boolean isVisibleFor(User user) {
        return getProject().isVisibleFor(user);
    }

    @Override
    public int compareTo(Risk other) {
        return Utl.compare(other.getPriority(), getPriority());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Risk)) {
            return false;
        }
        Risk r = (Risk) o;
        return getPriority() == r.getPriority();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + getPriority();
        return hash;
    }

    @Override
    public String toString() {
        return getReferenceAndLabel();
    }
}
