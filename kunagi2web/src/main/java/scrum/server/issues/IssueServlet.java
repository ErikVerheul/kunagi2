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
package scrum.server.issues;

import ilarkesto.base.StrExtend;
import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;
import ilarkesto.persistence.TransactionService;
import ilarkesto.webapp.RequestWrapper;
import ilarkesto.webapp.Servlet;
import java.io.IOException;
import javax.servlet.ServletConfig;
import scrum.server.WebSession;
import scrum.server.common.AKunagiServlet;
import scrum.server.common.KunagiUtl;
import scrum.server.common.SpamChecker;
import scrum.server.journal.ProjectEvent;
import scrum.server.journal.ProjectEventDao;
import scrum.server.pr.SubscriptionService;
import scrum.server.project.Project;
import scrum.server.project.ProjectDao;

public class IssueServlet extends AKunagiServlet {

	private static final long serialVersionUID = 1;

	private static Log log = Log.get(IssueServlet.class);

	private transient IssueDao issueDao;
	private transient ProjectDao projectDao;
	private transient ProjectEventDao projectEventDao;
	private transient TransactionService transactionService;
	private transient SubscriptionService subscriptionService;

	@Override
	protected void onRequest(RequestWrapper<WebSession> req) throws IOException {
		req.setRequestEncoding(IO.UTF_8);

		String projectId = req.get("projectId");
		String subject = req.get("subject");
		String text = req.get("text");
		String name = StrExtend.cutRight(req.get("name"), 33);
		if (StrExtend.isBlank(name)) name = null;
		String email = StrExtend.cutRight(req.get("email"), 66);
		if (StrExtend.isBlank(email)) email = null;
		boolean publish = StrExtend.isTrue(req.get("publish"));

		log.info("Message from the internets");
		log.info("    projectId: " + projectId);
		log.info("    name: " + name);
		log.info("    email: " + email);
		log.info("    publish: " + publish);
		log.info("    subject: " + subject);
		log.info("    text: " + text);
		log.info("  Request-Data:");
		log.info(Servlet.toString(req.getHttpRequest(), "        "));

		String message;
		try {
			SpamChecker.check(req);
			message = submitIssue(projectId, subject, text, name, email, publish);
		} catch (Throwable ex) {
			log.error("Submitting issue failed.", "\n" + Servlet.toString(req.getHttpRequest(), "  "), ex);
			message = "<h2>Failure</h2><p>Submitting your feedback failed: <strong>" + StrExtend.getRootCauseMessage(ex)
					+ "</strong></p><p>We are sorry, please try again later.</p>";
		}

		String returnUrl = req.get("returnUrl");
		if (returnUrl == null) returnUrl = "http://kunagi.org/message.html#{message}";
		returnUrl = returnUrl.replace("{message}", StrExtend.encodeUrlParameter(message));

		req.sendRedirect(returnUrl);
	}

	private String submitIssue(String projectId, String label, String text, String name, String email, boolean publish) {
		if (projectId == null) throw new RuntimeException("projectId == null");
		if (StrExtend.isBlank(label) && StrExtend.isBlank(text))
			throw new RuntimeException("Subject and Description are empty. At least one required.");
		if (StrExtend.isBlank(label)) label = "Message from the Internets";
		Project project = projectDao.getById(projectId);
		Issue issue = issueDao.postIssue(project, label, "<nowiki>" + text + "</nowiki>", name, email, publish);
		if (publish) {
			project.updateHomepage(issue, true);
		}
		String issuer = issue.getIssuer();
		if (StrExtend.isBlank(issuer)) issuer = "anonymous";
		ProjectEvent event = projectEventDao.postEvent(project, issuer + " submitted " + issue.getReferenceAndLabel(),
			issue);
		if (StrExtend.isEmail(email)) subscriptionService.subscribe(email, issue);
		transactionService.commit();

		webApplication.sendToConversationsByProject(project, issue);
		webApplication.sendToConversationsByProject(project, event);

		String issueLink = publish ? KunagiUtl.createExternalRelativeHtmlAnchor(issue) : "<code>"
				+ issue.getReference() + "</code>";
		return "<h2>Feedback submitted</h2><p>Thank you for your feedback!</p><p>Your issue is now known as "
				+ issueLink + " and will be reviewed by our Product Owner.</p>";
	}

	@Override
	protected void onInit(ServletConfig config) {
		super.onInit(config);
		issueDao = webApplication.getIssueDao();
		projectDao = webApplication.getProjectDao();
		transactionService = webApplication.getTransactionService();
		projectEventDao = webApplication.getProjectEventDao();
		subscriptionService = webApplication.getSubscriptionService();
	}

}