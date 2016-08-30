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

import com.google.gwt.core.client.GWT;
import static com.google.gwt.event.dom.client.ClickEvent.getType;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import static com.google.gwt.user.client.DOM.appendChild;
import static com.google.gwt.user.client.DOM.createAnchor;
import static com.google.gwt.user.client.DOM.createDiv;
import static com.google.gwt.user.client.DOM.eventGetType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import static com.google.gwt.user.client.Event.ONCLICK;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.HyperlinkImpl;

/**
 *
 *
 */
public class HyperlinkWithoutHistory extends Widget implements HasHTML, HasClickHandlers {

	private static HyperlinkImpl impl = GWT.create(HyperlinkImpl.class);

	private final Element anchorElem = createAnchor();

    /**
     *
     */
    public HyperlinkWithoutHistory() {
		this(createDiv());
	}

    /**
     *
     * @param elem
     */
    protected HyperlinkWithoutHistory(Element elem) {
		if (elem == null) {
			setElement(anchorElem);
		} else {
			setElement(elem);
			appendChild(getElement(), anchorElem);
		}

		sinkEvents(ONCLICK);
		setStyleName("gwt-Hyperlink");
	}

	@Override
	@Deprecated
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addHandler(handler, getType());
	}

	@Override
	public String getHTML() {
            return anchorElem.getInnerHTML();
	}

	@Override
	public String getText() {
		return anchorElem.getInnerText();
	}

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		if (eventGetType(event) == ONCLICK && impl.handleAsClick(event)) {
                    event.preventDefault();
		}
	}

	@Override
	public void setHTML(String html) {
                anchorElem.setInnerHTML(html);
	}

	@Override
	public void setText(String text) {
            anchorElem.setInnerText(text);
	}

	@Override
	protected void onEnsureDebugId(String baseID) {
		ensureDebugId(anchorElem, "", baseID);
		ensureDebugId(getElement(), baseID, "wrapper");
	}
}
