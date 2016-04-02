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
package ilarkesto.swing;

import ilarkesto.base.StrExtend;
import static ilarkesto.base.StrExtend.replaceForHtml;
import ilarkesto.core.logging.Log;
import static ilarkesto.core.logging.Log.DEBUG;
import static ilarkesto.core.logging.Log.setDebugEnabled;
import ilarkesto.io.IO;
import static ilarkesto.io.IO.getScaled;
import static ilarkesto.io.IO.loadImage;
import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import static java.awt.Dialog.ModalityType.DOCUMENT_MODAL;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import static java.awt.EventQueue.isDispatchThread;
import java.awt.FlowLayout;
import static java.awt.FlowLayout.RIGHT;
import java.awt.Font;
import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.SystemTray;
import static java.awt.SystemTray.getSystemTray;
import java.awt.Toolkit;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import static java.lang.Math.min;
import static java.lang.Math.min;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.showConfirmDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import static javax.swing.SwingUtilities.invokeAndWait;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.SwingUtilities.windowForComponent;
import javax.swing.UIManager;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;
import static javax.swing.UIManager.setLookAndFeel;
import static javax.swing.UIManager.setLookAndFeel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

/**
 * Utility methods for Swing. Dialogs, frames, positioning.
 */
public class Swing {

	public static void main(String[] args) throws Throwable {
		setDebugEnabled(true);
		DEBUG((Object[])getInstalledLookAndFeels());
		setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		JOptionPane.showMessageDialog(null, createMessageComponent("Nachricht"));
		showMessageDialog(
			null,
			" Geschafft, obwohl der Text so scheiss lang ist.\n Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheissGeschafft, obwohl der Text so scheiss lang ist.\\n Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheissGeschafft, obwohl der Text so scheiss lang ist.\\n Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheissGeschafft, obwohl der Text so scheiss lang ist.\\n Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss lang ist. Geschafft, obwohl der Text so scheiss");
	}

	public static void setSystemLookAndFeel() {
		try {
			setLookAndFeel(getSystemLookAndFeelClassName());
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Dimension getFractionFromScreen(double xFactor, double yFactor) {
		Dimension screen = getDefaultToolkit().getScreenSize();
		return new Dimension((int) (screen.getWidth() * xFactor), (int) (screen.getHeight() * yFactor));
	}

	public static void invokeInEventDispatchThreadLater(Runnable runnable) {
		invokeLater(runnable);
	}

	public static void invokeInEventDispatchThread(Runnable runnable) {
		if (isEventDispatchThread()) {
			runnable.run();
		} else {
			try {
				invokeAndWait(runnable);
			} catch (InterruptedException ex) {
			} catch (InvocationTargetException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public static void assertEventDispatchThread() {
		if (!isEventDispatchThread()) {
                        throw new RuntimeException("Thread is not the EventDispatchThread");
                }
	}

	public static boolean isEventDispatchThread() {
		return isDispatchThread();
	}

	public static boolean isBlank(JTextField field) {
		if (field == null) {
                        return true;
                }
		return StrExtend.isBlank(field.getText());
	}

	public static JDialog showModalDialogWithoutBlocking(Component parent, String title, Component content) {
		final JDialog dialog = new JDialog(getWindow(parent), title);
		dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		dialog.setModal(true);
		dialog.setModalityType(DOCUMENT_MODAL);
		dialog.add(content);
		dialog.pack();
		// dialog.setMinimumSize(dialog.getPreferredSize());
		dialog.setResizable(false);
		placeBest(dialog, parent);
		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					sleep(100);
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
				dialog.setVisible(true);
				synchronized (dialog) {
					dialog.notifyAll();
				}
			}
		};
		t.setDaemon(true);
		t.start();
		while (!dialog.isVisible()) {
			synchronized (dialog) {
				try {
					dialog.wait(10);
				} catch (InterruptedException ex) {}
			}
		}
		return dialog;
	}

	public static void showMessageDialog(Component parent, String message) {
		JFrame frame = null;
		if (parent == null) {
			frame = new JFrame("Nachricht");
			center(frame);
			frame.setVisible(true);
			parent = frame;
		}
		JOptionPane.showMessageDialog(parent, createMessageComponent(message));
		if (frame != null) {
			frame.dispose();
		}
	}

	public static JComponent createMessageComponent(String message) {
		return createMessageComponent(message, 600, null);
	}

	public static JComponent createMessageComponent(String message, int preferredWidth, Color color) {
		if (message != null && !message.startsWith("<html")) {
                        message = "<html>" + replaceForHtml(message);
                }
		JEditorPane editor = new JEditorPane("text/html", message);
		if (color != null) {
                        editor.setForeground(color);
                }
		editor.setOpaque(false);
		editor.setEditable(false);
		int lines = editor.getPreferredSize().width / preferredWidth;
		if (lines == 0) {
			return editor;
		} else {
			int height = min(editor.getPreferredSize().height + (lines * 25), 300);
			JScrollPane scroller = new JScrollPane(editor);
			scroller.setPreferredSize(new Dimension(preferredWidth, height));
			scroller.setBorder(null);
			return scroller;
		}
	}

	public static String showTextEditorDialog(Component parent, String text, String title) {
		JTextArea textArea = new JTextArea(text, 25, 80);
		textArea.setFont(new Font(MONOSPACED, PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(640, 480));
		if (OK_OPTION != showConfirmDialog(parent, scrollPane, title, OK_CANCEL_OPTION, QUESTION_MESSAGE, null)) {
                        return null;
                }
		return textArea.getText();
	}

	public static int getWidth(String s, Font font) {
		JLabel l = new JLabel(s);
		l.setFont(font);
		return l.getPreferredSize().width;
	}

	public static JTextField activateSelectAllOnFocusGained(final JTextField field) {
		field.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				field.selectAll();
			}

		});
		return field;
	}

	public static JSplitPane createVerticalSplit(Component upper, Component lower, int dividerLocation) {
		JSplitPane splitPane = new JSplitPane(VERTICAL_SPLIT);
		splitPane.add(upper);
		splitPane.add(lower);
		splitPane.setDividerLocation(dividerLocation);
		return splitPane;
	}

	public static JSplitPane createHorizontalSplit(Component left, Component right) {
		JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT);
		splitPane.add(left);
		splitPane.add(right);
		return splitPane;
	}

	public static JPanel createPanelForButtonsRight(Action... actions) {
		JPanel panel = new JPanel(new FlowLayout(RIGHT));
		for (Action action : actions) {
			if (action == null) {
				panel.add(createSpacer(10, 1));
				continue;
			}
			panel.add(new JButton(action));
		}
		return panel;
	}

	public static Component createSpacer(int width, int height) {
		Canvas canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		return canvas;
	}

	/**
	 * Takes a screenshot of the default graphics device.
	 * 
	 * @param windowToHide If this parameter is provided, the window will be hidden before the screen is
	 *            captured and then showed again. This causes that the window does not appear on the
	 *            screenshot.
	 */
	public static BufferedImage captureScreen(Window windowToHide) {
		return captureScreen(getLocalGraphicsEnvironment().getDefaultScreenDevice(), windowToHide);
	}

	/**
	 * Takes a screenshot of a given device.
	 * 
	 * @param screen The graphics device to capture.
	 * @param windowToHide If this parameter is provided, the window will be hidden before the screen is
	 *            captured and then showed again. This causes that the window does not appear on the
	 *            screenshot.
	 */
	public static BufferedImage captureScreen(GraphicsDevice screen, Window windowToHide) {
		DisplayMode mode = screen.getDisplayMode();
		if (windowToHide != null) {
                        windowToHide.setVisible(false);
                }
		try {
			return new Robot().createScreenCapture(new Rectangle(mode.getWidth(), mode.getHeight()));
		} catch (AWTException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (windowToHide != null) {
                                windowToHide.setVisible(true);
                        }
		}
	}

	/**
	 * Shows a component in a JFrame. The frame will be packed and centered on the screen.
	 * 
	 * @param component The Component to show.
	 * @param title The title of the frame.
	 * @param icon TODO
	 * @param exitOnClose Specifies if the Application quits, when the frame is closed.
	 * @return The packed and visible frame.
	 */
	public static JFrame showInJFrame(final Component component, final String title, final Image icon,
			final boolean exitOnClose) {
		final JFrame frame = new JFrame(title);
		if (icon != null) {
                        frame.setIconImage(icon);
                }
		frame.setDefaultCloseOperation(exitOnClose ? EXIT_ON_CLOSE : HIDE_ON_CLOSE);
		frame.add(component);
		frame.pack();
		center(frame);
		frame.setVisible(true);
		return frame;
	}

	public static JFrame showInJFrame(Component component, double percentScreenWidth) {
		component.setPreferredSize(getFractionFromScreen(percentScreenWidth, percentScreenWidth));
		return showInJFrame(component);
	}

	public static JFrame showInJFrame(Component component) {
		return showInJFrame(component, component.getClass().getSimpleName(), null, true);
	}

	public static Window getWindow(Component component) {
		if (component == null) {
                        return null;
                }
		if (component instanceof Window) {
                        return (Window) component;
                }
		return windowForComponent(component);
	}

	/**
	 * Creates a default modal dialog for a component.
	 * 
	 * @param component The component, to show in a dialog.
	 * @param parent The component, whichs frame to be the parent of the modal dialog.
	 * @return The created dialog.
	 */
	public static JDialog createDialog(Component component, Component parent, String title) {
		Window window = getWindow(parent);
		JDialog dialog = new JDialog((Frame) window); // TODO remove cast!
		dialog.setTitle(title);
		dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		dialog.getContentPane().add(component);
		dialog.setModal(true);
		dialog.pack();
		placeBest(dialog, window);
		return dialog;
	}

	public static JFrame createFrame(Component component, Component parent, String title) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(component);
		frame.pack();
		placeBest(frame, parent);
		return frame;
	}

	private static Map<String, ImageIcon> icons = new HashMap<>();

	/**
	 * Determines and loads a 16x16 icon. The icon hast to be placed in the classpath as the following file:
	 * <code>img/16/{name}.png</code> The icon will be cached.
	 * 
	 * @param name The name of the icon (Filename without extension).
	 */
	public static ImageIcon getIcon16(String name) {
		return getIcon("img/16/" + name + ".png", 16);
	}

	public static ImageIcon getIcon128(String name) {
		return getIcon("img/128/" + name + ".png", 128);
	}

	public static ImageIcon getIcon(String path) {
		return getIcon(path, null);
	}

	public static ImageIcon getIcon(String path, Integer size) {
		if (icons.containsKey(path)) {
                        return icons.get(path);
                }
		ImageIcon result = new ImageIcon(getImage(path, size));
		icons.put(path, result);
		return result;
	}

	public static int getTrayIconSize() {
		Dimension dim = getSystemTray().getTrayIconSize();
		int size = (int) min(dim.getWidth(), dim.getHeight());
		return size;
	}

	public static Image getImage(String path, Integer size) {
		BufferedImage im = loadImage(path);
		if (size == null) {
                        return im;
                }
		return getScaled(im, size, size);
	}

	public static void addTrayIcon(TrayIcon trayIcon) {
		try {
			getSystemTray().add(trayIcon);
		} catch (AWTException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Centers a window on the screen.
	 */
	public static void center(Window window) {
		Dimension w = window.getSize();
		Dimension s = getDefaultToolkit().getScreenSize();
		window.setLocation((s.width - w.width) / 2, (s.height - w.height) / 2);
	}

	/**
	 * Places a window at the best (centered) position relative to another window.
	 */
	public static void placeBest(Window window, Window parent) {
		if (parent == null) {
			center(window);
			return;
		}
		placeBest(window, parent.getLocation(), parent.getSize());
	}

	/**
	 * Places a window at the best (centered) position relative to another window.
	 */
	public static void placeBest(Window window, Component parent) {
		Window parentWindow = getWindow(parent);
		if (parentWindow == null) {
			center(window);
			return;
		}
		placeBest(window, parentWindow.getLocation(), parent.getSize());
	}

	/**
	 * Places a window at the best (centered) position relative to another window.
	 */
	public static void placeBest(Window window, Point parentPosition, Dimension parentSize) {
		window.setLocation(getBestWindowPosition(window.getSize(), parentPosition, parentSize));
	}

	/**
	 * Determines the best (centered) position for a window relative to another window.
	 */
	public static Point getBestWindowPosition(Dimension dim, Point parentPos, Dimension parentDim) {
		return getBestWindowPosition(dim, getCenter(parentPos, parentDim));
	}

	/**
	 * Determines the best (centered) position for a window relative to another window.
	 */
	public static Point getBestWindowPosition(Dimension dim, Point parentPos) {
		int x = parentPos.x - (dim.width / 2);
		int y = parentPos.y - (dim.height / 2);
		boolean xOk = false;
		boolean yOk = false;
		if (x < 0) {
			x = 0;
			xOk = true;
		}
		if (y < 0) {
			y = 0;
			yOk = true;
		}
		if (!xOk) {
			if (x + dim.width > getDefaultToolkit().getScreenSize().width) {
				x = getDefaultToolkit().getScreenSize().width - dim.width;
			}
		}
		if (!yOk) {
			if (y + dim.height > getDefaultToolkit().getScreenSize().height - 20) {
				y = getDefaultToolkit().getScreenSize().height - dim.height - 20;
			}
		}
		return new Point(x, y);
	}

	/**
	 * Determines the center of an window.
	 */
	public static Point getCenter(Point parentPos, Dimension parentDim) {
		return new Point(parentPos.x + (parentDim.width / 2), parentPos.y + (parentDim.height / 2));
	}

	public static MenuItem createMenuItem(String label, ActionListener actionListener) {
		MenuItem item = new MenuItem(label);
		item.addActionListener(actionListener);
		return item;
	}

	private Swing() {}

}
