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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import scrum.client.ScrumGwt;
import scrum.client.admin.User;
import scrum.client.common.AScrumWidget;
import scrum.client.issues.Issue;
import scrum.client.project.Project;
import scrum.client.project.Requirement;
import scrum.client.sprint.Task;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class TeamTasksWidget extends AScrumWidget {

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
		sb.append("<div class='TeamTasksWidget'>");

		List<User> lazyUsers = new ArrayList<User>();
		List<User> team = new LinkedList<User>(project.getTeamMembers());
		Collections.sort(team, User.NAME_COMPARATOR);
		User currentUser = getCurrentUser();
		if (team.contains(currentUser)) {
			team.remove(currentUser);
			team.add(0, currentUser);
		}
		for (User user : team) {
			List<Task> tasks = project.getClaimedTasks(user);
			List<Issue> issues = project.getClaimedBugs(user);
			if (tasks.isEmpty() && issues.isEmpty()) {
				lazyUsers.add(user);
				continue;
			}

			sb.append("<div class='TeamTasksWidget-user'>");
			sb.append("<span style='color: ").append(project.getUserConfig(user).getColor()).append(";'>");
			sb.append(user.getName());
			sb.append("</span> is working on");

			sb.append("<ul>");
			for (Issue issue : issues) {
				sb.append("<li>").append(issue.toHtml()).append("</li>");
			}
			List<Requirement> requirements = new ArrayList<Requirement>(getRequirements(tasks));
			Collections.sort(requirements, project.getCurrentSprint().getRequirementsOrderComparator());
			for (Requirement req : requirements) {
				List<Task> usersTasks = req.getClaimedTasks(user);
				Collections.sort(usersTasks, req.getTasksOrderComparator());
				for (Task task : usersTasks) {
					sb.append("<li>").append(task.toHtml()).append(" (").append(ScrumGwt.createHtmlReference(req))
							.append(")</li>");
				}
			}
			sb.append("</ul></div>");
		}

		for (User user : lazyUsers) {
			sb.append("<div class='TeamTasksWidget-user'>");
			sb.append("<span style='color: ").append(project.getUserConfig(user).getColor()).append(";'>");
			sb.append(user.getName());
			sb.append("</span> is working on <span style='color: red;'>nothing</span></div>");
		}

		sb.append("</div>");
		html.setHTML(sb.toString());
	}

	private Set<Requirement> getRequirements(List<Task> tasks) {
		Set<Requirement> reqs = new HashSet<Requirement>();
		for (Task task : tasks) {
			reqs.add(task.getRequirement());
		}
		return reqs;
	}

}
