
package scrum.client.release;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class ReleaseReleaseAction extends GReleaseReleaseAction {

    /**
     *
     * @param release
     */
    public ReleaseReleaseAction(scrum.client.release.Release release) {
		super(release);
	}

	@Override
	public String getLabel() {
		return "Publish this release";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Mark this release as published and available to the users.");
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) {
                    tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
        }
	}

	@Override
	public boolean isPermitted() {
		return release.getProject().isScrumTeamMember(getCurrentUser());
	}

	@Override
	public boolean isExecutable() {
		if (release.isReleased()) {
                    return false;
        }
		return !release.isScriptRunning();
	}

	@Override
	protected void onExecute() {
		new PublishReleaseServiceCall(release.getId()).execute();
	}

}