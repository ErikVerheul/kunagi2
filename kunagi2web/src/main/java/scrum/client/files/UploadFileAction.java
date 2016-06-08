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
package scrum.client.files;

import ilarkesto.core.scope.Scope;
import scrum.client.common.AScrumAction;
import scrum.client.common.TooltipBuilder;
import scrum.client.files.Uploader.UploadedFileHandler;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class UploadFileAction extends AScrumAction {

	@Override
	public String getLabel() {
		return "Upload file";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Upload a local file to the repository.");
	}

	@Override
	protected void onExecute() {
		Scope.get().getComponent(Uploader.class).showUploadDialog(null, new ShowFileHandler());
	}

	static class ShowFileHandler implements UploadedFileHandler {

		@Override
		public void onFileUploaded(File file) {
			Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(file);
		}

	}

}
