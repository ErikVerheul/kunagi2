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
package scrum.client.wiki;

import org.testng.Assert;
import org.testng.annotations.Test;

public class WikiTest extends Assert {

	@Test
	public void table() {
		assertEquals(toHtml("{|a|}"), "\n<table class='data-table'>\n<tr> <td>a</td> </tr>\n</table>\n");
		assertEquals(toHtml("{|\n|a\n|b\n|-\n|c\n|d\n\n|}"),
			"\n<table class='data-table'>\n<tr> <td>a</td>  <td>b</td> </tr>\n<tr> <td>c</td>  <td>d</td> </tr>\n</table>\n");
		assertEquals(toHtml("{|\n|a||b\n|-\n|c||d\n|}"),
			"\n<table class='data-table'>\n<tr> <td>a</td>  <td>b</td> </tr>\n<tr> <td>c</td>  <td>d</td> </tr>\n</table>\n");
	}

	@Test
	public void tableWithHeaders() {
		assertEquals(toHtml("{|\n!a\n!b\n|-\n|c\n|d\n\n|}"),
			"\n<table class='data-table'>\n<tr> <th>a</th>  <th>b</th> </tr>\n<tr> <td>c</td>  <td>d</td> </tr>\n</table>\n");
		assertEquals(toHtml("{|\n!a!!b\n|-\n!c||d\n|}"),
			"\n<table class='data-table'>\n<tr> <th>a</th>  <th>b</th> </tr>\n<tr> <th>c</th>  <td>d</td> </tr>\n</table>\n");
	}

	@Test
	public void localImg() {
		Assert.assertEquals(toHtml("[[Image:fle1]]"), "<a href='fle1.html'><img src=\"fle1\"></a>");
		Assert.assertEquals(toHtml("[[Image:fle1|thumb]]"),
			"<a href='fle1.html'><img src=\"fle1\" width=\"100px\" align=\"right\"></a>");
		Assert.assertEquals(toHtml("[[Image:fle1|thumb|left]]"),
			"<a href='fle1.html'><img src=\"fle1\" width=\"100px\" align=\"left\"></a>");
	}

	@Test
	public void externalImg() {
		Assert.assertEquals(toHtml("[[Image:http://servisto.de/image.png]]"),
			"<a href=\"http://servisto.de/image.png\" target=\"_blank\"><img src=\"http://servisto.de/image.png\"></a>");

	}

	@Test
	public void toc() {
		Assert.assertEquals(toHtml("__TOC__\n= 1 =\n== 1.1 ==\n= 2 ="), "<div class=\"toc\"><ul><li>"
				+ "<a href=\"#wiki_h1_1\">1</a></li><ul><li>" + "<a href=\"#wiki_h2_1_1\">1.1</a></li></ul><li>"
				+ "<a href=\"#wiki_h1_2\">2</a></li></ul></div>"
				+ "<a name=\"wiki_h1_1\" id=\"wiki_h1_1\"></a><h1>1</h1>"
				+ "<a name=\"wiki_h2_1_1\" id=\"wiki_h2_1_1\"></a><h2>1.1</h2>"
				+ "<a name=\"wiki_h1_2\" id=\"wiki_h1_2\"></a><h1>2</h1>");
	}

	@Test
	public void emphAndStrong() {
		Assert.assertEquals(toHtml("'''''emph and strong'''''"), "<strong><em>emph and strong</em></strong>");
		Assert.assertEquals(toHtml("this is '''strong'''"), "this is <strong>strong</strong>");
		Assert.assertEquals(toHtml("this is ''emph''"), "this is <em>emph</em>");
		Assert.assertEquals(toHtml("''''''''''"), "''''''''''");
		Assert.assertEquals(toHtml("'''''test"), "'''''test");
	}

	@Test
	public void entityReference() {
		Assert.assertTrue(toHtml("tsk15 is completed").contains("<a "));
		Assert.assertTrue(toHtml("[[Wiki]] is cool").contains("<a "));
		Assert.assertTrue(toHtml("[[Wiki|Custom Text]] is cool").contains(">Custom Text</a>"));
		Assert.assertTrue(toHtml("tsk15!").contains("<a "));
		Assert.assertTrue(toHtml("(tsk15!), :-)").contains("<a "));
		Assert.assertTrue(toHtml("tsk15:; :-)").contains("<a "));
	}

	@Test
	public void link() {
		Assert.assertEquals(toHtml("link www.servisto.de here"),
			"link <a href=\"http://www.servisto.de\" target=\"_blank\">servisto.de</a> here");
		Assert.assertEquals(toHtml("http://www.servisto.de"),
			"<a href=\"http://www.servisto.de\" target=\"_blank\">servisto.de</a>");
		Assert.assertEquals(toHtml("link [www.servisto.de Servisto] here"),
			"link <a href=\"http://www.servisto.de\" target=\"_blank\">Servisto</a> here");
	}

	@Test
	public void itemList() {
		Assert.assertEquals(toHtml("* item"), "<ul><li>item</li></ul>");
		Assert.assertEquals(toHtml("# item"), "<ol><li>item</li></ol>");
		Assert.assertEquals(toHtml("* item\nxyz"), "<ul><li>item<br>xyz</li></ul>");
		Assert.assertEquals(toHtml("* item 1\n* item 2"), "<ul><li>item 1</li><li>item 2</li></ul>");
	}

	@Test
	public void itemListWithValue() {
		Assert.assertEquals(toHtml("#=42 item"), "<ol><li value=\"42\">item</li></ol>");
		Assert.assertEquals(toHtml("#=42 item 1\n# item 2"), "<ol><li value=\"42\">item 1</li><li>item 2</li></ol>");
	}

	@Test
	public void itemListWithIdentifier() {
		Assert.assertEquals(toHtml("#=myid item 1\n# item 2\n\n#=myid item 3"),
			"<ol><li>item 1</li><li>item 2</li></ol><ol><li value=\"3\">item 3</li></ol>");
	}

	@Test
	public void nestedItemList() {
		Assert.assertEquals(toHtml("* item\n # subitem"), "<ul><li>item<ol><li>subitem</li></ol></li></ul>");
		Assert.assertEquals(
			toHtml("* topitem 1\n * subitem 1.1\n * subitem 1.2\n  * subsubitem 1.2.1\n * subitem 1.3\n* topitem 2"),
			"<ul><li>topitem 1<ul><li>subitem 1.1</li><li>subitem 1.2<ul><li>subsubitem 1.2.1</li></ul></li><li>subitem 1.3</li></ul></li><li>topitem 2</li></ul>");
		Assert.assertEquals(toHtml("* topitem 1\n  * subitem 1.1\n  * subitem 1.2\n    * subsubitem 1.2.1"),
			"<ul><li>topitem 1<ul><li>subitem 1.1</li><li>subitem 1.2<ul><li>subsubitem 1.2.1</li></ul></li></ul></li></ul>");
		Assert.assertEquals(toHtml("* item\n # subitem\n  * subsubitem"),
			"<ul><li>item<ol><li>subitem<ul><li>subsubitem</li></ul></li></ol></li></ul>");
	}

	@Test
	public void nestedItemListDirty() {
		Assert.assertEquals(toHtml("* topitem\n    * A\n * B\n    * C"),
			"<ul><li>topitem<ul><li>A</li><li>B</li><li>C</li></ul></li></ul>");
	}

	@Test
	public void preformated() {
		Assert.assertEquals(toHtml(" preformated"), "<pre class=\"codeBlock\"> preformated</pre>");
		Assert.assertEquals(toHtml("\tpreformated"), "<pre class=\"codeBlock\">    preformated</pre>");
		Assert.assertEquals(toHtml(" line 1\n line 2"), "<pre class=\"codeBlock\"> line 1\n line 2</pre>");
	}

	@Test
	public void nowiki() {
		Assert.assertEquals(toHtml("here is <nowiki>'''plain'''</nowiki>."), "here is '''plain'''.");
		Assert.assertEquals(toHtml("<nowiki>\n= header 1 =\n\nparagraph...</nowiki>"),
			"<p><br>= header 1 =<br><br>paragraph...</p>");
	}

	@Test
	public void code() {
		Assert.assertEquals(toHtml("some <code>code</code> is here."), "some <code>code</code> is here.");
		Assert.assertEquals(toHtml("here is <code>code</code>."), "here is <code>code</code>.");
		Assert.assertEquals(toHtml("here is <code>multiword code</code>."), "here is <code>multiword&nbsp;code</code>.");
		Assert.assertEquals(toHtml("here is <code>multiline\ncode</code>."),
			"<p>here is <code class=\"codeBlock\">multiline<br>code</code>.</p>");
		Assert.assertEquals(toHtml("simple line\n\n<code>code</code>"),
			"<p>simple line</p><p><code class=\"codeBlock\">code</code></p>");
		Assert.assertEquals(toHtml("<code>\n# enum\n# enum\n</code>"),
			"<p><code class=\"codeBlock\">#&nbsp;enum<br>#&nbsp;enum<br></code></p>");
		Assert.assertEquals(toHtml("<code>a\n\nb</code>"), "<p><code class=\"codeBlock\">a<br><br>b</code></p>");
		Assert.assertEquals(toHtml("<code>simple block</code>"),
			"<p><code class=\"codeBlock\">simple&nbsp;block</code></p>");
		Assert.assertEquals(
			toHtml("some <code>looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong code</code> is here."),
			"some <code class=\"codeBlock\">looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong&nbsp;code</code> is here.");
	}

	@Test
	public void paragraph() {
		Assert.assertEquals(toHtml("a b"), "a b");
		Assert.assertEquals(toHtml("a\nb"), "<p>a<br>b</p>");
		Assert.assertEquals(toHtml("a\r\nb"), "<p>a<br>b</p>");
		Assert.assertEquals(toHtml("a\n\nb"), "<p>a</p><p>b</p>");
		Assert.assertEquals(toHtml("a\n\n\n"), "<p>a</p>");
	}

	@Test
	public void header() {
		Assert.assertEquals(toHtml("= header ="),
			"<a name=\"wiki_h1_header\" id=\"wiki_h1_header\"></a><h1>header</h1>");
		Assert.assertEquals(toHtml("= ="), "= =");
		Assert.assertEquals(toHtml("= header = dummy"), "= header = dummy");

		Assert.assertEquals(toHtml("== header =="),
			"<a name=\"wiki_h2_header\" id=\"wiki_h2_header\"></a><h2>header</h2>");
		Assert.assertEquals(toHtml("== =="), "== ==");
		Assert.assertEquals(toHtml("== header == dummy"), "== header == dummy");

		Assert.assertEquals(toHtml("=== header ==="),
			"<a name=\"wiki_h3_header\" id=\"wiki_h3_header\"></a><h3>header</h3>");
		Assert.assertEquals(toHtml("==== header ===="),
			"<a name=\"wiki_h4_header\" id=\"wiki_h4_header\"></a><h4>header</h4>");
	}

	@Test
	public void specialChars() {
		Assert.assertEquals(toHtml("ü ä ß"), "ü ä ß");
		Assert.assertEquals(toHtml("& #"), "&amp; #");
		Assert.assertEquals(toHtml("< >"), "&lt; &gt;");
	}

	@Test
	public void simple() {
		Assert.assertEquals(toHtml("hello world"), "hello world");
	}

	@Test
	public void complete() {
		String html = toHtml("= header 1 =\nmy first paragraph\nstill first\n\nsecond paragraph\n\n\n\nthird paragraph\n\n== header 2 ==");
		// System.out.println("\n-----\n" + html + "\n-----\n");
		Assert.assertEquals(
			html,
			"<a name=\"wiki_h1_header_1\" id=\"wiki_h1_header_1\"></a><h1>header 1</h1><p>my first paragraph<br>still first</p><p>second paragraph</p><p>third paragraph</p><a name=\"wiki_h2_header_2\" id=\"wiki_h2_header_2\"></a><h2>header 2</h2>");
	}

	private static String toHtml(String wiki) {
		WikiParser parser = new WikiParser(wiki);
		WikiModel model = parser.parse(true);
		return model.toHtml(new TestHtmlContext());
	}

	static class TestHtmlContext implements HtmlContext {

		@Override
		public boolean isEntityReferenceAvailable(String reference) {
			return true;
		}

		@Override
		public String getEntityReferenceHrefOrOnclickAParameter(String reference) {
			return "href='" + reference + ".html'";
		}

		@Override
		public String getTocHrefOrOnclickAParameter(Header h) {
			return "href=\"#" + h.getAnchor() + "\"";
		}

		@Override
		public String getDownloadUrlByReference(String reference) {
			return reference;
		}

		@Override
		public String getEntityLabelByReference(String reference) {
			return null;
		}

	}

}
