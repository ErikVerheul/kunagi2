// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.workspace;

public class GUsersWorkspaceWidgetsReflector implements ilarkesto.core.scope.ComponentReflector<UsersWorkspaceWidgets> {

    @Override
    public void injectComponents(UsersWorkspaceWidgets component, ilarkesto.core.scope.Scope scope) {
        component.auth = (scrum.client.admin.Auth) scope.getComponent("auth");
        component.ui = (Ui) scope.getComponent("ui");
    }

    @Override
    public void callInitializationMethods(UsersWorkspaceWidgets component) {
        component.initialize();
    }

    @Override
    public void outjectComponents(UsersWorkspaceWidgets component, ilarkesto.core.scope.Scope scope) {
    }

}

