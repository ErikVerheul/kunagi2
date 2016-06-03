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
package scrum.client.common;

import ilarkesto.gwt.client.AMultiSelectionViewEditWidget;
import ilarkesto.gwt.client.editor.AEditorModel;

public class WeekdaySelectorEditorWidget extends AMultiSelectionViewEditWidget<Integer> {

	private AEditorModel<WeekdaySelector> model;

	public WeekdaySelectorEditorWidget(AEditorModel<WeekdaySelector> editor) {
		super();
		this.model = editor;
	}

	@Override
	protected void onViewerUpdate() {
		setViewerItems(model.getValue().getWeekdaysLong(), ", ");
	}

	@Override
	protected void onEditorUpdate() {
		setEditorItems(WeekdaySelector.INDEXES);
		setEditorSelectedItems(model.getValue().getWeekdaysIndexes());
	}

	@Override
	protected void onEditorSubmit() {
		model.setValue(new WeekdaySelector(getEditorSelectedItems()));
	}

	@Override
	protected String toHtml(Integer weekday) {
		switch (weekday) {
			case 1:
				return "Sunday";
			case 2:
				return "Monday";
			case 3:
				return "Tuesday";
			case 4:
				return "Wednesday";
			case 5:
				return "Thursday";
			case 6:
				return "Friday";
			case 7:
				return "Saturday";
		}
		return weekday.toString();
	}

	@Override
	public boolean isEditable() {
		return model.isEditable();
	}

	@Override
	public String getTooltip() {
		return model.getTooltip();
	}

	@Override
	public String getId() {
		return model.getId();
	}

}
