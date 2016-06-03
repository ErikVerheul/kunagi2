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
package ilarkesto.io.nio.httpserver;

import ilarkesto.core.time.Tm;
import static ilarkesto.core.time.Tm.getCurrentTimeMillis;
import java.util.UUID;
import static java.util.UUID.randomUUID;

public class HttpSession<S> {

	private String id = randomUUID().toString();
	private long startTime = getCurrentTimeMillis();
	private long lastAccessTime = getCurrentTimeMillis();

	private S bean;

	void setBean(S bean) {
		this.bean = bean;
	}

	public S getBean() {
		return bean;
	}

	public String getId() {
		return id;
	}

	public void touch() {
		lastAccessTime = getCurrentTimeMillis();
	}

	public long getAge() {
		return getCurrentTimeMillis() - startTime;
	}

	public long getIdleTime() {
		return getCurrentTimeMillis() - lastAccessTime;
	}

}
