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
package ilarkesto.gwt.client.animation;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import java.util.Iterator;

public class AnimatingFlowPanel<W extends Widget> extends Composite implements HasWidgets {

        @SuppressWarnings("MS_SHOULD_BE_FINAL")
	public static boolean animationsDisabled = false;

	private FlowPanel panel;

	public AnimatingFlowPanel() {
		panel = new FlowPanel();
		initWidget(panel);
	}

	public void insertAnimated(int index, W widget, Integer height) {
		insert(index, widget);
		if (!animationsDisabled) {
                        new AppearAnimation(height, widget).run(250);
                }
	}

	public void insert(int index, W widget) {
		if (index < 0) {
                        index = panel.getWidgetCount();
                }
		panel.insert(widget, index);
	}

	@Override
	public boolean remove(final Widget widget) {
		return panel.remove(widget);
	}

	@Override
	public void clear() {
		panel.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return panel.iterator();
	}

	@Override
	public void add(Widget w) {
		insertAnimated(-1, (W) w, null);
	}

	public static interface InsertCallback {

		void onInserted(int index);

	}

}
