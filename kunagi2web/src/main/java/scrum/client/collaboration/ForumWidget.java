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
package scrum.client.collaboration;

import scrum.client.collaboration.RequestForumServiceCall;
import ilarkesto.gwt.client.ButtonWidget;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Widget;

public class ForumWidget extends AScrumWidget {

	public BlockListWidget<ForumSupport> list;

	@Override
	protected Widget onInitialization() {
		new RequestForumServiceCall(false).execute();

		list = new BlockListWidget<ForumSupport>(ForumItemBlock.FACTORY);
		list.setAutoSorter(ForumSupport.COMPARATOR);

		PagePanel page = new PagePanel();
		page.addHeader("Forum", new ButtonWidget(new CreateSubjectAction()));
		page.addSection(list);
		page.addSection(new UserGuideWidget(getLocalizer().views().forum(),
				getCurrentProject().getSubjects().size() < 5, getCurrentUser().getHideUserGuideForumModel()));
		return page;
	}

	@Override
	protected void onUpdate() {
		list.setObjects(getCurrentProject().getEntitiesWithComments());
		super.onUpdate();
	}

	public boolean select(ForumSupport entity) {
		if (entity == null) return false;
		if (!list.contains(entity)) update();
		return list.showObject(entity);
	}
}
