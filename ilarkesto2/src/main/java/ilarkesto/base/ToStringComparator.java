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
package ilarkesto.base;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import java.util.Comparator;

public class ToStringComparator implements Comparator {

	public static final ToStringComparator INSTANCE = new ToStringComparator(false);
	public static final ToStringComparator INSTANCE_IGNORECASE = new ToStringComparator(true);

	private boolean ignoreCase;

	public ToStringComparator(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

        @SuppressWarnings(value = "RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE", justification = "Findbugs error?")
	@Override
	public int compare(Object a, Object b) {
		String as = a.toString();
		String bs = b.toString();
		if (as == null && bs == null) {
                        return 0;
                }
		if (as == null) {
                        return 1;
                }
		if (bs == null) {
                        return -1;
                }
		if (ignoreCase) {
			return as.compareToIgnoreCase(bs);
		} else {
			return as.compareTo(bs);
		}
	}

}
