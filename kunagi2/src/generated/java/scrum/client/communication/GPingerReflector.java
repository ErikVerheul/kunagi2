// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.communication;

public class GPingerReflector implements ilarkesto.core.scope.ComponentReflector<Pinger> {

    @Override
    public void injectComponents(Pinger component, ilarkesto.core.scope.Scope scope) {
        component.app = (scrum.client.ScrumGwtApplication) scope.getComponent("app");
        component.serviceCaller = (scrum.client.core.ServiceCaller) scope.getComponent("serviceCaller");
    }

    @Override
    public void callInitializationMethods(Pinger component) {
    }

    @Override
    public void outjectComponents(Pinger component, ilarkesto.core.scope.Scope scope) {
    }

}

