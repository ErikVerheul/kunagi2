
package scrum.client.release;

import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class DeleteReleaseAction extends GDeleteReleaseAction {

    /**
     *
     * @param release
     */
    public DeleteReleaseAction(scrum.client.release.Release release) {
		super(release);
	}

	@Override
	public String getLabel() {
		return "Delete";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Delete this Release permanently.");
		if (!getCurrentProject().isScrumTeamMember(getCurrentUser())) {
                    tb.addRemark(TooltipBuilder.NOT_SCRUMTEAM);
        }
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public boolean isPermitted() {
		return getCurrentProject().isScrumTeamMember(getCurrentUser());
	}

	@Override
	protected void onExecute() {
		getDao().deleteRelease(release);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Delete " + release.getReference() + " " + release.getLabel();
		}

		@Override
		protected void onUndo() {
			getDao().createRelease(release);
		}

	}
}