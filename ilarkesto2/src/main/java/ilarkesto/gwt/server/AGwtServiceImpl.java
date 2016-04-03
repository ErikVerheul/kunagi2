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
package ilarkesto.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ilarkesto.core.logging.Log;
import ilarkesto.di.Context;
import ilarkesto.gwt.client.ErrorWrapper;
import ilarkesto.persistence.DaoService;
import ilarkesto.webapp.AWebApplication;
import static ilarkesto.webapp.AWebApplication.get;
import ilarkesto.webapp.AWebSession;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * Base class for GWT service implementations.
 */
public abstract class AGwtServiceImpl extends RemoteServiceServlet {

	private static final Log LOG = Log.get(AGwtServiceImpl.class);

	protected abstract Class<? extends AWebApplication> getWebApplicationClass();

	protected abstract AWebApplication getWebApplication();

	@Override
        @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_INFERRED", justification = "Return value not needed")
	protected void onBeforeRequestDeserialized(String serializedRequest) {
		getSession().getContext().createSubContext("gwt-srv");
		super.onBeforeRequestDeserialized(serializedRequest);
	}

	@Override
	protected void doUnexpectedFailure(Throwable t) {
		LOG.error("Service execution failed:", t);
		// getSession().getGwtConversation().getNextData().errors.add("Server error:" +
		// Str.getRootCauseMessage(t));
		super.doUnexpectedFailure(t);
	}

	protected final void handleServiceMethodException(int conversationNumber, String method, Throwable t) {
		LOG.info("Service method failed:", method, "->", t);

		// reset modified entities
		getWebApplication().getTransactionService().cancel();

		try {
			// send error to client
			AGwtConversation conversation = getSession().getGwtConversation(conversationNumber);
			conversation.getNextData().addError(new ErrorWrapper(t));
		} catch (Throwable ex) {
			LOG.info(ex);
		}
	}

	protected final void onServiceMethodExecuted(Context context) {
		// save modified entities
		getWebApplication().getTransactionService().commit();

		// destroy request context
		context.destroy();

		// destroy session, if invalidated
		AWebSession session = getSession();
		if (session.isSessionInvalidated()) {
                        getWebApplication().destroyWebSession(session, getThreadLocalRequest().getSession());
                }
	}

	@Override
	protected void checkPermutationStrongName() throws SecurityException {
		// TODO remove this to use super.checkPermutationStrongName();
		// Workaround for SecurityException: Blocked request without GWT permutation header (XSRF attack?)
		// @see http://code.google.com/p/gwteventservice/issues/detail?id=30
	}

	protected final AWebSession getSession() {
		AWebApplication webApplication = getWebApplication();
		if (webApplication.isShutdown()) {
                        throw new RuntimeException("Application shut down");
                }
		return webApplication.getWebSession(getThreadLocalRequest());
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		get().autowire(this);
	}

	@Override
	public void destroy() {
		getWebApplication().shutdown();
		super.destroy();
	}

	// --- helper ---

	protected DaoService getDaoService() {
		return getWebApplication().getDaoService();
	}
}
