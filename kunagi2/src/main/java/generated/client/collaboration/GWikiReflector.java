// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.collaboration;

import scrum.client.collaboration.Wiki;

public class GWikiReflector implements ilarkesto.core.scope.ComponentReflector<Wiki> {

    public void injectComponents(Wiki component, ilarkesto.core.scope.Scope scope) {
        component.dao = (scrum.client.Dao) scope.getComponent("dao");
        component.project = (scrum.client.project.Project) scope.getComponent("project");
        component.uploader = (scrum.client.files.Uploader) scope.getComponent("uploader");
    }

    public void callInitializationMethods(Wiki component) {
        component.initialize();
    }

    public void outjectComponents(Wiki component, ilarkesto.core.scope.Scope scope) {
    }

}

