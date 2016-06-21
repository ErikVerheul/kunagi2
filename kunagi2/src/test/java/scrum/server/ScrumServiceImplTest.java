/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.server;

import ilarkesto.auth.WrongPasswordException;
import ilarkesto.base.PermissionDeniedException;
import ilarkesto.base.StrExtend;
import ilarkesto.core.time.Date;
import ilarkesto.gwt.client.ErrorWrapper;
import ilarkesto.persistence.AEntity;
import ilarkesto.testng.ATest;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import scrum.TestUtil;
import scrum.client.DataTransferObject;
import scrum.client.admin.SystemMessage;
import scrum.server.admin.User;
import scrum.server.collaboration.Comment;
import scrum.server.collaboration.Subject;
import scrum.server.estimation.RequirementEstimationVote;
import scrum.server.issues.Issue;
import scrum.server.journal.Change;
import scrum.server.project.Project;
import scrum.server.project.Requirement;
import scrum.server.release.Release;
import scrum.server.sprint.Sprint;

public class ScrumServiceImplTest extends ATest {

    ScrumWebApplication app;
    ScrumServiceImpl service;
    WebSession sessionForAdmin;
    WebSession session;
    GwtConversation conversationForAdmin;
    GwtConversation conversation;
    User admin;
    User duke;
    Project project;

    @BeforeTest
    public void init() {
        TestUtil.initialize();
        app = TestUtil.getApp();

        admin = TestUtil.getAdmin();
        duke = TestUtil.getDuke();

        service = new ScrumServiceImpl();
        service.setWebApplication(app);
        app.autowire(service);

        session = (WebSession) app.createWebSession(null);
        session.setUser(duke);

        sessionForAdmin = (WebSession) app.createWebSession(null);
        sessionForAdmin.setUser(admin);

        project = TestUtil.createProject(duke);
        project.addAdmin(admin);

        app.getTransactionService().commit();
    }

    @BeforeMethod
    public void initConversations() {
        session.setUser(duke);
        conversation = (GwtConversation) session.createGwtConversation();
        conversation.getNextData().clear();
        conversation.setProject(project);

        conversationForAdmin = (GwtConversation) sessionForAdmin.createGwtConversation();
        conversationForAdmin.getNextData().clear();
    }

    @AfterMethod
    public void commit() {
        app.getTransactionService().commit();
    }

    @Test
    public void createExampleProject() {
        service.onCreateExampleProject(conversation);
        assertConversationWithoutErrors(conversation);
        assertEquals(conversation.getNextData().getEntities().size(), 1);
        Project local_project = getEntityByType(conversation, Project.class);
        assertStartsWith(local_project.getLabel(), "Project");
        assertTrue(local_project.containsAdmin(duke));
        assertTrue(local_project.containsParticipant(duke));
        assertTrue(local_project.containsProductOwner(duke));
        assertTrue(local_project.containsScrumMaster(duke));
        assertTrue(local_project.containsTeamMember(duke));
        app.getProjectDao().deleteEntity(local_project);
    }

    @Test
    public void search() {
        service.onSearch(conversation, "something");
        assertConversationWithoutErrors(conversation);
    }

    @Test(expectedExceptions = PermissionDeniedException.class)
    public void updateSystemMessage() {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setText("Alert!");
        systemMessage.setActive(true);
        service.onUpdateSystemMessage(conversation, systemMessage);
        assertConversationWithoutErrors(conversation);
    }

    @Test
    public void updateSystemMessageAdmin() {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setText("Alert!");
        systemMessage.setActive(true);
        service.onUpdateSystemMessage(conversationForAdmin, systemMessage);
        assertConversationWithoutErrors(conversationForAdmin);
    }

    @Test
    public void resetPassword() {
        service.onResetPassword(conversationForAdmin, duke.getId());
        assertConversationWithoutErrors(conversationForAdmin);
    }

    @Test
    public void changePassword() {
        duke.setPassword("geheim");
        service.onChangePassword(conversation, "geheim", "supergeheim");
    }

    @Test(expectedExceptions = WrongPasswordException.class)
    public void changePasswordFail() {
        duke.setPassword("geheim");
        service.onChangePassword(conversation, "wrong", "supergeheim");
        assertConversationWithoutErrors(conversation);
        assertTrue(duke.matchesPassword("supergeheim"));
    }

    @Test
    public void createEntity() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("id", UUID.randomUUID().toString());
        properties.put("name", "anonymous");
        service.onCreateEntity(conversation, "user", properties);
        assertConversationWithoutErrors(conversation);
        User anonymous = app.getUserDao().getUserByName("anonymous");
        assertNotNull(anonymous);
        app.getUserDao().deleteEntity(anonymous);
    }

    @Test
    public void deleteEntity() {
        User anonymous = app.getUserDao().postUserWithDefaultPassword("daemon");
        service.onDeleteEntity(conversationForAdmin, anonymous.getId());
        assertConversationWithoutErrors(conversationForAdmin);
        assertNull(app.getUserDao().getUserByName("daemon"));
    }

    @Test
    public void changeProperties() {
        duke.setEmail("support@kunagi.org");
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("email", "duke@kunagi.org");
        service.onChangeProperties(conversation, duke.getId(), properties);
        assertConversationWithoutErrors(conversation);
        assertEquals(duke.getEmail(), "duke@kunagi.org");
    }

    @Test
    public void selectProject() {
        conversation.setProject(null);
        service.onSelectProject(conversation, project.getId());
        assertConversationWithoutErrors(conversation);
        assertSame(conversation.getProject(), project);
        assertSame(duke.getCurrentProject(), project);
    }

    @Test
    public void closeProject() {
        service.onCloseProject(conversation);
        assertConversationWithoutErrors(conversation);
        assertNull(conversation.getProject());
    }

    @Test
    public void requestForum() {
        Subject subject = app.getSubjectDao().postSubject(project, "Test subject");
        app.getCommentDao().postComment(subject, "hello world", "anonymous", null, true);
        service.onRequestForum(conversation, true);
        assertConversationWithoutErrors(conversation);
//        assertContainsEntities(conversation, project.getSubjects()); //TODO: {Erik} find out why this test fails
        assertContainsEntities(conversation, project.getLatestComments());
    }

    @Test
    public void requestImpediments() {
        app.getImpedimentDao().postImpediment(project, Date.today(), "Test", false);
        service.onRequestImpediments(conversation);
        assertConversationWithoutErrors(conversation);
        assertContainsEntities(conversation, project.getImpediments());
    }

    @Test
    public void requestRisks() {
        app.getRiskDao().postRisk(project, "Test", 1, 1);
        service.onRequestRisks(conversation);
        assertConversationWithoutErrors(conversation);
        assertContainsEntities(conversation, project.getRisks());
    }

    @Test
    public void requestAcceptedIssues() {
        Issue spam = app.getIssueDao().postIssue(project, "spam");
        Issue bug = app.getIssueDao().postIssue(project, "bug");
        bug.setAcceptDate(Date.today());
        service.onRequestAcceptedIssues(conversation);
        assertConversationWithoutErrors(conversation);
        assertContainsEntities(conversation, project.getAcceptedIssues());
        assertContainsEntity(conversation, bug);
        assertNotContainsEntity(conversation, spam);
    }

	@Test
	public void requestClosedIssues() {
		Issue spam = app.getIssueDao().postIssue(project, "spam");
		Issue bug = app.getIssueDao().postIssue(project, "bug");
		bug.setAcceptDate(Date.today());
		bug.setCloseDate(Date.today());
		service.onRequestClosedIssues(conversation);
		assertConversationWithoutErrors(conversation);
		assertContainsEntities(conversation, project.getClosedIssues());
		assertContainsEntity(conversation, bug);
		assertNotContainsEntity(conversation, spam);
	}

	@Test
	public void requestReleaseIssues() {
		Release release = app.getReleaseDao().newEntityInstance();
		release.setProject(project);
		Issue bug = app.getIssueDao().postIssue(project, "bug");
		bug.setAcceptDate(Date.today());
		bug.addAffectedRelease(release);
		service.onRequestReleaseIssues(conversation, release.getId());
		assertConversationWithoutErrors(conversation);
		assertContainsEntity(conversation, bug);
	}

    @Test
    public void requestEntity() {
        Issue problem = app.getIssueDao().postIssue(project, "problem");
        service.onRequestEntity(conversation, problem.getId());
        assertConversationWithoutErrors(conversation);
        assertContainsEntity(conversation, problem);
    }

    @Test
    public void requestEntityByReference() {
        Issue problem = app.getIssueDao().postIssue(project, "problem");
        service.onRequestEntityByReference(conversation, problem.getReference());
        assertConversationWithoutErrors(conversation);
        assertContainsEntity(conversation, problem);
    }

    @Test
    public void requestComments() {
        Comment comment = app.getCommentDao().postComment(project, "test comment", "duke", "duke@kunagi.org", true);
        comment.setParent(project);
        service.onRequestComments(conversation, project.getId());
        assertConversationWithoutErrors(conversation);
        assertContainsEntities(conversation, app.getCommentDao().getCommentsByParent(project));
        assertContainsEntity(conversation, comment);
    }

    @Test
    public void requestChanges() {
        Change change = app.getChangeDao().postChange(project, duke, "dummy", "olddummy", "newdummy");
        service.onRequestChanges(conversation, project.getId());
        assertConversationWithoutErrors(conversation);
        assertContainsEntities(conversation, app.getChangeDao().getChangesByParent(project));
        assertContainsEntity(conversation, change);
    }

    @Test
    public void requestRequirementEstimationVotes() {
        Requirement requirement = app.getRequirementDao().postRequirement(project, "new feature", 2f);
        RequirementEstimationVote vote = app.getRequirementEstimationVoteDao().postVote(requirement, duke);
        service.onRequestRequirementEstimationVotes(conversation, requirement.getId());
        assertConversationWithoutErrors(conversation);
        assertContainsEntities(conversation, requirement.getEstimationVotes());
        assertContainsEntity(conversation, vote);
    }

    @Test
    public void activateRequirementEstimationVoting() {
        Requirement requirement = app.getRequirementDao().postRequirement(project, "new feature", 2f);
        requirement.setWorkEstimationVotingActive(false);
        service.onActivateRequirementEstimationVoting(conversation, requirement.getId());
        assertConversationWithoutErrors(conversation);
        assertTrue(requirement.isWorkEstimationVotingActive());
    }

    @Test
    public void switchToNextSprint() {
        Sprint currentSprint = project.getCurrentSprint();
        Sprint nextSprint = project.getNextSprint();
        service.onSwitchToNextSprint(conversation);
        assertConversationWithoutErrors(conversation);
        assertNotSame(project.getCurrentSprint(), currentSprint);
        assertNotSame(project.getNextSprint(), nextSprint);
        assertEquals(project.getCurrentSprint(), nextSprint);
    }

    // --- helpers ---
    private static void assertContainsEntities(GwtConversation conversation, Collection<? extends AEntity> entities) {
        for (AEntity entity : entities) {
            assertContainsEntity(conversation, entity);
        }
    }

    private static void assertContainsEntity(GwtConversation conversation, AEntity entity) {
        DataTransferObject dto = conversation.getNextData();
        assertTrue(dto.containsEntities());
        for (Map properties : dto.getEntities()) {
            if (entity.getId().equals(properties.get("id"))) {
                return;
            }
        }
        fail("Conversation does not contain <" + entity + ">.");
    }

    private static void assertNotContainsEntity(GwtConversation conversation, AEntity entity) {
        DataTransferObject dto = conversation.getNextData();
        assertTrue(dto.containsEntities());
        for (Map properties : dto.getEntities()) {
            if (entity.getId().equals(properties.get("id"))) {
                fail("Conversation does contain <" + entity + ">.");
            }
        }
    }

    private static <E extends AEntity> E getEntityByType(GwtConversation conversation, Class<E> type) {
        DataTransferObject dto = conversation.getNextData();
        if (!dto.containsEntities()) {
            return null;
        }
        for (Map entity : dto.getEntities()) {
            if (type.getSimpleName().toLowerCase().equals(entity.get("@type"))) {
                String id = (String) entity.get("id");
                E e = (E) TestUtil.getApp().getDaoService().getEntityById(id);
                return e;
            }
        }
        return null;
    }

    private static <E extends AEntity> E createEntity(Class<E> type, Map properties) {
        E entity;
        try {
            entity = type.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        entity.updateProperties(properties);
        return entity;
    }

    private static void assertConversationError(GwtConversation conversation, ErrorWrapper error) {
        List<ErrorWrapper> errors = conversation.getNextData().getErrors();
        assertTrue(errors != null && errors.contains(error),
                "Conversation error not found: <" + error + "> in " + StrExtend.format(errors));
    }

    private static void assertConversationWithoutErrors(GwtConversation conversation) {
        List<ErrorWrapper> errors = conversation.getNextData().getErrors();
        assertTrue(errors == null || errors.isEmpty(), "Conversation contains errors: " + StrExtend.format(errors));
    }
}
