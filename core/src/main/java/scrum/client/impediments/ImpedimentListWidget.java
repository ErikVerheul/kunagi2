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
package scrum.client.impediments;

import generated.scrum.client.impediments.RequestImpedimentsServiceCall;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;
import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Widget;

public class ImpedimentListWidget extends AScrumWidget {

	public BlockListWidget<Impediment> list;

	@Override
	protected Widget onInitialization() {
		new RequestImpedimentsServiceCall().execute();

		list = new BlockListWidget<Impediment>(ImpedimentBlock.FACTORY);
		list.setAutoSorter(Impediment.REVERSE_DATE_COMPARATOR);

		PagePanel page = new PagePanel();
		page.addHeader("Impediment List", new ButtonWidget(new CreateImpedimentAction()));
		page.addSection(Gwt.createFlowPanel(list, Gwt.createSpacer(1, 10),
			ScrumGwt.createPdfLink("Downlad as PDF", "impedimentList", "")));
		page.addSection(new UserGuideWidget(getLocalizer().views().impediments(), getCurrentProject().getImpediments()
				.size() < 5, getCurrentUser().getHideUserGuideImpedimentsModel()));
		return page;
	}

	@Override
	protected void onUpdate() {
		list.setObjects(getCurrentProject().getImpediments());
		super.onUpdate();
	}

	public boolean select(Impediment impediment) {
		if (!list.contains(impediment)) update();
		return list.showObject(impediment);
	}
}
