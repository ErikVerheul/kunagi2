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
package scrum.client.dashboard;

import ilarkesto.core.time.Tm;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class SprintBurndownWidget extends AScrumWidget {

	public static final int CHART_WIDTH = 800;
	public static final int CHART_HEIGHT = 270;

	private Image sprintChart;

	@Override
	protected Widget onInitialization() {
		sprintChart = new Image(getChartUrl(200));
		return sprintChart;
	}

	@Override
	protected void onUpdate() {
		int width = getChartWidth();
		sprintChart.setWidth(width + "px");
		sprintChart.setUrl(getChartUrl(width) + "&timestamp=" + Tm.getCurrentTimeMillis());
	}

	private String getChartUrl(int width) {
		return getCurrentSprint().getChartUrl(width, CHART_HEIGHT);
	}

	private int getChartWidth() {
		int width = Window.getClientWidth() - 280;
		width = width / 2;
		if (width < 100) width = 100;
		return width;
	}
}
