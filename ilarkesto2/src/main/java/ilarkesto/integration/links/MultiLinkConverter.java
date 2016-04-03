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

import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.List;

public class MultiLinkConverter implements LinkConverter {

	public final static MultiLinkConverter ALL = new MultiLinkConverter().add(new ImageLinkConverter(),
		new YoutubeLinkConverter(), new VimeoLinkConverter());

	private List<LinkConverter> converters = new ArrayList<>();

	public MultiLinkConverter add(LinkConverter... converters) {
                this.converters.addAll(asList(converters));
		return this;
	}

	@Override
	public String convert(String href, int maxWidth) {
		if (href == null) {
                        return null;
                }
		for (LinkConverter converter : converters) {
			String s = converter.convert(href, maxWidth);
			if (!s.equals(href)) {
                                return s;
                        }
		}
		return href;
	}

}
