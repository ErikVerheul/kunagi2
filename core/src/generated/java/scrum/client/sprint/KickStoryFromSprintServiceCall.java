// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.sprint;

public class KickStoryFromSprintServiceCall extends scrum.client.core.AServiceCall {

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

