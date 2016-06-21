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
package scrum.client.sprint;

import ilarkesto.gwt.client.FloatingFlowPanel;
import ilarkesto.gwt.client.Gwt;
import scrum.client.common.AScrumWidget;
import scrum.client.project.Requirement;

import com.google.gwt.user.client.ui.Widget;

public class RequirementWorkIndicatorBarWidget extends AScrumWidget {

	private FloatingFlowPanel flowPanel;
	private int factor = 1;

	private Requirement requirement;

	public RequirementWorkIndicatorBarWidget(Requirement requirement) {
		this.requirement = requirement;

		Sprint sprint = requirement.getSprint();
		int totalWork = sprint.getRemainingWork() + sprint.getBurnedWork();

		factor = 500 / totalWork;
	}

	@Override
	protected void onUpdate() {
		flowPanel.clear();

		int burnedHours = requirement.getBurnedWork();
		Widget burnedWidget = Gwt.createEmptyDiv("EstimationBarWidget-bar1");
		burnedWidget.setHeight("6px");
		burnedWidget.setWidth((factor * burnedHours) + "px");
		flowPanel.add(burnedWidget);

		int remainingHours = requirement.getRemainingWork();
		Widget remainingWidget = Gwt.createEmptyDiv("EstimationBarWidget-bar0");
		remainingWidget.setHeight("6px");
		remainingWidget.setWidth((factor * remainingHours) + "px");
		flowPanel.add(remainingWidget);

		flowPanel.getElement().setTitle(burnedHours + " of " + (burnedHours + remainingHours) + " hours burned.");
	}

	@Override
	protected Widget onInitialization() {
		flowPanel = new FloatingFlowPanel();
		return Gwt.createFloatingFlowPanelRight(Gwt.createDiv("EstimationBarWidget", flowPanel));
	}
}
