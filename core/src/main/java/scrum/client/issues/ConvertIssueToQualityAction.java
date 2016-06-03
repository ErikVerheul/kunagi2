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
package scrum.client.issues;

import generated.client.issues.GConvertIssueToQualityAction;
import scrum.client.common.TooltipBuilder;
import scrum.client.project.Quality;

public class ConvertIssueToQualityAction extends GConvertIssueToQualityAction {

	public ConvertIssueToQualityAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Convert to Quality";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Convert this issue to a Quality in the Quality Backlog.");
		if (!issue.getProject().isProductOwner(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
		}
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!issue.getProject().isProductOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		Quality quality = new Quality(issue);
		getDao().createQuality(quality);
		getDao().deleteIssue(issue);
		addUndo(new Undo(quality));
	}

	class Undo extends ALocalUndo {

		private Quality quality;

		public Undo(Quality quality) {
			this.quality = quality;
		}

		@Override
		public String getLabel() {
			return "Undo Convert " + issue.getReference() + " " + issue.getLabel() + " to Quality";
		}

		@Override
		protected void onUndo() {
			getDao().deleteQuality(quality);
			getDao().createIssue(issue);
		}

	}

}
