// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.admin;

public class LogoutServiceCall extends generated.client.core.AServiceCall {

    public  LogoutServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().logout(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "Logout";
    }

}

