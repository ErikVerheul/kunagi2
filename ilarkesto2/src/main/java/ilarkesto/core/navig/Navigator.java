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
import static ilarkesto.core.logging.ClientLog.DEBUG;
import static ilarkesto.core.logging.ClientLog.INFO;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 */
public final class Navigator {

	private final Page rootPage = new Page(null);
	private final List<Page> pages = new LinkedList<Page>();
	private final List<Plugin> plugins = new ArrayList<Plugin>();
	private NavigatorObserver observer;

    /**
     *
     */
    public Navigator() {
		goBackToRoot();
	}

    /**
     *
     * @param item
     */
    public void execute(Item item) {
		if (item == null) {
                        return;
                }
		INFO("execute:", item);
		Plugin plugin = item.getPlugin();
		if (plugin == null) {
                        return;
                }
		plugin.execute(this, item);
	}

    /**
     *
     */
    public void goBack() {
		if (pages.size() <= 1) {
                        return;
                }
		pages.remove(0);
		firePageChanged();
	}

    /**
     *
     * @param page
     */
    public void goNext(Page page) {
		INFO("goNext:", page);
		for (Item item : page.getItems()) {
			DEBUG(" -", item);
		}
		pages.add(0, page);
		firePageChanged();
	}

    /**
     *
     * @return
     */
    public int getDepth() {
		return pages.size();
	}

    /**
     *
     * @return
     */
    public Page getPage() {
		return pages.get(0);
	}

    /**
     *
     */
    public void goBackToRoot() {
		pages.clear();
		pages.add(rootPage);
		firePageChanged();
	}

    /**
     *
     * @return
     */
    public Page getRootPage() {
		return rootPage;
	}

    /**
     *
     * @param observer
     */
    public void setObserver(NavigatorObserver observer) {
		this.observer = observer;
	}

	private void firePageChanged() {
		if (observer != null) {
                        observer.onPageChanged(this);
                }
	}

    /**
     *
     * @param plugin
     */
    public void addPlugin(Plugin plugin) {
		plugins.add(plugin);
		plugin.initialize(this);
		if (getPage() == rootPage) {
                        firePageChanged();
                }
	}

	@Override
	public String toString() {
		return toStringHelper(this, getDepth(), getPage());
	}

}
