
package scrum.client.workspace;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.Gwt;
import scrum.client.admin.ProjectSelectorWidget;
import scrum.client.admin.SystemConfigWidget;
import scrum.client.admin.SystemMessageManagerWidget;
import scrum.client.admin.SystemMessageWidget;
import scrum.client.admin.UserConfigWidget;
import scrum.client.admin.UserListWidget;

/**
 *
 * @author erik
 */
public class UsersWorkspaceWidgets extends GUsersWorkspaceWidgets {

	private FlowPanel sidebar;
	private ScrumNavigatorWidget navigator;
	private ProjectSelectorWidget projectSelector;
	private UserConfigWidget userConfig;
	private UserListWidget userList;
	private SystemMessageManagerWidget messageManager;
	private SystemConfigWidget systemConfig;

    /**
     *
     */
    @Override
	public void initialize() {
		projectSelector = new ProjectSelectorWidget();
		userConfig = new UserConfigWidget();
		messageManager = new SystemMessageManagerWidget();
		systemConfig = new SystemConfigWidget();

		navigator = new ScrumNavigatorWidget();
		navigator.addItem("Projects", projectSelector);
		navigator.addItem("Personal Preferences", userConfig);
		if (auth.getUser().isAdmin()) {
			userList = new UserListWidget();
			navigator.addItem("System Configuration", systemConfig);
			navigator.addItem("User Management", userList);
			navigator.addItem("System Message", messageManager);
		}

		sidebar = new FlowPanel();
		sidebar.getElement().getStyle().setMarginTop(10, Unit.PX);
		sidebar.getElement().getStyle().setMarginLeft(10, Unit.PX);
		sidebar.add(new SystemMessageWidget());
		sidebar.add(Gwt.createSpacer(1, 10));
		sidebar.add(navigator);
	}

    /**
     *
     * @param page
     */
    public void activate(String page) {
		AWidget widget = projectSelector;
		if (page != null) {
			if (page.equals(Page.getPageName(userConfig))) {
                            widget = userConfig;
            }
			if (page.equals(Page.getPageName(messageManager))) {
                            widget = messageManager;
            }
                        if (page.equals(Page.getPageName(userList))) {
                            widget = userList;
            }
                        if (page.equals(Page.getPageName(systemConfig))) {
                widget = systemConfig;
            }
		}
		ui.show(sidebar, widget);
	}

    /**
     *
     * @return
     */
    public UserListWidget getUserList() {
		return userList;
	}

    /**
     *
     * @return
     */
    public ProjectSelectorWidget getProjectSelector() {
		return projectSelector;
	}

}
