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
package scrum.client.workspace;

import ilarkesto.gwt.client.Gwt;

import com.google.gwt.user.client.ui.Widget;

public class Ui extends GUi implements VisibleDataChangedHandler {

	private WorkspaceWidget workspace;

	@Override
	public void initialize() {
		workspace = new WorkspaceWidget();
		Gwt.setRootWidget(workspace);
	}

	@Override
	public void onVisibleDataChanged(VisibleDataChangedEvent event) {
		workspace.update();
	}

	public void lock(String message) {
		workspace.lock(message);
	}

	public void unlock() {
		workspace.unlock();
	}

	public void show(Widget sidebar, Widget workarea) {
		// workspace.lock(workarea);
		workspace.getSidebar().show(sidebar);
		workspace.getWorkarea().show(workarea);
		workspace.unlock();
	}

	public WorkspaceWidget getWorkspace() {
		return workspace;
	}

}