package scrum.client.core;

import ilarkesto.core.base.Utl;
import ilarkesto.core.scope.Scope;
import ilarkesto.core.service.ServiceCall;
import ilarkesto.gwt.client.ErrorWrapper;

import java.util.List;

import scrum.client.DataTransferObject;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AServiceCall implements ServiceCall {

	protected ServiceCaller serviceCaller = Scope.get().getComponent(ServiceCaller.class);

	@Override
	public void execute() {
		execute(null);
	}

	public boolean isDispensable() {
		return false;
	}

	protected class DefaultCallback implements AsyncCallback<DataTransferObject> {

		private Runnable returnHandler;
		private AServiceCall serviceCall;

		public DefaultCallback(AServiceCall serviceCall, Runnable returnHandler) {
			this.returnHandler = returnHandler;
			this.serviceCall = serviceCall;
		}

		@Override
		public void onSuccess(DataTransferObject data) {
			List<ErrorWrapper> errors = data.getErrors();
			if (errors != null && !errors.isEmpty()) {
				serviceCaller.onServiceCallFailure(AServiceCall.this, errors);
				return;
			}
			serviceCaller.onServiceCallReturn(serviceCall);
			serviceCaller.onServiceCallSuccess(data);
			if (returnHandler != null) returnHandler.run();
		}

		@Override
		public void onFailure(Throwable ex) {
			serviceCaller.onServiceCallReturn(AServiceCall.this);
			serviceCaller.onServiceCallFailure(AServiceCall.this, Utl.toList(new ErrorWrapper(ex)));
		}

	}

}
