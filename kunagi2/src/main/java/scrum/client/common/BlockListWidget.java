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
import static ilarkesto.core.logging.ClientLog.DEBUG;
import static ilarkesto.core.logging.ClientLog.ERROR;
import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.AGwtEntity;
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
 *
 * @param <O>
 */
public final class BlockListWidget<O extends AGwtEntity> extends AScrumWidget {

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

    /**
     *
     * @param blockWidgetFactory
     * @param dropAction
     */
    public BlockListWidget(BlockWidgetFactory<O> blockWidgetFactory, BlockListDropAction<O> dropAction) {
        globals = GlobalsSingleton.getInstance();
        this.dropAction = dropAction;
        this.blockWidgetFactory = blockWidgetFactory;

        dndMarkerBottom = new BlockDndMarkerWidget();
    }

    /**
     *
     * @return
     */
    public ABlockWidget<O> getExtendedBlock() {
        for (ABlockWidget<O> block : getBlocks()) {
            if (block.isExtended()) {
                return block;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public O getExtendedObject() {
        ABlockWidget<O> block = getExtendedBlock();
        return block == null ? null : block.getObject();
    }

    /**
     *
     * @param height
     */
    public void setMinHeight(int height) {
        initialize();
        panel.getElement().getStyle().setProperty("minHeight", height + "px");
    }

    /**
     *
     * @param blockWidgetFactory
     */
    public BlockListWidget(BlockWidgetFactory<O> blockWidgetFactory) {
        this(blockWidgetFactory, null);
    }

    /**
     *
     * @param selectionManager
     */
    public void setSelectionManager(BlockListSelectionManager selectionManager) {
        this.selectionManager = selectionManager;
        selectionManager.add(this);
    }

    @Override
    protected Widget onInitialization() {
        if (dnd) {
            dndManager = Scope.get().getComponent(DndManager.class);
        }

        list = new ObjectMappedFlowPanel<>(
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
        DEBUG("list is initialized");
        
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
        if (globals.isDragging()) {
            return;
        }
        super.onUpdate();
    }

    /**
     *
     * @param styleName
     */
    public void addAdditionalStyleName(String styleName) {
        initialize();
        panel.addStyleName(styleName);
    }

    /**
     *
     * @param styleName
     */
    public void removeAdditionalStyleName(String styleName) {
        initialize();
        panel.removeStyleName(styleName);
    }

    /**
     *
     * @param row
     * @return
     */
    public ABlockWidget<O> getBlock(int row) {
        return list.getWidget(row);
    }

    /**
     *
     * @param object
     * @return
     */
    public O getPrevious(O object) {
        int index = list.getObjects().indexOf(object);
        if (index < 1) {
            return null;
        }
        return list.getObjects().get(index - 1);
    }

    /**
     *
     * @param object
     * @return
     */
    public ABlockWidget<O> getBlock(O object) {
        return list.getWidget(object);
    }

    /**
     *
     * @param orderObserver
     */
    public void setMoveObserver(InsertCallback orderObserver) {
        this.moveObserver = orderObserver;
    }

    /**
     *
     * @param autoSorter
     */
    public final void setAutoSorter(Comparator<O> autoSorter) {
        this.autoSorter = autoSorter;
        if (autoSorter != null) {
            setDndSorting(false);
        }
    }

    /**
     *
     * @param dnd
     */
    public void setDnd(boolean dnd) {
        this.dnd = dnd;
    }

    /**
     *
     * @return
     */
    public final boolean isDndSorting() {
        if (dndManager == null) {
            return false;
        }
        return dndSorting;
    }

    /**
     *
     * @param dndSorting
     */
    public final void setDndSorting(boolean dndSorting) {
        this.dndSorting = dndSorting;
    }

    /**
     *
     */
    public final void clear() {
        initialize();
        list.clear();
    }

    /**
     *
     * @param objects
     */
    public final void setObjects(O... objects) {
        setObjects(Gwt.toList(objects));
    }

    /**
     *
     * @param newObjects
     */
    public final void setObjects(Collection<O> newObjects) {
        setObjects(new ArrayList<O>(newObjects));
    }

    /**
     *
     * @param newObjects
     */
    public final void setObjects(List<O> newObjects) {
        initialize();
        if (autoSorter != null) {
            Collections.sort(newObjects, autoSorter);
        }
        list.set(newObjects);
    }

    /**
     *
     * @param block
     * @param toIndex
     */
    public final void drop(ABlockWidget<O> block, int toIndex) {
        if (null == block) {
            ERROR("object == null");
            return;
        }
        
        O object = block.getObject();
        if (block.getList() == this) {
            list.move(toIndex, object, true, moveObserver);
            return;
        }
        if (dropAction.isDroppable(object)) {
            dropAction.onDrop(object);
        }
    }

    /**
     *
     * @return
     */
    public final int size() {
        return list.size();
    }

    /**
     *
     * @param block
     * @return
     */
    public final int indexOfBlock(ABlockWidget<O> block) {
        return indexOfObject(block.getObject());
    }

    private int indexOfObject(O object) {
        return list.indexOfObject(object);
    }

    /**
     *
     * @param row
     * @param exclusive
     * @return
     */
    public final boolean extendRow(int row, boolean exclusive) {
        if (exclusive) {
            if (selectionManager == null) {
                collapseAll();
            } else {
                selectionManager.deselectAll();
            }
        }

        if (row < 0) {
            return false;
        }

        ABlockWidget<O> block = getBlock(row);
        block.setExtended(true);
        block.activate();
        return true;
    }

    /**
     *
     * @param row
     */
    public final void collapseRow(int row) {
        getBlock(row).setExtended(false);
    }

    /**
     *
     * @param object
     */
    public final void collapseObject(O object) {
        collapseRow(indexOfObject(object));
    }

    /**
     *
     * @param object
     * @return
     */
    public final boolean showObject(O object) {
        Navigator navigator = Scope.get().getComponent(Navigator.class);
        if (navigator != null && navigator.isToggleMode() && isExtended(object)) {
            collapseObject(object);
            return false;
        }
        if (!extendObject(object)) {
            return false;
        }
        scrollToObject(object);
        return true;
    }

    /**
     *
     * @param object
     */
    public final void scrollToObject(O object) {
        getBlock(object).scrollIntoView();
    }

    /**
     *
     * @param block
     * @param exclusive
     */
    public final void extendBlock(ABlockWidget<O> block, boolean exclusive) {
        int idx = indexOfBlock(block);
        extendRow(idx, exclusive);
        if (!isExtended(block.getObject())) {
            ERROR("!isExtended: ", block.getObject());
        }
    }

    /**
     *
     * @param object
     * @return
     */
    public final boolean extendObject(O object) {
        return extendObject(object, true);
    }

    /**
     *
     * @param object
     * @param exclusive
     * @return
     */
    public final boolean extendObject(O object, boolean exclusive) {
        int idx = indexOfObject(object);
        if (idx < 0) {
            return false;
        }
        extendRow(idx, exclusive);
        assert isExtended(object);
        return true;
    }

    /**
     *
     * @param object
     * @param exclusive
     */
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

    /**
     *
     * @param object
     * @return
     */
    public final boolean isExtended(O object) {
        return getBlock(object).isExtended();
    }

    /**
     *
     * @param object
     * @return
     */
    public final boolean contains(O object) {
        return list.containsObject(object);
    }

    /**
     *
     */
    public final void collapseAll() {
        for (ABlockWidget<O> block : list.getWidgets()) {
            block.setExtended(false);
        }
    }

    private ABlockWidget<O> getPreviousBlock(ABlockWidget<O> block) {
        int idx = indexOfBlock(block);
        if (idx < 1) {
            return null;
        }
        ABlockWidget<O> previous = getBlock(idx - 1);
        return previous;
    }

    private ABlockWidget<O> getNextBlock(ABlockWidget<O> block) {
        int idx = indexOfBlock(block);
        if (idx < 0 || idx > size() - 2) {
            return null;
        }
        ABlockWidget<O> next = getBlock(idx + 1);
        return next;
    }

    /**
     *
     */
    public void deactivateDndMarkers() {
        for (ABlockWidget<O> block : list.getWidgets()) {
            block.deactivateDndMarkers();
        }
        dndMarkerBottom.setActive(false);
    }

    /**
     *
     * @param block
     */
    public void deactivateDndMarkers(ABlockWidget<O> block) {
        block.deactivateDndMarkers();
        ABlockWidget<O> previous = getPreviousBlock(block);
        if (previous != null) {
            previous.deactivateDndMarkers();
        }
        ABlockWidget<O> next = getNextBlock(block);
        if (next != null) {
            next.deactivateDndMarkers();
        }
        dndMarkerBottom.setActive(false);
    }

    /**
     *
     * @param block
     */
    public void activateDndMarkerBefore(ABlockWidget<O> block) {
        ABlockWidget<O> previous = getPreviousBlock(block);
        if (previous != null) {
            previous.deactivateDndMarkers();
        }
        ABlockWidget<O> next = getNextBlock(block);
        if (next != null) {
            next.deactivateDndMarkers();
        }
        dndMarkerBottom.setActive(false);

        block.activateDndMarkerTop();
    }

    /**
     *
     * @param block
     */
    public void activateDndMarkerAfter(ABlockWidget<O> block) {
        deactivateDndMarkers(block);
        ABlockWidget<O> next = getNextBlock(block);
        if (next == null) {
            dndMarkerBottom.setActive(true);
        } else {
            next.activateDndMarkerTop();
        }
    }

    /**
     *
     */
    public void activateDrop() {
        dndMarkerBottom.setActive(true);
    }

    /**
     *
     * @return
     */
    public List<O> getObjects() {
        return list.getObjects();
    }

    /**
     *
     * @return
     */
    public Collection<ABlockWidget<O>> getBlocks() {
        return list.getWidgets();
    }

    /**
     *
     * @param block
     * @return
     */
    public boolean acceptsDrop(ABlockWidget<O> block) {
        if (this == block.getList()) {
            return true;
        }
        if (dummy == null) {
            dummy = blockWidgetFactory.createBlock();
        }
        if (!dummy.getClass().getName().equals(block.getClass().getName())) {
            return false;
        }
        return dropAction.isDroppable(block.getObject());
    }

    private ABlockWidget<O> dummy = null;

    @Override
    protected void onLoad() {
        super.onLoad();
        if (dnd && dndManager != null) {
            dndManager.registerDropTarget(this);
        }
    }

    @Override
    protected void onUnload() {
        if (dnd && dndManager != null) {
            dndManager.unregisterDropTarget(this);
        }
        super.onUnload();
    }

    private void updateTaskHighlighting() {
        for (ABlockWidget<O> block : list.getWidgets()) {
            if (highlightPredicate != null && highlightPredicate.contains(block.getObject())) {
                block.addStyleName("highlighted");
            } else {
                block.removeStyleName("highlighted");
            }
        }
    }

    /**
     *
     * @param predicate
     */
    public void setTaskHighlighting(ElementPredicate<O> predicate) {
        this.highlightPredicate = predicate;
        updateTaskHighlighting();
    }

    /**
     *
     */
    public void clearTaskHighlighting() {
        this.highlightPredicate = null;
        updateTaskHighlighting();
    }

}
