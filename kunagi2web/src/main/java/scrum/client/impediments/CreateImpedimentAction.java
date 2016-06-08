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

import generated.scrum.client.impediments.GCreateImpedimentAction;
import scrum.client.common.TooltipBuilder;

public class CreateImpedimentAction extends GCreateImpedimentAction {

	@Override
	public String getLabel() {
		return "Create Impediment";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create new Impediment.");
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		Impediment impediment = getCurrentProject().createNewImpediment();
		getNavigator().gotoPageWithEntity(null, impediment);
	}

}
