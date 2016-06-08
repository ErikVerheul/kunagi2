/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package ilarkesto.io;

import ilarkesto.core.logging.Log;
import static ilarkesto.io.IO.loadImage;
import static ilarkesto.io.IO.quadratizeAndLimitSize;
import static ilarkesto.io.IO.writeImage;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import static java.lang.Integer.parseInt;

public class ImageThumbFactory {

	private final Log log = Log.get(ImageThumbFactory.class);

	private final File thumbDir;

	public ImageThumbFactory(File thumbDir) {
		super();
		this.thumbDir = thumbDir;
	}

	public File getThumb(File imageFile, String folder, String scale) {
		return getThumb(imageFile, folder, parseInt(scale));
	}

	public File getThumb(File imageFile, String folder, int size) {
		File thumbFile = new File(thumbDir.getPath() + "/" + folder + "/" + size + "/" + imageFile.getName());
		if (thumbFile.exists() && thumbFile.lastModified() == imageFile.lastModified()) {
                        return thumbFile;
                }
		createThumb(imageFile, thumbFile, size);
		return thumbFile;
	}

	private void createThumb(File imageFile, File thumbFile, int size) {
		log.info("Creating thumb:", imageFile, "->", thumbFile);
		BufferedImage image = loadImage(imageFile);
		Image thumbImage = quadratizeAndLimitSize(image, size);
		writeImage(thumbImage, "JPG", thumbFile);
		if (!thumbFile.setLastModified(imageFile.lastModified())) {
                        log.warn("Unable to set lastmodified time to file " + thumbFile);
                }
	}
}
