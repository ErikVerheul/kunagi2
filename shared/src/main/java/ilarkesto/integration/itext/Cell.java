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
package ilarkesto.integration.itext;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import ilarkesto.pdf.ACell;
import ilarkesto.pdf.AImage;
import ilarkesto.pdf.AParagraph;
import static ilarkesto.pdf.APdfBuilder.mmToPoints;
import ilarkesto.pdf.APdfElement;
import ilarkesto.pdf.ATable;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Cell extends ACell implements ItextElement {

	private final Collection<ItextElement> elements = new ArrayList<>();

	public Cell(APdfElement parent) {
		super(parent);
	}

	@Override
	public AParagraph paragraph() {
		Paragraph p = new Paragraph(this);
		p.setDefaultFontStyle(getFontStyle());
		elements.add(p);
		return p;
	}

	@Override
	public AImage image(File file) {
		Image i = new Image(this, file);
		elements.add(i);
		return i;
	}

	@Override
	public ATable table(float... cellWidths) {
		Table t = new Table(this);
		t.setCellWidths(cellWidths);
		elements.add(t);
		return t;
	}

	@Override
	public ATable table(int columnCount) {
		Table t = new Table(this);
		t.setColumnCount(columnCount);
		elements.add(t);
		return t;
	}

	@Override
	public AImage image(byte[] data) {
		Image i = new Image(this, data);
		elements.add(i);
		return i;
	}

	@Override
	public Element getITextElement() {
		PdfPCell cell = new PdfPCell();

		cell.setBorderColorTop(getBorderTopColor());
		cell.setBorderColorBottom(getBorderBottomColor());
		cell.setBorderColorLeft(getBorderLeftColor());
		cell.setBorderColorRight(getBorderRightColor());
		cell.setBorderWidthTop(mmToPoints(getBorderTopWidth()));
		cell.setBorderWidthBottom(mmToPoints(getBorderBottomWidth()));
		cell.setBorderWidthLeft(mmToPoints(getBorderLeftWidth()));
		cell.setBorderWidthRight(mmToPoints(getBorderRightWidth()));
		cell.setUseBorderPadding(false);

		cell.setPadding(0);
		cell.setPaddingTop(mmToPoints(getPaddingTop()));
		cell.setPaddingBottom(mmToPoints(getPaddingBottom()));
		cell.setPaddingLeft(mmToPoints(getPaddingLeft()));
		cell.setPaddingRight(mmToPoints(getPaddingRight()));

		cell.setBackgroundColor(getBackgroundColor());
		cell.setExtraParagraphSpace(0);
		cell.setIndent(0);

		cell.setColspan(getColspan());
		for (ItextElement element : elements) {
                        cell.addElement(element.getITextElement());
                }
		return cell;
	}

	// --- dependencies ---

}
