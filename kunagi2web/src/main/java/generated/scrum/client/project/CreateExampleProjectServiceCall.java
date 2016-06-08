// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.scrum.client.project;

public class CreateExampleProjectServiceCall extends generated.scrum.client.core.AServiceCall {

    public  CreateExampleProjectServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().createExampleProject(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "CreateExampleProject";
    }

}

