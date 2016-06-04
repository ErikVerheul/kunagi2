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
package scrum.client.issues;

import generated.scrum.client.issues.RequestClosedIssuesServiceCall;
import generated.scrum.client.issues.GIssueManager;

public class IssueManager extends GIssueManager {

	private boolean suspendedIssuesVisible;
	private boolean closedIssuesVisible;

	public void setSuspendedIssuesVisible(boolean suspendedIssuesVisible) {
		this.suspendedIssuesVisible = suspendedIssuesVisible;
	}

	public boolean isSuspendedIssuesVisible() {
		return suspendedIssuesVisible;
	}

	public boolean isClosedIssuesVisible() {
		return closedIssuesVisible;
	}

	public void setClosedIssuesVisible(boolean closedIssuesVisible) {
		if (this.closedIssuesVisible == closedIssuesVisible) return;
		this.closedIssuesVisible = closedIssuesVisible;
		if (closedIssuesVisible) {
			new RequestClosedIssuesServiceCall().execute();
		}
	}

}
