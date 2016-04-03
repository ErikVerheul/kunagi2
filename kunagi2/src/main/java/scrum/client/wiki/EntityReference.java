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
package scrum.client.wiki;

import ilarkesto.core.base.Str;

public class EntityReference extends AWikiElement {

	private String reference;
	private String label;

	public EntityReference(String reference, String label) {
		super();
		this.reference = reference;
		this.label = label;
	}

	public EntityReference(String reference) {
		this(reference, reference);
	}

	@Override
	String toHtml(HtmlContext context) {
		StringBuilder sb = new StringBuilder();
		String styleClass = "reference";
		if (!context.isEntityReferenceAvailable(reference)) styleClass += " reference-unavailable";
		sb.append("<a class='").append(styleClass).append("' ");
		String hrefOrOnclick = context.getEntityReferenceHrefOrOnclickAParameter(reference);
		sb.append(hrefOrOnclick);

		String entityLabel = context.getEntityLabelByReference(reference);
		if (!Str.isBlank(entityLabel)) {
			entityLabel = entityLabel.replace("'", "`");
			entityLabel = entityLabel.replace("\"", "`");
			sb.append(" title='").append(entityLabel).append("'");
		}

		sb.append(">");
		sb.append(escapeHtml(label));
		sb.append("</a>");
		return sb.toString();
	}

	public String getLabel() {
		return label;
	}

	public String getReference() {
		return reference;
	}

	@Override
	public String toString() {
		return "EntityReference(" + reference + "," + label + ")";
	}
}
