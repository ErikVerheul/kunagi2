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
package scrum.server.impediments;

import com.itextpdf.text.BaseColor;
import ilarkesto.pdf.ACell;
import ilarkesto.pdf.APdfContainerElement;
import ilarkesto.pdf.ARow;
import ilarkesto.pdf.ATable;
import ilarkesto.pdf.FontStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import scrum.server.common.APdfCreator;
import scrum.server.project.Project;
import scrum.server.sprint.Task;

public class ImpedimentListPdfCreator extends APdfCreator {

	public ImpedimentListPdfCreator(Project project) {
		super(project);
	}

	@Override
	protected void build(APdfContainerElement pdf) {
		pdf.paragraph().text("Impediments", headerFonts[0]);

		List<Impediment> impediments = new ArrayList<Impediment>(project.getImpediments());
		Collections.sort(impediments);
		for (Impediment imp : impediments) {
			if (imp.isClosed()) continue;
			impediment(pdf, imp);
		}
	}

	private void impediment(APdfContainerElement pdf, Impediment imp) {
		pdf.nl();

		ATable table = pdf.table(2, 20, 3);

		ARow rowHeader = table.row().setDefaultBackgroundColor(BaseColor.LIGHT_GRAY);
		rowHeader.cell().setFontStyle(referenceFont).text(imp.getReference());
		rowHeader.cell().setFontStyle(new FontStyle(defaultFont).setBold(true)).text(imp.getLabel());
		rowHeader.cell().setFontStyle(smallerFont).text(imp.getDate());

		richtextRow(table, "Description", imp.getDescription());

		if (imp.isSolutionSet()) richtextRow(table, "Solution", imp.getSolution());

		Set<Task> tasks = imp.getTasks();
		if (!tasks.isEmpty()) {
			ACell cell = richtextRow(table, "Blocked tasks", null);
			for (Task task : tasks) {
				cell.paragraph().text(task.getReference(), referenceFont).text(" ").text(task.getLabel()).text(" (")
						.text(task.getRequirement().getReference(), referenceFont).text(")");
			}
		}

		table.createCellBorders(BaseColor.GRAY, 0.2f);
	}

	@Override
	protected String getFilename() {
		return "impediments";
	}

}
