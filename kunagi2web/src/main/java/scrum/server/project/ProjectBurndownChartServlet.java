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
package scrum.server.project;

import ilarkesto.core.logging.Log;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import scrum.client.project.ProjectOverviewWidget;

public class ProjectBurndownChartServlet extends HttpServlet {

	private static final Log LOG = Log.get(ProjectBurndownChartServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String projectId = req.getParameter("projectId");
		String width = req.getParameter("width");
		if (width == null) width = String.valueOf(ProjectOverviewWidget.CHART_WIDTH);
		String height = req.getParameter("height");
		if (height == null) height = String.valueOf(ProjectOverviewWidget.CHART_HEIGHT);

		LOG.debug("Generating project burndown chart:", width + "x" + height, projectId);

		resp.setContentType("image/png");
		// ScrumWebApplication.get().getBurndownChart().writeProjectBurndownChart(resp.getOutputStream(),
		// projectId,
		// Integer.parseInt(width), Integer.parseInt(height));
	}

}
