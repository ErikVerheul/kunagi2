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
package scrum.client.core;

import ilarkesto.core.base.Str;
import static ilarkesto.core.logging.ClientLog.DEBUG;
import static ilarkesto.core.logging.ClientLog.ERROR;
import static ilarkesto.core.logging.ClientLog.INFO;
import static ilarkesto.core.logging.ClientLog.WARN;
import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.Tm;
import ilarkesto.gwt.client.ErrorWrapper;
import java.util.LinkedList;
import java.util.List;
import scrum.client.Dao;
import scrum.client.DataTransferObject;
import scrum.client.ScrumGwtApplication;
import scrum.client.ScrumService;
import scrum.client.ScrumServiceAsync;
import scrum.client.common.AScrumWidget;
import scrum.client.communication.ServerDataReceivedEvent;

/**
 *
 * @author erik
 */
public class ServiceCaller extends GServiceCaller {

	private static final long MAX_FAILURE_TIME = 30 * Tm.SECOND;

	private List<AServiceCall> activeServiceCalls = new LinkedList<AServiceCall>();
	private AScrumWidget statusWidget;
	private ScrumServiceAsync scrumService;

    /**
     *
     */
    protected int conversationNumber = -1;
	private long lastSuccessfullServiceCallTime;

    /**
     *
     * @return
     */
    public final ScrumServiceAsync getService() {
		if (scrumService == null) {
			scrumService = com.google.gwt.core.client.GWT.create(ScrumService.class);
			((com.google.gwt.user.client.rpc.ServiceDefTarget) scrumService)
					.setServiceEntryPoint(com.google.gwt.core.client.GWT.getModuleBaseURL() + "scrum");
		}
		return scrumService;
	}

    /**
     *
     * @param data
     */
    public void onServiceCallSuccess(DataTransferObject data) {
		lastSuccessfullServiceCallTime = Tm.getCurrentTimeMillis();

		if (data.conversationNumber != null) {
			conversationNumber = data.conversationNumber;
			INFO("conversatioNumber received:", conversationNumber);
		}
		Scope.get().getComponent(Dao.class).handleDataFromServer(data);

		ScrumGwtApplication app = ScrumGwtApplication.get();
		if (data.applicationInfo != null) {
			app.applicationInfo = data.applicationInfo;
			DEBUG("applicationInfo:", data.applicationInfo);
			// Scope.get().putComponent(data.applicationInfo);
		} else {
			assert app.applicationInfo != null;
		}

		new ServerDataReceivedEvent(data).fireInCurrentScope();
	}

    /**
     *
     * @param serviceCall
     * @param errors
     */
    public void onServiceCallFailure(AServiceCall serviceCall, List<ErrorWrapper> errors) {
		long timeFromLastSuccess = Tm.getCurrentTimeMillis() - lastSuccessfullServiceCallTime;
		if (serviceCall.isDispensable() && timeFromLastSuccess < MAX_FAILURE_TIME) {
			WARN("Dispensable service call failed:", serviceCall, errors);
			return;
		}
		ERROR("Service call failed:", serviceCall, errors);
		String serviceCallName = Str.getSimpleName(serviceCall.getClass());
		serviceCallName = Str.removeSuffix(serviceCallName, "ServiceCall");
		ScrumGwtApplication.get().handleServiceCallError(serviceCallName, errors);
	}

    /**
     *
     * @param call
     */
    public void onServiceCall(AServiceCall call) {
		activeServiceCalls.add(call);
		if (statusWidget != null) {
                    statusWidget.update();
        }
	}

    /**
     *
     * @param call
     */
    public void onServiceCallReturn(AServiceCall call) {
		activeServiceCalls.remove(call);
		if (statusWidget != null) {
                    statusWidget.update();
        }
	}

    /**
     *
     * @return
     */
    public List<AServiceCall> getActiveServiceCalls() {
		return activeServiceCalls;
	}

    /**
     *
     * @param type
     * @return
     */
    public boolean containsServiceCall(Class<? extends AServiceCall> type) {
		String name = Str.getSimpleName(type);
		for (AServiceCall call : activeServiceCalls) {
			String callName = Str.getSimpleName(call.getClass());
			if (callName.equals(name)) {
                            return true;
            }
		}
		return false;
	}

    /**
     *
     * @return
     */
    public int getConversationNumber() {
		return conversationNumber;
	}

    /**
     *
     */
    public void resetConversation() {
		conversationNumber = -1;
	}

    /**
     *
     * @param statusWidget
     */
    public void setStatusWidget(AScrumWidget statusWidget) {
		this.statusWidget = statusWidget;
	}

}
