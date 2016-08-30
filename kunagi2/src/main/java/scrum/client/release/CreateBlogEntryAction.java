
package scrum.client.release;

import ilarkesto.core.scope.Scope;
import scrum.client.common.TooltipBuilder;
import scrum.client.pr.BlogEntry;
import scrum.client.workspace.ProjectWorkspaceWidgets;

/**
 *
 *
 */
public class CreateBlogEntryAction extends GCreateBlogEntryAction {

    /**
     *
     * @param release
     */
    public CreateBlogEntryAction(scrum.client.release.Release release) {
		super(release);
	}

	@Override
	public String getLabel() {
		return "Create Blog Entry";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Create a new Blog Entry advertizing this Release. It will contain itemized Release Notes.");
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) {
                    tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
        }
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isScrumTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		BlogEntry blogEntry = getCurrentProject().createNewBlogEntry(release);
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(blogEntry);
	}

}