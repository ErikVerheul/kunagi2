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

import generated.scrum.client.impediments.GCloseImpedimentAction;
import scrum.client.common.TooltipBuilder;

public class CloseImpedimentAction extends GCloseImpedimentAction {

	public CloseImpedimentAction(scrum.client.impediments.Impediment impediment) {
		super(impediment);
	}

	@Override
	public String getLabel() {
		return "Close";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Close this Impediment, marking it as solved or obsolete.");
		if (!impediment.getProject().isScrumTeamMember(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
		}
	}

	@Override
	public boolean isExecutable() {
		if (impediment.isClosed()) return false;
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!impediment.getProject().isScrumTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		impediment.setClosed(true);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Close " + impediment.getReference() + " " + impediment.getLabel();
		}

		@Override
		protected void onUndo() {
			impediment.setClosed(false);
		}

	}

}