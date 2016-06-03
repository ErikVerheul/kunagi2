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
package ilarkesto.webapp;

import static ilarkesto.core.base.Utl.compare;
import ilarkesto.core.logging.Log;
import ilarkesto.core.time.DateAndTime;
import static ilarkesto.core.time.DateAndTime.now;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.di.Context;
import ilarkesto.gwt.server.AGwtConversation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public abstract class AWebSession implements Comparable<AWebSession> {

	private static final Log LOG = Log.get(AWebSession.class);
	private static final TimePeriod DEFAULT_TIMEOUT = TimePeriod.minutes(30);

	private Context context;
	private final String userAgent;
	private boolean shitBrowser;
	private final String initialRemoteHost;
	private boolean sessionInvalidated;
	private final DateAndTime sessionStartedTime;
	private DateAndTime lastTouched;
	private final Set<AGwtConversation> gwtConversations = new HashSet<>();
	private int lastGwtConversationNumber = 0;

	public AWebSession(Context parentContext, HttpServletRequest initialRequest) {
		this.initialRemoteHost = initialRequest == null ? "localhost" : initialRequest.getRemoteHost();

		sessionStartedTime = now();

		context = parentContext.createSubContext(toString());
		context.addBeanProvider(this);

		userAgent = initialRequest == null ? "unknown" : Servlet.getUserAgent(initialRequest);
		shitBrowser = userAgent != null && userAgent.contains("MSIE 6");

		touch();
	}

	public synchronized AGwtConversation getGwtConversation(int conversationNumber) {
		if (conversationNumber == -1) {
			AGwtConversation conversation = createGwtConversation();
			gwtConversations.add(conversation);
			return conversation;
		}
		for (AGwtConversation conversation : gwtConversations) {
			if (conversation.getNumber() == conversationNumber) {
				conversation.touch();
				return conversation;
			}
		}
		throw new GwtConversationDoesNotExist(conversationNumber);
	}

	public AGwtConversation createGwtConversation() {
		return null;
	}

	public synchronized void destroyGwtConversation(AGwtConversation conversation) {
		conversation.invalidate();
		gwtConversations.remove(conversation);
	}

	public Set<AGwtConversation> getGwtConversations() {
		return gwtConversations;
	}

	// --- ---

	public int nextGwtConversationNumber() {
		lastGwtConversationNumber++;
		return lastGwtConversationNumber;
	}

	public final String getInitialRemoteHost() {
		return initialRemoteHost;
	}

	final void touch() {
		lastTouched = now();
	}

	protected TimePeriod getTimeout() {
		return DEFAULT_TIMEOUT;
	}

	final boolean isTimeouted() {
		TimePeriod idle = lastTouched.getPeriodToNow();
		TimePeriod maxIdle = getTimeout();
		return idle.isGreaterThen(maxIdle);
	}

	public final DateAndTime getLastTouched() {
		return lastTouched;
	}

	public final String getUserAgent() {
		return userAgent;
	}

	public final boolean isShitBrowser() {
		return shitBrowser;
	}

	public final void setShitBrowser(boolean value) {
		this.shitBrowser = value;
	}

	public DateAndTime getSessionStartedTime() {
		return sessionStartedTime;
	}

	public final Context getContext() {
		return context;
	}

	public final boolean isSessionInvalidated() {
		return sessionInvalidated;
	}

	protected void onInvalidate() {
		for (AGwtConversation conversation : new ArrayList<>(gwtConversations)) {
			destroyGwtConversation(conversation);
		}
	}

	public final void invalidate() {
		LOG.info("Invalidating session:", this);
		sessionInvalidated = true;
		onInvalidate();
	}

	final void destroy() {
		if (!sessionInvalidated) {
			invalidate();
		}
		if (context != null) {
			context.destroy();
			context = null;
		}
	}

	@Override
	public String toString() {
		return "session:" + initialRemoteHost;
	}

	@Override
	public int compareTo(AWebSession o) {
		return compare(o.getLastTouched(), getLastTouched());
	}

}