// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.scrum.client.journal;

import scrum.client.journal.ChangeHistoryManager;

public class GChangeHistoryManagerReflector implements ilarkesto.core.scope.ComponentReflector<ChangeHistoryManager> {

    public void injectComponents(ChangeHistoryManager component, ilarkesto.core.scope.Scope scope) {
        component.app = (scrum.client.ScrumGwtApplication) scope.getComponent("app");
        component.dao = (scrum.client.Dao) scope.getComponent("dao");
    }

    public void callInitializationMethods(ChangeHistoryManager component) {
    }

    public void outjectComponents(ChangeHistoryManager component, ilarkesto.core.scope.Scope scope) {
    }

}

