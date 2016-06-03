// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.communication;

public class TouchLastActivityServiceCall extends generated.client.core.AServiceCall {

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

