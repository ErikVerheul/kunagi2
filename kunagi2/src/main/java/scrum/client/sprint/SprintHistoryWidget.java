
package scrum.client.sprint;

import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.base.ChangeIndicator;
import ilarkesto.gwt.client.Gwt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import scrum.client.common.AScrumWidget;
import scrum.client.project.Requirement;
import scrum.client.workspace.PagePanel;

/**
 *
 * @author erik
 */
public class SprintHistoryWidget extends AScrumWidget {

	private final Map<Sprint, SprintHistorySprintWidget> sprintWidgets = new HashMap<Sprint, SprintHistorySprintWidget>();
	private final ChangeIndicator changeIndicator = new ChangeIndicator();
	private final PagePanel page = new PagePanel();

	@Override
	protected Widget onInitialization() {
		new RequestHistoryServiceCall().execute(new Runnable() {

			@Override
			public void run() {
				getCurrentProject().historyLoaded = true;
				update();
			}
		});
		return page;
	}

	@Override
	protected void onUpdate() {
		List<Sprint> sprints = getCurrentProject().getCompletedSprintsInReverseOrder();
		if (changeIndicator.update(sprints)) {
			page.clear();
			for (Sprint sprint : sprints) {
				page.addHeader(sprint.getReferenceAndLabel());

				SprintHistorySprintWidget widget = sprintWidgets.get(sprint);
				if (widget == null) {
					widget = new SprintHistorySprintWidget(sprint);
					sprintWidgets.put(sprint, widget);
				}

				page.addSection(widget);
			}
		}
		super.onUpdate();
	}

	private Sprint getSprint(Requirement requirement) {
		List<Sprint> sprints = new ArrayList<Sprint>(getDao().getSprints());
		Collections.sort(sprints, Sprint.END_DATE_COMPARATOR);
		for (Sprint sprint : sprints) {
			SprintReport report = sprint.getSprintReport();
			if (report == null) {
				continue; // TODO legacy workaround
			}
			if (report.containsCompletedRequirement(requirement) || report.containsRejectedRequirement(requirement)) {
                            return sprint;
            }
		}
		return null;
	}

    /**
     *
     * @param task
     * @return
     */
    public boolean select(Task task) {
		Sprint sprint = getSprint(task.getRequirement());
		if (sprint == null) {
                    return false;
        }

		SprintHistorySprintWidget sprintHistorySprintWidget = sprintWidgets.get(sprint);
		return sprintHistorySprintWidget.selectTask(task);
	}

    /**
     *
     * @param requirement
     * @return
     */
    public boolean select(Requirement requirement) {
		Sprint sprint = getSprint(requirement);
                if (sprint == null) {
                    return false;
        }

		SprintHistorySprintWidget sprintHistorySprintWidget = sprintWidgets.get(sprint);
		return sprintHistorySprintWidget.selectRequirement(requirement);
	}

    /**
     *
     * @param sprint
     * @return
     */
    public boolean select(Sprint sprint) {
		update();
		Gwt.scrollTo(sprintWidgets.get(sprint));
		return true;
	}
}
