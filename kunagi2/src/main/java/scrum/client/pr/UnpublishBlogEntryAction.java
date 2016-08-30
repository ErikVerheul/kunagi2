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
package scrum.client.pr;

/**
 *
 *
 */
public class UnpublishBlogEntryAction extends GUnpublishBlogEntryAction {

    /**
     *
     * @param blogEntry
     */
    public UnpublishBlogEntryAction(scrum.client.pr.BlogEntry blogEntry) {
		super(blogEntry);
	}

	@Override
	public String getLabel() {
		return "Unpublish this Blog entry";
	}

	@Override
	public boolean isPermitted() {
		return blogEntry.getProject().isProductOwnerOrScrumMaster(getCurrentUser());
	}

	@Override
	public boolean isExecutable() {
		return blogEntry.isPublished();
	}

	@Override
	protected void onExecute() {
		blogEntry.setPublished(false);
	}

	private class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Unpublish " + blogEntry.getReference() + " " + blogEntry.getTitle();
		}

		@Override
		protected void onUndo() {
			blogEntry.setPublished(true);
		}

	}
}