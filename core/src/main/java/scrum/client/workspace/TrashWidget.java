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

import ilarkesto.core.scope.Scope;
import scrum.client.common.AScrumWidget;
import scrum.client.common.StyleSheet;
import scrum.client.dnd.TrashSupport;
import scrum.client.img.Img;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TrashWidget extends AScrumWidget {

	private DndManager dndManager;
	private HorizontalPanel trash;
	private DropController trashDropController = new TrashDropController();

	@Override
	protected Widget onInitialization() {
		dndManager = Scope.get().getComponent(DndManager.class);

		trash = new HorizontalPanel();
		trash.setStyleName("TrashWidget");
		trash.add(Img.bundle.trash32().createImage());
		// trash.add(new Label("Trash"));

		dndManager.getDragController().registerDropController(trashDropController);

		return trash;
	}

	@Override
	protected void onUpdate() {}

	private boolean isTrashable(Widget draggable) {
		boolean trashable;
		if (draggable instanceof TrashSupport) {
			trashable = ((TrashSupport) draggable).getTrashAction().isExecutable();
		} else {
			trashable = false;
		}
		return trashable;
	}

	private class TrashDropController implements DropController {

		@Override
		public Widget getDropTarget() {
			return trash;
		}

		@Override
		public void onDrop(DragContext context) {
			if (!isTrashable(context.draggable)) return;
			((TrashSupport) context.draggable).getTrashAction().execute();
		}

		@Override
		public void onEnter(DragContext context) {
			if (!isTrashable(context.draggable)) { return; }
			trash.addStyleName(StyleSheet.DND_DROP_ALLOWED);
		}

		@Override
		public void onLeave(DragContext context) {
			trash.removeStyleName(StyleSheet.DND_DROP_ALLOWED);
		}

		@Override
		public void onMove(DragContext context) {}

		@Override
		public void onPreviewDrop(DragContext context) throws VetoDragException {}

	};

}
