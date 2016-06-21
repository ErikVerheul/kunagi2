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
package scrum.client.admin;

import ilarkesto.gwt.client.AMultiSelectionViewEditWidget;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.editor.TextEditorWidget;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import scrum.client.Dao;
import scrum.client.ScrumGwt;
import scrum.client.common.ABlockWidget;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.project.DeleteProjectAction;
import scrum.client.project.OpenProjectAction;
import scrum.client.project.Project;

import com.google.gwt.user.client.ui.Widget;

public class ProjectBlock extends ABlockWidget<Project> {

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		Project project = getObject();
		header.addText(project.getLabelModel());
		header.addText(project.getLastOpenedAgoModel(), true);
		header.addToolbarAction(new OpenProjectAction(project));
		header.addMenuAction(new DeleteProjectAction(project));
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		header.setDragHandle("prj");
	}

	@Override
	protected Widget onExtendedInitialization() {
		final Project project = getObject();

		TableBuilder tb = ScrumGwt.createFieldTable();
		tb.addFieldRow("Name", new TextEditorWidget(project.getLabelModel()));

		tb.addFieldRow("Support Email", project.getSupportEmailModel());

		addParticipantsAndAdminsFieldRows(project, tb);

		addRolesFieldRows(project, tb);

		// if (project.isAdmin(getCurrentUser())) {
		// tb.addFieldRow("",
		// Gwt.createServletDownloadLink("backupDownload.zip?projectId=" + project.getId(),
		// "Download Backup ZIP"));
		// }

		return tb.createTable();
	}

	public static void addParticipantsAndAdminsFieldRows(final Project project, TableBuilder tb) {
		tb.addFieldRow("Participants", new AMultiSelectionViewEditWidget<User>() {

			@Override
			protected void onViewerUpdate() {
				setViewerItemsAsHtml(project.getParticipants());
			}

			@Override
			protected void onEditorUpdate() {
				List<User> users = Dao.get().getUsersByDisabled(false);
				Set<User> participants = project.getParticipants();
				for (User participant : participants) {
					if (!users.contains(participant)) users.add(participant);
				}
				Collections.sort(users);
				setEditorItems(users);
				setEditorSelectedItems(participants);
			}

			@Override
			protected void onEditorSubmit() {
				project.setParticipantsConfigured(getEditorSelectedItems());
			}

			@Override
			public boolean isEditable() {
				return project.isEditable();
			}
		});

		tb.addFieldRow("Admins", new AMultiSelectionViewEditWidget<User>() {

			@Override
			protected void onViewerUpdate() {
				setViewerItemsAsHtml(project.getAdmins());
			}

			@Override
			protected void onEditorUpdate() {
				setEditorItems(project.getParticipants());
				setEditorSelectedItems(project.getAdmins());
			}

			@Override
			protected void onEditorSubmit() {
				project.setAdmins(getEditorSelectedItems());
			}

			@Override
			public boolean isEditable() {
				return project.isEditable();
			}

		});
	}

	public static void addRolesFieldRows(final Project project, TableBuilder tb) {
		tb.addFieldRow("Product Owner", new AMultiSelectionViewEditWidget<User>() {

			@Override
			protected void onViewerUpdate() {
				setViewerItemsAsHtml(project.getProductOwners());
			}

			@Override
			protected void onEditorUpdate() {
				setEditorItems(project.getParticipants());
				setEditorSelectedItems(project.getProductOwners());
			}

			@Override
			protected void onEditorSubmit() {
				project.setProductOwners(getEditorSelectedItems());
			}

			@Override
			public boolean isEditable() {
				return project.isEditable();
			}
		});

		tb.addFieldRow("Scrum Master", new AMultiSelectionViewEditWidget<User>() {

			@Override
			protected void onViewerUpdate() {
				setViewerItemsAsHtml(project.getScrumMasters());
			}

			@Override
			protected void onEditorUpdate() {
				setEditorItems(project.getParticipants());
				setEditorSelectedItems(project.getScrumMasters());
			}

			@Override
			protected void onEditorSubmit() {
				project.setScrumMasters(getEditorSelectedItems());
			}

			@Override
			public boolean isEditable() {
				return project.isEditable();
			}
		});

		tb.addFieldRow("Development Team", new AMultiSelectionViewEditWidget<User>() {

			@Override
			protected void onViewerUpdate() {
				setViewerItemsAsHtml(project.getTeamMembers());
			}

			@Override
			protected void onEditorUpdate() {
				setEditorItems(project.getParticipants());
				setEditorSelectedItems(project.getTeamMembers());
			}

			@Override
			protected void onEditorSubmit() {
				project.setTeamMembers(getEditorSelectedItems());
			}

			@Override
			public boolean isEditable() {
				return project.isEditable();
			}
		});
	}

	public static final BlockWidgetFactory<Project> FACTORY = new BlockWidgetFactory<Project>() {

		@Override
		public ProjectBlock createBlock() {
			return new ProjectBlock();
		}
	};
}
