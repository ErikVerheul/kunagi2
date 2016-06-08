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
package scrum.server.pr;

import ilarkesto.base.StrExtend;
import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.DaoService;
import ilarkesto.persistence.TransactionService;
import ilarkesto.webapp.RequestWrapper;
import ilarkesto.webapp.Servlet;
import java.io.IOException;
import javax.servlet.ServletConfig;
import scrum.server.WebSession;
import scrum.server.common.AKunagiServlet;
import scrum.server.common.KunagiUtl;

public class SubscribeServlet extends AKunagiServlet {

	private static final long serialVersionUID = 1;

	private static Log log = Log.get(SubscribeServlet.class);

	private transient SubscriptionService subscriptionService;
	private transient DaoService daoService;
	private transient TransactionService transactionService;

	@Override
	protected void onRequest(RequestWrapper<WebSession> req) throws IOException {
		req.setRequestEncoding(IO.UTF_8);

		String subjectId = req.get("subject");
		String email = StrExtend.cutRight(req.get("email"), 64);
		if (StrExtend.isBlank(email)) email = null;

		log.info("Subscription from the internet");
		log.info("    subject: " + subjectId);
		log.info("    email: " + email);
		log.info("  Request-Data:");
		log.info(Servlet.toString(req.getHttpRequest(), "        "));

		String message;
		try {
			AEntity subject = subscribe(email, subjectId);
			message = "Succesfully subscribed to <strong>" + KunagiUtl.createExternalRelativeHtmlAnchor(subject)
					+ "</strong>";
		} catch (Throwable ex) {
			log.error("Subscription failed.", "\n" + Servlet.toString(req.getHttpRequest(), "  "), ex);
			message = "<h2>Failure</h2><p>Subscription failed: <strong>" + StrExtend.getRootCauseMessage(ex)
					+ "</strong></p><p>We are sorry, please try again later.</p>";
		}

		String returnUrl = req.get("returnUrl");
		if (returnUrl == null) returnUrl = "http://kunagi.org/message.html?#{message}";
		returnUrl = returnUrl.replace("{message}", StrExtend.encodeUrlParameter(message));

		req.sendRedirect(returnUrl);
	}

	private AEntity subscribe(String email, String subjectId) {
		AEntity subject = daoService.getById(subjectId);
		subscriptionService.subscribe(email, subject);
		transactionService.commit();
		return subject;
	}

	@Override
	protected void onInit(ServletConfig config) {
		super.onInit(config);
		subscriptionService = webApplication.getSubscriptionService();
		daoService = webApplication.getDaoService();
		transactionService = webApplication.getTransactionService();
	}

}