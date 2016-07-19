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
package ilarkesto.base;

import ilarkesto.console.ColorsConsoleApp;
import java.awt.Color;
import static java.awt.Color.decode;
import static java.lang.String.format;

/**
 * A Class that provides utility methods for mixing colours.
 */
public class Colors {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		ColorsConsoleApp.main(args);
	}

    /**
     *
     * @param one
     * @param two
     * @param ratio
     * @return
     */
    public static Color blend(Color one, Color two, float ratio) {
		if (ratio < 0 || ratio > 1) {
                        throw new IllegalArgumentException("Color blend ratio r must be between 0 and 1.");
                }

		float r1 = ratio;
		float r2 = 1 - ratio;

		float[] rgb1 = new float[3];
		float[] rgb2 = new float[3];
		one.getColorComponents(rgb1);
		two.getColorComponents(rgb2);

		return new Color(rgb1[0] * r1 + rgb2[0] * r2, rgb1[1] * r1 + rgb2[1] * r2, rgb1[2] * r1 + rgb2[2] * r2);
	}

    /**
     *
     * @param one
     * @param two
     * @return
     */
    public static Color blend(Color one, Color two) {
		return blend(one, two, 0.5f);
	}

    /**
     *
     * @param hexOne
     * @param hexTwo
     * @param ratio
     * @return
     */
    public static String blend(String hexOne, String hexTwo, float ratio) {
		return toHex(blend(fromHex(hexOne), fromHex(hexTwo), ratio));
	}

    /**
     *
     * @param hexOne
     * @param hexTwo
     * @return
     */
    public static String blend(String hexOne, String hexTwo) {
		return blend(hexOne, hexTwo, 0.5f);
	}

    /**
     *
     * @param color
     * @return
     */
    public static Color darken(Color color) {
		float[] rgb = new float[3];
		color.getColorComponents(rgb);

		rgb[0] = (rgb[0] <= 0.1f) ? 0.0f : rgb[0] - 0.1f;
		rgb[1] = (rgb[1] <= 0.1f) ? 0.0f : rgb[1] - 0.1f;
		rgb[2] = (rgb[2] <= 0.1f) ? 0.0f : rgb[2] - 0.1f;

		return new Color(rgb[0], rgb[1], rgb[2]);
	}

    /**
     *
     * @param color
     * @return
     */
    public static Color lighten(Color color) {
		float[] rgb = new float[3];
		color.getColorComponents(rgb);

		rgb[0] = (rgb[0] >= 0.9f) ? 1.0f : rgb[0] + 0.1f;
		rgb[1] = (rgb[1] >= 0.9f) ? 1.0f : rgb[1] + 0.1f;
		rgb[2] = (rgb[2] >= 0.9f) ? 1.0f : rgb[2] + 0.1f;

		return new Color(rgb[0], rgb[1], rgb[2]);
	}

    /**
     *
     * @param hexColor
     * @return
     */
    public static String darken(String hexColor) {
		return toHex(darken(fromHex(hexColor)));
	}

    /**
     *
     * @param hexColor
     * @return
     */
    public static String lighten(String hexColor) {
		return toHex(lighten(fromHex(hexColor)));
	}

    /**
     *
     * @param hexColor
     * @return
     */
    public static Color fromHex(String hexColor) {
		try {
			return decode(hexColor);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("'" + hexColor
					+ "' is not a valid color. Use the format #RRGGBB where R, G and B are values 0-9 or A-F.");
		}
	}

    /**
     *
     * @param color
     * @return
     */
    public static String toHex(Color color) {
		return format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
	}

    /**
     *
     * @param hexColor
     * @return
     */
    public static boolean isHexColor(String hexColor) {
		return hexColor.matches("#[0-9A-F]{6}");
	}
}
