// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.issues;

public class RequestClosedIssuesServiceCall extends generated.client.core.AServiceCall {

    public  RequestClosedIssuesServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().requestClosedIssues(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "RequestClosedIssues";
    }

}

