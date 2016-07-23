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
package scrum.client.files;

import ilarkesto.core.base.KunagiProperties;
import java.util.Comparator;
import scrum.client.ScrumGwt;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;

public class File extends GFile implements ReferenceSupport, LabelSupport {

	public static final String REFERENCE_PREFIX = "fle";

	public File(KunagiProperties data) {
		super(data);
	}

	public boolean isImage() {
		String name = getFilename().toLowerCase();
		return name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".jpg");
	}

	@Override
	public String getReference() {
		return REFERENCE_PREFIX + getNumber();
	}

	@Override
	public String toHtml() {
		return ScrumGwt.toHtml(this, getLabel());
	}

	@Override
	public String toString() {
		return getFilename();
	}

	public static final Comparator<File> UPLOAD_TIME_COMPARATOR = new Comparator<File>() {

		@Override
		public int compare(File a, File b) {
			return a.getUploadTime().compareTo(b.getUploadTime());
		}
	};

	public static final Comparator<File> REVERSE_UPLOAD_TIME_COMPARATOR = new Comparator<File>() {

		@Override
		public int compare(File a, File b) {
			return UPLOAD_TIME_COMPARATOR.compare(b, a);
		}
	};

}