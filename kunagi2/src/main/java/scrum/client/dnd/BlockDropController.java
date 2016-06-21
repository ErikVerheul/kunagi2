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
package scrum.client.dnd;

import scrum.client.common.ABlockWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.project.CreateRequirementAction;
import scrum.client.project.CreateStoryButtonWidget;
import scrum.client.project.Requirement;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.allen_sauer.gwt.dnd.client.util.CoordinateLocation;
import com.allen_sauer.gwt.dnd.client.util.WidgetArea;
import com.google.gwt.user.client.ui.Widget;

public class BlockDropController implements DropController {

	private BlockListWidget targetList;
	private ABlockWidget targetBlock;

	public BlockDropController(ABlockWidget targetBlock) {
		this.targetBlock = targetBlock;
		this.targetList = targetBlock.getList();
	}

	@Override
	public Widget getDropTarget() {
		return targetBlock;
	}

	@Override
	public void onDrop(DragContext context) {
		Widget draggable = context.draggable;
		if (!isDropAllowed(draggable)) return;

		WidgetArea area = new WidgetArea(targetBlock, null);
		CoordinateLocation location = new CoordinateLocation(context.mouseX, context.mouseY);

		if (draggable instanceof ABlockWidget) {
			// move inside list
			ABlockWidget draggedBlock = (ABlockWidget) draggable;
			int fromIndex = targetList.indexOfBlock(draggedBlock);
			int toIndex = targetList.indexOfBlock(targetBlock);
			if (fromIndex > toIndex) toIndex++;
			if (isHigher(area, location)) toIndex--;
			targetList.drop(draggedBlock, toIndex);
		}

		if (draggable instanceof CreateStoryButtonWidget) {
			CreateStoryButtonWidget button = (CreateStoryButtonWidget) draggable;
			CreateRequirementAction action = button.getAction();
			action.setRelative((Requirement) targetBlock.getObject());
			action.setBefore(isHigher(area, location));
			action.execute();
		}
	}

	@Override
	public void onEnter(DragContext context) {}

	@Override
	public void onLeave(DragContext context) {
		targetList.deactivateDndMarkers();
	}

	@Override
	public void onMove(DragContext context) {
		if (!isDropAllowed(context.draggable)) return;

		WidgetArea area = new WidgetArea(targetBlock, null);
		CoordinateLocation location = new CoordinateLocation(context.mouseX, context.mouseY);
		boolean isHigher = isHigher(area, location);
		if (isHigher) {
			targetBlock.getList().activateDndMarkerBefore(targetBlock);
		} else {
			targetBlock.getList().activateDndMarkerAfter(targetBlock);
		}
	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		if (!isDropAllowed(context.draggable)) { throw new VetoDragException(); }
	}

	private boolean isHigher(WidgetArea area, CoordinateLocation location) {
		int mid = area.getCenter().getTop();
		return location.getTop() < mid;
	}

	private boolean isDropAllowed(Widget draggable) {
		if (draggable instanceof ABlockWidget) {
			ABlockWidget block = (ABlockWidget) draggable;
			return targetBlock.getList().acceptsDrop(block);
		} else if (draggable instanceof CreateStoryButtonWidget) { return true; }
		return false;
	}
}
