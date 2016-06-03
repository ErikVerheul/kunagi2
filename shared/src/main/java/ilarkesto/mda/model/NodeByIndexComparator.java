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
package ilarkesto.mda.model;

import ilarkesto.core.logging.Log;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;
import java.util.Comparator;

public class NodeByIndexComparator implements Comparator<Node>, NodeTypes {

	private static final Log log = Log.get(NodeByIndexComparator.class);

	@Override
	public int compare(Node a, Node b) {
		int ai = parse(a.getChildValueByType(Index));
		int bi = parse(b.getChildValueByType(Index));
		if (ai == bi) {
                        return 0;
                }
		return ai > bi ? 1 : -1;
	}

	private int parse(String value) {
		if (value == null) {
                        return MAX_VALUE;
                }
		try {
			return parseInt(value);
		} catch (NumberFormatException ex) {
			log.warn("Failed to parse Index:", value);
			return MAX_VALUE;
		}
	}
}
