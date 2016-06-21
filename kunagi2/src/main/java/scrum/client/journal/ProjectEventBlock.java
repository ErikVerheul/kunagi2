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
package scrum.client.journal;

import scrum.client.common.ABlockWidget;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;

import com.google.gwt.user.client.ui.Widget;

public class ProjectEventBlock extends ABlockWidget<ProjectEvent> {

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		header.setDragHandle("evt");
		header.addText(getObject().getDateAndTimeModel(), "120px", true, true);
		header.addText(getObject().getLabelModel());
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {}

	@Override
	protected Widget onExtendedInitialization() {
		return new ProjectEventWidget(getObject());
	}

	public static final BlockWidgetFactory<ProjectEvent> FACTORY = new BlockWidgetFactory<ProjectEvent>() {

		@Override
		public ProjectEventBlock createBlock() {
			return new ProjectEventBlock();
		}
	};

}
