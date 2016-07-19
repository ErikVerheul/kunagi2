package scrum.server.release;

import ilarkesto.base.Proc;
import ilarkesto.concurrent.ATask;
import ilarkesto.logging.Log;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.TransactionService;
import java.io.File;
import scrum.server.ScrumWebApplication;
import scrum.server.admin.User;
import scrum.server.collaboration.ChatMessageDao;
import scrum.server.project.Project;

/**
 *
 * @author erik
 */
public class ReleaseTask extends ATask {

	private static Log log = Log.get(ReleaseTask.class);

	// --- dependencies ---

	private ChatMessageDao chatMessageDao;
	private ScrumWebApplication webApplication;
	private TransactionService transactionService;

	private User user;
	private Release release;

    /**
     *
     * @param user
     * @param release
     */
    public ReleaseTask(User user, Release release) {
		this.user = user;
		this.release = release;
	}

    /**
     *
     * @param transactionService
     */
    public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

    /**
     *
     * @param webApplication
     */
    public void setWebApplication(ScrumWebApplication webApplication) {
		this.webApplication = webApplication;
	}

    /**
     *
     * @param chatMessageDao
     */
    public void setChatMessageDao(ChatMessageDao chatMessageDao) {
		this.chatMessageDao = chatMessageDao;
	}

	// --- ---

    /**
     *
     * @throws InterruptedException
     */
    
	@Override
	protected void perform() throws InterruptedException {
		Project project = release.getProject();
		File script = new File(project.getReleaseScriptPath());
		if (!script.exists()) {
                    throw new RuntimeException("Release script does not exist: " + script.getAbsolutePath());
        }
		File dir = script.getParentFile();
		release.setScriptRunning(true);
		webApplication.sendToConversationsByProject(project, release);
		String output;
		try {
			output = Proc.execute(dir, script.getPath(), release.getLabel());
		} catch (Proc.UnexpectedReturnCodeException ex) {
			log.info("Release script failed.", ex);
			release.setScriptOutput(ex.getOutput());
			release.setScriptRunning(false);
			transactionService.commit();
			String message = "Releasing failed: " + release.getReferenceAndLabel();
			AEntity chatMessage = chatMessageDao.postChatMessage(project, message);
			webApplication.sendToConversationsByProject(project, chatMessage);
			webApplication.sendToConversationsByProject(project, release);
			return;
		} finally {
			release.setScriptRunning(false);
		}
		log.info("Release script successful:\n", output);
		release.setScriptOutput(output);
		release.markReleased(project, user, webApplication);
		transactionService.commit();
	}

}
