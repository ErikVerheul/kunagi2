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
package scrum.server.project;

import generated.scrum.server.project.GProjectDao;
import ilarkesto.core.logging.Log;
import ilarkesto.core.time.Date;
import ilarkesto.core.time.DateAndTime;
import scrum.server.admin.User;
import scrum.server.admin.UserDao;

public class ProjectDao extends GProjectDao {

	private static final Log LOG = Log.get(ProjectDao.class);

	@Override
	public Project newEntityInstance() {
		Project project = super.newEntityInstance();
		project.setLastOpenedDateAndTime(DateAndTime.now());
		return project;
	}

	public Project postProject(User admin) {
		Project project = newEntityInstance();
		project.setLabel(createProjectLabel("Project"));
		project.addAdmin(admin);
		saveEntity(project);
		return project;
	}

	private String createProjectLabel(String labelPrefix) {
		int count = 1;
		String label = labelPrefix + " " + count;
		Project project = getProjectByLabel(label);
		while (project != null) {
			count++;
			label = labelPrefix + " " + count;
			project = getProjectByLabel(label);
		}
		return label;
	}

	public void scanFiles() {
		LOG.info("Scanning file repositories of all projects");
		for (Project project : getEntities()) {
			project.scanFiles();
		}
	}

	// --- test data ---

	public Project postExampleProject(User owner, User po, User sm) {
		Project project = postProject(owner);
		project.setBegin(Date.today().addMonths(-2));
		project.setEnd(Date.today().addMonths(5));
		project.setSupportEmail("support@kunagi.org");
		project.setIssueReplyTemplate("Hello ${issuer.name},\n" + "\n"
				+ "Thank you for your feedback. You can check the status of your issue here: "
				+ "${homepage.url}/${issue.reference}.html" + "\n" + "\nKind regards,\n" + "${user.name}");

		project.addAdmin(owner);
		project.addProductOwner(po);
		project.addProductOwner(owner);
		project.addScrumMaster(sm);
		project.addScrumMaster(owner);
		project.addTeamMember(owner);
		project.addParticipants(project.getAdmins());
		project.addParticipants(project.getTeamMembers());
		project.addParticipants(project.getProductOwners());
		project.addParticipants(project.getScrumMasters());

		project.addTestSprints();
		project.addTestRequirements();
		project.addTestImpediments();
		project.addTestRisks();
		project.addTestQualitys();
		project.addTestIssues();
		project.addTestEvents();
		project.addTestSimpleEvents();
		project.addTestReleases();

		project.getCurrentSprint().burndownTasksRandomly(Date.beforeDays(15), Date.today().addDays(-1));

		po.setCurrentProject(project);

		return project;
	}

}
