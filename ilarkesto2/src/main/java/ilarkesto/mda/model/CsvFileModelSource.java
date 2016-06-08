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
package ilarkesto.mda.model;

import ilarkesto.core.logging.Log;
import ilarkesto.io.CsvParser;
import ilarkesto.io.CsvWriter;
import static ilarkesto.io.IO.UTF_8;
import static ilarkesto.io.IO.writeFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import static java.util.Arrays.asList;
import java.util.List;

public class CsvFileModelSource implements ModelSource {

	private static final Log log = Log.get(CsvFileModelSource.class);

	private final String charset = UTF_8;
	private final File file;

	public CsvFileModelSource(File file) {
		super();
		this.file = file;
	}

	@Override
	public void save(Model model) {
		StringWriter sw = new StringWriter();
		CsvWriter out = new CsvWriter(sw);
		out.writeHeaders(asList("id", "parentId", "type", "value"));
		writeNode(model.getRoot(), out);

		log.info("Writing file:", file.getPath());
		writeFile(file, sw.toString(), charset);
	}

	private void writeNode(Node node, CsvWriter out) {
		if (node.isTransient()) {
                        return;
                }
		out.writeField(node.getId());
		out.writeField(node.getParentId());
		out.writeField(node.getType());
		out.writeField(node.getValue());
		out.closeRecord();

		for (Node child : node.getChildren()) {
			writeNode(child, out);
		}
	}

	@Override
	public void load(Model model) {
		model.clear();

		log.info("Loading file:", file.getPath());

		CsvParser parser;
		try {
			parser = new CsvParser(file, charset, true);
		} catch (FileNotFoundException | UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}

		List<String> record = parser.nextRecord();
		if (record == null) {
                        throw new RuntimeException("Illegal model file format");
                }
		if (!"id".equals(record.get(0))) {
                        throw new RuntimeException("Illegal model file format");
                }
		if (!"parentId".equals(record.get(1))) {
                        throw new RuntimeException("Illegal model file format");
                }
		if (!"type".equals(record.get(2))) {
                        throw new RuntimeException("Illegal model file format");
                }
		if (!"value".equals(record.get(3))) {
                        throw new RuntimeException("Illegal model file format");
                }

		while ((record = parser.nextRecord()) != null) {
			String id = record.get(0);
			String parentId = record.get(1);
			String type = record.get(2);
			String value = record.get(3);
			model.addNode(id, parentId, type, value);
		}

	}

}
