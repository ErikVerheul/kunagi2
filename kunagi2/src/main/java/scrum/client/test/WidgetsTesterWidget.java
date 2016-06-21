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
package scrum.client.test;

import ilarkesto.gwt.client.AAction;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.DropdownMenuButtonWidget;
import ilarkesto.gwt.client.FloatingFlowPanel;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.ImageAnchor;
import ilarkesto.gwt.client.MultiSelectionWidget;
import ilarkesto.gwt.client.NavigatorWidget;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.ToolbarWidget;
import ilarkesto.gwt.client.editor.AIntegerEditorModel;
import ilarkesto.gwt.client.editor.ATextEditorModel;
import ilarkesto.gwt.client.editor.IntegerEditorWidget;
import scrum.client.ScrumGwt;
import scrum.client.collaboration.Wiki;
import scrum.client.common.ABlockWidget;
import scrum.client.common.AScrumAction;
import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.common.TooltipBuilder;
import scrum.client.img.Img;
import scrum.client.project.SelectProjectServiceCall;
import scrum.client.workspace.PagePanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class WidgetsTesterWidget extends AScrumWidget {

	private PagePanel page;

	@Override
	protected Widget onInitialization() {
		page = new PagePanel();
		page.setStyleName("WidgetsTesterWidget");

		textTextarea();
		testDropdown();
		testActions();
		testPagePanel();
		testFloatingFlowPanel();
		testTextConverter();
		testBlockList();
		testFields();
		testMultiSelection();
		testNavigator();
		testToolbar();
		testButtons();
		// testImageAnchor();

		return page;
	}

	private void textTextarea() {
		final TextArea ta = new TextArea();
		Button btn = new Button("check", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int cursorPos = ta.getCursorPos();
				Gwt.confirm("cursorPos: " + cursorPos);
			}
		});
		addTest("Textarea", Gwt.createFlowPanel(ta, btn));
	}

	private void testDropdown() {
		DropdownMenuButtonWidget empty = new DropdownMenuButtonWidget();
		addActions(empty);

		DropdownMenuButtonWidget label = new DropdownMenuButtonWidget();
		label.setLabel("My Dropdown");
		addActions(label);

		DropdownMenuButtonWidget icon = new DropdownMenuButtonWidget();
		icon.setIcon("emoticons/smile.png");
		addActions(icon);

		addTest("Dropdown", TableBuilder.row(false, 10, empty, label, icon));
	}

	private void addActions(DropdownMenuButtonWidget dropdown) {
		dropdown.addAction(new DummyAction("Item 1"));
		dropdown.addAction(new DummyAction("Item 2"));
		dropdown.addAction(new DummyAction("Item 3"));
		dropdown.addAction(new DummyAction("Item 4"));
		dropdown.addAction(new DummyAction("Item 5"));
	}

	private void testPagePanel() {
		PagePanel page = new PagePanel();
		page.addHeader("Header");
		page.addSection("section 1");
		page.addSection("section 2");
		page.addHeader("Another Header", new Label("extra stuff in the header"));
		page.addSection("section 3");
		addTest("PagePanel", page);
	}

	private void testActions() {
		ToolbarWidget toolbar = new ToolbarWidget();
		toolbar.addButton(new AAction() {

			@Override
			protected void onExecute() {
				new SelectProjectServiceCall("invalidprojectid").execute();
			}

			@Override
			public String getLabel() {
				return "Produce Server Error";
			}
		});
		addTest("Actions", toolbar);
	}

	private void testFloatingFlowPanel() {
		FloatingFlowPanel panel = new FloatingFlowPanel();
		panel.add(Gwt.createDiv("title", "Item 1"));
		panel.add(Gwt.createDiv("title", "Item 2"));
		panel.add(Gwt.createDiv("title", "Item 3"), true);
		panel.add(Gwt.createDiv("title", "Item 4"), true);
		addTest("FloatingFlowPanel", panel);
	}

	private void testTextConverter() {
		StringBuilder html = new StringBuilder();
		html.append(Wiki.toHtml("[Wiki] r1 aaaa t5 aaaa (r3) aaaa r3. aaaa r3: aaaa [t12] aaar7 aaaa r7x aaaa t9"))
				.append("<hr>");
		html.append(Wiki.toHtml("<b>html?</b> C&A\nnew line")).append("<hr>");
		addTest("TextConverter", new HTML(html.toString()));
	}

	private void testBlockList() {
		final BlockListWidget<String> list = new BlockListWidget<String>(TestBlock.FACTORY);
		list.setObjects("Element A", "Element B", "Element C", "Element D", "Element E");
		list.update();
		addTest("BlockList", list);
	}

	private static class TestBlock extends ABlockWidget<String> {

		@Override
		protected void onInitializationHeader(BlockHeaderWidget header) {
			header.addIconWrapper().setWidget(Img.bundle.hyperlink().createImage());
			header.setDragHandle("dmy666");
			header.addToolbarAction(new DummyAction("Function 1"));
			header.addToolbarAction(new DummyAction("Function 2"));
			header.addMenuAction(new DummyAction("Function 3"));
			header.addMenuAction(new DummyAction("Function 4"));
			header.addMenuAction(new DummyAction("Function 5"));
		}

		@Override
		protected void onUpdateHeader(BlockHeaderWidget header) {}

		@Override
		protected Widget onExtendedInitialization() {
			HTML content = new HTML(
					"<h3>"
							+ getObject()
							+ "</h3><p>Das ist der Content. Das ist der Content. Das ist der Content. Das ist der Content. </p>");
			return content;
		}

		public static BlockWidgetFactory<String> FACTORY = new BlockWidgetFactory<String>() {

			@Override
			public TestBlock createBlock() {
				return new TestBlock();
			}
		};

	}

	private String fieldsText = "test";
	private String fieldsRichText = "test";
	private Integer fieldsInt = 5;

	private void testFields() {
		TableBuilder tb = ScrumGwt.createFieldTable();
		tb.addFieldRow("TextPropertyEditorWidget", new ATextEditorModel() {

			@Override
			public void setValue(String value) {
				fieldsText = value;
			}

			@Override
			public String getValue() {
				return fieldsText;
			}
		});
		tb.addFieldRow("RichtextEditorWidget", new ATextEditorModel() {

			@Override
			public void setValue(String value) {
				fieldsRichText = value;
			}

			@Override
			public String getValue() {
				return fieldsRichText;
			}

			@Override
			public boolean isRichtext() {
				return true;
			}
		});
		tb.addFieldRow("IntegerEditorWidget", new IntegerEditorWidget(new AIntegerEditorModel() {

			@Override
			public void setValue(Integer value) {
				fieldsInt = value;
			}

			@Override
			public Integer getValue() {
				return fieldsInt;
			}

			@Override
			public void increment() {
				fieldsInt++;
			}

			@Override
			public void decrement() {
				fieldsInt--;
			}
		}));

		addTest("FieldsWidget", tb.createTable());
	}

	private void testMultiSelection() {
		MultiSelectionWidget<String> ms = new MultiSelectionWidget<String>();
		ms.add("Item 1");
		ms.add("Item 2");
		ms.add("Item 3");
		ms.update();
		addTest("MultiSelectionWidget", ms);
	}

	private void testNavigator() {
		NavigatorWidget navigator = new NavigatorWidget();
		navigator.addItem("Item 1", "1", null);
		navigator.addItem("Item 2", "2", null);
		navigator.addItem("Item 3", "3", null);
		addTest("NavigatorWidget", navigator);
	}

	private void testToolbar() {
		ToolbarWidget toolbar = new ToolbarWidget();
		toolbar.add(new ButtonWidget(createAction(Img.bundle.hyperlink().createImage(), "icon and text")));
		toolbar.add(new ButtonWidget(createAction("text only")));
		toolbar.add(new ButtonWidget(createAction(Img.bundle.hyperlink().createImage(), null)));
		addTest("ToolbarWidget", toolbar);
	}

	private void testButtons() {
		addTest("ButtonWidget:text-only", new ButtonWidget(createAction("text only")));
		addTest("ButtonWidget:icon-only", new ButtonWidget(createAction(Img.bundle.hyperlink().createImage(), null)));
		addTest("ButtonWidget:icon-text",
			new ButtonWidget(createAction(Img.bundle.hyperlink().createImage(), "icon and text")));
		addTest("ButtonWidget:nonexecutable",
			new ButtonWidget(createAction(Img.bundle.hyperlink().createImage(), "icon and text", false)));

		FlowPanel multipleButtons = new FlowPanel();
		multipleButtons.add(new ButtonWidget(createAction("Button 1")).update());
		multipleButtons.add(new ButtonWidget(createAction("Button 2")).update());
		multipleButtons.add(new ButtonWidget(createAction("Button 3")).update());
		addTest("multiple ButtonWidgets", multipleButtons);
	}

	private void testImageAnchor() {
		ImageAnchor a = new ImageAnchor(Img.bundle.hyperlink().createImage(), "click");
		addTest("ImageAnchor", a);
	}

	@Override
	protected void onUpdate() {

	}

	private void addTest(String title, Widget content) {
		Gwt.update(content);

		SimplePanel sectionContent = new SimplePanel();
		sectionContent.setStyleName("test-content");
		sectionContent.setWidget(content);

		page.addHeader(title);
		page.addSection(sectionContent);
	}

	private AAction createAction(String label) {
		return createAction(null, label);
	}

	private AAction createAction(final Image icon, final String label) {
		return createAction(icon, label, true);
	}

	private AAction createAction(final Image icon, final String label, final boolean executable) {
		return new AAction() {

			@Override
			protected void onExecute() {}

			@Override
			public String getLabel() {
				return label;
			}

			@Override
			public Image getIcon() {
				return icon;
			}

			@Override
			public boolean isExecutable() {
				return executable;
			}

			@Override
			public String getTooltip() {
				return "Tooltip for " + getLabel();
			}
		};
	}

	static class DummyAction extends AScrumAction {

		private String label;

		public DummyAction(String label) {
			this.label = label;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public boolean isExecutable() {
			return true;
		}

		@Override
		protected void updateTooltip(TooltipBuilder tb) {
			tb.setText("Tooltip for " + label);
		}

		@Override
		protected void onExecute() {}

	}

}
