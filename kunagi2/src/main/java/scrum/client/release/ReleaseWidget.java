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
package scrum.client.release;

import ilarkesto.core.base.Str;
import ilarkesto.gwt.client.AMultiSelectionViewEditWidget;
import ilarkesto.gwt.client.AOutputViewEditWidget;
import ilarkesto.gwt.client.TableBuilder;

import java.util.Collections;
import java.util.List;

import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.issues.RequestReleaseIssuesServiceCall;
import scrum.client.journal.ChangeHistoryWidget;
import scrum.client.sprint.Sprint;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ReleaseWidget extends AScrumWidget {

	private Release release;

	public ReleaseWidget(Release release) {
		super();
		this.release = release;
	}

	@Override
	protected Widget onInitialization() {
		new RequestReleaseIssuesServiceCall(release.getId()).execute();

		TableBuilder tb = ScrumGwt.createFieldTable();

		tb.addFieldRow("Label", release.getLabelModel());
		tb.addFieldRow("Release Date", release.getReleaseDateModel());
		tb.addFieldRow("Development Notes", release.getNoteModel());
		tb.addFieldRow("Release Notes", release.getReleaseNotesModel());
		tb.addFieldRow("SCM Tag", release.getScmTagModel());
		if (release.isMajor()) {
			tb.addFieldRow("Sprints", new AMultiSelectionViewEditWidget<Sprint>() {

				@Override
				protected void onViewerUpdate() {
					setViewerItemsAsHtml(release.getSprints());
				}

				@Override
				protected void onEditorUpdate() {
					List<Sprint> sprints = release.getProject().getSprints();
					Collections.sort(sprints, Sprint.END_DATE_COMPARATOR);
					setEditorItems(sprints);
					setEditorSelectedItems(release.getSprints());
				}

				@Override
				protected void onEditorSubmit() {
					release.setSprints(getEditorSelectedItems());
				}

				@Override
				public boolean isEditable() {
					return release.getProject().isProductOwnerOrScrumMaster(getCurrentUser());
				}

				@Override
				public String getTooltip() {
					return getLocalizer().fields().releaseSprints();
				}
			});

			tb.addFieldRow("Stories", new AOutputViewEditWidget() {

				@Override
				protected void onViewerUpdate() {
					setViewer(ScrumGwt.createToHtmlItemsWidget(release.getRequirements()));
				}

				@Override
				public String getTooltip() {
					return getLocalizer().fields().releaseRequirements();
				}
			});
		}
		tb.addFieldRow("Affected by Issues", new AOutputViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewer(ScrumGwt.createToHtmlItemsWidget(release.getAffectedByIssues()));
			}

			@Override
			public String getTooltip() {
				return getLocalizer().fields().releaseAffectedByIssues();
			}
		});
		tb.addFieldRow("Fixed Issues", new AOutputViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewer(ScrumGwt.createToHtmlItemsWidget(release.getFixedIssues()));
			}

			@Override
			public String getTooltip() {
				return getLocalizer().fields().releaseFixedIssues();
			}
		});
		tb.addFieldRow("Planned Issues", new AOutputViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewer(ScrumGwt.createToHtmlItemsWidget(release.getPlannedIssues()));
			}

			@Override
			public String getTooltip() {
				return getLocalizer().fields().releasePlannedIssues();
			}
		});
		if (!Str.isBlank(release.getProject().getReleaseScriptPath())) {
			tb.addFieldRow("Script output", new AOutputViewEditWidget() {

				@Override
				protected void onViewerUpdate() {
					if (release.isScriptRunning()) {
						setViewer(ScrumGwt.createDiv("ReleaseWidget-script-running", "Release script is running..."));
						return;
					}
					String output = Str.toHtml(release.getScriptOutput());
					if (release.isReleased()) {
						setViewer(ScrumGwt.createDiv("ReleaseWidget-script-ok", new HTML(output)));
						return;
					}
					if (Str.isBlank(output)) {
						setViewer(ScrumGwt.createDiv("ReleaseWidget-script-empty", output));
						return;
					}
					setViewer(ScrumGwt.createDiv("ReleaseWidget-script-failed", new HTML(output)));
				}
			});
		}
		tb.addRow(new ChangeHistoryWidget(release), 2);

		return TableBuilder.row(20, tb.createTable(), ScrumGwt.createEmoticonsAndComments(release));
	}

}
