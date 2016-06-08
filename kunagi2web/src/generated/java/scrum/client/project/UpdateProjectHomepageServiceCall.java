// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.project;

public class UpdateProjectHomepageServiceCall extends scrum.client.core.AServiceCall {

    public  UpdateProjectHomepageServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().updateProjectHomepage(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "UpdateProjectHomepage";
    }

}

