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

import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author erik
 * @param <I>
 */
public class MultiSelectionWidget<I extends Object> extends AWidget {

	private FlexTable table;
	private final Map<I, CheckBox> items = new HashMap<I, CheckBox>();
	private FocusPanel panel;

    /**
     *
     * @return
     */
    @Override
	protected Widget onInitialization() {
		table = new FlexTable();
		table.setCellPadding(0);
		table.setCellSpacing(0);

		panel = new FocusPanel();
		panel.setStyleName("MultiSelectionWidget");
		panel.setWidget(table);
		return panel;
	}

    /**
     *
     * @param item
     */
    public void add(I item) {
		initialize();
		CheckBox checkbox = createCheckbox(item);
		items.put(item, checkbox);
		table.setWidget(table.getRowCount(), 0, checkbox);
	}

    /**
     *
     * @param item
     * @return
     */
    protected CheckBox createCheckbox(I item) {
		CheckBox checkbox;
		if (item instanceof HtmlLabelSupport) {
			checkbox = new CheckBox(((HtmlLabelSupport) item).getHtmlLabel(), true);
		} else {
			checkbox = new CheckBox(item.toString());
		}
		return checkbox;
	}

    /**
     *
     * @param items
     */
    public void setItems(Collection<I> items) {
		initialize();
		clear();
		for (I item : items) {
			add(item);
		}
	}

    /**
     *
     * @return
     */
    public List<I> getItems() {
		return new ArrayList(items.keySet());
	}

    /**
     *
     */
    public void clear() {
		items.clear();
		table.clear();
	}

    /**
     *
     * @param selectedItems
     */
    public void setSelected(Collection<I> selectedItems) {
		for (Map.Entry<I, CheckBox> entry : items.entrySet()) {
			entry.getValue().setValue((selectedItems.contains(entry.getKey())));
		}
	}

    /**
     *
     * @param item
     */
    public void addSelected(I item) {
		CheckBox checkBox = items.get(item);
		if (checkBox != null) {
                        checkBox.setValue(true);
                }
	}

    /**
     *
     * @return
     */
    public List<I> getSelected() {
		List<I> ret = new LinkedList<I>();
		for (Map.Entry<I, CheckBox> entry : items.entrySet()) {
			if (entry.getValue().getValue()) {
                                ret.add(entry.getKey());
                        }
		}
		return ret;
	}

    /**
     *
     * @param focushandler
     */
    public void addFocusHandler(FocusHandler focushandler) {
		initialize();
		panel.addFocusHandler(focushandler);
	}

    /**
     *
     * @param handler
     */
    public void addKeyDownHandler(KeyDownHandler handler) {
		initialize();
		panel.addKeyDownHandler(handler);
	}

    /**
     *
     * @param focus
     */
    public void setFocus(boolean focus) {
		initialize();
		panel.setFocus(focus);
	}

}
