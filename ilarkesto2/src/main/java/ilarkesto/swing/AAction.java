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
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.swing;

import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public abstract class AAction extends AbstractAction {

	public AAction(String name, String iconResourceName) {
		super(name);
		setIcon(iconResourceName);
	}

	public void update() {}

	public void setIcon(String iconResourceName) {
		setIcon(loadIconFromResource(iconResourceName));
	}

	public void setIcon(Icon icon) {
		putValue(SMALL_ICON, icon);
	}

	protected final Icon loadIconFromResource(String resourceName) {
		if (resourceName == null) {
                        return null;
                }
		URL url = getClass().getResource(resourceName);
		if (url == null) {
                        throw new RuntimeException("Resource not found: " + getClass().getPackage().getName() + "/" + resourceName);
                }
		return new ImageIcon(url);
	}

	// ---

	public static void updateAll(AAction... actions) {
		for (AAction action : actions) {
			action.update();
		}
	}

        @Override
        public Object clone() throws CloneNotSupportedException {
                return super.clone(); //To change body of generated methods, choose Tools | Templates.
        }

}
