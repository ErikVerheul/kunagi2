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

import ilarkesto.concurrent.ACollectionTask;
import ilarkesto.core.logging.Log;
import ilarkesto.core.time.Date;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.persistence.TransactionService;

import java.util.Collection;

public class DeleteOldProjectsTask extends ACollectionTask<Project> {

	private static Log log = Log.get(DeleteOldProjectsTask.class);

	// --- dependencies ---

	private TransactionService transactionService;
	private ProjectDao projectDao;

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	// --- ---

	@Override
	protected Collection<Project> prepare() throws InterruptedException {
		return projectDao.getEntities();
	}

	@Override
	protected void perform(Project project) throws InterruptedException {
		DateAndTime opened = project.getLastOpenedDateAndTime();
		int timeToLiveInDays = project.containsParticipantWithVerifiedEmail() ? 4 : 2;
		Date deadline = Date.beforeDays(timeToLiveInDays);
		if (opened == null || opened.getDate().isAfter(deadline)) return;
		log.info("Deleting old project:", project);
		projectDao.deleteEntity(project);
	}

	@Override
	protected void cleanup() throws InterruptedException {
		transactionService.commit();
		super.cleanup();
	}

}
