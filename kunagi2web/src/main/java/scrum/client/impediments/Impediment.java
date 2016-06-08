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
package scrum.client.impediments;

import generated.scrum.client.impediments.GImpediment;
import ilarkesto.core.time.Date;
import ilarkesto.gwt.client.HyperlinkWidget;

import java.util.Comparator;
import java.util.Map;

import scrum.client.ScrumGwt;
import scrum.client.collaboration.ForumSupport;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.common.ShowEntityAction;
import scrum.client.project.Project;

import com.google.gwt.user.client.ui.Widget;

public class Impediment extends GImpediment implements ReferenceSupport, LabelSupport, ForumSupport {

	public static final String REFERENCE_PREFIX = "imp";

	public Impediment(Project project) {
		setDate(Date.today());
		setProject(project);
	}

	public Impediment(Map data) {
		super(data);
	}

	public boolean isBlockingTasksFromCurrentSprint() {
		return !getProject().getCurrentSprint().getTasksBlockedBy(this).isEmpty();
	}

	public String getReferenceAndLabel() {
		return getReference() + " " + getLabel();
	}

	public boolean isOpen() {
		return !isClosed();
	}

	@Override
	public String getReference() {
		return REFERENCE_PREFIX + getNumber();
	}

	@Override
	public String toHtml() {
		return ScrumGwt.toHtml(this, getLabel());
	}

	@Override
	public String toString() {
		return getReference() + " " + getLabel();
	}

	@Override
	public Widget createForumItemWidget() {
		return new HyperlinkWidget(new ShowEntityAction(ImpedimentListWidget.class, this, getLabel()));
	}

	public static final Comparator<Impediment> DATE_COMPARATOR = new Comparator<Impediment>() {

		@Override
		public int compare(Impediment a, Impediment b) {
			return a.getDate().compareTo(b.getDate());
		}
	};

	public static final Comparator<Impediment> REVERSE_DATE_COMPARATOR = new Comparator<Impediment>() {

		@Override
		public int compare(Impediment a, Impediment b) {
			return DATE_COMPARATOR.compare(b, a);
		}
	};
}
