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
package scrum.client.test;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.base.Str;
import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.animation.AnimatingFlowPanel;
import java.util.Map;
import scrum.client.Dao;
import scrum.client.ScrumGwt;
import scrum.client.ScrumGwtApplication;
import scrum.client.collaboration.Comment;
import scrum.client.common.AScrumAction;
import scrum.client.common.AScrumWidget;
import scrum.client.core.DeleteEntityServiceCall;
import scrum.client.core.ServiceCaller;
import scrum.client.issues.Issue;
import scrum.client.project.Requirement;
import scrum.client.sprint.Task;
import scrum.client.workspace.PagePanel;
import scrum.client.workspace.Ui;

public class ScrumStatusWidget extends AScrumWidget {

	private Dao dao;
	private ServiceCaller serviceCaller;

	private SimplePanel entityCountWrapper;
	private SimplePanel stateInformationWrapper;

	@Override
	protected Widget onInitialization() {
		dao = Scope.get().getComponent(Dao.class);
		ScrumGwtApplication app = (ScrumGwtApplication) Scope.get().getComponent("app");
		serviceCaller = Scope.get().getComponent(ServiceCaller.class);

		entityCountWrapper = new SimplePanel();
		stateInformationWrapper = new SimplePanel();

		PagePanel page = new PagePanel();
		page.addHeader("Entities");
		page.addSection(entityCountWrapper);

		page.addHeader("State information");
		page.addSection(stateInformationWrapper);

		if (!app.getApplicationInfo().isProductionStage()) {
			page.addHeader("Generators");
			page.addSection(createGenerators());
		}

		return page;
	}

	private Widget createGenerators() {
		TableBuilder tb = new TableBuilder();
		tb.setWidth(null);
		tb.setCellPadding(5);
		tb.addRow(new ButtonWidget(new ToggleListAnimationsAction()));
		tb.addRow(new ButtonWidget(new ThrowExceptionAction()));
		tb.addRow(new ButtonWidget(new ThrowServerExceptionAction()));
		tb.addRow(new ButtonWidget(new ShowWidgetsTesterAction()));
		tb.addRow(new ButtonWidget(new GenerateRequirementsAction()));
		tb.addRow(new ButtonWidget(new GenerateIssuesAction()));
		tb.addRow(new ButtonWidget(new GenerateTasksAction()));
		tb.addRow(new ButtonWidget(new GenerateCommentsAction()));
		return tb.createTable();
	}

	@Override
	protected void onUpdate() {
		TableBuilder tb = new TableBuilder();
		tb.setWidth(null);
		tb.setCellSpacing(5);
		for (Map.Entry<String, Integer> entry : dao.getEntityCounts().entrySet()) {
			tb.addRow(Gwt.createFieldLabel(entry.getKey()), new Label(String.valueOf(entry.getValue())));
		}
		entityCountWrapper.setWidget(tb.createTable());
		stateInformationWrapper.setWidget(createStateInformation());
		super.onUpdate();
	}

	private Widget createStateInformation() {
		TableBuilder tb = ScrumGwt.createFieldTable();
		tb.addFieldRow("activeServiceCallCount",
			new Label(String.valueOf(Str.concat(serviceCaller.getActiveServiceCalls(), ", "))));
		tb.addFieldRow("conversationNumber", new Label(String.valueOf(serviceCaller.getConversationNumber())));
		tb.addFieldRow("entityIdBase", new Label(dao.getEntityIdBase()));
		tb.addFieldRow("entityIdCounter", new Label(String.valueOf(dao.getEntityIdCounter())));
		return tb.createTable();
	}

	static class ToggleListAnimationsAction extends AScrumAction {

		@Override
		public String getLabel() {
			return AnimatingFlowPanel.animationsDisabled ? "Enable list animations" : "Disable list animations";
		}

		@Override
		protected void onExecute() {
			AnimatingFlowPanel.animationsDisabled = !AnimatingFlowPanel.animationsDisabled;
		}
	}

	static class ThrowExceptionAction extends AScrumAction {

		@Override
		public String getLabel() {
			return "Throw Client Exception";
		}

		@Override
		protected void onExecute() {
			throw new RuntimeException("User initiated exception.<br>:-D");
		}
	}

	static class ThrowServerExceptionAction extends AScrumAction {

		@Override
		public String getLabel() {
			return "Throw Server Exception";
		}

		@Override
		protected void onExecute() {
			new DeleteEntityServiceCall("bad-entity-id").execute();
		}
	}

	static class ShowWidgetsTesterAction extends AScrumAction {

		@Override
		public String getLabel() {
			return "Show widgets tester";
		}

		@Override
		protected void onExecute() {
			Scope.get().getComponent(Ui.class).getWorkspace().getWorkarea().show(new WidgetsTesterWidget());
		}

	}

	class GenerateCommentsAction extends AScrumAction {

		@Override
		public String getLabel() {
			return "Generate Comments";
		}

		@Override
		protected void onExecute() {
			DateAndTime time = DateAndTime.now();
			Requirement req = getCurrentProject().getCurrentSprint().getRequirements().get(0);
			for (int i = 0; i < COUNT; i++) {
				Comment comment = new Comment(req, getCurrentUser(), time + " " + longText());
				dao.createComment(comment);
			}
		}

	}

	class GenerateTasksAction extends AScrumAction {

		@Override
		public String getLabel() {
			return "Generate Tasks";
		}

		@Override
		protected void onExecute() {
			DateAndTime time = DateAndTime.now();
			Requirement req = getCurrentProject().getCurrentSprint().getRequirements().get(0);
			for (int i = 0; i < COUNT; i++) {
				Task task = new Task(req);
				task.setLabel("Generated Task " + time + " - #" + i);
				task.setDescription(longText());
				dao.createTask(task);
			}
		}

	}

	class GenerateIssuesAction extends AScrumAction {

		@Override
		public String getLabel() {
			return "Generate Issues";
		}

		@Override
		protected void onExecute() {
			DateAndTime time = DateAndTime.now();
			for (int i = 0; i < COUNT; i++) {
				Issue issue = new Issue(getCurrentProject());
				issue.setLabel("Generated Issue " + time + " - #" + i);
				issue.setDescription(longText());
				dao.createIssue(issue);
			}
		}

	}

	class GenerateRequirementsAction extends AScrumAction {

		@Override
		public String getLabel() {
			return "Generate Stories";
		}

		@Override
		protected void onExecute() {
			DateAndTime time = DateAndTime.now();
			for (int i = 0; i < COUNT; i++) {
				final Requirement req = new Requirement(getCurrentProject());
				req.setLabel("Generated Story " + time + " - #" + i);
				req.setDescription(longText());
				req.setTestDescription(longText());
				dao.createRequirement(req);
			}
		}

	}

	private static String longText() {
		return text(10);
	}

	private static String text(int lines) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lines; i++) {
			sb.append("This is stupid text. You should not waste your time to read it. There is nothing valuable to find.\n");
		}
		return sb.toString();
	}

	private static final int COUNT = 10;

}
