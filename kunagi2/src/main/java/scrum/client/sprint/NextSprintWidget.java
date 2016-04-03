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
package scrum.client.sprint;

import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.editor.RichtextEditorWidget;
import scrum.client.ScrumGwt;
import scrum.client.collaboration.CommentsWidget;
import scrum.client.common.AScrumWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.project.Project;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Widget;

public class NextSprintWidget extends AScrumWidget {

	private Sprint sprint;

	@Override
	protected Widget onInitialization() {
		sprint = getSprint();

		TableBuilder tb = ScrumGwt.createFieldTable();
		tb.setColumnWidths("15%", "15%", "15%", "15%", "15%", "15%");
		tb.addFieldRow("Label", sprint.getLabelModel(), 5);
		tb.addFieldRow("Goal", sprint.getGoalModel(), 5);
		tb.addField("Begin", sprint.getBeginModel());
		tb.addField("Length", sprint.getLengthInDaysModel());
		tb.addFieldRow("End", sprint.getEndModel());
		tb.addFieldRow("Planning Note", new RichtextEditorWidget(sprint.getPlanningNoteModel()), 5);

		PagePanel page = new PagePanel();

		page.addHeader("Next Sprint", new ButtonWidget(new SwitchToNextSprintAction()));
		page.addSection(TableBuilder.row(20, tb.createTable(), new CommentsWidget(sprint)));

		page.addHeader("Current Sprint");
		page.addSection(new SprintWidget(getCurrentSprint()));

		page.addSection(new UserGuideWidget(getLocalizer().views().nextSprint(), getCurrentProject().getSprints()
				.size() < 5, getCurrentUser().getHideUserGuideNextSprintModel()));
		return page;
	}

	@Override
	protected boolean isResetRequired() {
		return sprint != getSprint();
	}

	private Sprint getSprint() {
		Project project = getCurrentProject();
		if (project == null) return null;
		return project.getNextSprint();
	}

}
