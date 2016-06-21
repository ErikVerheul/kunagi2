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
package scrum.server.files;

import ilarkesto.base.PermissionDeniedException;
import ilarkesto.webapp.RequestWrapper;

import java.io.IOException;

import scrum.server.ScrumWebApplication;
import scrum.server.WebSession;
import scrum.server.common.AKunagiServlet;
import scrum.server.project.Project;

public class FileDownloadServlet extends AKunagiServlet {

	@Override
	protected void onRequest(RequestWrapper<WebSession> req) throws IOException {
		String fileId = req.get("fileId");
		String reference = req.get("reference");
		if (fileId == null && reference == null) throw new RuntimeException("fileId==null && reference==null");

		Project project = getProject(req);
		File file;

		if (fileId != null) {
			file = ScrumWebApplication.get().getFileDao().getById(fileId);
			if (file == null) throw new RuntimeException("File does not exist: " + fileId);
			if (!file.isProject(project)) throw new PermissionDeniedException();
		} else {
			int number = Integer.parseInt(reference.substring(3));
			file = project.getFileByNumber(number);
			if (file == null) throw new RuntimeException("File does not exist: " + reference);
		}

		req.serve(file.getJavaFile(), false, false);
	}

}
