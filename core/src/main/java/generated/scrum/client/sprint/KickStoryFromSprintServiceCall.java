// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package generated.scrum.client.sprint;

public class KickStoryFromSprintServiceCall extends generated.scrum.client.core.AServiceCall {

    private String storyId;

    public  KickStoryFromSprintServiceCall(String storyId) {
        this.storyId = storyId;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().kickStoryFromSprint(serviceCaller.getConversationNumber(), storyId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "KickStoryFromSprint";
    }

}

