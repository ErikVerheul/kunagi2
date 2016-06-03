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
package ilarkesto.gwt.client.undo;

import ilarkesto.core.time.DateAndTime;
import static ilarkesto.core.time.DateAndTime.now;

public abstract class AUndoOperation {

	private DateAndTime dateAndTime;

	protected abstract void onUndo();

	public abstract String getLabel();

	public AUndoOperation() {
		dateAndTime = now();
	}

	public final void undo() {
		onUndo();
	}

	public String getLongLabel() {
		return getLabel() + " (" + dateAndTime.getPeriodToNow().toShortestString() + " ago)";
	}

	@Override
	public String toString() {
		return "AUndoOperation(" + getLabel() + ")";
	}

}
