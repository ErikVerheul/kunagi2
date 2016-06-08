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
package ilarkesto.gwt.client;

import com.google.gwt.user.client.Window;
import static com.google.gwt.user.client.Window.scrollTo;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SwitcherWidget extends AWidget {

	private Widget currentWidget;
	private boolean height100;

	public SwitcherWidget(boolean height100) {
		this.height100 = height100;
	}

	@Override
	protected final Widget onInitialization() {
		if (height100) {
                        setHeight100();
                }
		return new Label("Empty SwitcherWidget");
	}

	public <W extends Widget> W show(W widget) {
		if (currentWidget == widget) {
                        return widget;
                }
		currentWidget = widget;
		replaceContent(currentWidget);
		update();
		scrollTo(0, 0);
		return widget;
	}

	public boolean isShowing(Widget widget) {
		return currentWidget == widget;
	}

	public Widget getCurrentWidget() {
		return currentWidget;
	}

	@Override
	public String toString() {
		return "SwitcherWidget(" + Gwt.toString(currentWidget) + ")";
	}

}
