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
package scrum.client.journal;

import ilarkesto.gwt.client.Gwt;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Widget;

public class JournalWidget extends AScrumWidget {

	BlockListWidget<ProjectEvent> list;

	@Override
	protected Widget onInitialization() {
		list = new BlockListWidget<ProjectEvent>(ProjectEventBlock.FACTORY);
		list.setAutoSorter(ProjectEvent.DATE_AND_TIME_COMPARATOR);

		PagePanel page = new PagePanel();
		page.addHeader("Project Journal");
		page.addSection(list);
		page.addSection(Gwt.createServletDownloadLink("projectJournal.rss?projectId=" + getCurrentProject().getId(),
			"RSS Feed"));
		page.addSection(new UserGuideWidget(getLocalizer().views().journal(), getCurrentProject().getProjectEvents()
				.size() < 15, getCurrentUser().getHideUserGuideJournalModel()));
		return page;
	}

	@Override
	protected void onUpdate() {
		list.setObjects(getCurrentProject().getProjectEvents());
		super.onUpdate();
	}

	public boolean select(ProjectEvent event) {
		return list.showObject(event);
	}

}
