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
import ilarkesto.gwt.client.AMultiSelectionViewEditWidget;
import ilarkesto.gwt.client.MultiSelectionWidget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import scrum.client.ScrumGwt;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ThemesWidget extends AMultiSelectionViewEditWidget<String> {

	private ThemesContainer model;
	private TextBox newThemeTextBox;

	public ThemesWidget(ThemesContainer model) {
		super();
		this.model = model;
	}

	@Override
	protected void onViewerUpdate() {
		List<String> themes = model.getThemes();
		Collections.sort(themes);
		setViewerItems(themes, ", ");
	}

	@Override
	protected void onEditorUpdate() {
		List<String> themes = new ArrayList<String>(model.getAvailableThemes());
		Collections.sort(themes);
		setEditorItems(themes);
		setEditorSelectedItems(model.getThemes());
		newThemeTextBox.setFocus(true);
	}

	@Override
	protected void onEditorSubmit() {
		model.setThemes(getEditorSelectedItems());
	}

	@Override
	protected Widget getExtendedEditorContent() {
		StringBuilder tooltip = new StringBuilder("Create a new theme.");
		if (!model.isThemesCreatable()) tooltip.append("Only Product Owner can create themes.");

		newThemeTextBox = new TextBox();
		newThemeTextBox.setEnabled(model.isThemesCreatable());
		newThemeTextBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keyCode = event.getNativeKeyCode();
				if (keyCode == KeyCodes.KEY_ENTER) {
					createTheme();
				}
				if (keyCode == KeyCodes.KEY_ESCAPE) {
					cancelEditor();
				}
				event.stopPropagation();
			}

		});
		newThemeTextBox.setWidth(200 + "px");
		newThemeTextBox.setTitle(tooltip.toString());

		Button createThemeButton = new Button("Add", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				createTheme();
			}

		});
		createThemeButton.setEnabled(model.isThemesCreatable());
		createThemeButton.setTitle(tooltip.toString());

		HorizontalPanel hp = ScrumGwt.createHorizontalPanel(3, newThemeTextBox, createThemeButton);
		hp.setWidth("");
		return hp;

		// return new HyperlinkWidget(new AddThemeAction()).update();
	}

	private void createTheme() {
		String theme = newThemeTextBox.getText();
		if (Str.isBlank(theme)) return;
		theme = theme.trim();
		MultiSelectionWidget<String> editor = getEditor();

		List<String> items = editor.getItems();

		List<String> selected = editor.getSelected();

		if (!items.contains(theme)) items.add(theme);
		Collections.sort(items);
		selected.add(theme);

		editor.setItems(items);
		editor.setSelected(selected);

		newThemeTextBox.setText(null);
	}

	@Override
	public boolean isEditable() {
		return model.isThemesEditable();
	}

}
