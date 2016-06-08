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
package scrum.client.admin;

import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.editor.TextEditorWidget;
import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Widget;

public class UserConfigWidget extends AScrumWidget {

	@Override
	protected Widget onInitialization() {

		final User user = getCurrentUser();

		TableBuilder tb = ScrumGwt.createFieldTable();
		tb.addFieldRow("Name", user.getNameModel());
		tb.addFieldRow("Public Name", user.getPublicNameModel());
		tb.addFieldRow("Email", user.getEmailModel());
		tb.addFieldRow("Full name", user.getFullNameModel());
		tb.addFieldRow("OpenID", user.getOpenIdModel());
		tb.addFieldRow("Password", new PasswordChangeWidget());
		tb.addFieldRow("Default Color", new TextEditorWidget(user.getColorModel()) {

			@Override
			protected void onViewerUpdate() {
				super.onViewerUpdate();
				getViewer().getElement().getStyle().setProperty("color", user.getColor());
			}
		});

		PagePanel page = new PagePanel();
		page.addHeader("Personal preferences for all projects");
		page.addSection(tb.createTable());
		return page;
	}

}
