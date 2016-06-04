// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.scrum.client.issues;

public class RequestAcceptedIssuesServiceCall extends generated.scrum.client.core.AServiceCall {

    public  RequestAcceptedIssuesServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().requestAcceptedIssues(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "RequestAcceptedIssues";
    }

}

