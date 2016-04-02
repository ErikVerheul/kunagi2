/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package ilarkesto.integration.links;

public class ImageLinkConverter implements LinkConverter {

	@Override
	public String convert(String href, int maxWidth) {
		if (!isConvertable(href)) {
                        return href;
                }
		return createHtml(href, maxWidth);
	}

	private String createHtml(String href, int maxWidth) {
		StringBuilder sb = new StringBuilder();
		sb.append("<img src=\"");
		sb.append(href.startsWith("www.") ? "http://" + href : href);
		sb.append("\" style=\"max-width: ").append(maxWidth).append("px; max-height: ").append(maxWidth)
				.append("px;\" alt=\"").append(href).append("\">");
		return sb.toString();
	}

	private boolean isConvertable(String href) {
		String hrefLower = href.toLowerCase();
		if (hrefLower.endsWith(".jpg")) {
                        return true;
                }
		if (hrefLower.endsWith(".gif")) {
                        return true;
                }
		if (hrefLower.endsWith(".png")) {
                        return true;
                }
		if (hrefLower.endsWith(".jpeg")) {
                        return true;
                }
		return href.contains(".ggpht.com/");
	}

}
