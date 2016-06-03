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
package scrum.client.sprint;

import generated.client.sprint.GSwitchToNextSprintAction;
import generated.client.sprint.SwitchToNextSprintServiceCall;
import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.Gwt;

import java.util.List;

import scrum.client.common.TooltipBuilder;
import scrum.client.project.Requirement;
import scrum.client.workspace.ProjectWorkspaceWidgets;
import scrum.client.workspace.Ui;

public class SwitchToNextSprintAction extends GSwitchToNextSprintAction {

	@Override
	public String getLabel() {
		return "Switch to next Sprint";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Finish the current Sprint and replace it by the next one.");
		if (!getCurrentProject().isProductOwner(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isProductOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		List<Requirement> undecidedRequirements = getCurrentProject().getCurrentSprint()
				.getCompletedUnclosedRequirements();
		if (!undecidedRequirements.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			if (undecidedRequirements.size() == 1) {
				sb.append("Story ").append(undecidedRequirements.get(0).getReference()).append(" is");
			} else {
				sb.append("Stories ");
				boolean first = true;
				for (Requirement req : undecidedRequirements) {
					if (first) {
						first = false;
					} else {
						sb.append(", ");
					}
					sb.append(req.getReference());
				}
				sb.append(" are");
			}
			sb.append(" completed and should be either accepted or rejected. Switch to next Sprint and reject all undecided Stories?");
			if (!Gwt.confirm(sb.toString())) return;
		} else {
			if (!Gwt.confirm("Switch to next Sprint?")) return;
		}
		Scope.get().getComponent(Ui.class).lock("Switching to next Sprint");
		new SwitchToNextSprintServiceCall().execute(new Runnable() {

			@Override
			public void run() {
				Scope.get().getComponent(ProjectWorkspaceWidgets.class).showSprintBacklog((Requirement) null);
				Scope.get().getComponent(Ui.class).unlock();
			}
		});
	}
}
