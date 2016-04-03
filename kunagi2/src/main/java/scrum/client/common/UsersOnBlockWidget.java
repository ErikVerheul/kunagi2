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
package scrum.client.common;

import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;

import java.util.Set;

import scrum.client.admin.User;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class UsersOnBlockWidget extends AScrumWidget {

	private AScrumGwtEntity entity;
	private SimplePanel wrapper;

	public UsersOnBlockWidget(AScrumGwtEntity entity) {
		super();
		this.entity = entity;
	}

	@Override
	protected Widget onInitialization() {
		wrapper = new SimplePanel();
		wrapper.setStyleName("UsersOnBlockWidget");
		return wrapper;
	}

	@Override
	protected void onUpdate() {
		TableBuilder tb = new TableBuilder();
		tb.setWidth(null);
		Set<User> users = getCurrentProject().getUsersSelecting(entity);
		boolean first = true;
		for (User user : users) {
			if (user == getCurrentUser()) continue;
			if (first) {
				first = false;
			} else {
				tb.add(new Label(","));
				tb.add(Gwt.createSpacer(3, 1));
			}

			Label label = Gwt.createInline(user.getName());
			label.getElement().getStyle().setProperty("color", user.getProjectConfig().getColor());
			tb.add(label);
		}
		wrapper.setWidget(tb.createTable());
	}

}
