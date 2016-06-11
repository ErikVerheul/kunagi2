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

import ilarkesto.gwt.client.HyperlinkWidget;

import java.util.Map;

import scrum.client.common.ShowEntityAction;
import scrum.client.project.Project;

import com.google.gwt.user.client.ui.Widget;

public class Wikipage extends GWikipage implements ForumSupport {

	public Wikipage(Project project, String name) {
		setProject(project);
		setName(name);
		setText("= " + name + " =\n\n...");
	}

	public Wikipage(Map data) {
		super(data);
	}

	@Override
	public String toHtml() {
		return Wiki.toHtml("[[" + getName() + "]]");
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	public String getReference() {
		return "wki";
	}

	@Override
	public Widget createForumItemWidget() {
		return new HyperlinkWidget(new ShowEntityAction(WikiWidget.class, this, getLabel()));
	}

}
