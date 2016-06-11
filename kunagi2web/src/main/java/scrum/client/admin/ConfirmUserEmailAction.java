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
package scrum.client.admin;

import ilarkesto.core.base.Str;

public class ConfirmUserEmailAction extends GConfirmUserEmailAction {

	public ConfirmUserEmailAction(scrum.client.admin.User user) {
		super(user);
	}

	@Override
	public String getLabel() {
		return "Confirm email";
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentUser().isAdmin()) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (!Str.isEmail(user.getEmail())) return false;
		if (user.isEmailVerified()) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		user.setEmailVerified(true);
	}

}