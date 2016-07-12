// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.project;

public class CreateExampleProjectServiceCall extends scrum.client.core.AServiceCall {

    public  CreateExampleProjectServiceCall() {
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().createExampleProject(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "CreateExampleProject";
    }

}

