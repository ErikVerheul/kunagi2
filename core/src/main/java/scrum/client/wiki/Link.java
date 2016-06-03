/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
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
package scrum.client.wiki;

public class Link extends AWikiElement {

	private String href;
	private String label;

	public Link(String href, String label) {
		super();
		this.href = enhance(href);
		this.label = label;
	}

	public Link(String href) {
		this(href, shorten(href));
	}

	@Override
	String toHtml(HtmlContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"");
		sb.append(enhance(href));
		sb.append("\" target=\"_blank\">");
		sb.append(escapeHtml(label));
		sb.append("</a>");
		return sb.toString();
	}

	private String enhance(String s) {
		if (s.startsWith("http://")) return s;
		if (s.startsWith("https://")) return s;
		if (s.startsWith("ftp://")) return s;
		if (s.startsWith("mailto://")) return s;
		if (s.startsWith("apt://")) return s;
		if (s.startsWith("#")) return s;
		return "http://" + s;
	}

	private static String shorten(String s) {
		if (s.startsWith("http://")) {
			s = s.substring(7);
		} else if (s.startsWith("https://")) {
			s = s.substring(8);
		} else if (s.startsWith("ftp://")) {
			s = s.substring(6);
		}
		if (s.startsWith("www.")) {
			s = s.substring(4);
		}
		if (s.length() > 50) {
			s = s.substring(0, 50) + "...";
		}
		return s;
	}

	public String getHref() {
		return href;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return "EntityReference(" + href + ")";
	}
}
