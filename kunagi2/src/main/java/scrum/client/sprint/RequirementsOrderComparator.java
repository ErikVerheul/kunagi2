/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package scrum.client.sprint;

import java.util.Comparator;
import java.util.List;

import scrum.client.project.Requirement;

public abstract class RequirementsOrderComparator implements Comparator<Requirement> {

	protected abstract List<String> getOrder();

	@Override
	public int compare(Requirement a, Requirement b) {
		List<String> order = getOrder();
		int additional = order.size();
		int ia = order.indexOf(a.getId());
		if (ia < 0) {
			ia = additional;
			additional++;
		}
		int ib = order.indexOf(b.getId());
		if (ib < 0) {
			ib = additional;
			additional++;
		}
		return ia - ib;
	}

}
