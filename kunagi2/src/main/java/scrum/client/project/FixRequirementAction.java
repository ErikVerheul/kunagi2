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
package scrum.client.project;

/**
 *
 * @author erik
 */
public class FixRequirementAction extends GFixRequirementAction {

    /**
     *
     * @param requirement
     */
    public FixRequirementAction(scrum.client.project.Requirement requirement) {
		super(requirement);
	}

	@Override
	public boolean isPermitted() {
		return requirement.getProject().isTeamMember(getCurrentUser());
	}

	@Override
	public boolean isExecutable() {
		return requirement.isRejected();
	}

	@Override
	public String getLabel() {
		return "Mark as done";
	}

	@Override
	protected void onExecute() {
		requirement.fix();
	}

}