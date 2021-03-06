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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import static ilarkesto.core.base.Str.getSimpleName;
import static ilarkesto.core.base.Str.isBlank;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.core.logging.ClientLog.DEBUG;
import static ilarkesto.core.logging.ClientLog.ERROR;
import static ilarkesto.gwt.client.Gwt.addTooltipHtml;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.gwt.client.Gwt.getRootWidget;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;
import static ilarkesto.core.base.Utl.getUserMessageStack;
import static ilarkesto.gwt.client.Gwt.createDiv;

/**
 *
 */
public abstract class AViewEditWidget extends AWidget {

    private static AViewEditWidget currentEditor;
    private static ModeSwitchHandler globalModeSwitchHandler;

    private boolean viewMode = true;
    private ModeSwitchHandler modeSwitchHandler;

    private FocusPanel masterWrapper;
    private FocusPanel viewerWrapper;
    private FlowPanel editorWrapper;
    private SimplePanel errorWrapper;

    private boolean viewerInitialized;
    private boolean viewerInitializing;
    private boolean editorInitialized;
    private boolean editorInitializing;

    /**
     *
     */
    protected abstract void onViewerUpdate();

    /**
     *
     * @return
     */
    protected abstract Widget onViewerInitialization();

    /**
     *
     */
    protected abstract void onEditorUpdate();

    /**
     *
     * @return
     */
    protected abstract Widget onEditorInitialization();

    /**
     *
     */
    protected abstract void onEditorSubmit();

    /**
     *
     * @return
     */
    @Override
    protected final Widget onInitialization() {
        masterWrapper = new FocusPanel();
        masterWrapper.setStyleName("AViewEditWidget");
        addTooltipHtml(masterWrapper, getTooltip());
        return masterWrapper;
    }

    /**
     *
     */
    @Override
    protected void onUpdate() {
        if (isViewMode()) {
            updateViewer();
        } else {
            focusEditor();
            // updateEditor();
        }
    }

    /**
     *
     */
    protected void onEditorClose() {
    }

    /**
     *
     */
    protected void focusEditor() {
    }

    /**
     *
     */
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", justification = "Assume this is OK")
    public void switchToEditMode() {
        if (isEditMode()) {
            return;
        }
        if (!isEditable()) {
            return;
        }
        DEBUG("Switching to edit mode: " + toString());
        ensureEditorInitialized();
        viewMode = false;
        if (currentEditor != null) {
            try {
                currentEditor.closeEditor();
            } catch (Exception ex) {
                ERROR(ex);
            }
        }
        currentEditor = this;
        updateEditor();
        focusEditor();
        if (modeSwitchHandler != null) {
            modeSwitchHandler.onEditorActivated(this);
        }
        if (globalModeSwitchHandler != null) {
            globalModeSwitchHandler.onEditorActivated(this);
        }
        onSwitchToEditModeCompleted();
    }

    /**
     *
     */
    protected void onSwitchToEditModeCompleted() {
    }

    /**
     *
     * @return
     */
    public abstract boolean isEditable();

    /**
     *
     * @return
     */
    public String getTooltip() {
        return null;
    }

    /**
     *
     */
    public void switchToViewMode() {
        switchToViewMode(isAttached());
    }

    /**
     *
     * @param update
     */
    public void switchToViewMode(boolean update) {
        if (isViewMode()) {
            return;
        }
        DEBUG("Switching to view mode: " + toString());
        viewMode = true;
        onEditorClose();
        if (currentEditor == this) {
            currentEditor = null;
        }
        if (modeSwitchHandler != null) {
            modeSwitchHandler.onViewerActivated(this);
        }
        if (globalModeSwitchHandler != null) {
            globalModeSwitchHandler.onViewerActivated(this);
        }
        if (update) {
            update();
        }
    }

    /**
     *
     * @return
     */
    public final boolean submitEditor() {
        return submitEditor(true);
    }

    /**
     *
     * @param switchToViewMode
     * @return
     */
    public final boolean submitEditor(boolean switchToViewMode) {
        if (!isEditMode()) {
            throw new RuntimeException("submitEditor() not allowed. Not in edit mode: " + toString());
        }
        try {
            onEditorSubmit();
        } catch (Exception ex) {
            ex.printStackTrace();
            setEditorError(getUserMessageStack(ex));
            return false;
        }
        setEditorError(null);
        if (switchToViewMode || !editorWrapper.isAttached()) {
            switchToViewMode();
            if (isAttached()) {
                updateAutoUpdateWidget();
            }
        }
        return true;
    }

    /**
     *
     */
    protected void updateAutoUpdateWidget() {
        Gwt.update(getRootWidget());
    }

    /**
     *
     */
    protected final void cancelEditor() {
        if (!isEditMode()) {
            throw new RuntimeException("cancelEditor() not allowed. Not in edit mode: " + toString());
        }
        switchToViewMode();
    }

    /**
     *
     */
    protected void closeEditor() {
        cancelEditor();
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        if (isEditMode()) {
            closeEditor();
        }
    }

    private void initializeViewer() {
        if (viewerInitialized) {
            throw new RuntimeException("Viewer already initialized: " + toString());
        }
        if (viewerInitializing) {
            throw new RuntimeException("Viewer already initializing: " + toString());
        }
        viewerInitializing = true;
        // GwtLogger.DEBUG("Initializing Viewer: " + toString());
        viewerWrapper = new FocusPanel();
        viewerWrapper.getElement().setId(getViewerId());
        viewerWrapper.setStyleName("AViewEditWidget-viewer");
        viewerWrapper.addClickHandler(new ViewerClickListener());
        viewerWrapper.setWidget(onViewerInitialization());
        viewerInitialized = true;
        viewerInitializing = false;
    }

    private void updateViewer() {
        if (viewerInitializing) {
            throw new RuntimeException("Viewer initializing. Don't call update() within onViewerInitailization(): "
                    + toString());
        }
        if (!viewerInitialized) {
            initializeViewer();
        }
        // GwtLogger.DEBUG("Updating viewer: " + toString());
        onViewerUpdate();
        if (isEditable()) {
            viewerWrapper.addStyleDependentName("editable");
        } else {
            viewerWrapper.removeStyleDependentName("editable");
        }
        masterWrapper.setWidget(viewerWrapper);
    }

    private void initializeEditor() {
        if (editorInitialized) {
            throw new RuntimeException("Editor already initialized: " + toString());
        }
        if (editorInitializing) {
            throw new RuntimeException("Editor already initializing: " + toString());
        }
        editorInitializing = true;
        // GwtLogger.DEBUG("Initializing Editor: " + toString());

        errorWrapper = new SimplePanel();

        editorWrapper = new FlowPanel();
        editorWrapper.setStyleName("AViewEditWidget-editor");
        editorWrapper.add(errorWrapper);
        Widget editor = onEditorInitialization();
        editor.getElement().setId(getEditroId());
        editorWrapper.add(editor);
        editorInitialized = true;
        editorInitializing = false;
    }

    /**
     *
     * @param text
     */
    protected void setEditorError(String text) {
        if (isBlank(text)) {
            errorWrapper.clear();
        } else {
            errorWrapper.setWidget(createDiv("AViewEditWidget-error", text));
        }
    }

    /**
     *
     * @param modeSwitchHandler
     */
    public void setModeSwitchHandler(ModeSwitchHandler modeSwitchHandler) {
        this.modeSwitchHandler = modeSwitchHandler;
    }

    /**
     *
     */
    protected final void ensureEditorInitialized() {
        if (editorInitializing) {
            throw new RuntimeException("Editor initializing. Don't call update() within onEditorInitailization(): "
                    + toString());
        }
        if (!editorInitialized) {
            initializeEditor();
        }
    }

    private void updateEditor() {
        initialize();
        masterWrapper.setWidget(editorWrapper);
        onEditorUpdate();
        getElement().scrollIntoView();
    }

    /**
     *
     * @return
     */
    public final boolean isViewMode() {
        return viewMode;
    }

    /**
     *
     * @return
     */
    public final boolean isEditMode() {
        return !viewMode;
    }

    /**
     *
     * @return
     */
    @Override
    public String getId() {
        return getSimpleName(getClass()).replace('$', '_');
    }

    /**
     *
     * @return
     */
    protected String getViewerId() {
        return "viewer_" + getId();
    }

    /**
     *
     * @return
     */
    protected String getEditroId() {
        return "editor_" + getId();
    }

    /**
     *
     * @return
     */
    public static AViewEditWidget getCurrentEditor() {
        return currentEditor;
    }

    /**
     *
     * @param globalModeSwitchHandler
     */
    public static void setGlobalModeSwitchHandler(ModeSwitchHandler globalModeSwitchHandler) {
        AViewEditWidget.globalModeSwitchHandler = globalModeSwitchHandler;
    }

    private class ViewerClickListener implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            if (isEditable()) {
                switchToEditMode();
            }
            event.stopPropagation();
        }

    }

    /**
     *
     */
    protected class SubmitEditorFocusHandler implements FocusHandler {

        /**
         *
         */
        public SubmitEditorFocusHandler() {
        }

        @Override
        public void onFocus(FocusEvent event) {
        }

    }

    /**
     *
     */
    public class CancelKeyHandler implements KeyDownHandler {

        @Override
        public void onKeyDown(KeyDownEvent event) {
            int keyCode = event.getNativeKeyCode();
            if (keyCode == KEY_ESCAPE) {
                cancelEditor();
            }
        }

    }

    /**
     *
     */
    public static interface ModeSwitchHandler {

        /**
         *
         * @param widget
         */
        void onViewerActivated(AViewEditWidget widget);

        /**
         *
         * @param widget
         */
        void onEditorActivated(AViewEditWidget widget);

    }

}
