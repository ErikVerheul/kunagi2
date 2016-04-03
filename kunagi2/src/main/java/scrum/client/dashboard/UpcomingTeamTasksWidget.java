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

import java.util.Collections;
import java.util.List;

import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.issues.Issue;
import scrum.client.project.Project;
import scrum.client.project.Requirement;
import scrum.client.sprint.Sprint;
import scrum.client.sprint.Task;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class UpcomingTeamTasksWidget extends AScrumWidget {

	private HTML html;

	@Override
	protected Widget onInitialization() {
		html = new HTML();
		return html;
	}

	@Override
	protected void onUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='UpcomingTasksWidget'>");

		Project project = getCurrentProject();

		List<Issue> criticalBugs = project.getUnclaimedBugs(2);
		Collections.sort(criticalBugs, project.getIssuesOrderComparator());
		if (!criticalBugs.isEmpty()) {
			sb.append("<span style='color: red; font-weight: bold;'>Unclaimed critical bugs</span>:");
			sb.append("<ul>");
			for (Issue issue : criticalBugs) {
				sb.append("<li>");
				sb.append(issue.toHtml());
				sb.append("</li>");
			}
			sb.append("</ul>");
		}

		List<Issue> severeBugs = project.getUnclaimedBugs(1);
		Collections.sort(severeBugs, project.getIssuesOrderComparator());
		if (!severeBugs.isEmpty()) {
			sb.append("<span style='color: red;'>Unclaimed severe bugs</span>:");
			sb.append("<ul>");
			for (Issue issue : severeBugs) {
				sb.append("<li>");
				sb.append(issue.toHtml());
				sb.append("</li>");
			}
			sb.append("</ul>");
		}

		int maxTasks = project.getTeamMembers().size() - criticalBugs.size() - severeBugs.size();
		int minTasks = 10 - criticalBugs.size();
		if (maxTasks < minTasks) maxTasks = minTasks;
		int taskCount = 0;
		Sprint sprint = project.getCurrentSprint();
		List<Task> tasks = sprint.getUnclaimedTasks(false);
		Collections.sort(tasks, sprint.getTasksOrderComparator());
		if (!tasks.isEmpty()) {
			sb.append("Next upcoming tasks:");
			sb.append("<ul>");
			for (Task task : tasks) {
				Requirement req = null;
				if (!task.isRequirement(req) && taskCount > 0) break;
				req = task.getRequirement();
				sb.append("<li>").append(task.toHtml()).append(" (").append(ScrumGwt.createHtmlReference(req))
						.append(")</li>");
				taskCount++;
				if (taskCount == maxTasks) break;
			}
			sb.append("</ul></div>");
		}

		if (taskCount < maxTasks) {
			List<Issue> normalBugs = project.getUnclaimedBugs(0);
			Collections.sort(normalBugs, project.getIssuesOrderComparator());
			if (!normalBugs.isEmpty()) {
				sb.append("<span style='color: black;'>Unclaimed normal bugs</span>:");
				sb.append("<ul>");
				for (Issue issue : normalBugs) {
					sb.append("<li>");
					sb.append(issue.toHtml());
					sb.append("</li>");
					taskCount++;
					if (taskCount == maxTasks) break;
				}
				sb.append("</ul>");
			}
		}

		if (taskCount < maxTasks) {
			List<Issue> minorBugs = project.getUnclaimedBugs(-1);
			Collections.sort(minorBugs, project.getIssuesOrderComparator());
			if (!minorBugs.isEmpty()) {
				sb.append("<span style='color: #555;'>Unclaimed minor bugs</span>:");
				sb.append("<ul>");
				for (Issue issue : minorBugs) {
					sb.append("<li>");
					sb.append(issue.toHtml());
					sb.append("</li>");
					taskCount++;
					if (taskCount == maxTasks) break;
				}
				sb.append("</ul>");
			}
		}

		sb.append("</div>");
		html.setHTML(sb.toString());
	}
}
