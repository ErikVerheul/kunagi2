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

import static ilarkesto.core.base.Str.getSimpleName;
import static ilarkesto.core.event.AEventBus.DEFAULT_COMPONENT_NAME;
import ilarkesto.core.logging.Log;
import static ilarkesto.core.scope.Scope.get;

public abstract class AEvent implements Event {

	protected Log log = Log.get(this.toString());

	public final void fireInCurrentScope() {
		AEventBus eventBus = (AEventBus) get().getComponent(DEFAULT_COMPONENT_NAME);
		if (eventBus == null) {
                        throw new IllegalStateException("Missing component in scope: " + DEFAULT_COMPONENT_NAME);
                }
		eventBus.fireEvent(this);
	}

	@Override
	public String toString() {
		return getSimpleName(getClass());
	}

}
