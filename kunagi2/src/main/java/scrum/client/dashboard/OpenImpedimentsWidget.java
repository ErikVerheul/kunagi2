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
package scrum.client.dashboard;

import java.util.List;

import scrum.client.common.AScrumWidget;
import scrum.client.impediments.Impediment;
import scrum.client.project.Project;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class OpenImpedimentsWidget extends AScrumWidget {

	private HTML html;

	@Override
	protected Widget onInitialization() {
		html = new HTML();
		return html;
	}

	@Override
	protected void onUpdate() {
		Project project = getCurrentProject();
		StringBuilder sb = new StringBuilder();
		sb.append("<ul class='OpenImpedimentsWidget'>");

		List<Impediment> impediments = project.getOpenImpediments();
		if (!impediments.isEmpty()) {
			for (Impediment impediment : impediments) {
				sb.append("<li>");
				boolean blocking = impediment.isBlockingTasksFromCurrentSprint();
				if (blocking) sb.append("<span style='color: red;'>");
				sb.append(impediment.toHtml());
				if (blocking) sb.append("</span>");
				sb.append("</li>");
			}
		}

		sb.append("</ul>");
		html.setHTML(sb.toString());
	}
}
