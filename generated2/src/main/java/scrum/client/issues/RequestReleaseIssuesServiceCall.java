// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.issues;

public class RequestReleaseIssuesServiceCall extends scrum.client.core.AServiceCall {

    private String releaseId;

    public  RequestReleaseIssuesServiceCall(String releaseId) {
        this.releaseId = releaseId;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().requestReleaseIssues(serviceCaller.getConversationNumber(), releaseId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "RequestReleaseIssues";
    }

}

