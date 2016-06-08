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
package scrum.client.estimation;

import ilarkesto.gwt.client.Gwt;
import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

public class PlanningPokerCardWidget extends AScrumWidget {

	private float value;
	private ClickHandler clickHandler;
	private MouseOverHandler mouseOverHandler;
	private String clickTooltip;
	private boolean visible;

	public PlanningPokerCardWidget(float value, boolean visible, ClickHandler clickHandler, String clickTooltip) {
		super();
		this.value = value;
		this.visible = visible;
		this.clickHandler = clickHandler;
		this.clickTooltip = clickTooltip;
	}

	@Override
	protected Widget onInitialization() {
		FocusPanel card = new FocusPanel();
		card.setStyleName("PlanningPokerCardWidget");
		if (visible) {
			String label = ScrumGwt.getEstimationAsString(value);
			card.setWidget(Gwt.createDiv("PlanningPokerCardWidget-text", label));
		} else {
			card.setWidget(Gwt.createEmptyDiv("PlanningPokerCardWidget-back"));
		}
		if (clickHandler != null) {
			card.addClickHandler(clickHandler);
			card.addStyleName("PlanningPokerCardWidget-clickable");
			card.setTitle(clickTooltip);
		}
		if (mouseOverHandler != null) {
			card.addMouseOverHandler(mouseOverHandler);
		}

		return card;
	}

	public void setMouseOverHandler(MouseOverHandler handler) {
		this.mouseOverHandler = handler;
	}
}
