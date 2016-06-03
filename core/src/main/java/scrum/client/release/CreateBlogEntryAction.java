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
package scrum.client.release;

import generated.client.release.GCreateBlogEntryAction;
import ilarkesto.core.scope.Scope;
import scrum.client.common.TooltipBuilder;
import scrum.client.pr.BlogEntry;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class CreateBlogEntryAction extends GCreateBlogEntryAction {

	public CreateBlogEntryAction(scrum.client.release.Release release) {
		super(release);
	}

	@Override
	public String getLabel() {
		return "Create Blog Entry";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create a new Blog Entry advertizing this Release. It will contain itemized Release Notes.");
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		BlogEntry blogEntry = getCurrentProject().createNewBlogEntry(release);
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(blogEntry);
	}

}