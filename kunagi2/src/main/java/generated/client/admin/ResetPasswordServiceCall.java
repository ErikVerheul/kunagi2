// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.client.admin;

public class ResetPasswordServiceCall extends generated.client.core.AServiceCall {

    private String userId;

    public  ResetPasswordServiceCall(String userId) {
        this.userId = userId;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().resetPassword(serviceCaller.getConversationNumber(), userId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "ResetPassword";
    }

}

