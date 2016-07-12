// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.admin;

public class TestLdapServiceCall extends scrum.client.core.AServiceCall {

    public  TestLdapServiceCall() {
    }

    @Override
    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().testLdap(serviceCaller.getConversationNumber(), new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "TestLdap";
    }

}

