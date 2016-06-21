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
package scrum.client.calendar;

import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.editor.DateEditorWidget;
import ilarkesto.gwt.client.editor.TextEditorWidget;
import ilarkesto.gwt.client.editor.TimeEditorWidget;
import scrum.client.ScrumGwt;
import scrum.client.collaboration.CommentsWidget;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.Widget;

public class SimpleEventWidget extends AScrumWidget {

	private SimpleEvent event;

	public SimpleEventWidget(SimpleEvent event) {
		super();
		this.event = event;
	}

	@Override
	protected Widget onInitialization() {

		TableBuilder tb = ScrumGwt.createFieldTable();
		tb.addFieldRow("Label", event.getLabelModel());
		tb.addFieldRow("Date", new DateEditorWidget(event.getDateModel()));
		tb.addFieldRow("Time", new TimeEditorWidget(event.getTimeModel()));
		tb.addFieldRow("Location", new TextEditorWidget(event.getLocationModel()));
		tb.addFieldRow("Agenda", event.getAgendaModel());
		tb.addFieldRow("Note", event.getNoteModel());
		tb.addRow(new CommentsWidget(event), 2);

		return tb.createTable();
	}

}
