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

public class Image extends AWikiElement {

	private String reference;
	private boolean thumb;
	private boolean thumbAlignmentLeft;

	public Image(String reference, boolean thumb, boolean thumbAlignmentLeft) {
		super();
		this.reference = reference;
		this.thumb = thumb;
		this.thumbAlignmentLeft = thumbAlignmentLeft;
	}

	@Override
	String toHtml(HtmlContext context) {
		boolean external = isExternal();
		StringBuilder sb = new StringBuilder();
		if (external) {
			sb.append("<a href=\"");
			sb.append(reference);
			sb.append("\" target=\"_blank\">");
		} else {
			String hrefOrOnclick = context.getEntityReferenceHrefOrOnclickAParameter(reference);
			sb.append("<a ");
			sb.append(hrefOrOnclick);
			sb.append(">");
		}
		sb.append("<img src=\"");
		if (external) {
			sb.append(reference);
		} else {
			sb.append(context.getDownloadUrlByReference(reference));
		}
		if (thumb) {
			sb.append("\" width=\"100px\" align=\"");
			sb.append(thumbAlignmentLeft ? "left" : "right");
		}
		// TODO alt="label"
		sb.append("\">");
		sb.append("</a>");
		return sb.toString();
	}

	public boolean isExternal() {
		return reference.startsWith("http://") || reference.startsWith("https://");
	}

	public String getReference() {
		return reference;
	}

	public boolean isThumb() {
		return thumb;
	}

	public boolean isThumbAlignmentLeft() {
		return thumbAlignmentLeft;
	}

	@Override
	public String toString() {
		return "Image(" + reference + ")";
	}
}
