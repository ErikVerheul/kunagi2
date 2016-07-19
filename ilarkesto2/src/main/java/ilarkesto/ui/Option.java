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
package ilarkesto.ui;

import ilarkesto.base.StringProvider;

/**
 *
 * @author erik
 * @param <T>
 */
public final class Option<T> {

    /**
     *
     */
    public static final String KEY_CANCEL = "_cancel";

    /**
     *
     * @return
     */
    public String getKey() {
		return key;
	}

    /**
     *
     * @return
     */
    public String getIcon() {
		return icon;
	}

    /**
     *
     * @return
     */
    public String getImage() {
		return image;
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
    public T getPayload() {
		return payload;
	}

    /**
     *
     * @return
     */
    public boolean isGroup() {
		return group;
	}

    /**
     *
     * @return
     */
    public String getTooltip() {
		return tooltip;
	}

	@Override
	public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Option)) {return false;}
		return key.equals(((Option) obj).getKey());
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public String toString() {
		return label;
	}

	// --- dependencies ---

	private String key;
	private String label;
	private String icon;
	private String image;
	private String tooltip;
	private T payload;
	private boolean group;

    /**
     *
     * @param key
     * @param label
     * @param icon
     * @param image
     * @param payload
     */
    public Option(String key, String label, String icon, String image, T payload) {
		this.key = key;
		this.label = label;
		this.icon = icon;
		this.image = image;
		this.payload = payload;
	}

    /**
     *
     * @param key
     * @param label
     * @param icon
     * @param image
     */
    public Option(String key, String label, String icon, String image) {
		this(key, label, icon, image, null);
	}

    /**
     *
     * @param tooltip
     */
    public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

    /**
     *
     * @param group
     */
    public void setGroup(boolean group) {
		this.group = group;
	}

	// --- tooltip StringProvider ---

    /**
     *
     */
    
	public static final OptionTooltipStringProvider OPTION_TOOLTIP_STRING_PROVIDER = new OptionTooltipStringProvider();

    /**
     *
     * @param <T>
     */
    public static class OptionTooltipStringProvider<T> implements StringProvider<Option<T>> {

        /**
         *
         * @param o
         * @return
         */
        @Override
		public String getString(Option<T> o) {
			return o.getTooltip();
		}
	}

    /**
     *
     * @param <T>
     */
    public static class OptionImageUrlStringProvider<T> implements StringProvider<Option<T>> {

        /**
         *
         * @param o
         * @return
         */
        @Override
		public String getString(Option<T> o) {
			return o.getImage();
		}
	}

}
