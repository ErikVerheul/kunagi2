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
package scrum.client.common;

import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.Gwt;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class GroupWidget extends AWidget {

	private String title;
	private FlowPanel contentPanel;
	private Widget content;
	private boolean contentChanged;

	public GroupWidget(String title) {
		this.title = title;
	}

	public GroupWidget(String title, Widget content) {
		this(title);
		setContent(content);
	}

	@Override
	protected Widget onInitialization() {
		SimplePanel titlePanel = new SimplePanel();
		titlePanel.setStyleName("GroupWidget-title");
		titlePanel.add(new Label(title));

		contentPanel = new FlowPanel();
		contentPanel.setStyleName("GrouplWidget-content");

		FlowPanel panel = new FlowPanel();
		panel.setStyleName("GrouplWidget");
		panel.add(titlePanel);
		panel.add(contentPanel);

		return panel;
	}

	@Override
	protected void onUpdate() {
		if (contentChanged) {
			contentPanel.clear();
			contentPanel.add(content);
			contentChanged = false;
		}
		Gwt.update(content);
	}

	public GroupWidget setContent(Widget content) {
		this.content = content;
		contentChanged = true;
		update();
		return this;
	}

	@Override
	public String toString() {
		return "GroupWidget(" + title + ")";
	}
}
