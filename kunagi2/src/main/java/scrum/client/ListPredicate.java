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
package scrum.client;

import scrum.client.common.ElementPredicate;

/**
 *
 * @author erik
 * @param <G>
 */
public abstract class ListPredicate<G> implements ElementPredicate<G> {

	private final String name;
	private boolean enabled = true;

    /**
     *
     * @param name
     * @param enabled
     */
    public ListPredicate(String name, boolean enabled) {
		this.name = name;
		setEnabled(enabled);
	}

    /**
     *
     * @param element
     * @return
     */
    @Override
	public boolean contains(G element) {
		if (!isEnabled()) {
                    return false;
        }
		return test(element);
	}

    /**
     *
     * @param element
     * @return
     */
    protected abstract boolean test(G element);

    /**
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

    /**
     *
     * @return
     */
    public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return name;
	}
}
