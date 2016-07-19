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
package ilarkesto.pdf;

import com.itextpdf.text.BaseColor;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 *
 * @author erik
 */
public abstract class ATable extends APdfElement {

	private Float width = 100f;
	private float[] cellWidths;
	private int columnCount;

	private Float defaultCellPadding;
	private FontStyle defaultFontStyle;

    /**
     *
     * @return
     */
    public abstract ACell cell();

    /**
     *
     * @return
     */
    public abstract ARow row();

    /**
     *
     * @param cellTexts
     * @return
     */
    public abstract ARow row(Object... cellTexts);

    /**
     *
     * @param color
     * @param width
     * @return
     */
    public abstract ATable createCellBorders(BaseColor color, float width);

	/**
	 * Width in percent.
     * @return 
	 */
	public ATable setWidth(Float width) {
		this.width = width;
		return this;
	}

    /**
     *
     * @return
     */
    public Float getWidth() {
		return width;
	}

    /**
     *
     * @return
     */
    @SuppressWarnings("EI_EXPOSE_REP")
	public float[] getCellWidths() {
		return cellWidths;
	}

    /**
     *
     * @param cellWidths
     * @return
     */
    public ATable setCellWidths(float... cellWidths) {
		this.cellWidths = cellWidths;
		setColumnCount(cellWidths.length);
		return this;
	}

    /**
     *
     * @return
     */
    public int getColumnCount() {
		return columnCount;
	}

    /**
     *
     * @param columnCount
     * @return
     */
    public ATable setColumnCount(int columnCount) {
		this.columnCount = columnCount;
		return this;
	}

    /**
     *
     * @param defaultCellPadding
     * @return
     */
    public ATable setDefaultCellPadding(Float defaultCellPadding) {
		this.defaultCellPadding = defaultCellPadding;
		return this;
	}

    /**
     *
     * @return
     */
    public Float getDefaultCellPadding() {
		return defaultCellPadding;
	}

    /**
     *
     * @param defaultFontStyle
     * @return
     */
    public ATable setDefaultFontStyle(FontStyle defaultFontStyle) {
		this.defaultFontStyle = defaultFontStyle;
		return this;
	}

    /**
     *
     * @return
     */
    public FontStyle getDefaultFontStyle() {
		return defaultFontStyle;
	}

	// --- helper ---

    /**
     *
     * @param text
     * @return
     */
    
	public ACell cell(Object text) {
		ACell cell = cell();
		if (text != null) {
                        cell.paragraph().text(text);
                }
		return cell;
	}

    /**
     *
     * @param text
     * @param fontStyle
     * @return
     */
    public ACell cell(Object text, FontStyle fontStyle) {
		ACell cell = cell();
		if (text != null) {
                        cell.paragraph().text(text, fontStyle);
                }
		return cell;
	}

	// --- dependencies ---

    /**
     *
     * @param parent
     */
    
	public ATable(APdfElement parent) {
		super(parent);
	}

}
