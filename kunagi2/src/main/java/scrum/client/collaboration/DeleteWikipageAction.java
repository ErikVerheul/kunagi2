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
package scrum.client.collaboration;

import ilarkesto.gwt.client.Gwt;

public class DeleteWikipageAction extends GDeleteWikipageAction {

	public DeleteWikipageAction(scrum.client.collaboration.Wikipage wikipage) {
		super(wikipage);
	}

	@Override
	public String getLabel() {
		return "Delete this Page";
	}

	@Override
	protected void onExecute() {
		if (!Gwt.confirm("Delete wiki page " + wikipage.getName() + "?")) return;
		getDao().deleteWikipage(wikipage);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Delete Wikipage " + wikipage.getName();
		}

		@Override
		protected void onUndo() {
			getDao().createWikipage(wikipage);
		}

	}

}