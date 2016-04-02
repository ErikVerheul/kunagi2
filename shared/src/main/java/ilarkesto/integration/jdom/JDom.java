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
package ilarkesto.integration.jdom;

import ilarkesto.core.logging.Log;
import static ilarkesto.io.IO.close;
import static ilarkesto.io.IO.closeQuiet;
import static ilarkesto.io.IO.createDirectory;
import static ilarkesto.io.IO.openUrlInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import static java.lang.System.out;
import java.net.MalformedURLException;
import java.net.URL;
import static java.util.Collections.emptyList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class JDom {

	public static void main(String[] args) {
		Document doc = createDocument(new File("test-input/email.xml"));
		out.println(doc);
	}

	private static final Log log = Log.get(JDom.class);

	public static final EntityResolver DUMMY_ENTITY_RESOLVER = new DummyEntityResolver();

	private JDom() {}

	public static List<Element> getChildren(Element parent, String name) {
		if (parent == null) {
                        return emptyList();
                }
		return parent.getChildren(name, null);
	}

	public static String getChildText(Element parent, String name) {
		Element child = getChild(parent, name);
		return child == null ? null : child.getText();
	}

	public static Element getChild(Element parent, String name) {
		Namespace ns = null;

		int idx = name.indexOf(':');
		if (idx > 0) {
			String prefix = name.substring(0, idx);
			name = name.substring(idx + 1);
			ns = parent.getNamespace(prefix);
		}

		return parent.getChild(name, ns);
	}

	public static Element getChildByAttribute(Element parent, String name, String attributeName, String attributeValue) {
		for (Element child : getChildren(parent, name)) {
			if (attributeValue.equals(child.getAttributeValue(attributeName))) {
                                return child;
                        }
		}
		return null;
	}

	public static Element getChild(Document doc, String name) {
		return getChild(doc.getRootElement(), name);
	}

	public static String getChildAttributeValue(Element parent, String childName, String attributeName) {
		Element child = getChild(parent, childName);
		return child == null ? null : child.getAttributeValue(attributeName);
	}

	public static String getChildAttributeValue(Document doc, String childName, String attributeName) {
		return getChildAttributeValue(doc.getRootElement(), childName, attributeName);
	}

	public static Document createDocument(File file) {
		try {
			return createDocumentFromUrl(file.toURI().toURL().toString());
		} catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Document createDocument(String xmlData) {
		SAXBuilder builder = createSaxBuilder();
		try {
			return builder.build(new StringReader(xmlData));
		} catch (JDOMException | IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Document createDocumentFromStream(InputStream in) {
		SAXBuilder builder = createSaxBuilder();
		try {
			return builder.build(in);
		} catch (Exception ex) {
			throw new RuntimeException("Loading XML from InputStream failed.", ex);
		}
	}

	public static Document createDocumentFromUrl(final String url) {
		log.debug("Loading from URL:", url);
		SAXBuilder builder = createSaxBuilder();
		try {
			return builder.build(new URL(url));
		} catch (Exception ex) {
			throw new RuntimeException("Loading XML from URL failed: " + url, ex);
		}
	}

	private static SAXBuilder createSaxBuilder() {
		SAXBuilder builder = new SAXBuilder(false);
		builder.setExpandEntities(false);
		builder.setValidation(false);
		builder.setEntityResolver(DUMMY_ENTITY_RESOLVER);
		return builder;
	}

	public static Document createDocumentFromUrl(String url, String username, String password) {
		log.debug("Downloading:", url);
		SAXBuilder builder = createSaxBuilder();
		try {
			BufferedInputStream is = new BufferedInputStream(openUrlInputStream(url, username, password));
			Document doc = builder.build(is);
			closeQuiet(is);
			return doc;
		} catch (Exception ex) {
			throw new RuntimeException("Loading XML from URL failed: " + url, ex);
		}
	}

	public static Element addTextElement(Element parent, String name, Object text) {
		Element element = addElement(parent, name);
		return setText(element, text);
	}

	public static Element setText(Element element, Object text) {
		element.setText(text == null ? null : text.toString());
		return element;
	}

	public static Element addElement(Element parent, String name) {
		Element e = new Element(name);
		parent.addContent(e);
		return e;
	}

	public static void save(Element root, File file, String encoding) {
		Document doc = new Document(root);
		save(doc, file, encoding);
	}

	public static void save(Document doc, File file, String encoding) {
		createDirectory(file.getParentFile());
		Writer out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
		} catch (UnsupportedEncodingException | FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		XMLOutputter outputter = new XMLOutputter();
		outputter.getFormat().setEncoding(encoding);
		try {
			outputter.output(doc, out);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		close(out);
	}

	public static void write(Document doc, OutputStream out, String encoding) {
		XMLOutputter outputter = new XMLOutputter();
		outputter.getFormat().setEncoding(encoding);
		try {
			outputter.output(doc, out);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		close(out);
	}

	public static class DummyEntityResolver implements EntityResolver {

		@Override
		public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
			return new InputSource(new StringReader(""));
		}

	}
}
