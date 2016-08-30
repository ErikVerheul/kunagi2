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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Image;
import static ilarkesto.core.base.Str.getSimpleName;
import static ilarkesto.core.base.Str.toHtml;
import static ilarkesto.core.logging.ClientLog.DEBUG;
import static ilarkesto.gwt.client.Gwt.getRootWidget;
import static ilarkesto.gwt.client.Gwt.update;

/**
 *
 *
 */
public abstract class AAction implements Command, ClickHandler {

    /**
     *
     * @return
     */
    public abstract String getLabel();

    /**
     *
     */
    protected abstract void onExecute();

	@Override
	public final void execute() {
		DEBUG("Executing action: " + this);
		if (!isExecutable()) {
                        throw new RuntimeException("Action not executable: " + this);
                }
		if (!isPermitted()) {
                        throw new RuntimeException("Action not permitted: " + this);
                }
		onExecute();
		update(getRootWidget());
	}

    /**
     *
     * @return
     */
    public String getTargetHistoryToken() {
		return null;
	}

	@Override
	public void onClick(ClickEvent event) {
		event.stopPropagation();
		execute();
	}

    /**
     *
     * @return
     */
    public Image getIcon() {
		return null;
	}

    /**
     *
     * @return
     */
    public boolean isExecutable() {
		return true;
	}

    /**
     *
     * @return
     */
    public boolean isPermitted() {
		return true;
	}

    /**
     *
     * @return
     */
    public String getTooltip() {
		return null;
	}

    /**
     *
     * @return
     */
    public String getTooltipAsHtml() {
		return toHtml(getTooltip());
	}

    /**
     *
     * @return
     */
    public String getId() {
		return getSimpleName(getClass()).replace('$', '_');
	}

	@Override
	public String toString() {
		return getSimpleName(getClass());
	}

}
