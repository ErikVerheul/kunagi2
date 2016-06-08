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
package scrum.client.collaboration;

import ilarkesto.gwt.client.AOutputViewEditWidget;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.editor.RichtextEditorWidget;
import scrum.client.ScrumGwt;
import scrum.client.admin.ProjectUserConfig;
import scrum.client.admin.User;
import scrum.client.common.AScrumWidget;
import scrum.client.img.Img;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CommentWidget extends AScrumWidget {

	private Label date;

	private Comment comment;

	private RichtextEditorWidget editor;

	public CommentWidget(Comment comment) {
		this.comment = comment;
	}

	@Override
	protected Widget onInitialization() {
		Label authorLabel = new Label(comment.getAuthorName());
		authorLabel.setStyleName("CommentWidget-header-author");

		User author = comment.getAuthor();
		if (author != null) {
			ProjectUserConfig userConfig = getCurrentProject().getUserConfig(author);
			String color = userConfig == null ? "darkgray" : userConfig.getColor();
			authorLabel.getElement().getStyle().setProperty("color", color);
		}

		date = new Label();
		date.setStyleName("CommentWidget-header-date");

		HorizontalPanel header = new HorizontalPanel();
		header.setStyleName("CommentWidget-header");
		header.add(Gwt.createFlowPanel(authorLabel, date));
		if (getCurrentProject().getHomepageDir() != null) {
			header.add(new AOutputViewEditWidget() {

				@Override
				protected void onViewerUpdate() {
					Widget widget = null;
					if (getCurrentProject().isHomepagePublishingEnabled()) {
						if (comment.isPublished()) {
							widget = Img.bundle.publicComment().createImage();
							widget.setTitle("This comment is visible on the homepage.");
						} else {
							widget = new ButtonWidget(new PublishCommentAction(comment)).update();
						}
					}
					setViewer(widget == null ? null : ScrumGwt.createDiv("Comment-Widget-header-pub", widget));
				}
			});
		}

		FlowPanel panel = new FlowPanel();
		panel.setStyleName("CommentWidget");
		panel.add(header);

		editor = new RichtextEditorWidget(comment.getTextModel());
		editor.setAutosave(false);
		panel.add(editor);

		return panel;
	}

	@Override
	protected void onUpdate() {
		date.setText(comment.getDateAndTime().getPeriodToNow().toShortestString() + " ago");
		super.onUpdate();
	}

	public boolean isEditMode() {
		return editor != null && editor.isEditMode();
	}

}
