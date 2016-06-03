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
package scrum.server.calendar;

import ilarkesto.core.time.Date;
import ilarkesto.pdf.APdfContainerElement;
import ilarkesto.pdf.FieldList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import scrum.server.common.APdfCreator;
import scrum.server.project.Project;

public class CalendarPdfCreator extends APdfCreator {

	private Project local_project;
	private Date from;
	private Date to;

	public CalendarPdfCreator(Project project, Date from, Date to) {
		super(project);
		this.local_project = project;
		this.from = from;
		this.to = to;
	}

	@Override
	protected void build(APdfContainerElement pdf) {
		pdf.paragraph().text("Calendar", headerFonts[0]);

		List<SimpleEvent> events = new ArrayList<SimpleEvent>(local_project.getCalendarEvents());
		Collections.sort(events);
		for (SimpleEvent evt : events) {
			if (evt.getDate().isBefore(from)) continue;
			if (evt.getDate().isAfter(to)) continue;
			pdf.nl();
			StringBuilder date = new StringBuilder();
			date.append(evt.getDate());
			if (evt.isTimeSet()) date.append(" ").append(evt.getTime());
			pdf.paragraph().text(date + " " + evt.getLabel(), headerFonts[2]);
			if (evt.isNoteSet()) wiki(pdf, evt.getNote());
			pdf.nl();
			FieldList fields = pdf.fieldList().setLabelFontStyle(fieldLabelFont);
			if (evt.isDurationSet()) fields.field("Duration").text(evt.getDuration() + " min.");
			if (evt.isLocationSet()) fields.field("Location").text(evt.getLocation());
			if (evt.isAgendaSet()) wiki(fields.field("Agenda"), evt.getAgenda());
		}
	}

	@Override
	protected String getFilename() {
		return "calendar";
	}

}
