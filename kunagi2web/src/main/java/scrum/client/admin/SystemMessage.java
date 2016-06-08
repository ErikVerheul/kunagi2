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
package scrum.client.admin;

import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.core.time.Tm;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SystemMessage implements Serializable, IsSerializable {

	private boolean active = false;

	private String text;

	private DateAndTime expires;

	public SystemMessage() {}

	public SystemMessage(String text, DateAndTime expires, boolean active) {
		this.text = text;
		this.expires = expires;
		this.active = active;
	}

	public SystemMessage(String text) {
		this(text, null, true);
	}

	public SystemMessage(String text, long expiresInMilliseconds) {
		this(text, new DateAndTime(Tm.getCurrentTimeMillis() + expiresInMilliseconds), true);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public DateAndTime getExpires() {
		return expires;
	}

	public String getExpiresAsString() {
		if (expires == null) return null;
		TimePeriod timePeriod = expires.getPeriodFromNow();
		if (!timePeriod.isPositive()) return null;
		return "in " + timePeriod.toShortestString();
	}

	public void setExpires(DateAndTime expires) {
		this.expires = expires;
	}

}
