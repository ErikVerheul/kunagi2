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
package scrum.client.issues;

import ilarkesto.gwt.client.AnchorPanel;
import scrum.client.collaboration.EmoticonsWidget;
import scrum.client.common.ABlockWidget;
import scrum.client.common.AScrumAction;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.dnd.TrashSupport;
import scrum.client.img.Img;
import scrum.client.journal.ActivateChangeHistoryAction;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class IssueBlock extends ABlockWidget<Issue> implements TrashSupport {

	private AnchorPanel statusIcon;

	// private Label typeLabel;

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		Issue issue = getObject();

		if (issue.isBug()) statusIcon = header.addIconWrapper();

		if (issue.isBug()) header.addText(issue.getSeverityLabelModel(), "40px", true, false);

		header.addText(issue.getLabelModel());
		header.addText(issue.getThemesAsStringModel(), true, false);
		header.addText(issue.getStatusLabelModel(), true);

		header.appendOuterCell(new EmoticonsWidget(issue), null, true);

		header.addMenuAction(new AcceptIssueAsBugAction(issue));
		header.addMenuAction(new AcceptIssueAsIdeaAction(issue));
		header.addMenuAction(new ClaimIssueAction(issue));
		header.addMenuAction(new UnclaimIssueAction(issue));
		header.addMenuAction(new FixIssueAction(issue));
		header.addMenuAction(new RejectFixIssueAction(issue));
		header.addMenuAction(new PublishIssueAction(issue));
		header.addMenuAction(new ConvertIssueToRequirementAction(issue));
		header.addMenuAction(new ReopenIssueAction(issue));
		header.addMenuAction(new SuspendIssueAction(issue, 7));
		header.addMenuAction(new SuspendIssueAction(issue, 30));
		header.addMenuAction(new SuspendIssueAction(issue, 180));
		header.addMenuAction(new UnsuspendIssueAction(issue));
		header.addMenuAction(new CloseIssueAction(issue));
		header.addMenuAction(new ReplyToIssueAuthorAction(issue));
		header.addMenuAction(new ActivateChangeHistoryAction(issue));
		header.addMenuAction(new DeleteIssueAction(issue));
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		Issue issue = getObject();

		if (issue.isBug()) {
			Image statusImage = null;
			if (issue.isFixed()) {
				statusImage = Img.bundle.issFixed().createImage();
				statusImage.setTitle("Closed.");
			} else if (issue.isOwnerSet()) {
				statusImage = Img.bundle.issClaimed().createImage();
				statusImage.setTitle("Claimed by " + issue.getOwner().getName());
			}
			if (statusIcon != null) statusIcon.setWidget(statusImage);
		}
		header.setDragHandle(issue.getReference());
	}

	@Override
	protected Widget onExtendedInitialization() {
		return new IssueWidget(getObject());
	}

	@Override
	public AScrumAction getTrashAction() {
		return new DeleteIssueAction(getObject());
	}

	public static final BlockWidgetFactory<Issue> FACTORY = new BlockWidgetFactory<Issue>() {

		@Override
		public IssueBlock createBlock() {
			return new IssueBlock();
		}
	};
}
