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

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.base.ToHtmlSupport;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ADropdownViewEditWidget extends AViewEditWidget {

	private HTML viewer;
	private ListBox editor;

	@Override
	protected final Widget onViewerInitialization() {
		viewer = new HTML();
		return viewer;
	}

	@Override
	protected final Widget onEditorInitialization() {
		editor = new ListBox();
		editor.addChangeHandler(new EditorChangeListener());
		editor.addKeyDownHandler(new CancelKeyHandler());
		editor.setVisibleItemCount(7);
		return editor;
	}

	@Override
	protected void onEditorUpdate() {
		editor.setFocus(true);
	}

	public final void setOptions(String... options) {
		Map<String, String> optionsAsKeyLabelMap = new LinkedHashMap<String, String>();
		for (String option : options) {
			optionsAsKeyLabelMap.put(option, option);
		}
		setOptions(optionsAsKeyLabelMap);
	}

	public final void setOptions(Map<String, String> optionsAsKeyLabelMap) {
		ensureEditorInitialized();
		editor.clear();
		for (Map.Entry<String, String> entry : optionsAsKeyLabelMap.entrySet()) {
			editor.addItem(entry.getValue(), entry.getKey());
		}
	}

	public final void setSelectedOption(String key) {
		for (int i = 0; i < editor.getItemCount(); i++) {
			if (editor.getValue(i).equals(key)) {
				editor.setItemSelected(i, true);
				break;
			}
		}
	}

	public final String getSelectedOption() {
		return editor.getValue(editor.getSelectedIndex());
	}

	public final void setViewerText(String text) {
		viewer.setText(text);
	}

	public final void setViewerItem(Object item) {
		if (item == null) {
			setViewerText(null);
			return;
		}
		if (item instanceof ToHtmlSupport) {
			viewer.setHTML(((ToHtmlSupport) item).toHtml());
			return;
		}
		setViewerText(item.toString());
	}

	private class EditorChangeListener implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {
			submitEditor();
		}

	}

}
