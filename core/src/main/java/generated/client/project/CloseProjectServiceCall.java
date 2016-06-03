// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.project;

public class CloseProjectServiceCall extends generated.client.core.AServiceCall {

    public  CloseProjectServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().closeProject(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "CloseProject";
    }

}

