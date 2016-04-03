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
package scrum.client.files;

import scrum.client.common.ABlockWidget;
import scrum.client.common.AScrumAction;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.dnd.TrashSupport;

import com.google.gwt.user.client.ui.Widget;

public class FileBlock extends ABlockWidget<File> implements TrashSupport {

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		File file = getObject();
		header.addText(file.getLabelModel());
		header.addText(file.getUploadTimeModel(), true);
		header.addMenuAction(new DeleteFileAction(file));
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		File file = getObject();
		header.setDragHandle(file.getReference());
	}

	@Override
	protected Widget onExtendedInitialization() {
		return new FileWidget(getObject());
	}

	@Override
	public AScrumAction getTrashAction() {
		return new DeleteFileAction(getObject());
	}

	public static final BlockWidgetFactory<File> FACTORY = new BlockWidgetFactory<File>() {

		@Override
		public FileBlock createBlock() {
			return new FileBlock();
		}

	};

}
