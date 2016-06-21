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
package scrum.client.dnd;

import scrum.client.common.ABlockWidget;
import scrum.client.common.BlockListWidget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.user.client.ui.Widget;

public class BlockListDropController implements DropController {

	private BlockListWidget list;

	public BlockListDropController(BlockListWidget list) {
		this.list = list;
	}

	@Override
	public Widget getDropTarget() {
		return list;
	}

	@Override
	public void onDrop(DragContext context) {
		if (!isDropAllowed(context.draggable)) return;

		list.drop((ABlockWidget) context.draggable, 0);
	}

	@Override
	public void onEnter(DragContext context) {
		if (!isDropAllowed(context.draggable)) return;
		list.activateDrop();
	}

	@Override
	public void onLeave(DragContext context) {
		list.deactivateDndMarkers();
	}

	@Override
	public void onMove(DragContext context) {}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {}

	private boolean isDropAllowed(Widget draggable) {
		if (draggable instanceof ABlockWidget) {
			ABlockWidget block = (ABlockWidget) draggable;
			if (block.getList() == list) return false;
			return list.acceptsDrop(block);
		}
		return false;
	}

}
