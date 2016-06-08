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
package scrum.client.workspace;

import generated.scrum.client.workspace.GDndManager;
import java.util.HashMap;
import java.util.Map;

import scrum.client.common.ABlockWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.dnd.BlockDropController;
import scrum.client.dnd.BlockListDropController;
import scrum.client.dnd.ScrumDragController;

import com.google.gwt.user.client.ui.Widget;

public class DndManager extends GDndManager {

	private final ScrumDragController dragController;
	private final Map<ABlockWidget, BlockDropController> blockDropControllers;
	private final Map<BlockListWidget, BlockListDropController> blockListDropControllers;

	public DndManager() {
		dragController = new ScrumDragController();
		blockDropControllers = new HashMap<ABlockWidget, BlockDropController>();
		blockListDropControllers = new HashMap<BlockListWidget, BlockListDropController>();
	}

	public void registerDropTarget(BlockListWidget list) {
		BlockListDropController dropController = new BlockListDropController(list);
		dragController.registerDropController(dropController);
		blockListDropControllers.put(list, dropController);
	}

	public void unregisterDropTarget(BlockListWidget list) {
		BlockListDropController dropController = blockListDropControllers.get(list);
		if (dropController != null) {
			dragController.unregisterDropController(dropController);
			blockListDropControllers.remove(list);
		}
	}

	public void registerDropTarget(ABlockWidget block) {
		BlockDropController dropController = new BlockDropController(block);
		dragController.registerDropController(dropController);
		blockDropControllers.put(block, dropController);
	}

	public void unregisterDropTarget(ABlockWidget block) {
		BlockDropController dropController = blockDropControllers.get(block);
		if (dropController != null) {
			dragController.unregisterDropController(dropController);
			blockDropControllers.remove(block);
		}
	}

	public void makeDraggable(Widget widget, Widget dragHandle) {
		dragController.makeDraggable(widget, dragHandle);
	}

	public ScrumDragController getDragController() {
		return dragController;
	}
}
