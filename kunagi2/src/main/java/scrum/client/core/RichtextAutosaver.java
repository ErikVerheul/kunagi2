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

import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.AViewEditWidget;
import ilarkesto.gwt.client.editor.RichtextEditorWidget;
import scrum.client.admin.ProjectUserConfig;

import com.google.gwt.user.client.Timer;
import static ilarkesto.core.logging.ClientLog.ERROR;

public class RichtextAutosaver extends GRichtextAutosaver implements ApplicationStartedHandler {

	public static final int SAVE_INTERVAL_IN_SECONDS = 5;

	@Override
	public void onApplicationStarted(ApplicationStartedEvent event) {
		new Timer() {

			@Override
			public void run() {
				autosave();
			}
		}.scheduleRepeating(SAVE_INTERVAL_IN_SECONDS * 1000);
	}

	private void autosave() {
		AViewEditWidget currentEditor = RichtextEditorWidget.getCurrentEditor();
		if (currentEditor == null) return;

		try {
			if (!currentEditor.isInitialized()) return;
			ProjectUserConfig config = Scope.get().getComponent(ProjectUserConfig.class);
			if (config == null) return;
			if (!(currentEditor instanceof RichtextEditorWidget)) return;
			RichtextEditorWidget editor = (RichtextEditorWidget) currentEditor;
			if (!editor.isEditMode()) return;
			if (!editor.isAutosave()) return;
			editor.submitEditor(false);
		} catch (Exception ex) {
			ERROR(ex);
		}
	}

}
