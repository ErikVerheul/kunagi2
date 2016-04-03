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
package scrum.client.test;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

public class DndTestWidget extends Composite {

	public DndTestWidget() {
		AbsolutePanel dndPanel = new AbsolutePanel();
		dndPanel.setPixelSize(400, 400);

		final Composite drag = new DragComposite();
		final Label drop = new Label("drop");

		PickupDragController dragController = new PickupDragController(RootPanel.get(), true);
		dragController.setBehaviorDragProxy(true);
		dragController.setBehaviorConstrainedToBoundaryPanel(false);
		dragController.setBehaviorMultipleSelection(true);
		DropController dropController = new DropController() {

                    @Override
			public Widget getDropTarget() {
				return drop;
			}

                    @Override
			public void onDrop(DragContext context) {
				drop.setText(drop.getText() + ".");
			}

                    @Override
			public void onEnter(DragContext context) {}

                    @Override
			public void onLeave(DragContext context) {}

                    @Override
			public void onMove(DragContext context) {}

                    @Override
			public void onPreviewDrop(DragContext context) throws VetoDragException {}
		};

		dragController.registerDropController(dropController);
		dragController.makeDraggable(drag);

		dndPanel.add(drag);
		dndPanel.add(drop);
		initWidget(dndPanel);
	}

	private static class DragComposite extends Composite implements SourcesMouseEvents {

		MouseListenerCollection mouseListeners;

		public DragComposite() {
			initWidget(new Label("ownDragWidget"));
		}

            @Override
		public void addMouseListener(MouseListener listener) {
			if (mouseListeners == null) {
				mouseListeners = new MouseListenerCollection();
				sinkEvents(Event.MOUSEEVENTS);
			}
			mouseListeners.add(listener);
		}

            @Override
		public void removeMouseListener(MouseListener listener) {
			if (mouseListeners != null) {
				mouseListeners.remove(listener);
			}
		}

		@Override
		public void onBrowserEvent(Event event) {
			switch (DOM.eventGetType(event)) {
				case Event.ONMOUSEDOWN:
				case Event.ONMOUSEUP:
				case Event.ONMOUSEMOVE:
				case Event.ONMOUSEOVER:
				case Event.ONMOUSEOUT:
					mouseListeners.fireMouseEvent(this, event);
					break;
                                default:
                                    // do nothing
			}
		}

	}
}
