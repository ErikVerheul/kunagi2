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
package scrum.client.admin;

import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.ButtonWidget;
import scrum.client.Dao;
import scrum.client.common.BlockListWidget;
import scrum.client.project.CreateExampleProjectAction;
import scrum.client.project.CreateProjectAction;
import scrum.client.project.Project;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Widget;

public class ProjectSelectorWidget extends AWidget {

	private BlockListWidget<Project> list;

	@Override
	protected Widget onInitialization() {

		list = new BlockListWidget<Project>(ProjectBlock.FACTORY);
		list.setAutoSorter(Project.LAST_OPENED_COMPARATOR);

		PagePanel page = new PagePanel();
		page.addHeader("Projects", new ButtonWidget(new CreateProjectAction()), new ButtonWidget(
				new CreateExampleProjectAction()));
		page.addSection(list);
		return page;
	}

	@Override
	protected void onUpdate() {
		list.setObjects(Dao.get().getProjects());
		super.onUpdate();
	}

	public BlockListWidget<Project> getList() {
		return this.list;
	}

	public boolean select(Project project) {
		if (!list.contains(project)) update();
		return list.showObject(project);
	}

}
