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

import ilarkesto.junit.AjunitTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author erik
 */
public class WikiTest extends AjunitTest {
    
    //A convenience method to cope with the previous TestNG order
    private static void assertEqualsReverse(Object actual, Object expected) {
        assertEquals(expected, actual);
    }

    /**
     *
     */
    @Test
    public void table() {
        assertEqualsReverse(toHtml("{|a|}"), "\n<table class='data-table'>\n<tr> <td>a</td> </tr>\n</table>\n");
        assertEqualsReverse(toHtml("{|\n|a\n|b\n|-\n|c\n|d\n\n|}"),
                "\n<table class='data-table'>\n<tr> <td>a</td>  <td>b</td> </tr>\n<tr> <td>c</td>  <td>d</td> </tr>\n</table>\n");
        assertEqualsReverse(toHtml("{|\n|a||b\n|-\n|c||d\n|}"),
                "\n<table class='data-table'>\n<tr> <td>a</td>  <td>b</td> </tr>\n<tr> <td>c</td>  <td>d</td> </tr>\n</table>\n");
    }

    /**
     *
     */
    @Test
    public void tableWithHeaders() {
        assertEqualsReverse(toHtml("{|\n!a\n!b\n|-\n|c\n|d\n\n|}"),
                "\n<table class='data-table'>\n<tr> <th>a</th>  <th>b</th> </tr>\n<tr> <td>c</td>  <td>d</td> </tr>\n</table>\n");
        assertEqualsReverse(toHtml("{|\n!a!!b\n|-\n!c||d\n|}"),
                "\n<table class='data-table'>\n<tr> <th>a</th>  <th>b</th> </tr>\n<tr> <th>c</th>  <td>d</td> </tr>\n</table>\n");
    }

    /**
     *
     */
    @Test
    public void localImg() {
        assertEqualsReverse(toHtml("[[Image:fle1]]"), "<a href='fle1.html'><img src=\"fle1\"></a>");
        assertEqualsReverse(toHtml("[[Image:fle1|thumb]]"),
                "<a href='fle1.html'><img src=\"fle1\" width=\"100px\" align=\"right\"></a>");
        assertEqualsReverse(toHtml("[[Image:fle1|thumb|left]]"),
                "<a href='fle1.html'><img src=\"fle1\" width=\"100px\" align=\"left\"></a>");
    }

    /**
     *
     */
    @Test
    public void externalImg() {
        assertEqualsReverse(toHtml("[[Image:http://servisto.de/image.png]]"),
                "<a href=\"http://servisto.de/image.png\" target=\"_blank\"><img src=\"http://servisto.de/image.png\"></a>");

    }

    /**
     *
     */
    @Test
    public void toc() {
        assertEqualsReverse(toHtml("__TOC__\n= 1 =\n== 1.1 ==\n= 2 ="), "<div class=\"toc\"><ul><li>"
                + "<a href=\"#wiki_h1_1\">1</a></li><ul><li>" + "<a href=\"#wiki_h2_1_1\">1.1</a></li></ul><li>"
                + "<a href=\"#wiki_h1_2\">2</a></li></ul></div>"
                + "<a name=\"wiki_h1_1\" id=\"wiki_h1_1\"></a><h1>1</h1>"
                + "<a name=\"wiki_h2_1_1\" id=\"wiki_h2_1_1\"></a><h2>1.1</h2>"
                + "<a name=\"wiki_h1_2\" id=\"wiki_h1_2\"></a><h1>2</h1>");
    }

    /**
     *
     */
    @Test
    public void emphAndStrong() {
        assertEqualsReverse(toHtml("'''''emph and strong'''''"), "<strong><em>emph and strong</em></strong>");
        assertEqualsReverse(toHtml("this is '''strong'''"), "this is <strong>strong</strong>");
        assertEqualsReverse(toHtml("this is ''emph''"), "this is <em>emph</em>");
        assertEqualsReverse(toHtml("''''''''''"), "''''''''''");
        assertEqualsReverse(toHtml("'''''test"), "'''''test");
    }

    /**
     *
     */
    @Test
    public void entityReference() {
        assertTrue(toHtml("tsk15 is completed").contains("<a "));
        assertTrue(toHtml("[[Wiki]] is cool").contains("<a "));
        assertTrue(toHtml("[[Wiki|Custom Text]] is cool").contains(">Custom Text</a>"));
        assertTrue(toHtml("tsk15!").contains("<a "));
        assertTrue(toHtml("(tsk15!), :-)").contains("<a "));
        assertTrue(toHtml("tsk15:; :-)").contains("<a "));
    }

    /**
     *
     */
    @Test
    public void link() {
        assertEqualsReverse(toHtml("link www.servisto.de here"),
                "link <a href=\"http://www.servisto.de\" target=\"_blank\">servisto.de</a> here");
        assertEqualsReverse(toHtml("http://www.servisto.de"),
                "<a href=\"http://www.servisto.de\" target=\"_blank\">servisto.de</a>");
        assertEqualsReverse(toHtml("link [www.servisto.de Servisto] here"),
                "link <a href=\"http://www.servisto.de\" target=\"_blank\">Servisto</a> here");
    }

    /**
     *
     */
    @Test
    public void itemList() {
        assertEqualsReverse(toHtml("* item"), "<ul><li>item</li></ul>");
        assertEqualsReverse(toHtml("# item"), "<ol><li>item</li></ol>");
        assertEqualsReverse(toHtml("* item\nxyz"), "<ul><li>item<br>xyz</li></ul>");
        assertEqualsReverse(toHtml("* item 1\n* item 2"), "<ul><li>item 1</li><li>item 2</li></ul>");
    }

    /**
     *
     */
    @Test
    public void itemListWithValue() {
        assertEqualsReverse(toHtml("#=42 item"), "<ol><li value=\"42\">item</li></ol>");
        assertEqualsReverse(toHtml("#=42 item 1\n# item 2"), "<ol><li value=\"42\">item 1</li><li>item 2</li></ol>");
    }

    /**
     *
     */
    @Test
    public void itemListWithIdentifier() {
        assertEqualsReverse(toHtml("#=myid item 1\n# item 2\n\n#=myid item 3"),
                "<ol><li>item 1</li><li>item 2</li></ol><ol><li value=\"3\">item 3</li></ol>");
    }

    /**
     *
     */
    @Test
    public void nestedItemList() {
        assertEqualsReverse(toHtml("* item\n # subitem"), "<ul><li>item<ol><li>subitem</li></ol></li></ul>");
        assertEqualsReverse(
                toHtml("* topitem 1\n * subitem 1.1\n * subitem 1.2\n  * subsubitem 1.2.1\n * subitem 1.3\n* topitem 2"),
                "<ul><li>topitem 1<ul><li>subitem 1.1</li><li>subitem 1.2<ul><li>subsubitem 1.2.1</li></ul></li><li>subitem 1.3</li></ul></li><li>topitem 2</li></ul>");
        assertEqualsReverse(toHtml("* topitem 1\n  * subitem 1.1\n  * subitem 1.2\n    * subsubitem 1.2.1"),
                "<ul><li>topitem 1<ul><li>subitem 1.1</li><li>subitem 1.2<ul><li>subsubitem 1.2.1</li></ul></li></ul></li></ul>");
        assertEqualsReverse(toHtml("* item\n # subitem\n  * subsubitem"),
                "<ul><li>item<ol><li>subitem<ul><li>subsubitem</li></ul></li></ol></li></ul>");
    }

    /**
     *
     */
    @Test
    public void nestedItemListDirty() {
        assertEqualsReverse(toHtml("* topitem\n    * A\n * B\n    * C"),
                "<ul><li>topitem<ul><li>A</li><li>B</li><li>C</li></ul></li></ul>");
    }

    /**
     *
     */
    @Test
    public void preformated() {
        assertEqualsReverse(toHtml(" preformated"), "<pre class=\"codeBlock\"> preformated</pre>");
        assertEqualsReverse(toHtml("\tpreformated"), "<pre class=\"codeBlock\">    preformated</pre>");
        assertEqualsReverse(toHtml(" line 1\n line 2"), "<pre class=\"codeBlock\"> line 1\n line 2</pre>");
    }

    /**
     *
     */
    @Test
    public void nowiki() {
        assertEqualsReverse(toHtml("here is <nowiki>'''plain'''</nowiki>."), "here is '''plain'''.");
        assertEqualsReverse(toHtml("<nowiki>\n= header 1 =\n\nparagraph...</nowiki>"),
                "<p><br>= header 1 =<br><br>paragraph...</p>");
    }

    /**
     *
     */
    @Test
    public void code() {
        assertEqualsReverse(toHtml("some <code>code</code> is here."), "some <code>code</code> is here.");
        assertEqualsReverse(toHtml("here is <code>code</code>."), "here is <code>code</code>.");
        assertEqualsReverse(toHtml("here is <code>multiword code</code>."), "here is <code>multiword&nbsp;code</code>.");
        assertEqualsReverse(toHtml("here is <code>multiline\ncode</code>."),
                "<p>here is <code class=\"codeBlock\">multiline<br>code</code>.</p>");
        assertEqualsReverse(toHtml("simple line\n\n<code>code</code>"),
                "<p>simple line</p><p><code class=\"codeBlock\">code</code></p>");
        assertEqualsReverse(toHtml("<code>\n# enum\n# enum\n</code>"),
                "<p><code class=\"codeBlock\">#&nbsp;enum<br>#&nbsp;enum<br></code></p>");
        assertEqualsReverse(toHtml("<code>a\n\nb</code>"), "<p><code class=\"codeBlock\">a<br><br>b</code></p>");
        assertEqualsReverse(toHtml("<code>simple block</code>"),
                "<p><code class=\"codeBlock\">simple&nbsp;block</code></p>");
        assertEqualsReverse(
                toHtml("some <code>looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong code</code> is here."),
                "some <code class=\"codeBlock\">looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong&nbsp;code</code> is here.");
    }

    /**
     *
     */
    @Test
    public void paragraph() {
        assertEqualsReverse(toHtml("a b"), "a b");
        assertEqualsReverse(toHtml("a\nb"), "<p>a<br>b</p>");
        assertEqualsReverse(toHtml("a\r\nb"), "<p>a<br>b</p>");
        assertEqualsReverse(toHtml("a\n\nb"), "<p>a</p><p>b</p>");
        assertEqualsReverse(toHtml("a\n\n\n"), "<p>a</p>");
    }

    /**
     *
     */
    @Test
    public void header() {
        assertEqualsReverse(toHtml("= header ="),
                "<a name=\"wiki_h1_header\" id=\"wiki_h1_header\"></a><h1>header</h1>");
        assertEqualsReverse(toHtml("= ="), "= =");
        assertEqualsReverse(toHtml("= header = dummy"), "= header = dummy");

        assertEqualsReverse(toHtml("== header =="),
                "<a name=\"wiki_h2_header\" id=\"wiki_h2_header\"></a><h2>header</h2>");
        assertEqualsReverse(toHtml("== =="), "== ==");
        assertEqualsReverse(toHtml("== header == dummy"), "== header == dummy");

        assertEqualsReverse(toHtml("=== header ==="),
                "<a name=\"wiki_h3_header\" id=\"wiki_h3_header\"></a><h3>header</h3>");
        assertEqualsReverse(toHtml("==== header ===="),
                "<a name=\"wiki_h4_header\" id=\"wiki_h4_header\"></a><h4>header</h4>");
    }

    /**
     *
     */
    @Test
    public void specialChars() {
        assertEqualsReverse(toHtml("ü ä ß"), "ü ä ß");
        assertEqualsReverse(toHtml("& #"), "&amp; #");
        assertEqualsReverse(toHtml("< >"), "&lt; &gt;");
    }

    /**
     *
     */
    @Test
    public void simple() {
        assertEqualsReverse(toHtml("hello world"), "hello world");
    }

    /**
     *
     */
    @Test
    public void complete() {
        assertEqualsReverse(toHtml("= header 1 =\nmy first paragraph\nstill first\n\nsecond paragraph\n\n\n\nthird paragraph\n\n== header 2 =="),
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
