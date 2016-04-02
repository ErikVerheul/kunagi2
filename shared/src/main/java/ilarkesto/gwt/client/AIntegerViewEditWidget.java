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

import com.google.gwt.event.dom.client.KeyCodes;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_BACKSPACE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_DELETE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_TAB;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import ilarkesto.core.base.Str;
import static ilarkesto.core.base.Str.isBlank;
import static ilarkesto.gwt.client.Gwt.createNbsp;
import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;

public abstract class AIntegerViewEditWidget extends AViewEditWidget {

	private Label viewer;
	private TextBox editor;
	private ButtonWidget minus;
	private ButtonWidget plus;
	private SimplePanel wrapper;
	private HorizontalPanel panel;

	protected abstract void onMinusClicked();

	protected abstract void onPlusClicked();

	protected abstract void onIntegerViewerUpdate();

	@Override
	protected final Widget onViewerInitialization() {
		viewer = new Label();

		minus = new ButtonWidget(new MinusAction());
		plus = new ButtonWidget(new PlusAction());

		wrapper = new SimplePanel();
		wrapper.setStyleName("AIntegerViewEditWidget");

		return wrapper;
	}

	@Override
	protected final void onViewerUpdate() {
		boolean editable = isEditable();
		if (panel == null || (editable && panel.getWidgetCount() < 3) || (!editable && panel.getWidgetCount() > 1)) {
			panel = new HorizontalPanel();
			if (editable) {
				panel.add(minus.update());
				panel.add(createNbsp());
			}
			panel.add(viewer);
			if (editable) {
				panel.add(createNbsp());
				panel.add(plus.update());
			}
			wrapper.setWidget(panel);
		}
		onIntegerViewerUpdate();
	}

	@Override
	protected final Widget onEditorInitialization() {
		editor = new TextBox();
		editor.setMaxLength(10);
		editor.setWidth("50px");
		editor.addFocusListener(new EditorFocusListener());
		editor.addKeyDownHandler(new EditorKeyboardListener());
		return editor;
	}

	public final void setViewerText(String text) {
		if (isBlank(text)) {
                        text = ".";
                }
		viewer.setText(text);
	}

	public final void setViewerValue(Integer value) {
		setViewerValue(value, null);
	}

	public final void setViewerValue(Integer value, String suffix) {
		String text = null;
		if (value != null) {
			text = value.toString();
			if (suffix != null) {
                                text += " " + suffix;
                        }
		}
		setViewerText(text);
	}

	public final void setEditorValue(Integer value) {
		editor.setText(value == null ? null : value.toString());
		editor.setSelectionRange(0, editor.getText().length());
		editor.setFocus(true);
	}

	public final Integer getEditorValue() {
		String text = editor.getText();
		if (text == null) {
			return null;
		} else {
			text = text.trim();
			if (text.length() == 0) {
				return null;
			} else {
				try {
					return parseInt(text);
				} catch (NumberFormatException e) {
					return null;
				}
			}
		}
	}

	public final int getEditorValue(int alternativeValueForNull) {
		Integer value = getEditorValue();
		return value == null ? alternativeValueForNull : value;
	}

	private void plus() {
		if (!isEditable()) {
                        return;
                }
		onPlusClicked();
	}

	private void minus() {
		if (!isEditable()) {
                        return;
                }
		onMinusClicked();
	}

	private class EditorKeyboardListener implements KeyDownHandler {

		@Override
		public void onKeyDown(KeyDownEvent event) {
			int keyCode = event.getNativeKeyCode();

			if (keyCode == 96) {
				keyCode = '0';
			} else if (keyCode == 97) {
				keyCode = '1';
			} else if (keyCode == 98) {
				keyCode = '2';
			} else if (keyCode == 99) {
				keyCode = '3';
			} else if (keyCode == 100) {
				keyCode = '4';
			} else if (keyCode == 101) {
				keyCode = '5';
			} else if (keyCode == 102) {
				keyCode = '6';
			} else if (keyCode == 103) {
				keyCode = '7';
			} else if (keyCode == 104) {
				keyCode = '8';
			} else if (keyCode == 105) {
				keyCode = '9';
			}

			if (isCancelKey(keyCode)) {
				editor.cancelKey();
			}

			if (keyCode == KEY_ENTER || keyCode == KEY_ESCAPE) {
				submitEditor();
			}
		}

		private boolean isCancelKey(int keyCode) {
			boolean chancelKey = true;

			chancelKey &= isDigit((char) keyCode) == false;
			chancelKey &= keyCode != KEY_ENTER;
			chancelKey &= (keyCode != KEY_TAB);
			chancelKey &= (keyCode != KEY_BACKSPACE);
			chancelKey &= (keyCode != KEY_DELETE);
			chancelKey &= (keyCode != KEY_ESCAPE);
			chancelKey |= (keyCode == 46); // 46 = "."

			return chancelKey;
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

	private class MinusAction extends AAction {

		@Override
		public String getLabel() {
			return "-";
		}

		@Override
		protected void onExecute() {
			minus();
			update();
		}
	}

	private class PlusAction extends AAction {

		@Override
		public String getLabel() {
			return "+";
		}

		@Override
		protected void onExecute() {
			plus();
			update();
		}
	}

}
