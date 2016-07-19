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
package ilarkesto.ui.usermessage;

import ilarkesto.core.time.DateAndTime;
import static ilarkesto.core.time.DateAndTime.now;

/**
 *
 * @author erik
 */
public class UserMessage {

    /**
     *
     */
    public static final String INFO = "info";

    /**
     *
     */
    public static final String ERROR = "error";

    /**
     *
     */
    public static final String WARN = "warn";

	private DateAndTime dateAndTime;
	private String type;
	private String text;

    /**
     *
     * @param type
     * @param text
     */
    public UserMessage(String type, String text) {
		this.type = type;
		this.text = text;
		this.dateAndTime = now();
	}

    /**
     *
     * @return
     */
    public boolean isError() {
		return ERROR.equals(getType());
	}

    /**
     *
     * @return
     */
    public boolean isWarn() {
		return WARN.equals(getType());
	}

    /**
     *
     * @return
     */
    public boolean isInfo() {
		return INFO.equals(getType());
	}

    /**
     *
     * @return
     */
    public String getText() {
		return text;
	}

    /**
     *
     * @return
     */
    public String getType() {
		return type;
	}

    /**
     *
     * @return
     */
    public DateAndTime getDateAndTime() {
		return dateAndTime;
	}

	@Override
	public String toString() {
		return type + " " + text;
	}

}
