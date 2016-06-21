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

public class Toc extends AWikiElement {

	private final WikiModel model;

	public Toc(WikiModel model) {
		super();
		this.model = model;
	}

	@Override
	String toHtml(HtmlContext context) {
		List<Header> headers = new ArrayList<Header>();
		for (AWikiElement element : model.getElements()) {
			appendHeaders(headers, element);
		}
		if (headers.isEmpty()) return "";

		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"toc\">");
		sb.append("<ul>");
		int currentDepth = 1;
		for (Header h : headers) {

			int depth = h.getDepth();
			int diff = depth - currentDepth;
			for (int i = 0; i < diff; i++) {
				sb.append("<ul>");
			}
			for (int i = diff; i < 0; i++) {
				sb.append("</ul>");
			}
			currentDepth = depth;

			sb.append("<li>");
			sb.append("<a ").append(context.getTocHrefOrOnclickAParameter(h)).append(">");
			sb.append(escapeHtml(h.getText()));
			sb.append("</a>");
			sb.append("</li>");
		}
		while (currentDepth > 1) {
			sb.append("</ul>");
			currentDepth--;
		}
		sb.append("</ul>");
		sb.append("</div>");
		return sb.toString();
	}

	private void appendHeaders(List<Header> headers, AWikiElement element) {
		if (element instanceof Paragraph) {
			Paragraph p = (Paragraph) element;
			for (AWikiElement e : p.getElements()) {
				appendHeaders(headers, e);
			}
			return;
		}
		if (element instanceof Header) headers.add((Header) element);
	}

	@Override
	public String toString() {
		return "Toc";
	}

}
