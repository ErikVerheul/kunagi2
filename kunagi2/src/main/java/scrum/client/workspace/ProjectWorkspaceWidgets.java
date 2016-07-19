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
package scrum.client.workspace;

import com.google.gwt.user.client.ui.Widget;
import static ilarkesto.core.logging.ClientLog.DEBUG;
import static ilarkesto.core.logging.ClientLog.WARN;
import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.AGwtEntity;
import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.EntityDoesNotExistException;
import ilarkesto.gwt.client.SwitcherWidget;
import scrum.client.admin.ProjectUserConfigWidget;
import scrum.client.admin.PunishmentsWidget;
import scrum.client.admin.SystemConfigWidget;
import scrum.client.admin.SystemMessageManagerWidget;
import scrum.client.admin.User;
import scrum.client.admin.UserListWidget;
import scrum.client.calendar.CalendarWidget;
import scrum.client.calendar.SimpleEvent;
import scrum.client.collaboration.Chat;
import scrum.client.collaboration.ForumSupport;
import scrum.client.collaboration.ForumWidget;
import scrum.client.collaboration.Subject;
import scrum.client.collaboration.WikiWidget;
import scrum.client.collaboration.Wikipage;
import scrum.client.context.UserHighlightSupport;
import scrum.client.core.RequestEntityByReferenceServiceCall;
import scrum.client.core.RequestEntityServiceCall;
import scrum.client.dashboard.DashboardWidget;
import scrum.client.files.File;
import scrum.client.files.FileRepositoryWidget;
import scrum.client.impediments.Impediment;
import scrum.client.impediments.ImpedimentListWidget;
import scrum.client.issues.Issue;
import scrum.client.issues.IssueManagementWidget;
import scrum.client.journal.JournalWidget;
import scrum.client.journal.ProjectEvent;
import scrum.client.pr.BlogEntry;
import scrum.client.pr.BlogWidget;
import scrum.client.project.ProductBacklogWidget;
import scrum.client.project.Project;
import scrum.client.project.ProjectAdminWidget;
import scrum.client.project.ProjectDataReceivedEvent;
import scrum.client.project.ProjectDataReceivedHandler;
import scrum.client.project.ProjectOverviewWidget;
import scrum.client.project.Quality;
import scrum.client.project.QualityBacklogWidget;
import scrum.client.project.Requirement;
import scrum.client.release.Release;
import scrum.client.release.ReleaseManagementWidget;
import scrum.client.risks.Risk;
import scrum.client.risks.RiskListWidget;
import scrum.client.search.Search;
import scrum.client.search.SearchResultsWidget;
import scrum.client.sprint.NextSprintWidget;
import scrum.client.sprint.Sprint;
import scrum.client.sprint.SprintBacklogWidget;
import scrum.client.sprint.SprintHistoryWidget;
import scrum.client.sprint.Task;
import scrum.client.tasks.WhiteboardWidget;

/**
 *
 * @author erik
 */
public class ProjectWorkspaceWidgets extends GProjectWorkspaceWidgets implements ProjectDataReceivedHandler {

	private ProjectSidebarWidget sidebar = new ProjectSidebarWidget();
	private DashboardWidget dashboard;
	private ProjectOverviewWidget projectOverview;
	private WhiteboardWidget whiteboard;
	private SprintBacklogWidget sprintBacklog;
	private ProductBacklogWidget productBacklog;
	private QualityBacklogWidget qualityBacklog;
	private ForumWidget forum;
	private CalendarWidget calendar;
	private NextSprintWidget nextSprint;
	private ImpedimentListWidget impedimentList;
	private IssueManagementWidget issueList;
	private RiskListWidget riskList;
	private ReleaseManagementWidget releaseList;
	private WikiWidget wiki;
	private SprintHistoryWidget sprintHistory;
	private ProjectUserConfigWidget projectUserConfig;
	private PunishmentsWidget punishments;
	private JournalWidget projectEventList;
	private FileRepositoryWidget fileRepository;
	private BlogWidget blog;
	private ProjectAdminWidget projectAdmin;
	private SystemConfigWidget systemConfig;
	private SystemMessageManagerWidget systemMessageManager;
	private UserListWidget userList;

	private PageSet pages = new PageSet();

	private boolean searchResultsAdded;

	private User highlightedUser;

    /**
     *
     */
    @Override
	public void initialize() {
		projectOverview = new ProjectOverviewWidget();
		dashboard = new DashboardWidget();

		pages.addPage(new Page(dashboard, "Dashboard", null));

		String sprintGroupKey = "sprint";
		whiteboard = new WhiteboardWidget();
		pages.addPage(new Page(whiteboard, "Whiteboard", sprintGroupKey));
		sprintBacklog = new SprintBacklogWidget();
		pages.addPage(new Page(sprintBacklog, "Sprint Backlog", sprintGroupKey));

		String productGroupKey = "product";
		productBacklog = new ProductBacklogWidget();
		pages.addPage(new Page(productBacklog, "Product Backlog", productGroupKey));
		qualityBacklog = new QualityBacklogWidget();
		pages.addPage(new Page(qualityBacklog, "Qualities", productGroupKey));
		issueList = new IssueManagementWidget();
		pages.addPage(new Page(issueList, "Issues", productGroupKey));
		releaseList = new ReleaseManagementWidget();
		pages.addPage(new Page(releaseList, "Releases", productGroupKey));

		String projectGroupKey = "project";
		impedimentList = new ImpedimentListWidget();
		pages.addPage(new Page(impedimentList, "Impediments", projectGroupKey));
		riskList = new RiskListWidget();
		pages.addPage(new Page(riskList, "Risks", projectGroupKey));
		projectEventList = new JournalWidget();
		pages.addPage(new Page(projectEventList, "Journal", projectGroupKey));
		nextSprint = new NextSprintWidget();
		pages.addPage(new Page(nextSprint, "Next Sprint", projectGroupKey));
		sprintHistory = new SprintHistoryWidget();
		pages.addPage(new Page(sprintHistory, "Sprint History", projectGroupKey));

		String collaborationGroupKey = "collaboration";
		wiki = new WikiWidget();
		pages.addPage(new Page(wiki, "Wiki", collaborationGroupKey));
		forum = new ForumWidget();
		pages.addPage(new Page(forum, "Forum", collaborationGroupKey));
		calendar = new CalendarWidget();
		pages.addPage(new Page(calendar, "Calendar", collaborationGroupKey));
		fileRepository = new FileRepositoryWidget();
		pages.addPage(new Page(fileRepository, "File Repository", collaborationGroupKey));
		punishments = new PunishmentsWidget();
		pages.addPage(new Page(punishments, "Courtroom", collaborationGroupKey));

		String administrationKey = "administration";
		blog = new BlogWidget();
		pages.addPage(new Page(blog, "Blog", administrationKey));
		projectUserConfig = new ProjectUserConfigWidget();
		pages.addPage(new Page(projectUserConfig, "Personal Preferences", administrationKey));
                Project local_project = user.getCurrentProject(); //guess this was missing from the merge with r.0.17
		if (local_project.isScrumTeamMember(user)) {
			projectAdmin = new ProjectAdminWidget();
			pages.addPage(new Page(projectAdmin, "Project administration", administrationKey));
		}
		if (user.isAdmin()) {
			systemMessageManager = new SystemMessageManagerWidget();
			pages.addPage(new Page(systemMessageManager, "System Message", administrationKey));
			systemConfig = new SystemConfigWidget();
			pages.addPage(new Page(systemConfig, "System Configuration", administrationKey));
			userList = new UserListWidget();
			pages.addPage(new Page(userList, "User Management", administrationKey));
		}

		ScrumNavigatorWidget navigator = getSidebar().getNavigator();
		navigator.addItem("Dashboard", dashboard);
		addNavigatorGroup(navigator, sprintGroupKey, "Sprint");
		addNavigatorGroup(navigator, productGroupKey, "Product");
		addNavigatorGroup(navigator, projectGroupKey, "Project");
		addNavigatorGroup(navigator, collaborationGroupKey, "Collaboration");
		addNavigatorGroup(navigator, administrationKey, "Administration");
	}

	private void addNavigatorGroup(ScrumNavigatorWidget navigator, String groupKey, String label) {
		navigator.addGroup(label, groupKey);
		for (Page page : pages.getPagesByGroupKey(groupKey)) {
			navigator.addItem(groupKey, page.getLabel(), page.getWidget());
		}
	}

    /**
     *
     * @param event
     */
    @Override
	public void onProjectDataReceived(ProjectDataReceivedEvent event) {
		Scope.get().getComponent(Ui.class).show(sidebar, dashboard);
	}

    /**
     *
     * @param user
     */
    public void highlightUser(User user) {
		if (highlightedUser == user) {
                    return;
        }
		Widget currentWidget = getWorkarea().getCurrentWidget();
		if (currentWidget instanceof UserHighlightSupport) {
			((UserHighlightSupport) currentWidget).highlightUser(user);
		}
		highlightedUser = user;
	}

    /**
     *
     * @return
     */
    public ProjectUserConfigWidget getProjectUserConfig() {
		return projectUserConfig;
	}

    /**
     *
     * @param reference
     */
    public void showEntityByReference(final String reference) {
		DEBUG("Showing entity by reference:", reference);

		AGwtEntity entity = dao.getEntityByReference(reference);
		if (entity != null) {
			showEntity(entity);
			return;
		}
		Scope.get().getComponent(Ui.class).lock("Searching for " + reference);
		new RequestEntityByReferenceServiceCall(reference).execute(new Runnable() {

			@Override
			public void run() {
				AGwtEntity entity = dao.getEntityByReference(reference);
				Ui ui = Scope.get().getComponent(Ui.class);
				if (entity == null) {
					ui.unlock();
					if (reference.length() > 4 && reference.startsWith("[[")) {
						String pageName = reference.substring(2, reference.length() - 2);
						showWiki(pageName);
					} else {
						Scope.get().getComponent(Chat.class)
								.postSystemMessage("Object does not exist: " + reference, false);
					}
					return;
				}
				ui.unlock();
				showEntity(entity);
			}
		});
	}

    /**
     *
     * @param entityId
     */
    public void showEntityById(final String entityId) {
		DEBUG("Showing entity by id:", entityId);

		AGwtEntity entity;
		try {
			entity = dao.getEntity(entityId);
		} catch (EntityDoesNotExistException ex) {
			entity = null;
		}
		if (entity != null) {
			showEntity(entity);
			return;
		}
		Scope.get().getComponent(Ui.class).lock("Searching for " + entityId);
		new RequestEntityServiceCall(entityId).execute(new Runnable() {

			@Override
			public void run() {
				AGwtEntity entity;
				try {
					entity = dao.getEntity(entityId);
				} catch (EntityDoesNotExistException ex) {
					entity = null;
				}
				Ui ui = Scope.get().getComponent(Ui.class);
				if (entity == null) {
					ui.unlock();
					Scope.get().getComponent(Chat.class).postSystemMessage("Entity does not exist: " + entityId, false);
					return;
				}
				ui.unlock();
				showEntity(entity);
			}
		});
	}

    /**
     *
     * @param entity
     */
    public void showEntity(AGwtEntity entity) {
		DEBUG("Showing entity:", entity);
		if (entity instanceof Task) {
			showTask((Task) entity);
		} else if (entity instanceof Requirement) {
			showRequirement((Requirement) entity);
		} else if (entity instanceof Issue) {
			showIssue((Issue) entity);
		} else if (entity instanceof Risk) {
			showRisk((Risk) entity);
		} else if (entity instanceof Quality) {
			showQualityBacklog((Quality) entity);
		} else if (entity instanceof Subject) {
			showForum((Subject) entity);
		} else if (entity instanceof Impediment) {
			showImpediment((Impediment) entity);
		} else if (entity instanceof File) {
			showFile((File) entity);
		} else if (entity instanceof Sprint) {
			showSprint((Sprint) entity);
		} else if (entity instanceof Wikipage) {
			showWiki((Wikipage) entity);
		} else if (entity instanceof SimpleEvent) {
			showCalendar((SimpleEvent) entity);
		} else if (entity instanceof Project) {
			showDashboard();
		} else if (entity instanceof ProjectEvent) {
			showProjectEventList((ProjectEvent) entity);
		} else if (entity instanceof Release) {
			showRelease((Release) entity);
		} else if (entity instanceof BlogEntry) {
			showBlog((BlogEntry) entity);
		} else if (entity instanceof User) {
			showUserList((User) entity);
		} else {
			throw new RuntimeException("Showing entity not supported: " + entity.getClass().getName());
		}

		navigator.updateHistory(Page.getPageName(getWorkarea().getCurrentWidget()), entity);
	}

    /**
     *
     * @param entityId
     * @return
     */
    public String getPageForEntity(String entityId) {
		try {
			return getPageForEntity(dao.getEntityByReference(entityId));
		} catch (EntityDoesNotExistException ex) {
			return null;
		}
	}

    /**
     *
     * @param entity
     * @return
     */
    public String getPageForEntity(AGwtEntity entity) {
		return Page.getPageName(getWidgetForEntity(entity));
	}

    /**
     *
     * @param entity
     * @return
     */
    public AWidget getWidgetForEntity(AGwtEntity entity) {
		// TODO only for subjects and entities with comments
		if (getWorkarea().isShowing(forum)) {
                    return forum;
        }

                if (entity instanceof Task) {
                    return getWidgetForEntity(((Task) entity).getRequirement());
        }

		if (entity instanceof Requirement) {
			Requirement requirement = (Requirement) entity;
                        if (requirement.isClosed() && !requirement.isInCurrentSprint()) {
                return sprintHistory;
            }

			if (getWorkarea().isShowing(sprintHistory)) {
				// FIXME multiple requirements on same page
                            boolean existsInHistory = !dao.getSprintReportsByRejectedRequirement(requirement).isEmpty();
                            if (existsInHistory) {
                    return sprintHistory;
                }
			}

                        boolean inCurrentSprint = requirement.isInCurrentSprint();
                        if (inCurrentSprint) {
				if (getWorkarea().isShowing(sprintBacklog)) {
                    return sprintBacklog;
                }
				return whiteboard;
                        }
                        return productBacklog;
		}
                if (entity instanceof Sprint) {
                    Sprint sprint = (Sprint) entity;
                    if (sprint.isCurrent()) {
                        return whiteboard;
                    }
                    return sprintHistory;
                }
                if (entity instanceof Issue) {
                    return issueList;
                }
		if (entity instanceof Risk) {
                    return riskList;
        }
		if (entity instanceof Quality) {
                    return qualityBacklog;
        }
                if (entity instanceof Subject) {
                    return forum;
        }
                if (entity instanceof Impediment) {
                    return impedimentList;
                }
                if (entity instanceof File) {
                    return fileRepository;
        }
                if (entity instanceof Wikipage) {
                    return wiki;
        }
                if (entity instanceof SimpleEvent) {
            return calendar;
        }
		if (entity instanceof Project) {
            return dashboard;
        }
		if (entity instanceof ProjectEvent) {
            return projectEventList;
        }
		if (entity instanceof Release) {
            return releaseList;
        }
		if (entity instanceof BlogEntry) {
            return blog;
        }
		if (entity instanceof User) {
            return userList;
        }
		return null;
	}

    /**
     *
     * @param pageName
     */
    public void showPage(String pageName) {
		Page page = pages.getPageByName(pageName);
		if (page == null) {
			if (pageName.equals(Page.getPageName(SearchResultsWidget.class))) {
				ScrumNavigatorWidget local_navigator = getSidebar().getNavigator();
				SearchResultsWidget results = Scope.get().getComponent(Search.class).getResultsWidget();
				if (!searchResultsAdded) {
					local_navigator.addItem("Search Results", results);
					searchResultsAdded = true;
				}
				select(results);
			} else {
				WARN("Page does not exist:", pageName);
			}
			return;
		}
		select(page.getWidget());
	}

    /**
     *
     */
    public void showDashboard() {
		select(dashboard);
	}

	private void showSprint(Sprint sprint) {
		if (sprint.isCurrent()) {
			showSprintBacklog((Requirement) null);
		} else {
			showSprintHistory(sprint);
		}
	}

	private void showProjectEventList(ProjectEvent event) {
		select(projectEventList);
		projectEventList.select(event);
	}

	private void showSprintHistory(Sprint sprint) {
		select(sprintHistory);
		sprintHistory.select(sprint);
	}

	private void showSprintHistory(Requirement requirement) {
		select(sprintHistory);
		sprintHistory.select(requirement);
	}

	private void showSprintHistory(Task task) {
		select(sprintHistory);
		sprintHistory.select(task);
	}

	private void showIssue(Issue issue) {
		select(issueList);
		issueList.select(issue);
	}

	private void showRelease(Release release) {
		select(releaseList);
		releaseList.select(release);
	}

	private void showImpediment(Impediment impediment) {
		select(impedimentList);
		impedimentList.select(impediment);
	}

	private void showFile(File file) {
		select(fileRepository);
		fileRepository.select(file);
	}

	private void showRisk(Risk risk) {
		select(riskList);
		riskList.select(risk);
	}

	private void showTask(Task task) {
		AWidget widget = getWidgetForEntity(task);

		if (widget == whiteboard) {
			showWhiteboard(task);
		} else if (widget == sprintHistory) {
			showSprintHistory(task);
		} else {
			showSprintBacklog(task);
		}
	}

	private void showRequirement(Requirement requirement) {
		// TODO generalize

		AWidget widget = getWidgetForEntity(requirement);
		if (widget == sprintHistory) {
                    showSprintHistory(requirement);
		} else if (widget == whiteboard) {
			showWhiteboard(requirement);
                } else if (widget == sprintBacklog) {
			showSprintBacklog(requirement);
		} else if (widget == productBacklog) {
			showProductBacklog(requirement);
		} else {
			throw new IllegalStateException("Unknown widget: " + widget.getClass().getName());
		}
	}

	private void showWiki(String page) {
		select(wiki);
		if (page != null) {
            wiki.showPage(page);
        }
	}

	private void showWiki(Wikipage page) {
		select(wiki);
		if (page != null) {
            wiki.showPage(page);
        }
	}

	private SwitcherWidget getWorkarea() {
		return Scope.get().getComponent(Ui.class).getWorkspace().getWorkarea();
	}

        private void showWhiteboard(Task task) {
            select(whiteboard);
            whiteboard.selectTask(task);
	}

	private void showWhiteboard(Requirement requirement) {
		select(whiteboard);
		whiteboard.selectRequirement(requirement);
	}

	private void showSprintBacklog(Task task) {
		select(sprintBacklog);
		sprintBacklog.selectTask(task);
	}

    /**
     *
     * @param requirement
     */
    public void showSprintBacklog(Requirement requirement) {
		select(sprintBacklog);
		if (requirement != null) {
            sprintBacklog.selectRequirement(requirement);
        }
	}

	private void showProductBacklog(Requirement requirement) {
		select(productBacklog);
		productBacklog.select(requirement);
	}

    /**
     *
     * @param entity
     */
    public void showForum(ForumSupport entity) {
		select(forum);
		forum.select(entity);
	}

	private void showUserList(User user) {
		select(userList);
		userList.select(user);
	}

	private void showQualityBacklog(Quality quality) {
		select(qualityBacklog);
		qualityBacklog.select(quality);
	}

	private void showBlog(BlogEntry blogEntry) {
		select(blog);
		blog.select(blogEntry);
	}

	private void showCalendar(SimpleEvent event) {
		select(calendar);
		calendar.showEvent(event);
	}

	private void select(AWidget widget) {
		getSidebar().getNavigator().select(widget);
		getWorkarea().show(widget);
		Scope.get().getComponent(Ui.class).unlock();
	}

    /**
     *
     * @return
     */
    public WikiWidget getWiki() {
		return wiki;
	}

    /**
     *
     * @return
     */
    public SprintHistoryWidget getSprintHistory() {
		return sprintHistory;
	}

    /**
     *
     * @return
     */
    public CalendarWidget getCalendar() {
		return calendar;
	}

    /**
     *
     * @return
     */
    public JournalWidget getProjectEventList() {
		return projectEventList;
	}

    /**
     *
     * @return
     */
    public ImpedimentListWidget getImpedimentList() {
		return impedimentList;
	}

    /**
     *
     * @return
     */
    public FileRepositoryWidget getFileRepository() {
		return fileRepository;
	}

    /**
     *
     * @return
     */
    public IssueManagementWidget getIssueList() {
		return issueList;
	}

    /**
     *
     * @return
     */
    public NextSprintWidget getNextSprint() {
		return nextSprint;
	}

    /**
     *
     * @return
     */
    public ProductBacklogWidget getProductBacklog() {
		return productBacklog;
	}

    /**
     *
     * @return
     */
    public ProjectOverviewWidget getProjectOverview() {
		return projectOverview;
	}

    /**
     *
     * @return
     */
    public QualityBacklogWidget getQualityBacklog() {
		return qualityBacklog;
	}

    /**
     *
     * @return
     */
    public ForumWidget getForum() {
		return forum;
	}

    /**
     *
     * @return
     */
    public RiskListWidget getRiskList() {
		return riskList;
	}

    /**
     *
     * @return
     */
    public ProjectSidebarWidget getSidebar() {
		return sidebar;
	}

    /**
     *
     * @return
     */
    public SprintBacklogWidget getSprintBacklog() {
		return sprintBacklog;
	}

    /**
     *
     * @return
     */
    public WhiteboardWidget getWhiteboard() {
		return whiteboard;
	}

}
