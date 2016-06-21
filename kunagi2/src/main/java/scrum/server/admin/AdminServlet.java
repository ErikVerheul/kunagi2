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
package scrum.server.admin;

import ilarkesto.base.Bytes;
import ilarkesto.base.Proc;
import ilarkesto.base.Sys;
import ilarkesto.base.UtlExtend;
import ilarkesto.core.logging.LogRecord;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.gwt.server.AGwtConversation;
import ilarkesto.logging.DefaultLogRecordHandler;
import ilarkesto.ui.web.HtmlRenderer;
import ilarkesto.webapp.AWebSession;
import ilarkesto.webapp.RequestWrapper;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import scrum.server.GwtConversation;
import scrum.server.WebSession;
import scrum.server.common.AKunagiServlet;

public class AdminServlet extends AKunagiServlet {

	@Override
	protected void onRequest(RequestWrapper<WebSession> req) throws IOException {
		tokenLogin(req);

		User user = req.getSession().getUser();
		if (user == null) {
			redirectToLogin(req);
			return;
		}

		if (!user.isAdmin()) {
			req.sendErrorForbidden();
			return;
		}

		HtmlRenderer html = createDefaultHtmlWithHeader(req, "Administration");

		html.startBODY().setStyle("font-size: 10px");

		adminLinks(html, req);

		version(html);
		sessions(html);
		conversations(html);
		errors(html);
		runtime(html);
		processes(html);
		threads(html);
		// TODO: entities
		// TODO: available disk space
		// TODO: threadlocals
		systemProperties(html);
		environment(html);
		actions(html);
		html.BR();
		html.BR();

		adminLinks(html, req);

		html.endBODY();

		html.endHTML();
		html.flush();
	}

	private void actions(HtmlRenderer html) {
		sectionHeader(html, "Functions");
		html.text("[ ");
		html.A("backup", "Create backup");
		html.text(" ]");

		html.text("[ ");
		html.A("shutdown", "Shutdown application now");
		html.text(" ]");

		html.text("[ ");
		html.A("shutdown?delay=5", "Shutdown application in 5 minutes");
		html.text(" ]");
	}

	private void errors(HtmlRenderer html) {
		sectionHeader(html, "Warnings and Errors");
		List<LogRecord> logs = DefaultLogRecordHandler.getErrors();
		logsTable(html, logs);
	}

	private void runtime(HtmlRenderer html) {
		sectionHeader(html, "Runtime");
		startTABLE(html);

		Runtime runtime = Runtime.getRuntime();
		long freeMemory = runtime.freeMemory();
		long totalMemory = runtime.totalMemory();
		long usedMemory = totalMemory - freeMemory;
		long maxMemory = runtime.maxMemory();
		long availableMemory = maxMemory - usedMemory;
		double usedMemoryPercent = usedMemory * 100d / maxMemory;
		double availableMemoryPercent = availableMemory * 100d / maxMemory;
		DateAndTime startupTime = new DateAndTime(Sys.getStartupTime());
		keyValueRow(html, "Startup time", startupTime);
		keyValueRow(html, "Run time", startupTime.getPeriodToNow().toShortestString());
		keyValueRow(html, "Used memory",
			new Bytes(usedMemory).toRoundedString() + " (" + new DecimalFormat("#0").format(usedMemoryPercent) + "%)");
		keyValueRow(html, "Available memory", new Bytes(availableMemory).toRoundedString() + " ("
				+ new DecimalFormat("#0").format(availableMemoryPercent) + "%)");
		keyValueRow(html, "Max memory", new Bytes(maxMemory).toRoundedString());

		keyValueRow(html, "Available processors", String.valueOf(runtime.availableProcessors()));
		keyValueRow(html, "Default locale", Locale.getDefault().toString());

		keyValueRow(html, "DataPath", applicationInfo.getDataPath());
		File dir = new File(applicationInfo.getDataPath());
		keyValueRow(html, "Free disk space", new Bytes(dir.getFreeSpace()).toRoundedString());

		endTABLE(html);
		html.flush();
	}

	private void systemProperties(HtmlRenderer html) {
		sectionHeader(html, "Java System Properties");
		startTABLE(html);
		Properties properties = System.getProperties();
		for (Object key : properties.keySet()) {
			String property = key.toString();
			keyValueRow(html, property, properties.getProperty(property));
		}
		endTABLE(html);
		html.flush();
	}

	private void threads(HtmlRenderer html) {
		sectionHeader(html, "Threads");
		startTABLE(html);
		headersRow(html, "Name", "Prio", "State", "Group", "Stack trace");
		for (Thread thread : UtlExtend.getAllThreads()) {
			StackTraceElement[] stackTrace = thread.getStackTrace();
			String groupName = thread.getThreadGroup().getName();
			valuesRow(html, thread.getName(), thread.getPriority(), thread.getState(), groupName,
				UtlExtend.formatStackTrace(stackTrace, " -> "));
		}
		endTABLE(html);
	}

	private void processes(HtmlRenderer html) {
		sectionHeader(html, "Spawned processes");
		startTABLE(html);
		headersRow(html, "Command", "Start time", "Run time");
		for (Proc proc : Proc.getRunningProcs()) {
			long startTime = proc.getStartTime();
			long runTime = proc.getRunTime();
			valuesRow(html, proc.toString(), new DateAndTime(startTime).getTime(), new TimePeriod(runTime));
		}
		endTABLE(html);
	}

	private void conversations(HtmlRenderer html) {
		sectionHeader(html, "Active Conversations");
		startTABLE(html);
		headersRow(html, "#", "User", "Project", "Last request");
		List<AGwtConversation> conversations = new ArrayList<AGwtConversation>(webApplication.getGwtConversations());
		Collections.sort(conversations);
		for (AGwtConversation aConversation : conversations) {
			GwtConversation conversation = (GwtConversation) aConversation;
			valuesRow(html, conversation.getNumber(), conversation.getSession().getUser(), conversation.getProject(),
				conversation.getLastTouched().getPeriodToNow().toShortestString() + " ago");
		}
		endTABLE(html);
	}

	private void sessions(HtmlRenderer html) {
		sectionHeader(html, "Active Sessions");
		startTABLE(html);
		headersRow(html, "User", "Last request", "Age", "Host", "Agent");
		List<AWebSession> sessions = new ArrayList<AWebSession>(webApplication.getWebSessions());
		Collections.sort(sessions);
		for (AWebSession aSession : sessions) {
			WebSession session = (WebSession) aSession;
			valuesRow(html, session.getUser(), session.getLastTouched().getPeriodToNow().toShortestString() + " ago",
				session.getSessionStartedTime().getPeriodToNow().toShortestString(), session.getInitialRemoteHost(),
				session.getUserAgent());
		}
		endTABLE(html);
	}

	private void version(HtmlRenderer html) {
		sectionHeader(html, "Version");
		startTABLE(html);
		keyValueRow(html, "Release", applicationInfo.getRelease());
		keyValueRow(html, "Build", applicationInfo.getBuild());
		endTABLE(html);
	}

	private void environment(HtmlRenderer html) {
		sectionHeader(html, "Environment");
		startTABLE(html);
		Map<String, String> env = System.getenv();
		for (Map.Entry<String, String> pair : env.entrySet()) {
			keyValueRow(html, pair.getKey(), pair.getValue());
		}
		endTABLE(html);
		html.flush();
	}

}
