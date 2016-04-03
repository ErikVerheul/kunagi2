/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
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
package ilarkesto.swing;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public abstract class ATableModel<T> extends AbstractTableModel {

	private final String[] columnNames;

	private List<T> items = emptyList();

	public ATableModel(String... columnNames) {
		if (columnNames.length == 0) {
                        columnNames = new String[1];
                }
		this.columnNames = columnNames;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	public Object getValueAt(int column, T item) {
		return item.toString();
	}

	public void setItems(List<T> items) {
		this.items = items;
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return items.size();
	}

	@Override
	public final Object getValueAt(int row, int column) {
		T item = items.get(row);
		return getValueAt(column, item);
	}

	public List<T> getItems() {
		return items;
	}

        @SuppressWarnings("EI_EXPOSE_REP")
	public String[] getColumnNames() {
		return columnNames;
	}

}
