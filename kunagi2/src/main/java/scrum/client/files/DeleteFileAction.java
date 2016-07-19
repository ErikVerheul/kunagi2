
package scrum.client.files;

import ilarkesto.gwt.client.Gwt;
import scrum.client.common.TooltipBuilder;

/**
 *
 * @author erik
 */
public class DeleteFileAction extends GDeleteFileAction {

    /**
     *
     * @param file
     */
    public DeleteFileAction(scrum.client.files.File file) {
		super(file);
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
		tb.setText("Delete this File permanently.");
		if (!file.getProject().isScrumTeamMember(getCurrentUser())) {
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
		if (!Gwt.confirm("Deleting a file is unrecoverable. Delete it anyway?")) {
                    return;
        }
		getCurrentProject().deleteFile(file);
	}

}