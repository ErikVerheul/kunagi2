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
package scrum.server.journal;

import ilarkesto.io.IO;
import ilarkesto.webapp.RequestWrapper;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.ServletConfig;
import scrum.server.WebSession;
import scrum.server.common.AKunagiServlet;
import scrum.server.project.Project;
import scrum.server.project.ProjectDao;

public class JournalRssServlet extends AKunagiServlet {

	private static final long serialVersionUID = 1;

	private transient ProjectDao projectDao;

	@Override
	protected void onRequest(RequestWrapper<WebSession> req) throws IOException {
		// TODO auth
		String projectId = req.getMandatory("projectId");
		Project project = projectDao.getById(projectId);
		req.setContentTypeRss();
		project.writeJournalAsRss(req.getOutputStream(), IO.UTF_8);
	}

	@Override
	protected void onInit(ServletConfig config) {
		super.onInit(config);
		projectDao = webApplication.getProjectDao();
	}

}
