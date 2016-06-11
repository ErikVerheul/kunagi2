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

import ilarkesto.core.base.Str;
import scrum.client.common.TooltipBuilder;

public class PublishBlogEntryAction extends GPublishBlogEntryAction {

	public PublishBlogEntryAction(scrum.client.pr.BlogEntry blogEntry) {
		super(blogEntry);
	}

	@Override
	public String getLabel() {
		return "Publish";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Publish this Blog entry. Published entries are taken into account when exporting project data.");
		if (!isPermitted()) tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER_NOR_SCRUMMASTER);
	}

	@Override
	public boolean isPermitted() {
		if (!blogEntry.getProject().isProductOwnerOrScrumMaster(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (blogEntry.isPublished()) return false;
		if (Str.isBlank(blogEntry.getTitle())) return false;
		if (Str.isBlank(blogEntry.getText())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		blogEntry.setPublished(true);
		addUndo(new Undo());
	}

	private class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Publish " + blogEntry.getReference() + " " + blogEntry.getTitle();
		}

		@Override
		protected void onUndo() {
			blogEntry.setPublished(false);
		}

	}

}