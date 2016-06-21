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

import ilarkesto.gwt.client.AAction;
import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.AnchorPanel;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.DropdownMenuButtonWidget;
import ilarkesto.gwt.client.editor.AFieldModel;
import ilarkesto.gwt.client.editor.TextOutputWidget;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class BlockHeaderWidget extends AWidget {

	private HorizontalPanel outerFramePanel;
	private FocusPanel dragHandleWrapper;
	private AnchorPanel centerAnchorPanel;
	private Collection<AnchorPanel> anchorPanels = new ArrayList<AnchorPanel>();
	private HorizontalPanel centerTable;

	private DropdownMenuButtonWidget menu;
	private Label dragHandle;

	@Override
	protected Widget onInitialization() {
		dragHandleWrapper = new FocusPanel();
		dragHandleWrapper.setStyleName("BlockHeaderWidget-dragHandle");
		// dragHandleWrapper.setHeight("100%");

		centerTable = new HorizontalPanel();

		centerAnchorPanel = new AnchorPanel();
		centerAnchorPanel.setStyleName("BlockHeaderWidget-anchor");
		centerAnchorPanel.setWidth("100%");
		centerAnchorPanel.add(centerTable);

		outerFramePanel = new HorizontalPanel();
		outerFramePanel.setStyleName("BlockHeaderWidget");
		outerFramePanel.setWidth("100%");
		outerFramePanel.add(dragHandleWrapper);
		outerFramePanel.setCellWidth(dragHandleWrapper, "50px");
		outerFramePanel.add(centerAnchorPanel);

		return outerFramePanel;
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		centerAnchorPanel.setFocus(false);
	}

	public void addText(AFieldModel<?> model) {
		addText(model, null, false, false);
	}

	public void addText(AFieldModel<?> model, boolean secondary) {
		addText(model, null, secondary, secondary);
	}

	public void addText(AFieldModel<?> model, boolean secondary, boolean small) {
		addText(model, null, secondary, small);
	}

	public void addText(AFieldModel<?> model, String width, boolean secondary, boolean small) {
		AnchorPanel cell = createCell(new TextOutputWidget(model).setForceEmptyChar(true), secondary, small, null);
		if (width != null) cell.setWidth(width);
		centerTable.add(cell);
	}

	// ---

	public AnchorPanel addIconWrapper() {
		AnchorPanel cell = createCell(null, false, false, "BlockHeaderWidget-prefixIcon");
		cell.setHeight("16px");
		cell.setWidth("16px");
		centerTable.add(cell);
		return cell;
	}

	public void appendOuterCell(Widget widget, String width, boolean alignRight) {
		outerFramePanel.add(widget);
		if (alignRight) outerFramePanel.setCellHorizontalAlignment(widget, HorizontalPanel.ALIGN_RIGHT);
		if (width != null) outerFramePanel.setCellWidth(widget, width);
	}

	private AnchorPanel createCell(Widget widget, boolean secondary, boolean small, String additionalStyleName) {
		AnchorPanel wrapper = new AnchorPanel();
		wrapper.setStyleName("BlockHeaderWidget-cell");
		wrapper.addStyleName("BlockHeaderWidget-anchor");
		wrapper.setWidth("100%");
		wrapper.setHeight("100%");

		anchorPanels.add(wrapper);

		if (secondary) wrapper.addStyleName("BlockHeaderWidget-cell-secondary");
		if (small) wrapper.addStyleName("BlockHeaderWidget-cell-small");
		if (additionalStyleName != null) wrapper.addStyleName(additionalStyleName);
		wrapper.add(widget);
		return wrapper;
	}

	public void addMenuAction(AScrumAction action) {
		if (menu == null) {
			menu = new DropdownMenuButtonWidget();
			appendOuterCell(menu, "30px", true);
		}
		menu.addAction(action);
	}

	public void addToolbarAction(AAction action) {
		appendOuterCell(new ButtonWidget(action), "5px", true);
	}

	public void setDragHandle(String text) {
		if (dragHandle == null) {
			dragHandle = new Label();
			setDragHandle(dragHandle);
		}
		dragHandle.setText(text);
	}

	public void setDragHandle(Widget widget) {
		dragHandleWrapper.setWidget(widget);
	}

	public void addClickHandler(ClickHandler handler) {
		centerAnchorPanel.addClickHandler(handler);

		for (AnchorPanel ap : anchorPanels) {
			ap.addClickHandler(handler);
		}
	}

	public void setHref(String href) {
		centerAnchorPanel.setHref(href);
		for (AnchorPanel ap : anchorPanels) {
			ap.setHref(href);
		}
	}

	public FocusPanel getDragHandle() {
		return dragHandleWrapper;
	}
}
