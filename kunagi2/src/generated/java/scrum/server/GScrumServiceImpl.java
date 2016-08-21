// // ----------> GENERATED FILE - DON'T TOUCH! <----------
package scrum.server;

import ilarkesto.webapp.GwtConversationDoesNotExist;
import java.util.HashMap;

@SuppressWarnings("serial")
public abstract class GScrumServiceImpl extends ilarkesto.gwt.server.AGwtServiceImpl implements scrum.client.ScrumService {

    public abstract void onChangePassword(GwtConversation conversation, String newPassword, String oldPassword);

    public abstract void onLogout(GwtConversation conversation);

    public abstract void onResetPassword(GwtConversation conversation, String userId);

    public abstract void onSendTestEmail(GwtConversation conversation);

    public abstract void onTestLdap(GwtConversation conversation);

    public abstract void onUpdateSystemMessage(GwtConversation conversation, scrum.client.admin.SystemMessage systemMessage);

    public abstract void onRequestComments(GwtConversation conversation, String parentId);

    public abstract void onRequestForum(GwtConversation conversation, boolean all);

    public abstract void onPing(GwtConversation conversation);

    public abstract void onTouchLastActivity(GwtConversation conversation);

    public abstract void onChangeProperties(GwtConversation conversation, String entityId, HashMap<String, Object> properties);

    public abstract void onCreateEntity(GwtConversation conversation, String type, HashMap<String, Object> properties);

    public abstract void onDeleteEntity(GwtConversation conversation, String entityId);

    public abstract void onRequestEntity(GwtConversation conversation, String entityId);

    public abstract void onRequestEntityByReference(GwtConversation conversation, String reference);

    public abstract void onSleep(GwtConversation conversation, long millis);

    public abstract void onActivateRequirementEstimationVoting(GwtConversation conversation, String requirementId);

    public abstract void onRequestRequirementEstimationVotes(GwtConversation conversation, String requirementId);

    public abstract void onRequestImpediments(GwtConversation conversation);

    public abstract void onConvertIssueToStory(GwtConversation conversation, String issueId);

    public abstract void onRequestAcceptedIssues(GwtConversation conversation);

    public abstract void onRequestClosedIssues(GwtConversation conversation);

    public abstract void onRequestReleaseIssues(GwtConversation conversation, String releaseId);

    public abstract void onSendIssueReplyEmail(GwtConversation conversation, String issueId, String from, String to, String subject, String text);

    public abstract void onRequestChanges(GwtConversation conversation, String parentId);

    public abstract void onCloseProject(GwtConversation conversation);

    public abstract void onCreateExampleProject(GwtConversation conversation);

    public abstract void onSelectProject(GwtConversation conversation, String projectId);

    public abstract void onUpdateProjectHomepage(GwtConversation conversation);

    public abstract void onPublishRelease(GwtConversation conversation, String releaseId);

    public abstract void onRequestRisks(GwtConversation conversation);

    public abstract void onSearch(GwtConversation conversation, String text);

    public abstract void onKickStoryFromSprint(GwtConversation conversation, String storyId);

    public abstract void onPullStoryToSprint(GwtConversation conversation, String storyId);

    public abstract void onRequestHistory(GwtConversation conversation);

    public abstract void onSwitchToNextSprint(GwtConversation conversation);

    @Override
    public scrum.client.DataTransferObject changePassword(int conversationNumber, String newPassword, String oldPassword) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:ChangePassword");
            context.bindCurrentThread();
            try {
                onChangePassword(conversation, newPassword, oldPassword);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "ChangePassword", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject logout(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:Logout");
            context.bindCurrentThread();
            try {
                onLogout(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "Logout", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject resetPassword(int conversationNumber, String userId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:ResetPassword");
            context.bindCurrentThread();
            try {
                onResetPassword(conversation, userId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "ResetPassword", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject sendTestEmail(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:SendTestEmail");
            context.bindCurrentThread();
            try {
                onSendTestEmail(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "SendTestEmail", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject testLdap(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:TestLdap");
            context.bindCurrentThread();
            try {
                onTestLdap(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "TestLdap", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject updateSystemMessage(int conversationNumber, scrum.client.admin.SystemMessage systemMessage) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:UpdateSystemMessage");
            context.bindCurrentThread();
            try {
                onUpdateSystemMessage(conversation, systemMessage);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "UpdateSystemMessage", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestComments(int conversationNumber, String parentId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestComments");
            context.bindCurrentThread();
            try {
                onRequestComments(conversation, parentId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestComments", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestForum(int conversationNumber, boolean all) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestForum");
            context.bindCurrentThread();
            try {
                onRequestForum(conversation, all);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestForum", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject ping(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:Ping");
            context.bindCurrentThread();
            try {
                onPing(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "Ping", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject touchLastActivity(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:TouchLastActivity");
            context.bindCurrentThread();
            try {
                onTouchLastActivity(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "TouchLastActivity", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject changeProperties(int conversationNumber, String entityId, HashMap<String, Object> properties) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:ChangeProperties");
            context.bindCurrentThread();
            try {
                onChangeProperties(conversation, entityId, properties);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "ChangeProperties", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject createEntity(int conversationNumber, String type, HashMap<String, Object> properties) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:CreateEntity");
            context.bindCurrentThread();
            try {
                onCreateEntity(conversation, type, properties);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "CreateEntity", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject deleteEntity(int conversationNumber, String entityId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:DeleteEntity");
            context.bindCurrentThread();
            try {
                onDeleteEntity(conversation, entityId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "DeleteEntity", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestEntity(int conversationNumber, String entityId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestEntity");
            context.bindCurrentThread();
            try {
                onRequestEntity(conversation, entityId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestEntity", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestEntityByReference(int conversationNumber, String reference) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestEntityByReference");
            context.bindCurrentThread();
            try {
                onRequestEntityByReference(conversation, reference);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestEntityByReference", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject sleep(int conversationNumber, long millis) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:Sleep");
            context.bindCurrentThread();
            try {
                onSleep(conversation, millis);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "Sleep", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject activateRequirementEstimationVoting(int conversationNumber, String requirementId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:ActivateRequirementEstimationVoting");
            context.bindCurrentThread();
            try {
                onActivateRequirementEstimationVoting(conversation, requirementId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "ActivateRequirementEstimationVoting", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestRequirementEstimationVotes(int conversationNumber, String requirementId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestRequirementEstimationVotes");
            context.bindCurrentThread();
            try {
                onRequestRequirementEstimationVotes(conversation, requirementId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestRequirementEstimationVotes", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestImpediments(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestImpediments");
            context.bindCurrentThread();
            try {
                onRequestImpediments(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestImpediments", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject convertIssueToStory(int conversationNumber, String issueId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:ConvertIssueToStory");
            context.bindCurrentThread();
            try {
                onConvertIssueToStory(conversation, issueId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "ConvertIssueToStory", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestAcceptedIssues(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestAcceptedIssues");
            context.bindCurrentThread();
            try {
                onRequestAcceptedIssues(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestAcceptedIssues", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestClosedIssues(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestClosedIssues");
            context.bindCurrentThread();
            try {
                onRequestClosedIssues(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestClosedIssues", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestReleaseIssues(int conversationNumber, String releaseId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestReleaseIssues");
            context.bindCurrentThread();
            try {
                onRequestReleaseIssues(conversation, releaseId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestReleaseIssues", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject sendIssueReplyEmail(int conversationNumber, String issueId, String from, String to, String subject, String text) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:SendIssueReplyEmail");
            context.bindCurrentThread();
            try {
                onSendIssueReplyEmail(conversation, issueId, from, to, subject, text);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "SendIssueReplyEmail", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestChanges(int conversationNumber, String parentId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestChanges");
            context.bindCurrentThread();
            try {
                onRequestChanges(conversation, parentId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestChanges", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject closeProject(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:CloseProject");
            context.bindCurrentThread();
            try {
                onCloseProject(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "CloseProject", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject createExampleProject(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:CreateExampleProject");
            context.bindCurrentThread();
            try {
                onCreateExampleProject(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "CreateExampleProject", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject selectProject(int conversationNumber, String projectId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:SelectProject");
            context.bindCurrentThread();
            try {
                onSelectProject(conversation, projectId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "SelectProject", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject updateProjectHomepage(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:UpdateProjectHomepage");
            context.bindCurrentThread();
            try {
                onUpdateProjectHomepage(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "UpdateProjectHomepage", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject publishRelease(int conversationNumber, String releaseId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:PublishRelease");
            context.bindCurrentThread();
            try {
                onPublishRelease(conversation, releaseId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "PublishRelease", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestRisks(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestRisks");
            context.bindCurrentThread();
            try {
                onRequestRisks(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestRisks", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject search(int conversationNumber, String text) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:Search");
            context.bindCurrentThread();
            try {
                onSearch(conversation, text);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "Search", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject kickStoryFromSprint(int conversationNumber, String storyId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:KickStoryFromSprint");
            context.bindCurrentThread();
            try {
                onKickStoryFromSprint(conversation, storyId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "KickStoryFromSprint", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject pullStoryToSprint(int conversationNumber, String storyId) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:PullStoryToSprint");
            context.bindCurrentThread();
            try {
                onPullStoryToSprint(conversation, storyId);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "PullStoryToSprint", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject requestHistory(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:RequestHistory");
            context.bindCurrentThread();
            try {
                onRequestHistory(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "RequestHistory", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

    @Override
    public scrum.client.DataTransferObject switchToNextSprint(int conversationNumber) {
        WebSession session = (WebSession) getSession();
        synchronized (session) {
            GwtConversation conversation = null;
            try {
                conversation = session.getGwtConversation(conversationNumber);
            } catch (GwtConversationDoesNotExist ex) {
                LOG.info("Getting conversation failed:", conversationNumber);
                scrum.client.DataTransferObject dto = new scrum.client.DataTransferObject();
                dto.addError(new ilarkesto.gwt.client.ErrorWrapper(ex));
                return dto;
            }
            ilarkesto.di.Context context = ilarkesto.di.Context.get();
            context.setName("gwt-srv:SwitchToNextSprint");
            context.bindCurrentThread();
            try {
                onSwitchToNextSprint(conversation);
                onServiceMethodExecuted(context);
            } catch (Exception ex) {
                handleServiceMethodException(conversationNumber, "SwitchToNextSprint", ex);
            }
            return (scrum.client.DataTransferObject) conversation.popNextData();
        }
    }

}
