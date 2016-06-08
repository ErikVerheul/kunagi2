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
package ilarkesto.ui.web;

import ilarkesto.base.StrExtend;
import static ilarkesto.base.StrExtend.activateLinksInHtml;
import static ilarkesto.base.StrExtend.replaceForHtml;
import ilarkesto.base.Url;
import ilarkesto.base.UtlExtend;
import static ilarkesto.base.UtlExtend.getRootCause;
import static ilarkesto.core.base.Str.toHtml;
import static ilarkesto.core.base.Utl.equalsAny;
import ilarkesto.id.CountingIdGenerator;
import ilarkesto.id.IdGenerator;
import ilarkesto.integration.links.MultiLinkConverter;
import static ilarkesto.integration.links.MultiLinkConverter.ALL;
import ilarkesto.io.IO;
import static ilarkesto.io.IO.UTF_8;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.String.valueOf;

public class HtmlRenderer {

	private PrintWriter out;
	private String encoding = UTF_8;

	private StringWriter buffer; // optional
	private FileWriter fileWriter; // optional

	private String startingTag;
	private int depth;

	public HtmlRenderer() {
		buffer = new StringWriter();
		out = new PrintWriter(buffer);
	}

	public HtmlRenderer(PrintWriter out, String encoding) {
		this.encoding = encoding;
		this.out = out;
	}

	public HtmlRenderer(OutputStream out, String encoding) throws UnsupportedEncodingException {
		this(new PrintWriter(new OutputStreamWriter(out, encoding)), encoding);
	}

	public HtmlRenderer(FileWriter fileWriter, String encoding) {
		this(new PrintWriter(fileWriter), encoding);
		this.fileWriter = fileWriter;
	}

	public void setIndentationDepth(int depth) {
		this.depth = depth;
	}

	public void flush() {
		out.flush();
	}

	public void close() {
		flush();
		out.close();
		if (fileWriter != null) {
			try {
				fileWriter.close();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public void flattrCompactStatic(String thingUrl) {
		startA(thingUrl).setTargetBlank();
		IMG("http://api.flattr.com/button/button-compact-static-100x17.png", "Flattr this", null, null, 100, 17);
		endA();

	}

	public void flattrCompactJs(String url) {
		SCRIPTjavascript(null, "var flattr_url = '" + url + "';\n" + "var flattr_btn='compact';");
		SCRIPTjavascript("http://api.flattr.com/button/load.js", null);
	}

	// --- IFRAME ---

	private static final String IFRAME = "iframe";

	public void IFRAME(String src, String id, String height) {
		startTag(IFRAME).set("src", src).setId(id).set("frameborder", 0).setWidth("100%").set("height", height);
		A(src, src);
		endTag(IFRAME);
	}

	// --- TEXTAREA ---

	private static final String TEXTAREA = "textarea";

	public void TEXTAREA(String name, String id, String text) {
		startTag(TEXTAREA).set("name", name).setId(id);
		html(text);
		endTag(TEXTAREA);
	}

	public void TEXTAREA(String name, String id, String text, Integer cols, int rows, boolean wysiwyg, String width) {
		Tag tag = startTag(TEXTAREA).set("name", name).setId(id).set("rows", rows).set("cols", cols)
				.set("wrap", "virtual").setWidth(width).setStyle("width: " + width + ";");
		if (wysiwyg) {
                        tag.setClass("wysiwyg");
                }
		html(text);
		endTag(TEXTAREA);
	}

	// --- PRE ---

	private static final String PRE = "pre";

	public void PRE(String text) {
		startTag(PRE);
		text(text);
		endTag(PRE);
	}

	// --- UL ---

	private static final String UL = "ul";

	public Tag startUL() {
		return startTag(UL);
	}

	public void endUL() {
		endTag(UL);
	}

	// --- LI ---

	private static final String LI = "li";

	public Tag startLI() {
		return startTag(LI);
	}

	public void endLI() {
		endTag(LI);
	}

	public void LI(String text) {
		startLI();
		text(text);
		endLI();
	}

	// --- STRONG ---

	private static final String STRONG = "strong";

	public Tag startSTRONG() {
		return startTag(STRONG);
	}

	public void endSTRONG() {
		endTag(STRONG);
	}

	// --- EM ---

	private static final String EM = "em";

	public Tag startEM() {
		return startTag(EM);
	}

	public void endEM() {
		endTag(EM);
	}

	public void EM(String text) {
		startEM();
		text(text);
		endEM();
	}

	// --- SPAN ---

	private static final IdGenerator hintIdGenerator = new CountingIdGenerator("hint");

	private static final String SPAN = "span";

	public void startSPANwithHint(String clazz, String content) {
		Tag tag = startSPAN(clazz);
		if (content == null) {
                        return;
                }

		String hintDivId = hintIdGenerator.generateId();
		tag.setOnmouseover("showHint('" + hintDivId + "')").setOnmouseout("hideHint()");
		startDIV("hint").setId(hintDivId);
		text(content);
		endDIV();
	}

	public Tag startSPAN(String clazz) {
		return startTag(SPAN).setClass(clazz);
	}

	public void endSPAN() {
		endTag(SPAN);
	}

	public void SPAN(String clazz, String text) {
		startSPAN(clazz);
		text(text);
		endSPAN();
	}

	// --- LABEL ---

	private static final String LABEL = "label";

	public Tag startLABEL(String for_) {
		return startTag(LABEL).set("for", for_);
	}

	public void endLABEL() {
		endTag(LABEL);
	}

	public void LABEL(String for_, String text) {
		startLABEL(for_);
		text(text);
		endLABEL();
	}

	// --- HR ---

	private static final String HR = "hr";

	public void HR() {
		startTag(HR).end();
	}

	// --- BR ---

	private static final String BR = "br";

	public void BR() {
		startTag(BR).end();
	}

	public void BR(int count) {
		for (int i = 0; i < count; i++) {
			BR();
		}
	}

	// --- CENTER ---

	private static final String CENTER = "center";

	public void startCENTER() {
		startTag(CENTER);
	}

	public void endCENTER() {
		endTag(CENTER);
	}

	// --- FORM ---

	private static final String FORM = "form";

	private static final String INPUT = "input";

	private static final String SELECT = "select";

	private static final String OPTION = "option";

	private static final String BUTTON = "button";

	private static final String FIELDSET = "fieldset";

	public Tag startFIELDSET() {
		return startTag(FIELDSET);
	}

	public void endFIELDSET() {
		endTag(FIELDSET);
	}

	public Tag startFORM(Url actionUrl, String name, boolean multipart) {
		return startFORM(actionUrl, "post", name, multipart);
	}

	public Tag startFORM(Url actionUrl, String method, String name, boolean multipart) {
		String action = actionUrl == null ? null : actionUrl.toString();
		Tag tag = startTag(FORM, true).set("action", action).set("method", method).setName(name)
				.set("accept-charset", encoding);
		if (multipart) {
			tag.set("enctype", "multipart/form-data");
		}
		return tag;
	}

	public void endFORM() {
		endTag(FORM);
	}

	public Tag startSELECT(String name) {
		return startTag(SELECT, true).setName(name);
	}

	public Tag startSELECT(String name, Integer size) {
		return startSELECT(name).set("size", size);
	}

	public void endSELECT() {
		endTag(SELECT);
	}

	public void OPTION(String value, String text, boolean selected) {
		Tag tag = startTag(OPTION).set("value", value);
		if (selected) {
                        tag.set("selected", "true");
                }
		text(text);
		endTag(OPTION);
	}

	public Tag startINPUT(String type, String name) {
		return startTag(INPUT).set("type", type).setName(name);
	}

	public void endINPUT() {
		endTag(INPUT);
	}
        public void INPUTreset(String value, String onclick) {
		startTag(INPUT).set("type", "reset").set("value", value).setOnclick(onclick).end();
        }
        
	private static final IdGenerator dateIdGenerator = new CountingIdGenerator("date");

	public void INPUTdate(String name, String value) {
		String id = dateIdGenerator.generateId();
		// INPUTtext(id, name, value, 10);
		// String buttonName = "b_" + name;
		// INPUTreset("...", "return showCalendar('"+id+"', '%d.%m.%Y', '24', false, true);");

		INPUTtext(id, name, value, 10);
		String buttonId = "b_" + id;
		BUTTON("button", buttonId, null, "Kalender", null, null, "/img/kde/16x16/apps/cal.png");
		StringBuilder code = new StringBuilder();
		code.append("Calendar.setup({\n");
		code.append("    inputField:  \"").append(id).append("\",\n");
		code.append("    ifFormat:    \"%d.%m.%Y\",\n");
		code.append("    button:      \"").append(buttonId).append("\",\n");
		code.append("});\n");
		SCRIPTjavascript(null, code.toString());
	}

	public void INPUThidden(String name, String value) {
		INPUT("hidden", name, value).end();
	}

	public void INPUTsubmit(String name, String label, String onclick, Character accessKey) {
		INPUTsubmit(name, label, onclick, accessKey, null);
	}

	public void INPUTsubmit(String name, String label, String onclick, Character accessKey, String style) {
		Tag tag = INPUT("submit", name, label);
		tag.set("class", "inputButton");
		tag.set("accesskey", accessKey);
		tag.setStyle(style);
		if (onclick != null) {
                        tag.setOnclick(onclick);
                }
		tag.end();
	}

	public void INPUTtext(String name, String value, int width) {
		INPUTtext(name, value, width, null);
	}

	public void INPUTtext(String name, String value, int width, String style) {
		INPUTtext(null, name, value, width, style);
	}

	public void INPUTtext(String id, String name, String value, int width) {
		INPUTtext(id, name, value, width, null);
	}

	public void INPUTtext(String id, String name, String value, int width, String style) {
		Tag tag = INPUT("text", name, value).setId(id).set("size", width).setClass("inputText").setStyle(style);
		tag.setOnfocus("javascript:select();");
		tag.end();
	}

	public void INPUTpassword(String name, int width, String value) {
		INPUT("password", name, value).set("size", width).setClass("inputText").end();
	}

	public void INPUTpassword(String id, String name, int width, String value) {
		INPUT("password", name, value).set("id", id).set("size", width).setClass("inputText").end();
	}

	public void INPUTcheckbox(String id, String name, boolean checked) {
		Tag tag = INPUT("checkbox", name, "true").setId(id).setClass("inputCheckbox");
		if (checked) {
			tag.set("checked", "checked");
		}
	}

	public void INPUTcheckbox(String name, boolean checked) {
		INPUTcheckbox(null, name, checked);
	}

	public void INPUTcheckbox(String name, boolean checked, String text) {
		String id = "cb_" + name;
		INPUTcheckbox(id, name, checked);
		LABEL(id, text);
	}

	public void INPUTradio(String name, String value, boolean checked) {
		Tag tag = INPUT("radio", name, value).setClass("inputRadio");
		if (checked) {
			tag.set("checked", "checked");
		}
	}

	public void INPUTfile(String name, Integer maxlength) {
		INPUT("file", name, null).set("maxlength", maxlength).setClass("inputText");
	}

	private Tag INPUT(String type, String name, String value) {
		Tag tag = startTag(INPUT, true).set("type", type).set("name", name);
		if (value != null) {
                        tag.set("value", replaceForHtml(value));
                }
		return tag;
	}

	public Tag startBUTTON() {
		Tag tag = startTag(BUTTON);
		return tag;
	}

	public void endBUTTON() {
		endTag(BUTTON);
	}

	public void BUTTON(String type, String id, String name, String text, String onclick, Character accessKey,
			String icon) {
		Tag tag = startTag(BUTTON, true).set("value", text).set("type", type).setId(id).setName(name)
				.setClass("button");
		tag.set("accesskey", accessKey);
		if (onclick != null) {
			tag.setOnclick(onclick);
		}
		if (icon != null) {
			// globalTag.setStyle("background: transparent url(" + icon + ") 2px center no-repeat; padding: 2px 2px
			// 2px 20px;");
			IMG(icon, "icon", null, null, 12, 12);
			nbsp();
		}
		text(text);
		nbsp();
		endTag(BUTTON);
	}

	// --- HTML ---

	private static final String HTML = "html";

	private static final String HEAD = "head";

	private static final String TITLE = "title";

	private static final String META = "meta";

	private static final String LINK = "link";

	private static final String SCRIPT = "script";

	private static final String BODY = "body";

	public void startHTML() {
		// out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"
		// \"http://www.w3.org/TR/html4/loose.dtd\">");
		// out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		// out.print("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\">");
		// startTag(HTML);

		startHTMLstandard();
	}

	public void startHTMLstandard() {
		out.print("<!DOCTYPE html>");
		startTag(HTML);
	}

	public void endHTML() {
		endTag(HTML);
	}

	public void startHEAD(String title, String language) {
		startTag(HEAD);
		METAviewport("device-width", 1);
		META("Content-Language", language);
		META("Content-Type", "text/html; charset=" + encoding);
		TITLE(title);
	}

	public void TITLE(String title) {
		startTag(TITLE);
		text(title);
		endTag(TITLE);
	}

	public void endHEAD() {
		endTag(HEAD);
	}

	public void META(String httpEquiv, String content) {
		startTag(META, true).set("http-equiv", httpEquiv).set("content", content).end();
	}

	public void METAviewport(String width, Integer initialScale) {
		StringBuilder sb = new StringBuilder();
		sb.append("width=").append(width);
		sb.append(", initial-scale=").append(initialScale);
		startTag(META, true).set("name", "viewport").set("content", sb.toString()).end();
	}

	public void METArefresh(int seconds, String url) {
		StringBuilder content = new StringBuilder();
		content.append(seconds);
		if (url != null) {
                        content.append("; URL=").append(url);
                }
		META("refresh", content.toString());
	}

	public void LINK(String rel, String type, String title, String href) {
		startTag(LINK, true).set("rel", rel).set("type", type).set("title", title).setHref(href).end();
	}

	public void LINK(String rel, String type, String href) {
		LINK(rel, type, null, href);
	}

	public void LINKfavicon() {
		LINK("shortcut icon", "image/x-icon", "favicon.ico");
	}

	public void LINKcss(Url href) {
		LINKcss(toString(href));
	}

	public void LINKcss(String href) {
		LINK("stylesheet", "text/css", href);
	}

	public void SCRIPT(String type, String language, String src, String code) {
		startTag(SCRIPT, true).set("type", type).set("language", language).set("src", src);
		html(code);
		endTag(SCRIPT);
	}

	public void SCRIPTjavascript(String src, String code) {
		SCRIPT("text/javascript", "javascript", src, code);
	}

	public void SCRIPTdojo() {
		SCRIPTjavascript("http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js", null);
	}

	public void SCRIPTjquery() {
		SCRIPTjavascript("http://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js", null);
	}

	public Tag startBODY() {
		return startTag(BODY, true).setId("body");
	}

	public void endBODY() {
		endTag(BODY);
	}

	// --- google analytics ---

	public void googleAnalytics(String webPropertyId) {
		if (webPropertyId == null) {
                        return;
                }
		html("<script type=\"text/javascript\">\r\n"
				+ "var gaJsHost = ((\"https:\" == document.location.protocol) ? \"https://ssl.\" : \"http://www.\");\r\n"
				+ "document.write(unescape(\"%3Cscript src='\" + gaJsHost + \"google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E\"));\r\n"
				+ "</script>\r\n" + "<script type=\"text/javascript\">\r\n" + "try {\r\n"
				+ "var pageTracker = _gat._getTracker(\"" + webPropertyId + "\");\r\n"
				+ "pageTracker._trackPageview();\r\n" + "} catch(err) {}</script>");
	}

	@Override
	public String toString() {
		if (buffer == null) {
                        return super.toString();
                }
		flush();
		return buffer.toString();
	}

	// --- helper ---

	private String toString(Url url) {
		if (url == null) {
                        return null;
                }
		return url.toString();
	}

	// --- STYLE ---

	private static final String STYLE = "style";

	public CssRenderer startSTYLEcss() {
		startTag(STYLE).set("type", "text/css");
		closeStartingTag();
		CssRenderer css = new CssRenderer(out);
		return css;
	}

	public void endSTYLE() {
		endTag(STYLE);
	}

	// --- IMG ---

	private static final String IMG = "img";

	public void IMG(String src, String alternatieText, String id, String align, Integer width, Integer height) {
		IMG(src, alternatieText, id, align, width, height, null);
	}

	public void IMG(String src, String alternatieText, String id, String align, Integer width, Integer height,
			String style) {
		Tag tag = startTag(IMG).setSrc(src).setAlt(alternatieText).setBorder(0);
		if (id != null) {
                        tag.setId(id);
                }
		if (width != null) {
                        tag.setWidth(width);
                }
		if (height != null) {
                        tag.set("height", height);
                }
		if (align != null) {
                        tag.setAlign(align);
                }
		if (style != null) {
                        tag.setStyle(style);
                }
		tag.end();
	}

	// --- DIV ---

	private static final String DIV = "div";

	public void DIVclear() {
		startDIV().setStyle("clear: both;");
		endDIV();
	}

	public Tag startDIV(String clazz) {
		return startDIV().setClass(clazz);
	}

	public Tag startDIV() {
		return startTag(DIV, true);
	}

	public void endDIV() {
		endTag(DIV);
	}

	public void DIV(String clazz, String text) {
		startDIV(clazz);
		text(text);
		endDIV();
	}

	// --- H ---

	private static final String H1 = "h1";

	public Tag startH1() {
		return startTag(H1);
	}

	public void endH1() {
		endTag(H1);
	}

	public void H1(String text) {
		startH1();
		text(text);
		endH1();
	}

	private static final String H2 = "h2";

	public Tag startH2() {
		return startTag(H2);
	}

	public void endH2() {
		endTag(H2);
	}

	public void H2(String text) {
		startH2();
		text(text);
		endH2();
	}

	private static final String H3 = "h3";

	public Tag startH3() {
		return startTag(H3);
	}

	public void endH3() {
		endTag(H3);
	}

	public void H3(String text) {
		startH3();
		text(text);
		endH3();
	}

	// --- P ---

	private static final String P = "p";

	public Tag startP() {
		return startTag(P);
	}

	public void endP() {
		endTag(P);
	}

	// --- CODE ---

	private static final String CODE = "CODE";

	public Tag startCODE() {
		return startTag(CODE);
	}

	public void endCODE() {
		endTag(CODE);
	}

	public void CODE(String text) {
		startCODE();
		text(text);
		endCODE();
	}

	// --- A ---

	private static final String A = "a";

	public Tag startA(String clazz, String href) {
		return startTag(A, false).setClass(clazz).setHref(href);
	}

	public Tag startA(String href) {
		return startTag(A, false).setHref(href);
	}

	public void endA() {
		endTag(A);
	}

	public void A(String href, String text) {
		A(href, text, false);
	}

	public void A(String href, String text, boolean targetBlank) {
		Tag a = startA(href);
		if (targetBlank) {
                        a.setTargetBlank();
                }
		text(text);
		endA();
	}

	// --- TABLE ---

	private static final String TABLE = "table";

	private static final String TR = "tr";

	private static final String TD = "td";

	private static final String TH = "th";

	public Tag startTABLE() {
		return startTag(TABLE, true).setBorder(0).set("cellpadding", 0).set("cellspacing", 0);
	}

	public Tag startTABLE(String clazz) {
		return startTABLE(clazz, 0, 0, 0);
	}

	public Tag startTABLE(String clazz, int border, int cellpadding, int cellspacing) {
		return startTag(TABLE, true).setClass(clazz).setBorder(border).set("cellpadding", cellpadding)
				.set("cellspacing", cellspacing);
	}

	public void endTABLE() {
		endTag(TABLE);
	}

	public Tag startTR() {
		return startTag(TR, true);
	}

	public void endTR() {
		endTag(TR);
	}

	public Tag startTD() {
		return startTag(TD).setValign(VALIGN_TOP);
	}

	public Tag startTD(String clazz) {
		return startTag(TD).setClass(clazz).setValign(VALIGN_TOP);
	}

	public Tag startTD(String clazz, int colspan) {
		return startTag(TD).setClass(clazz).set("colspan", colspan).setValign(VALIGN_TOP);
	}

	public void endTD() {
		endTag(TD);
	}

	public void TD(String text) {
		startTD();
		text(text);
		endTD();
	}

	public void TD(String clazz, String content) {
		startTD().setClass(clazz);
		text(content);
		endTD();
	}

	public Tag startTH() {
		return startTag(TH);
	}

	public void endTH() {
		endTag(TH);
	}

	public void TH(String text) {
		startTH();
		text(text);
		endTH();
	}

	public void TH(String text, String width) {
		Tag tag = startTH();
		tag.setWidth(width);
		text(text);
		endTH();
	}

	public void nbsp(int count) {
		for (int i = 0; i < count; i++) {
			nbsp();
		}
	}

	// --- core ---

	public void nbsp() {
		closeStartingTag();
		out.print("&nbsp;");
	}

	public void nl() {
		out.println();
	}

	public Tag startTag(String name) {
		return startTag(name, true);
	}

	private void printPrefix() {
		// increases html file size
		for (int i = 0; i < depth; i++) {
                        out.print(" ");
                }
	}

	public Tag startTag(String name, boolean nl) {
		closeStartingTag();
		startingTag = name;
		if (nl) {
			nl();
			printPrefix();
		}
		out.print('<');
		out.print(name);
		depth++;
		return new Tag();
	}

	public void textEm(Object text) {
		startEM();
		text(text);
		endEM();
	}

	public void textStrong(Object text) {
		startSTRONG();
		text(text);
		endSTRONG();
	}

	public void text(Object text) {
		text(text, false);
	}

	public void text(Object text, boolean activateLinks) {
		closeStartingTag();
		if (text != null) {
			String s;
			if (text instanceof String) {
				s = (String) text;
			} else {
				try {
					s = text.toString();
				} catch (Throwable ex) {
					s = "<ERROR: " + getRootCause(ex).getMessage() + ">";
				}
			}
			if (s.startsWith("<html>")) {
				if (s.length() > 6) {
					s = s.substring(6);
					if (activateLinks) {
                                                s = activateLinksInHtml(s, ALL);
                                        }
					out.print(s);
				}
			} else {
				s = replaceForHtml(s);
				// text = StringEscapeUtils.escapeHtml(text);
				// text = text.replace("\n", "<BR/>");
				if (activateLinks) {
                                        s = activateLinksInHtml(s, ALL);
                                }
				out.print(s);
			}
		}
	}

	public void html(String html) {
		closeStartingTag();
		if (html != null) {
			out.print(html);
		}
	}

	public void comment(String text) {
		comment(text, true);
	}

	public void comment(String text, boolean nl) {
		closeStartingTag();
		if (nl) {
			nl();
			printPrefix();
		}
		out.print("<!-- ");
		out.print(text);
		out.print(" -->");
	}

	public void endTag(String name) {
		closeStartingTag();
		depth--;

		if (equalsAny(name, "html", "body", "head", "ul", "div")) {
			nl();
			printPrefix();
		}

		out.print("</");
		out.print(name);
		out.print(">");
	}

	private void closeStartingTag() {
		if (startingTag != null) {
			out.print(">");
			startingTag = null;
		}
	}

	public String getEncoding() {
		return encoding;
	}

	public static final String VALIGN_TOP = "top";

	public static final String ALIGN_RIGHT = "right";

	public static final String TARGET_BLANK = "_blank";

	public class Tag {

		public Tag setTarget(String value) {
			return set("target", value);
		}

		public Tag setTargetBlank() {
			return setTarget(TARGET_BLANK);
		}

		public Tag setWidth(int value) {
			return set("width", value);
		}

		public Tag setWidth(String value) {
			return set("width", value);
		}

		public Tag setHeight(String value) {
			return set("height", value);
		}

		public Tag setId(String value) {
			return set("id", value);
		}

		public Tag setValue(String value) {
			return set("value", value);
		}

		public Tag setSrc(String value) {
			return set("src", value);
		}

		public Tag setAlt(String value) {
			return set("alt", value);
		}

		public Tag setTitle(String value) {
			return set("title", toHtml(value));
		}

		public Tag setHref(String value) {
			if (value == null) {
                                return this;
                        }
			return set("href", value.replace("&", "&amp;"));
		}

		public Tag setAlign(String value) {
			return set("align", value);
		}

		public Tag setAlignCenter() {
			return setAlign("center");
		}

		public Tag setAlignRight() {
			return setAlign("right");
		}

		public Tag setValign(String value) {
			return set("valign", value);
		}

		public Tag setBorder(int value) {
			return set("border", value);
		}

		public Tag setClass(String value) {
			return set("class", value);
		}

		public Tag setName(String value) {
			return set("name", value);
		}

		public Tag setOnclick(String value) {
			return set("onclick", value);
		}

		public Tag setOnfocus(String value) {
			return set("onfocus", value);
		}

		public Tag setOnmouseover(String value) {
			return set("onmouseover", value);
		}

		public Tag setOnmouseout(String value) {
			return set("onmouseout", value);
		}

		public Tag setOnload(String value) {
			return set("onload", value);
		}

		public Tag setStyle(String... values) {
			StringBuilder sb = new StringBuilder();
			for (String value : values) {
				sb.append(value).append("; ");
			}
			return setStyle(sb.toString());
		}

		public Tag setStyle(String value) {
			return set("style", value);
		}

		public Tag setColspan(int value) {
			return set("colspan", value);
		}

		public Tag setRowspan(int value) {
			return set("rowspan", value);
		}

		public Tag setDataRole(String value) {
			return set("data-role", value);
		}

		public Tag setDataRel(String value) {
			return set("data-rel", value);
		}

		public Tag setDataRelPopup() {
			return setDataRel("popup");
		}

		public Tag setDataIcon(String value) {
			return set("data-icon", value);
		}

		private Tag set(String name, Character value) {
			if (value == null) {
                                return this;
                        }
			return set(name, value.toString());
		}

		public Tag set(String name, Object value) {
			if (value == null) {
                                return this;
                        }
			return set(name, valueOf(value));
		}

		public Tag set(String name, String value) {
			// TODO cache in map
			if (value == null) {
                                return this;
                        }
			out.print(" ");
			out.print(name);
			out.print("=\"");
			out.print(value);
			out.print("\"");
			return this;
		}

		public void end() {
			depth--;
			out.print(" />");
			startingTag = null;
		}

	}

}
