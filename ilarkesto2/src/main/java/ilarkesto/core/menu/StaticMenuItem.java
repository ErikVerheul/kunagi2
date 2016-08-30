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
package ilarkesto.core.menu;

import static ilarkesto.core.logging.ClientLog.DEBUG;

/**
 *
 *
 */
public class StaticMenuItem implements MenuItem {

	private final String label;
	private boolean selected;

    /**
     *
     */
    protected StaticMenu menu;
	private Object payload;

	private Runnable onSelect;
	private Runnable onDeselect;

    /**
     *
     * @param label
     */
    public StaticMenuItem(String label) {
		super();
		this.label = label;
	}

	void setMenu(StaticMenu menu) {
		this.menu = menu;
	}

    /**
     *
     * @return
     */
    @Override
	public String getLabel() {
		return label;
	}

    /**
     *
     * @return
     */
    @Override
	public boolean isSelected() {
		return selected;
	}

    /**
     *
     */
    @Override
	public void select() {
		if (selected) {
                        return;
                }
		DEBUG("select(", this, ")");
		menu.deselectAll();
		selected = true;
		if (onSelect != null) {
                        onSelect.run();
                }
		menu.changeIndicator.markChanged();
	}

    /**
     *
     */
    @Override
	public void deselect() {
		if (!selected) {
                        return;
                }
		selected = false;
		if (onDeselect != null) {
                        onDeselect.run();
                }
		menu.changeIndicator.markChanged();
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
     * @param test
     * @return
     */
    public boolean isPayload(Object test) {
		if (test == null && payload == null) {
                        return true;
                }
		if (test == null || payload == null) {
                        return false;
                }
		return payload.equals(test);
	}

    /**
     *
     * @param payload
     * @return
     */
    public StaticMenuItem setPayload(Object payload) {
		this.payload = payload;
		if (menu != null) {
                        menu.changeIndicator.markChanged();
                }
		return this;
	}

    /**
     *
     * @param onSelect
     * @return
     */
    public StaticMenuItem setOnSelect(Runnable onSelect) {
		this.onSelect = onSelect;
		return this;
	}

    /**
     *
     * @param onDeselect
     * @return
     */
    public StaticMenuItem setOnDeselect(Runnable onDeselect) {
		this.onDeselect = onDeselect;
		return this;
	}

	@Override
	public String toString() {
		return selected ? "*" + getLabel() : getLabel();
	}

}
