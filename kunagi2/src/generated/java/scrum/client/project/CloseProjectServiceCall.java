// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.project;

public class CloseProjectServiceCall extends scrum.client.core.AServiceCall {

    public  CloseProjectServiceCall() {
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().closeProject(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "CloseProject";
    }

}

