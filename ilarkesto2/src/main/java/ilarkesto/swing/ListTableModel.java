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
package ilarkesto.swing;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author erik
 * @param <E>
 */
@SuppressWarnings("EI_EXPOSE_REP")
public final class ListTableModel<E> extends AbstractTableModel {

    private Column[] columns;
    private LinkedList<E> elements = new LinkedList<>();

    /**
     *
     */
    public ListTableModel() {
        columns = new Column[] { new DefaultColumn() };
    }

    /**
     *
     * @param element
     */
    public void moveToBottom(E element) {
        elements.remove(element);
        elements.add(element);
        fireTableDataChanged();
    }

    /**
     *
     * @param elements
     */
    public void moveToTop(Collection<E> elements) {
        this.elements.removeAll(elements);
        this.elements.addAll(0, elements);
        fireTableDataChanged();
    }

    /**
     *
     * @return
     */
    public Column[] getColumns() {
        return columns;
    }

    /**
     *
     * @param columns
     */
    public void setColumns(Column... columns) {
        this.columns = columns;
        fireTableStructureChanged();
    }

    /**
     *
     * @param elements
     */
    public final void setElements(Collection<E> elements) {
        this.elements = new LinkedList<>(elements);
        fireTableDataChanged();
    }

    /**
     *
     * @param elements
     */
    public final void removeElements(Collection<E> elements) {
        this.elements.removeAll(elements);
        fireTableDataChanged();
    }

    /**
     *
     */
    public void removeAllElements() {
        elements.clear();
        fireTableDataChanged();
    }

    /**
     *
     * @param elements
     */
    public final void addElements(Collection<E> elements) {
        this.elements.addAll(elements);
        fireTableDataChanged();
    }

    /**
     *
     * @param element
     */
    public final void addElement(E element) {
        elements.add(element);
        fireTableDataChanged();
    }

    /**
     *
     * @param index
     * @return
     */
    public final E getElement(int index) {
        return elements.get(index);
    }

    /**
     *
     * @return
     */
    public int getElementsCount() {
        return elements.size();
    }

        @Override
    public final int getColumnCount() {
        return columns.length;
    }

    @Override
    public final String getColumnName(int column) {
        return columns[column].getLabel();
    }

        @Override
    public final int getRowCount() {
        return elements.size();
    }

        @Override
    public final Object getValueAt(int rowIndex, int columnIndex) {
        return columns[columnIndex].getValue(getElement(rowIndex));
    }

    /**
     *
     * @param <E>
     */
    public static interface Column<E> {

        /**
         *
         * @return
         */
        String getLabel();

        /**
         *
         * @param element
         * @return
         */
        Object getValue(E element);

    }

    /**
     *
     * @param selectedRows
     * @return
     */
    public List<E> getElements(int[] selectedRows) {
        List<E> result = new ArrayList<>(selectedRows.length);
        for (int row : selectedRows) {
            result.add(getElement(row));
        }
        return result;
    }

        class DefaultColumn implements Column {

                @Override
                public String getLabel() {
                        return "";
                }

                @Override
                public Object getValue(Object element) {
                        return element.toString();
                }
        }

}
