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

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public abstract class ATablePanel<T> extends JPanel {

	private Model model;
	private JTable table;
	private DefaultListSelectionModel selectionModel;
	private JScrollPane scroller;

	public ATablePanel(String... columnNames) {

		model = new Model(columnNames);

		selectionModel = new DefaultListSelectionModel();
		selectionModel.addListSelectionListener(new SelectionListener());

		table = new JTable(model);
		table.setSelectionModel(selectionModel);

		scroller = new JScrollPane(table);

		setLayout(new BorderLayout());
		add(scroller, CENTER);
	}

	protected abstract List<T> loadItems();

	protected abstract Object getValueAt(int column, T item);

	protected abstract void onSelectionChanged();

	public void update() {
		setItems(loadItems());
	}

	public boolean isItemSelected() {
		return !selectionModel.isSelectionEmpty();
	}

	public boolean isSelectionEmpty() {
		return selectionModel.isSelectionEmpty();
	}

	public int getSelectedItemsCount() {
		return table.getSelectedRowCount();
	}

	public List<T> getSelectedItems() {
		List<T> items = model.getItems();
		int[] selectedRows = table.getSelectedRows();
		List<T> ret = new ArrayList<>(selectedRows.length);
		for (int i = 0; i < selectedRows.length; i++) {
			int row = selectedRows[i];
			ret.add(items.get(row));
		}
		return ret;
	}

	public void setSelectedItems(Collection<T> itemsToSelect) {
		selectionModel.clearSelection();
		List<T> items = model.getItems();
		for (T item : itemsToSelect) {
			int row = items.indexOf(item);
			if (row < 0) {
                                continue;
                        }
			selectionModel.addSelectionInterval(row, row);
		}
	}

	public List<T> getItems() {
		return model.getItems();
	}

	public void setItems(List<T> items) {
		model.setItems(items);
	}

	public JTable getTable() {
		return table;
	}

	public JScrollPane getScroller() {
		return scroller;
	}

	public ATableModel<T> getModel() {
		return model;
	}

	private class Model extends ATableModel<T> {

		public Model(String... columnNames) {
			super(columnNames);
		}

		@Override
		public Object getValueAt(int column, T item) {
			return ATablePanel.this.getValueAt(column, item);
		}

	}

	private class SelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			onSelectionChanged();
		}

	}

}
