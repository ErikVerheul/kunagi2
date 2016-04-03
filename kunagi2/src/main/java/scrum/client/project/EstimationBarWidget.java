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
package scrum.client.project;

import ilarkesto.gwt.client.FloatingFlowPanel;
import ilarkesto.gwt.client.Gwt;

import java.util.ArrayList;
import java.util.List;

import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.Widget;

public class EstimationBarWidget extends AScrumWidget {

	private FloatingFlowPanel flowPanel;
	private static int factor = 3;

	private Requirement requirement;

	public EstimationBarWidget(Requirement requirement) {
		this.requirement = requirement;
	}

	@Override
	protected void onUpdate() {
		EstimationBar bar = requirement == null ? null : requirement.getEstimationBar();
		if (bar == null) bar = new EstimationBar(0, new ArrayList<Float>());
		flowPanel.clear();
		List<Float> estimations = bar.getWorkPerSprint();
		int sprintOffset = bar.getSprintOffset();

		for (int i = 0; i < estimations.size(); i++) {

			int barIndex;
			if ((i + sprintOffset) % 2 == 0) {
				barIndex = 0;
			} else {
				barIndex = 1;
			}
			Widget w = Gwt.createEmptyDiv("EstimationBarWidget-bar" + barIndex);
			w.setHeight("6px");
			w.setWidth((factor * estimations.get(i)) + "px");
			flowPanel.add(w);
		}

		String tip;

		int requiredSprints = estimations.size() <= 1 ? sprintOffset + 1 : sprintOffset + estimations.size();
		tip = "Expected  to be completed after " + requiredSprints + " sprint" + (requiredSprints == 1 ? "." : "s.");

		flowPanel.getElement().setTitle(tip);
	}

	@Override
	protected Widget onInitialization() {
		flowPanel = new FloatingFlowPanel();
		return Gwt.createFloatingFlowPanelRight(Gwt.createDiv("EstimationBarWidget", flowPanel));
	}
}
