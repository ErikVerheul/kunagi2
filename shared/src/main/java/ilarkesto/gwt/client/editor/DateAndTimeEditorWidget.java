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
package ilarkesto.gwt.client.editor;

import com.google.gwt.event.dom.client.KeyCodes;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.base.Str;
import static ilarkesto.core.base.Str.isBlank;
import ilarkesto.core.logging.Log;
import static ilarkesto.core.logging.Log.DEBUG;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.gwt.client.AViewEditWidget;

public class DateAndTimeEditorWidget extends AViewEditWidget {

	private Label viewer;
	private TextBox editor;
	private ADateAndTimeEditorModel model;

	public DateAndTimeEditorWidget(ADateAndTimeEditorModel model) {
		super();
		assert model != null;
		this.model = model;
	}

	@Override
	protected void onViewerUpdate() {
		setViewerValue(model.getValue());
	}

	@Override
	protected void onEditorUpdate() {
		setEditorValue(model.getValue());
	}

	@Override
	protected void onEditorSubmit() {
		model.changeValue(getEditorValue());
	}

	@Override
	protected final Widget onViewerInitialization() {
		viewer = new Label();
		return viewer;
	}

	@Override
	protected final Widget onEditorInitialization() {
		editor = new TextBox();
		editor.addFocusListener(new EditorFocusListener());
		editor.addKeyDownHandler(new EditorKeyboardListener());
		return editor;
	}

	public final void setViewerValue(DateAndTime value) {
		viewer.setText(value == null ? "." : value.toString());
	}

	public final void setEditorValue(DateAndTime value) {
		editor.setText(value == null ? null : value.toString());
		editor.setSelectionRange(0, editor.getText().length());
		editor.setFocus(true);
	}

	public final DateAndTime getEditorValue() {
		String s = editor.getText();
		if (isBlank(s)) {
                        return null;
                }
		try {
			return new DateAndTime(s);
		} catch (Exception ex) {
			DEBUG("Parsing date and time '" + s + "' failed: ", ex);
			return null;
		}
	}

	@Override
	public boolean isEditable() {
		return model.isEditable();
	}

	@Override
	public String getTooltip() {
		return model.getTooltip();
	}

	@Override
	public String getId() {
		return model.getId();
	}

	private class EditorKeyboardListener implements KeyDownHandler {

		@Override
		public void onKeyDown(KeyDownEvent event) {
			int keyCode = event.getNativeKeyCode();

			if (keyCode == KEY_ENTER) {
				submitEditor();
			} else if (keyCode == KEY_ESCAPE) {
				cancelEditor();
			}
		}
	}

	private class EditorFocusListener implements FocusListener {

		@Override
		public void onFocus(Widget sender) {}

		@Override
		public void onLostFocus(Widget sender) {
			submitEditor();
		}

	}
}
