// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.search;

public class SearchServiceCall extends scrum.client.core.AServiceCall {

    private String text;

    public  SearchServiceCall(String text) {
        this.text = text;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().search(serviceCaller.getConversationNumber(), text, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "Search";
    }

}

