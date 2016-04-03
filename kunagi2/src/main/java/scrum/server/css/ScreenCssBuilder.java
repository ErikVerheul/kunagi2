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
package scrum.server.css;

import ilarkesto.base.Colors;
import ilarkesto.ui.web.CssRenderer;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ScreenCssBuilder implements CssBuilder {

	// http://www.colorcombos.com/color-scheme-203.html

	public final static String fontFamily = "Arial Unicode MS, Arial, sans-serif";
	public final static int fontSize = 12;
	public final static int lineHeight = 16;

	public final static int fontSizeSmall = 9;
	public final static int lineHeightSmall = 9;

	public final static int fontSizeTitle = 14;
	public final static int lineHeightTitle = 18;

	public final static String cBackground = "#F2F5FE";

	public final static String cHeaderBackground = "#667B99";
	public final static String cHeaderLink = "#D6E4E1";

	public final static String cEstimationBar0 = cHeaderBackground;
	public final static String cEstimationBar1 = "#669976";

	public final static String cLink = "#2956B2";
	public final static String cErrorBackground = "#FEE";
	public final static String cError = "darkred";
	public final static String cHeaderText = "#FFFFFF";

	public final static String cNavigatorSeparator = "#D9DEE6";
	public final static String cNavigatorLink = "#465B79";
	public final static String cNavigatorSelectedItemBackground = "#CCD5E6";
	public final static String cNavigatorHoverItemBackground = "#E9EEF6";

	public final static String cFieldBackground = "#FFFFFF";

	public final static String cBlockHeaderBackground = cNavigatorHoverItemBackground;
	public final static String cBlockBackground = "#FAFDFF";
	public final static String cBlockSelectionBorder = cHeaderBackground;
	public final static String cBlockHeaderHoverBackground = cBackground;
	public final static String cBlockHeaderDragHandleBackground = "#FBFBFF";
	public final static String cBlockHeaderCellSecondary = "gray";

	public final static String cToolbarBackground = cNavigatorHoverItemBackground;

	public final static String cPagePanelHeaderBackground = "#FFF7F0";
	public final static String cPagePanelHeader = "#FF6637";
	public final static String cPagePanelBorder = cNavigatorSeparator;

	public final static String cChatBackground = "#FFFFFF";
	public final static String cChatBorder = cPagePanelBorder;

	public final static String cTrashBackground = cNavigatorHoverItemBackground;
	public final static String cTrashBorder = cPagePanelBorder;

	public final static String cWaitBackground = cFieldBackground;
	public final static String cWait = cLink;

	public final static String cButtonText = "#444";
	public final static String cButtonTextHover = "#500";
	public final static String cButtonTextDisabled = "lightgray";
	public final static String cButtonBorder = "#666";
	public final static String cButtonBorderHover = "#866";
	public final static String cButtonBorderDisabled = cButtonTextDisabled;

	public final static String cCommentsBackground = "#F8FFF8";
	public final static String cCommentsBorder = "#EAFFEA";
	public final static String cCommentDate = "darkgray";

	// public final static String cUserguideBackground = "#F8F8FF";
	// public final static String cUserguide = "#000066";
	// public final static String cUserguideBorder = "#E0E0FF";

	public final static String cUserguideBackground = cPagePanelHeaderBackground;
	public final static String cUserguide = "#330000";
	public final static String cUserguideBorder = "#E0E0E0";
	public final static String cUserguideTableHeaderBackground = cUserguideBorder;

	public final static String cChangesBackground = "#FFFFF8";
	public final static String cChangesBorder = "#FFFFEA";
	public final static String cChangeDate = cCommentDate;
	public final static String cChangesCommentBackground = "#F7F7F0";

	public final static String cActionsBackground = cPagePanelHeaderBackground;
	public final static String cActionsBorder = "#FFC697";

	public final static String cPlanningPokerTableLines = "#EEE";

	public final static String cBurndownLine = Colors.darken(cHeaderBackground);
	public final static String cBurndownProjectionLine = Colors.lighten(Colors.lighten(cBurndownLine));
	public final static String cBurndownOptimalLine = cPagePanelBorder;

	@Override
	public void buildCss(CssRenderer css) {
		html(css);
		gwt(css);
		ilarkesto(css);
		loginPage(css);
		systemMessage(css);
		blockList(css);
		comments(css);
		userGuide(css);
		actions(css);
		changeHistory(css);
		workspace(css);
		navigator(css);
		chat(css);
		pagePanel(css);
		whiteboard(css);
		dashboard(css);
		calendar(css);
		planningPoker(css);
		userStatus(css);

		css.style(".EmoticonSelectorWidget-emoticon").border(1, "white").borderRadius(2).padding(2, 1, 0, 1)
				.cursorPointer();
		css.style(".EmoticonSelectorWidget-emoticon-selected").background(cBlockHeaderBackground)
				.border(1, cBlockSelectionBorder).padding(2, 3, 0, 2);

		css.style(".TrashWidget").background(cTrashBackground).border(1, cTrashBorder).padding(5);

		css.style(".ToolbarWidget").background(cToolbarBackground).padding(3, 0, 3, 3);
		css.style(".ToolbarWidget .FloatingFlowPanel-element-left").marginRight(3);
		css.style(".ToolbarWidget .FloatingFlowPanel-element-right").marginLeft(3);

		css.style(".fieldLabel").color(cHeaderBackground).lineHeight(22).whiteSpaceNowrap();
		css.style(".AFieldValueWidget").background("white").border(1, "dotted", "white").minWidth(16).minHeight(16)
				.displayBlock().padding(3);
		css.style(".FieldsWidget-fieldLabel").color(cHeaderBackground).lineHeight(22).whiteSpaceNowrap();

		css.style(".dnd-drop-allowed").background(cHeaderBackground);

		css.style(".WidgetsTesterWidget .test-content").background("white").color("black").border(1, "#AAA");

		css.style(".highlighted .ABlockWidget-title").border(1, cError);

		css.style(".LockInfoWidget-icon").marginRight(10);

		css.style(".WaitWidget").background(cWaitBackground).margin(200, 0, 200, 0).borderTop(1, cPagePanelBorder)
				.borderBottom(1, cPagePanelBorder).fontSize(fontSize + 2);
		css.style(".LoginWidget-errorMessage").background(cErrorBackground).color(cError).border(1, cError)
				.fontSize(fontSize + 2).padding(5);

		css.style(".PunishmentsWidget-tableHeader").padding(10).fontSize(fontSizeTitle).lineHeight(lineHeightTitle);

		css.style(".AViewEditWidget-viewer").minWidth(16).minHeight(16).displayBlock().padding(3);
		css.style(".AViewEditWidget-viewer-editable").background(cFieldBackground)
				.border(1, "dotted", cNavigatorSelectedItemBackground).cursorPointer();
		css.style(".ARichtextViewEditWidget-viewer .codeBlock").displayBlock().padding(5).margin(0, 10, 10, 10)
				.border(1, "#EEE").background(Colors.lighten(cFieldBackground)).maxWidth(350).maxHeight(400)
				.overflowScroll();
		css.style(".ARichtextViewEditWidget-viewer div.toc").border(1, cPagePanelBorder).background("#EEE")
				.floatRight().padding(10, 10, 3, 5).margin(10);
		css.style(".ARichtextViewEditWidget-editor").border(1, cPagePanelBorder);
		css.style(".ARichtextViewEditWidget-editor .html-editor-toolbar").displayNone();
		css.style(".AEditableTextareaWidget-editorPanel").width100();
		css.style(".Integer-editor").width(10, "%");
		css.style(".AViewEditWidget-error").color(cError).background(cErrorBackground).border(1, cError).margin(2)
				.padding(2);
		css.style(".data-table").borderCollapseCollapse();
		css.style(".data-table td").border(1, "#ccc").padding(5);
		css.style(".data-table th").border(1, "#ccc").padding(5).fontWeightBold().textAlignCenter().background("#eee");

		css.style(".AIntegerViewEditWidget .gwt-Button").padding(0, 3, 0, 3).fontSize(fontSizeSmall);

		css.style(".TaskRemainingWorkWidget").marginLeft(3);
		css.style(".TaskRemainingWorkWidget .gwt-Button").fontSize(fontSizeSmall);

		css.style(".RequirementEstimatedWorkWidget").marginLeft(3);
		css.style(".RequirementEstimatedWorkWidget .gwt-Button").fontSize(fontSizeSmall);

		css.style(".EstimationBarWidget").marginTop(8).border(1, cNavigatorSelectedItemBackground);
		css.style(".EstimationBarWidget-bar0").background(cEstimationBar0).lineHeight(1).fontSize(1);
		css.style(".EstimationBarWidget-bar1").background(cEstimationBar1).lineHeight(1).fontSize(1);

		css.style(".SprintBorderIndicatorWidget").background(cPagePanelHeaderBackground).color(cPagePanelHeader)
				.border(1, cPagePanelBorder).textAlignCenter().borderRadius(10).fontSize(fontSizeSmall)
				.margin(3, 100, 3, 100);

		css.style("ul.toc");// .displayInline().floatRight();

		css.style("a.reference, a.reference:visited").fontFamilyMonospace().color(cLink);
		css.style("a.reference-unavailable").color("darkgray");

		css.style("input.InputMandatory").border(1, "black");

		css.style(".CreateTaskButtonWrapper").marginTop(5);
		css.style(".CreateTaskButtonWrapper .ButtonWidget .gwt-Button").fontSize(fontSizeSmall).margin(0).padding(1);

		css.style(".CreateStoryButtonWidget").marginTop(1).width(140);
		css.style(".CreateStoryButtonWidget-dragHandle").marginTop(0).width(40).colorBlack();

		css.style(".ReleaseWidget-script-running").fontFamilyMonospace().overflowHidden().color("red").fontWeightBold()
				.textDecorationBlink();
		css.style(".ReleaseWidget-script-ok").fontFamilyMonospace().overflowHidden();
		css.style(".ReleaseWidget-script-empty").fontFamilyMonospace().overflowHidden();
		css.style(".ReleaseWidget-script-failed").fontFamilyMonospace().overflowHidden().color("red");
	}

	private void loginPage(CssRenderer css) {
		css.style(".loginPage");
		css.style(".loginPage a").color("moccasin");
		css.style(".loginPage code").color("#FFA");
		css.style(".loginPage .panel").background(cHeaderBackground).width(380).margin("50px auto").padding(20)
				.borderRadius(15);
		css.style(".loginPage .panel img").marginBottom(-20);
		css.style(".loginPage .message").color("gold").fontWeightBold().marginBottom(10).fontSize(fontSizeTitle);
		String labelColor = Colors.lighten(Colors.lighten(Colors.lighten(cHeaderBackground)));

		css.style(".loginPage .inputButton, .loginPage a.openid .button").background(Colors.darken(cHeaderBackground))
				.border(1, cPagePanelBorder).borderRadius(5).color(labelColor).fontWeightBold().padding(1, 10)
				.cursorPointer().textShadow(-1, -1, 0, "#444");
		css.style(".loginPage .inputButton").marginRight(10);
		css.style(".loginPage a.openid .button").margin(0, 5, 5, 0).floatLeft();
		css.style(".loginPage input:hover.inputButton, .loginPage a.openid:hover .button").color(
			Colors.lighten(labelColor));

		css.style(".loginPage .inputCheckbox").marginLeft(0);
		css.style(".loginPage .panel label").color(labelColor).marginRight(5);
		css.style(".loginPage .panel .optionalLabel label").color(Colors.lighten(cHeaderBackground));
		css.style(".loginPage h2").color("#fff").margin(30, 0, 20, 0).fontSize(fontSizeTitle + 4)
				.textShadow(1, 1, 0, "#222");
		css.style(".loginPage input").marginBottom(5);
		css.style("#username, #email, #password").width(150).marginRight(10);
		css.style("#openid").width(309).marginRight(10);
		css.style(".loginPage .separator").borderTop(1, Colors.lighten(cHeaderBackground)).margin(20, 0, 20, 0);
		css.style(".loginPage .configMessage").colorWhite();
		css.style(".loginPage .kunagiLink").textAlignRight().fontSize(fontSizeSmall).color(labelColor);
		css.style(".loginPage .kunagiLink a").color(Colors.lighten(labelColor));
	}

	private void planningPoker(CssRenderer css) {
		css.style(".PlanningPokerWidget-table-border").background("#333").border(2, "#2A2A2A").padding(12)
				.borderRadius(60).marginBottom(10);
		css.style(".PlanningPokerWidget-table").border(2, "#2A2A2A").borderRadius(45)
				.background("#5A5 url(pokertable_bg.jpg)").padding(40);
		css.style(".PlanningPokerWidget-table-branding").color(cPlanningPokerTableLines).fontFamily("Times New Roman")
				.fontWeightBold().fontSize(30).textAlignCenter().marginBottom(30);
		css.style(
			".PlanningPokerWidget-table .gwt-Hyperlink, .PlanningPokerWidget-table a, .PlanningPokerTableWidget-estimationHelp ul li a.reference")
				.color(cPlanningPokerTableLines);

		int cardWidth = 40;
		int cardHeight = 60;
		css.style(".PlanningPokerCardSlotWidget-slot").width(cardWidth - 8).height(cardHeight - 8)
				.border(5, "solid", cPlanningPokerTableLines).borderRadius(5);
		css.style(".PlanningPokerCardSlotWidget-text").color(cPlanningPokerTableLines).fontSize(fontSizeSmall)
				.textAlignCenter();

		css.style(".PlanningPokerCardWidget").borderRadius(5).width(cardWidth).height(cardHeight).background("#FFF")
				.border(1, "#333");
		css.style(".PlanningPokerCardWidget-clickable").cursorPointer();
		css.style(".PlanningPokerCardWidget-text").fontSize(23).lineHeight(60).fontFamily("Times New Roman")
				.textAlignCenter();
		css.style(".PlanningPokerCardWidget-back").height100().background(cHeaderBackground + " url(pokercard_bg.jpg)");

	}

	private void calendar(CssRenderer css) {
		css.style(".DateSelectorWidget").background("white").textAlignCenter().border(1, "white");
		css.style(".DateSelectorWidget-weekday").background("white").textAlignCenter().color(cBlockHeaderCellSecondary)
				.border(1, "white");
		css.style(".DateSelectorWidget-weeknumber").background("white").color(cBlockHeaderCellSecondary).width(20)
				.border(1, "white");
		css.style(".DateSelectorWidget-spacer").background("white").textAlignCenter().height(20)
				.border(1, "solid", "white");
		css.style(".DateSelectorWidget-selected").background(cNavigatorHoverItemBackground).textAlignCenter()
				.border(1, cNavigatorHoverItemBackground);
		css.style(".DateSelectorWidget-visible").background(cBackground).textAlignCenter()
				.border(1, "solid", cBackground);
		css.style(".DateSelectorWidget-today").border(1, cNavigatorSelectedItemBackground);
		css.style(".DateSelectorWidget-events").color("red").fontSize(fontSizeSmall).lineHeight(lineHeightSmall)
				.textAlignCenter();
		css.style(".DayListWidget-date").color(cBlockHeaderCellSecondary);
		css.style(".DayListWidget-date-info").padding(2);
		css.style(".DayListWidget-week").color(cBlockHeaderCellSecondary);
		css.style(".DayListWidget-month").color(cBlockHeaderCellSecondary);
	}

	private void dashboard(CssRenderer css) {
		css.style(
			".TeamTasksWidget, .UpcomingTasksWidget, .AcceptedIssuesWidget, .OpenImpedimentsWidget, .HighestRisksWidget, .LatestEventsWidget td")
				.lineHeight(lineHeight + 4);
		css.style(".TeamTasksWidget-user").borderTop(1, cBlockHeaderBackground).margin(3, 0);
		css.style(".TeamTasksWidget").borderBottom(1, cBlockHeaderBackground);
		css.style(".LatestEventsWidget td").borderTop(1, cBlockHeaderBackground).padding(3);
		css.style(".LatestEventsWidget table").borderBottom(1, cBlockHeaderBackground);
		css.style(".LatestEventsWidget-time").whiteSpaceNowrap().color(cCommentDate);
	}

	private void whiteboard(CssRenderer css) {
		String cOpen = "#f99";
		css.style(".WhiteboardWidget-open").borderTop(3, cOpen);
		String cOwned = "#ff9";
		css.style(".WhiteboardWidget-owned").borderTop(3, cOwned).padding(0, 1, 0, 1);
		String cDone = "#9e9";
		css.style(".WhiteboardWidget-done").borderTop(3, cDone);
		// int pad = 3;
		// css.style(".WhiteboardWidget-open").padding(pad, pad, pad, pad).background("#fff5f5");
		// css.style(".WhiteboardWidget-owned").padding(pad, pad, pad, pad).background("#fffff5");
		// css.style(".WhiteboardWidget-done").padding(pad, pad, pad, pad).background("#f5fff5");
		css.style(".WhiteboardWidget-columnLabel").fontSize(fontSizeTitle).lineHeight(lineHeightTitle);
		css.style(".WhiteboardWidget-columnLabel-open").borderTop(3, cOpen);
		css.style(".WhiteboardWidget-columnLabel-owned").borderTop(3, cOwned);
		css.style(".WhiteboardWidget-columnLabel-done").borderTop(3, cDone);
		css.style(".WhiteboardWidget-requirement-list").padding(0).paddingTop(10);
	}

	private void ilarkesto(CssRenderer css) {
		css.style(".AWidget-height100").height100();
		css.style(".ImageAnchor img").floatLeft().marginRight(3);
		css.style(".ImageAnchor .text").displayInline();

		css.style(".FloatingFlowPanel-element-left").floatLeft();
		css.style(".FloatingFlowPanel-element-right").floatRight();
		css.style(".floatClear").clearBoth();

		css.style(".BugMarker").border(1, cError).background(cErrorBackground).color(cError).displayBlock().margin(3)
				.padding(3).fontWeightBold().fontSize(fontSizeSmall);

		css.style(".Tooltip").background(cUserguideBackground).color(cUserguide).padding(10)
				.border(1, cUserguideBorder).maxWidth(400);

		css.style(".DropdownMenuButtonWidget .gwt-MenuBar-horizontal").paddingLeft(1).border(1, cPagePanelBorder);
		css.style(".DropdownMenuButtonWidget .gwt-MenuBar-horizontal td").fontSize(fontSizeSmall)
				.lineHeight(lineHeightSmall).padding(0, 3, 0, 3).whiteSpaceNowrap().cursorPointer();
		css.style(".DropdownMenuButtonWidget .gwt-MenuItem-selected").backgroundNone();
	}

	private void gwt(CssRenderer css) {
		css.style(".gwt-Hyperlink a").whiteSpaceNowrap();
		css.style(".gwt-Button").fontFamily(fontFamily).fontSize(fontSize).fontWeightBold().color(cButtonText)
				.padding(2).margin(0).whiteSpaceNowrap().border(1, cButtonBorder);
		css.style(".gwt-Button:hover").color(cButtonTextHover).border(1, cButtonBorderHover);
		css.style(".gwt-Button[disabled], .gwt-Button[disabled]:hover").color(cButtonTextDisabled)
				.border(1, "solid", cButtonBorderDisabled);
		css.style(".MenuItem-disabled").color(cButtonTextDisabled);
	}

	private void html(CssRenderer css) {
		css.html().padding(0).margin(0);
		css.body().padding(0).margin(0).background(cBackground).fontFamily(fontFamily).fontSize(fontSize)
				.lineHeight(lineHeight);
		css.table().borderCollapseCollapse();
		css.td().verticalAlignTop().fontFamily(fontFamily).fontSize(fontSize).lineHeight(lineHeight);
		css.a().cursorPointer().color(cLink).textDecorationUnderline().outlineNone();
		css.p().margin(0, 0, 10, 0);
		css.h1().fontSize(fontSize + 6).lineHeight(lineHeight + 6).fontWeightBold().margin(5, 0, 5, 0);
		css.h2().fontSize(fontSize + 4).lineHeight(lineHeight + 4).fontWeightBold().margin(5, 0, 5, 0);
		css.h3().fontSize(fontSize + 2).lineHeight(lineHeight + 2).fontWeightBold().margin(0, 0, 5, 0);
		css.h4().fontSize(fontSize + 1).lineHeight(lineHeight + 1).fontWeightBold().margin(0, 0, 5, 0);
		css.pre().margin(0, 0, 10, 0).color(cHeaderBackground).fontSize(fontSize).lineHeight(lineHeight);
		css.code().color(cHeaderBackground).fontSize(fontSize).lineHeight(lineHeight);
		css.ul().margin(0, 0, 10, 0).padding(0, 0, 0, 20);
		css.ol().margin(0, 0, 10, 0).padding(0, 0, 0, 20);
		css.img().border("0");
		css.input().border(1, cPagePanelBorder);
		css.textarea().border(1, cPagePanelBorder);
	}

	private void workspace(CssRenderer css) {
		int headerHeight = 25;
		css.style(".Workspace");

		css.style(".Workspace-header").height(headerHeight).background(cHeaderBackground, "../header-bg.png")
				.positionFixed().width100();

		css.style(".Workspace-sidebar").overflowHidden().positionFixed(headerHeight + 10, 0).width(200);
		css.style(".Workspace-sidebar .PagePanel").padding(0);
		css.style(".Workspace-sidebar .PagePanel-header").color(cHeaderText);
		css.style(".Workspace-sidebar .PagePanel-content").border("0");
		css.style(".ProjectSidebarWidget").marginLeft(10);

		css.style(".Workspace-workarea").marginTop(headerHeight + 10).marginRight(10).minHeight(2000);

		css.style(".HeaderWidget-logo").marginLeft(5).marginRight(10); // .positionFixed().top(0).left(40).zIndex(100);
		css.style(".HeaderWidget-title").color(cHeaderText).fontSize(12).fontWeightBold().paddingLeft(5).paddingTop(3);
		css.style(".HeaderWidget-user").color(cHeaderText).fontSize(12).textAlignCenter().marginTop(3).marginRight(5);
		css.style(".HeaderWidget .ToolbarWidget").background("none").margin(0).textAlignRight();
		css.style(".HeaderWidget .ToolbarWidget .FloatingFlowPanel-element").floatRight();
		css.style(".HeaderWidget a").color(cHeaderLink);

		css.style(".StatusWidget").width(10).height(6).marginTop(8).borderRadius(1);

		css.style(".SearchInputWidget input").fontSize(fontSizeSmall).lineHeight(lineHeightSmall).margin(0, 10, 0, 10)
				.padding(1);
	}

	private void systemMessage(CssRenderer css) {
		css.style(".SystemMessageWidget-box").background(cErrorBackground).color(cError).border(1, cError).padding(10)
				.marginBottom(10);
		css.style(".SystemMessageWidget-box-title").fontWeightBold().marginBottom(5);
		css.style(".SystemMessageWidget-box-time").fontStyleItalic().marginTop(5).textAlignRight();
	}

	private void chat(CssRenderer css) {
		css.style(".ChatWidget-outputScroller").background(cChatBackground).border(1, cChatBorder).padding(5);
		css.style(".ChatWidget-output .author").color("green").fontStyleItalic();
		css.style(".ChatWidget-output .author-system").color("red").fontStyleItalic();
		css.style(".ChatWidget-output .author-me").color("gray").fontStyleItalic();
		css.style(".ChatWidget-output a").textDecorationUnderline();
		css.style(".ChatWidget-message").margin(0, 0, 0, 5);
		css.style(".ChatWidget-input").marginTop(5).width(97, "%");
	}

	private void navigator(CssRenderer css) {
		css.style(".NavigatorWidget");
		css.style(".NavigatorWidget-head").borderBottom(1, cNavigatorSeparator).overflowHidden();
		css.style(".NavigatorWidget-item-link").borderBottom(1, cNavigatorSeparator).overflowHidden();
		css.style(".NavigatorWidget-item-link a").color(cNavigatorLink).displayBlock().padding(5, 3, 5, 3)
				.textDecorationNone().overflowHidden();
		css.style(".NavigatorWidget-item-link a:hover").background(cNavigatorHoverItemBackground);
		css.style(".NavigatorWidget-item-link-selected a").background(cNavigatorSelectedItemBackground);
		css.style(".NavigatorWidget-item-link-selected a:hover").background(cNavigatorSelectedItemBackground);

		css.style(".NavigatorWidget-submenu .NavigatorWidget-item-link a").paddingLeft(30).overflowHidden();
	}

	private void actions(CssRenderer css) {
		css.style(".ActionsPanel").background(cActionsBackground, "../specialarea-bg.png", "repeat-x")
				.border(1, cActionsBorder).padding(7).borderRadius(10);
	}

	private void userGuide(CssRenderer css) {
		css.style(".UserGuideWidget").color(cUserguide)
				.background(cUserguideBackground, "../specialarea-bg.png", "repeat-x").border(1, cUserguideBorder)
				.borderRadius(10);
		css.style(".UserGuideWidget-header").padding(7);
		css.style(".UserGuideWidget-content").margin(0, 7, 7, 7).paddingTop(7).borderTop(1, cUserguideBorder)
				.columnWidth(310).columnGap(25).textAlignJustify();
		css.style(".UserGuideWidget-content table").width100().marginBottom(10);
		css.style(".UserGuideWidget-content table td").fontSize(fontSizeSmall);
		css.style(".UserGuideWidget-content table td.headerCell").background(cUserguideTableHeaderBackground)
				.fontWeightBold();
	}

	private void comments(CssRenderer css) {
		css.style(".CommentsWidget").background(cCommentsBackground, "../specialarea-bg.png", "repeat-x")
				.border(1, cCommentsBorder).padding(7).borderRadius(10);
		css.style(".CommentsWidget-pageNavigator").borderTop(1, cPagePanelBorder).paddingTop(3);
		css.style(".CommentsWidget-pageNavigator-currentPage").border(1, cPagePanelBorder).padding(1, 5)
				.background(Colors.darken(cCommentsBackground));
		css.style(".CommentsWidget-pageNavigator-page a").displayBlock().border(1, cPagePanelBorder).padding(1, 5)
				.textDecorationNone().fontWeightBold();
		css.style(".CommentsWidget-pageNavigator-page-disabled").displayBlock().border(1, cPagePanelBorder)
				.padding(1, 5).textDecorationNone().fontWeightBold().color(cPagePanelBorder);
		css.style(".CommentsWidget .AViewEditWidget-viewer").padding(0);
		css.style(".CommentsWidget .AViewEditWidget-viewer-editable").padding(3);

		css.style(".CommentWidget").margin(15, 0, 10, 0).borderTop(1, cPagePanelBorder);
		css.style(".CommentWidget-header").margin(4, 0, 2, 0).width100();
		css.style(".CommentWidget-header-author").floatLeft().marginRight(5);
		css.style(".CommentWidget-header-date").color(cCommentDate);
		css.style(".CommentWidget-editor");

		css.style(".Comment-Widget-header-pub").floatRight();
		css.style(".Comment-Widget-header-pub .gwt-Button").fontSize(fontSizeSmall);
	}

	private void changeHistory(CssRenderer css) {
		css.style(".ChangeHistoryWidget").background(cChangesBackground, "../specialarea-bg.png", "repeat-x")
				.border(1, cChangesBorder).padding(7).borderRadius(10);
		css.style(".ChangeWidget").margin(15, 0, 10, 0).borderTop(1, cPagePanelBorder);
		css.style(".ChangeWidget-header").margin(4, 0, 2, 0);
		css.style(".ChangeWidget-header-author").floatLeft().marginRight(5);
		css.style(".ChangeWidget-header-date").floatLeft().marginRight(5).color(cChangeDate);
		css.style(".ChangeWidget-comment").marginTop(4);
		css.style(".ChangeWidget-comment .AViewEditWidget-viewer").background(cChangesCommentBackground);
		css.style(".ChangeWidget-diff span.added").color("green");
		css.style(".ChangeWidget-diff span.removed").color("darkred").textDecorationLineThrough();
	}

	private void blockList(CssRenderer css) {
		css.style(".ABlockWidget-extended").border(2, cBlockSelectionBorder).padding(3).backgroundWhite();
		css.style(".ABlockWidget-body").padding(10).border(1, cBlockHeaderBackground).background(cBlockBackground);

		// css.style(".BlockListWidget").border(1, "yellow").minHeight(50, "px");
		css.style(".BlockHeaderWidget").background(cBlockHeaderBackground, "../blockheader-bg.png", "repeat-x");
		css.style(".BlockHeaderWidget:hover").background(cBlockHeaderHoverBackground);
		css.style(".BlockHeaderWidget-dragHandle, .CreateStoryButtonWidget-dragHandle").margin(2).padding(2)
				.fontSize(fontSize - 1).lineHeight(lineHeight - 2).textAlignCenter().cursorMove()
				.background(cBlockHeaderDragHandleBackground, "../blockdraghandle-bg.png")
				.border(1, cNavigatorSeparator).borderRadius(5);
		css.style(".BlockHeaderWidget-anchor").displayBlock().floatLeft().width100().textDecorationNone();
		css.style(".BlockHeaderWidget-dragHandle:hover").background("white", "../blockdraghandle-hover-bg.png");

		css.style(".BlockHeaderWidget-cell").fontWeightBold().color(cHeaderBackground).padding(4, 5, 0, 5);

		css.style("a.BlockHeaderWidget-anchor:hover, a.BlockHeaderWidget-anchor:visited").color(cHeaderBackground);
		css.style(
			".BlockHeaderWidget-cell-secondary, a.BlockHeaderWidget-cell-secondary:visited, a.BlockHeaderWidget-cell-secondary:hover")
				.color(cBlockHeaderCellSecondary).fontWeightNormal();
		css.style(
			".BlockHeaderWidget-cell-small, a.BlockHeaderWidget-cell-small:visited, a.BlockHeaderWidget-cell-small:hover")
				.color(cBlockHeaderCellSecondary).fontSize(fontSizeSmall).fontWeightNormal().paddingTop(6);

		css.style(".BlockHeaderWidget .ButtonWidget").padding(2, 5, 0, 5).margin(0);
		css.style(".BlockHeaderWidget .ButtonWidget .gwt-Button").fontSize(fontSizeSmall).margin(0).padding(1);
		css.style(".BlockHeaderWidget .EmoticonsWidget").padding(3, 5, 0, 5);
		css.style(".BlockHeaderWidget .DropdownMenuButtonWidget").padding(2, 3, 0, 5);

		css.style(".BlockDndMarkerWidget").background("none");
		css.style(".BlockDndMarkerWidget-active").background(cError);

		css.style(".UsersOnBlockWidget").padding(3, 5, 0, 5).textAlignRight();
	}

	private void userStatus(CssRenderer css) {
		css.style(".UsersStatusWidget").borderBottom(1, cNavigatorSeparator);

		css.style(".UserStatusWidget").padding(3).borderTop(1, cNavigatorSeparator);
		css.style(".UserStatusWidget-name").floatLeft().textDecorationLineThrough().fontStyleItalic().cursorPointer();
		css.style(".UserStatusWidget-name-online").textDecorationNone().fontStyleNormal();
		css.style(".UserStatusWidget-selectedEntities a").fontFamilyMonospace();
		css.style(".UserStatusWidget-selectedEntities").floatRight().fontSize(fontSizeSmall + 1);
		css.style(".UserStatusWidget .AFieldValueWidget").backgroundNone().borderNone();
	}

	private void pagePanel(CssRenderer css) {
		css.style(".PagePanel");// .padding(10);
		css.style(".PagePanel-content").background("white").border(1, cPagePanelBorder);
		css.style(".PagePanel-header").padding(6, 10, 6, 10).fontSize(fontSizeTitle).lineHeight(lineHeightTitle)
				.background(cPagePanelHeaderBackground, "../page-header-bg.png").color(cPagePanelHeader);
		css.style(".PagePanel-header .gwt-Button").fontSize(fontSizeSmall);
		css.style(".PagePanel-header input").fontSize(fontSizeSmall);
		css.style(".PagePanel-header .gwt-Hyperlink").fontSize(fontSizeSmall);
		css.style(".PagePanel-section").margin(0, 10, 0, 10);
		css.style(".PagePanel-spacer").height(10);
	}

	@Override
	public String toString() {
		StringWriter out = new StringWriter();
		CssRenderer renderer = new CssRenderer(new PrintWriter(out));
		buildCss(renderer);
		renderer.flush();
		return out.toString();
	}
}
