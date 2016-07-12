// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.sprint;

public class SwitchToNextSprintServiceCall extends scrum.client.core.AServiceCall {

    public  SwitchToNextSprintServiceCall() {
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().switchToNextSprint(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "SwitchToNextSprint";
    }

}

