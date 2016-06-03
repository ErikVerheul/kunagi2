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

import java.util.Collections;
import java.util.List;

import scrum.client.common.AScrumWidget;
import scrum.client.issues.Issue;
import scrum.client.project.Project;
import scrum.client.project.Requirement;
import scrum.client.sprint.Sprint;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class UpcomingPoWorkWidget extends AScrumWidget {

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

		appendFixedBugs(sb);
		appendStoriesToReview(sb);
		appendIssueInbox(sb);

		sb.append("</div>");
		html.setHTML(sb.toString());
	}

	private void appendIssueInbox(StringBuilder sb) {
		Project project = getCurrentProject();
		List<Issue> issues = project.getOpenIssues(false);
		Collections.sort(issues, project.getIssuesOrderComparator());
		if (!issues.isEmpty()) {
			sb.append("New issues to classify:");
			sb.append("<ul>");
			int count = 0;
			for (Issue issue : issues) {
				sb.append("<li>");
				sb.append(issue.toHtml());
				sb.append("</li>");
				if (count++ >= 5) break;
			}
			sb.append("</ul>");
		}
	}

	private void appendStoriesToReview(StringBuilder sb) {
		Sprint sprint = getCurrentProject().getCurrentSprint();
		List<Requirement> decidableRequirements = sprint.getDecidableUndecidedRequirements();
		Collections.sort(decidableRequirements, sprint.getRequirementsOrderComparator());
		if (!decidableRequirements.isEmpty()) {
			sb.append("Completed Stories to review:");
			sb.append("<ul>");
			for (Requirement requirement : decidableRequirements) {
				sb.append("<li>");
				sb.append(requirement.toHtml());
				sb.append("</li>");
			}
			sb.append("</ul>");
		}
	}

	private void appendFixedBugs(StringBuilder sb) {
		Project project = getCurrentProject();
		List<Issue> fixedBugs = project.getFixedBugs();
		Collections.sort(fixedBugs, project.getIssuesOrderComparator());
		if (!fixedBugs.isEmpty()) {
			sb.append("Fixed bugs to review:");
			sb.append("<ul>");
			for (Issue issue : fixedBugs) {
				sb.append("<li>");
				sb.append(issue.toHtml());
				sb.append("</li>");
			}
			sb.append("</ul>");
		}
	}
}
