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
package scrum.client.files;

import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;
import scrum.client.ScrumGwt;
import scrum.client.collaboration.CommentsWidget;
import scrum.client.common.AScrumWidget;
import scrum.client.project.Project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class FileWidget extends AScrumWidget {

	private File file;
	private Project project;

	public FileWidget(File impediment) {
		super();
		this.file = impediment;
	}

	@Override
	protected Widget onInitialization() {
		project = Scope.get().getComponent(Project.class);

		TableBuilder left = ScrumGwt.createFieldTable();
		left.addFieldRow("Label", file.getLabelModel());
		String relativeUrl = "fileDownload/" + file.getFilename() + "?projectId=" + project.getId() + "&fileId="
				+ file.getId();
		HTML downloadLink = Gwt.createServletDownloadLink(relativeUrl, file.getFilename());
		left.addFieldRow("Download", downloadLink);
		left.addFieldRow("Notes", file.getNoteModel());
		left.addFieldRow("Uploaded", file.getUploadTimeModel());

		TableBuilder right = new TableBuilder();
		if (file.isImage()) {
			String url = GWT.getModuleBaseURL() + relativeUrl;
			right.addRow(new HTML("<a href='" + url + "' target='_blank'><img src='" + url + "' width='300px'></a>"));
		}
		right.addRow(new CommentsWidget(file));

		return TableBuilder.row(20, left.createTable(), right.createTable());
	}

}
