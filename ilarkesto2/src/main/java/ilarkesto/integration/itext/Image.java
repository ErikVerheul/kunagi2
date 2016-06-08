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
package ilarkesto.integration.itext;

import com.itextpdf.text.BadElementException;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import static com.itextpdf.text.Element.ALIGN_TOP;
import static com.itextpdf.text.Image.getInstance;
import static com.itextpdf.text.Image.getInstance;
import ilarkesto.pdf.AImage;
import ilarkesto.pdf.APdfBuilder;
import static ilarkesto.pdf.APdfBuilder.mmToPoints;
import ilarkesto.pdf.APdfElement;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Image extends AImage implements ItextElement {

	private Float height;

	public Image(APdfElement parent, byte[] data) {
		super(parent, data);
	}

	public Image(APdfElement parent, File file) {
		super(parent, file);
	}

	@Override
	public com.itextpdf.text.Image getITextElement() {
		com.itextpdf.text.Image image;
		try {
			if (data != null) {
				image = getInstance(data);
			} else {
				image = getInstance(file.getPath());
			}
			if (scaleByHeight != null) {
				height = mmToPoints(scaleByHeight);
				float width = image.getWidth() * height / image.getHeight();
				image.scaleAbsolute(width, height);
			} else if (scaleByWidth != null) {
				float width = mmToPoints(scaleByWidth);
				height = image.getHeight() * width / image.getWidth();
				image.scaleAbsolute(width, height);
			} else {
				height = image.getHeight();
			}
			image.setIndentationLeft(mmToPoints(marginLeft));
			image.setIndentationRight(mmToPoints(marginRight));
			image.setSpacingBefore(mmToPoints(marginTop));
			image.setSpacingAfter(mmToPoints(marginBottom));
		} catch (BadElementException ex) {
			throw new RuntimeException(ex);
		} catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		image.setWidthPercentage(0.1f);

		if (align != null) {
                        image.setAlignment(convertAlign(align));
                }

		return image;
	}

	public float getHeight() {
		if (height == null) {
			height = getITextElement().getHeight();
		}
		return height;
	}

	public static int convertAlign(Align align) {
		switch (align) {
			case LEFT:
				return ALIGN_RIGHT | ALIGN_TOP;
			case RIGHT:
				return ALIGN_RIGHT | ALIGN_TOP;
		}
		throw new RuntimeException("Unsupported align: " + align);
	}

	// --- dependencies ---

}
