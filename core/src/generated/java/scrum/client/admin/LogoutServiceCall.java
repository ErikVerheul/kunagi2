// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.admin;

public class LogoutServiceCall extends scrum.client.core.AServiceCall {

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

