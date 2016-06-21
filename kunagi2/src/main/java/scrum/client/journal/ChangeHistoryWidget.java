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
package scrum.client.journal;

import ilarkesto.core.scope.Scope;

import java.util.Collections;
import java.util.List;

import scrum.client.common.AScrumGwtEntity;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ChangeHistoryWidget extends AScrumWidget {

	private AScrumGwtEntity parent;
	private FlowPanel panel;
	private ChangeHistoryManager changeHistoryManager;
	private boolean active;

	public ChangeHistoryWidget(AScrumGwtEntity parent) {
		super();
		this.parent = parent;
	}

	@Override
	protected Widget onInitialization() {
		changeHistoryManager = Scope.get().getComponent(ChangeHistoryManager.class);

		panel = new FlowPanel();
		return panel;
	}

	@Override
	protected void onUpdate() {
		if (!changeHistoryManager.isChangeHistoryActive(parent)) {
			if (active) {
				panel.clear();
				panel.removeStyleName("ChangeHistoryWidget");
				active = false;
			}
			return;
		}

		if (!active) {
			panel.setStyleName("ChangeHistoryWidget");
			active = true;
		}

		panel.clear();
		List<Change> changes = changeHistoryManager.getChanges(parent);

		if (changes.isEmpty()) {
			panel.add(new Label("No change history available."));
		} else {
			Collections.sort(changes, Change.DATE_AND_TIME_COMPARATOR);
			for (Change change : changes) {
				panel.add(new ChangeWidget(change));
			}
		}

		super.onUpdate();
	}

}
