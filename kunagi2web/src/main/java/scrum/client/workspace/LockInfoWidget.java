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
package scrum.client.workspace;

import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LockInfoWidget extends AScrumWidget {

	private HTML message;
	private Image image;

	@Override
	protected Widget onInitialization() {
		Label spacer = new Label();
		message = new HTML();
		image = new Image();
		image.addStyleName("LockInfoWidget-icon");
		HorizontalPanel panel = new HorizontalPanel();
		panel.setStyleName("WaitWidget");
		panel.setWidth("100%");
		panel.setSpacing(10);
		panel.add(spacer);
		panel.setCellWidth(spacer, "200px");
		panel.add(image);
		panel.setCellWidth(image, "36px");
		panel.add(message);
		panel.setCellVerticalAlignment(message, HorizontalPanel.ALIGN_MIDDLE);
		return panel;
	}

	public void showBug(String html) {
		initialize();
		image.setUrl("bug.png");

		if (html == null) html = "";

		html += "<br><a href=\"" + ScrumGwt.getLoginUrl() + "\">Reload</a>";

		message.setHTML(html);
	}

	public void showWait(String text) {
		initialize();
		image.setUrl("spinner.gif");
		message.setText(text);
	}

	@Override
	protected void onUpdate() {}

}
