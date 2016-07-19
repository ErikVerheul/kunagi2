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


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.EAST;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.NORTH;
import static java.awt.GridBagConstraints.NORTHEAST;
import static java.awt.GridBagConstraints.NORTHWEST;
import static java.awt.GridBagConstraints.REMAINDER;
import static java.awt.GridBagConstraints.VERTICAL;
import static java.awt.GridBagConstraints.WEST;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author erik
 */
public class PanelBuilder {

	private List<Cell> cells = new ArrayList<>();
	private Cell lastCell;
	private GridBagConstraints defaultConstraints = new GridBagConstraints();
	private Border border;
	private BufferedImage backgroundImage;
	private boolean opaque = false;
	private Dimension preferredSize;
	private Color background;

    /**
     *
     */
    public PanelBuilder() {
		defaultConstraints.weightx = 1;
	}

    /**
     *
     * @param title
     * @return
     */
    public PanelBuilder setupAsButtonMenu(String title) {
		setDefaultPadding(2, 2, 2, 2);
		setDefaultFillToHorizontal();
		if (title != null) {
                        setBorder(new TitledBorder(new EtchedBorder(), title));
                }
		return this;
	}

    /**
     *
     * @param <C>
     * @param component
     * @return
     */
    @SuppressWarnings("unchecked")
	public <C extends Component> Cell<C> add(C component) {
		lastCell = new Cell<>(component);
		cells.add(lastCell);
		return lastCell;
	}

	// public Cell<JButton> add(AView.Button button) {
	// return add(button.toJButton());
	// }

    /**
     *
     * @return
     */
    
	public Cell<JLabel> addEmpty() {
		return add("");
	}

    /**
     *
     * @param label
     * @return
     */
    public Cell<JLabel> add(String label) {
		return add(new JLabel(label));
	}

    /**
     *
     * @param <C>
     * @param label
     * @param component
     * @return
     */
    public <C extends Component> Cell<C> addLn(String label, C component) {
		Insets insets = new Insets(defaultConstraints.insets.top, defaultConstraints.insets.left,
				defaultConstraints.insets.bottom, defaultConstraints.insets.right + 3);
		add(label).setInsets(insets);
		Cell<C> cell = add(component);
		nl();
		return cell;
	}

    /**
     *
     */
    public void nl() {
		if (lastCell == null) {
                        return;
                }
		lastCell.constraints.gridwidth = REMAINDER;
	}

    /**
     *
     * @param background
     * @return
     */
    public PanelBuilder setBackground(Color background) {
		this.background = background;
		this.opaque = true;
		return this;
	}

    /**
     *
     * @param preferredSize
     * @return
     */
    public PanelBuilder setPreferredSize(Dimension preferredSize) {
		this.preferredSize = preferredSize;
		return this;
	}

    /**
     *
     * @param opaque
     * @return
     */
    public PanelBuilder setOpaque(boolean opaque) {
		this.opaque = opaque;
		return this;
	}

    /**
     *
     * @param backgroundImage
     * @return
     */
    public PanelBuilder setBackgroundImage(BufferedImage backgroundImage) {
		this.backgroundImage = backgroundImage;
		return this;
	}

    /**
     *
     * @param border
     * @return
     */
    public PanelBuilder setBorder(Border border) {
		this.border = border;
		return this;
	}

    /**
     *
     * @param value
     * @return
     */
    public PanelBuilder setDefaultWeightX(double value) {
		defaultConstraints.weightx = value;
		return this;
	}

    /**
     *
     * @param value
     * @return
     */
    public PanelBuilder setDefaultWeightY(double value) {
		defaultConstraints.weightx = value;
		return this;
	}

    /**
     *
     * @return
     */
    public PanelBuilder setDefaultAnchorToNorthWest() {
		defaultConstraints.anchor = NORTHWEST;
		return this;
	}

    /**
     *
     * @return
     */
    public PanelBuilder setDefaultAnchorToNorth() {
		defaultConstraints.anchor = NORTH;
		return this;
	}

    /**
     *
     * @param top
     * @param bottom
     * @param left
     * @param right
     * @return
     */
    public PanelBuilder setDefaultPadding(int top, int bottom, int left, int right) {
		defaultConstraints.insets = new Insets(top, left, bottom, right);
		return this;
	}

    /**
     *
     * @return
     */
    public PanelBuilder setDefaultFillToHorizontal() {
		defaultConstraints.fill = HORIZONTAL;
		return this;
	}

    /**
     *
     * @return
     */
    public PanelBuilder setDefaultFillToBoth() {
		defaultConstraints.fill = BOTH;
		return this;
	}

    /**
     *
     * @return
     */
    public JPanel toPanel() {
		GridBagLayout layout = new GridBagLayout();
		JPanel panel = backgroundImage == null ? new JPanel(layout) : new MyPanel(layout, backgroundImage);
		for (Cell cell : cells) {
			layout.addLayoutComponent(cell.component, cell.constraints);
			panel.add(cell.component);
		}
		if (border != null) {
                        panel.setBorder(border);
                }
		if (preferredSize != null) {
                        panel.setPreferredSize(preferredSize);
                }
		if (background != null) {
                        panel.setBackground(background);
                }
		panel.setOpaque(opaque);
		return panel;
	}

    /**
     *
     * @param <C>
     */
    public class Cell<C extends Component> {

		private C component;
		private GridBagConstraints constraints;

        /**
         *
         * @param component
         */
        public Cell(C component) {
			if (component == null) {
                                throw new NullPointerException("component");
                        }
			this.component = component;
			constraints = (GridBagConstraints) defaultConstraints.clone();
			setAnchorToNorthWest();
		}

        /**
         *
         * @param insets
         * @return
         */
        public Cell setInsets(Insets insets) {
			constraints.insets = insets;
			return this;
		}

        /**
         *
         * @return
         */
        public Cell setAnchorToCenter() {
			constraints.anchor = CENTER;
			return this;
		}

        /**
         *
         * @return
         */
        public Cell setAnchorToNorthEast() {
			constraints.anchor = NORTHEAST;
			return this;
		}

        /**
         *
         * @return
         */
        public Cell setAnchorToEast() {
			constraints.anchor = EAST;
			return this;
		}

        /**
         *
         * @return
         */
        public Cell setAnchorToWest() {
			constraints.anchor = WEST;
			return this;
		}

        /**
         *
         * @return
         */
        public final Cell setAnchorToNorthWest() {
			constraints.anchor = NORTHWEST;
			return this;
		}

        /**
         *
         * @return
         */
        public Cell setFillToHorizontal() {
			constraints.fill = HORIZONTAL;
			return this;
		}

        /**
         *
         * @return
         */
        public Cell setFillToVertical() {
			constraints.fill = VERTICAL;
			return this;
		}

        /**
         *
         * @return
         */
        public Cell setFillToBoth() {
			constraints.fill = BOTH;
			return this;
		}

        /**
         *
         * @return
         */
        public Cell setFillToNone() {
			constraints.fill = NONE;
			return this;
		}

        /**
         *
         * @param top
         * @param bottom
         * @param left
         * @param right
         * @return
         */
        public Cell setPadding(int top, int bottom, int left, int right) {
			constraints.insets = new Insets(top, left, bottom, right);
			return this;
		}

        /**
         *
         * @param value
         * @return
         */
        public Cell setWeightX(double value) {
			constraints.weightx = value;
			return this;
		}

        /**
         *
         * @param value
         * @return
         */
        public Cell setWeightY(double value) {
			constraints.weighty = value;
			return this;
		}

        /**
         *
         * @param columns
         * @return
         */
        public Cell span(int columns) {
			constraints.gridwidth = columns;
			return this;
		}

        /**
         *
         */
        public void nl() {
			PanelBuilder.this.nl();
		}

        /**
         *
         * @return
         */
        public C getComponent() {
			return component;
		}

	}

	static class MyPanel extends JPanel {

		private BufferedImage backgroundImage;

		public MyPanel(LayoutManager layout, BufferedImage backgroundImage) {
			super(layout);
			this.backgroundImage = backgroundImage;
		}

		@Override
		public void paint(Graphics g) {
			if (backgroundImage != null) {
				g.drawImage(backgroundImage, 0, 0, getBackground(), null);
			}
			super.paint(g);
		}
	}

}
