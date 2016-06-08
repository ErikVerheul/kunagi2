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
package scrum.client.admin;

import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.core.time.Tm;
import ilarkesto.gwt.client.Gwt;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SystemMessageWidget extends AScrumWidget {

	private SystemMessageManager systemMessageManager;
	private SimplePanel panel;
	private Label text = new Label();
	private Label expires = new Label();

	@Override
	protected Widget onInitialization() {
		systemMessageManager = Scope.get().getComponent(SystemMessageManager.class);

		expires.setStyleName("SystemMessageWidget-box-time");

		FlowPanel content = new FlowPanel();
		content.add(Gwt.createDiv("SystemMessageWidget-box-title", "Message from Admin"));
		content.add(text);
		content.add(expires);

		panel = Gwt.createDiv("SystemMessageWidget-box", content);
		panel.setVisible(false);
		return panel;
	}

	@Override
	protected void onUpdate() {
		SystemMessage message = systemMessageManager.getSystemMessage();
		if (message.isActive()) {
			text.setText(message.getText());
			expires.setText(message.getExpiresAsString());
			panel.setVisible(true);
			DateAndTime expiration = message.getExpires();
			if (updater == null) {
				if (expiration != null) {
					TimePeriod period = expiration.getPeriodFromNow();
					if (period.isPositive()) {
						updater = new Updater();
						Gwt.runLater(period.toMillis() > Tm.MINUTE ? Tm.SECOND * 10 : Tm.SECOND, updater);
					}
				}
			}
		} else {
			panel.setVisible(false);
		}
	}

	private Updater updater;

	private class Updater implements Runnable {

		@Override
		public void run() {
			updater = null;
			update();
		}
	}

}
