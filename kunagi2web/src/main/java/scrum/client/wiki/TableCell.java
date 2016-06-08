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

public class TableCell extends AWikiElement {

	private Paragraph paragraph;
	private boolean header;

	public TableCell(Paragraph paragraph, boolean header) {
		super();
		this.paragraph = paragraph;
		this.header = header;
	}

	@Override
	String toHtml(HtmlContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append(header ? "<th>" : "<td>");
		sb.append(paragraph.toHtml(context));
		sb.append(header ? "</th>" : "</td>");
		return sb.toString();
	}

	public Paragraph getParagraph() {
		return paragraph;
	}

	public boolean isHeader() {
		return header;
	}

}
