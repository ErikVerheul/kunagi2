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
package scrum.client.common;

import ilarkesto.core.base.Str;

import java.util.ArrayList;
import java.util.List;

public class TooltipBuilder {

	private String text;
	private List<String> remarks = new ArrayList<String>();

	public final static String NOT_TEAM = "You are not Team Member.";
	public final static String NOT_SCRUMMASTER = "You are not ScrumMaster.";
	public final static String NOT_PRODUCT_OWNER = "You are not Product Owner.";
	public final static String NOT_PRODUCT_OWNER_NOR_SCRUMMASTER = "You are not Product Owner nor ScrumMaster.";
	public final static String NOT_SCRUMTEAM = "You are neither Product Owner nor ScrumMaster nor Team Member.";
	public final static String NOT_TEAM_NOR_PRODUCT_OWNER = "You are neither Team Member nor Product Owner.";
	public final static String NOT_ADMIN = "You are not Project Admin.";
	public final static String NOT_SYS_ADMIN = "You are not System Admin.";

	public TooltipBuilder() {}

	public void setText(String mainTooltip) {
		this.text = mainTooltip;
	}

	public boolean isBlank() {
		if (!Str.isBlank(text)) return false;
		if (!remarks.isEmpty()) return false;
		return true;
	}

	public void addRemark(String remark) {
		remarks.add(remark);
	}

	public String getTooltip() {
		StringBuilder sb = new StringBuilder();
		sb.append(text);

		if (remarks.size() > 0) {

			if (remarks.size() == 1) {
				sb.append(" Disabled for the following reason:");
			} else {
				sb.append(" Disabled for the following reasons:");
			}

			for (String element : remarks) {
				sb.append(" ").append(element);
			}

		}
		return sb.toString();
	}

	public String getTooltipAsHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>").append(text).append("</p>");

		if (remarks.size() > 0) {

			if (remarks.size() == 1) {
				sb.append("<p><i>Disabled for the following reason:</i></p>");
			} else {
				sb.append("<p><i>Disabled for the following reasons:</i></p>");
			}

			sb.append("<ul>");
			for (String element : remarks) {
				sb.append("<li>").append(element).append("</li>");
			}
			sb.append("</ul>");

		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return getTooltip();
	}

}
