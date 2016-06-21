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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.base.Str;
import ilarkesto.gwt.client.AAction;
import ilarkesto.gwt.client.AViewEditWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.HyperlinkWidget;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.editor.ATextEditorModel;
import ilarkesto.gwt.client.editor.RichtextEditorWidget;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import scrum.client.common.AScrumGwtEntity;
import scrum.client.common.AScrumWidget;

public class CommentsWidget extends AScrumWidget {

	private static final int COMMENTS_PER_PAGE = 5;

	private SimplePanel editorWrapper;
	private FlowPanel commentListPanel;
	private AScrumGwtEntity parent;
	private Map<Comment, CommentWidget> widgets;

	private HyperlinkWidget activateCommentLink;
	private RichtextEditorWidget editor;

	private int currentPage = 1;

	public CommentsWidget(AScrumGwtEntity parent) {
		this.parent = parent;
	}

	@Override
	protected Widget onInitialization() {
		new RequestCommentsServiceCall(parent.getId()).execute(); // TODO commentsManagerComponent

		activateCommentLink = new HyperlinkWidget(new ActivateCommentEditorAction());

		widgets = new HashMap<Comment, CommentWidget>();

		editorWrapper = new SimplePanel();
		editorWrapper.setWidget(activateCommentLink);

		commentListPanel = new FlowPanel();

		FlowPanel panel = new FlowPanel();
		panel.setStyleName("CommentsWidget");
		panel.add(editorWrapper);
		panel.add(commentListPanel);
		return panel;
	}

	@Override
	protected void onUpdate() {
		if (editor == null && editorWrapper.getWidget() != activateCommentLink) {
			editorWrapper.setWidget(activateCommentLink);
		} else if (editor != null && editorWrapper.getWidget() != editor) {
			editorWrapper.setWidget(editor);
		}
		updateCommentList();
		super.onUpdate();
	}

	private void updateCommentList() {
		for (CommentWidget widget : widgets.values()) {
			if (widget.isEditMode()) return;
		}
		commentListPanel.clear();
		List<Comment> comments = parent.getComments();
		Collections.sort(comments, Comment.REVERSE_DATEANDTIME_COMPARATOR);
		List<Comment> pageComments = filterCurrentPageComments(comments);
		for (Comment comment : pageComments) {
			CommentWidget widget = getWidget(comment);
			commentListPanel.add(widget);
		}

		if (comments.size() > COMMENTS_PER_PAGE) {
			commentListPanel.add(createPageNavigator(comments.size()));
		}
	}

	private Widget createPageNavigator(int commentCount) {
		TableBuilder tb = new TableBuilder();
		tb.setWidth(null);
		tb.addFieldLabel("Pages:");
		int page = 1;
		int endIdx = COMMENTS_PER_PAGE - 1;
		tb.addSpacer(5, 1);
		if (currentPage > 1) {
			tb.add(Gwt.createDiv("CommentsWidget-pageNavigator-page", new HyperlinkWidget(new ShowPageAction("<",
					currentPage - 1))));
		} else {
			tb.add(Gwt.createDiv("CommentsWidget-pageNavigator-page-disabled", "<"));
		}
		tb.addSpacer(5, 1);
		while (true) {
			tb.addSpacer(5, 1);
			if (currentPage == page) {
				tb.add(Gwt.createDiv("CommentsWidget-pageNavigator-currentPage", String.valueOf(page)));
			} else {
				tb.add(Gwt.createDiv("CommentsWidget-pageNavigator-page",
					new HyperlinkWidget(new ShowPageAction(String.valueOf(page), page))));
			}
			if (endIdx >= commentCount - 1) break;
			page++;
			endIdx += COMMENTS_PER_PAGE;
		}
		tb.addSpacer(10, 1);
		if (page > currentPage) {
			tb.add(Gwt.createDiv("CommentsWidget-pageNavigator-page", new HyperlinkWidget(new ShowPageAction(">",
					currentPage + 1))));
		} else {
			tb.add(Gwt.createDiv("CommentsWidget-pageNavigator-page-disabled", ">"));
		}
		return Gwt.createDiv("CommentsWidget-pageNavigator", tb.createTable());
	}

	private List<Comment> filterCurrentPageComments(List<Comment> comments) {
		List<Comment> ret = new ArrayList<Comment>(COMMENTS_PER_PAGE);
		int startIdx = (currentPage - 1) * COMMENTS_PER_PAGE;
		int endIdx = startIdx + COMMENTS_PER_PAGE - 1;
		if (endIdx >= comments.size()) endIdx = comments.size() - 1;
		for (int i = startIdx; i <= endIdx; i++) {
			ret.add(comments.get(i));
		}
		return ret;
	}

	private CommentWidget getWidget(Comment comment) {
		CommentWidget widget = widgets.get(comment);
		if (widget == null) {
			widget = new CommentWidget(comment);
			widgets.put(comment, widget);
		}
		return widget;
	}

	private void postComment(String text) {
		if (Str.isBlank(text)) return;
		text = text.trim();
		Comment comment = new Comment(parent, getAuth().getUser(), text);
		getDao().createComment(comment);
		update();
	}

	public boolean isEditorActive() {
		return editor != null;
	}

	private void activateEditor() {
		this.editor = new RichtextEditorWidget(new ATextEditorModel() {

			@Override
			public void setValue(String text) {
				postComment(text);
			}

			@Override
			public String getValue() {
				return null;
			}

		});
		this.editor.setAutosave(false);
		this.editor.setApplyButtonLabel("Post comment");
		this.editor.switchToEditMode();
		this.editor.setModeSwitchHandler(new AViewEditWidget.ModeSwitchHandler() {

			@Override
			public void onViewerActivated(AViewEditWidget widget) {
				editor = null;
				update();
			}

			@Override
			public void onEditorActivated(AViewEditWidget widget) {}
		});
		update();
	}

	private class ActivateCommentEditorAction extends AAction {

		@Override
		public String getLabel() {
			return "Create a comment...";
		}

		@Override
		protected void onExecute() {
			activateEditor();
		}

	}

	private class ShowPageAction extends AAction {

		private String label;
		private int page;

		public ShowPageAction(String label, int page) {
			super();
			this.label = label;
			this.page = page;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public String getTooltip() {
			return "Show page " + page;
		}

		@Override
		protected void onExecute() {
			currentPage = page;
			update();
		}
	}

}
