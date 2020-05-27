package com.nick.wood.swing_gui.view.panels.objects;

import com.nick.wood.swing_gui.view.frames.EmptyWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TabPanel extends JPanel {

	private final JLayeredPane jLayeredPane;
	private JPanel glassPanel = new JPanel();
	private final JTabbedPane tab;
	private BufferedImage tabImage;
	private Point currentMousePoint;
	private boolean dragging = false;
	private boolean moving = false;
	private boolean exited = false;
	private JLabel label = null;

	public TabPanel() {

		setLayout(new GridLayout());

		this.jLayeredPane = new JLayeredPane();
		jLayeredPane.setLayout(new OverlayLayout(jLayeredPane));

		this.tab = new JTabbedPane();
		tab.add("MAin1", new TextPanel());
		tab.add("MAin2", new TextPanel());
		tab.add("MAin3", new TextPanel());

		glassPanel.setOpaque(false);
		glassPanel.setBackground(new Color(0, 0, 0, 150));

		jLayeredPane.add(tab, JLayeredPane.DEFAULT_LAYER);
		jLayeredPane.add(glassPanel, JLayeredPane.DRAG_LAYER);

		this.add(jLayeredPane);

		this.tab.addMouseMotionListener(mouseAdapter);
		this.tab.addMouseListener(mouseAdapter);

	}



	public TabPanel(Component content) {

		setLayout(new GridLayout());

		this.jLayeredPane = new JLayeredPane();
		jLayeredPane.setLayout(new OverlayLayout(jLayeredPane));

		this.tab = new JTabbedPane();
		tab.add("MAin1", content);

		glassPanel.setLayout(null);
		glassPanel.setOpaque(false);
		glassPanel.setBackground(new Color(0, 0, 0, 150));

		jLayeredPane.add(tab, JLayeredPane.DEFAULT_LAYER);
		jLayeredPane.add(glassPanel, JLayeredPane.DRAG_LAYER);

		this.add(jLayeredPane);

		this.tab.addMouseMotionListener(mouseAdapter);
		this.tab.addMouseListener(mouseAdapter);

	}

	private final MouseAdapter mouseAdapter = new MouseAdapter() {

		private Component removedTabContent;
		private int selectedTabNumber = -1;

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			this.selectedTabNumber = tab.getUI().tabForCoordinate(tab, e.getX(), e.getY());
			System.out.println("pressed");
			dragging = true;
			moving = false;
			e.consume();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			System.out.println("mouseReleased");
			if (exited && dragging) {
				new EmptyWindow(600, 600, new TabPanel(removedTabContent), new JMenuBar());
				exited = false;
			}
			dragging = false;
			moving = false;
			e.consume();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			exited = false;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
			exited = true;
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			super.mouseWheelMoved(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			super.mouseMoved(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (!moving && dragging) {
				Rectangle bounds = tab.getUI().getTabBounds(tab, selectedTabNumber);
				tabImage = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
				Graphics graphics = tabImage.getGraphics();
				graphics.drawImage(tabImage, 0, 0, bounds.width, bounds.height, bounds.x, bounds.y, bounds.x + bounds.width, bounds.y+bounds.height, tab);

				try {
					BufferedImage img = ImageIO.read(TabPanel.class.getResource("/icons/icon.png"));
					label = new JLabel(new ImageIcon(img));
					label.setLocation(e.getPoint());
					glassPanel.add(label);
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}

				jLayeredPane.getGraphics().drawImage(tabImage, e.getPoint().x, e.getPoint().y, null);
				removedTabContent = tab.getComponentAt(selectedTabNumber);
				tab.remove(selectedTabNumber);
				moving = true;
			}
			currentMousePoint = e.getPoint();
			if (label!=null) {
				label.setLocation(e.getPoint());
			}
			// Need to repaint
			tab.revalidate();
			tab.repaint();
			super.mouseDragged(e);
		}

	};
}
