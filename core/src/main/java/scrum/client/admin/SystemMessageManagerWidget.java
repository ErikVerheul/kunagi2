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
package scrum.client.admin;

import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.editor.DateAndTimeEditorWidget;
import ilarkesto.gwt.client.editor.TextEditorWidget;
import scrum.client.common.AScrumWidget;
import scrum.client.common.FieldsWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Widget;

public class SystemMessageManagerWidget extends AScrumWidget {

	@Override
	protected Widget onInitialization() {
		SystemMessageManager systemMessageManager = Scope.get().getComponent(SystemMessageManager.class);

		FieldsWidget fields = new FieldsWidget();
		fields.add("Text", new TextEditorWidget(systemMessageManager.systemMessageTextModel));
		fields.add("Date and Time", new DateAndTimeEditorWidget(systemMessageManager.systemMessageExpiresModel));

		PagePanel page = new PagePanel();
		page.addHeader("System Message Management", new ButtonWidget(new ActivateSystemMessageAction()),
			new ButtonWidget(new DeactivateSystemMessageAction()));
		page.addSection(fields);

		return page;
	}

}
