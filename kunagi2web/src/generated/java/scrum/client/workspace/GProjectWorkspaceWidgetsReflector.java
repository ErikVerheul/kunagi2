// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.workspace;

public class GProjectWorkspaceWidgetsReflector implements ilarkesto.core.scope.ComponentReflector<ProjectWorkspaceWidgets> {

    public void injectComponents(ProjectWorkspaceWidgets component, ilarkesto.core.scope.Scope scope) {
        component.app = (scrum.client.ScrumGwtApplication) scope.getComponent("app");
        component.dao = (scrum.client.Dao) scope.getComponent("dao");
        component.navigator = (scrum.client.workspace.Navigator) scope.getComponent("navigator");
        component.project = (scrum.client.project.Project) scope.getComponent("project");
        component.user = (scrum.client.admin.User) scope.getComponent("user");
    }

    public void callInitializationMethods(ProjectWorkspaceWidgets component) {
        component.initialize();
    }

    public void outjectComponents(ProjectWorkspaceWidgets component, ilarkesto.core.scope.Scope scope) {
    }

}

