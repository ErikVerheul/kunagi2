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
package ilarkesto.gwt.client;

import com.google.gwt.dom.client.AnchorElement;
import static com.google.gwt.dom.client.AnchorElement.as;
import com.google.gwt.user.client.DOM;
import static com.google.gwt.user.client.DOM.appendChild;
import static com.google.gwt.user.client.DOM.createAnchor;
import static com.google.gwt.user.client.DOM.createDiv;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Image;

public class ImageAnchor extends FocusWidget {

	private AnchorElement a;

	public ImageAnchor(Image image, String text, boolean spacerBetweenImageAndText) {
		if (image == null && text == null) {
                        throw new IllegalArgumentException("image or text must be not null");
                }

		a = as(createAnchor());
		setElement(a);
		setStyleName("ImageAnchor");

		// a.setHref("javascript:");

		if (image != null) {
			Element img = image.getElement();
			appendChild(getElement(), img);
		}

		// if (spacerBetweenImageAndText && image != null && text != null) {
		// Element span = DOM.createSpan();
		// span.setInnerHTML("&nbsp;");
		// DOM.appendChild(getElement(), span);
		// }

		if (text != null) {
			Element div = createDiv();
			div.setClassName("text");
			div.setInnerText(text);
			appendChild(getElement(), div);
		}

		if (image != null) {
			Element clear = createDiv();
			clear.setClassName("floatClear");
			appendChild(getElement(), clear);
		}
	}

	public ImageAnchor(Image image, String text) {
		this(image, text, true);
	}

	public ImageAnchor(Image image) {
		this(image, null, false);
	}

	public void setTooltip(String text) {
		a.setTitle(text);
	}

	public void setHref(String href) {
		a.setHref(href);
	}
}
