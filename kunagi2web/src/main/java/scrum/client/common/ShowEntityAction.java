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

import ilarkesto.core.base.Str;
import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.AWidget;
import scrum.client.workspace.Navigator;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class ShowEntityAction extends AScrumAction {

	private String page;
	private AScrumGwtEntity entity;
	private String label;

	public ShowEntityAction(Class<? extends AWidget> page, AScrumGwtEntity entity, String label) {
		this(Str.removeSuffix(Str.getSimpleName(page), "Widget"), entity, label);
	}

	private ShowEntityAction(String page, AScrumGwtEntity entity, String label) {
		super();
		this.page = page;
		this.entity = entity;
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	protected void onExecute() {
		// TODO change to url navigation
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(entity);
	}

	@Override
	public String getTargetHistoryToken() {
		return Navigator.getEntityHistoryToken(page, entity.getId());
	}

}
