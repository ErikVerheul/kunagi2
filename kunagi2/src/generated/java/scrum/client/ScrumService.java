// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client;

import ilarkesto.gwt.client.DataTransferObject;
import java.util.HashMap;

public interface ScrumService extends com.google.gwt.user.client.rpc.RemoteService {

    DataTransferObject changePassword(int conversationNumber, String newPassword, String oldPassword);

    DataTransferObject logout(int conversationNumber);

    DataTransferObject resetPassword(int conversationNumber, String userId);

    DataTransferObject sendTestEmail(int conversationNumber);

    DataTransferObject testLdap(int conversationNumber);

    DataTransferObject updateSystemMessage(int conversationNumber, ilarkesto.gwt.client.SystemMessage systemMessage);

    DataTransferObject requestComments(int conversationNumber, String parentId);

    DataTransferObject requestForum(int conversationNumber, boolean all);

    DataTransferObject ping(int conversationNumber);

    DataTransferObject startConversation(int conversationNumber);

    DataTransferObject touchLastActivity(int conversationNumber);
    
    //TODO: try to type properties at a later GWT version
    DataTransferObject changeProperties(int conversationNumber, String entityId, HashMap properties);

    DataTransferObject createEntity(int conversationNumber, String type, HashMap<String, Object> properties);

    DataTransferObject deleteEntity(int conversationNumber, String entityId);

    DataTransferObject requestEntity(int conversationNumber, String entityId);

    DataTransferObject requestEntityByReference(int conversationNumber, String reference);

    DataTransferObject sleep(int conversationNumber, long millis);

    DataTransferObject activateRequirementEstimationVoting(int conversationNumber, String requirementId);

    DataTransferObject requestRequirementEstimationVotes(int conversationNumber, String requirementId);

    DataTransferObject requestImpediments(int conversationNumber);

    DataTransferObject convertIssueToStory(int conversationNumber, String issueId);

    DataTransferObject requestAcceptedIssues(int conversationNumber);

    DataTransferObject requestClosedIssues(int conversationNumber);

    DataTransferObject requestReleaseIssues(int conversationNumber, String releaseId);

    DataTransferObject sendIssueReplyEmail(int conversationNumber, String issueId, String from, String to, String subject, String text);

    DataTransferObject requestChanges(int conversationNumber, String parentId);

    DataTransferObject closeProject(int conversationNumber);

    DataTransferObject createExampleProject(int conversationNumber);

    DataTransferObject selectProject(int conversationNumber, String projectId);

    DataTransferObject updateProjectHomepage(int conversationNumber);

    DataTransferObject publishRelease(int conversationNumber, String releaseId);

    DataTransferObject requestRisks(int conversationNumber);

    DataTransferObject search(int conversationNumber, String text);

    DataTransferObject kickStoryFromSprint(int conversationNumber, String storyId);

    DataTransferObject pullStoryToSprint(int conversationNumber, String storyId);

    DataTransferObject requestHistory(int conversationNumber);

    DataTransferObject switchToNextSprint(int conversationNumber);
    
}

