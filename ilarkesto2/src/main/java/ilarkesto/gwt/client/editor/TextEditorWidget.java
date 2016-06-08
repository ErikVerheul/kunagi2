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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.base.Str;
import static ilarkesto.core.base.Str.isBlank;
import ilarkesto.gwt.client.AViewEditWidget;
import ilarkesto.gwt.client.Gwt;
import static ilarkesto.gwt.client.Gwt.getDefaultRichtextFormater;
import ilarkesto.gwt.client.RichtextFormater;

public class TextEditorWidget extends AViewEditWidget {

	private HTML viewer;
	private TextBox editor;
	private ATextEditorModel model;

	public TextEditorWidget(ATextEditorModel model) {
		super();
		this.model = model;
	}

	@Override
	protected final Widget onViewerInitialization() {
		viewer = new HTML();
		return viewer;
	}

	@Override
	protected final Widget onEditorInitialization() {
		editor = new TextBox();
		editor.setMaxLength(model.getMaxLenght());
		editor.setWidth("97%");
		editor.addFocusListener(new EditorFocusListener());
		editor.addKeyDownHandler(new EditorKeyHandler());
		return editor;
	}

	@Override
	protected void onViewerUpdate() {
		String value = model.getValue();
		if (model.isMasked() && !isBlank(value)) {
			value = "*****";
		}
		setViewerText(value);
	}

	@Override
	protected void onEditorUpdate() {
		setEditorText(model.getValue());
	}

	@Override
	protected void onEditorSubmit() {
		String value = getEditorText();
		// TODO check lenght
		// TODO check format/syntax
		model.changeValue(value);
		// TODO catch exceptions
	}

	public final void setViewerText(String text) {
		if (isBlank(text)) {
			viewer.setHTML(".");
			return;
		}
		String html = getRichtextFormater().richtextToHtml(text);
		viewer.setHTML(html);
	}

	protected RichtextFormater getRichtextFormater() {
		return getDefaultRichtextFormater();
	}

	public final void setEditorText(String text) {
		editor.setText(text);
		focusEditor();
		editor.setSelectionRange(0, editor.getText().length());
	}

	@Override
	public boolean isEditable() {
		return model.isEditable();
	}

	@Override
	protected void focusEditor() {
		editor.setFocus(true);
	}

	public final String getEditorText() {
		String text = editor.getText();
		if (isBlank(text)) {
                        return null;
                }
		return text;
	}

	protected Label getViewer() {
		return viewer;
	}

	public TextEditorWidget switchToEditModeIfNull() {
		if (isEditable() && isBlank(model.getValue())) {
                        switchToEditMode();
                }
		return this;
	}

	@Override
	public String getTooltip() {
		return model.getTooltip();
	}

	@Override
	public String getId() {
		return model.getId();
	}

	private class EditorKeyHandler implements KeyDownHandler {

		@Override
		public void onKeyDown(KeyDownEvent event) {
			int keyCode = event.getNativeKeyCode();
			if (keyCode == KEY_ENTER) {
				submitEditor();
			} else if (keyCode == KEY_ESCAPE) {
				cancelEditor();
			}
			event.stopPropagation();
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
