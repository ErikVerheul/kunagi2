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

import ilarkesto.core.time.Date;
import ilarkesto.gwt.client.AnchorPanel;
import ilarkesto.gwt.client.editor.AFieldModel;
import scrum.client.collaboration.EmoticonsWidget;
import scrum.client.common.ABlockWidget;
import scrum.client.common.AScrumAction;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.dnd.TrashSupport;
import scrum.client.img.Img;
import scrum.client.journal.ActivateChangeHistoryAction;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ReleaseBlock extends ABlockWidget<Release> implements TrashSupport {

	private AnchorPanel typeIcon;

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		Release release = getObject();
		typeIcon = header.addIconWrapper();
		header.addText(release.getLabelModel());
		header.addText(createDateSuffixModel(), true);
		header.addText(release.getParentReleaseLabelModel(), true);
		header.appendOuterCell(new EmoticonsWidget(release), null, true);
		header.addMenuAction(new CreateBugfixReleaseAction(release));
		header.addMenuAction(new ReleaseReleaseAction(release));
		header.addMenuAction(new UnreleaseReleaseAction(release));
		header.addMenuAction(new CreateBlogEntryAction(release));
		header.addMenuAction(new ActivateChangeHistoryAction(release));
		header.addMenuAction(new DeleteReleaseAction(release));
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		Release release = getObject();
		typeIcon.setWidget(createTypeIcon());
		header.setDragHandle(release.getReference());
	}

	private Image createTypeIcon() {
		Release release = getObject();
		Image image;
		if (release.isBugfix()) {
			image = Img.bundle.bugfixRelease().createImage();
			image.setTitle("Bugfix Release");
		} else {
			image = Img.bundle.majorRelease().createImage();
			image.setTitle("Major Release");
		}
		return image;
	}

	private AFieldModel<String> createDateSuffixModel() {
		return new AFieldModel<String>() {

			@Override
			public String getValue() {
				Release release = getObject();
				String dateSuffix = null;
				Date date = release.getReleaseDate();
				if (date != null) {
					dateSuffix = date.toString();
					Date today = Date.today();
					if (date.isAfter(today)) {
						dateSuffix += " (in " + today.getPeriodTo(date).toDays() + " days)";
					} else if (date.isBefore(today)) {
						dateSuffix += " (" + date.getPeriodTo(today).toShortestString() + " ago)";
					} else {
						dateSuffix += " (today)";
					}
				} else {
					dateSuffix = "unscheduled";
				}
				return dateSuffix;
			}
		};
	}

	@Override
	protected Widget onExtendedInitialization() {
		return new ReleaseWidget(getObject());
	}

	@Override
	public AScrumAction getTrashAction() {
		return new DeleteReleaseAction(getObject());
	}

	public static final BlockWidgetFactory<Release> FACTORY = new BlockWidgetFactory<Release>() {

		@Override
		public ReleaseBlock createBlock() {
			return new ReleaseBlock();
		}
	};
}
