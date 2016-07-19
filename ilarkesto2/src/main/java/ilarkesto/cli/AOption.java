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
package ilarkesto.cli;

/**
 *
 * @author erik
 */
public abstract class AOption {

    /**
     *
     * @param value
     * @throws BadSyntaxException
     */
    public abstract void setValue(String value) throws BadSyntaxException;

    /**
     *
     * @return
     */
    public abstract String getUsageSyntax();

	private String name;
	private String usageText;

    /**
     *
     * @param name
     * @param usageText
     */
    public AOption(String name, String usageText) {
		this.name = name;
		this.usageText = usageText;
	}

    /**
     *
     * @return
     */
    public String getUsageDescription() {
		return usageText;
	}

    /**
     *
     * @param usageText
     */
    public void setUsageText(String usageText) {
		this.usageText = usageText;
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
     * @param name
     */
    public void setName(String name) {
		this.name = name;
	}

}
