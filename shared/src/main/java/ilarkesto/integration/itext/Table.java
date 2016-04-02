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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import ilarkesto.pdf.ACell;
import ilarkesto.pdf.APdfElement;
import ilarkesto.pdf.ARow;
import ilarkesto.pdf.ATable;
import ilarkesto.pdf.FontStyle;
import java.util.ArrayList;
import java.util.List;

public class Table extends ATable implements ItextElement {

	private List<Cell> cells = new ArrayList<>();

	@Override
	public ACell cell() {
		Cell c = new Cell(this);

		Float defaultCellPadding = getDefaultCellPadding();
		if (defaultCellPadding != null) {
                        c.setPadding(defaultCellPadding);
                }

		FontStyle defaultFontStyle = getDefaultFontStyle();
		if (defaultFontStyle != null) {
                        c.setFontStyle(defaultFontStyle);
                }

		cells.add(c);
		return c;
	}

	@Override
	public Element getITextElement() {
		float[] cellWidths = getCellWidths();
		PdfPTable t = cellWidths == null ? new PdfPTable(getColumnCount()) : new PdfPTable(cellWidths);

		t.setHorizontalAlignment(ALIGN_LEFT);

		Float width = getWidth();
		if (width != null) {
                        t.setWidthPercentage(width);
                }

		for (Cell cell : cells) {
			t.addCell((PdfPCell) cell.getITextElement());
		}

		return t;
	}

	@Override
	public ATable createCellBorders(BaseColor color, float width) {
		float[] cellWidths = getCellWidths();
		int cols = cellWidths == null ? getColumnCount() : cellWidths.length;
		int col = 0;
		int row = 0;
		for (Cell cell : cells) {
			if (row == 0) {
                                cell.setBorderTop(color, width);
                        }
			cell.setBorderRight(color, width);
			cell.setBorderBottom(color, width);
			if (col == 0) {
                                cell.setBorderLeft(color, width);
                        }
			col += cell.getColspan();
			if (col >= cols) {
				col = 0;
				row++;
			}
		}
		return this;
	}

	@Override
	public ARow row() {
		ARow row = new ARow(this);

		FontStyle defaultFontStyle = getDefaultFontStyle();
		if (defaultFontStyle != null) {
                        row.setDefaultFontStyle(defaultFontStyle);
                }

		return row;
	}

	@Override
	public ARow row(Object... cellTexts) {
		ARow row = row();
		for (Object text : cellTexts) {
                        row.cell(text);
                }
		return row;
	}

	// --- dependencies ---

	public Table(APdfElement parent) {
		super(parent);
	}

}
