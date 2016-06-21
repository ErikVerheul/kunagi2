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
package scrum.client.dnd;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class BlockDndMarkerWidget extends Composite {

	private SimplePanel panel;
	private boolean active;

	public BlockDndMarkerWidget() {
		panel = new SimplePanel();
		panel.setHeight("1px");
		panel.setStyleName("BlockDndMarkerWidget");
		panel.add(new Label(""));

		initWidget(panel);
	}

	public void setActive(boolean active) {
		if (this.active == active) return;
		this.active = active;
		panel.setStyleName(active ? "BlockDndMarkerWidget-active" : "BlockDndMarkerWidget");
	}

}
