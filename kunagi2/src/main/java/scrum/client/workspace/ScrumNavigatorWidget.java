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
package scrum.client.workspace;

import ilarkesto.core.menu.MenuItem;
import ilarkesto.core.menu.StaticMenu;
import ilarkesto.core.menu.StaticMenuItem;
import ilarkesto.core.menu.StaticSubmenu;
import ilarkesto.core.menu.Submenu;
import ilarkesto.gwt.client.AAction;
import ilarkesto.gwt.client.NavigatorWidget;
import scrum.client.collaboration.WikiWidget;
import scrum.client.impediments.ImpedimentListWidget;
import scrum.client.pr.BlogWidget;
import scrum.client.project.ProductBacklogWidget;
import scrum.client.tasks.WhiteboardWidget;

import com.google.gwt.user.client.ui.Widget;

public class ScrumNavigatorWidget extends NavigatorWidget<Object> {

	@Override
	protected String getHref(MenuItem item) {
		if (item instanceof StaticMenuItem) {
			StaticMenuItem staticItem = (StaticMenuItem) item;
			Object payload = staticItem.getPayload();
			if ("sprint".equals(payload)) return Navigator.getPageHref(WhiteboardWidget.class);
			if ("product".equals(payload)) return Navigator.getPageHref(ProductBacklogWidget.class);
			if ("project".equals(payload)) return Navigator.getPageHref(ImpedimentListWidget.class);
			if ("collaboration".equals(payload)) return Navigator.getPageHref(WikiWidget.class);
			if ("administration".equals(payload)) return Navigator.getPageHref(BlogWidget.class);
			if (payload instanceof Widget) return Navigator.getPageHref(((Widget) payload).getClass());
		}
		return super.getHref(item);
	}

	public void addGroup(String label, String key) {
		final StaticSubmenu submenu = new StaticSubmenu(label);
		submenu.setPayload(key);
		menu.addItem(submenu);
	}

	public void addItem(String group, String label, final Widget widget) {
		final Submenu<StaticMenuItem> groupItem = (Submenu<StaticMenuItem>) menu.getItemByPayload(group);
		assert groupItem != null;
		StaticMenu menu = (StaticMenu) groupItem.getMenu();
		StaticMenuItem subItem = menu.addItem(new StaticMenuItem(label));
		subItem.setPayload(widget);
		subItem.setOnSelect(new Runnable() {

			@Override
			public void run() {
				groupItem.select();
			}

		});
	}

	public void addItem(String label, final Widget widget) {
		assert label != null;
		assert widget != null;
		addItem(label, widget, new Runnable() {

			@Override
			public void run() {}

		});
	}

	public SwitchAction createSwitchAction(Widget widget) {
		return new SwitchAction(widget);
	}

	public class SwitchAction extends AAction {

		private Widget widget;
		private String label;

		public SwitchAction(Widget widget) {
			this.widget = widget;
		}

		@Override
		public String getLabel() {
			if (label != null) return label;
			StaticMenuItem item = menu.getItemByPayload(widget);
			return item.getLabel();
		}

		@Override
		protected void onExecute() {
			select(widget);
		}

		@Override
		public String getTargetHistoryToken() {
			return Navigator.getPageHistoryToken(Page.getPageName(widget));
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}

}
