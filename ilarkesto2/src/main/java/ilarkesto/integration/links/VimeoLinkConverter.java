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

public class VimeoLinkConverter implements LinkConverter {

	@Override
	public String convert(String href, int maxWidth) {
		if (!isConvertable(href)) {
                        return href;
                }
		String videoId = parseVideoId(href);
		return createHtml(videoId, maxWidth);
	}

	private String createHtml(String videoId, int width) {
		int height = (int) (width / 1.56f);
		StringBuilder sb = new StringBuilder();
		sb.append("<iframe src=\"http://player.vimeo.com/video/").append(videoId).append("?portrait=0\" width=\"")
				.append(width).append("\" height=\"").append(height)
				.append("\" frameborder=\"0\" webkitAllowFullScreen mozallowfullscreen allowFullScreen></iframe>");
		return sb.toString();
	}

	private String parseVideoId(String href) {
		int idx = href.indexOf("vimeo.com/") + 10;
		int endIdx = href.indexOf('?', idx);
		if (endIdx > 0) {
                        return href.substring(idx, endIdx);
                }
		return href.substring(idx);
	}

	private boolean isConvertable(String href) {
		if (href == null || href.endsWith("vimeo.com/")) {
                        return false;
                }

		if (href.startsWith("http://vimeo.com/")) {
                        return true;
                }
		if (href.startsWith("https://vimeo.com/")) {
                        return true;
                }
		return href.startsWith("vimeo.com/");
	}
}
