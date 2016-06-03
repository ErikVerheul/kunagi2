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
package ilarkesto.base;

import static java.lang.Character.isDigit;
import java.net.InetAddress;
import static java.net.InetAddress.getAllByName;
import java.net.UnknownHostException;

/**
 * Utilities for networking.
 */
public abstract class Net {

	public static final String getHostname(String ip) {
		String host = null;
		try {
			for (InetAddress a : getAllByName(ip)) {
				host = a.getHostName();
				if (host.length() > 0 && !isDigit(host.charAt(0))) {
                                        break;
                                }
			}
		} catch (UnknownHostException ex) {}
		return host;
	}

	public static String getHostnameOrIp(String ip) {
		String host = getHostname(ip);
		return host == null ? ip : host;
	}

}
