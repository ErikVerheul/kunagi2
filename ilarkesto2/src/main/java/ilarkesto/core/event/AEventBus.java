/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.core.event;

import ilarkesto.core.logging.Log;
import java.util.Collection;

public abstract class AEventBus implements EventBus {

	public static final String DEFAULT_COMPONENT_NAME = "eventBus";

	private static final Log log = Log.get(AEventBus.class);

	protected abstract Collection getPotentialEventHandlers();

	@Override
	public void fireEvent(Event event) {
		if (!(event instanceof Quiet)) {
                        log.debug("Firing event:", event);
                }
		for (Object handler : getPotentialEventHandlers()) {
			event.tryToGetHandled(handler);
		}
	}

}
