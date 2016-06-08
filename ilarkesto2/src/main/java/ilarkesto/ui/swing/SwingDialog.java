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
package ilarkesto.ui.swing;

import static ilarkesto.swing.Swing.getIcon128;
import static ilarkesto.swing.Swing.getIcon16;
import static ilarkesto.swing.Swing.showModalDialogWithoutBlocking;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import static java.awt.Color.GRAY;
import java.awt.Component;
import java.awt.FlowLayout;
import static java.awt.FlowLayout.RIGHT;
import java.awt.Font;
import static java.awt.Font.BOLD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SwingDialog {

	private JDialog dialog;

	// --- dependencies ---

	private SwingUi ui;
	private String title;
	private String description;
	private String okLabel;
	private String okHint;
	private Component parentComponent;
	private Component component;
	private String icon128;

	public void setUi(SwingUi ui) {
		this.ui = ui;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIcon128(String icon128) {
		this.icon128 = icon128;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOkLabel(String okLabel) {
		this.okLabel = okLabel;
	}

	public void setOkHint(String okHint) {
		this.okHint = okHint;
	}

	public void setParentComponent(Component parentComponent) {
		this.parentComponent = parentComponent;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	// --- ---

	public void showDialog(ADialogAdapter adapter) {
		JButton abortButton = new JButton(ui.getLocalizer().string("abort"));
		abortButton.setIcon(getIcon16("abort"));
		abortButton.addActionListener(new DialogAbortActionListener(adapter));
		JButton okButton = new JButton(okLabel);
		okButton.setToolTipText(okHint);
		okButton.setIcon(getIcon16("ok"));
		okButton.addActionListener(new DialogOkActionListener(adapter));

		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), BOLD, 20));

		JLabel descriptionLabel = null;
		if (description != null && description.length() > 0) {
			descriptionLabel = new JLabel(description);
			descriptionLabel.setForeground(GRAY);
		}

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
		headerPanel.add(titleLabel, NORTH);
		if (descriptionLabel != null) {
			headerPanel.add(descriptionLabel, CENTER);
		}

		JPanel buttons = new JPanel(new FlowLayout(RIGHT, 0, 0));
		buttons.add(abortButton);
		buttons.add(new JLabel("  "));
		buttons.add(okButton);

		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(headerPanel, NORTH);
		if (icon128 != null) {
                        panel.add(new JLabel(getIcon128(icon128)), WEST);
                }
		panel.add(component, CENTER);
		panel.add(buttons, SOUTH);

		dialog = showModalDialogWithoutBlocking(parentComponent, title, panel);
	}

	public void closeDialog() {
		dialog.dispose();
		synchronized (dialog) {
			dialog.notifyAll();
		}
	}

	class DialogOkActionListener implements ActionListener {

		private ADialogAdapter adapter;

		public DialogOkActionListener(ADialogAdapter adapter) {
			this.adapter = adapter;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			closeDialog();
			adapter.onSubmit(null);
		}
	}

	class DialogAbortActionListener implements ActionListener {

		private ADialogAdapter adapter;

		public DialogAbortActionListener(ADialogAdapter adapter) {
			this.adapter = adapter;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			closeDialog();
			adapter.onAbort();
		}
	}

	static class DialogWindowAdapter extends WindowAdapter {

		private final ADialogAdapter adapter;

		public DialogWindowAdapter(ADialogAdapter adapter) {
			this.adapter = adapter;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			adapter.onAbort();
		}

	}
}
