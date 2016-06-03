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
package ilarkesto.gwt.client;

import com.google.gwt.dom.client.AnchorElement;
import static com.google.gwt.dom.client.AnchorElement.as;
import com.google.gwt.event.dom.client.ClickEvent;
import static com.google.gwt.event.dom.client.ClickEvent.getType;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import static com.google.gwt.user.client.DOM.createAnchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class AnchorPanel extends ComplexPanel {

	private AnchorElement a;

	public AnchorPanel() {
		a = as(createAnchor());
		setElement(a);
		setStyleName("AnchorPanel");
	}

	@Override
	public void add(Widget w) {
		if (w == null) {
                        return;
                }
		add(w, getElement());
	}

	public void setWidget(Widget w) {
		clear();
		add(w);
	}

	public void setTooltip(String text) {
		a.setTitle(text);
	}

	public void setHref(String href) {
		a.setHref(href);
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, getType());
	}

	public void setFocus(boolean focused) {
		if (focused) {
			a.focus();
		} else {
			a.blur();
		}
	}
}
