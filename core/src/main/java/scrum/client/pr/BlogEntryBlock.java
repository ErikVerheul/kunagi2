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

import ilarkesto.gwt.client.AnchorPanel;
import scrum.client.collaboration.EmoticonsWidget;
import scrum.client.common.ABlockWidget;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.img.Img;
import scrum.client.journal.ActivateChangeHistoryAction;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class BlogEntryBlock extends ABlockWidget<BlogEntry> {

	private AnchorPanel statusIcon;

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		BlogEntry blogEntry = getObject();
		statusIcon = header.addIconWrapper();
		header.addText(blogEntry.getTitleModel());
		header.addText(blogEntry.getDateModel(), true);
		header.appendOuterCell(new EmoticonsWidget(blogEntry), null, true);
		header.addMenuAction(new PublishBlogEntryAction(blogEntry));
		header.addMenuAction(new UnpublishBlogEntryAction(blogEntry));
		header.addMenuAction(new ActivateChangeHistoryAction(blogEntry));
		header.addMenuAction(new DeleteBlogEntryAction(blogEntry));
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		BlogEntry blogEntry = getObject();
		header.setDragHandle(blogEntry.getReference());
		Image statusImage = null;
		if (blogEntry.isPublished()) {
			statusImage = Img.bundle.blgPublished().createImage();
			statusImage.setTitle("Published.");
		}
		statusIcon.setWidget(statusImage);

	}

	@Override
	protected Widget onExtendedInitialization() {
		return new BlogEntryWidget(getObject());
	}

	public static final BlockWidgetFactory<BlogEntry> FACTORY = new BlockWidgetFactory<BlogEntry>() {

		@Override
		public BlogEntryBlock createBlock() {
			return new BlogEntryBlock();
		}
	};

}
