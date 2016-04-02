// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client;

public interface ScrumService extends com.google.gwt.user.client.rpc.RemoteService {

    scrum.client.DataTransferObject changePassword(int conversationNumber, String newPassword, String oldPassword);

    scrum.client.DataTransferObject logout(int conversationNumber);

    scrum.client.DataTransferObject resetPassword(int conversationNumber, String userId);

    scrum.client.DataTransferObject sendTestEmail(int conversationNumber);

    scrum.client.DataTransferObject testLdap(int conversationNumber);

    scrum.client.DataTransferObject updateSystemMessage(int conversationNumber, scrum.client.admin.SystemMessage systemMessage);

    scrum.client.DataTransferObject requestComments(int conversationNumber, String parentId);

    scrum.client.DataTransferObject requestForum(int conversationNumber, boolean all);

    scrum.client.DataTransferObject ping(int conversationNumber);

    scrum.client.DataTransferObject startConversation(int conversationNumber);

    scrum.client.DataTransferObject touchLastActivity(int conversationNumber);

    scrum.client.DataTransferObject changeProperties(int conversationNumber, String entityId, java.util.Map properties);

    scrum.client.DataTransferObject createEntity(int conversationNumber, String type, java.util.Map properties);

    scrum.client.DataTransferObject deleteEntity(int conversationNumber, String entityId);

    scrum.client.DataTransferObject requestEntity(int conversationNumber, String entityId);

    scrum.client.DataTransferObject requestEntityByReference(int conversationNumber, String reference);

    scrum.client.DataTransferObject sleep(int conversationNumber, long millis);

    scrum.client.DataTransferObject activateRequirementEstimationVoting(int conversationNumber, String requirementId);

    scrum.client.DataTransferObject requestRequirementEstimationVotes(int conversationNumber, String requirementId);

    scrum.client.DataTransferObject requestImpediments(int conversationNumber);

    scrum.client.DataTransferObject convertIssueToStory(int conversationNumber, String issueId);

    scrum.client.DataTransferObject requestAcceptedIssues(int conversationNumber);

    scrum.client.DataTransferObject requestClosedIssues(int conversationNumber);

    scrum.client.DataTransferObject requestReleaseIssues(int conversationNumber, String releaseId);

    scrum.client.DataTransferObject sendIssueReplyEmail(int conversationNumber, String issueId, String from, String to, String subject, String text);

    scrum.client.DataTransferObject requestChanges(int conversationNumber, String parentId);

    scrum.client.DataTransferObject closeProject(int conversationNumber);

    scrum.client.DataTransferObject createExampleProject(int conversationNumber);

    scrum.client.DataTransferObject selectProject(int conversationNumber, String projectId);

    scrum.client.DataTransferObject updateProjectHomepage(int conversationNumber);

    scrum.client.DataTransferObject publishRelease(int conversationNumber, String releaseId);

    scrum.client.DataTransferObject requestRisks(int conversationNumber);

    scrum.client.DataTransferObject search(int conversationNumber, String text);

    scrum.client.DataTransferObject kickStoryFromSprint(int conversationNumber, String storyId);

    scrum.client.DataTransferObject pullStoryToSprint(int conversationNumber, String storyId);

    scrum.client.DataTransferObject requestHistory(int conversationNumber);

    scrum.client.DataTransferObject switchToNextSprint(int conversationNumber);

}

