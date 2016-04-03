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
package scrum.client.common;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.ObjectMappedFlowPanel;
import ilarkesto.gwt.client.animation.AnimatingFlowPanel.InsertCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import scrum.client.dnd.BlockDndMarkerWidget;
import scrum.client.dnd.BlockListDropAction;
import scrum.client.workspace.DndManager;
import scrum.client.workspace.Navigator;

/**
 * List of <code>BlockWidget</code>s.
 */
public final class BlockListWidget<O> extends AScrumWidget {

	// private static final Logger LOG = Logger.get(BlockListWidget.class);

	private GlobalsSingleton globals;
        DndManager dndManager;

	private ObjectMappedFlowPanel<O, ABlockWidget<O>> list;
	private boolean dnd = true;
	private boolean dndSorting = true;
	private Comparator<O> autoSorter;
	private BlockWidgetFactory<O> blockWidgetFactory;
	private InsertCallback moveObserver;
	private BlockListSelectionManager selectionManager;
	private BlockListDropAction<O> dropAction;
	private BlockDndMarkerWidget dndMarkerBottom;
	private FlowPanel panel;
	private ElementPredicate<O> highlightPredicate;

	public BlockListWidget(BlockWidgetFactory<O> blockWidgetFactory, BlockListDropAction<O> dropAction) {
                globals = GlobalsSingleton.getInstance();
		this.dropAction = dropAction;
		this.blockWidgetFactory = blockWidgetFactory;

		dndMarkerBottom = new BlockDndMarkerWidget();
	}

	public ABlockWidget<O> getExtendedBlock() {
		for (ABlockWidget<O> block : getBlocks()) {
			if (block.isExtended()) return block;
		}
		return null;
	}

	public O getExtendedObject() {
		ABlockWidget<O> block = getExtendedBlock();
		return block == null ? null : block.getObject();
	}

	public void setMinHeight(int height) {
		initialize();
		panel.getElement().getStyle().setProperty("minHeight", height + "px");
	}

	public BlockListWidget(BlockWidgetFactory<O> blockWidgetFactory) {
		this(blockWidgetFactory, null);
	}

	public void setSelectionManager(BlockListSelectionManager selectionManager) {
		this.selectionManager = selectionManager;
		selectionManager.add(this);
	}

	@Override
	protected Widget onInitialization() {
		if (dnd) dndManager = Scope.get().getComponent(DndManager.class);

		list = new ObjectMappedFlowPanel<O, ABlockWidget<O>>(
				new ObjectMappedFlowPanel.WidgetFactory<O, ABlockWidget<O>>() {

					@Override
					public ABlockWidget<O> createWidget(O object) {
						ABlockWidget<O> block = blockWidgetFactory.createBlock();
						block.setObject(object);
						block.setList(BlockListWidget.this);
						block.update();
						return block;
					}
				});
		list.setMoveObserver(new ObjectMappedFlowPanel.MoveObserver<O, ABlockWidget<O>>() {

			@Override
			public void moved(O object, ABlockWidget<O> oldWidget, ABlockWidget<O> newWidget) {
				newWidget.setExtended(oldWidget.isExtended());
			}
		});

		panel = new FlowPanel();
		panel.setStyleName("BlockListWidget");
		panel.add(list);
		panel.add(dndMarkerBottom);
		return panel;
	}

	@Override
	protected void onUpdate() {
		if (globals.isDragging()) return;
		super.onUpdate();
	}

	public void addAdditionalStyleName(String styleName) {
		initialize();
		panel.addStyleName(styleName);
	}

	public void removeAdditionalStyleName(String styleName) {
		initialize();
		panel.removeStyleName(styleName);
	}

	public ABlockWidget<O> getBlock(int row) {
		return list.getWidget(row);
	}

	public O getPrevious(O object) {
		int index = list.getObjects().indexOf(object);
		if (index < 1) return null;
		return list.getObjects().get(index - 1);
	}

	public ABlockWidget<O> getBlock(O object) {
		return list.getWidget(object);
	}

	public void setMoveObserver(InsertCallback orderObserver) {
		this.moveObserver = orderObserver;
	}

	public final void setAutoSorter(Comparator<O> autoSorter) {
		this.autoSorter = autoSorter;
		if (autoSorter != null) setDndSorting(false);
	}

	public void setDnd(boolean dnd) {
		this.dnd = dnd;
	}

	public final boolean isDndSorting() {
		if (dndManager == null) return false;
		return dndSorting;
	}

	public final void setDndSorting(boolean dndSorting) {
		this.dndSorting = dndSorting;
	}

	public final void clear() {
		initialize();
		list.clear();
	}

	public final void setObjects(O... objects) {
		setObjects(Gwt.toList(objects));
	}

	public final void setObjects(Collection<O> newObjects) {
		setObjects(new ArrayList<O>(newObjects));
	}

	public final void setObjects(List<O> newObjects) {
		initialize();
		if (autoSorter != null) {
			Collections.sort(newObjects, autoSorter);
		}
		list.set(newObjects);
	}

	public final void drop(ABlockWidget<O> block, int toIndex) {
		assert block != null;
		O object = block.getObject();
		if (block.getList() == this) {
			list.move(toIndex, object, true, moveObserver);
			return;
		}
		if (dropAction.isDroppable(object)) dropAction.onDrop(object);
	}

	public final int size() {
		return list.size();
	}

	public final int indexOfBlock(ABlockWidget<O> block) {
		return indexOfObject(block.getObject());
	}

	private int indexOfObject(O object) {
		return list.indexOfObject(object);
	}

	public final boolean extendRow(int row, boolean exclusive) {
		if (exclusive) {
			if (selectionManager == null) {
				collapseAll();
			} else {
				selectionManager.deselectAll();
			}
		}

		if (row < 0) return false;

		ABlockWidget<O> block = getBlock(row);
		block.setExtended(true);
		block.activate();
		return true;
	}

	public final void collapseRow(int row) {
		getBlock(row).setExtended(false);
	}

	public final void collapseObject(O object) {
		collapseRow(indexOfObject(object));
	}

	public final boolean showObject(O object) {
		Navigator navigator = Scope.get().getComponent(Navigator.class);
		if (navigator != null && navigator.isToggleMode() && isExtended(object)) {
			collapseObject(object);
			return false;
		}
		if (!extendObject(object)) return false;
		scrollToObject(object);
		return true;
	}

	public final void scrollToObject(O object) {
		getBlock(object).scrollIntoView();
	}

	public final void extendBlock(ABlockWidget<O> block, boolean exclusive) {
		int idx = indexOfBlock(block);
		extendRow(idx, exclusive);
		assert isExtended(block.getObject());
	}

	public final boolean extendObject(O object) {
		return extendObject(object, true);
	}

	public final boolean extendObject(O object, boolean exclusive) {
		int idx = indexOfObject(object);
		if (idx < 0) { return false; }
		extendRow(idx, exclusive);
		assert isExtended(object);
		return true;
	}

	public final void toggleExtension(O object, boolean exclusive) {
		if (isExtended(object)) {
			if (exclusive) {
				if (selectionManager != null) {
					selectionManager.deselectAll();
				} else {
					collapseAll();
				}
			} else {
				collapseObject(object);
			}
		} else {
			extendObject(object, exclusive);
		}
	}

	public final boolean isExtended(O object) {
		return getBlock(object).isExtended();
	}

	public final boolean contains(O object) {
		return list.containsObject(object);
	}

	public final void collapseAll() {
		for (ABlockWidget<O> block : list.getWidgets()) {
			block.setExtended(false);
		}
	}

	private ABlockWidget<O> getPreviousBlock(ABlockWidget<O> block) {
		int idx = indexOfBlock(block);
		if (idx < 1) return null;
		ABlockWidget<O> previous = getBlock(idx - 1);
		assert list.indexOfObject(previous.getObject()) == list.indexOfObject(block.getObject()) - 1;
		return previous;
	}

	private ABlockWidget<O> getNextBlock(ABlockWidget<O> block) {
		int idx = indexOfBlock(block);
		if (idx < 0 || idx > size() - 2) return null;
		ABlockWidget<O> next = getBlock(idx + 1);
		assert list.indexOfObject(next.getObject()) == list.indexOfObject(block.getObject()) + 1;
		return next;
	}

	public void deactivateDndMarkers() {
		for (ABlockWidget<O> block : list.getWidgets()) {
			block.deactivateDndMarkers();
		}
		dndMarkerBottom.setActive(false);
	}

	public void deactivateDndMarkers(ABlockWidget<O> block) {
		block.deactivateDndMarkers();
		ABlockWidget<O> previous = getPreviousBlock(block);
		if (previous != null) previous.deactivateDndMarkers();
		ABlockWidget<O> next = getNextBlock(block);
		if (next != null) next.deactivateDndMarkers();
		dndMarkerBottom.setActive(false);
	}

	public void activateDndMarkerBefore(ABlockWidget<O> block) {
		ABlockWidget<O> previous = getPreviousBlock(block);
		if (previous != null) previous.deactivateDndMarkers();
		ABlockWidget<O> next = getNextBlock(block);
		if (next != null) next.deactivateDndMarkers();
		dndMarkerBottom.setActive(false);

		block.activateDndMarkerTop();
	}

	public void activateDndMarkerAfter(ABlockWidget<O> block) {
		deactivateDndMarkers(block);
		ABlockWidget<O> next = getNextBlock(block);
		if (next == null) {
			dndMarkerBottom.setActive(true);
		} else {
			next.activateDndMarkerTop();
		}
	}

	public void activateDrop() {
		dndMarkerBottom.setActive(true);
	}

	public List<O> getObjects() {
		return list.getObjects();
	}

	public Collection<ABlockWidget<O>> getBlocks() {
		return list.getWidgets();
	}

	public boolean acceptsDrop(ABlockWidget<O> block) {
		if (this == block.getList()) return true;
		if (dummy == null) dummy = blockWidgetFactory.createBlock();
		if (!dummy.getClass().getName().equals(block.getClass().getName())) return false;
		return dropAction.isDroppable(block.getObject());
	}

	private ABlockWidget<O> dummy = null;

	@Override
	protected void onLoad() {
		super.onLoad();
		if (dnd && dndManager != null) dndManager.registerDropTarget(this);
	}

	@Override
	protected void onUnload() {
		if (dnd && dndManager != null) dndManager.unregisterDropTarget(this);
		super.onUnload();
	}

	private void updateTaskHighlighting() {
		for (ABlockWidget<O> block : list.getWidgets()) {
			if (highlightPredicate != null && highlightPredicate.contains(block.getObject()))
				block.addStyleName("highlighted");
			else block.removeStyleName("highlighted");
		}
	}

	public void setTaskHighlighting(ElementPredicate<O> predicate) {
		this.highlightPredicate = predicate;
		updateTaskHighlighting();
	}

	public void clearTaskHighlighting() {
		this.highlightPredicate = null;
		updateTaskHighlighting();
	}

}
