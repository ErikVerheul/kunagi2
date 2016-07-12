// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.project;

public class SelectProjectServiceCall extends scrum.client.core.AServiceCall {

    private String projectId;

    public  SelectProjectServiceCall(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().selectProject(serviceCaller.getConversationNumber(), projectId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "SelectProject";
    }

}

