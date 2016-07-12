// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.issues;

public class ConvertIssueToStoryServiceCall extends scrum.client.core.AServiceCall {

    private String issueId;

    public  ConvertIssueToStoryServiceCall(String issueId) {
        this.issueId = issueId;
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().convertIssueToStory(serviceCaller.getConversationNumber(), issueId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "ConvertIssueToStory";
    }

}

