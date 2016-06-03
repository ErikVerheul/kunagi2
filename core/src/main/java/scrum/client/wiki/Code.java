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

public class Code extends AWikiElement {

	private String text;
	private boolean forceBlock;

	public Code(String text, boolean forceBlock) {
		super();
		this.text = text;
		this.forceBlock = forceBlock;
	}

	@Override
	String toHtml(HtmlContext context) {
		String s = text;
		if (s.length() > 0 && s.startsWith("\n")) s = s.substring(1);
		String html = escapeHtml(s);
		html = html.replace("\n", "<br>");
		html = html.replace(" ", "&nbsp;");

		boolean codeBlock = isBlock();

		StringBuilder sb = new StringBuilder();
		if (codeBlock) {
			sb.append("<code class=\"codeBlock\">");
		} else {
			sb.append("<code>");
		}
		sb.append(html);
		sb.append("</code>");
		return sb.toString();
	}

	public boolean isBlock() {
		if (forceBlock) return true;
		if (text.length() > 80) return true;
		if (text.contains("\n")) return true;
		return false;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return "Code(\"" + text + "\")";
	}

}
