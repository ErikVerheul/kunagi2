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

public class Highlight extends Paragraph {

	private boolean em;
	private boolean strong;

	public Highlight(boolean em, boolean strong) {
		super(false);
		this.em = em;
		this.strong = strong;
	}

	@Override
	String toHtml(HtmlContext context) {
		StringBuilder sb = new StringBuilder();
		if (strong) sb.append("<strong>");
		if (em) sb.append("<em>");
		sb.append(super.toHtml(context));
		if (em) sb.append("</em>");
		if (strong) sb.append("</strong>");
		return sb.toString();
	}

	public boolean isEm() {
		return em;
	}

	public boolean isStrong() {
		return strong;
	}

	@Override
	public String toString() {
		return "Highlight(" + Gwt.toString(getElements()) + ")";
	}

}
