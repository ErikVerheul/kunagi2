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

import ilarkesto.core.time.Tm;
import ilarkesto.core.logging.Log;
import ilarkesto.core.scope.In;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.di.Context;
import ilarkesto.gwt.server.AGwtConversation;
import ilarkesto.persistence.TransactionService;
import ilarkesto.webapp.AWebSession;

import javax.servlet.http.HttpServletRequest;

import scrum.server.admin.User;

public class WebSession extends AWebSession {

	private static final Log LOG = Log.get(WebSession.class);

	@In
	private TransactionService transactionService;

	private TimePeriod TIMEOUT = new TimePeriod(Tm.HOUR);
	private User user;

	public WebSession(Context parentContext, HttpServletRequest initialRequest) {
		super(parentContext, initialRequest);
	}

	@Override
	public GwtConversation getGwtConversation(int conversationNumber) {
		return (GwtConversation) super.getGwtConversation(conversationNumber);
	}

	@Override
	public AGwtConversation createGwtConversation() {
		GwtConversation gwtConversation = new GwtConversation(this, nextGwtConversationNumber());
		gwtConversation.setEmoticonDao(ScrumWebApplication.get().getEmoticonDao());
		gwtConversation.setTransactionService(transactionService);

		if (user != null) gwtConversation.sendUserScopeDataToClient(user);

		return gwtConversation;
	}

	public void setUser(User user) {
		LOG.info("User set:", user);
		this.user = user;
		getContext().setName(toString());
	}

	public User getUser() {
		return user;
	}

	@Override
	protected void onInvalidate() {
		setUser(null);
		super.onInvalidate();
	}

	@Override
	protected TimePeriod getTimeout() {
		return TIMEOUT;
	}

	@Override
	public String toString() {
		return user == null ? super.toString() : "session:" + user;
	}

}
