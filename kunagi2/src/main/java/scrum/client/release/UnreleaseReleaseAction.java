
package scrum.client.release;

import ilarkesto.core.time.Date;
import scrum.client.common.TooltipBuilder;

/**
 *
 *
 */
public class UnreleaseReleaseAction extends GUnreleaseReleaseAction {

    /**
     *
     * @param release
     */
    public UnreleaseReleaseAction(scrum.client.release.Release release) {
		super(release);
	}

	@Override
	public String getLabel() {
		return "Mark as planned";
	}

    /**
     *
     * @param tb
     */
    @Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Mark this release as not published and not available to the users.");
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
		return release.isReleased();
	}

	@Override
	protected void onExecute() {
		Date previousDate = release.getReleaseDate();
		release.setScriptOutput(null);
		release.setReleaseDate(Date.today());
		release.setReleased(false);
		addUndo(new Undo(previousDate));
	}

	class Undo extends ALocalUndo {

		private Date date;

		public Undo(Date date) {
			super();
			this.date = date;
		}

		@Override
		public String getLabel() {
			return "Undo Mark as planned " + release.getReference() + " " + release.getLabel();
		}

		@Override
		protected void onUndo() {
			release.setReleaseDate(date);
			release.setReleased(true);
		}

	}
}