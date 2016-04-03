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
package scrum.client.journal;

import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.editor.DateAndTimeEditorWidget;
import scrum.client.ScrumGwt;

import com.google.gwt.user.client.ui.Widget;

public class ProjectEventWidget extends AWidget {

	private ProjectEvent projectEvent;

	public ProjectEventWidget(ProjectEvent projectEvent) {
		this.projectEvent = projectEvent;
	}

	@Override
	protected Widget onInitialization() {
		TableBuilder tb = ScrumGwt.createFieldTable();

		tb.addFieldRow("Timestamp", new DateAndTimeEditorWidget(projectEvent.getDateAndTimeModel()));
		tb.addFieldRow("Label", projectEvent.getLabelModel());

		return tb.createTable();
	}

}
