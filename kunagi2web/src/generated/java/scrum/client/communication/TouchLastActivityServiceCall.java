// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.communication;

public class TouchLastActivityServiceCall extends scrum.client.core.AServiceCall {

    public  TouchLastActivityServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().touchLastActivity(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public boolean isDispensable() {
        return true;
    }

    @Override
    public String toString() {
        return "TouchLastActivity";
    }

}

