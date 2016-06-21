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
package scrum.client.workspace;

import ilarkesto.gwt.client.FloatingFlowPanel;
import ilarkesto.gwt.client.Gwt;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PagePanel extends Composite implements HasWidgets {

	private FlowPanel content;
	private boolean spacered;

	public PagePanel() {
		content = new FlowPanel();
		initWidget(Gwt.createDiv("PagePanel", Gwt.createDiv("PagePanel-content", content)));
	}

	public void addHeader(String title, Widget... rightWidgets) {
		addHeader(Gwt.createDiv("title", title), rightWidgets);
	}

	public void addHeader(Widget left, Widget... rightWidgets) {
		FloatingFlowPanel panel = new FloatingFlowPanel();
		panel.add(left);
		for (Widget right : rightWidgets) {
			panel.add(Gwt.createNbsp(), true);
			panel.add(right, true);
		}
		addHeader(panel);
	}

	public void addHeader(String text) {
		addHeader(new Label(text));
	}

	public void addHeader(Widget widget) {
		add("header", widget);
	}

	public void addSection(String text) {
		addSection(new Label(text));
	}

	public void addSection(Widget widget) {
		if (!spacered) addSpacer();
		add("section", widget);
		addSpacer();
	}

	public void add(String styleSuffix, Widget widget) {
		add(Gwt.createDiv("PagePanel-" + styleSuffix, widget));
		spacered = false;
	}

	public void addSpacer() {
		add(Gwt.createEmptyDiv("PagePanel-spacer"));
		spacered = true;
	}

        @Override
	public void add(Widget w) {
		content.add(w);
	}

        @Override
	public boolean remove(Widget w) {
		return content.remove(w);
	}

        @Override
	public void clear() {
		content.clear();
		spacered = false;
	}

        @Override
	public Iterator<Widget> iterator() {
		return content.iterator();
	}

	@Override
	public String toString() {
		return Gwt.getSimpleName(getClass());
	}

	public static PagePanel createSimple(String header, Widget... sections) {
		PagePanel page = new PagePanel();
		page.addHeader(header);
		for (Widget section : sections) {
			page.addSection(section);
		}
		return page;
	}

}
