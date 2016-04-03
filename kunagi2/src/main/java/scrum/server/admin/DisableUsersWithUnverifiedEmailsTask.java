/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.server.admin;

import ilarkesto.concurrent.ACollectionTask;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.TransactionService;

import java.util.Collection;

public class DisableUsersWithUnverifiedEmailsTask extends ACollectionTask<User> {

	private static Log log = Log.get(DisableUsersWithUnverifiedEmailsTask.class);

	// --- dependencies ---

	private UserDao userDao;
	private TransactionService transactionService;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	// --- ---

	@Override
	protected Collection<User> prepare() throws InterruptedException {
		return userDao.getUsersByEmailVerified(false);
	}

	@Override
	protected void perform(User user) throws InterruptedException {
		if (user.isDisabled()) return;
		if (user.isAdmin()) return;
		if (user.isEmailVerificationOverdue()) {
			log.warn("Disabling user:", user);
			user.setDisabled(true);
		}
	}

	@Override
	protected void cleanup() throws InterruptedException {
		transactionService.commit();
		super.cleanup();
	}

}
