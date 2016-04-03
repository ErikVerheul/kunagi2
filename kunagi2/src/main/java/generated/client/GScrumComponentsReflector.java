// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client;

public class GScrumComponentsReflector implements ilarkesto.core.scope.ComponentReflector {

    protected ilarkesto.core.scope.ComponentReflector authReflector = createAuthReflector();

    protected ilarkesto.core.scope.ComponentReflector systemMessageManagerReflector = createSystemMessageManagerReflector();

    protected ilarkesto.core.scope.ComponentReflector calendarReflector = createCalendarReflector();

    protected ilarkesto.core.scope.ComponentReflector chatReflector = createChatReflector();

    protected ilarkesto.core.scope.ComponentReflector usersStatusReflector = createUsersStatusReflector();

    protected ilarkesto.core.scope.ComponentReflector wikiReflector = createWikiReflector();

    protected ilarkesto.core.scope.ComponentReflector pingerReflector = createPingerReflector();

    protected ilarkesto.core.scope.ComponentReflector serverErrorManagerReflector = createServerErrorManagerReflector();

    protected ilarkesto.core.scope.ComponentReflector richtextAutosaverReflector = createRichtextAutosaverReflector();

    protected ilarkesto.core.scope.ComponentReflector serviceCallerReflector = createServiceCallerReflector();

    protected ilarkesto.core.scope.ComponentReflector uploaderReflector = createUploaderReflector();

    protected ilarkesto.core.scope.ComponentReflector localizerReflector = createLocalizerReflector();

    protected ilarkesto.core.scope.ComponentReflector issueManagerReflector = createIssueManagerReflector();

    protected ilarkesto.core.scope.ComponentReflector changeHistoryManagerReflector = createChangeHistoryManagerReflector();

    protected ilarkesto.core.scope.ComponentReflector searchReflector = createSearchReflector();

    protected ilarkesto.core.scope.ComponentReflector undoReflector = createUndoReflector();

    protected ilarkesto.core.scope.ComponentReflector dndManagerReflector = createDndManagerReflector();

    protected ilarkesto.core.scope.ComponentReflector navigatorReflector = createNavigatorReflector();

    protected ilarkesto.core.scope.ComponentReflector projectWorkspaceWidgetsReflector = createProjectWorkspaceWidgetsReflector();

    protected ilarkesto.core.scope.ComponentReflector uiReflector = createUiReflector();

    protected ilarkesto.core.scope.ComponentReflector usersWorkspaceWidgetsReflector = createUsersWorkspaceWidgetsReflector();

    public void injectComponents(Object component, ilarkesto.core.scope.Scope scope) {
        if (component instanceof scrum.client.admin.Auth) authReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.admin.SystemMessageManager) systemMessageManagerReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.calendar.Calendar) calendarReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.collaboration.Chat) chatReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.collaboration.UsersStatus) usersStatusReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.collaboration.Wiki) wikiReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.communication.Pinger) pingerReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.communication.ServerErrorManager) serverErrorManagerReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.core.RichtextAutosaver) richtextAutosaverReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.core.ServiceCaller) serviceCallerReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.files.Uploader) uploaderReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.i18n.Localizer) localizerReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.issues.IssueManager) issueManagerReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.journal.ChangeHistoryManager) changeHistoryManagerReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.search.Search) searchReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.undo.Undo) undoReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.workspace.DndManager) dndManagerReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.workspace.Navigator) navigatorReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.workspace.ProjectWorkspaceWidgets) projectWorkspaceWidgetsReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.workspace.Ui) uiReflector.injectComponents(component, scope);
        if (component instanceof scrum.client.workspace.UsersWorkspaceWidgets) usersWorkspaceWidgetsReflector.injectComponents(component, scope);
    }

    public void callInitializationMethods(Object component) {
        if (component instanceof scrum.client.admin.Auth) authReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.admin.SystemMessageManager) systemMessageManagerReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.calendar.Calendar) calendarReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.collaboration.Chat) chatReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.collaboration.UsersStatus) usersStatusReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.collaboration.Wiki) wikiReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.communication.Pinger) pingerReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.communication.ServerErrorManager) serverErrorManagerReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.core.RichtextAutosaver) richtextAutosaverReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.core.ServiceCaller) serviceCallerReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.files.Uploader) uploaderReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.i18n.Localizer) localizerReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.issues.IssueManager) issueManagerReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.journal.ChangeHistoryManager) changeHistoryManagerReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.search.Search) searchReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.undo.Undo) undoReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.workspace.DndManager) dndManagerReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.workspace.Navigator) navigatorReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.workspace.ProjectWorkspaceWidgets) projectWorkspaceWidgetsReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.workspace.Ui) uiReflector.callInitializationMethods(component);
        if (component instanceof scrum.client.workspace.UsersWorkspaceWidgets) usersWorkspaceWidgetsReflector.callInitializationMethods(component);
    }

    public void outjectComponents(Object component, ilarkesto.core.scope.Scope scope) {
        if (component instanceof scrum.client.admin.Auth) authReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.admin.SystemMessageManager) systemMessageManagerReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.calendar.Calendar) calendarReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.collaboration.Chat) chatReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.collaboration.UsersStatus) usersStatusReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.collaboration.Wiki) wikiReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.communication.Pinger) pingerReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.communication.ServerErrorManager) serverErrorManagerReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.core.RichtextAutosaver) richtextAutosaverReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.core.ServiceCaller) serviceCallerReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.files.Uploader) uploaderReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.i18n.Localizer) localizerReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.issues.IssueManager) issueManagerReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.journal.ChangeHistoryManager) changeHistoryManagerReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.search.Search) searchReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.undo.Undo) undoReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.workspace.DndManager) dndManagerReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.workspace.Navigator) navigatorReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.workspace.ProjectWorkspaceWidgets) projectWorkspaceWidgetsReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.workspace.Ui) uiReflector.outjectComponents(component, scope);
        if (component instanceof scrum.client.workspace.UsersWorkspaceWidgets) usersWorkspaceWidgetsReflector.outjectComponents(component, scope);
    }

    public ilarkesto.core.scope.ComponentReflector createAuthReflector() {
        return new generated.client.admin.GAuthReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createSystemMessageManagerReflector() {
        return new generated.client.admin.GSystemMessageManagerReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createCalendarReflector() {
        return new generated.client.calendar.GCalendarReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createChatReflector() {
        return new generated.client.collaboration.GChatReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createUsersStatusReflector() {
        return new generated.client.collaboration.GUsersStatusReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createWikiReflector() {
        return new generated.client.collaboration.GWikiReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createPingerReflector() {
        return new generated.client.communication.GPingerReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createServerErrorManagerReflector() {
        return new generated.client.communication.GServerErrorManagerReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createRichtextAutosaverReflector() {
        return new generated.client.core.GRichtextAutosaverReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createServiceCallerReflector() {
        return new generated.client.core.GServiceCallerReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createUploaderReflector() {
        return new generated.client.files.GUploaderReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createLocalizerReflector() {
        return new generated.client.i18n.GLocalizerReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createIssueManagerReflector() {
        return new generated.client.issues.GIssueManagerReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createChangeHistoryManagerReflector() {
        return new generated.client.journal.GChangeHistoryManagerReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createSearchReflector() {
        return new generated.client.search.GSearchReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createUndoReflector() {
        return new generated.client.undo.GUndoReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createDndManagerReflector() {
        return new generated.client.workspace.GDndManagerReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createNavigatorReflector() {
        return new generated.client.workspace.GNavigatorReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createProjectWorkspaceWidgetsReflector() {
        return new generated.client.workspace.GProjectWorkspaceWidgetsReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createUiReflector() {
        return new generated.client.workspace.GUiReflector();
    }

    public ilarkesto.core.scope.ComponentReflector createUsersWorkspaceWidgetsReflector() {
        return new generated.client.workspace.GUsersWorkspaceWidgetsReflector();
    }

}

