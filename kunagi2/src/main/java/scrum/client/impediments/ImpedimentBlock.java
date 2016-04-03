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

import ilarkesto.gwt.client.AnchorPanel;
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

public class ImpedimentBlock extends ABlockWidget<Impediment> implements TrashSupport {

	private AnchorPanel statusIcon;

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		Impediment impediment = getObject();
		header.addText(impediment.getDateModel(), "70px", true, true);
		statusIcon = header.addIconWrapper();
		header.addText(impediment.getLabelModel());
		header.appendOuterCell(new EmoticonsWidget(impediment), null, true);
		header.addMenuAction(new ActivateChangeHistoryAction(impediment));
		header.addMenuAction(new CloseImpedimentAction(impediment));
		header.addMenuAction(new ReopenImpedimentAction(impediment));
		header.addMenuAction(new DeleteImpedimentAction(impediment));
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		Impediment impediment = getObject();
		header.setDragHandle(impediment.getReference());
		Image statusImage = null;
		if (impediment.isOpen()) {
			statusImage = Img.bundle.impOpen().createImage();
			statusImage.setTitle("Still impeding.");
		}
		statusIcon.setWidget(statusImage);
	}

	@Override
	protected Widget onExtendedInitialization() {
		return new ImpedimentWidget(getObject());
	}

	@Override
	public AScrumAction getTrashAction() {
		return new DeleteImpedimentAction(getObject());
	}

	public static final BlockWidgetFactory<Impediment> FACTORY = new BlockWidgetFactory<Impediment>() {

		@Override
		public ImpedimentBlock createBlock() {
			return new ImpedimentBlock();
		}

	};

}
