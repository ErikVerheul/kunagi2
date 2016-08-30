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
package ilarkesto.pdf;

import com.itextpdf.text.BaseColor;

/**
 *
 *
 */
public abstract class ACell extends APdfContainerElement {

	private int colspan = 1;

	private BaseColor backgroundColor;

	private BaseColor borderTopColor;
	private BaseColor borderBottomColor;
	private BaseColor borderLeftColor;
	private BaseColor borderRightColor;

	private float borderTopWidth;
	private float borderBottomWidth;
	private float borderLeftWidth;
	private float borderRightWidth;

	private float paddingTop = 0;
	private float paddingBottom = 1;
	private float paddingLeft = 1;
	private float paddingRight = 1;

	private FontStyle fontStyle;

    /**
     *
     * @param parent
     */
    public ACell(APdfElement parent) {
		super(parent);
	}

    /**
     *
     * @param colspan
     * @return
     */
    public ACell setColspan(int colspan) {
		this.colspan = colspan;
		return this;
	}

    /**
     *
     * @param color
     * @param width
     * @return
     */
    public ACell setBorderTop(BaseColor color, float width) {
		borderTopColor = color;
		borderTopWidth = width;
		return this;
	}

    /**
     *
     * @param color
     * @param width
     * @return
     */
    public ACell setBorderBottom(BaseColor color, float width) {
		borderBottomColor = color;
		borderBottomWidth = width;
		return this;
	}

    /**
     *
     * @param color
     * @param width
     * @return
     */
    public ACell setBorderLeft(BaseColor color, float width) {
		borderLeftColor = color;
		borderLeftWidth = width;
		return this;
	}

    /**
     *
     * @param color
     * @param width
     * @return
     */
    public ACell setBorderRight(BaseColor color, float width) {
		borderRightColor = color;
		borderRightWidth = width;
		return this;
	}

    /**
     *
     * @param color
     * @param width
     * @return
     */
    public ACell setBorder(BaseColor color, float width) {
		setBorderTop(color, width);
		setBorderBottom(color, width);
		setBorderLeft(color, width);
		setBorderRight(color, width);
		return this;
	}

    /**
     *
     * @param fontStyle
     * @return
     */
    public ACell setFontStyle(FontStyle fontStyle) {
		this.fontStyle = fontStyle;
		return this;
	}

    /**
     *
     * @param backgroundColor
     * @return
     */
    public ACell setBackgroundColor(BaseColor backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

    /**
     *
     * @param paddingTop
     * @return
     */
    public ACell setPaddingTop(float paddingTop) {
		this.paddingTop = paddingTop;
		return this;
	}

    /**
     *
     * @param paddingRight
     * @return
     */
    public ACell setPaddingRight(float paddingRight) {
		this.paddingRight = paddingRight;
		return this;
	}

    /**
     *
     * @param paddingBottom
     * @return
     */
    public ACell setPaddingBottom(float paddingBottom) {
		this.paddingBottom = paddingBottom;
		return this;
	}

    /**
     *
     * @param paddingLeft
     * @return
     */
    public ACell setPaddingLeft(float paddingLeft) {
		this.paddingLeft = paddingLeft;
		return this;
	}

    /**
     *
     * @param padding
     * @return
     */
    public ACell setPadding(float padding) {
		setPaddingTop(padding);
		setPaddingRight(padding);
		setPaddingBottom(padding);
		setPaddingLeft(padding);
		return this;
	}

    /**
     *
     * @return
     */
    public int getColspan() {
		return colspan;
	}

    /**
     *
     * @return
     */
    public BaseColor getBackgroundColor() {
		return backgroundColor;
	}

    /**
     *
     * @return
     */
    public BaseColor getBorderBottomColor() {
		return borderBottomColor;
	}

    /**
     *
     * @return
     */
    public float getBorderBottomWidth() {
		return borderBottomWidth;
	}

    /**
     *
     * @return
     */
    public BaseColor getBorderLeftColor() {
		return borderLeftColor;
	}

    /**
     *
     * @return
     */
    public float getBorderLeftWidth() {
		return borderLeftWidth;
	}

    /**
     *
     * @return
     */
    public BaseColor getBorderRightColor() {
		return borderRightColor;
	}

    /**
     *
     * @return
     */
    public float getBorderRightWidth() {
		return borderRightWidth;
	}

    /**
     *
     * @return
     */
    public BaseColor getBorderTopColor() {
		return borderTopColor;
	}

    /**
     *
     * @return
     */
    public float getBorderTopWidth() {
		return borderTopWidth;
	}

    /**
     *
     * @return
     */
    public float getPaddingBottom() {
		return paddingBottom;
	}

    /**
     *
     * @return
     */
    public float getPaddingLeft() {
		return paddingLeft;
	}

    /**
     *
     * @return
     */
    public float getPaddingRight() {
		return paddingRight;
	}

    /**
     *
     * @return
     */
    public float getPaddingTop() {
		return paddingTop;
	}

    /**
     *
     * @return
     */
    public FontStyle getFontStyle() {
		return fontStyle;
	}

	// --- dependencies ---

}
