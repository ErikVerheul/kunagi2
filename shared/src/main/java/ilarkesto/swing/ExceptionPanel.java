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
package ilarkesto.swing;

import ilarkesto.base.StrExtend;
import static ilarkesto.base.StrExtend.getRootCauseMessage;
import static ilarkesto.core.base.Str.getStackTrace;
import static ilarkesto.swing.Swing.center;
import static ilarkesto.swing.Swing.createMessageComponent;
import static ilarkesto.swing.Swing.getWindow;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import java.awt.Component;
import java.awt.FlowLayout;
import static java.awt.FlowLayout.RIGHT;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ExceptionPanel extends JPanel {

    public static void main(String[] args) {
        showErrorDialog(
            null,
            new RuntimeException(
                    new RuntimeException(
                            new RuntimeException(
                                    new RuntimeException(
                                            new RuntimeException(
                                                    new RuntimeException(
                                                            new RuntimeException(
                                                                    "Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!Unrecoverable error in your brain! Unrecoverable error in your brain! Unrecoverable error in your brain!"))))))));
    }

    private JComponent messageComponent;
    private JTextArea stackTraceField;
    private JPanel expandButtonPanel;

    public ExceptionPanel(Throwable exception) {

        // messageField = new JEditorPane();
        // messageField = new JTextArea(3, 50);
        // messageField = new JLabel();
        // messageField.setOpaque(false);
        // Font font = messageField.getFont();
        // messageField.setFont(new Font(font.getFamily(), Font.BOLD, font.getSize()));
        // String msg = StrExtend.getRootCauseMessage(exception);
        // messageField.setText("<html>" + StrExtend.replaceForHtml(msg));
        messageComponent = createMessageComponent(getRootCauseMessage(exception));

        stackTraceField = new JTextArea(10, 50);
        stackTraceField.setOpaque(false);
        // stackTraceField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, font.getSize() - 1));
        stackTraceField.setText(getStackTrace(exception));

        setLayout(new BorderLayout(10, 10));
        add(messageComponent, NORTH);

        JButton expandButton = new JButton("?");
        // expandButton.setFont(new Font(font.getFamily(), Font.PLAIN, font.getSize() - 5));
        expandButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                expand();
            }

        });
        expandButtonPanel = new JPanel(new FlowLayout(RIGHT, 0, 0));
        expandButtonPanel.add(expandButton);
        add(expandButtonPanel, EAST);

        setOpaque(false);
    }

    private void expand() {
        JScrollPane stackTraceFieldScrollPane = new JScrollPane(stackTraceField);
        add(stackTraceFieldScrollPane, CENTER);
        remove(expandButtonPanel);
        Window window = getWindow(messageComponent);
        window.pack();
                center(window);
    }

    public static void showErrorDialog(Component parent, Throwable ex) {
                showMessageDialog(parent, new ExceptionPanel(ex), "Error", ERROR_MESSAGE);
    }

    public static void showDialog(Component parent, Throwable ex, String title) {
                showMessageDialog(parent, new ExceptionPanel(ex), title, ERROR_MESSAGE);
    }

}
