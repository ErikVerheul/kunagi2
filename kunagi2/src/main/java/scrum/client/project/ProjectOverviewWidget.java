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
package scrum.client.project;

import ilarkesto.gwt.client.TableBuilder;
import scrum.client.ScrumGwt;
import scrum.client.collaboration.CommentsWidget;
import scrum.client.common.AScrumWidget;
import scrum.client.sprint.Sprint;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ProjectOverviewWidget extends AScrumWidget {

	public static final int CHART_WIDTH = 800;
	public static final int CHART_HEIGHT = 270;

	private Image sprintChart;

	@Override
	protected Widget onInitialization() {
		final Project project = getCurrentProject();

		PagePanel page = new PagePanel();
		page.addHeader("Project Properties");
		page.addSection(createProjectOverview(project));

		Sprint sprint = project.getCurrentSprint();
		if (sprint != null) {
			page.addHeader("Current Sprint");
			page.addSection(createCurrentSprintOverview(sprint));
		}

		return page;
	}

	private Widget createProjectOverview(Project project) {

		TableBuilder tb = ScrumGwt.createFieldTable();
		tb.addFieldRow("Name", project.getLabelModel());
		tb.addFieldRow("Vision", project.getVisionModel());

		return TableBuilder.row(20, tb.createTable(), new CommentsWidget(project));
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		if (sprintChart != null) {
			Sprint sprint = getCurrentProject().getCurrentSprint();
			if (sprint != null) sprintChart.setUrl(getChartUrl(sprint));
		}
	}

	private Widget createCurrentSprintOverview(Sprint sprint) {
		sprintChart = new Image(getChartUrl(sprint), 0, 0, CHART_WIDTH, CHART_HEIGHT);
		return TableBuilder.row(20, sprintChart, new CommentsWidget(sprint));
	}

	private String getChartUrl(Sprint sprint) {
		int width = Window.getClientWidth() - 280;
		width = width / 2;
		return sprint.getChartUrl(width, CHART_HEIGHT);
	}

}
