// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.scrum.client.admin;

public class TestLdapServiceCall extends generated.scrum.client.core.AServiceCall {

    public  TestLdapServiceCall() {
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().testLdap(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "TestLdap";
    }

}
