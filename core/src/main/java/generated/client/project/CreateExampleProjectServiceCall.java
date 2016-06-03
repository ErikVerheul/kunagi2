// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.project;

public class CreateExampleProjectServiceCall extends generated.client.core.AServiceCall {

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

