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
package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class HyperlinkWidget extends AWidget {

	private Widget hyperlink;
	private AAction action;

	public HyperlinkWidget(AAction action) {
		this.action = action;
		String token = action.getTargetHistoryToken();
		if (token == null) {
			HyperlinkWithoutHistory hl = new HyperlinkWithoutHistory();
			hl.addClickHandler(action);
			this.hyperlink = hl;
		} else {
			Hyperlink hl = new Hyperlink(action.getLabel(), token);
			this.hyperlink = hl;
		}
	}

	@Override
	protected Widget onInitialization() {
		return hyperlink;
	}

	@Override
	protected void onUpdate() {
		hyperlink.getElement().setId("hyperlink_" + action.getId());
		if (hyperlink instanceof HyperlinkWithoutHistory) {
			((HyperlinkWithoutHistory) hyperlink).setText(action.getLabel());
		} else {
			((Hyperlink) hyperlink).setText(action.getLabel());
		}
		hyperlink.setTitle(action.getTooltip());
		hyperlink.setVisible(action.isExecutable() && action.isPermitted());
	}

	@Override
	public String toString() {
		return "HyperlinkWidget(" + action + ")";
	}

}
