// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.calendar;

public class GCalendarReflector implements ilarkesto.core.scope.ComponentReflector<Calendar> {

    @Override
    public void injectComponents(Calendar component, ilarkesto.core.scope.Scope scope) {
        component.project = (scrum.client.project.Project) scope.getComponent("project");
        component.projectWorkspaceWidgets = (scrum.client.workspace.ProjectWorkspaceWidgets) scope.getComponent("projectWorkspaceWidgets");
    }

    @Override
    public void callInitializationMethods(Calendar component) {
    }

    @Override
    public void outjectComponents(Calendar component, ilarkesto.core.scope.Scope scope) {
    }

}

