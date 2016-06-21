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
package scrum.client.collaboration;

import scrum.client.common.TooltipBuilder;

public class PublishCommentAction extends GPublishCommentAction {

	public PublishCommentAction(Comment comment) {
		super(comment);
	}

	@Override
	public String getLabel() {
		return "Publish";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Make this comment visible on the homepage.");
	}

	@Override
	public boolean isExecutable() {
		if (comment.isPublished()) return false;
		if (getCurrentProject().getHomepageDir() == null) return false;
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!comment.isAuthor(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		comment.setPublished(true);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Publish Comment";
		}

		@Override
		protected void onUndo() {
			comment.setPublished(false);
		}
	}

}