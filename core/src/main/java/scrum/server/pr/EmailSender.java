/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package scrum.server.pr;

import ilarkesto.core.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.core.scope.In;
import ilarkesto.email.Eml;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import scrum.server.admin.SystemConfig;
import scrum.server.project.Project;

public class EmailSender {

	private static Log log = Log.get(Log.class);

	@In
	private SystemConfig systemConfig;

	public void sendEmail(Project project, String to, String subject, String text) {
		sendEmail(project == null ? null : project.getSupportEmail(), to, subject, text);
	}

	public void sendEmail(String from, String to, String subject, String text) {
		Session session = createSmtpSession();
		if (session == null) {
			log.debug("Skipping sending email.", from, "->", to, "|", subject, "|", text);
			return;
		}

		if (Str.isBlank(from)) from = systemConfig.getSmtpFrom();
		if (Str.isBlank(from)) {
			log.error("Missing configuration: smtpFrom");
			return;
		}

		if (Str.isBlank(to)) to = systemConfig.getAdminEmail();
		if (Str.isBlank(to)) {
			log.error("Missing configuration: adminEmail");
			return;
		}

		if (Str.isBlank(subject)) subject = "Kunagi";

		MimeMessage message = Eml.createTextMessage(session, subject, text, from, to);
		Eml.sendSmtpMessage(session, message);
	}

	public Session createSmtpSession() {
		String smtpServer = systemConfig.getSmtpServer();
		Integer smtpPort = systemConfig.getSmtpPort();
		boolean smtpTls = systemConfig.isSmtpTls();
		if (smtpServer == null) {
			log.error("Missing configuration: smtpServer");
			return null;
		}
		return Eml.createSmtpSession(smtpServer, smtpPort, smtpTls, systemConfig.getSmtpUser(),
			systemConfig.getSmtpPassword());
	}
}
