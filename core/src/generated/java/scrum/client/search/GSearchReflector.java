// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.search;

public class GSearchReflector implements ilarkesto.core.scope.ComponentReflector<Search> {

    public void injectComponents(Search component, ilarkesto.core.scope.Scope scope) {
        component.app = (scrum.client.ScrumGwtApplication) scope.getComponent("app");
        component.navigator = (scrum.client.workspace.Navigator) scope.getComponent("navigator");
        component.project = (scrum.client.project.Project) scope.getComponent("project");
    }

    public void callInitializationMethods(Search component) {
    }

    public void outjectComponents(Search component, ilarkesto.core.scope.Scope scope) {
    }

}

