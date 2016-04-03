// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.calendar;

import scrum.client.calendar.Calendar;

public class GCalendarReflector implements ilarkesto.core.scope.ComponentReflector<Calendar> {

    public void injectComponents(Calendar component, ilarkesto.core.scope.Scope scope) {
        component.project = (scrum.client.project.Project) scope.getComponent("project");
        component.projectWorkspaceWidgets = (scrum.client.workspace.ProjectWorkspaceWidgets) scope.getComponent("projectWorkspaceWidgets");
    }

    public void callInitializationMethods(Calendar component) {
    }

    public void outjectComponents(Calendar component, ilarkesto.core.scope.Scope scope) {
    }

}

