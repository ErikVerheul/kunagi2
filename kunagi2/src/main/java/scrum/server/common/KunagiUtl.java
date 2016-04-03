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
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package scrum.server.common;

import ilarkesto.base.StrExtend;
import ilarkesto.persistence.AEntity;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;

public class KunagiUtl {

	public static String createExternalRelativeHtmlAnchor(AEntity entity) {
		String reference = entity.toString();
		if (entity instanceof ReferenceSupport) {
			reference = ((ReferenceSupport) entity).getReference();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<a href='").append(reference).append(".html'>");
		sb.append(reference);
		sb.append("</a> ");
		if (entity instanceof LabelSupport) {
			sb.append(StrExtend.toHtml(((LabelSupport) entity).getLabel()));
		}
		return sb.toString();
	}
}
