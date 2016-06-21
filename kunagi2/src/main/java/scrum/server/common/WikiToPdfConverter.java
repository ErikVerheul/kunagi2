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
package scrum.server.common;

import com.itextpdf.text.BaseColor;
import ilarkesto.core.logging.Log;
import ilarkesto.pdf.ACell;
import ilarkesto.pdf.AImage;
import ilarkesto.pdf.AParagraph;
import ilarkesto.pdf.APdfContainerElement;
import ilarkesto.pdf.ARow;
import ilarkesto.pdf.ATable;
import ilarkesto.pdf.FontStyle;

import java.awt.Color;

import scrum.client.wiki.AWikiElement;
import scrum.client.wiki.Code;
import scrum.client.wiki.EntityReference;
import scrum.client.wiki.Header;
import scrum.client.wiki.Highlight;
import scrum.client.wiki.Image;
import scrum.client.wiki.ItemList;
import scrum.client.wiki.LineBreak;
import scrum.client.wiki.Link;
import scrum.client.wiki.Paragraph;
import scrum.client.wiki.Pre;
import scrum.client.wiki.Table;
import scrum.client.wiki.TableCell;
import scrum.client.wiki.TableRow;
import scrum.client.wiki.Text;
import scrum.client.wiki.Toc;
import scrum.client.wiki.WikiModel;
import scrum.client.wiki.WikiParser;

public class WikiToPdfConverter extends APdfCreator {

	private static final Log log = Log.get(WikiToPdfConverter.class);

	private WikiModel model;
	private PdfContext pdfContext;

	private boolean firstParagraph = true;

	public WikiToPdfConverter(WikiModel model, PdfContext pdfContext) {
		super(pdfContext.getProject());
		this.model = model;
		this.pdfContext = pdfContext;
	}

	@Override
	protected void build(APdfContainerElement parent) {
		for (AWikiElement element : model.getElements())
			processElement(element, parent);
	}

	private void processElement(AWikiElement element, APdfContainerElement parent) {
		if (firstParagraph) {
			firstParagraph = false;
		} else {
			parent.nl(defaultFont);
		}
		if (element instanceof Paragraph) {
			processParagraph((Paragraph) element, parent);
			return;
		}
		if (element instanceof Header) {
			processHeader((Header) element, parent);
			return;
		}
		if (element instanceof Pre) {
			processPre((Pre) element, parent);
			return;
		}
		if (element instanceof ItemList) {
			processItemList((ItemList) element, parent);
			return;
		}
		if (element instanceof Table) {
			processTable((Table) element, parent);
			return;
		}
		if (element instanceof Toc) {
			processToc((Toc) element, parent);
			return;
		}
		throw new RuntimeException("Unsupported Wiki-Element: " + element.getClass().getName());
	}

	private void processToc(Toc element, APdfContainerElement parent) {
		// TODO?
	}

	private void processTable(Table wikiTable, APdfContainerElement parent) {
		ATable pdfTable = parent.table(wikiTable.getColumnCount());
		pdfTable.setWidth(null);
		pdfTable.setDefaultCellPadding(2f);
		for (TableRow wikiRow : wikiTable.getRows()) {
			ARow pdfRow = pdfTable.row();
			for (TableCell wikiCell : wikiRow.getCells()) {
				ACell pdfCell = pdfRow.cell();
				if (wikiCell.isHeader()) {
					pdfCell.setFontStyle(tableHeaderFont);
					pdfCell.setBackgroundColor(tableHeaderBackground);
					processParagraph(wikiCell.getParagraph(), pdfCell, tableHeaderFont);
				} else {
					processParagraph(wikiCell.getParagraph(), pdfCell);
				}
			}
		}
		pdfTable.createCellBorders(BaseColor.GRAY, 0.2f);
	}

	private void processItemList(ItemList list, APdfContainerElement parent) {
		boolean ordered = list.isOrdered();
		ATable table = parent.table(1, ordered ? 30 : 45);
		int counter = 0;
		for (ItemList.Item item : list.getItems()) {
			ARow row = table.row();
			counter++;
			row.cell(ordered ? counter + "." : "-", defaultFont);
			ACell cell = row.cell();
			processParagraph(item.getParagraph(), cell);
			if (item.containsList()) {
				cell.paragraph().nl(new FontStyle(defaultFont).setSize(0.7f));
				processItemList(item.getList(), cell);
			}
		}
	}

	private void processPre(Pre pre, APdfContainerElement parent) {
		parent.paragraph().text(pre.getText(), codeFont);
	}

	private void processParagraph(Paragraph element, APdfContainerElement parent) {
		processParagraph(element, parent, defaultFont);
	}

	private void processParagraph(Paragraph element, APdfContainerElement parent, FontStyle font) {
		AParagraph paragraph = parent.paragraph();
		for (AWikiElement paragraphElement : element.getElements()) {
			processParagraphElement(paragraphElement, paragraph, font);
		}
	}

	private void processHeader(Header element, APdfContainerElement parent) {
		parent.paragraph().text(element.getText(), headerFonts[element.getDepth() - 1]).nl();
	}

	private void processParagraphElement(AWikiElement element, AParagraph parent, FontStyle fontStyle) {
		if (element instanceof Text) {
			processText((Text) element, parent, fontStyle);
			return;
		}
		if (element instanceof Highlight) {
			processHighlight((Highlight) element, parent, fontStyle);
			return;
		}
		if (element instanceof Code) {
			processCode((Code) element, parent);
			return;
		}
		if (element instanceof EntityReference) {
			processEntityReference((EntityReference) element, parent);
			return;
		}
		if (element instanceof Link) {
			processLink((Link) element, parent);
			return;
		}
		if (element instanceof Image) {
			processImage((Image) element, parent);
			return;
		}
		if (element instanceof LineBreak) {
			parent.nl();
			return;
		}
		throw new RuntimeException("Unsupported Wiki-Paragraph-Element: " + element.getClass().getName());
	}

	private void processImage(Image image, AParagraph parent) {
		AImage pdfImage;
		try {
			pdfImage = pdfContext.appendImage(parent, image);
		} catch (Exception ex) {
			log.warn("Image processing failed:", image, ex);
			return;
		}
		if (pdfImage == null) return;

		if (image.isThumb()) {
			if (image.isThumbAlignmentLeft()) {
				pdfImage.setAlignLeft();
			} else {
				pdfImage.setAlignRight();
			}
			pdfImage.setScaleByWidth(40f);
		}

	}

	private void processLink(Link link, AParagraph parent) {
		parent.text(link.getLabel(), defaultFont).text(" " + link.getHref(), referenceFont);
	}

	private void processEntityReference(EntityReference ref, AParagraph parent) {
		parent.text(ref.getLabel(), referenceFont);
	}

	private void processCode(Code code, AParagraph parent) {
		parent.text(code.getText(), codeFont);
	}

	private void processHighlight(Highlight highlight, AParagraph parent, FontStyle fontStyle) {
		FontStyle newFontStyle = new FontStyle(fontStyle);
		if (highlight.isEm()) newFontStyle.setItalic(true);
		if (highlight.isStrong()) newFontStyle.setBold(true);
		for (AWikiElement element : highlight.getElements()) {
			processParagraphElement(element, parent, newFontStyle);
		}
	}

	private void processText(Text text, AParagraph parent, FontStyle fontStyle) {
		parent.text(text.getText(), fontStyle);
	}

	public static void buildPdf(APdfContainerElement parent, String code, PdfContext pdfContext) {
		if (code == null) return;
		WikiParser parser = new WikiParser(code);
		WikiModel model = parser.parse(false);
		WikiToPdfConverter converter = new WikiToPdfConverter(model, pdfContext);
		converter.build(parent);
	}

	@Override
	protected String getFilename() {
		return "wiki";
	}

}
