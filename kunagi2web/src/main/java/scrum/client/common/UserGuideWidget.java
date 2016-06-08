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
package scrum.client.common;

import ilarkesto.core.base.Str;
import ilarkesto.gwt.client.AAction;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.HyperlinkWidget;
import ilarkesto.gwt.client.editor.ABooleanEditorModel;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class UserGuideWidget extends AScrumWidget {

	private boolean enabled;
	private String text;
	private ABooleanEditorModel hideSwitch;

	private FlowPanel panel;

	public UserGuideWidget(String text, boolean enabled, ABooleanEditorModel hideSwitch) {
		super();
		this.text = text;
		this.enabled = enabled;
		this.hideSwitch = hideSwitch;
	}

	@Override
	protected Widget onInitialization() {
		if (Str.isBlank(text)) return null;

		boolean hide = hideSwitch != null && hideSwitch.isTrue();
		boolean open = enabled && !hide;
		AAction toggleAction = open ? new DeactivateAction() : new ActivateAction();

		panel = new FlowPanel();
		panel.setStyleName("UserGuideWidget");
		panel.add(Gwt.createDiv("UserGuideWidget-header", new HyperlinkWidget(toggleAction)));
		if (open) {
			panel.add(Gwt.createDiv("UserGuideWidget-content", new HTML(text)));
		}

		return panel;
	}

	private class ActivateAction extends AScrumAction {

		@Override
		public String getLabel() {
			return "User Guide";
		}

		@Override
		protected void onExecute() {
			enabled = true;
			if (hideSwitch != null) hideSwitch.setValue(false);
			reset();
		}

	}

	private class DeactivateAction extends AScrumAction {

		@Override
		public String getLabel() {
			return "User Guide";
		}

		@Override
		protected void onExecute() {
			enabled = false;
			if (hideSwitch != null) hideSwitch.setValue(true);
			reset();
		}

	}
}
