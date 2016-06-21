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
package scrum.server.risks;

import ilarkesto.pdf.APdfContainerElement;
import ilarkesto.pdf.FieldList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import scrum.server.common.APdfCreator;
import scrum.server.project.Project;

public class RiskListPdfCreator extends APdfCreator {

	public RiskListPdfCreator(Project project) {
		super(project);
	}

	@Override
	protected void build(APdfContainerElement pdf) {
		pdf.paragraph().text("Risks", headerFonts[0]);

		List<Risk> risks = new ArrayList<Risk>(project.getRisks());
		Collections.sort(risks);
		for (Risk rsk : risks) {
			pdf.nl();
			pdf.paragraph().text(rsk.getReferenceAndLabel(), headerFonts[2]);
			wiki(pdf, rsk.getDescription());
			FieldList fields = pdf.fieldList().setLabelFontStyle(fieldLabelFont);
			fields.field("Priority").text(rsk.getPriorityLabel());
			fields.field("Probability").text(rsk.getProbabilityLabel());
			if (rsk.isProbabilityMitigationSet())
				wiki(fields.field("Probability mitigation"), rsk.getProbabilityMitigation());
			fields.field("Impact").text(rsk.getImpactLabel());
			if (rsk.isImpactMitigationSet()) wiki(fields.field("Impact mitigation"), rsk.getImpactMitigation());
		}
	}

	@Override
	protected String getFilename() {
		return "risks";
	}

}
