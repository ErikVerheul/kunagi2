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

import ilarkesto.core.scope.Scope;
import scrum.client.Dao;
import scrum.client.collaboration.ForumSupport;
import scrum.client.common.AScrumGwtEntity;
import scrum.client.workspace.Navigator;

import com.google.gwt.core.client.GWT;

public class ScrumHtmlContext implements HtmlContext {

	private Dao dao;

	public ScrumHtmlContext() {
		this.dao = Scope.get().getComponent(Dao.class);
	}

	@Override
	public boolean isEntityReferenceAvailable(String reference) {
		return dao.getEntityByReference(reference) != null;
	}

	@Override
	public String getEntityReferenceHrefOrOnclickAParameter(String reference) {
		// return "onclick='window.scrum.showEntityByReference(\"" + reference + "\")'";
		return "href='" + Navigator.getEntityHref(reference) + "'";
	}

	@Override
	public String getTocHrefOrOnclickAParameter(Header h) {
		return "onclick=\"document.getElementById('" + h.getAnchor() + "').scrollTop = document.getElementById('"
				+ h.getAnchor() + "').offsetTop\"";
	}

	@Override
	public String getEntityLabelByReference(String reference) {
		AScrumGwtEntity entity = dao.getEntityByReference(reference);
		if (entity == null) {
			dao.requestEntityByReference(reference);
			return null;
		}
		if (entity instanceof ForumSupport) return ((ForumSupport) entity).getLabel();
		return entity.toString();
	}

	@Override
	public String getDownloadUrlByReference(String reference) {
		return GWT.getModuleBaseURL() + "fileDownload?reference=" + reference;
	}

}
