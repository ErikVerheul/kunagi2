// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.journal;

public class GChangeHistoryManagerReflector implements ilarkesto.core.scope.ComponentReflector<ChangeHistoryManager> {

    @Override
    public void injectComponents(ChangeHistoryManager component, ilarkesto.core.scope.Scope scope) {
        component.app = (scrum.client.ScrumGwtApplication) scope.getComponent("app");
        component.dao = (scrum.client.Dao) scope.getComponent("dao");
    }

    @Override
    public void callInitializationMethods(ChangeHistoryManager component) {
    }

    @Override
    public void outjectComponents(ChangeHistoryManager component, ilarkesto.core.scope.Scope scope) {
    }

}

