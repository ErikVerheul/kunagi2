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

/**
 *
 * @author erik
 */
public class TableRow extends AWikiElement {

	private final List<TableCell> cells = new ArrayList<TableCell>();

	@Override
	String toHtml(HtmlContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>");
		for (TableCell cell : cells) {
			sb.append(" ");
			sb.append(cell.toHtml(context));
			sb.append(" ");
		}
		sb.append("</tr>\n");
		return sb.toString();
	}

    /**
     *
     * @param cell
     */
    public void addCell(TableCell cell) {
		cells.add(cell);
	}

    /**
     *
     * @return
     */
    public List<TableCell> getCells() {
		return cells;
	}

}
