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

import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.Tm;

import java.util.List;

import scrum.client.common.AScrumWidget;
import scrum.client.communication.Pinger;
import scrum.client.core.AServiceCall;
import scrum.client.core.ServiceCaller;
import scrum.client.test.ScrumStatusWidget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CommunicationIndicatorWidget extends AScrumWidget {

	private ServiceCaller serviceCaller;
	private Pinger pinger;

	private FocusPanel focusPanel;
	private Label status;
	private Style statusStyle;

	private long onTime;

	@Override
	protected Widget onInitialization() {
		serviceCaller = Scope.get().getComponent(ServiceCaller.class);
		serviceCaller.setStatusWidget(this);
		pinger = Scope.get().getComponent(Pinger.class);

		status = new Label();
		status.setStyleName("StatusWidget");
		statusStyle = status.getElement().getStyle();

		focusPanel = new FocusPanel(status);
		focusPanel.addClickHandler(new StatusClickHandler());

		new Timer() {

			@Override
			public void run() {
				if (onTime > 0) {
					long onDuration = Tm.getCurrentTimeMillis() - onTime;
					if (onDuration > 3000) {
						statusStyle.setBackgroundColor("#f00");
						status.setTitle("Waiting for server response since " + (onDuration / 1000) + " seconds...");
						return;
					}
				}
				status.setTitle(pinger.getAvaragePingTimeMessage());
			}
		}.scheduleRepeating(1000);

		return focusPanel;
	}

	@Override
	protected void onUpdate() {
		List<AServiceCall> calls = serviceCaller.getActiveServiceCalls();
		if (calls.isEmpty()) {
			if (isOn()) switchOff();
		} else {
			if (!isOn()) switchOn();
		}
	}

	private void switchOn() {
		onTime = Tm.getCurrentTimeMillis();
		statusStyle.setBackgroundColor("#aa6");
	}

	private void switchOff() {
		onTime = 0;
		statusStyle.setBackgroundColor("#666");
	}

	private boolean isOn() {
		return onTime > 0;
	}

	class StatusClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Scope.get().getComponent(Ui.class).getWorkspace().getWorkarea().show(new ScrumStatusWidget());
			focusPanel.setFocus(false);
		}

	}

}
