// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.scrum.client.workspace;

import scrum.client.workspace.Ui;
import scrum.client.workspace.UsersWorkspaceWidgets;

public class GUsersWorkspaceWidgetsReflector implements ilarkesto.core.scope.ComponentReflector<UsersWorkspaceWidgets> {

    public void injectComponents(UsersWorkspaceWidgets component, ilarkesto.core.scope.Scope scope) {
        component.auth = (scrum.client.admin.Auth) scope.getComponent("auth");
        component.ui = (Ui) scope.getComponent("ui");
    }

    public void callInitializationMethods(UsersWorkspaceWidgets component) {
        component.initialize();
    }

    public void outjectComponents(UsersWorkspaceWidgets component, ilarkesto.core.scope.Scope scope) {
    }

}

