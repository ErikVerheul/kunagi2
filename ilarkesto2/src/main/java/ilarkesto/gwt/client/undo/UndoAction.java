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
package ilarkesto.gwt.client.undo;

import ilarkesto.gwt.client.AAction;
import static ilarkesto.gwt.client.Gwt.getRootWidget;
import static ilarkesto.gwt.client.Gwt.update;

/**
 *
 *
 */
public class UndoAction extends AAction {

	private final UndoManager undoManager;
	private final AUndoOperation operation;

    /**
     *
     * @param undoManager
     * @param operation
     */
    public UndoAction(UndoManager undoManager, AUndoOperation operation) {
		this.undoManager = undoManager;
		this.operation = operation;
	}

    /**
     *
     * @return
     */
    @Override
	public String getLabel() {
		return operation.getLongLabel();
	}

    /**
     *
     */
    @Override
	protected void onExecute() {
		undoManager.undo(operation);
		update(getRootWidget());
	}

}
