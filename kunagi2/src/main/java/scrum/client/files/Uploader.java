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

import com.google.gwt.user.client.ui.DialogBox;
import static ilarkesto.core.logging.ClientLog.DEBUG;

public class Uploader extends GUploader implements FileUploadedHandler {

	private DialogBox dialog;
	private UploadedFileHandler uploadedFileHandler;

	public void showUploadDialog(Integer topPosition, UploadedFileHandler uploadedFileHandler) {
		DEBUG("Acitvating file upload dialog");
		this.uploadedFileHandler = uploadedFileHandler;
		UploadWidget uploadWidget = UploadWidget.showDialog(topPosition);
		this.dialog = uploadWidget.getDialog();
	}

        @Override
	public void onFileUploaded(FileUploadedEvent event) {
		File file = event.getFile();
		DEBUG("File received:", file);
		if (dialog != null) {
			dialog.hide();
			dialog = null;
		}
		if (uploadedFileHandler != null) {
			uploadedFileHandler.onFileUploaded(file);
			uploadedFileHandler = null;
		}
	}

	public static interface UploadedFileHandler {

		void onFileUploaded(File file);

	}
}
