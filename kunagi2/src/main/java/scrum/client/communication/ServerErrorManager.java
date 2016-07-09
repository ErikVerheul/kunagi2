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
package scrum.client.communication;

import static ilarkesto.core.logging.ClientLog.INFO;
import ilarkesto.gwt.client.ErrorWrapper;

import java.util.ArrayList;
import java.util.List;

public class ServerErrorManager extends GServerErrorManager implements ServerDataReceivedHandler {

	private List<ErrorWrapper> errors = new ArrayList<ErrorWrapper>();

	@Override
	public void onServerDataReceived(ServerDataReceivedEvent event) {
		List<ErrorWrapper> serverErrors = event.getData().getErrors();
		if (serverErrors != null) {
			errors.addAll(serverErrors);
			INFO("Errors received:", serverErrors);
		}
	}

	public String popErrorsAsString() {
		if (errors.isEmpty()) return null;
		StringBuilder sb = new StringBuilder();
		for (ErrorWrapper error : errors) {
			sb.append(error).append("\n");
		}
		errors.clear();
		return sb.toString();
	}

}
