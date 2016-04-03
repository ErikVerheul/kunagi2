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
package scrum.client.project;

import ilarkesto.gwt.client.ButtonWidget;
import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Widget;

public class QualityBacklogWidget extends AScrumWidget {

	public BlockListWidget<Quality> list;

	@Override
	protected Widget onInitialization() {
		list = new BlockListWidget<Quality>(QualityBlock.FACTORY);
		list.setAutoSorter(Quality.LABEL_COMPARATOR);

		PagePanel page = new PagePanel();
		page.addHeader("Quality Backlog", new ButtonWidget(new CreateQualityAction()));
		page.addSection(list);
		page.addSection(ScrumGwt.createPdfLink("Download as PDF", "qualityBacklog", getCurrentProject()));
		page.addSection(new UserGuideWidget(getLocalizer().views().qualities(), getCurrentProject().getQualitys()
				.size() < 5, getCurrentUser().getHideUserGuideQualityBacklogModel()));
		return page;
	}

	@Override
	protected void onUpdate() {
		list.setObjects(getCurrentProject().getQualitys());
		super.onUpdate();
	}

	public boolean select(Quality quality) {
		if (!list.contains(quality)) update();
		return list.showObject(quality);
	}
}
