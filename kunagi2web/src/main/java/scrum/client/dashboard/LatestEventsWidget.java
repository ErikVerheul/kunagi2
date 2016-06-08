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
package scrum.client.dashboard;

import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;

import java.util.List;

import scrum.client.admin.User;
import scrum.client.common.AScrumWidget;
import scrum.client.journal.ProjectEvent;
import scrum.client.project.Project;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LatestEventsWidget extends AScrumWidget {

	private SimplePanel wrapper;

	@Override
	protected Widget onInitialization() {
		wrapper = new SimplePanel();
		wrapper.setStyleName("LatestEventsWidget");
		return wrapper;
	}

	@Override
	protected void onUpdate() {
		Project project = getCurrentProject();
		List<ProjectEvent> events = project.getLatestProjectEvents(5);

		TableBuilder tb = new TableBuilder();
		tb.setColumnWidths("100px");
		if (!events.isEmpty()) {
			for (ProjectEvent event : events) {
				Widget timeWidget = Gwt.createDiv("LatestEventsWidget-time", event.getDateAndTime().getPeriodToNow()
						.toShortestString()
						+ " ago");
				String html = event.toHtml();
				html = colorUsers(html);
				Widget textWidget = new HTML(html);
				tb.addRow(timeWidget, textWidget);
			}
		}
		wrapper.setWidget(tb.createTable());
	}

	private String colorUsers(String html) {
		Project project = getCurrentProject();
		for (User user : project.getParticipants()) {
			String key = user.getName() + " ";
			html = html.replace(key,
				"<span style='color: " + project.getUserConfig(user).getColor() + ";'>" + user.getName() + "</span> ");
		}
		return html;
	}

}
