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
package scrum.client.common;

import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Widget, which displays fields. A field is a pair of a label and a value.
 * 
 * @deprecated Use TableBuilder instead
 * @see TableBuilder
 */
@Deprecated
public class FieldsWidget extends AWidget {

	private Grid grid;
	private List<Widget> widgets = new ArrayList<Widget>();

	@Override
	protected Widget onInitialization() {
		grid = new Grid(0, 2);
		grid.setWidth("100%");
		grid.setStyleName("FieldsWidget-grid");
		return grid;
	}

	@Override
	protected void onUpdate() {
		Gwt.update(widgets);
	}

	public <W extends Widget> W add(String label, W value) {
		widgets.add(value);
		return addWidget(label, value);
	}

	public Label add(String label, Label value) {
		return addWidget(label, value);
	}

	/**
	 * @param label Label of the field (left).
	 * @param value Value widget of the field (right).
	 */
	public <W extends Widget> W addWidget(String label, W value) {
		assert value != null;
		initialize();

		if (label == null) label = "";
		Label l = new Label(label);
		l.setStyleName("FieldsWidget-fieldLabel");
		value.addStyleName("FieldsWidget-fieldValue");
		l.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		int row = grid.getRowCount();
		grid.resizeRows(row + 1);
		if (row == 0) {
			grid.getCellFormatter().setWidth(row, 0, "10%");
			grid.getCellFormatter().setWidth(row, 1, "90%");
		}
		grid.setWidget(row, 0, l);
		grid.setWidget(row, 1, value);

		return value;
	}

}
