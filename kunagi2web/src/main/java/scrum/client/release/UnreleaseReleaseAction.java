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
package scrum.client.release;

import scrum.client.release.GUnreleaseReleaseAction;
import ilarkesto.core.time.Date;
import scrum.client.common.TooltipBuilder;

public class UnreleaseReleaseAction extends GUnreleaseReleaseAction {

	public UnreleaseReleaseAction(scrum.client.release.Release release) {
		super(release);
	}

	@Override
	public String getLabel() {
		return "Mark as planned";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Mark this release as not published and not available to the users.");
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
	}

	@Override
	public boolean isPermitted() {
		if (!release.getProject().isScrumTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (!release.isReleased()) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		Date previousDate = release.getReleaseDate();
		release.setScriptOutput(null);
		release.setReleaseDate(Date.today());
		release.setReleased(false);
		addUndo(new Undo(previousDate));
	}

	class Undo extends ALocalUndo {

		private Date date;

		public Undo(Date date) {
			super();
			this.date = date;
		}

		@Override
		public String getLabel() {
			return "Undo Mark as planned " + release.getReference() + " " + release.getLabel();
		}

		@Override
		protected void onUndo() {
			release.setReleaseDate(date);
			release.setReleased(true);
		}

	}
}