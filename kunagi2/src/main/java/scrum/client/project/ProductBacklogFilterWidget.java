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
package scrum.client.project;

import ilarkesto.gwt.client.AAction;
import ilarkesto.gwt.client.ADropdownViewEditWidget;
import ilarkesto.gwt.client.AMultiSelectionViewEditWidget;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.TableBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import scrum.client.ScrumGwt;
import scrum.client.admin.ProjectUserConfig;
import scrum.client.common.AScrumWidget;
import scrum.client.common.ThemesWidget;
import scrum.client.search.Search;

import com.google.gwt.user.client.ui.Widget;

public class ProductBacklogFilterWidget extends AScrumWidget {

	private ProjectUserConfig userConfig;

	public List<Requirement> getRequirements() {
		initialize();
		List<Requirement> ret = new ArrayList<Requirement>();
		for (Requirement req : getCurrentProject().getProductBacklogRequirements()) {
			if (matches(req)) ret.add(req);
		}
		return ret;
	}

	private boolean matches(Requirement req) {
		if (!userConfig.getPblFilterThemes().isEmpty()) {
			for (String theme : userConfig.getPblFilterThemes()) {
				if (!req.containsTheme(theme)) return false;
			}
		}
		if (!userConfig.getPblFilterQualitys().isEmpty()) {
			for (Quality quality : userConfig.getPblFilterQualitys()) {
				if (!req.containsQuality(quality)) return false;
			}
		}
		if (userConfig.getPblFilterEstimationFrom() != null) {
			Float estimatedWork = req.getEstimatedWork();
			if (estimatedWork == null || estimatedWork < userConfig.getPblFilterEstimationFrom()) return false;
		}
		if (userConfig.getPblFilterEstimationTo() != null) {
			Float estimatedWork = req.getEstimatedWork();
			if (estimatedWork == null || estimatedWork > userConfig.getPblFilterEstimationTo()) return false;
		}
		if (userConfig.getPblFilterText() != null) {
			if (!Search.matchesQuery(req, userConfig.getPblFilterText())) return false;
		}
		// if (userConfig.getPblFilterDateFrom()!=null) {
		// if (!req.getlastM)
		// }
		return true;
	}

	@Override
	protected Widget onInitialization() {
		userConfig = getCurrentProject().getUserConfig(getCurrentUser());

		TableBuilder tb = ScrumGwt.createFieldTable();
		tb.setColumnWidths("50px", "", "100px", "40px", "70px", "20px", "70px", "", "80px");

		tb.addField("Text", userConfig.getPblFilterTextModel(), 1);
		tb.addFieldLabel("Estimation");
		tb.addField("from", new ADropdownViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewerText(ScrumGwt.getEstimationAsString(userConfig.getPblFilterEstimationFrom(), userConfig
						.getProject().getEffortUnit()));
			}

			@Override
			protected void onEditorUpdate() {
				setOptions(Requirement.getWorkEstimationValues());
				String work = ScrumGwt.getEstimationAsString(userConfig.getPblFilterEstimationFrom());
				setSelectedOption(work == null ? "" : work.toString());
			}

			@Override
			protected void onEditorSubmit() {
				String value = getSelectedOption();
				userConfig.setPblFilterEstimationFrom(value.length() == 0 ? null : Float.parseFloat(value));
			}

			@Override
			public boolean isEditable() {
				return true;
			}

		});
		tb.addField("to", new ADropdownViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewerText(ScrumGwt.getEstimationAsString(userConfig.getPblFilterEstimationTo(), userConfig
						.getProject().getEffortUnit()));
			}

			@Override
			protected void onEditorUpdate() {
				setOptions(Requirement.getWorkEstimationValues());
				String work = ScrumGwt.getEstimationAsString(userConfig.getPblFilterEstimationTo());
				setSelectedOption(work == null ? "" : work.toString());
			}

			@Override
			protected void onEditorSubmit() {
				String value = getSelectedOption();
				userConfig.setPblFilterEstimationTo(value.length() == 0 ? null : Float.parseFloat(value));
			}

			@Override
			public boolean isEditable() {
				return true;
			}

		});
		tb.addFieldLabel("");
		tb.add(new ButtonWidget(new ResetFilterAction()));
		tb.nextRow();
		tb.addField("Themes", new ThemesWidget(userConfig.getPblFilter()));
		tb.addFieldRow("Qualities", new AMultiSelectionViewEditWidget<Quality>() {

			@Override
			protected void onViewerUpdate() {
				List<Quality> qualitys = new ArrayList<Quality>(userConfig.getPblFilterQualitys());
				Collections.sort(qualitys, Quality.LABEL_COMPARATOR);
				setViewerItemsAsHtml(qualitys);
			}

			@Override
			protected void onEditorUpdate() {
				List<Quality> qualitys = new ArrayList<Quality>(userConfig.getProject().getQualitys());
				Collections.sort(qualitys, Quality.LABEL_COMPARATOR);
				setEditorItems(qualitys);
				setEditorSelectedItems(userConfig.getPblFilterQualitys());
			}

			@Override
			protected void onEditorSubmit() {
				userConfig.setPblFilterQualitys(getEditorSelectedItems());
			}

			@Override
			public boolean isEditable() {
				return true;
			}
		}, 6);

		// tb.addField("Last modified from", userConfig.getPblFilterDateFromModel());
		// tb.addFieldRow("Last modified to", userConfig.getPblFilterDateToModel());

		return tb.createTable();
	}

	class ResetFilterAction extends AAction {

		@Override
		public String getLabel() {
			return "Reset filter";
		}

		@Override
		protected void onExecute() {
			userConfig.setPblFilterDateFrom(null);
			userConfig.setPblFilterDateTo(null);
			userConfig.setPblFilterEstimationFrom(null);
			userConfig.setPblFilterEstimationTo(null);
			Set<Quality> qualitys = Collections.emptySet();
			userConfig.setPblFilterQualitys(qualitys);
			userConfig.setPblFilterText(null);
			List<String> themes = Collections.emptyList();
			userConfig.setPblFilterThemes(themes);
		}
	}
}
