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
package scrum.client.workspace;

import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.base.Str;
import scrum.client.common.AScrumWidget;

/**
 *
 *
 */
public class Page {

	private String name;
	private AScrumWidget widget;
	private String label;
	private String groupKey;

    /**
     *
     * @param name
     * @param widget
     * @param label
     * @param groupKey
     */
    public Page(String name, AScrumWidget widget, String label, String groupKey) {
		super();
		this.name = name;
		this.widget = widget;
		this.label = label;
		this.groupKey = groupKey;
	}

    /**
     *
     * @param widget
     * @param label
     * @param groupKey
     */
    public Page(AScrumWidget widget, String label, String groupKey) {
		this(getPageName(widget), widget, label, groupKey);
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
    public String getName() {
		return name;
	}

    /**
     *
     * @return
     */
    public AScrumWidget getWidget() {
		return widget;
	}

    /**
     *
     * @return
     */
    public String getGroupKey() {
		return groupKey;
	}

	@Override
	public String toString() {
		return name;
	}

    /**
     *
     * @param page
     * @return
     */
    public static String getPageName(Widget page) {
		if (page == null) {
                    return null;
        }
		return getPageName(page.getClass());
	}

    /**
     *
     * @param pageClass
     * @return
     */
    public static String getPageName(Class<? extends Widget> pageClass) {
		String name = Str.getSimpleName(pageClass);
		return name.substring(0, name.length() - 6);
	}

}
