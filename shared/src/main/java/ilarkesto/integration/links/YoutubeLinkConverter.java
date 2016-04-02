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

public class YoutubeLinkConverter implements LinkConverter {

	@Override
	public String convert(String href, int maxWidth) {
		if (!isConvertable(href)) {
                        return href;
                }
		String videoId = parseVideoId(href);
		return createHtml(videoId, maxWidth);
	}

	private String createHtml(String videoId, int width) {
		int height = (int) (width / 1.6623f);
		StringBuilder sb = new StringBuilder();
		sb.append("<iframe width=\"").append(width).append("\" height=\"").append(height)
				.append("\" src=\"https://www.youtube-nocookie.com/embed/").append(videoId)
				.append("?rel=0\" frameborder=\"0\" allowfullscreen></iframe>");
		return sb.toString();
	}

	private String parseVideoId(String href) {
		if (href.contains("/embed/")) {
			int idx = href.lastIndexOf("/embed/") + 7;
			int endIdx = href.indexOf('&', idx);
			if (endIdx > 0) {
                                return href.substring(idx, endIdx);
                        }
			return href.substring(idx);
		}
		int idx = href.indexOf("v=") + 2;
		int endIdx = href.indexOf('&', idx);
		if (endIdx > 0) {
                        return href.substring(idx, endIdx);
                }
		return href.substring(idx);
	}

	private boolean isConvertable(String href) {
		if (href == null) {
                        return false;
                }

		if (href.startsWith("http://www.youtube.com/watch?")) {
                        return true;
                }
		if (href.startsWith("https://www.youtube.com/watch?")) {
                        return true;
                }
		if (href.startsWith("www.youtube.com/watch?")) {
                        return true;
                }

		if (href.startsWith("http://www.youtu.be/watch?")) {
                        return true;
                }
		if (href.startsWith("https://www.youtu.be/watch?")) {
                        return true;
                }
		if (href.startsWith("www.youtu.be/watch?")) {
                        return true;
                }

		if (href.startsWith("http://www.youtube-nocookie.com/embed/")) {
                        return true;
                }
		if (href.startsWith("https://www.youtube-nocookie.com/embed/")) {
                        return true;
                }
		return href.startsWith("www.youtube-nocookie.com/embed/");
	}

}
