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
package scrum.client.risks;

import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.HyperlinkWidget;
import ilarkesto.gwt.client.editor.AFieldModel;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import scrum.client.ScrumGwt;
import scrum.client.admin.Auth;
import scrum.client.admin.User;
import scrum.client.collaboration.ForumSupport;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.common.ShowEntityAction;
import scrum.client.project.Project;

public class Risk extends GRisk implements Comparable<Risk>, ReferenceSupport, LabelSupport, ForumSupport {

    public static final String REFERENCE_PREFIX = "rsk";
    public static final List<Integer> IMPACTS = Gwt.toList(20, 40, 60, 80, 100);
    public static final List<Integer> PROBABILITIES = Gwt.toList(20, 40, 60, 80, 100);

    public Risk(Project project) {
        setProject(project);
    }

    public Risk(Map data) {
        super(data);
    }

    @Override
    public List<Integer> getImpactOptions() {
        return IMPACTS;
    }

    @Override
    public List<Integer> getProbabilityOptions() {
        return PROBABILITIES;
    }

    @Override
    public String getReference() {
        return REFERENCE_PREFIX + getNumber();
    }

    public String getProbabilityLabel() {
        return RiskComputer.getProbabilityLabel(getProbability());
    }

    public String getImpactLabel() {
        return RiskComputer.getImpactLabel(getImpact());
    }

    public int getPriority() {
        return RiskComputer.getPriority(getImpact(), getProbability());
    }

    public String getPriorityLabel() {
        return RiskComputer.getPriorityLabel(getImpact(), getProbability());
    }

    public String getSummary() {
        return getPriorityLabel() + " priority because " + getImpactLabel() + " and " + getProbabilityLabel();
    }

    @Override
    public int compareTo(Risk o) {
        return getPriority() - o.getPriority();
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
    public boolean isPriorityEditable() {
        Project project = getProject();
        User user = Scope.get().getComponent(Auth.class).getUser();
        return project.isProductOwner(user) || project.isTeamMember(user);
    }

    @Override
    public String toHtml() {
        return ScrumGwt.toHtml(this, getLabel());
    }

    @Override
    public String toString() {
        return getReference() + " " + getLabel();
    }
    private AFieldModel<String> priorityLabelModel;

    public AFieldModel<String> getPriorityLabelModel() {
        if (priorityLabelModel == null) {
            priorityLabelModel = new AFieldModel<String>() {

                @Override
                public String getValue() {
                    return getPriorityLabel();
                }
            };
        }
        return priorityLabelModel;
    }

    @Override
    public Widget createForumItemWidget() {
        return new HyperlinkWidget(new ShowEntityAction(RiskListWidget.class, this, getLabel()));
    }
    public static final Comparator<Risk> PRIORITY_COMPARATOR = new Comparator<Risk>() {

        @Override
        public int compare(Risk a, Risk b) {
            return b.getPriority() - a.getPriority();
        }
    };
}
