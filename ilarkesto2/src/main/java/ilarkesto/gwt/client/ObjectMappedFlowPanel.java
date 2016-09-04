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
package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import static ilarkesto.core.logging.ClientLog.ERROR;
import ilarkesto.gwt.client.animation.AnimatingFlowPanel;
import ilarkesto.gwt.client.animation.AnimatingFlowPanel.InsertCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @param <O>
 * @param <W>
 */
public class ObjectMappedFlowPanel<O extends AGwtEntity, W extends Widget> extends Composite implements HasWidgets {

    /**
     *
     */
    public final static Map<Object, Integer> OBJECTHEIGHTS = new HashMap<>();

    private final AnimatingFlowPanel<W> panel;
    private final WidgetFactory<O, W> widgetFactory;
    private MoveObserver<O, W> moveObserver;

    private final List<O> objectList;
    private final Map<String, W> widgetMap;
    private boolean virgin = true;

    /**
     *
     * @param widgetFactory
     */
    public ObjectMappedFlowPanel(WidgetFactory<O, W> widgetFactory) {
        this.widgetFactory = widgetFactory;
        objectList = new ArrayList<>();
        widgetMap = new HashMap<>();
        panel = new AnimatingFlowPanel<>();
        initWidget(panel);
    }

    /**
     *
     * @param newObjects
     */
    public void set(List<O> newObjects) {
        boolean animationAllowed = !virgin;
        virgin = false;
        if (objectList.equals(newObjects)) {
            return;
        }

        // remove old objects
        List<O> objectsToRemove = getOtherObjects(newObjects);
        if (!objectsToRemove.isEmpty()) {
            boolean animate = animationAllowed && objectsToRemove.size() == 1;
            for (O object : objectsToRemove) {
                remove(object, animate);
            }
        }

        if (objectList.equals(newObjects)) {
            return;
        }

        // add new objects
        List<O> objectsToAdd = new ArrayList<>(newObjects);
        objectsToAdd.removeAll(objectList);
        if (!objectsToAdd.isEmpty()) {
            boolean animate = animationAllowed && objectsToAdd.size() == 1;
            for (O object : objectsToAdd) {
                int index = newObjects.indexOf(object);
                if (index > objectList.size()) {
                    index = objectList.size();
                }
                insert(index, object, animate, null);
            }
        }

        if (objectList.equals(newObjects)) {
            return;
        }

        // move existing objects
        int index = 0;
        for (O object : newObjects) {
            int currentIndex = objectList.indexOf(object);
            if (currentIndex != index) {
                move(index, object, false, null);
            }
            index++;
        }

        assert objectList.equals(newObjects);
    }

    private W insert(int index, O gwtEntity, boolean animate, InsertCallback callback) {
        assert gwtEntity != null;
        assert objectList.size() == widgetMap.size();
        assert !objectList.contains(gwtEntity);
        assert !widgetMap.containsKey(gwtEntity.getId());
        assert OBJECTHEIGHTS != null;
        assert panel != null;
        W widget = createWidget(gwtEntity);
        assert widget != null;
        if (animate) {
            panel.insertAnimated(index, widget, OBJECTHEIGHTS.get(gwtEntity));
        } else {
            panel.insert(index, widget);
        }
        W previous = widgetMap.put(gwtEntity.getId(), widget);
        assert previous == null;
        objectList.add(index, gwtEntity);
        assert objectList.contains(gwtEntity);
        assert widgetMap.containsKey(gwtEntity.getId());
        assert objectList.size() == widgetMap.size();
        if (callback != null) {
            callback.onInserted(index);
        }
        return widget;
    }

    private W remove(O gwtEntity, boolean animate) {
        assert gwtEntity != null;
        assert objectList.size() == widgetMap.size();
        assert objectList.contains(gwtEntity);
        assert widgetMap.containsKey(gwtEntity.getId());
        W widget = getWidget(gwtEntity);
        assert widget != null;
        boolean removed = panel.remove(widget);
        assert removed;
        objectList.remove(gwtEntity);
        widgetMap.remove(gwtEntity.getId());
        assert !objectList.contains(gwtEntity);
        assert !widgetMap.containsKey(gwtEntity.getId());
        assert objectList.size() == widgetMap.size();
        return widget;
    }

    /**
     *
     * @param toIndex
     * @param object
     * @param animate
     * @param callback
     * @return
     */
    public W move(int toIndex, O object, boolean animate, InsertCallback callback) {
        assert toIndex >= 0 && toIndex <= objectList.size();
        assert objectList.contains(object);

        W oldWidget = remove(object, animate);
        W newWidget = insert(toIndex, object, animate, callback);
        if (moveObserver != null) {
            moveObserver.moved(object, oldWidget, newWidget);
        }

        assert objectList.size() == widgetMap.size();
        return newWidget;
    }

    @Override
    public void clear() {
        panel.clear();
        objectList.clear();
        widgetMap.clear();
    }

    private List<O> getOtherObjects(Collection<O> objects) {
        List<O> ret = new ArrayList<>();
        for (O object : objectList) {
            if (!objects.contains(object)) {
                ret.add(object);
            }
        }
        return ret;
    }

    /**
     *
     * @return
     */
    public List<O> getObjects() {
        return objectList;
    }

    /**
     *
     * @return
     */
    public Collection<W> getWidgets() {
        return widgetMap.values();
    }

    /**
     *
     * @param object
     * @return
     */
    public int indexOfObject(O object) {
        return objectList.indexOf(object);
    }

    /**
     *
     * @param index
     * @return
     */
    public W getWidget(int index) {
        return getWidget(objectList.get(index));
    }

    /**
     *
     * @param gwtEntity
     * @return the widget or null on error
     */
    public W getWidget(O gwtEntity) {
        if (null == gwtEntity) {
            ERROR("ObjectMappedFlowPanel: gwtEntity == null");
            return null;
        }
        W widget = widgetMap.get(gwtEntity.getId());

        if (null == widget) {
            ERROR("ObjectMappedFlowPanel: widget == null while getting object:", gwtEntity, ", of class:", gwtEntity.getClass());
            return null;
        }
        return widget;
    }

    private W createWidget(O object) {
        W widget = widgetFactory.createWidget(object);
        return widget;
    }

    /**
     *
     * @param object
     * @return
     */
    public boolean containsObject(O object) {
        return objectList.contains(object);
    }

    /**
     *
     * @return
     */
    public int size() {
        return objectList.size();
    }

    @Override
    public Iterator<Widget> iterator() {
        return panel.iterator();
    }

    @Override
    public void add(Widget w) {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public boolean remove(Widget w) {
        return false;
    }

    /**
     *
     * @param moveObserver
     */
    public void setMoveObserver(MoveObserver<O, W> moveObserver) {
        this.moveObserver = moveObserver;
    }

    /**
     *
     * @param <O>
     * @param <W>
     */
    public static interface WidgetFactory<O extends Object, W extends Widget> {

        /**
         *
         * @param object
         * @return
         */
        W createWidget(O object);

    }

    /**
     *
     * @param <O>
     * @param <W>
     */
    public static interface MoveObserver<O extends Object, W extends Widget> {

        /**
         *
         * @param object
         * @param oldWidget
         * @param newWidget
         */
        void moved(O object, W oldWidget, W newWidget);

    }

}
