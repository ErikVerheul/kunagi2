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
package scrum.client.calendar;

import scrum.client.common.ABlockWidget;
import scrum.client.common.AScrumAction;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.dnd.TrashSupport;

import com.google.gwt.user.client.ui.Widget;

public class SimpleEventBlock extends ABlockWidget<SimpleEvent> implements TrashSupport {

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		SimpleEvent event = getObject();
		header.addText(event.getTimeModel(), "35px", true, true);
		header.addText(event.getLabelModel());
		header.addText(event.getDurationModel(), true);
		header.addMenuAction(new PublishSimpleEventAction(event));
		header.addMenuAction(new DeleteSimpleEventAction(event));
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		SimpleEvent event = getObject();
		header.setDragHandle(event.getReference());
	}

	@Override
	protected Widget onExtendedInitialization() {
		return new SimpleEventWidget(getObject());
	}

	@Override
	public AScrumAction getTrashAction() {
		return new DeleteSimpleEventAction(getObject());
	}

	public static final BlockWidgetFactory<SimpleEvent> FACTORY = new BlockWidgetFactory<SimpleEvent>() {

		@Override
		public SimpleEventBlock createBlock() {
			return new SimpleEventBlock();
		}

	};

}
