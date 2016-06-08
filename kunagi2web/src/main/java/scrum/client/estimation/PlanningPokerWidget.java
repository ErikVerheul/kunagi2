/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.estimation;

import scrum.client.common.AScrumWidget;
import scrum.client.project.Requirement;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PlanningPokerWidget extends AScrumWidget {

	private Requirement requirement;
	private SimplePanel wrapper;
	private PlanningPokerTableWidget table;

	public PlanningPokerWidget(Requirement requirement) {
		super();
		this.requirement = requirement;
	}

	@Override
	protected Widget onInitialization() {
		wrapper = new SimplePanel();
		return wrapper;
	}

	@Override
	protected void onUpdate() {
		if (requirement.isWorkEstimationVotingActive()) {
			if (table == null) table = new PlanningPokerTableWidget(requirement);
			wrapper.setWidget(table);
		} else {
			wrapper.setWidget(null);
		}
		super.onUpdate();
	}

}
