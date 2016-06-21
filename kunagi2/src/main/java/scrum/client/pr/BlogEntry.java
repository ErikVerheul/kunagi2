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
package scrum.client.pr;

import ilarkesto.core.base.Utl;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.gwt.client.HyperlinkWidget;
import ilarkesto.gwt.client.editor.AFieldModel;

import java.util.Comparator;
import java.util.Map;

import scrum.client.ScrumGwt;
import scrum.client.collaboration.ForumSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.common.ShowEntityAction;
import scrum.client.project.Project;

import com.google.gwt.user.client.ui.Widget;

public class BlogEntry extends GBlogEntry implements ReferenceSupport, ForumSupport {

	public static final String REFERENCE_PREFIX = "blg";

	public BlogEntry(Map data) {
		super(data);
	}

	public BlogEntry(Project project) {
		setProject(project);
	}

	@Override
	public String getReference() {
		return REFERENCE_PREFIX + getNumber();
	}

	@Override
	public String getLabel() {
		return getTitle();
	}

	@Override
	public String toHtml() {
		return ScrumGwt.toHtml(this, getTitle());
	}

	@Override
	public String toString() {
		return getReference() + " " + getTitle();
	}

	@Override
	public Widget createForumItemWidget() {
		return new HyperlinkWidget(new ShowEntityAction(BlogWidget.class, this, getLabel()));
	}

	public static final Comparator<BlogEntry> DATE_COMPARATOR = new Comparator<BlogEntry>() {

		@Override
		public int compare(BlogEntry a, BlogEntry b) {
			return Utl.compare(b.getDateAndTime(), a.getDateAndTime());
		}
	};

	private AFieldModel<String> dateModel;

	public AFieldModel<String> getDateModel() {
		if (dateModel == null) dateModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				DateAndTime dateAndTime = getDateAndTime();
				return dateAndTime != null ? dateAndTime.getDate().toString() : null;
			}
		};
		return dateModel;
	}
}