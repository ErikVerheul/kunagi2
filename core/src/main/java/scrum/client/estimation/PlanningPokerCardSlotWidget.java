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
package scrum.client.estimation;

import ilarkesto.gwt.client.Gwt;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class PlanningPokerCardSlotWidget extends AScrumWidget {

	private String text;
	private Widget card;

	public PlanningPokerCardSlotWidget(String owner, Widget card) {
		super();
		this.text = owner;
		this.card = card;
	}

	@Override
	protected Widget onInitialization() {
		FlowPanel panel = new FlowPanel();
		panel.setStyleName("PlanningPokerCardSlotWidget");
		panel.add(card == null ? createEmptySlot() : card);
		if (text != null) panel.add(Gwt.createDiv("PlanningPokerCardSlotWidget-text", text));

		return panel;
	}

	private Widget createEmptySlot() {
		return Gwt.createEmptyDiv("PlanningPokerCardSlotWidget-slot");
	}

}
