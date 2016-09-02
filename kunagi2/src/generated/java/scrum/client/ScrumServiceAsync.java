// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client;

import ilarkesto.gwt.client.DataTransferObject;
import java.util.HashMap;

public interface ScrumServiceAsync {

    void changePassword(int conversationNumber, String newPassword, String oldPassword, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void logout(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void resetPassword(int conversationNumber, String userId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void sendTestEmail(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void testLdap(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void updateSystemMessage(int conversationNumber, ilarkesto.gwt.client.SystemMessage systemMessage, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestComments(int conversationNumber, String parentId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestForum(int conversationNumber, boolean all, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void ping(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void startConversation(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void touchLastActivity(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    //TODO: try to type properties at a later GWT version
    void changeProperties(int conversationNumber, String entityId, HashMap properties, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void createEntity(int conversationNumber, String type, HashMap<String, Object> properties, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void deleteEntity(int conversationNumber, String entityId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestEntity(int conversationNumber, String entityId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestEntityByReference(int conversationNumber, String reference, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void sleep(int conversationNumber, long millis, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void activateRequirementEstimationVoting(int conversationNumber, String requirementId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestRequirementEstimationVotes(int conversationNumber, String requirementId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestImpediments(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void convertIssueToStory(int conversationNumber, String issueId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestAcceptedIssues(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestClosedIssues(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestReleaseIssues(int conversationNumber, String releaseId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void sendIssueReplyEmail(int conversationNumber, String issueId, String from, String to, String subject, String text, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestChanges(int conversationNumber, String parentId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void closeProject(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void createExampleProject(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void selectProject(int conversationNumber, String projectId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void updateProjectHomepage(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void publishRelease(int conversationNumber, String releaseId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestRisks(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void search(int conversationNumber, String text, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void kickStoryFromSprint(int conversationNumber, String storyId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void pullStoryToSprint(int conversationNumber, String storyId, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void requestHistory(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

    void switchToNextSprint(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<DataTransferObject> callback);

}

