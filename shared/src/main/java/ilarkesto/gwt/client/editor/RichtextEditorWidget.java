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

import static com.google.gwt.dom.client.Style.Unit.PX;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.AttachDetachException;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import static ilarkesto.core.base.Str.isBlank;
import ilarkesto.core.logging.Log;
import ilarkesto.gwt.client.AAction;
import ilarkesto.gwt.client.AViewEditWidget;
import ilarkesto.gwt.client.CodemirrorEditorWidget;
import static ilarkesto.gwt.client.Gwt.addTooltipHtml;
import static ilarkesto.gwt.client.Gwt.getDefaultRichtextFormater;
import static ilarkesto.gwt.client.Gwt.getDefaultRichtextSyntaxInfo;
import static ilarkesto.gwt.client.Gwt.getRichtextEditorEditInitializer;
import ilarkesto.gwt.client.Initializer;
import ilarkesto.gwt.client.RichtextFormater;
import ilarkesto.gwt.client.ToolbarWidget;

public class RichtextEditorWidget extends AViewEditWidget {

	private static final Log log = Log.get(RichtextEditorWidget.class);

	private HTML viewer;
	private SimplePanel editorWrapper;
	private CodemirrorEditorWidget editor;
	private String editorHeight = "300px";
	private ToolbarWidget editorToolbar;
	private String applyButtonLabel;
	private boolean autosave = true;

	private ATextEditorModel model;
	private ToolbarWidget bottomToolbar;

	public RichtextEditorWidget(ATextEditorModel model) {
		super();
		this.model = model;
	}

	@Override
	protected void onUpdate() {
		if (editor != null && editor.isBurned()) {
			editor = null;
			closeEditor();
			return;
		}
		super.onUpdate();
	}

	@Override
	protected void onViewerUpdate() {
		setViewerText(model.getValue());
	}

	@Override
	protected void onEditorUpdate() {
		if (editor != null && editor.isBurned()) {
                        editor = null;
                }
		if (editor == null) {
			editor = new CodemirrorEditorWidget();
			// editor.setObserver(new CodemirrorEditorWidget.Observer() {
			//
			// @Override
			// public void onCodemirrorDetach() {
			// editorWrapper.setWidget(null);
			// editor = null;
			// switchToViewMode(false);
			// }
			// });
			// editor.addFocusListener(new EditorFocusListener());
			editor.addKeyDownHandler(new EditorKeyboardListener());
			editor.ensureDebugId("richtext-id");
			editor.setStyleName("ARichtextViewEditWidget-editor");
			// editor.setWidth("97%");
			if (editorHeight != null) {
                                editor.setHeight(editorHeight);
                        }
			editor.initialize();

			String text = model.getValue();
			String template = model.getTemplate();
			if (template != null && isBlank(text)) {
                                text = template;
                        }
			editor.setText(text);
			editorWrapper.setWidget(editor);
		}
		editor.focus();
		editor.update();
		bottomToolbar.update();
	}

	@Override
	protected void focusEditor() {
		if (editor != null) {
                        editor.focus();
                }
	}

	@Override
	protected void onEditorSubmit() {
		String value = getEditorText();
		// TODO check lenght
		// TODO check format/syntax
		model.changeValue(value);
	}

	@Override
	protected final Widget onViewerInitialization() {
		viewer = new HTML();
		viewer.setStyleName("ARichtextViewEditWidget-viewer");
		return viewer;
	}

	protected void armToolbar(ToolbarWidget toolbar) {
		String syntaxInfoHtml = getSyntaxInfo();
		if (syntaxInfoHtml != null) {
			Label syntaxInfo = new Label("Syntax Info");
			syntaxInfo.getElement().getStyle().setMargin(5, PX);
			addTooltipHtml(syntaxInfo, syntaxInfoHtml);
			toolbar.add(syntaxInfo);
		}
	}

	public void setApplyButtonLabel(String applyButtonLabel) {
		this.applyButtonLabel = applyButtonLabel;
	}

	public void setAutosave(boolean autosave) {
		this.autosave = autosave;
	}

	public boolean isAutosave() {
		return autosave;
	}

	@Override
	protected final Widget onEditorInitialization() {
		editorWrapper = new SimplePanel();

		editorToolbar = new ToolbarWidget();
		armToolbar(editorToolbar);

		bottomToolbar = new ToolbarWidget();
		bottomToolbar.addButton(new AAction() {

			@Override
			public String getLabel() {
				String label = applyButtonLabel;
				if (isBlank(label)) {
                                        label = autosave ? "Finish" : "Apply";
                                }
				return label;
			}

			@Override
			protected void onExecute() {
				submitEditor();
			}
		});
		if (!autosave) {
			bottomToolbar.addButton(new AAction() {

				@Override
				public String getLabel() {
					return "Cancel";
				}

				@Override
				protected void onExecute() {
					cancelEditor();
				}
			});
		}

		// toolbar.add(Gwt
		// .createHyperlink("http://en.wikipedia.org/wiki/Wikipedia:Cheatsheet", "Syntax Cheatsheet", true));

		FlowPanel editorPanel = new FlowPanel();
		editorPanel.setStyleName("AEditableTextareaWidget-editorPanel");
		if (!editorToolbar.isEmpty()) {
                        editorPanel.add(editorToolbar.update());
                }

		editorPanel.add(editorWrapper);
		editorPanel.add(bottomToolbar.update());

		Initializer<RichtextEditorWidget> initializer = getRichtextEditorEditInitializer();
		if (initializer != null) {
                        initializer.initialize(this);
                }

		return editorPanel;
	}

	@Override
	protected void onEditorClose() {
		super.onEditorClose();
		editor = null;
		try {
			editorWrapper.clear();
		} catch (AttachDetachException ex) {
			log.error(ex);
		}
	}

	public ToolbarWidget getEditorToolbar() {
		return editorToolbar;
	}

	public CodemirrorEditorWidget getEditor() {
		return editor;
	}

	public final void setViewerText(String text) {
		if (isBlank(text)) {
			viewer.setHTML(".");
			return;
		}
		String html = getRichtextFormater().richtextToHtml(text);
		viewer.setHTML(html);
	}

	// public final void setViewerHtml(String html) {
	// if (Str.isBlank(html)) html = ".";
	// viewer.setHTML(html);
	// }

	@Override
	protected void closeEditor() {
		if (autosave) {
			submitEditor();
		} else {
			super.closeEditor();
		}
	}

	public final String getEditorText() {
		if (editor == null) {
                        return null;
                }
		String text = editor.getText();
		if (isBlank(text)) {
                        return null;
                }
		return text;
	}

	@Override
	public boolean isEditable() {
		return model.isEditable();
	}

	protected String getSyntaxInfo() {
		return getDefaultRichtextSyntaxInfo();
	}

	protected RichtextFormater getRichtextFormater() {
		return getDefaultRichtextFormater();
	}

	public RichtextEditorWidget setEditorHeight(int pixels) {
		editorHeight = pixels + "px";
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

	public ATextEditorModel getModel() {
		return model;
	}

	private class EditorFocusListener implements FocusListener {

		@Override
		public void onFocus(Widget sender) {}

		@Override
		public void onLostFocus(Widget sender) {
			submitEditor();
		}

	}

	private class EditorKeyboardListener implements KeyDownHandler {

		@Override
		public void onKeyDown(KeyDownEvent event) {
			int keyCode = event.getNativeKeyCode();

			if (keyCode == KEY_ESCAPE) {
				cancelEditor();
				event.stopPropagation();
			}

			if (event.isControlKeyDown()) {
				if (keyCode == KEY_ENTER || keyCode == 10) {
					submitEditor();
					event.stopPropagation();
				}
			}
		}

	}

}
