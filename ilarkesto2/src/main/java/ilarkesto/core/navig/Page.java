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
package ilarkesto.core.navig;

import static ilarkesto.core.base.Str.toStringHelper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class Page {

	private final Plugin plugin;
	private String label;
	private final List<Item> items = new ArrayList<Item>();
	private Object payload;

    /**
     *
     * @param plugin
     */
    public Page(Plugin plugin) {
		this.plugin = plugin;
	}

    /**
     *
     * @param item
     */
    public void add(Item item) {
		items.add(item);
	}

    /**
     *
     * @param item
     */
    public void remove(Item item) {
		items.remove(item);
	}

    /**
     *
     * @return
     */
    public List<Item> getItems() {
		return items;
	}

    /**
     *
     * @param label
     */
    public void setLabel(String label) {
		this.label = label;
	}

    /**
     *
     * @return
     */
    public String getLabel() {
		return label;
	}

    /**
     *
     * @return
     */
    public Plugin getPlugin() {
		return plugin;
	}

    /**
     *
     * @return
     */
    public Object getPayload() {
		return payload;
	}

    /**
     *
     * @param payload
     */
    public void setPayload(Object payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return toStringHelper(this, label, items.size());
	}

}
