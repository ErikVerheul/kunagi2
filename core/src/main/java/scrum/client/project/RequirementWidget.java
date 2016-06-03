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
package scrum.client.project;

import ilarkesto.gwt.client.AFieldValueWidget;
import ilarkesto.gwt.client.AMultiSelectionViewEditWidget;
import ilarkesto.gwt.client.AOutputViewEditWidget;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import scrum.client.ScrumGwt;
import scrum.client.collaboration.CommentsWidget;
import scrum.client.collaboration.EmoticonSelectorWidget;
import scrum.client.common.AScrumWidget;
import scrum.client.common.ThemesWidget;
import scrum.client.estimation.PlanningPokerWidget;
import scrum.client.journal.ChangeHistoryWidget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class RequirementWidget extends AScrumWidget {

	private Requirement requirement;
	private boolean showLabel;
	private boolean showSprint;
	private boolean showTaskWork;
	private boolean showComments;
	private boolean planningPoker;
	private boolean showChangeHistory;
	private boolean acceptReject;

	private boolean decidableOnInitialization;

	// FIXME remove showComments
	public RequirementWidget(Requirement requirement, boolean showLabel, boolean showSprint, boolean showTaskWork,
			boolean showComments, boolean planningPoker, boolean showChangeHistory, boolean acceptReject) {
		this.requirement = requirement;
		this.showLabel = showLabel;
		this.showSprint = showSprint;
		this.showTaskWork = showTaskWork;
		this.showComments = showComments;
		this.planningPoker = planningPoker;
		this.showChangeHistory = showChangeHistory;
		this.acceptReject = acceptReject;
	}

	@Override
	protected Widget onInitialization() {
		decidableOnInitialization = requirement.isDecidable();

		TableBuilder left = ScrumGwt.createFieldTable();

		if (showLabel) {
			left.addFieldRow("Label", requirement.getLabelModel());
		}

		left.addFieldRow("Description", requirement.getDescriptionModel());
		left.addFieldRow("Themes", new ThemesWidget(requirement));

		left.addFieldRow("Qualities", new AMultiSelectionViewEditWidget<Quality>() {

			@Override
			protected void onViewerUpdate() {
				List<Quality> qualitys = new ArrayList<Quality>(requirement.getQualitys());
				Collections.sort(qualitys);
				setViewerItemsAsHtml(qualitys);
			}

			@Override
			protected void onEditorUpdate() {
				List<Quality> qualitys = new ArrayList<Quality>(requirement.getProject().getQualitys());
				Collections.sort(qualitys);
				setEditorItems(qualitys);
				setEditorSelectedItems(requirement.getQualitys());
			}

			@Override
			protected void onEditorSubmit() {
				requirement.setQualitys(getEditorSelectedItems());
			}

			@Override
			public boolean isEditable() {
				return requirement.isEditable();
			}
		});

		left.addFieldRow("Test", requirement.getTestDescriptionModel());

		left.addFieldRow("Estimated work", new RequirementEstimatedWorkWidget(requirement));

		if (showTaskWork) {
			left.addFieldRow("Remaining Task Work", new AFieldValueWidget() {

				@Override
				protected void onUpdate() {
					setHours(requirement.getRemainingWork());
				}
			});
		}

		if (showSprint) left.addFieldRow("Sprint", new AFieldValueWidget() {

			@Override
			protected void onUpdate() {
				setText(requirement.getSprint());
			}
		});

		left.addFieldRow("Related Stories", new AOutputViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewer(ScrumGwt.createToHtmlItemsWidget(requirement.getRelatedRequirements()));
			}
		});

		left.addFieldRow("Related Issues", new AOutputViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewer(ScrumGwt.createToHtmlItemsWidget(requirement.getRelatedIssues()));
			}
		});

		if (showChangeHistory) left.addRow(new ChangeHistoryWidget(requirement), 2);

		TableBuilder right = ScrumGwt.createFieldTable();
		if (acceptReject && requirement.getProject().isProductOwner(getCurrentUser()) && requirement.isDecidable()) {
			right.addRow(createActionsPanelForCompletedRequirement(requirement), 2);
		}
		if (planningPoker) {
			right.addRow(new PlanningPokerWidget(requirement), 2);
		}
		right.addFieldRow("My emoticon", new EmoticonSelectorWidget(requirement));
		if (showComments) {
			right.addRow(new CommentsWidget(requirement), 2);
		}

		return showComments || planningPoker || acceptReject ? TableBuilder.row(20, left.createTable(),
			right.createTable()) : left.createTable();
	}

	@Override
	protected boolean isResetRequired() {
		return acceptReject && decidableOnInitialization != requirement.isDecidable();
	}

	public static Widget createActionsPanelForCompletedRequirement(Requirement requirement) {
		TableBuilder tb = new TableBuilder();
		tb.setWidth(null);
		tb.setColumnWidths("48%", "10", "48%");

		tb.addRow(new Label("This story is completed. As Product Owner, you have to decide:"), 3);
		tb.addRow(Gwt.createSpacer(1, 10));

		tb.add(new ButtonWidget(new CloseRequirementAction(requirement)));
		tb.add(Gwt.createSpacer(10, 1));
		tb.add(new ButtonWidget(new RejectRequirementAction(requirement)));
		tb.nextRow();
		tb.addRow(Gwt.createSpacer(1, 10));

		tb.add(new Label("If this story is implemented sufficiently."));
		tb.add(Gwt.createSpacer(10, 1));
		tb.add(new Label("If this story is fixed insufficient. Please provide a comment in this case."));

		return ScrumGwt.createActionsPanel(tb.createTable());
	}
}
