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

import ilarkesto.gwt.client.Gwt;

import java.util.ArrayList;
import java.util.List;

public class Paragraph extends AWikiElement {

	private final List<AWikiElement> elements = new ArrayList<AWikiElement>();
	private final boolean p;

	public Paragraph(boolean p) {
		super();
		this.p = p;
	}

	public void add(AWikiElement element) {
		elements.add(element);
	}

	@Override
	String toHtml(HtmlContext context) {
		StringBuilder sb = new StringBuilder();
		if (p) sb.append("<p>");
		for (AWikiElement element : elements) {
			sb.append(element.toHtml(context));
		}
		if (p) sb.append("</p>");
		return sb.toString();
	}

	public List<AWikiElement> getElements() {
		return elements;
	}

	@Override
	public String toString() {
		return "Paragraph(" + Gwt.toString(elements) + ")";
	}

}
