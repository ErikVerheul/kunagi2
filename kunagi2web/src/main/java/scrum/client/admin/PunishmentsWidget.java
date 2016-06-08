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

import ilarkesto.gwt.client.AFieldValueWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.editor.IntegerEditorWidget;
import ilarkesto.gwt.client.editor.TextEditorWidget;
import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.project.Project;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PunishmentsWidget extends AScrumWidget {

	@Override
	protected Widget onInitialization() {

		PagePanel page = new PagePanel();
		Project project = getCurrentProject();

		TableBuilder main = ScrumGwt.createFieldTable();

		main.add(Gwt.createDiv("PunishmentsWidget-tableHeader", "User"));
		main.add(Gwt.createDiv("PunishmentsWidget-tableHeader", "Misconducts"));
		main.add(Gwt.createDiv("PunishmentsWidget-tableHeader", "Punishment"));
		main.nextRow();

		for (User u : project.getParticipants()) {
			Label nameLabel = new Label(u.getName());
			nameLabel.getElement().getStyle().setProperty("color", project.getUserConfig(u).getColor());
			main.add(nameLabel);
			main.add(new IntegerEditorWidget(u.getProjectConfig().getMisconductsModel()));
			main.add(new PunishmentViewer(u.getProjectConfig(), project));
			main.nextRow();
		}

		main.addRow(Gwt.createSpacer(1, 20), 3);
		main.addRow(new Label("Sum"), null, new PunishmentSumViewer(project));

		page.addHeader("Courtroom");
		page.addSection(main.createTable());

		if (project.isScrumMaster(getCurrentUser())) {
			TableBuilder settings = ScrumGwt.createFieldTable();
			settings.addFieldRow("Factor", new IntegerEditorWidget(project.getPunishmentFactorModel()));
			settings.addFieldRow("Unit", new TextEditorWidget(project.getPunishmentUnitModel()));
			page.addHeader("Punishment Configuration");
			page.addSection(settings.createTable());
		}

		page.addSection(new UserGuideWidget(getLocalizer().views().courtRoom(), true, getCurrentUser()
				.getHideUserGuideCourtroomModel()));

		return page;
	}

	private static class PunishmentSumViewer extends AFieldValueWidget {

		private Project project;

		public PunishmentSumViewer(Project project) {
			super();
			this.project = project;
		}

		@Override
		protected void onUpdate() {
			setText(project.getTotalMisconducts() * project.getPunishmentFactor() + " " + project.getPunishmentUnit());
		}
	}

	private static class PunishmentViewer extends AFieldValueWidget {

		private ProjectUserConfig userConfig;
		private Project project;

		public PunishmentViewer(ProjectUserConfig userConfig, Project project) {
			super();
			this.userConfig = userConfig;
			this.project = project;
		}

		@Override
		protected void onUpdate() {
			setText(userConfig.getMisconducts() * project.getPunishmentFactor() + " " + project.getPunishmentUnit());
		}
	}

}
