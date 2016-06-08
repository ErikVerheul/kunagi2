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

public class JqueryMobileDownloader {

	private static final Log log = Log.get(JqueryMobileDownloader.class);

	public static String getStableVersion() {
		return "1.1.1";
	}

	public static String getPreviewVersion() {
		return "1.2.0-alpha.1";
	}

	public static String getCompatibleJqueryVersion(String jqueryMobileVersion) {
		return "1.7.1";
	}
	
	public static boolean isInstalled(String version, File destinationDir) {
		if (!new File(destinationDir.getPath() + "/jquery.mobile-" + version + ".min.js").exists()) {
                        return false;
                }
		if (!new File(destinationDir.getPath() + "/jquery.mobile-" + version + ".js").exists()) {
                        return false;
                }
		if (!new File(destinationDir.getPath() + "/jquery.mobile-" + version + ".min.css").exists()) {
                        return false;
                }
		if (!new File(destinationDir.getPath() + "/jquery.mobile-" + version + ".css").exists()) {
                        return false;
                }
		return new File(destinationDir.getPath() + "/images").exists();
	}
		
	public static void downloadPackage(String version, File destinationFile) {
		String url = "http://code.jquery.com/mobile/" + version + "/jquery.mobile-" + version + ".zip";
		log.info("Downloading JQM:", url);
		downloadUrlToFile(url, destinationFile.getAbsolutePath());
	}

}
