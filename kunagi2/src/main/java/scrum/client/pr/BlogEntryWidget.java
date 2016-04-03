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
package scrum.client.pr;

import ilarkesto.core.base.Str;
import ilarkesto.gwt.client.AMultiSelectionViewEditWidget;
import ilarkesto.gwt.client.AOutputViewEditWidget;
import ilarkesto.gwt.client.TableBuilder;
import scrum.client.ScrumGwt;
import scrum.client.admin.User;
import scrum.client.common.AScrumWidget;
import scrum.client.journal.ChangeHistoryWidget;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class BlogEntryWidget extends AScrumWidget {

	private BlogEntry blogEntry;

	public BlogEntryWidget(BlogEntry blogEntry) {
		super();
		this.blogEntry = blogEntry;
	}

	@Override
	protected Widget onInitialization() {
		TableBuilder tb = ScrumGwt.createFieldTable();

		tb.addFieldRow("Title", blogEntry.getTitleModel());
		tb.addFieldRow("Text", blogEntry.getTextModel());
		tb.addFieldRow("Date", blogEntry.getDateAndTimeModel());
		tb.addFieldRow("Authors", new AMultiSelectionViewEditWidget<User>() {

			@Override
			protected void onViewerUpdate() {
				setViewerItemsAsHtml(blogEntry.getAuthors());
			}

			@Override
			protected void onEditorUpdate() {
				setEditorItems(blogEntry.getProject().getParticipants());
				setEditorSelectedItems(blogEntry.getAuthors());
			}

			@Override
			protected void onEditorSubmit() {
				blogEntry.setAuthors(getEditorSelectedItems());
			}

			@Override
			public boolean isEditable() {
				return true;
			}
		});
		if (blogEntry.isPublished() && blogEntry.getProject().getHomepageDir() != null) {
			tb.addFieldRow("Public URL", new AOutputViewEditWidget() {

				@Override
				protected void onViewerUpdate() {
					String url = blogEntry.getProject().getHomepageUrl();
					if (Str.isBlank(url)) {
						setViewer(new Label("Yes"));
					} else {
						if (!url.endsWith("/")) url += "/";
						url += blogEntry.getReference() + ".html";
						setViewer(new HTML("<a href=\"" + url + "\" target=\"_blank\">" + url + "</a>"));
					}
				}
			});
		}
		tb.addRow(new ChangeHistoryWidget(blogEntry), 2);

		return TableBuilder.row(20, tb.createTable(), ScrumGwt.createEmoticonsAndComments(blogEntry));
	}
}
