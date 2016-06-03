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
package scrum.client.risks;

import ilarkesto.gwt.client.AFieldValueWidget;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.editor.DropdownEditorWidget;
import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.journal.ChangeHistoryWidget;

import com.google.gwt.user.client.ui.Widget;

public class RiskWidget extends AScrumWidget {

	private Risk risk;

	public RiskWidget(Risk risk) {
		super();
		this.risk = risk;
	}

	@Override
	protected Widget onInitialization() {
		TableBuilder tb = ScrumGwt.createFieldTable();

		tb.addFieldRow("Label", risk.getLabelModel());
		tb.addFieldRow("Description", risk.getDescriptionModel());
		tb.addFieldRow("Impact", new DropdownEditorWidget<Integer>(risk.getImpactModel(),
				RiskComputer.IMPACT_LABEL_PROVIDER));
		tb.addFieldRow("Probability", new DropdownEditorWidget<Integer>(risk.getProbabilityModel(),
				RiskComputer.PROBABILITY_LABEL_PROVIDER));
		tb.addFieldRow("Priority", new AFieldValueWidget() {

			@Override
			protected void onUpdate() {
				setText(risk.getPriorityLabel());
			}
		});
		tb.addFieldRow("Impact Mitigation", risk.getImpactMitigationModel());
		tb.addFieldRow("Probability Mitigation", risk.getProbabilityMitigationModel());
		tb.addRow(new ChangeHistoryWidget(risk), 2);

		return TableBuilder.row(20, tb.createTable(), ScrumGwt.createEmoticonsAndComments(risk));
	}

}
