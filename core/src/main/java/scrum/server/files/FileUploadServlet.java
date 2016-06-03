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

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;
import ilarkesto.base.PermissionDeniedException;
import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;
import ilarkesto.webapp.Servlet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import scrum.server.GwtConversation;
import scrum.server.ScrumWebApplication;
import scrum.server.WebSession;
import scrum.server.project.Project;

public class FileUploadServlet extends UploadAction {

	private static final Log log = Log.get(FileUploadServlet.class);

	@Override
	public String executeAction(HttpServletRequest req, List<FileItem> sessionFiles) throws UploadActionException {
		log.debug("File received", "\n" + Servlet.toString(req, "    "));
		sessionFiles = new ArrayList<FileItem>(sessionFiles);
		String projectId = null;
		for (FileItem item : new ArrayList<FileItem>(sessionFiles)) {
			String fieldName = item.getFieldName();
			if (item.isFormField()) {
				sessionFiles.remove(item);
				if (fieldName.equals("projectId")) projectId = item.getString();
			}
		}
		if (projectId == null) throw new RuntimeException("projectId == null");
		if (sessionFiles.size() != 1) throw new IllegalStateException("sessionFiles.size() == " + sessionFiles.size());

		ScrumWebApplication webApp = ScrumWebApplication.get();
		WebSession session = (WebSession) webApp.getWebSession(req);

		Project project = (Project) ScrumWebApplication.get().getDaoService().getById(projectId);
		if (!project.isVisibleFor(session.getUser())) throw new PermissionDeniedException();

		FileItem item = sessionFiles.get(0);
		try {
			String filename = getFilename(item.getName());
			java.io.File f = new java.io.File(project.getFileRepositoryPath() + "/" + filename);
			int count = 0;
			while (f.exists()) {
				count++;
				f = new java.io.File(project.getFileRepositoryPath() + "/" + insertSuffix(filename, count));
			}
			IO.copyDataToFile(item.getInputStream(), f);

			File file = webApp.getFileDao().postFile(f, project);
			webApp.getTransactionService().commit();
			for (GwtConversation conversation : webApp.getConversationsByProject(project, null)) {
				conversation.sendToClient(file);
			}
			return file.getReference();
		} catch (Exception e) {
			log.error(e);
			throw new UploadActionException(e.getMessage());
		} finally {
			removeSessionFileItems(req);
		}
	}

	private String insertSuffix(String name, int count) {
		int idx = name.lastIndexOf('.');
		if (idx > 0) return name.substring(0, idx) + "_" + count + name.substring(idx);
		return name + "_" + count;
	}

	private String getFilename(String name) {
		if (name == null) return "unnamed.bin";
		name = name.replace('\\', '/');
		int idx = name.lastIndexOf('/');
		if (idx >= 0) return name.substring(idx + 1);
		return name;
	}

	@Override
	public void checkRequest(HttpServletRequest request) {
		ScrumWebApplication webApp = ScrumWebApplication.get();
		Integer maxFileSize = webApp.getSystemConfig().getMaxFileSize();
		maxSize = maxFileSize == null ? DEFAULT_REQUEST_LIMIT_KB : maxFileSize * 1024L * 1024L;
		super.checkRequest(request);
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
}
