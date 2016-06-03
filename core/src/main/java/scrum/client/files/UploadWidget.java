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

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;
import gwtupload.client.Uploader;
import ilarkesto.core.logging.Log;
import ilarkesto.core.scope.Scope;

import java.util.Set;

import scrum.client.common.AScrumWidget;
import generated.client.communication.PingServiceCall;
import scrum.client.project.Project;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UploadWidget extends AScrumWidget {

	private static final Log log = Log.get(UploadWidget.class);

	private FormFlowPanel formPanel = new FormFlowPanel();
	private Button button = new Button();
	private Label statusLabel;
	private SingleUploader uploader;
	private DialogBox dialog;

	public UploadWidget() {
		statusLabel = new Label();
		uploader = new SingleUploader(FileInputType.BROWSER_INPUT, new UploadStatus(), button, formPanel);
		uploader.setAutoSubmit(true);
		Uploader.setStatusInterval(1000);
	}

	@Override
	protected Widget onInitialization() {
		uploader.addOnFinishUploadHandler(new FinishHandler());
		return uploader;
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		button.setVisible(false);
	}

	public static UploadWidget showDialog(Integer topPosition) {
		UploadWidget uploadWidget = new UploadWidget();

		uploadWidget.dialog = new DialogBox(true, true);
		DialogBox dialog = uploadWidget.dialog;
		dialog.setAnimationEnabled(true);
		dialog.setWidget(uploadWidget.update());

		dialog.center();
		if (topPosition != null) dialog.setPopupPosition(dialog.getPopupLeft(), topPosition);
		dialog.show();

		return uploadWidget;
	}

	public DialogBox getDialog() {
		return dialog;
	}

	private class FinishHandler implements IUploader.OnFinishUploaderHandler {

		@Override
		public void onFinish(IUploader ul) {
			if (ul.getStatus() != Status.SUCCESS) {
				new PingServiceCall().execute();
				if (dialog != null) dialog.hide();
			}
		}

	}

	private class UploadStatus implements IUploadStatus {

		private Status status;
		private String filename;

		@Override
		public HandlerRegistration addCancelHandler(UploadCancelHandler handler) {
			return null;
		}

		@Override
		public Status getStatus() {
			return status;
		}

		@Override
		public Widget getWidget() {
			return statusLabel;
		}

		@Override
		public IUploadStatus newInstance() {
			return null;
		}

		@Override
		public void setCancelConfiguration(Set<CancelBehavior> config) {}

		@Override
		public void setError(String error) {
			log.error("Upload failed: " + error);
			Window.alert("Upload failed: " + error);
			statusLabel.setText(error);
		}

		@Override
		public void setFileName(String name) {
			this.filename = name;
		}

		@Override
		public void setI18Constants(UploadStatusConstants strs) {

		}

		@Override
		public void setStatus(Status status) {
			if (this.status == null) {
				dialog.setAutoHideEnabled(false);
				formPanel.hideFileField();
				statusLabel.getElement().getStyle().setPadding(10, Unit.PX);
			} else if (status != Status.INPROGRESS && filename != null) {
				statusLabel.setText("Uploading " + filename);
			}
			this.status = status;
		}

		@Override
		public void setStatusChangedHandler(UploadStatusChangedHandler handler) {}

		@Override
		public void setVisible(boolean b) {}

		@Override
		public void setProgress(int done, int total) {
			if (total == 0) {
				statusLabel.setText("Uploading " + filename);
			} else {
				int percent = done * 100 / total;
				statusLabel.setText("Uploading " + filename + " (" + percent + "%)");
			}
		}
	}

	public class FormFlowPanel extends FormPanel {

		FlowPanel formElements = new FlowPanel();
		Widget fileField;

		public FormFlowPanel() {
			super.add(formElements);
			Project project = Scope.get().getComponent(Project.class);
			Hidden projectIdField = new Hidden("projectId", project.getId());
			projectIdField.setID("uploadProjectId");
			add(projectIdField);
		}

		@Override
		public void add(Widget w) {
			formElements.add(w);
			if (w != button) fileField = w;
		}

		public void hideFileField() {
			fileField.setVisible(false);
		}
	}

}
