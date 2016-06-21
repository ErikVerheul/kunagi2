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
package scrum.client.collaboration;

import ilarkesto.gwt.client.CodemirrorEditorWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.Initializer;
import ilarkesto.gwt.client.RichtextFormater;
import ilarkesto.gwt.client.ToolbarWidget;
import ilarkesto.gwt.client.editor.RichtextEditorWidget;
import scrum.client.files.File;
import scrum.client.files.Uploader;
import scrum.client.img.Img;
import scrum.client.wiki.ScrumHtmlContext;
import scrum.client.wiki.WikiModel;
import scrum.client.wiki.WikiParser;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;

public class Wiki extends GWiki implements RichtextFormater {

	@Override
	public void initialize() {
		Gwt.setDefaultRichtextFormater(this);
		Gwt.setRichtextEditorEditInitializer(new RichtextEditorEditInitializer());
		Gwt.setDefaultRichtextSyntaxInfo(WikiParser.SYNTAX_INFO_HTML);
	}

	public String getTemplate(String name) {
		Wikipage page = project.getWikipage("template:" + name);
		return page == null ? null : page.getText();
	}

	@Override
	public String richtextToHtml(String text) {
		return toHtml(text);
	}

	public static String toHtml(String wiki) {
		if (wiki == null) return null;
		if (wiki.trim().length() == 0) return "";
		WikiParser parser = new WikiParser(wiki);
		WikiModel model = parser.parse(true);
		return model.toHtml(new ScrumHtmlContext());
	}

	public SuggestOracle createPagesSuggestOracle() {
		MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
		for (Wikipage page : project.getWikipages()) {
			oracle.add(page.getName());
		}
		return oracle;
	}

	class RichtextEditorEditInitializer implements Initializer<RichtextEditorWidget> {

		@Override
		public void initialize(final RichtextEditorWidget editor) {
			final ToolbarWidget toolbar = editor.getEditorToolbar();

			toolbar.insert(createToolbarButton(Img.bundle.toc().createImage(), "Table of contents", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					CodemirrorEditorWidget textArea = editor.getEditor();
					textArea.wrapSelection("TOC\n\n", "");
				}
			}), 0);

			toolbar.insert(createToolbarButton(Img.bundle.upload().createImage(), "Upload file", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					CodemirrorEditorWidget textArea = editor.getEditor();
					int topPosition = textArea.getAbsoluteTop() + 20;
					ReferenceInserter refInserter = new ReferenceInserter(textArea);
					uploader.showUploadDialog(topPosition, refInserter);
				}
			}), 0);

			toolbar.insert(createToolbarButton(Img.bundle.formatTextCode().createImage(), "Code", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					CodemirrorEditorWidget textArea = editor.getEditor();
					textArea.wrapSelection("<code>", "</code>");
				}
			}), 0);

			toolbar.insert(createToolbarButton(Img.bundle.image().createImage(), "Image", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					CodemirrorEditorWidget textArea = editor.getEditor();
					String selected = textArea.getSelectedText();
					if (selected != null && (selected.startsWith("http") || selected.startsWith("www."))) {
						textArea.wrapSelection("[[Image:", "|thumb]]");
					} else {
						textArea.wrapSelection("[[Image:http://", "|thumb]]");
					}
				}
			}), 0);

			toolbar.insert(createToolbarButton(Img.bundle.table().createImage(), "Table", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					CodemirrorEditorWidget textArea = editor.getEditor();
					textArea.wrapSelection("\n{|\n! Header 1\n! Header 2\n|-\n| ", "\n| \n|}\n");
				}
			}), 0);

			toolbar.insert(createToolbarButton(Img.bundle.hyperlink().createImage(), "Hyperlink", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					CodemirrorEditorWidget textArea = editor.getEditor();
					String selected = textArea.getSelectedText();
					if (selected != null && (selected.startsWith("http") || selected.startsWith("www."))) {
						textArea.wrapSelection("[", "]");
					} else {
						textArea.wrapSelection("[http:// ", "]");
					}
				}
			}), 0);

			toolbar.insert(
				createToolbarButton(Img.bundle.enumlist().createImage(), "Numbered list", new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						CodemirrorEditorWidget textArea = editor.getEditor();
						textArea.wrapSelection("\n# ", "\n# ");
					}
				}), 0);

			toolbar.insert(
				createToolbarButton(Img.bundle.itemlist().createImage(), "Bulleted list", new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						CodemirrorEditorWidget textArea = editor.getEditor();
						textArea.wrapSelection("\n* ", "\n* ");
					}
				}), 0);

			toolbar.insert(createToolbarButton(Img.bundle.h2().createImage(), "Heading 2", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					CodemirrorEditorWidget textArea = editor.getEditor();
					textArea.wrapLine("== ", " ==\n");
				}
			}), 0);

			toolbar.insert(createToolbarButton(Img.bundle.h1().createImage(), "Heading 1", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					CodemirrorEditorWidget textArea = editor.getEditor();
					textArea.wrapLine("= ", " =\n");
				}
			}), 0);

			toolbar.insert(createToolbarButton(Img.bundle.formatTextBold().createImage(), "Bold", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					CodemirrorEditorWidget textArea = editor.getEditor();
					textArea.wrapSelection("'''", "'''");
				}
			}), 0);

			toolbar.insert(
				createToolbarButton(Img.bundle.formatTextItalic().createImage(), "Italic", new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						CodemirrorEditorWidget textArea = editor.getEditor();
						textArea.wrapSelection("''", "''");
					}
				}), 0);

		}
	}

	protected final Widget createToolbarButton(Image icon, String tooltip, ClickHandler clickHandler) {
		PushButton button = new PushButton(icon, clickHandler);
		button.setTitle(tooltip);
		return button;
	}

	private static class ReferenceInserter implements Uploader.UploadedFileHandler {

		private CodemirrorEditorWidget textArea;

		public ReferenceInserter(CodemirrorEditorWidget textArea) {
			super();
			this.textArea = textArea;
		}

		@Override
		public void onFileUploaded(File file) {
			textArea.wrapSelection(file.getReference() + " ", "");
		}

	}

}
