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
package scrum.server.estimation;

import generated.scrum.server.estimation.GRequirementEstimationVoteDao;
import ilarkesto.core.logging.Log;
import ilarkesto.fp.Predicate;

import java.util.HashSet;
import java.util.Set;

import scrum.server.admin.User;
import scrum.server.project.Requirement;

public class RequirementEstimationVoteDao extends GRequirementEstimationVoteDao {

	private static final Log LOG = Log.get(RequirementEstimationVoteDao.class);

	public RequirementEstimationVote getRequirementEstimationVoteByUser(final Requirement requirement, final User user) {
		return getEntity(new Predicate<RequirementEstimationVote>() {

                        @Override
			public boolean test(RequirementEstimationVote vote) {
				return vote.isRequirement(requirement) && vote.isUser(user);
			}
		});
	}

	private Set<RequirementEstimationVote> getRequirementEstimationVotesByUser(final Requirement requirement,
			final User user) {
		return getEntities(new Predicate<RequirementEstimationVote>() {

                        @Override
			public boolean test(RequirementEstimationVote vote) {
				return vote.isRequirement(requirement) && vote.isUser(user);
			}
		});
	}

	public RequirementEstimationVote postVote(Requirement requirement, User user) {
		RequirementEstimationVote vote = newEntityInstance();
		vote.setRequirement(requirement);
		vote.setUser(user);
		saveEntity(vote);
		return vote;
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();

		Set<Requirement> requirements = new HashSet<Requirement>();
		for (RequirementEstimationVote vote : getEntities()) {
			requirements.add(vote.getRequirement());
		}
		for (Requirement requirement : requirements) {
			Set<User> users = requirement.getProject().getParticipants();
			for (User user : users) {
				Set<RequirementEstimationVote> votes = getRequirementEstimationVotesByUser(requirement, user);
				if (votes.size() > 1) {
					LOG.warn("Multiple estimation votes. Deleting all.");
					for (RequirementEstimationVote vote : votes) {
						deleteEntity(vote);
					}
				}
			}
		}
	}

}