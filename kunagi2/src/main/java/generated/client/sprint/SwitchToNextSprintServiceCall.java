// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.sprint;

public class SwitchToNextSprintServiceCall extends generated.client.core.AServiceCall {

    public  SwitchToNextSprintServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().switchToNextSprint(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "SwitchToNextSprint";
    }

}

