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
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.editor.RichtextEditorWidget;
import scrum.client.ScrumGwt;
import scrum.client.admin.ProjectBlock;
import scrum.client.common.AScrumWidget;
import scrum.client.common.WeekdaySelectorEditorWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Widget;

public class ProjectAdminWidget extends AScrumWidget {

	@Override
	protected Widget onInitialization() {

		final Project project = getCurrentProject();

		PagePanel page = new PagePanel();

		page.addHeader("Project properties");
		TableBuilder tbPro = ScrumGwt.createFieldTable();
		tbPro.addFieldRow("Name", project.getLabelModel());
		tbPro.addFieldRow("Vision", new RichtextEditorWidget(project.getVisionModel()));

		ProjectBlock.addParticipantsAndAdminsFieldRows(project, tbPro);
		ProjectBlock.addRolesFieldRows(project, tbPro);
		tbPro.addFieldRow("Free days", new WeekdaySelectorEditorWidget(project.getFreeDaysWeekdaySelectorModel()));
		tbPro.addFieldRow("Release info", project.getReleasingInfoModel());
		page.addSection(tbPro.createTable());

		page.addHeader("Product descriptions");
		TableBuilder tbDescr = ScrumGwt.createFieldTable();
		tbDescr.addFieldRow("Name", project.getProductLabelModel());
		tbDescr.addFieldRow("Tagline", project.getShortDescriptionModel());
		tbDescr.addFieldRow("Short description", project.getDescriptionModel());
		tbDescr.addFieldRow("Long description", project.getLongDescriptionModel());
		page.addSection(tbDescr.createTable());

		page.addHeader("Project homepage", new ButtonWidget(new UpdateProjectHomepageAction(project)));
		TableBuilder tbHomepage = ScrumGwt.createFieldTable();
		tbHomepage.addFieldRow("Homepage directory", project.getHomepageDirModel());
		tbHomepage.addFieldRow("Homepage URL", project.getHomepageUrlModel());
		tbHomepage.addFieldRow("Update automatically", project.getAutoUpdateHomepageModel());
		page.addSection(tbHomepage.createTable());

		page.addHeader("Script integration");
		TableBuilder tbScripts = ScrumGwt.createFieldTable();
		tbScripts.addFieldRow("Release script", project.getReleaseScriptPathModel());
		page.addSection(tbScripts.createTable());

		page.addHeader("Project support");
		TableBuilder tbSupport = ScrumGwt.createFieldTable();
		tbSupport.addFieldRow("Support email", project.getSupportEmailModel());
		tbSupport.addFieldRow("Issue reply template", project.getIssueReplyTemplateModel());
		tbSupport.addFieldRow("Subscriber notification template", project.getSubscriberNotificationTemplateModel());
		page.addSection(tbSupport.createTable());

		return page;
	}
}
