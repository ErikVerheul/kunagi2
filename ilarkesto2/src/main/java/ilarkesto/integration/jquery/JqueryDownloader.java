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
package ilarkesto.integration.jquery;

import ilarkesto.core.logging.Log;
import static ilarkesto.io.IO.downloadUrlToFile;
import java.io.File;

public class JqueryDownloader {

	private static final Log log = Log.get(JqueryDownloader.class);

	public static void installToDir(String version, File destinationDir) {
		download("jquery-" + version + ".min.js", destinationDir);
		download("jquery-" + version + ".js", destinationDir);
	}

	public static boolean isInstalled(String version, File destinationDir) {
		if (!new File(destinationDir.getPath() + "/jquery-" + version + ".min.js").exists()) {
                        return false;
                }
		return new File(destinationDir.getPath() + "/jquery-" + version + ".js").exists();
	}

	private static void download(String filename, File destinationDir) {
		String url = "http://code.jquery.com/" + filename;
		String file = destinationDir.getPath() + "/" + filename;
		log.info("Downloading", url, "to", file);
		downloadUrlToFile(url, file);
	}
}
