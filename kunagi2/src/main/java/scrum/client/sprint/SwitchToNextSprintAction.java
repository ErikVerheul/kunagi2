
package scrum.client.sprint;

import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.Gwt;
import java.util.List;
import scrum.client.common.TooltipBuilder;
import scrum.client.project.Requirement;
import scrum.client.workspace.ProjectWorkspaceWidgets;
import scrum.client.workspace.Ui;

/**
 *
 * @author erik
 */
public class SwitchToNextSprintAction extends GSwitchToNextSprintAction {

	@Override
	public String getLabel() {
		return "Switch to next Sprint";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Finish the current Sprint and replace it by the next one.");
		if (!getCurrentProject().isProductOwner(getCurrentUser())) {
                    tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
        }
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isProductOwner(getCurrentUser());
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
			if (!Gwt.confirm(sb.toString())) {
                            return;
            }
		} else {
                    if (!Gwt.confirm("Switch to next Sprint?")) {
                        return;
            }
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
