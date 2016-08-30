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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public abstract class AParagraph extends APdfElement {

    /**
     *
     */
    public enum Align {

        /**
         *
         */
        LEFT, 

        /**
         *
         */
        CENTER, 

        /**
         *
         */
        RIGHT
	}

	private FontStyle defaultFontStyle;

    /**
     *
     */
    protected float height;

    /**
     *
     */
    protected Align align;

    /**
     *
     */
    protected float spacingTop;

    /**
     *
     */
    protected float spacingBottom;

    /**
     *
     */
    protected List<AParagraphElement> elements = new ArrayList<>(1);

    /**
     *
     * @param file
     * @return
     */
    public abstract AImage image(File file);

    /**
     *
     * @param data
     * @return
     */
    public abstract AImage image(byte[] data);

    /**
     *
     * @param parent
     */
    public AParagraph(APdfElement parent) {
		super(parent);
	}

    /**
     *
     * @param spacingTop
     */
    public void setSpacingTop(float spacingTop) {
		this.spacingTop = spacingTop;
	}

    /**
     *
     * @param spacingBottom
     */
    public void setSpacingBottom(float spacingBottom) {
		this.spacingBottom = spacingBottom;
	}

    /**
     *
     * @return
     */
    protected List<AParagraphElement> getElements() {
		return elements;
	}

    /**
     *
     * @param text
     * @param fontStyle
     * @return
     */
    public AParagraph text(Object text, FontStyle fontStyle) {
		if (text == null) {
                        return this;
                }
		elements.add(new TextChunk(this).text(text).setFontStyle(fontStyle));
		return this;
	}

    /**
     *
     * @param text
     * @return
     */
    public AParagraph text(Object text) {
		return text(text, defaultFontStyle);
	}

    /**
     *
     * @return
     */
    public AParagraph nl() {
		text("\n");
		return this;
	}

    /**
     *
     * @param fontStyle
     * @return
     */
    public AParagraph nl(FontStyle fontStyle) {
		text("\n", fontStyle);
		return this;
	}

    /**
     *
     * @param height
     * @return
     */
    public AParagraph setHeight(float height) {
		this.height = height;
		return this;
	}

    /**
     *
     * @param align
     * @return
     */
    public AParagraph setAlign(Align align) {
		this.align = align;
		return this;
	}

    /**
     *
     * @param defaultFontStyle
     * @return
     */
    public AParagraph setDefaultFontStyle(FontStyle defaultFontStyle) {
		this.defaultFontStyle = defaultFontStyle;
		return this;
	}

	// --- dependencies ---

}
