// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.estimation;

public class RequestRequirementEstimationVotesServiceCall extends scrum.client.core.AServiceCall {

    private String requirementId;

    public  RequestRequirementEstimationVotesServiceCall(String requirementId) {
        this.requirementId = requirementId;
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().requestRequirementEstimationVotes(serviceCaller.getConversationNumber(), requirementId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "RequestRequirementEstimationVotes";
    }

}

