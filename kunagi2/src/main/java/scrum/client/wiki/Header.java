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

public class Header extends AWikiElement {

	private int depth;
	private String text;
	private String anchor;

	public Header(String text, int depth) {
		super();
		this.text = text;
		this.depth = depth;
	}

	@Override
	String toHtml(HtmlContext context) {
		return "<a name=\"" + getAnchor() + "\" id=\"" + getAnchor() + "\"></a><h" + depth + ">" + escapeHtml(text)
				+ "</h" + depth + ">";
	}

	@Override
	public String toString() {
		return "Text(\"" + text + "\")";
	}

	public int getDepth() {
		return depth;
	}

	public String getText() {
		return text;
	}

	public String getAnchor() {
		if (anchor == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("wiki_h").append(depth).append("_");
			int len = text.length();
			for (int i = 0; i < len; i++) {
				char ch = text.charAt(i);
				sb.append(Character.isLetterOrDigit(ch) ? ch : "_");
			}
			anchor = sb.toString();
		}
		return anchor;
	}

}
