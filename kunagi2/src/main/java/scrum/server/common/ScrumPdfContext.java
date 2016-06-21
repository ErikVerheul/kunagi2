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
package scrum.server.common;

import ilarkesto.io.IO;
import ilarkesto.pdf.AImage;
import ilarkesto.pdf.AParagraph;
import scrum.client.wiki.Image;
import scrum.server.files.File;
import scrum.server.project.Project;

public class ScrumPdfContext implements PdfContext {

	private Project project;

	public ScrumPdfContext(Project project) {
		super();
		this.project = project;
	}

	@Override
	public AImage appendImage(AParagraph p, Image wikiImage) {
		if (wikiImage.isExternal()) {
			byte[] data = IO.downloadUrl(wikiImage.getReference(), null, null);
			return p.image(data);
		}

		File file = project == null ? null : project.getFileByReference(wikiImage.getReference());
		if (file == null) return null;
		java.io.File javaFile = file.getJavaFile();
		return p.image(javaFile);
	}

	@Override
	public Project getProject() {
		return project;
	}

}
