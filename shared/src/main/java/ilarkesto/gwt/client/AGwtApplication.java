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
package ilarkesto.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import static com.google.gwt.core.client.GWT.setUncaughtExceptionHandler;
import ilarkesto.core.logging.Log;
import static ilarkesto.core.logging.Log.setLogRecordHandler;
import java.util.List;

public abstract class AGwtApplication implements EntryPoint {

	private static final Log log = Log.get(AGwtApplication.class);

	private static AGwtApplication singleton;

	public abstract void handleServiceCallError(String serviceCall, List<ErrorWrapper> errors);

	protected abstract void handleUnexpectedError(Throwable ex);

	protected abstract AGwtDao getDao();

	public AGwtApplication() {
		if (singleton != null) {
                        throw new RuntimeException("GWT application already instantiated: " + singleton);
                }
		singleton = this;
		setLogRecordHandler(new GwtLogRecordHandler());
		setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable ex) {
				handleUnexpectedError(ex);
			}
		});
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

	public static AGwtApplication get() {
		return singleton;
	}

}
