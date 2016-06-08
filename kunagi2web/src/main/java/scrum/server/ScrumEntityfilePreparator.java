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
package scrum.server;

import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;
import ilarkesto.persistence.EntityfilePreparator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class ScrumEntityfilePreparator implements EntityfilePreparator {

	private static final Log LOG = Log.get(ScrumEntityfilePreparator.class);

	@Override
	public void prepareClusterfile(File file, Class type, String alias) {}

	@Override
	public void prepareEntityfile(File file, Class type, String alias) {

		try {
			if ("_template_".equalsIgnoreCase(alias)) prepare_template_(file);
			if ("sprint".equalsIgnoreCase(alias)) prepareSprint(file);
			// if ("projectUserConfig".equalsIgnoreCase(alias)) prepareProjectUserConfig(file);
			// if ("change".equalsIgnoreCase(alias)) prepareChange(file);
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	private void prepareSprint(File file) throws IOException {
		boolean modified = false;

		Document doc;
		try {
			doc = new SAXBuilder().build(file);
		} catch (JDOMException ex) {
			throw new RuntimeException(ex);
		}
		Element eSprint = doc.getRootElement();

		Element eCompletedRequirementLabels = eSprint.getChild("completedRequirementLabels");
		if (eCompletedRequirementLabels != null) {
			eSprint.removeContent(eCompletedRequirementLabels);
			modified = true;
		}

		if (modified) save(doc, file);
	}

	private void prepareIssue(File file) throws IOException {
		boolean modified = false;

		Document doc;
		try {
			doc = new SAXBuilder().build(file);
		} catch (JDOMException ex) {
			throw new RuntimeException(ex);
		}
		Element root = doc.getRootElement();

		Element value = root.getChild("urgent");
		if (value != null) {
			value.setName("bug");
			modified = true;
		}

		if (modified) save(doc, file);
	}

	private void prepareProjectUserConfig(File file) throws IOException {
		boolean modified = false;

		Document doc;
		try {
			doc = new SAXBuilder().build(file);
		} catch (JDOMException ex) {
			throw new RuntimeException(ex);
		}
		Element root = doc.getRootElement();

		Element ids = root.getChild("selectedEntitysIds");
		if (ids != null) {
			root.removeContent(ids);
			modified = true;
		}

		if (modified) save(doc, file);
	}

	/**
	 * use as template, don't modify
	 */
	private void prepare_template_(File file) throws IOException {
		if (true) throw new RuntimeException("remove this line");
		boolean modified = false;

		Document doc;
		try {
			doc = new SAXBuilder().build(file);
		} catch (JDOMException ex) {
			throw new RuntimeException(ex);
		}
		Element root = doc.getRootElement();

		Element principalDescription = root.getChild("principalDescription");
		if (principalDescription != null) {
			root.removeContent(principalDescription);
			modified = true;
		}

		if (modified) save(doc, file);
	}

	private boolean removeChild(String name, Element root) {
		Element e = root.getChild(name);
		if (e == null) return false;
		root.removeChild(name);
		return true;
	}

	private void save(Document doc, File file) throws IOException {
		LOG.info("Saving prepared entity file:", file);
		backup(file);
            try (OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                XMLOutputter outputter = new XMLOutputter();
                outputter.getFormat().setEncoding(IO.UTF_8);
                outputter.output(doc, out);
            }
	}

	private void backup(File src) {
		if (src.isDirectory()) throw new RuntimeException("sorry, backing up directories is not implemented yet.");

		File dst = new File(backupDir + "/" + src.getName());
		for (int i = 2; dst.exists(); i++) {
			dst = new File(backupDir + "/" + i + "_" + src.getName());
		}

		LOG.debug("Backing up", src.getPath(), "to", dst.getPath());

		IO.copyFile(src.getPath(), dst.getPath());
	}

	// --- dependencies ---

	private String backupDir;

	public void setBackupDir(String backupDir) {
		this.backupDir = backupDir;
	}
}