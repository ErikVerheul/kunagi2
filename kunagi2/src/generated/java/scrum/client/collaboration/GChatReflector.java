// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.collaboration;

public class GChatReflector implements ilarkesto.core.scope.ComponentReflector<Chat> {

    @Override
    public void injectComponents(Chat component, ilarkesto.core.scope.Scope scope) {
        component.auth = (scrum.client.admin.Auth) scope.getComponent("auth");
        component.dao = (scrum.client.Dao) scope.getComponent("dao");
        component.project = (scrum.client.project.Project) scope.getComponent("project");
        component.projectWorkspaceWidgets = (scrum.client.workspace.ProjectWorkspaceWidgets) scope.getComponent("projectWorkspaceWidgets");
    }

    @Override
    public void callInitializationMethods(Chat component) {
    }

    @Override
    public void outjectComponents(Chat component, ilarkesto.core.scope.Scope scope) {
    }

}

