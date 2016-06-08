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
package scrum.client.project;

import ilarkesto.gwt.client.TableBuilder;
import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.Widget;

public class QualityWidget extends AScrumWidget {

	private Quality quality;

	public QualityWidget(Quality quality) {
		super();
		this.quality = quality;
	}

	@Override
	protected Widget onInitialization() {
		TableBuilder tb = ScrumGwt.createFieldTable();

		tb.addFieldRow("Label", quality.getLabelModel());
		tb.addFieldRow("Description", quality.getDescriptionModel());
		tb.addFieldRow("Test", quality.getTestDescriptionModel());

		return TableBuilder.row(20, tb.createTable(), ScrumGwt.createEmoticonsAndComments(quality));
	}
}
