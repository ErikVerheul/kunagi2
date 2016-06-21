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
package scrum.client.project;

import scrum.client.collaboration.EmoticonsWidget;
import scrum.client.common.ABlockWidget;
import scrum.client.common.AScrumAction;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.dnd.TrashSupport;

import com.google.gwt.user.client.ui.Widget;

public class QualityBlock extends ABlockWidget<Quality> implements TrashSupport {

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		Quality quality = getObject();
		header.appendOuterCell(new EmoticonsWidget(quality), null, true);
		header.addText(quality.getLabelModel());
		header.addMenuAction(new DeleteQualityAction(quality));
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		Quality quality = getObject();
		header.setDragHandle(quality.getReference());
	}

	@Override
	protected Widget onExtendedInitialization() {
		return new QualityWidget(getObject());
	}

	@Override
	public AScrumAction getTrashAction() {
		return new DeleteQualityAction(getObject());
	}

	public static final BlockWidgetFactory<Quality> FACTORY = new BlockWidgetFactory<Quality>() {

		@Override
		public QualityBlock createBlock() {
			return new QualityBlock();
		}
	};
}
