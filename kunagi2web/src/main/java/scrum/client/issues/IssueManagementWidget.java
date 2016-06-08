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
package scrum.client.issues;

import generated.scrum.client.issues.RequestAcceptedIssuesServiceCall;
import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;

import java.util.List;

import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListSelectionManager;
import scrum.client.common.BlockListWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.project.Project;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class IssueManagementWidget extends AScrumWidget {

	private IssueManager issueManager;

	public BlockListWidget<Issue> openList;
	public BlockListWidget<Issue> bugList;
	public BlockListWidget<Issue> ideaList;
	public BlockListWidget<Issue> closedList;
	private BlockListSelectionManager selectionManager;
	private SimplePanel suspensionStatusButtonWrapper;

	@Override
	protected Widget onInitialization() {
		new RequestAcceptedIssuesServiceCall().execute();
		issueManager = Scope.get().getComponent(IssueManager.class);

		selectionManager = new BlockListSelectionManager();

		openList = new BlockListWidget<Issue>(IssueBlock.FACTORY);
		openList.setSelectionManager(selectionManager);
		openList.setAutoSorter(Issue.ISSUE_DATE_COMPARATOR);

		bugList = new BlockListWidget<Issue>(IssueBlock.FACTORY);
		bugList.setSelectionManager(selectionManager);
		bugList.setAutoSorter(Issue.SEVERITY_COMPARATOR);
		// bugList.setAutoSorter(getCurrentProject().getIssuesOrderComparator());
		// bugList.setDndSorting(true);
		// bugList.setMoveObserver(new UrgentMoveObserver());

		ideaList = new BlockListWidget<Issue>(IssueBlock.FACTORY);
		ideaList.setSelectionManager(selectionManager);
		ideaList.setAutoSorter(Issue.ACCEPT_DATE_COMPARATOR);

		closedList = new BlockListWidget<Issue>(IssueBlock.FACTORY);
		closedList.setSelectionManager(selectionManager);
		closedList.setAutoSorter(Issue.CLOSE_DATE_COMPARATOR);

		suspensionStatusButtonWrapper = new SimplePanel();

		PagePanel inboxPage = new PagePanel();
		inboxPage.addHeader("issue inbox (decision required)", new ButtonWidget(new CreateIssueAction()),
			suspensionStatusButtonWrapper);
		inboxPage.addSection(openList);

		PagePanel documentationPage = new PagePanel();
		documentationPage.addSection(new UserGuideWidget(getLocalizer().views().issues(), getCurrentProject()
				.getIssues().size() < 15, getCurrentUser().getHideUserGuideIssuesModel()));

		PagePanel bugsPage = PagePanel.createSimple("bugs (Team needs to fix this)", bugList);
		bugsPage.addSection(ScrumGwt.createPdfLink("Download as PDF", "bugList", getCurrentProject()));

		PagePanel ideasPage = PagePanel.createSimple("ideas (Product owner needs to create stories)", ideaList);
		ideasPage.addSection(ScrumGwt.createPdfLink("Download as PDF", "ideaList", getCurrentProject()));

		return Gwt.createFlowPanel(inboxPage, Gwt.createSpacer(1, 10), bugsPage, Gwt.createSpacer(1, 10), ideasPage,
			Gwt.createSpacer(1, 10), createClosedPage(), Gwt.createSpacer(1, 10), documentationPage);
	}

	private Widget createClosedPage() {
		PagePanel page = new PagePanel();
		page.addHeader("closed issues (done or rejected)", new ButtonWidget(new RequestClosedIssuesAction()));
		page.addSection(closedList);
		return page;
	}

	@Override
	protected void onUpdate() {
		suspensionStatusButtonWrapper.setWidget(new ButtonWidget(
				issueManager.isSuspendedIssuesVisible() ? new HideSuspendedIssuesAction()
						: new ShowSuspendedIssuesAction()));

		Project project = getCurrentProject();
		openList.setObjects(project.getOpenIssues(issueManager.isSuspendedIssuesVisible()));
		bugList.setObjects(project.getBugs());
		ideaList.setObjects(project.getIdeas());
		closedList.setObjects(project.getClosedIssues());
		super.onUpdate();
	}

	public boolean select(Issue issue) {
		update();
		return selectionManager.select(issue);
	}

	class UrgentMoveObserver implements Runnable {

		@Override
		public void run() {
			List<Issue> issues = bugList.getObjects();
			getCurrentProject().updateUrgentIssuesOrder(issues);
			update();
		}

	}
}
