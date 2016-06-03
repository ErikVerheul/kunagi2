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
package scrum.client.wiki;

import java.util.ArrayList;
import java.util.List;

public class Table extends AWikiElement {

	private final List<TableRow> rows = new ArrayList<TableRow>();
	private TableRow currentRow;

	@Override
	String toHtml(HtmlContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<table class='data-table'>\n");
		for (TableRow row : rows) {
			sb.append(row.toHtml(context));
		}
		sb.append("</table>\n");
		return sb.toString();
	}

	public void addCell(Paragraph p, boolean header) {
		if (p == null) return;
		if (currentRow == null) {
			currentRow = new TableRow();
			rows.add(currentRow);
		}
		currentRow.addCell(new TableCell(p, header));
	}

	public void nextRow() {
		currentRow = null;
	}

	public List<TableRow> getRows() {
		return rows;
	}

	public int getColumnCount() {
		int count = 0;
		for (TableRow row : rows) {
			int cells = row.getCells().size();
			if (cells > count) count = cells;
		}
		return count;
	}
}
