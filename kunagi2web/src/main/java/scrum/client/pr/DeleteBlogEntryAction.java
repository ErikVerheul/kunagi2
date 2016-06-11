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
package scrum.client.pr;

import scrum.client.common.TooltipBuilder;

public class DeleteBlogEntryAction extends GDeleteBlogEntryAction {

	public DeleteBlogEntryAction(scrum.client.pr.BlogEntry blogEntry) {
		super(blogEntry);
	}

	@Override
	public String getLabel() {
		return "Delete";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Delete this Blog entry permanently.");
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
	}

	@Override
	public boolean isExecutable() {
		if (blogEntry.isPublished()) return false;
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		getDao().deleteBlogEntry(blogEntry);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Delete " + blogEntry.getReference() + " " + blogEntry.getTitle();
		}

		@Override
		protected void onUndo() {
			getDao().createBlogEntry(blogEntry);
		}

	}
}