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
package scrum.client.risks;

import scrum.client.collaboration.EmoticonsWidget;
import scrum.client.common.ABlockWidget;
import scrum.client.common.AScrumAction;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.dnd.TrashSupport;
import scrum.client.journal.ActivateChangeHistoryAction;

import com.google.gwt.user.client.ui.Widget;

public class RiskBlock extends ABlockWidget<Risk> implements TrashSupport {

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		Risk risk = getObject();
		header.addText(risk.getPriorityLabelModel(), "100px", true, false);
		header.addText(risk.getLabelModel());
		header.appendOuterCell(new EmoticonsWidget(risk), null, true);
		header.addMenuAction(new ActivateChangeHistoryAction(risk));
		header.addMenuAction(new DeleteRiskAction(risk));
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		Risk risk = getObject();
		header.setDragHandle(risk.getReference());
	}

	@Override
	protected Widget onExtendedInitialization() {
		return new RiskWidget(getObject());
	}

	@Override
	public AScrumAction getTrashAction() {
		return new DeleteRiskAction(getObject());
	}

	public static final BlockWidgetFactory<Risk> FACTORY = new BlockWidgetFactory<Risk>() {

		@Override
		public RiskBlock createBlock() {
			return new RiskBlock();
		}
	};
}
