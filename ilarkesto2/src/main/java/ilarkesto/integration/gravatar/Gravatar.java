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
package ilarkesto.integration.gravatar;

import static ilarkesto.core.base.Str.isBlank;
import ilarkesto.core.logging.Log;
import static ilarkesto.io.IO.downloadUrlToString;
import ilarkesto.json.JsonObject;
import java.io.UnsupportedEncodingException;
import static java.lang.Integer.toHexString;
import static java.lang.System.out;
import java.security.MessageDigest;
import static java.security.MessageDigest.getInstance;
import java.security.NoSuchAlgorithmException;

public class Gravatar {

	private static final Log log = Log.get(Gravatar.class);

	public static void main(String[] args) {
		out.println(createAvatarUrl("wi@koczewski.de"));
		out.println(loadProfile("wi@koczewski.de"));
	}

	public static final Profile loadProfile(String email) {
		String url = createJsonProfileUrl(email);
		if (isBlank(url)) {
                        return null;
                }
		log.debug("Loading Gravatar profile for", email, "->", email);
		String json = downloadUrlToString(url);
		if (isBlank(json)) {
                        return null;
                }
		return new Profile(new JsonObject(json));
	}

	public static final String createJsonProfileUrl(String email) {
		String url = createProfileUrl(email);
		if (isBlank(url)) {
                        return null;
                }
		return url + ".json";
	}

	public static final String createProfileUrl(String email) {
		if (isBlank(email)) {
                        return null;
                }
		return "https://secure.gravatar.com/" + createHash(email);
	}

	public static final String createAvatarUrl(String email) {
		if (isBlank(email)) {
                        return null;
                }
		return "https://secure.gravatar.com/avatar/" + createHash(email);
	}

	public static final String createHash(String email) {
		if (isBlank(email)) {
                        return null;
                }
		return MD5Util.md5Hex(email.trim().toLowerCase());
	}

	static class MD5Util {

		public static String hex(byte[] array) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < array.length; ++i) {
				sb.append(toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		}

		public static String md5Hex(String message) {
			try {
				MessageDigest md = getInstance("MD5");
				return hex(md.digest(message.getBytes("CP1252")));
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

}
