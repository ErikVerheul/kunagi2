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

import ilarkesto.integration.itext.PdfBuilder;
import ilarkesto.pdf.AImage;
import ilarkesto.pdf.AParagraph;
import ilarkesto.testng.ATest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.testng.annotations.Test;
import scrum.client.wiki.Image;
import scrum.client.wiki.WikiModel;
import scrum.client.wiki.WikiParser;
import scrum.server.project.Project;

public class WikiToPdfConverterTest extends ATest {

	@Test
	public void test() throws IOException {
		StringBuilder sb = new StringBuilder();

		sb.append("= Section 1 =\n");
		sb.append("Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. Some Text in first paragraph. \n\n");
		sb.append("Some other text in second paragraph. Some other text in second paragraph. Some other text in second paragraph. Some other text in second paragraph. Some other text in second paragraph. Some other text in second paragraph. Some other text in second paragraph. Some other text in second paragraph. Some other text in second paragraph. Some other text in second paragraph. Some other text in second paragraph. Some other text in second paragraph. Some other text in second paragraph. \n\n");

		sb.append("== Section 1.1 ==\n");
		sb.append("Some Text.\n\n");

		sb.append("=== Section 1.1.1 ===\n");
		sb.append("Some text with ''italic'', '''bold''' and '''''both'''''. And then comes <code>crazy code</code>.\n\n");
		sb.append("Some reference to req23 and a link to [http://servisto.de/ servisto.de].\n\n");

		sb.append("==== Section 1.1.1.1 ====\n");
		sb.append(" preformated\n");
		sb.append("  code\n\n");

		sb.append("= Section 2 =\n");

		sb.append("== Section 2.1 ==\n");
		sb.append("* item 1\n");
		sb.append("* item 2\n");
		sb.append("* item 3\n\n");

		sb.append("== Section 2.2 ==\n");
		sb.append("# item 1\n");
		sb.append("# item 2\n");
		sb.append("# item 3\n\n");

		sb.append("== Section 2.3 ==\n");
		sb.append("# item 1\n");
		sb.append("# item 2\n");
		sb.append(" * subitem 1\n");
		sb.append(" * subitem 2\n");
		sb.append("  # subsubitem 1\n");
		sb.append("  # subsubitem 2\n");
		sb.append("  # subsubitem 3\n");
		sb.append(" * subitem 3\n");
		sb.append("# item 3\n\n");

		sb.append("= Section 3 =\n\n");
		sb.append("{|\n");
		sb.append("! Header 1\n");
		sb.append("! Header 2\n");
		sb.append("|-\n");
		sb.append("| first cell in upper left\n");
		sb.append("| ''second cell in upper right''\n");
		sb.append("|-\n");
		sb.append("| <code>third cell in lower left</code>\n");
		sb.append("| fourth cell in lower right\n");
		sb.append("|}\n\n");

		sb.append("= Section 4 =\n\n");
		sb.append("space burning\n\n");
		sb.append("space burning\n\n");
		sb.append("space burning\n\n");
		sb.append("space burning\n\n");
		sb.append("space burning\n\n");

		sb.append("= Section 5 =\n\n");
		sb.append("A paragraph with image inside. A paragraph with image inside. A paragraph with image inside. A paragraph with image inside. A paragraph with image inside. A paragraph with image inside. A paragraph with [[Image:http://www.google.com/intl/en_ALL/images/logo.gif]] image inside. A paragraph with image inside. A paragraph with image inside. A paragraph with image inside. A paragraph with image inside. A paragraph with image inside. A paragraph with image inside. \n\n");

		sb.append("= Section 6 =\n\n");
		sb.append("Paragraph before image.\n\n");
		sb.append("[[Image:http://www.google.com/intl/en_ALL/images/logo.gif]]\n\n");
		sb.append("Paragraph after image.\n\n");

		sb.append("= Section 7 =\n\n");
		sb.append("[[Image:http://www.google.com/intl/en_ALL/images/logo.gif|thumb]]");
		sb.append("A paragraph with thumb image on the rihgt. A paragraph with thumb image on the rihgt. A paragraph with thumb image on the rihgt. A paragraph with thumb image on the rihgt. A paragraph with thumb image on the rihgt. A paragraph with thumb image on the rihgt. A paragraph with thumb image on the rihgt. A paragraph with thumb image on the rihgt. A paragraph with thumb image on the rihgt. A paragraph with thumb image on the rihgt.\n\n");
		sb.append("A paragraph after the paragraph with thumg image on the rihgt. A paragraph after the paragraph with thumg image on the rihgt. A paragraph after the paragraph with thumg image on the rihgt. A paragraph after the paragraph with thumg image on the rihgt. A paragraph after the paragraph with thumg image on the rihgt. A paragraph after the paragraph with thumg image on the rihgt. A paragraph after the paragraph with thumg image on the rihgt. A paragraph after the paragraph with thumg image on the rihgt. A paragraph after the paragraph with thumg image on the rihgt. A paragraph after the paragraph with thumg image on the rihgt. \n\n");

		WikiParser parser = new WikiParser(sb.toString());
		WikiModel model = parser.parse(false);

            try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(OUTPUT_DIR + "/wiki.pdf"))) {
                PdfBuilder pdfBuilder = new PdfBuilder();
                
                WikiToPdfConverter converter = new WikiToPdfConverter(model, new TestPdfContext());
                converter.build(pdfBuilder);
                pdfBuilder.write(out);
            }
	}

	static class TestPdfContext implements PdfContext {

		@Override
		public AImage appendImage(AParagraph p, Image wikiImage) {
			AImage pdfImage = p.image(new File("src/artwork/kunagi-300x148.png"));
			return pdfImage;
		}

		@Override
		public Project getProject() {
			return null;
		}
	}
        
}
